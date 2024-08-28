/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.service;

import hdt.dto.HdtEngineError;
import hdt.dao.APPNumberDao;
import hdt.dao.DaoContainer;
import hdt.dao.FormulaDao;
import hdt.dao.HardwareConfigDao;
import hdt.dao.ParameterDao;
import hdt.dao.RoleDao;
import hdt.dao.VariableDao;
import hdt.domain.APPAlternative;
import hdt.domain.APPDependency;
import hdt.domain.APPNumber;
import hdt.domain.Alternatives;
import hdt.domain.Bundle;
import hdt.domain.Context;
import hdt.domain.Formula;
import hdt.domain.HardwareConfig;
import hdt.dto.InitialInputs;
import hdt.domain.Network;
import hdt.domain.Parameter;
import hdt.domain.HdtSystem;
import hdt.domain.ParameterInterface.ParameterChangeResult;
import hdt.domain.Role;
import hdt.domain.StringParameter;
import hdt.domain.Variable;
import hdt.service.HdtConstants.EngineState;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This is the HDT Dimensioning Engine. All necessary
 * data transformation, lookups and calculations are done
 * in this class.
 *
 * @author Ralph Schlosser
 * @version 0.1
 * <p>FIXME: The Version should come from CVS.</p>
 * <p>date 22/12/2010</p>
 */
public class HdtDimensionerImpl implements HdtDimensioner {

    private RoleDao roleDao;
    private ParameterDao parameterDao;
    private VariableDao variableDao;
    private FormulaDao formulaDao;
    private HardwareConfigDao hwConfigDao;
    private APPNumberDao appNumDao;
    private HdtSystem system;
    private Network network;
    private Bundle bundle;
    private List<Role> roleList;
    private List<Parameter> allMainParameters;
    private Map<String, Role> roleMap;
    private Map<String, Formula> formulaMap;
    private MultiKeyMap applicableCalculationParameters;
    private MultiKeyMap applicableViewParameters;
    private MultiKeyMap variableMap;
    private Map<String, List<HardwareConfig>> hwConfigMap;
    private Map<String, Alternatives> alternativesMap;    
    private Map<String, Boolean> hasAPPErrorsMap;
    private List<HdtEngineError> hdtErrorList;
    private List<Parameter> changedMainParameters;
    private Map<String, List<Parameter>> changedSecondaryParameters;
    private Map<String, List<APPNumber>> changedAPPQuantities;
    protected final Log logger = LogFactory.getLog(getClass());
    private List<Parameter> environmentParameters;
    private EngineState engineState;
    private InitialInputs cachedInputs;

    /**
     * The main HDT Dimensioner Object.
     * @param daoContainer - A DaoContainer 
     * @param inputs
     */
    public HdtDimensionerImpl(DaoContainer daoContainer) {
        // Retrieve DAOs. This is necessary as DAOs are instantiated in another context.       
        this.roleDao = daoContainer.getRoleDao();
        this.parameterDao = daoContainer.getParameterDao();
        this.variableDao = daoContainer.getVariableDao();
        this.formulaDao = daoContainer.getFormulaDao();
        this.hwConfigDao = daoContainer.getHwConfigDao();
        this.appNumDao = daoContainer.getAppNumDao();

        this.engineState = EngineState.CREATED;
    }

    @Override
    public void configureHdtDimensioner(InitialInputs inputs) {
        resetResults();

        this.network = inputs.getNetwork();
        this.bundle = inputs.getBundle();
        this.system = inputs.getSystem();

        // Save the Inputs for later use.
        this.cachedInputs = inputs;

        createEnvironmentParameters();
        // Get all the main parameters.
        gatherAllMainParameters();

        this.engineState = EngineState.CONFIGURED;
    }

    @Override
    public void prepareDimensioning() {
        // Need to configure engine first before proceeding.
        if (engineState != EngineState.CONFIGURED) {
            return;
        }

        // Determine the applicable roles for the system.
        this.roleList = roleDao.findRoleBySystem(system);

        // Extract roles and parameters for each system and network.        
        // Iterate over all roles and networks to determine necessary parameters.
        for (Role role : roleList) {
            // The role map comes in handy later, so we're caching this here.
            this.roleMap.put(role.getName(), role);
            // Determine applicable parameters. This is needed for the calculation step.
            gatherApplicableParameters(role);

            // Determine result variables and Formulas.
            appendAllVariablesAndFormulas(role);
        }

        // Now the engine is fully initialised and we can go ahead with the calculation.
        this.engineState = EngineState.INITIALISED;
    }

    /**
     *
     * @throws ScriptException
     */
    @Override
    public void calculateResults() throws ScriptException {

        // Don't calculate anything if we are not initialised.
        // This also means we won't calculate results twice if nothing has changed (parameters / quantities).
        if (engineState != EngineState.INITIALISED) {
            return;
        }

        // Create new JavaScript Engine.
        ScriptEngineManager factory = new ScriptEngineManager();

        // FIXME: Should dump GLOBAL parameters into engine here.
        // FIXME: Should cache intermediate results in a better way to avoid overheads when looping.

        Boolean hasVariableDependencies = true;
        Boolean hasAPPDependencies = true;
        String unresolvedDependencyExceptionRoleName = null;
        Map<String, Variable> resultsMap = new HashMap<String, Variable>();
        int numPasses = 0;

        // Repeat calculation until dependencies met or reached maximum number of passes.
        while ((hasVariableDependencies == true || hasAPPDependencies == true) && numPasses < HdtConstants.HDT_ENGINE_MAX_PASSES) {
            // Clear flags for new run.
            hasVariableDependencies = false;
            hasAPPDependencies = false;

            for (Role role : roleList) {
                // Create a new engine as we want to avoid problems with
                // incorrect variable and parameter scoping etc.
                // FIXME: A better approach would be to have a rules checker check
                // scoping first and only create the eninge once.
                ScriptEngine engine = factory.getEngineByName("JavaScript");

                // Get the list of variable applicable to the current role / network / system.
                List<Variable> varList = (List<Variable>) variableMap.get(role.getName(), network.getName(), system.getName());

                // Get the list of parameters applicable to the current bundle / role / network / system.
                // Note: This is done here because parameter names may not be unique across the board, i.e. roles may
                // share parameters of the same name, however within the CURRENT context the name must be uniqe.                                
                List<Parameter> parList = (List<Parameter>) applicableCalculationParameters.get(role.getName(), bundle.getName(), network.getName(), system.getName());
                dumpParametersIntoEngine(engine, parList);

                // This flag tells us that there was no change in the calculation results, thus we can skip HW lookup for this role.
                Boolean noChange = true;

                // This flag indicates if there was an error and we cannot proceed.
                Boolean engineError = false;

                // Calculate results for each variable for the current role.
                for (Variable var : varList) {
                    // Find the formula needed to calculate the variable result.
                    Formula fml = (Formula) formulaMap.get(var.getFormulaName());
                    if (fml != null) {
                        // Put the current variable object into the environment and make it accessible
                        // to JS. This way we can add dependencies to the dependency list.
                        engine.put(HdtConstants.HDT_JS_MAIN_VARIABLE, var);

                        // If there are variable dependencies for this variable, try to resolve them.
                        if (var.hasVariableDependencies()) {
                            resolveVariableDepdendencies(engine, var, resultsMap);
                        }

                        // If there are APP number dependencies for this role, try to resolve them.
                        if (var.hasAPPDependencies()) {
                            resolveAPPDependencies(engine, var);
                        }

                        // Execute the JavaScript code.
                        try {
                            engine.eval(fml.getFomulaJSCode());
                            // The result bears the respective variable's name, i.e. we look it up in the engine.
                            Object result = engine.get(var.getName());
                            // FIXME: Need to return an object in JS to specify result type!
                            if (result != null) {
                                try {
                                    if ((Double) result == -1.0) {
                                        HdtEngineError err = new HdtEngineError(HdtEngineErrorWrapper.HdtEngineErrorEnum.HDT_ERR_NO_VARIABLE_RESULT, var.getName());
                                        hdtErrorList.add(err);
                                        // FIXME: set engine error?
                                    }
                                } catch (ClassCastException ex) {
                                    logger.error("Cannot cast JavaScript result to Double. Variable " + var.getName() + " returned has not been assigned a return value in formula code.");
                                    HdtEngineError err = new HdtEngineError(HdtEngineErrorWrapper.HdtEngineErrorEnum.HDT_ERR_VARIABLE_UNDEF_TYPE, var.getName());
                                    hdtErrorList.add(err);
                                    // Set this to -1 so that we notify the user of the error.
                                    // FIXME: This doesn't seem like a clean solution.
                                    result = -1.0;
                                }


                                // FIXME: Does not consider the type of result!
                                // Check if we have a previous result.
                                if (var.isResultSet()) {
                                    Double oldRes = (Double) var.getResult();
                                    if (!oldRes.equals(result)) {
                                        var.setResult(result);
                                        noChange = false;
                                    } else {
                                        // Don't need to store the result in the result map down below.
                                        continue;
                                    }
                                } else {
                                    // Set the variable result as calculated in the script's code.
                                    var.setResult(result);
                                    noChange = false;
                                }

                                // Store each result calculated. We need this later on to resolve dependencies.                               
                                // NOTE: This assumes that variable names are UNIQUE per role.              
                                resultsMap.put(var.getName(), var);
                            } else {
                                // This is also an error because it means no result variable was passed back.
                                engineError = true;
                                HdtEngineError err = new HdtEngineError(HdtEngineErrorWrapper.HdtEngineErrorEnum.HDT_ERR_NO_VARIABLE, var.getName());
                                hdtErrorList.add(err);
                            }
                            // FIXME: Here we could return other objects as well, say error codes or instructions the engine should execute.
                        } catch (javax.script.ScriptException e) {
                            String exceptionName = e.toString();
                            engineError = true;
                            // Check the type of Exception we receive.
                            if (exceptionName.contains(HdtConstants.HDT_JS_VAR_DEPENDENCY_EXCEPTION)) {
                                // Check if another variable result is needed.
                                hasVariableDependencies = true;
                                // Save exception string for debugging purposes.
                                unresolvedDependencyExceptionRoleName = role.getName();
                                break;
                            } else if (exceptionName.contains(HdtConstants.HDT_JS_APP_DEPENDENCY_EXCEPTION)) {
                                // Check if a hardware result is needed (quantity etc.)                               
                                hasAPPDependencies = true;
                                // Save exception string for debugging purposes.
                                unresolvedDependencyExceptionRoleName = role.getName();
                                logger.debug("APP Dependencies still unresolved: ");
                                List<APPDependency> depList = var.getAPPDependencyList();
                                for (APPDependency d : depList) {
                                    logger.debug("Requested Role = " + d.getRoleName() + ", requested APP = " + d.getAppName());
                                }
                                break;
                            } else {
                                // Generic exception
                                HdtEngineError err = new HdtEngineError(HdtEngineErrorWrapper.HdtEngineErrorEnum.HDT_ERR_JS,
                                        "Lookup parameter: " + fml.getName() + ", Exception: " + e.getMessage());
                                hdtErrorList.add(err);
                                logger.error("Executing script: " + e);
                                logger.error("Formula evaluated: " + fml.getName());
                            }
                        }
                    } else {
                        engineError = true;
                        HdtEngineError err = new HdtEngineError(HdtEngineErrorWrapper.HdtEngineErrorEnum.HDT_ERR_NO_FORMULA, "Lookup parameter: " + var.getName());
                        hdtErrorList.add(err);
                        logger.error("NULL formula result for formula lookup of " + var.getFormulaName() + " in variable " + var.getName());
                    }
                }

                // We don't need to look up hardware for this role as either
                // not all dependencies fullfilled or no changes or both.
                //
                // FIXME: This means that the following scenario is currently impossible: (---> means "depends on", ++++> means "provides")
                // Role 1 ----> App 2
                // Role 2 ++++> App 2
                // Role 2 ----> Role 3
                //
                //
                // Explanation: Role 3 needs to be calculated first. Then we could calculate Role 2 to get a HW result.
                // But because of the circulat dependency we will never look up HW.

                if (engineError || noChange) {
                    continue;
                }

                lookupHardware(role, varList);
            }

            // Increase the number of passes.
            numPasses++;
        }

        // This means there are unresolved dependencies.
        if (numPasses == HdtConstants.HDT_ENGINE_MAX_PASSES) {
            // Check which type of unresolved dependeny.
            String debugStr = "Exhausted maximum number of passes for: " + unresolvedDependencyExceptionRoleName;
            HdtEngineError err = null;

            // Only these two, either or.
            if (hasVariableDependencies) {
                err = new HdtEngineError(HdtEngineErrorWrapper.HdtEngineErrorEnum.HDT_ERR_DEPENDENCY_VARIABLE, debugStr);
            } else if (hasAPPDependencies) {
                err = new HdtEngineError(HdtEngineErrorWrapper.HdtEngineErrorEnum.HDT_ERR_DEPENDENCY_APP, debugStr);
            }

            hdtErrorList.add(err);
        }

        // Calculation step finished.
        this.engineState = EngineState.CALCULATED;
    }

    @Override
    public void changeParameter(boolean isMainParameter, String parName, String roleName, Object newValue) {
        List<Parameter> parList;

        // Determine which parameter we want to change.
        if (isMainParameter) {
            parList = getAllMainParameters();
        } else {
            parList = getApplicableViewParameters(roleName);
        }

        if (parList == null || parList.isEmpty()) {
            logger.error("Attempting to change parameter " + parName + " failed as null or empty parameter list found.");
        } else {
            synchronized (parList) {
                // Iterate through the list. Parameter names must be UNIQUE within the CURRENT context.
                for (Parameter par : parList) {
                    if (par.getName().equals(parName)) {
                        Context ctx = par.getContext();
                        String currentRoleContext = ctx.getRoleContext();

                        // If we have a main parameter, we need to make sure that we are hitting the right one.
                        // There could be several of the same parameter name in the list, but we need to find the one with
                        // the correct context.
                        if (isMainParameter && (currentRoleContext.equals(roleName) == false)) {
                            break;
                        }

                        // Change the parameter value.
                        ParameterChangeResult result = par.changeValue(newValue);
                        if (result == ParameterChangeResult.CHANGE_SUCCESS) {
                            addChangedParameter(isMainParameter, roleName, par);

                            // Changing parameters may have a knock on effect on hardware results, thus if there
                            // were any previous APP changes for this role, we need to clear them.
                            List<APPNumber> aList = changedAPPQuantities.get(roleName);
                            if (aList != null) {
                                aList.clear();
                            }

                            // Check if we are changing a secondary parameter from global to local parameter.
                            if ((isMainParameter == false) && currentRoleContext.equals(HdtConstants.HDT_ANY_ROLE_CTX)) {
                                // Change the context for this role.
                                ctx.setRoleContext(roleName);
                            }

                        } else if (result == ParameterChangeResult.CHANGE_NONE) {
                            logger.error("Not changing parameter as same value provided.");
                        } else {
                            logger.error("Attempting to change parameter " + parName + " has failed.");
                        }
                    }
                }
            }
        }
    }

    @Override
    public void changeQuantities(String roleName, List<Map<String, String>> appList) {
        Alternatives alternatives = alternativesMap.get(roleName);

        // Get the currently selected alternative.
        APPAlternative engineAPPNumList = alternatives.getSelectedAlternative();

        // Iterate over the list of maps.
        for (Map<String, String> appMap : appList) {
            // For each Map, get the key and the value.

            String appNum = appMap.get("number");
            String userQty = appMap.get("quantity");

            for (APPNumber engineAPPNumber : engineAPPNumList.getAlternative()) {
                // Locate the respective APP number.
                if (engineAPPNumber.getName().equals(appNum)) {
                    // Set the user quantity.
                    engineAPPNumber.setUserQuantity(Integer.parseInt(userQty));
                    addChangedAPPQuantity(roleName, engineAPPNumber);
                    break;
                }
            }

        }
    }

    @Override
    public List<Parameter> getAllMainParameters() {
        return allMainParameters;
    }

    /**
     *
     * @return
     */
    @Override
    public MultiKeyMap getVariableMap() {
        return variableMap;
    }

    /**
     *
     * @return
     */
    @Override
    public Map<String, Formula> getFormulaMap() {
        return formulaMap;
    }

    /**
     *
     * @return
     */
    @Override
    public List<Role> getAllRoles() {
        return roleList;
    }

    @Override
    public Map<String, List<HardwareConfig>> getHwConfigMap() {
        return hwConfigMap;
    }
    
    @Override
    public Map<String, Boolean> getAPPErrorsMap() {
        return hasAPPErrorsMap;
    }

    @Override
    public List<HdtEngineError> getErrors() {
        return hdtErrorList;
    }

    @Override
    public List<Parameter> getChangedMainParameters() {
        return changedMainParameters;
    }

    @Override
    public Map<String, List<Parameter>> getChangedSecondaryParameters() {
        return changedSecondaryParameters;
    }

    @Override
    public Map<String, List<APPNumber>> getChangedAPPQuantities() {
        return changedAPPQuantities;
    }

    @Override
    public Map<String, Alternatives> getAlternativesMap() {
        return alternativesMap;
    }

    @Override
    public Boolean getAppsHaveChanged() {
        return changedAPPQuantities.isEmpty();
    }

    @Override
    public Boolean getParametersHaveChanged() {
        return (changedMainParameters.isEmpty() && changedSecondaryParameters.isEmpty());
    }

    @Override
    public InitialInputs getInitialInputs() {
        return cachedInputs;
    }

    @Override
    public void reCalculate() throws ScriptException {
        // Clear error messages.
        clearErrors();

        // Parameters / quantities may have changed, i.e. reset to intialised state.        
        if (engineState == EngineState.CALCULATED) {
            this.engineState = EngineState.INITIALISED;

            // Invoke calculation again.
            this.calculateResults();
        } else {
            logger.error("Cannot invoke recalculation if no results calculated yet / not properly initialised before.");
        }
    }

    @Override
    public List<Parameter> getApplicableViewParameters(String roleName) {
        return (List<Parameter>) applicableViewParameters.get(roleName, bundle.getName(), network.getName(), system.getName());
    }

    @Override
    public List<Parameter> getApplicableCalculationParameters(String roleName) {
        return (List<Parameter>) applicableCalculationParameters.get(roleName, bundle.getName(), network.getName(), system.getName());
    }

    // Reset the dimensioner main inputs for a complete new cycle.
    private void resetResults() {
        roleDao.clear();

        // Reset locally cached data.        
        this.hdtErrorList = null;
        this.environmentParameters = null;

        this.roleList = new ArrayList<Role>();
        this.roleMap = new HashMap<String, Role>();
        this.formulaMap = new HashMap<String, Formula>();
        this.allMainParameters = new ArrayList<Parameter>();
        this.applicableCalculationParameters = new MultiKeyMap();
        this.applicableViewParameters = new MultiKeyMap();
        this.variableMap = new MultiKeyMap();
        this.hwConfigMap = new HashMap<String, List<HardwareConfig>>();
        this.alternativesMap = new HashMap<String, Alternatives>();        
        this.hasAPPErrorsMap = new HashMap<String, Boolean>();
        this.hdtErrorList = new ArrayList<HdtEngineError>();
        this.environmentParameters = new ArrayList<Parameter>();
        this.changedMainParameters = new ArrayList<Parameter>();
        this.changedSecondaryParameters = new HashMap<String, List<Parameter>>();
        this.changedAPPQuantities = new HashMap<String, List<APPNumber>>();
    }

    // Reset results for new calculation (changed parameters etc.). Also needed for page refresh.
    // FIXME: Need to review this. Don't like the tests for each step. There must be a more clever way.
    private void clearResults() {
        if (roleList != null) {
            this.roleList.clear();
        }

        if (roleMap != null) {
            this.roleMap.clear();
        }

        if (allMainParameters != null) {
            this.allMainParameters.clear();
        }

        if (applicableCalculationParameters != null) {
            this.applicableCalculationParameters.clear();
        }

        if (applicableViewParameters != null) {
            this.applicableViewParameters.clear();
        }

        if (variableMap != null) {
            this.variableMap.clear();
        }

        if (formulaMap != null) {
            this.formulaMap.clear();
        }

        if (changedAPPQuantities != null) {
            changedAPPQuantities.clear();
        }

        if (changedMainParameters != null) {
            changedMainParameters.clear();
        }

        if (changedSecondaryParameters != null) {
            changedSecondaryParameters.clear();
        }

        clearErrors();
        clearHwResults();
    }

    private void clearHwResults() {
        if (hwConfigMap != null) {
            this.hwConfigMap.clear();
        }

        if (alternativesMap != null) {
            this.alternativesMap.clear();
        }       
    }

    private void clearErrors() {
        if (hasAPPErrorsMap != null) {
            hasAPPErrorsMap.clear();
        }

        if (hdtErrorList != null) {
            this.hdtErrorList.clear();
        }
    }

    //
    // This method retrieves a list of ALL main parameters for ALL roles.
    // 
    private void gatherAllMainParameters() {
        this.allMainParameters = this.parameterDao.findAllMainParameters(bundle, network, system);

        // Put the list of all parameters for this context in the map.
        if (allMainParameters == null || allMainParameters.isEmpty()) {
            logger.error("CONFIG ERROR: Empty main parameter list for bundle = " + bundle.getName()
                    + ", network = " + network.getName() + ", system = " + system.getName());

            HdtEngineError err = new HdtEngineError(HdtEngineErrorWrapper.HdtEngineErrorEnum.HDT_ERR_INTERNAL, null);
            hdtErrorList.add(err);
        }
    }

    //
    // VERY BAD CODE needs to be redone!!!
    //
    private void gatherApplicableParameters(Role role) {
        List<Parameter> roleSpecPar = this.parameterDao.findRoleSpecificMainParameters(bundle, role, network, system);
        List<Parameter> secondaryPar = this.parameterDao.findSecondaryParameters(bundle, role, network, system);

        if (roleSpecPar == null || secondaryPar == null) {
            logger.error("CONFIG ERROR: Empty applicable parameter list for bundle = " + bundle.getName() + ", role = " + role.getName()
                    + ", network = " + network.getName() + ", system = " + system.getName());
        } else {
            List<Parameter> parList1 = new ArrayList<Parameter>();
            List<Parameter> parList2 = new ArrayList<Parameter>();

            // 
            // FIXME: Enforce preference here!
            //
            // Parameters needed for the view.           
            parList2.addAll(roleSpecPar);
            parList2.addAll(secondaryPar);

            //            
            // After this parameter names in parList2 must be UNIQUE for given context!!!            
            // ENSURE UNIQUENESS

            //
            // Need to review this!
            // 
            List<Parameter> processedParams = null;
            if (changedMainParameters.isEmpty() == false) {
                processedParams = new ArrayList<Parameter>();
                for (Parameter par : parList2) {
                    // Take changed, if one is found
                    boolean foundChanged = false;
                    for (Parameter changedMP : changedMainParameters) {
                        // If the role-specific main parameter has been changed already, the changed version takes precedence.
                        if (par.getName().equals(changedMP.getName())) {
                            processedParams.add(changedMP);
                            foundChanged = true;
                            break;
                        }
                    }
                    // If not, add from normal parameters.
                    if (!foundChanged) {
                        processedParams.add(par);
                    }
                }
            } else {
                processedParams = parList2;
            }

            // Parameters needed for the calculation.
            parList1.addAll(environmentParameters);
            parList1.addAll(processedParams);

            this.applicableCalculationParameters.put(role.getName(), bundle.getName(), network.getName(), system.getName(), parList1);
            this.applicableViewParameters.put(role.getName(), bundle.getName(), network.getName(), system.getName(), processedParams);
        }
    }

    private void appendAllVariablesAndFormulas(Role role) {
        List<Variable> variablesList = this.variableDao.findVariables(role, network, system);

        if (variablesList.isEmpty()) {
            HdtEngineError err = new HdtEngineError(HdtEngineErrorWrapper.HdtEngineErrorEnum.HDT_ERR_NO_VARIABLES, "Lookup parameters: " + role.getName()
                    + ", " + system.getName());
            hdtErrorList.add(err);
        }

        this.variableMap.put(role.getName(), network.getName(), system.getName(), variablesList);
        for (Variable var : variablesList) {
            Formula fml = formulaDao.findFormulaByName(var.getFormulaName());

            if (fml == null) {
                HdtEngineError err = new HdtEngineError(HdtEngineErrorWrapper.HdtEngineErrorEnum.HDT_ERR_NO_FORMULA, "Lookup parameters: " + role.getName() + ", " + system.getName() + ", " + network.getName());
                hdtErrorList.add(err);
            }

            formulaMap.put(var.getFormulaName(), fml);
        }
    }

    // Note: If a parameter of the same name appears several times in the parameter list, 
    // only the last parameter will actually be available in the JavaScript environment!
    // This works as a local override for the current role.
    private void dumpParametersIntoEngine(ScriptEngine engine, List<Parameter> parameters) {
        for (Parameter par : parameters) {
            // Use the default, if nothing is set.
            if (par.getValueSet()) {
                engine.put(par.getName(), par.getValue());
            } else {
                engine.put(par.getName(), par.getDefaultValue());
            }
        }
    }

    // Create Environment Parameters
    private void createEnvironmentParameters() {
        // Add the currently selected system to the list.
        Parameter<String> envProduct1 = new StringParameter(HdtConstants.HDT_ENV_PRODUCT_VAL, HdtConstants.HDT_ENV_PRODUCT_DESC);
        envProduct1.setValue(this.system.getName());
        envProduct1.setVisibility(HdtConstants.Visibility.VISIBILITY_ENGINE.getVisibilityAsInt());

        // Add Environment Parameters to list of Environment Parameters.
        this.environmentParameters.add(envProduct1);

        // Add the currently selected network to the list.
        Parameter<String> envProduct2 = new StringParameter(HdtConstants.HDT_ENV_NETWORK_VAL, HdtConstants.HDT_ENV_NETWORK_DESC);
        envProduct2.setValue(network.getName());
        envProduct2.setVisibility(HdtConstants.Visibility.VISIBILITY_ENGINE.getVisibilityAsInt());

        // Add Environment Parameters to list of Environment Parameters.
        this.environmentParameters.add(envProduct2);
    }

    private void resolveVariableDepdendencies(ScriptEngine engine, Variable var, Map<String, Variable> resultsMap) {
        List<String> depList = var.getVariableDependencyList();

        // Go through the list of dependencies.
        for (String depName : depList) {
            if (resultsMap.get(depName) != null) {
                // The dependency can be satisfied from the results gathered so far.
                // So we put it in the JS environment.
                engine.put(depName, var.getResult());
            }
        }
    }

    // NOTE: The name of the APPNumber quantity object will be as follows:
    // e.g. APP number is APP1234, role is ADMIN, then the JS variable name
    // is ADMIN_APP1234_QTY.
    private void resolveAPPDependencies(ScriptEngine engine, Variable var) {
        List<APPDependency> depList = var.getAPPDependencyList();

        // Go through the list of requested dependencies.
        //
        // FIXME: Need to synchronise this???
        //
        for (APPDependency dep : depList) {
            String appDepName = dep.getAppName();
            Alternatives alt = alternativesMap.get(dep.getRoleName());

            // No alternatives found, i.e. the role is not defined in the current context but
            // we requested to resolve an APP dependency OR no hardware results.
            if (alt == null) {
                putAPPIntoEngine(engine, var, dep, HdtConstants.HDT_APP_DEPENDENCY_INVALID_CONTEXT_VAL);
                // FIXME: Should really add an engine error here?
                continue;
            }

            APPAlternative appAlt = alt.getSelectedAlternative();
            List<APPNumber> appList = null;

            if (appAlt != null) {
                appList = appAlt.getAlternative();
            }

            // First check if the requested dependency is defined in the current context for this role.
            APPNumber foundAPP = null;
            if (appList != null) {
                for (APPNumber app : appList) {
                    if (appDepName.equals(app.getName())) {
                        foundAPP = app;
                        break;
                    }
                }

                if (foundAPP != null) {
                    if (foundAPP.isExposedToJS()) {
                        // We are exposing the APPNumber quantity.
                        if (foundAPP.isUserQtySet()) {
                            putAPPIntoEngine(engine, var, dep, foundAPP.getUserQuantity());
                        } else {
                            putAPPIntoEngine(engine, var, dep, foundAPP.getQuantity());
                        }
                    } else {
                        // This is an error as the user has forgotten to expose the APP number to JS.
                        HdtEngineError err = new HdtEngineError(HdtEngineErrorWrapper.HdtEngineErrorEnum.HDT_ERR_DEPENDENCY_APP_NOT_EXPOSED, "APP Number: " + foundAPP.getName());
                        hdtErrorList.add(err);
                        putAPPIntoEngine(engine, var, dep, HdtConstants.HDT_APP_DEPENDENCY_INVALID_CONTEXT_VAL);
                    }
                } else {
                    // APP number we are looking for is not applicable in the current context. Could be either
                    // due to the script used being generic (i.e. requesting APP dependencies for all contexts)
                    // or misconfiguration.
                    putAPPIntoEngine(engine, var, dep, HdtConstants.HDT_APP_DEPENDENCY_INVALID_CONTEXT_VAL);
                }
            } else {
                // No alternatives.
                HdtEngineError err = new HdtEngineError(HdtEngineErrorWrapper.HdtEngineErrorEnum.HDT_ERR_NO_APP_RESULT, "APP Number: " + appDepName);
                hdtErrorList.add(err);
                putAPPIntoEngine(engine, var, dep, HdtConstants.HDT_APP_DEPENDENCY_INVALID_CONTEXT_VAL);
            }
        }

        // Remove all resolved dependencies from the APP dependency list.
        var.removeDepInRemoveList();
    }

    private void putAPPIntoEngine(ScriptEngine engine, Variable var, APPDependency dep, Object value) {
        String varName = dep.getRoleName() + "_" + dep.getAppName() + "_QTY";

        // This means we have requested an APP number but this APP Number is not defined within the current context,
        // i.e. the dependency can never be satisfied.
        engine.put(varName, value);

        // Mark this dependency as removed because we have resolved it.
        var.removeAPPDependency(dep);
    }

    private void lookupHardware(Role role, List<Variable> varList) {
        List<HardwareConfig> hwCfgList = hwConfigDao.findHardwareConfig(varList, role);
      
        if (hwCfgList.isEmpty()) {
            String debugStr = "Lookup parameters: " + role.getName() + ", ";
            for (Variable v : varList) {
                debugStr += v.getName() + " = " + v.getResult() + " *** ";
            }
            HdtEngineError err = new HdtEngineError(HdtEngineErrorWrapper.HdtEngineErrorEnum.HDT_ERR_NO_HW_RESULT, debugStr);
            hdtErrorList.add(err);
        }

        List<HardwareConfig> oldHwCfg = hwConfigMap.get(role.getName());
        if (oldHwCfg == null || !oldHwCfg.equals(hwCfgList)) {
            this.hwConfigMap.put(role.getName(), hwCfgList);
            
            // The final step is to express the Hardware Configuration in terms of APP numbers.
            Alternatives appNumAlternatives = appNumDao.convertHardwareConfigToAPPNumbers(hwCfgList);

            // Check if one of the alternatives has no APP mapping.
            if (appNumAlternatives.getContainsEmpty() == true) {
                HdtEngineError err = new HdtEngineError(HdtEngineErrorWrapper.HdtEngineErrorEnum.HDT_ERR_NO_APP_RESULT, role.getName());
                hdtErrorList.add(err);
                // FIXME: This is sort of ugly.
                hasAPPErrorsMap.put(role.getName(), Boolean.TRUE);
            }

            this.alternativesMap.put(role.getName(), appNumAlternatives);           
        }
    }

    private void addChangedParameter(Boolean isMainParameter, String roleName, Parameter par) {
        if (isMainParameter) {
            // Add a changed main parameter to the list.
            int idx = changedMainParameters.indexOf(par);
            if (idx != -1) {
                changedMainParameters.remove(par);
            }
            changedMainParameters.add(par);
        } else {
            List<Parameter> cList = changedSecondaryParameters.get(roleName);
            if (cList != null) {
                int idx = cList.indexOf(par);
                if (idx != -1) {
                    cList.remove(idx);
                }
                cList.add(par);
            } else {
                // We need to create the list first.
                List<Parameter> newParameterList = new ArrayList<Parameter>();
                newParameterList.add(par);
                changedSecondaryParameters.put(roleName, newParameterList);
            }

            // The Role has been modified by the user so we keep a note of this.
            Role changedRole = roleMap.get(roleName);
            changedRole.setUserModified(true);
        }
    }

    private void addChangedAPPQuantity(String roleName, APPNumber app) {
        List<APPNumber> aList = changedAPPQuantities.get(roleName);
        if (aList != null) {
            // There is at least one changed APP quantity already.
            // Check if we are trying to add the twice.
            int idx = aList.indexOf(app);
            if (idx != -1) {
                // The parameter is already in the list. So we remove the old one and add the new one.
                aList.remove(idx);
            }
            aList.add(app);
        } else {
            // We need to create the list first.
            List<APPNumber> newAPPList = new ArrayList<APPNumber>();
            newAPPList.add(app);
            changedAPPQuantities.put(roleName, newAPPList);

            Role changedRole = roleMap.get(roleName);
            changedRole.setUserModified(true);
        }
    }
    
    @Override
    public void changeHwAlternative(String roleName, String selectedId) {        
        if (roleName != null && selectedId != null) {                    
            try {
                int sId = Integer.parseInt(selectedId);                
                Alternatives existingAlt = alternativesMap.get(roleName);            
                if (existingAlt != null) {
                    int oldId = existingAlt.getSelectedAlternativeIdx();
                    if (oldId != sId) {
                        existingAlt.setSelectedAlternativeIdx(sId);
                    } else {
                        logger.error("changeHwAlternative: Not changing HW Alternative Index, as it's the same as before.");
                    }
                } else {
                    logger.error("changeHwAlternative: NULL HW Alternative returned.");
                }                
            } catch (NumberFormatException e) {
                logger.error("changeHwAlternative: Trying to set alternative to something that is not an Integer: " + selectedId);
                logger.error(e.getMessage());
            }                                    
        }        
    }
}

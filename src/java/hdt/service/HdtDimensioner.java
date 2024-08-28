/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.service;

import hdt.domain.APPAlternative;
import hdt.domain.APPNumber;
import hdt.domain.Alternatives;
import hdt.domain.Formula;
import hdt.domain.HardwareConfig;
import hdt.domain.Parameter;
import hdt.domain.Role;
import hdt.dto.HdtEngineError;
import hdt.dto.InitialInputs;
import java.util.List;
import java.util.Map;
import javax.script.ScriptException;
import org.apache.commons.collections.map.MultiKeyMap;

/**
 *
 * @author escralp
 */
public interface HdtDimensioner {

    public void configureHdtDimensioner(InitialInputs inputs);

    public void prepareDimensioning();

    public void calculateResults() throws ScriptException;

    public void changeParameter(boolean isMainParameter, String parName, String roleName, Object newValue);
    
    public void changeHwAlternative(String roleName, String selectedId);
        
    public void changeQuantities(String roleName, List<Map<String, String>> appList);
    
    public List<Parameter> getAllMainParameters();
       
    public MultiKeyMap getVariableMap();

    public Map<String, Formula> getFormulaMap();

    public List<Role> getAllRoles();

    public Map<String, List<HardwareConfig>> getHwConfigMap();
        
    public Map<String, Alternatives> getAlternativesMap();

    public Map<String, Boolean> getAPPErrorsMap();

    public List<HdtEngineError> getErrors();

    public List<Parameter> getChangedMainParameters();
    
    public Map<String, List<Parameter>> getChangedSecondaryParameters();

    public Map<String, List<APPNumber>> getChangedAPPQuantities();

    public Boolean getAppsHaveChanged();

    public Boolean getParametersHaveChanged();

    public InitialInputs getInitialInputs();

    public void reCalculate() throws ScriptException;
    
    public List<Parameter> getApplicableViewParameters(String roleName);
     
    public List<Parameter> getApplicableCalculationParameters(String roleName); 
}
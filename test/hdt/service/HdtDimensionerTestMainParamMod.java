/*
 * This tests the engine capability to modify parameters.
 */
package hdt.service;

import hdt.dao.APPNumberDao;
import hdt.test.BaseHdtTest;
import hdt.dao.DaoContainer;
import hdt.dao.FormulaDao;
import hdt.dao.HardwareConfigDao;
import hdt.dao.NetworkDao;
import hdt.dao.ParameterDao;
import hdt.dao.HdtSystemDao;
import hdt.dao.RoleDao;
import hdt.dao.VariableDao;
import hdt.domain.APPNumber;
import hdt.domain.Bundle;
import hdt.domain.Formula;
import hdt.domain.HardwareConfig;
import hdt.domain.Network;
import hdt.domain.Parameter;
import hdt.domain.HdtSystem;
import hdt.domain.Role;
import hdt.domain.Variable;
import hdt.dto.InitialInputs;
import hdt.test.Executor;
import hdt.test.MyLogger.LogLevel;
import java.util.List;
import java.util.Map;
import javax.script.ScriptException;
import org.apache.commons.collections.map.MultiKeyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author escralp
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class HdtDimensionerTestMainParamMod extends BaseHdtTest {

    //private static final Network anyNetwork = new Network(HdtConstants.HDT_ANY_NETWORK);
    // private static final String anyNetworkName = anyNetwork.getName();
    // The data access objects are injected by Spring.    
    @Autowired
    private NetworkDao netDao;
    @Autowired
    private HdtSystemDao sysDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private ParameterDao parameterDao;
    @Autowired
    private VariableDao variableDao;
    @Autowired
    private FormulaDao formulaDao;
    @Autowired
    private HardwareConfigDao hwConfigDao;
    @Autowired
    private APPNumberDao appNumDao;
    private DaoContainer daoContainer;

    public HdtDimensionerTestMainParamMod() {
    }

    @Before
    public void setUp() {
        daoContainer = new DaoContainer();
        daoContainer.setNetworkDao(netDao);
        daoContainer.setSystemDao(sysDao);
        daoContainer.setRoleDao(roleDao);
        daoContainer.setParameterDao(parameterDao);
        daoContainer.setVariableDao(variableDao);
        daoContainer.setFormulaDao(formulaDao);
        daoContainer.setHwConfigDao(hwConfigDao);
        daoContainer.setAppNumDao(appNumDao);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of various HDT Dimensioning Engine aspects.
     */
    @Test
    public void testDimensioner() throws ScriptException {
        // Create new dimensioner and calculate dimensioning result.
        InitialInputs inputs = new InitialInputs();
        HdtSystem s = new HdtSystem("RC112", "OSS RC 11.2");
        //HdtSystem s = new HdtSystem("EE113", "OSS RC 11.2");
        //HdtSystem s = new HdtSystem("RC112_ES112", "OSS RC 11.2 + ENIQS 11.2");
        //Network n = new Network("LR_CN", "Lte + Core");
        Network n = new Network("LR", "Lte");
        //Network n = new Network("WR", "WRAN");
        //Network n = new Network("Packet Core & RAN", "Packet Core & RAN");

        //Network n = new Network("CN", "Core");
        Bundle b = new Bundle("MEDIUM", "Medium Bundle");
        //Bundle b = new Bundle("LARGE", "Small Bundle");
        //Bundle b = new Bundle("<1.5 million subscribers", "<1.5 million subscribers");
        //Bundle b = new Bundle("1.5 - 4.5 million subscribers","1.5 - 4.5 million subscribers");

        myLogger.setLevel(LogLevel.SUB3);

        // Insert the test data into the inputs.
        inputs.setSystem(s);
        inputs.setNetwork(n);
        inputs.setBundle(b);

        myLogger.log(LogLevel.MAIN, "INPUTS: System = " + s.getName() + ", Network = " + n.getName() + ", Bundle = " + b.getName());

        HdtDimensioner dimensioner = new HdtDimensionerImpl(daoContainer);
        dimensioner.configureHdtDimensioner(inputs);

        // First run
        dimensioner.prepareDimensioning();

        List<Parameter> mainDimInpParList = dimensioner.getAllMainParameters();
        System.out.println("Main Dimensioning Parameters: " + mainDimInpParList);

        dimensioner.calculateResults();
        printResults(dimensioner, n, b, s);

        // Change parameter and recalculate.
        //dimensioner.changeParameter("LR_NODE", "ADMIN", "RC112", "2000");
        //dimensioner.changeParameter("LR_NODE", "ADMIN", "RC112_ES112", "4000");
        //myLogger.log(LogLevel.MAIN, "Changed parameters, performing run 2... ");
        //dimensioner.reCalculate();
        //printResults(dimensioner, n, b, s);

    }

    void printResults(HdtDimensioner dimensioner, Network n, Bundle b, HdtSystem s) {
        MultiKeyMap varMap = dimensioner.getVariableMap();
        Map hwConfigMap = dimensioner.getHwConfigMap();
        Map appNumMap = dimensioner.getAppNumbersMap();
        Map<String, Formula> formulaMap = dimensioner.getFormulaMap();

        String sysName = s.getName();
        List<Role> roles = dimensioner.getAllRoles();
        myLogger.log(LogLevel.SUB1, "Role(s) for System(s): " + roles);


        for (Role role : roles) {
            List<Parameter> parameters = dimensioner.getApplicableCalculationParameters(role.getName());
            List<Variable> variables = (List<Variable>) varMap.get(role.getName(), n.getName(), sysName);

            // Dump all parameters.
            myLogger.log(LogLevel.SUB2, "Parameters for role = " + role.getName() + ", network = "
                    + n.getName() + ", system = " + sysName + ": " + parameters);
            for (final Parameter par : parameters) {
                myLogger.log(LogLevel.SUB3, "Dumping PARAMETER " + par.getName() + " - " + par.getDescription());
                myLogger.executeIf(LogLevel.SUB3, new Executor() {

                    @Override
                    public void exec() {
                        par.dumpValues();
                    }
                });
            }

            // Dump all variables.
            myLogger.log(LogLevel.SUB3, "Variables for role = " + role.getName() + ", network = "
                    + n.getName() + ", system = " + sysName + ": " + variables);
            for (final Variable var : variables) {
                myLogger.log(LogLevel.SUB3, "Dumping VARIABLE " + var.getName() + " - " + var.getDescription());
                myLogger.executeIf(LogLevel.SUB3, new Executor() {

                    @Override
                    public void exec() {
                        var.dumpValues();
                    }
                });

                Formula fml = formulaMap.get(var.getFormulaName());
                if (fml != null) {
                    myLogger.log(LogLevel.SUB4, "FORMULA for Variable " + var.getName() + ", JS code:\n" + fml.getFomulaJSCode());
                }
            }

            // Dump HW Config Names.
            List<HardwareConfig> configList = (List<HardwareConfig>) hwConfigMap.get(role.getName());
            myLogger.log(LogLevel.SUB3, "Hardware Configuration name for role = " + role.getName() + ":");
            for (final HardwareConfig cfg : configList) {
                myLogger.executeIf(LogLevel.SUB3, new Executor() {

                    @Override
                    public void exec() {
                        cfg.dumpValues();
                    }
                });

            }

            myLogger.log(LogLevel.SUB2, "APP Numbers for role = " + role.getName() + ":");
            List<List<APPNumber>> appNumListAlternatives = (List<List<APPNumber>>) appNumMap.get(role.getName());
            Integer count = 1;
            for (List<APPNumber> appNumList : appNumListAlternatives) {
                myLogger.log(LogLevel.SUB3, "Alternative: " + count);
                count++;
                for (APPNumber num : appNumList) {
                    myLogger.log(LogLevel.SUB3, num.getName());
                }
            }
        }
    }
}

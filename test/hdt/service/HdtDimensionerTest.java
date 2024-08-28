/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
import hdt.domain.APPAlternative;
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
public class HdtDimensionerTest extends BaseHdtTest {

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

    public HdtDimensionerTest() {
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
        //HdtSystem s = new HdtSystem("EE112", "Eniq Events 11.2");
        //HdtSystem s = new HdtSystem("ES112", "Eniq Statistics 11.2");
        //HdtSystem s = new HdtSystem("RC112_ES112", "OSS RC 11.2 + ENIQS 11.2");
        Network n = new Network("LR_CN", "Lte + Core");
        //Network n = new Network("Packet Core Only", "Packet Core Only");
        //Network n = new Network("CN", "Core");
        //Bundle b = new Bundle("<7.5 million subscribers", "<7.5 million subscribers");
        Bundle b = new Bundle("MEDIUM", "Medium Bundle");
        MultiKeyMap parMap = null;
        MultiKeyMap varMap = null;
        Map hwConfigMap = null;
        Map appNumMap = null;

        // Insert the test data into the inputs.
        inputs.setSystem(s);
        inputs.setNetwork(n);
        inputs.setBundle(b);

        System.out.println("INPUTS: System = " + s.getName() + ", Network = " + n.getName() + ", Bundle = " + b.getName());

        HdtDimensioner dimensioner = new HdtDimensionerImpl(daoContainer);
        dimensioner.configureHdtDimensioner(inputs);

        Map<String, Formula> formulaMap = dimensioner.getFormulaMap();

        dimensioner.prepareDimensioning();

        List<Parameter> mainDimInpParList = dimensioner.getAllMainParameters();
        System.out.println("Main Dimensioning Parameters: " + mainDimInpParList);

        dimensioner.calculateResults();

        // Set the result variable objects.

        varMap = dimensioner.getVariableMap();
        hwConfigMap = dimensioner.getHwConfigMap();
        appNumMap = dimensioner.getAppNumbersMap();

        String sysName = s.getName();
        List<Role> roles = dimensioner.getAllRoles();
        System.out.println("Role(s) for System(s): " + roles);

        for (Role role : roles) {
            List<Parameter> parameters = dimensioner.getApplicableCalculationParameters(role.getName());
            List<Variable> variables = (List<Variable>) varMap.get(role.getName(), n.getName(), sysName);

            // Dump all parameters.
            System.out.println("Parameters for role = " + role.getName() + ", network = "
                    + n.getName() + ", system = " + sysName + ": " + parameters);
            for (Parameter par : parameters) {
                System.out.println("Dumping PARAMETER " + par.getName() + " - " + par.getDescription());
                par.dumpValues();
            }


            // Dump all variables.
            System.out.println("Variables for role = " + role.getName() + ", network = "
                    + n.getName() + ", system = " + sysName + ": " + variables);
            for (Variable var : variables) {
                System.out.println("Dumping VARIABLE " + var.getName() + " - " + var.getDescription());
                var.dumpValues();
                Formula fml = formulaMap.get(var.getFormulaName());
                if (fml != null) {
                    System.out.println("FORMULA for Variable " + var.getName() + ", JS code:\n" + fml.getFomulaJSCode());
                }
            }

            // Dump HW Config Names.
            List<HardwareConfig> configList = (List<HardwareConfig>) hwConfigMap.get(role.getName());
            System.out.println("Hardware Configuration name for role = " + role.getName() + ":");
            if (configList != null) {
                for (HardwareConfig cfg : configList) {
                    if (cfg != null) {
                        cfg.dumpValues();
                    } else {
                        System.err.println("ERROR: Empty HW config.");
                    }
                }
            } else {
                System.err.println("ERROR: Empty HW config list");
            }

            System.out.println("APP Numbers for role = " + role.getName() + ":");
            
            List<APPAlternative> appNumListAlternatives = (List<APPAlternative>) appNumMap.get(role.getName());            
            if (appNumListAlternatives != null) {
            for (APPAlternative appAlt : appNumListAlternatives) {
                System.out.println("Alternative revision: " + appAlt.getHwRevision());
                System.out.println("Alternative status: " + appAlt.getHwStatus());                
                for (APPNumber num : appAlt.getAlternative()) {
                    System.out.println(num.getName());
                }
            }
            } else {
                System.err.println("ERROR: Empty Alternatives list");
            }
        }
    }
}

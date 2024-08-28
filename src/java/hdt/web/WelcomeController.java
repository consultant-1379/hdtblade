/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.web;

import hdt.dao.APPNumberDao;
import hdt.dao.BundleDao;
import hdt.dao.DaoContainer;
import hdt.dao.FormulaDao;
import hdt.dao.HardwareConfigDao;
import hdt.dao.HdtAppInfo;
import hdt.dao.HdtDbConfigDao;
import hdt.dao.NetworkDao;
import hdt.dao.ParameterDao;
import hdt.dao.HdtSystemDao;
import hdt.dao.QuickLinkDao;
import hdt.dao.RoleDao;
import hdt.dao.VariableDao;
import hdt.domain.Bundle;
import hdt.domain.Network;
import hdt.domain.Parameter;
import hdt.domain.HdtSystem;
import hdt.domain.QuickLink;
import hdt.domain.forms.WelcomeForm;
import hdt.domain.modelcache.WelcomeModel;
import hdt.dto.InitialInputs;
import hdt.dto.ParameterDataJSON;
import hdt.service.HdtDimensioner;
import hdt.service.HdtDimensionerImpl;
import hdt.service.editors.SystemEditor;
import javax.servlet.ServletException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * The main welcome view controller class.
 *
 * @author Ralph Schlosser
 * @version 0.1
 * <s>FIXME: The Version should come from CVS.</s>
 * <s>date 21/10/2010</s>
 */
@Controller
@RequestMapping(value = "welcome")
@SessionAttributes({"welcomeModelCache"})
public class WelcomeController {

    protected final Log logger = LogFactory.getLog(getClass());
    // The data access objects are injected by Spring.
    @Autowired
    private HdtDbConfigDao dbConfigDao;
    @Autowired
    private QuickLinkDao qLDao;
    @Autowired
    private BundleDao bundleDao;
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
    @Autowired
    private HdtAppInfo appInfoDao;
    private DaoContainer daoContainer;
    // This is the storage container for the initial results.
    private InitialInputs initialInputs;

    @RequestMapping(method = RequestMethod.GET)
    public String showView(Model model, @ModelAttribute("quickLinks") List<QuickLink> quickLinks) throws ServletException {
        logger.info("[GET] Now in " + this.toString());

        this.initialInputs = new InitialInputs();
        initDaoContainer();
        HdtDimensioner dimensioner = new HdtDimensionerImpl(daoContainer);

        // In this model cache we keep all model related info, not only the model itself but also
        // service objects etc. This way we can easily carry "stuff" around between controllers.
        WelcomeModel wc = populateModel(model, new WelcomeForm(), dimensioner, quickLinks);
        model.addAttribute("welcomeModelCache", wc);

        return "welcome";
    }

    private WelcomeModel populateModel(Model model, WelcomeForm welcomeForm, HdtDimensioner dimensioner, List<QuickLink> quickLinks) {
        WelcomeModel wc = new WelcomeModel();
        Calendar cal = Calendar.getInstance();

        wc.put("welcomeForm", welcomeForm);
        wc.put("dimensioner", dimensioner);
        wc.put("quickLinks", quickLinks);

        wc.put("hdt_ver", appInfoDao.getHdtVersion());
        wc.put("hdt_buildnum", appInfoDao.getHdtBuildNum());
        wc.put("hdt_db_ver", dbConfigDao.getDbVersion());
        wc.put("date_str", cal.getTime().toString());

        model.addAllAttributes(wc.getCachedModel());

        return wc;
    }

    // AJAX handler.
    @RequestMapping(value = "/get_networks", method = RequestMethod.GET)
    public @ResponseBody
    List<Network> getNetworks(@RequestParam String systemId, @ModelAttribute("welcomeModelCache") WelcomeModel wc) {
        logger.info("[GET] Now in " + this.toString() + ", Parameters: systemId = " + systemId);
        List<Network> applicableNetworks = netDao.findNetworksBySystemId(systemId);

        return applicableNetworks;
    }

    // AJAX handler.
    @RequestMapping(value = "/get_main_parameters", method = RequestMethod.GET)
    public @ResponseBody
    ParameterDataJSON getParameters(@RequestParam String systemId, @RequestParam String networkId,
            @ModelAttribute("welcomeModelCache") WelcomeModel wc) {
        logger.info("[GET] Now in " + this.toString() + ", Parameters: systemId = " + systemId + ", networkId = "
                + networkId);
        ParameterDataJSON retVal = new ParameterDataJSON();
        HdtDimensioner dimensioner = (HdtDimensioner) wc.getCachedModel().get("dimensioner");
              
        HdtSystem s = sysDao.findById(systemId);
        Network n = netDao.findById(networkId);

        // Retrieve all bundles.
        List<Bundle> bundleList = bundleDao.findAll();
        Map<String, List<Parameter>> mainDimParMap = new HashMap<String, List<Parameter>>();
        for (Bundle b : bundleList) {
            setInitialInputs(s, n, b);
            dimensioner.configureHdtDimensioner(initialInputs);
            List<Parameter> mainDimParList = dimensioner.getAllMainParameters();
            if (!mainDimParList.isEmpty()) {
                mainDimParMap.put(b.getName(), mainDimParList);
            }
        }

        retVal.mainDimParMap = mainDimParMap;
        return retVal;
    }

    // Further initialise the Dimensioner.
    @RequestMapping(value = "/set_main_parameters", method = RequestMethod.POST)
    public void postChangedParametersInfo(@ModelAttribute("welcomeModelCache") WelcomeModel wc,
            @RequestBody Map<String, ? extends Object> mainParameters,
            HttpSession session, HttpServletResponse response) throws ScriptException {
        boolean success = true;
        
        // This is the half initialised Dimensioner.
        HdtDimensioner dimensioner = (HdtDimensioner) wc.getCachedModel().get("dimensioner");
        if (dimensioner != null) {
            // Determine what bundle has been selected and configure the dimensioner accordingly.
            String bundleId = (String) mainParameters.remove("bundleId");
            Bundle b = bundleDao.findById(bundleId);
            InitialInputs inputs = dimensioner.getInitialInputs();                                    
            if (inputs != null) {
                inputs.setBundle(b);                
                dimensioner.configureHdtDimensioner(inputs);
                
                // Now process the changed main parameters, if there are any.
                for (Map.Entry<String, ? extends Object> entry : mainParameters.entrySet()) {
                    String parName = entry.getKey();
                    Map<String, ? extends Object> parObject = (Map<String, ? extends Object>)entry.getValue();
                    if (parObject != null) {
                        // Apply the changed parameter.
                        String ctx = (String)parObject.get("roleContext");
                        dimensioner.changeParameter(true, parName, ctx, parObject.get("parameterValue"));
                    } else {
                        logger.error("Empty parameter object, cannot change parameter: " + parName);                    
                        success = false;
                        break;
                    }
                }                
            } else {
                logger.error("Cannot retrieve initial inputs from dimensioner.");            
                success = false;
            }
        } else {
            logger.error("Dimensioner object not found in session...session expiry?");            
            success = false;
        }
        
        // Set the response status code accordingly.
        if (success == true) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processForm(@ModelAttribute("postedWelcomeForm") WelcomeForm postedWelcomeForm,
            @ModelAttribute("welcomeModelCache") WelcomeModel wc, HttpSession session) {
        logger.info("[POST] Now in " + this.toString());

        String postedBundleId = postedWelcomeForm.getBundleId();
        String postedNetworkId = postedWelcomeForm.getNetworkId();

        // FIXME: Extremely simplistic error checking.
        if (postedWelcomeForm.getSystemId() == null || postedNetworkId == null || postedBundleId == null) {
            logger.error("Not posting empty form - going home.");
            return "redirect:welcome.htm";
        }

        // Put objects in the cache which we want to transfer over to the next controller.        
        wc.put("customerName", postedWelcomeForm.getCustomerName());
        wc.put("confName", postedWelcomeForm.getConfName());

        session.setAttribute("welcomeModelCache", wc);
        return "redirect:dimensioning.htm";
    }

    private void initDaoContainer() {
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

    // No error checking here as it must be done at another level.
    private void setInitialInputs(HdtSystem system, Network network, Bundle bundle) {
        this.initialInputs.setBundle(bundle);
        this.initialInputs.setNetwork(network);
        this.initialInputs.setSystem(system);
    }

    // Initialise a new binder class for use with <form:select>.
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(hdt.domain.HdtSystem.class, "systemId", new SystemEditor(sysDao));
    }

    // Reference data.
    @ModelAttribute("systemId")
    public List<HdtSystem> populateSystems() {
        return sysDao.findAll();
    }

    @ModelAttribute("quickLinks")
    public List<QuickLink> populateQuickLinksList() {
        return qLDao.findAll();
    }

    // Note: This gets invoked if the session has expired.
    // This usually gets invoked when a @SessionAttribute has become invalid and is accessed
    // through @ModelAttribute. Unfortunately, as of now, there is no
    // conditional @SessionAttribute. (https://jira.springsource.org/browse/SPR-4452)
    @ExceptionHandler(HttpSessionRequiredException.class)
    //@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleSessionRequiredException() {
        // Just log the event and PASS the exception. May require handling of this in the methods themselves.
        logger.debug("Received HttpSessionRequiredException.");
        return "redirect:welcome.htm";
    }
}

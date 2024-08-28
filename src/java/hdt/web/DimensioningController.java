/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.web;

import hdt.domain.APPAlternative;
import hdt.domain.Alternatives;
import hdt.domain.Parameter;
import hdt.domain.Role;
import hdt.domain.modelcache.DimensioningModel;
import hdt.domain.modelcache.WelcomeModel;
import hdt.dto.InitialInputs;
import hdt.service.HdtDimensioner;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.script.ScriptException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 *
 * @author escralp
 */
@Controller
@RequestMapping(value = "dimensioning")
@SessionAttributes({"dimensioner", "dimensioningModelCache"})
public class DimensioningController {

    protected final Log logger = LogFactory.getLog(getClass());

    @RequestMapping(method = RequestMethod.GET)
    public String showView(Model model, HttpSession session) throws ServletException, ScriptException {
        logger.info("[GET] Now in " + this.toString());

        // Retrieve Welcome form object from the session.        
        WelcomeModel wc = (WelcomeModel) session.getAttribute("welcomeModelCache");

        // FIXME: This is a very crude error handling mechanism.
        if (wc == null) {
            // Assume it is a session expiry; redirect to first page.
            logger.error("Could not retrieve required information from session, assuming session expiry - going home.");
            return "redirect:welcome.htm";
        }

        // Retrieve previously created dimensioner from session, initialise and calculate dimensioning results.
        // FIXME: Excepion handling.
        HdtDimensioner myDimensioner = (HdtDimensioner) wc.getCachedModel().get("dimensioner");
        myDimensioner.prepareDimensioning();
        myDimensioner.calculateResults();

        // Fill model with results needed for rendering.
        DimensioningModel dc = populateDimensioningModel(model, wc, myDimensioner);

        // Add the model cache to the model so we can treat it as a session attribute.
        model.addAttribute("dimensioningModelCache", dc);

        return "dimensioning";
    }

    private DimensioningModel populateDimensioningModel(Model model, WelcomeModel wc, HdtDimensioner dimensioner) {
        DimensioningModel dimModelCache = new DimensioningModel();

        Map<String, Object> m = (Map<String, Object>) wc.getCachedModel();
        InitialInputs inputs = dimensioner.getInitialInputs();

        dimModelCache.put("quickLinks", m.get("quickLinks"));
        dimModelCache.put("hdt_ver", m.get("hdt_ver"));
        dimModelCache.put("hdt_buildnum", m.get("hdt_buildnum"));
        dimModelCache.put("hdt_db_ver", m.get("hdt_db_ver"));
        dimModelCache.put("date_str", m.get("date_str"));
        dimModelCache.put("customerName", m.get("customerName"));
        dimModelCache.put("confName", m.get("confName"));
        dimModelCache.put("bundleDesc", inputs.getBundle().getDescription());
        dimModelCache.put("networkDesc", inputs.getNetwork().getDescription());
        dimModelCache.put("systemDesc", inputs.getSystem().getDescription());
        dimModelCache.put("dimensioner", dimensioner);
        dimModelCache.put("roleList", dimensioner.getAllRoles());          
        dimModelCache.put("alternativesMap", dimensioner.getAlternativesMap());
        
        // Merge all aatributes from the cached model with the model datastructure for rendering.
        model.addAllAttributes(dimModelCache.getCachedModel());

        return dimModelCache;
    }

    @RequestMapping(value = "/get_rendering_data", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Object> getRenderingData(@ModelAttribute("dimensioner") HdtDimensioner dimensioner) {
        Map<String, Object> renderData = new HashMap<String, Object>();

        renderData.put("roleList", dimensioner.getAllRoles());
        renderData.put("alternativesMap", dimensioner.getAlternativesMap());         
        renderData.put("hasAPPErrorsMap", dimensioner.getAPPErrorsMap());
        renderData.put("errorList", dimensioner.getErrors());
        renderData.put("noParChanges", dimensioner.getParametersHaveChanged());
        renderData.put("noAPPChanges", dimensioner.getAppsHaveChanged());
        return renderData;
    }
    
    //
    // FIXME: Here only the RELEVANT changeable params should be retrieved!
    // Policy must be enforced.
    //
    @RequestMapping(value = "/change_parameters", method = RequestMethod.GET)
    String getChangeParametersInfo(Model model, @ModelAttribute("dimensioner") HdtDimensioner dimensioner,
            @RequestParam String roleName) {
        // FIXME: Potential session expiry.        
        List<Parameter> parList = dimensioner.getApplicableViewParameters(roleName);
        
        // FIXME: Check returned list!! Set error codes.
        List<Role> allRoles = dimensioner.getAllRoles();
        for (Role role : allRoles) {
            if (role.getName().equals(roleName)) {
                model.addAttribute("currentRole", role);
                model.addAttribute("parameterList", parList);
                break;
            } else {
                // FIXME: Need some error handling. Put error messages in the form?
            }
        }

        return "change_parameters_form";
    }
    
    
    // 
    // FIXME: The data structure used may not be the best.
    //
    @RequestMapping(value = "/change_parameters", method = RequestMethod.POST)
    String postChangeParametersInfo(@ModelAttribute("dimensioner") HdtDimensioner dimensioner,
            @RequestBody Map<String, String> changeRequest, HttpServletResponse response) throws ScriptException {
        if (changeRequest != null) {
            // The role and system name are the first two entries, we'll extract them.
            String roleName = (String) changeRequest.remove("roleName");            

            if (roleName != null) {
                // Iterate over the map of parameters that we wish to change.
                for (Map.Entry<String, ? extends Object> entry : changeRequest.entrySet()) {
                    String parName = entry.getKey();
                    Map<String, String> parValue = (Map<String, String>) entry.getValue();

                    // FIXME: Should check a return code here --> Figure out if there was an error.
                    dimensioner.changeParameter(false, parName, roleName, parValue.get("parameterValue"));
                }   

                // Calculate results after all changes have been applied.
                dimensioner.reCalculate();
            } else {
                // This causes the error handler to be invoked.
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }

        return "dimensioning";
    }
    
    @RequestMapping(value = "/change_quantities", method = RequestMethod.POST)
    public String postChangeQuantitiesInfo(@ModelAttribute("dimensioner") HdtDimensioner dimensioner,
            @RequestBody Map<String, List<Map<String, String>>> changedQuantities,
            HttpServletResponse response) throws ScriptException {
        if (changedQuantities != null) {
            // Iterate over all roles.
            for (Map.Entry<String, List<Map<String, String>>> entry : changedQuantities.entrySet()) {
                String roleName = entry.getKey();
                List<Map<String, String>> appList = entry.getValue();

                // Apply the changed quantities.
                if (roleName != null && appList != null && !appList.isEmpty()) {
                    dimensioner.changeQuantities(roleName, appList);
                }

                // Need to recalculate as changed quantities may affect other roles' results.
                dimensioner.reCalculate();
            }
        } else {
            // This causes the error handler to be invoked.
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        return "dimensioning";
    }
    
    @RequestMapping(value = "/get_hw_alternatives", method = RequestMethod.GET)
    String getChangeHwInfo(Model model, @ModelAttribute("dimensioner") HdtDimensioner dimensioner,
            @RequestParam String roleName) {
        // FIXME: Potential session expiry.              
        Map<String, Alternatives> altMap = dimensioner.getAlternativesMap();
        List<Role> allRoles = dimensioner.getAllRoles();
        for (Role role : allRoles) {
            if (role.getName().equals(roleName)) {
                Alternatives alt = altMap.get(role.getName());
                if (alt != null) {
                    List<APPAlternative> altList = alt.getAlternatives();
                    int selectedIdx = alt.getSelectedAlternativeIdx();
                
                    model.addAttribute("currentRole", role);
                    model.addAttribute("appAlternativesList", altList); 
                    model.addAttribute("selectedIdx", selectedIdx);
                }
                break;
            } else {
                // FIXME: Need some error handling. Put error messages in the form?
            }
        }        
             
        return "change_hw_alternatives_form";
    }
    
    @RequestMapping(value = "/submit_changed_alternative", method = RequestMethod.GET)
    public String submitChangedAlternativeInfo(@ModelAttribute("dimensioner") HdtDimensioner dimensioner,
                                               @RequestParam String roleName, 
                                               @RequestParam String selectedId, HttpServletResponse response) throws ScriptException {
        if(roleName != null && selectedId != null) {            
            dimensioner.changeHwAlternative(roleName, selectedId);
            
            // Need to recalculate as changed alternative may affect other roles' results.
            dimensioner.reCalculate();

        } else {
            // This causes the error handler to be invoked.
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        return "dimensioning";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processForm(@ModelAttribute("dimensioningModelCache") DimensioningModel dimModelCache,
            @ModelAttribute("dimensioner") HdtDimensioner dimensioner,
            HttpSession session) throws ScriptException {
        logger.info("[POST] Now in " + this.toString());

        dimModelCache.put("changedMainParameters", dimensioner.getChangedMainParameters());
        dimModelCache.put("changedSecondaryParameters", dimensioner.getChangedSecondaryParameters());
        dimModelCache.put("changedAPPQuantities", dimensioner.getChangedAPPQuantities());
        dimModelCache.put("noParChanges", dimensioner.getParametersHaveChanged());
        dimModelCache.put("noAPPChanges", dimensioner.getAppsHaveChanged());

        // Save form and results in the session.
        session.setAttribute("dimensioningModelCache", dimModelCache);

        return "redirect:report.htm";
    }
}

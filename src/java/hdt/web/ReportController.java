/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.web;

import hdt.dao.NoteDao;
import hdt.domain.Note;
import hdt.domain.forms.ReportForm;
import hdt.domain.modelcache.DimensioningModel;
import hdt.service.HdtDimensioner;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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
@SessionAttributes({"dimensioner"})
public class ReportController {

    protected final Log logger = LogFactory.getLog(getClass());
    @Autowired
    private NoteDao noteDao;

    @RequestMapping(value = "report", method = RequestMethod.GET)
    public String showView(Model model, HttpSession session, @RequestParam(required = false, value = "view") String view) {
        logger.info("[GET] Now in " + this.toString());

        // Redirect to Excel handler so as to get the correct file ending.
        if (view != null && view.equals("excel") ) {
            return "redirect:report/hdt_report.xls";
        }
        
        if (populateReportModel(model, session) != true) {
            // Assume it is a session expiry; redirect to first page.
            logger.error("Could not retrieve required information from session, assuming session expiry - going home.");
            return "redirect:welcome.htm";
        }
        
        // Return the print preview, if requested.
        // DISABLED PRINT PREVIEW. RS12082011
        /*if ( view != null  && view.equals("print_preview")) {
            return "report_print_preview";
        }*/
        
        // Default: Return JSP view.
        return "report";
    }

    @RequestMapping(value = "report/hdt_report.xls", method = RequestMethod.GET)
    public String getExcelReport(Model model, HttpSession session) {
        logger.info("[GET] Now in " + this.toString());

        if (populateReportModel(model, session) != true) {
            // Assume it is a session expiry; redirect to first page.
            logger.error("Could not retrieve required information from session, assuming session expiry - going home.");
            return "redirect:welcome.htm";
        }

        return "report_excel";
    }
    
    @RequestMapping(value = "/get_rendering_data", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Object> getRenderingData(@ModelAttribute("dimensioner") HdtDimensioner dimensioner) {
        Map<String, Object> renderData = new HashMap<String, Object>();

        renderData.put("roleList", dimensioner.getAllRoles());
        renderData.put("alternativesMap", dimensioner.getAlternativesMap());
        renderData.put("hasAPPErrorsMap", dimensioner.getAPPErrorsMap());
        //renderData.put("noParChanges", dimensioner.getParametersHaveChanged());
        //renderData.put("noAPPChanges", dimensioner.getAppsHaveChanged());
        return renderData;
    }
    
    private Boolean populateReportModel(Model model, HttpSession session) {
        // Retrieve welcome form object from the session.
        DimensioningModel dimModel = (DimensioningModel) session.getAttribute("dimensioningModelCache");
        // FIXME: This is a very crude error handling mechanism.
        if (dimModel == null) {
            return false;
        }
       
        // Add the notes.
        List<Note> noteList = noteDao.getNotes();
        model.addAttribute("noteList", noteList);

        // Merge with Spring model.
        model.addAllAttributes(dimModel.getCachedModel());
        model.addAttribute(new ReportForm());        
                
        return true;
    }
}

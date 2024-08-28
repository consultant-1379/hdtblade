/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.service.editors;

import hdt.dao.GenericDao;
import hdt.domain.HdtItem;
import java.beans.PropertyEditorSupport;

/**
 *
 * @author escralp
 */
public class HdtItemEditor extends PropertyEditorSupport {

    private final GenericDao dao;

    public HdtItemEditor(GenericDao dao) {
        this.dao = dao;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        HdtItem item = (HdtItem) dao.findById(text);
        setValue(item);
    }

    /**
     * This is called upon initialising the list. It returns the 
     * name (String) which can be used by the form element.
     * @return
     */
    @Override
    public String getAsText() {
        HdtItem item = (HdtItem) getValue();
        if (item == null) {
            return null;
        } else {
            return item.getName();
        }
    }
}

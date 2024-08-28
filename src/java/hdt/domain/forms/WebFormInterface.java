/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.domain.forms;

import hdt.domain.modelcache.GenericModelCache;
import java.io.Serializable;
import org.springframework.ui.Model;

/**
 *
 * @author escralp
 */
public interface WebFormInterface extends Serializable {

    public void populateGenericModelWithFormData(GenericModelCache model);

    public void populateSpringModelWithFormData(Model model);
}

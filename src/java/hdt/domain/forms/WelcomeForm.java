/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.domain.forms;

import hdt.domain.HdtSystem;
import hdt.domain.Network;
import hdt.domain.modelcache.GenericModelCache;
import java.util.List;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.ui.Model;

/**
 *
 * @author escralp
 */
public class WelcomeForm extends WebForm {

    @NotEmpty
    @Size(min = 1, max = 50)
    private String customerName;
    @NotEmpty
    @Size(min = 1, max = 20)
    private String confName;    
    @NotEmpty
    private List<HdtSystem> systemId;
    @NotEmpty
    private String networkId;
    @NotEmpty
    private String bundleId;    


    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    public List<HdtSystem> getSystemId() {
        return systemId;
    }

    public void setSystemId(List<HdtSystem> systemId) {
        this.systemId = systemId;
    }
   
    public String getConfName() {
        return confName;
    }

    public void setConfName(String confName) {
        this.confName = confName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }
   

    @Override
    public void populateGenericModelWithFormData(GenericModelCache model) {
        model.put("customer_name", customerName);
        model.put("conf_name", confName);       
        model.put("system", systemId.get(0));
        model.put("network", networkId);
        model.put("bundle_id", bundleId);
    }

    @Override
    public void populateSpringModelWithFormData(Model model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

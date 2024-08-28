/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.domain;

import hdt.service.HdtConstants;
import java.io.Serializable;

/**
 * FIXME: Needs to have an interface --> refactor
 * @author escralp
 */
public class Context implements Serializable {

    private String roleContext;
    private String networkContext;
    private String systemContext;
    private String bundleContext;

    public String getBundleContext() {
        return bundleContext;
    }

    public void setBundleContext(String bundleContext) {
        this.bundleContext = bundleContext;
    }

    public String getNetworkContext() {
        return networkContext;
    }

    public void setNetworkContext(String networkContext) {
        this.networkContext = networkContext;
    }

    public String getRoleContext() {
        return roleContext;
    }

    public void setRoleContext(String roleContext) {
        this.roleContext = roleContext;
    }

    public String getSystemContext() {
        return systemContext;
    }

    public void setSystemContext(String systemContext) {
        this.systemContext = systemContext;
    }
    
    //
    // Returns true if 'this' has narrower context than 'ctx'.
    //
    public boolean hasNarrowerRoleContext(Context ctx) {
        if (ctx != null) {
            if ((ctx.getRoleContext().equals(HdtConstants.HDT_ANY_ROLE_CTX) == true) &&
                (this.roleContext.equals(HdtConstants.HDT_ANY_ROLE_CTX) == false)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Only for debugging purposes.
     */
    public void dumpValues() {
        System.out.println("Role Context: " + this.roleContext);
        System.out.println("Network Context: " + this.networkContext);
        System.out.println("System Context: " + this.systemContext);
        System.out.println("Bundle Context: " + this.bundleContext);
    }
}

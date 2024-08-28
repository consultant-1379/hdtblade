/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.dto;

/**
 * Used to generate JSON output.
 * 
 * @author escralp
 */
public class InputContext {

    private String bundleName;
    private String roleName;
    private String networkName;
    private String systemName;
    
    public String getBundleName() {
        return bundleName;
    }

    public void setBundleName(String bundleName) {
        this.bundleName = bundleName;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hdt.domain;

/**
 *
 * @author escralp
 */
public class APPDependency {

    private String roleName;
    private String appName;

    public APPDependency(String role, String app) {
        this.roleName = role;
        this.appName = app;
    }

    public String getAppName() {
        return appName;
    }

    public String getRoleName() {
        return roleName;
    }


}

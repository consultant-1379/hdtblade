/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.domain;

/**
 *
 * @author escralp
 */
public class Role extends HdtItem {

    private String hardwareConfigTableName;
    private String applicableSystemName;
    // FIXME: This property could actually be pushed up into HdtItem as similar properties are used
    // in APPNumber, Parameter etc.
    private boolean userModified;

    public Role(String name, String description, String sysName) {
        super(name, description);
        this.applicableSystemName = sysName;
    }

    public String getHardwareConfigTableName() {
        return hardwareConfigTableName;
    }

    public void setHardwareConfigTableName(String hardwareConfigTableName) {
        this.hardwareConfigTableName = hardwareConfigTableName;
    }

    public String getApplicableSystemName() {
        return applicableSystemName;
    }
    
    public boolean isUserModified() {
        return userModified;
    }

    public void setUserModified(boolean userModified) {
        this.userModified = userModified;
    }
}

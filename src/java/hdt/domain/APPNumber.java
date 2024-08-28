/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.domain;

/**
 *
 * @author escralp
 */
public class APPNumber extends HdtItem {

    private String lastOrderDate;
    private String endOfServiceLife;
    private int quantity;
    private int userQuantity;       
    boolean userQtySet = false;
    boolean exposedToJS = false;

    public APPNumber(String appNumber, String description) {
        super(appNumber, description);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUserQuantity() {
        return userQuantity;
    }

    public void setUserQuantity(int userQuantity) {
        this.userQuantity = userQuantity;
        this.userQtySet = true;
    }

    public String getEndOfServiceLife() {
        return endOfServiceLife;
    }

    public void setEndOfServiceLife(String endOfServiceLife) {
        this.endOfServiceLife = endOfServiceLife;
    }

    public String getLastOrderDate() {
        return lastOrderDate;
    }

    public void setLastOrderDate(String lastOrderDate) {
        this.lastOrderDate = lastOrderDate;
    }

    public boolean isUserQtySet() {
        return userQtySet;
    }

    public boolean isExposedToJS() {
        return exposedToJS;
    }

    public void setExposedToJS(boolean exposedToJS) {
        this.exposedToJS = exposedToJS;
    }   
}

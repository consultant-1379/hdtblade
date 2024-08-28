/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.domain;

/**
 *
 * @author escralp
 */
public class Formula extends HdtItem {

    private String fomulaJSCode;

    public Formula(String name, String description) {
        super(name, description);
    }

    public String getFomulaJSCode() {
        return fomulaJSCode;
    }

    public void setFomulaJSCode(String fomulaJSCode) {
        this.fomulaJSCode = fomulaJSCode;
    }
}

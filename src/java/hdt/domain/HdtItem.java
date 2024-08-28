/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.domain;

import java.io.Serializable;

/**
 *
 * @author escralp
 */
public abstract class HdtItem extends NamedType implements Serializable {

    private static final long serialVersionUID = 1L;
    private String description;

    public HdtItem(String name) {
        super(name);
        this.description = "undefined";
    }

    public HdtItem(String name, String description) {
        super(name);
        this.description = description;
    }

    // Used for object copying.
    public HdtItem(HdtItem template) {
        super(template);
        this.description = template.description;
    }

    public String getDescription() {
        return description;
    }
}

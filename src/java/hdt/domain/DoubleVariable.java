/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.domain;

import hdt.service.HdtConstants;

/**
 *
 * @author escralp
 */
public class DoubleVariable extends Variable<Double> {

    public DoubleVariable(String name, String description) {
        super(name, description, HdtConstants.HDT_TYPE_DOUBLE);
    }
}

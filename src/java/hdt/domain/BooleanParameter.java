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
public class BooleanParameter extends Parameter<Boolean> {

    public BooleanParameter(String name, String description) {
        super(name, description, HdtConstants.HDT_TYPE_BOOLEAN);
    }

    @Override
    public String getValueAsString() {
        if (super.getValue() != null) {
            Boolean val = super.getValue();
            return val.toString();
        } else {
            return null;
        }
    }

    @Override
    public String getDefaultValueAsString() {
        if (super.getDefaultValue() != null) {
            Boolean val = super.getDefaultValue();
            return val.toString();
        } else {
            return null;
        }
    }

    @Override
    public ParameterChangeResult changeValue(Object newValue) {
        ParameterChangeResult retVal = ParameterChangeResult.CHANGE_FAIL;
        boolean val = Boolean.parseBoolean(newValue.toString());

        if (super.getValue() == null) {
            // Value hasn't been set previously.
            if (super.getDefaultValue() == val) {
                retVal = ParameterChangeResult.CHANGE_NONE;
            } else {
                retVal = ParameterChangeResult.CHANGE_SUCCESS;
                super.setValue(val);
            }
        } else {
            // Only set the value if it's different from before.
            if (val == super.getValue()) {
                retVal = ParameterChangeResult.CHANGE_NONE;
            } else {
                retVal = ParameterChangeResult.CHANGE_SUCCESS;
                super.setValue(val);
            }
        }


        return retVal;
    }
}

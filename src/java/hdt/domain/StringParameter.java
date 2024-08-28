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
public class StringParameter extends Parameter<String> {
    
    public StringParameter(String name, String description) {
        super(name, description, HdtConstants.HDT_TYPE_STRING);
    }

    @Override
    public String getValueAsString() {
        return super.getValue();
    }

    @Override
    public String getDefaultValueAsString() {
        return super.getDefaultValue();
    }
    
    @Override
    public ParameterChangeResult changeValue(Object newValue) {
        ParameterChangeResult retVal = ParameterChangeResult.CHANGE_FAIL;
        
        if (super.getValue() == null) {
            // Value hasn't been set previously.
            if (super.getDefaultValue().equals(newValue)) {
                retVal = ParameterChangeResult.CHANGE_NONE;
            } else {
                retVal = ParameterChangeResult.CHANGE_SUCCESS;
                super.setValue(newValue.toString());
            }
        } else {
            // Only set the value if it's different from before.
            if (newValue.equals(super.getValue())) {
                retVal = ParameterChangeResult.CHANGE_NONE;
            } else {
                retVal = ParameterChangeResult.CHANGE_SUCCESS;
                super.setValue(newValue.toString());
            }
        }


        return retVal;
    }
}

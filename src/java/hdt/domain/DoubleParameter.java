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
public class DoubleParameter extends Parameter<Double> {

    public DoubleParameter(String name, String description) {
        super(name, description, HdtConstants.HDT_TYPE_DOUBLE);
    }

    @Override
    public String getDefaultValueAsString() {
        if (super.getDefaultValue() != null) {
            Double val = super.getDefaultValue();
            return Double.toString(val);
        } else {
            return null;
        }
    }

    @Override
    public String getValueAsString() {
        if (super.getValue() != null) {
            Double val = super.getValue();
            return Double.toString(val);
        } else {
            return null;
        }
    }

    @Override
    public ParameterChangeResult changeValue(Object newValue) {
        ParameterChangeResult retVal = ParameterChangeResult.CHANGE_FAIL;

        try {
            double val = Double.parseDouble(newValue.toString());

            if (val >= super.getMinValue() && val <= super.getMaxValue()) {
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
            } else {
                retVal = ParameterChangeResult.CHANGE_FAIL;
                logger.error("Not changing parameter " + super.getName() + ", because its not in the defined parameter value interval [min, max].");

            }
        } catch (java.lang.NumberFormatException e) {
            logger.error("Number format exception caught when attepmting to change parameter. Offending value: " + newValue);
        }

        return retVal;
    }
}

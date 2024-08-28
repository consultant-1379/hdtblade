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
public interface ParameterInterface<T> extends Serializable {
        
    public static enum ParameterChangeResult {CHANGE_SUCCESS, CHANGE_FAIL, CHANGE_NONE};
    
    public void setValue(T value);

    public T getValue();

    public boolean getValueSet();

    public T getDefaultValue();

    public void setDefaultValue(T defaultValue);

    public T getMinValue();

    public void setMinValue(T minValue);

    public T getMaxValue();

    public void setMaxValue(T maxValue);

    public T getStepValue();

    public void setStepValue(T stepValue);

    public String getType();

    public void setContext(Context context);

    public Context getContext();

    public void setVisibility(int visibility);
    
    public int getVisibility();

    public String getValueAsString();

    public String getDefaultValueAsString();
    
    //
    // FIXME: All implementations rely on the fact that the object "newValue" has a valid toString() method.
    // Not very clever and type-safe.
    //
    public ParameterChangeResult changeValue(Object newValue);
    
    public boolean takesPrecedence(Parameter p);
}

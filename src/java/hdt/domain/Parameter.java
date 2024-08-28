/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.domain;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author escralp
 */
public abstract class Parameter<T> extends HdtItem implements ParameterInterface<T> {

    // FIXME: Using the generic type T here has side effects. E.g. it is possible to do a setValue("BLA") on
    // a Parameter object that has been declared Parameter<Integer>. Perhaps setValue, getValue, ...
    // need to be implemented in the concrete subclasses rather than in the abstract base class.
    private boolean valueSet = false;
    private int visibility;
    private T defaultValue;
    private T value;
    private T minValue;
    private T maxValue;
    private T stepValue;
    private String parameterType;
    private Context context;
   
    protected final Log logger = LogFactory.getLog(getClass());

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public Parameter(String name, String description, String type) {
        super(name, description);
        this.parameterType = type;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T value) {
        this.value = value;
        valueSet = true;
    }

    @Override
    public boolean getValueSet() {
        return valueSet;
    }

    @Override
    public T getDefaultValue() {
        return defaultValue;
    }

    @Override
    public void setDefaultValue(T defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public T getMaxValue() {
        return maxValue;
    }

    @Override
    public void setMaxValue(T maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    public T getMinValue() {
        return minValue;
    }

    @Override
    public void setMinValue(T minValue) {
        this.minValue = minValue;
    }

    @Override
    public T getStepValue() {
        return stepValue;
    }

    @Override
    public void setStepValue(T stepValue) {
        this.stepValue = stepValue;
    }

    @Override
    public String getType() {
        return parameterType;
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public int getVisibility() {
        return visibility;
    }

    @Override
    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }
    
    // 
    // This returns true if 'this' takes precedence over 'p' i.e. if the 'this' has a
    // narrower role context than p.
    //
    @Override
    public boolean takesPrecedence(Parameter p) {
        if (p != null) {
            return this.context.hasNarrowerRoleContext(p.getContext());
        } else {
            return false;
        }
    }
    
    /**
     * Only for debugging purposes.
     */
    public void dumpValues() {
        System.out.println("Parameter type: " + this.parameterType);
        System.out.println("Parameter default value: " + this.defaultValue);
        System.out.println("Parameter set? " + this.valueSet);
        System.out.println("Parameter value: " + this.value);
        System.out.println("Parameter min value: " + this.minValue);
        System.out.println("Parameter max value: " + this.maxValue);
        System.out.println("Parameter step value: " + this.stepValue);
        System.out.println("Parameter visibility level: " + this.visibility);

        if (context != null) {
            context.dumpValues();
        }
    }        
}

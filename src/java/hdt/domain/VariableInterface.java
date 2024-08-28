/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.domain;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author escralp
 */
public interface VariableInterface<T> extends Serializable {

    public String getType();

    public String getFormulaName();

    public void setFormulaName(String name);
     
    public T getResult();

    public void setResult(T result);

    public boolean isResultSet();

    public String getResultType();

    public void addVariableDependency(String depName);

    public List<String> getVariableDependencyList();
    
    public void addAPPDependency(String roleName, String appName);

    public void removeAPPDependency(APPDependency dep);

    public void removeDepInRemoveList();
    
    public List<APPDependency> getAPPDependencyList();

    public boolean hasVariableDependencies();
    
    public boolean hasAPPDependencies();
}

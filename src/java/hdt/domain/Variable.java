/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.domain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author escralp
 */
public abstract class Variable<T> extends HdtItem implements VariableInterface<T> {

    private boolean resultSet = false;   
    private String resultType;
    private String formulaName;    
    private T result;
    private List<String> variableDependencyList;
    private List<APPDependency> appDependencyList;
    private List<APPDependency> removeAppDependencyList;
            
    public Variable(String name, String description, String type) {
        super(name, description);
        this.resultType = type;
        this.variableDependencyList = new ArrayList<String>();
        this.appDependencyList = new ArrayList<APPDependency>();
        this.removeAppDependencyList = new ArrayList<APPDependency>();
    }

    @Override
    public void addVariableDependency(String depName) {
        this.variableDependencyList.add(depName);
    }
    
    @Override
    public List<String> getVariableDependencyList() {
        return variableDependencyList;
    }

    @Override
    public void addAPPDependency(String roleName, String appName) {
        APPDependency dep = new APPDependency(roleName, appName);
        this.appDependencyList.add(dep);
    }

    @Override
    public void removeAPPDependency(APPDependency dep) {
        if (dep != null) {
            removeAppDependencyList.add(dep);
        }
    }

    @Override
    public void removeDepInRemoveList() {
        appDependencyList.removeAll(removeAppDependencyList);
        removeAppDependencyList.clear();
    }

    @Override
    public List<APPDependency> getAPPDependencyList() {
        return appDependencyList;
    }

    @Override
    public String getResultType() {
        return resultType;
    }  

    @Override
    public String getType() {
        return resultType;
    }

    @Override
    public String getFormulaName() {
        return formulaName;
    }
   
    @Override
    public T getResult() {
        return this.result;
    }

    @Override
    public void setFormulaName(String name) {
        this.formulaName = name;
    }

    @Override
    public void setResult(T result) {
        this.result = result;
        resultSet = true;
    }

    @Override
    public boolean isResultSet() {
        return resultSet;
    }

    @Override
    public boolean hasVariableDependencies() {
        return !this.variableDependencyList.isEmpty();
    }

    @Override
    public boolean hasAPPDependencies() {
        return !this.appDependencyList.isEmpty();
    }
   
    /**
     * Only for debugging purposes.
     */
    public void dumpValues() {
        System.out.println("Variable result type: " + this.resultType);
        System.out.println("Variable name: " + this.getName());
        System.out.println("Variable description: " + this.getDescription());
        System.out.println("Variable formula name: " + this.formulaName);        
        System.out.println("Variable result set? " + this.resultSet);
        System.out.println("Variable result: " + this.result);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.dao;

/**
 *
 * @author escralp
 */
public class DaoContainer {

    private NetworkDao networkDao;
    private HdtSystemDao systemDao;
    private RoleDao roleDao;
    private ParameterDao parameterDao;
    private VariableDao variableDao;
    private FormulaDao formulaDao;
    private HardwareConfigDao hwConfigDao;
    private APPNumberDao appNumDao;

    public APPNumberDao getAppNumDao() {
        return appNumDao;
    }

    public void setAppNumDao(APPNumberDao appNumDao) {
        this.appNumDao = appNumDao;
    }

    public HardwareConfigDao getHwConfigDao() {
        return hwConfigDao;
    }

    public void setHwConfigDao(HardwareConfigDao hwConfigDao) {
        this.hwConfigDao = hwConfigDao;
    }

    public FormulaDao getFormulaDao() {
        return formulaDao;
    }

    public void setFormulaDao(FormulaDao formulaDao) {
        this.formulaDao = formulaDao;
    }

    public VariableDao getVariableDao() {
        return variableDao;
    }

    public void setVariableDao(VariableDao variableDao) {
        this.variableDao = variableDao;
    }

    public ParameterDao getParameterDao() {
        return parameterDao;
    }

    public void setParameterDao(ParameterDao parameterDao) {
        this.parameterDao = parameterDao;
    }

    public RoleDao getRoleDao() {
        return roleDao;
    }

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public NetworkDao getNetworkDao() {
        return networkDao;
    }

    public void setNetworkDao(NetworkDao networkDao) {
        this.networkDao = networkDao;
    }

    public HdtSystemDao getSystemDao() {
        return systemDao;
    }

    public void setSystemDao(HdtSystemDao systemDao) {
        this.systemDao = systemDao;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.dao;

import hdt.domain.Formula;

/**
 *
 * @author escralp
 */
public interface FormulaDao extends GenericDao<Formula, Integer> {

    public Formula findFormulaByName(String formulaName);
}

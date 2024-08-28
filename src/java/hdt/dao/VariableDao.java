/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.dao;

import hdt.domain.HdtSystem;
import hdt.domain.Network;
import hdt.domain.Role;
import hdt.domain.Variable;
import java.util.List;

/**
 *
 * @author escralp
 */
public interface VariableDao extends GenericDao<Variable, Integer> {

    public List<Variable> findVariables(Role role, Network network, HdtSystem system);
}

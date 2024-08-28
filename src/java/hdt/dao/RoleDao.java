/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.dao;

import hdt.domain.HdtSystem;
import hdt.domain.Role;
import java.util.List;

/**
 *
 * @author escralp
 */
public interface RoleDao extends GenericDao<Role, Integer> {

    public List<Role> findRoleBySystem(HdtSystem system);
}

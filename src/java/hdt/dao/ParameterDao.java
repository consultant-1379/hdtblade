/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.dao;

import hdt.domain.Bundle;
import hdt.domain.HdtSystem;
import hdt.domain.Parameter;
import hdt.domain.Network;
import hdt.domain.Role;
import java.util.List;

/**
 *
 * @author escralp
 */
public interface ParameterDao extends GenericDao<Parameter, Integer> {

    public List<Parameter> findAllMainParameters(Bundle bundle, Network network, HdtSystem system);

    public List<Parameter> findRoleSpecificMainParameters(Bundle bundle, Role role, Network network, HdtSystem system);

    public List<Parameter> findSecondaryParameters(Bundle bundle, Role role, Network network, HdtSystem system);
}

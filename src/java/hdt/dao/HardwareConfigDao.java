/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.dao;

import hdt.domain.HardwareConfig;
import hdt.domain.Role;
import hdt.domain.Variable;
import java.util.List;

/**
 *
 * @author escralp
 */
public interface HardwareConfigDao extends GenericDao<HardwareConfig, Integer> {

    public List<HardwareConfig> findHardwareConfig(List<Variable> varList, Role role);
}

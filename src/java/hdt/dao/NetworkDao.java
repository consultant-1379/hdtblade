/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.dao;

import hdt.domain.Network;
import java.util.List;

/**
 *
 * @author escralp
 */
public interface NetworkDao extends GenericDao<Network, String> {

    public List<Network> findNetworksBySystemId(String systemId);
}

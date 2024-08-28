/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.dao.impl;

import hdt.dao.NetworkDao;
import hdt.domain.Network;
import hdt.service.HdtQueries;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author escralp
 */
@Repository
public class NetworkDaoJdbcImpl extends GenericDaoJdbcImpl<Network, String> implements NetworkDao {

    private final JdbcTemplate jdbcTemplate;   

    private static final class NetworkMapper implements RowMapper<Network> {

        @Override
        public Network mapRow(ResultSet rs, int rowNum) throws SQLException {
            Network net = new Network(rs.getString("network_name"), rs.getString("network_description"));
            return net;
        }
    }

    public NetworkDaoJdbcImpl() throws ServletException {
        super();
        jdbcTemplate = new JdbcTemplate(dataSource);        
    }

    @Override
    public List<Network> findNetworksBySystemId(String systemId) {        
        // This is really expensive.
        clear();

        List<Network> netList = this.jdbcTemplate.query(HdtQueries.GET_APPLICABLE_NETWORKS, new Object[]{systemId}, new NetworkMapper());

        // Need to cache it here so we can do a search for the name.
        insertAll(netList);
        return netList;
    }
}

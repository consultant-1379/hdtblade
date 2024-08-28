/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.dao.impl;

import hdt.dao.HardwareConfigDao;
import hdt.domain.HardwareConfig;
import hdt.domain.Role;
import hdt.domain.Variable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author escralp
 */
@Repository
public class HardwareConfigDaoJdbcImpl extends GenericDaoJdbcImpl<HardwareConfig, Integer> implements HardwareConfigDao {

    private final JdbcTemplate jdbcTemplate;

    private static final class HardwareConfigMapper implements RowMapper<HardwareConfig> {

        @Override
        public HardwareConfig mapRow(ResultSet rs, int rowNum) throws SQLException {
            HardwareConfig hwCfg = new HardwareConfig(rs.getString("hw_config_name"), rs.getString("hw_config_description"));
            hwCfg.setHwConfigRevision(rs.getInt("hw_config_revision"));
            hwCfg.setDbHwStatus(rs.getInt("hw_config_status"));
            return hwCfg;
        }
    }

    public HardwareConfigDaoJdbcImpl() throws ServletException {
        super();
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // This builds up a list of HardwareConfig objects that match the calculated results.
    @Override
    public List<HardwareConfig> findHardwareConfig(List<Variable> varList, Role role) {
        String hwTableName = role.getHardwareConfigTableName();
        List<HardwareConfig> hwConfList = new ArrayList<HardwareConfig>();
        List<HardwareConfig> intermediateResults = null;

        logger.debug("Finding hardware config for role = " + role.getName() + ", hwTableName = "
                + hwTableName);

        // Build query string.
        int size = varList.size();
        String query = "SELECT t1.hw_config_name, t1.hw_config_description, t2.hw_config_revision, t2.hw_config_status FROM hw_config AS t1 JOIN " + hwTableName
                + " AS t2 ON t1.hw_config_name=t2.hw_config_name WHERE ";
        for (Variable var : varList) {
            if (var.isResultSet()) {
                if (--size == 0) {
                    query = query + var.getName() + "=" + var.getResult() + " ORDER BY hw_config_revision DESC";
                } else {
                    query = query + var.getName() + "=" + var.getResult() + "AND ";
                }
            }
        }

        logger.debug("Executing SQL query: " + query);
        try {
            intermediateResults = (List<HardwareConfig>) this.jdbcTemplate.query(query, new HardwareConfigMapper());
        } catch (BadSqlGrammarException e) {
            logger.error("Error retrieving the Hardware Config, HW table name = " + hwTableName
                    + ", Error message: " + e.getMessage());
        }

        // Make sure resulting list contains unique elements.
        if (intermediateResults != null && !intermediateResults.isEmpty()) {
            for (HardwareConfig cfg : intermediateResults) {
                if (!hwConfList.contains(cfg)) {
                    hwConfList.add(cfg);
                }
            }
        }
        
        // List is sorted in descending hardware revision, i.e. highest (most modern) config first.
        return hwConfList;
    }
}

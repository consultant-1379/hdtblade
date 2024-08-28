/*
 * To change this template, choose Tools | Templates
 * and ope the template in the editor.
 */
package hdt.dao.impl;

import hdt.dao.HdtDbConfigDao;
import hdt.service.HdtQueries;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *  The HDT Application DAO.
 *
 * @author Ralph Schlosser
 * <p>FIXME: The version should come from CVS.</p>
 * @version 0.1
 * <p>date 21/10/2010</p>
 */
@Repository
public class HdtDbConfigDaoJdbcImpl extends BaseDaoJdbcImpl implements HdtDbConfigDao {

    private JdbcTemplate jdbcTemplate;
    private String dbVersion;

    private static final class HdtConfigMapper implements RowMapper<String> {

        @Override
        public String mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getString("dbversion");
        }
    }

    public HdtDbConfigDaoJdbcImpl() {
        super();
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.dbVersion = jdbcTemplate.queryForObject(HdtQueries.GET_DB_VERSION_SQL, new HdtConfigMapper());
    }
   
    @Override
    public String getDbVersion() {
        return dbVersion;
    }
}

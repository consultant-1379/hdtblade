/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.dao.impl;

import hdt.dao.BundleDao;
import hdt.domain.Bundle;
import hdt.service.HdtQueries;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author escralp
 */
@Repository
public class BundleDaoJdbcImpl extends GenericDaoJdbcImpl<Bundle, String> implements BundleDao {

    private final JdbcTemplate jdbcTemplate;

    private static final class BundleMapper implements RowMapper<Bundle> {

        @Override
        public Bundle mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Bundle(rs.getString("bundle_name"), rs.getString("bundle_description"));
        }
    }

    public BundleDaoJdbcImpl() throws ServletException {
        super();
        jdbcTemplate = new JdbcTemplate(dataSource);
        List<Bundle> bundleList = this.jdbcTemplate.query(HdtQueries.GET_BUNDLES_SQL, new BundleMapper());
        // Insert all elements into the generic DAO.
        insertAll(bundleList);
    }
}

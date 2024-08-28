/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.dao.impl;

import hdt.dao.QuickLinkDao;
import hdt.domain.QuickLink;
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
public class QuickLinkDaoJdbcImpl extends GenericDaoJdbcImpl<QuickLink, Integer> implements QuickLinkDao {

    private final JdbcTemplate jdbcTemplate;

    private static final class QuickLinkMapper implements RowMapper<QuickLink> {

        @Override
        public QuickLink mapRow(ResultSet rs, int rowNum) throws SQLException {
            QuickLink qL = new QuickLink(rs.getString("quicklink_description"), rs.getString("quicklink_url"));
            return qL;
        }
    }

    public QuickLinkDaoJdbcImpl() throws ServletException {
        super();

        jdbcTemplate = new JdbcTemplate(dataSource);
        // Load data from Quicklinks table.
        List<QuickLink> qlList = this.jdbcTemplate.query(HdtQueries.GET_QUICKLINKS_SQL, new QuickLinkMapper());
        // Insert all elements into the generic DAO.
        insertAll(qlList);
    }
}

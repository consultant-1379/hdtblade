package hdt.dao.impl;

import hdt.dao.HdtSystemDao;
import hdt.domain.HdtSystem;
import hdt.service.HdtQueries;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class HdtSystemDaoJdbcImpl extends GenericDaoJdbcImpl<HdtSystem, String> implements HdtSystemDao {

    private final JdbcTemplate jdbcTemplate;

    private static final class SystemMapper implements RowMapper<HdtSystem> {

        @Override
        public HdtSystem mapRow(ResultSet rs, int rowNum) throws SQLException {
            HdtSystem sys = new HdtSystem(rs.getString("system_name"), rs.getString("system_description"));

            return sys;
        }
    }

    public HdtSystemDaoJdbcImpl() throws ServletException {
        super();

        jdbcTemplate = new JdbcTemplate(dataSource);
        // Load data from Systems table.
        List<HdtSystem> sysList = this.jdbcTemplate.query(HdtQueries.GET_SYSTEMS_SQL, new SystemMapper());
        // Insert all elements into the generic DAO.
        insertAll(sysList);
    }
}

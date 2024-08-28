/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.dao.impl;

import hdt.dao.RoleDao;
import hdt.domain.HdtSystem;
import hdt.domain.Role;
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
public class RoleDaoJdbcImpl extends GenericDaoJdbcImpl<Role, Integer> implements RoleDao {

    private final JdbcTemplate jdbcTemplate;

    private static final class RoleMapper implements RowMapper<Role> {

        private String applicableSystem;

        public RoleMapper(String applicableSystem) {
            this.applicableSystem = applicableSystem;
        }

        @Override
        public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
            Role role = new Role(rs.getString("role_name"), rs.getString("role_description"), applicableSystem);
            role.setHardwareConfigTableName(rs.getString("hw_config_table_name"));
            // We keep this here so we can quickly get the system name
            // to which a role belongs.            
            return role;
        }
    }

    public RoleDaoJdbcImpl() throws ServletException {
        super();
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Role> findRoleBySystem(HdtSystem system) {
        List<Role> roleList = this.jdbcTemplate.query(HdtQueries.GET_ROLES_SQL, new Object[]{system.getName()}, new RoleMapper(system.getName()));
        insertAll(roleList);
        return roleList;
    }
}

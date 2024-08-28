/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.dao.impl;

import hdt.dao.VariableDao;
import hdt.domain.DoubleVariable;
import hdt.domain.HdtSystem;
import hdt.domain.Network;
import hdt.domain.Role;
import hdt.domain.Variable;
import hdt.service.HdtConstants;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author escralp
 */
@Repository
public class VariableDaoJdbcImpl extends GenericDaoJdbcImpl<Variable, Integer> implements VariableDao {

    private final JdbcTemplate jdbcTemplate;
    
     public static final String GET_FORMULA_TVCTX_SQL = "SELECT t1.`variable_name`, t1.`formula_name` "
            + "FROM `variable_formula` as t1 JOIN `variables` as t2 ON t1.`variable_name`=t2.`variable_name` "
            + "WHERE `role_name`=? AND `network_name`=? AND `system_name`=?";
     
     public static final String GET_VARIABLE_SQL = "SELECT * FROM `variables` WHERE `variable_name`=?";     
     
    /**
     * IncompleteVariable
     *
     * This inner class serves as a temporary data structure to hold query values to eventually
     * build.
     * 
     * NOTE: This is class only used locally and must never be instantiated outside the context of the enclosing
     *       class. Therefore all member fields are public to avoid setters / getters.
     */
    private static final class IncompleteVariable {
        // Only local access so make all fields public so we need no setters / getters.

        public String varName;
        public String fmlName;          
    }

    /**
     * VariableMapper
     *
     * This helper class maps a row of variable table entries to a concrete Variable object.
     */
    private static final class VariableMapper implements RowMapper<Variable> {

        @Override
        public Variable mapRow(ResultSet rs, int rowNum) throws SQLException {
            String type = rs.getString("variable_result_type");
            // Determine the variable result type. - Only Double supported for now.
            if (type != null && type.equals(HdtConstants.HDT_TYPE_DOUBLE)) {
                Variable<Double> var = new DoubleVariable(rs.getString("variable_name"), rs.getString("variable_description"));
                return var;
            } else {
                throw new UnsupportedOperationException("Variable type not yet supported.");
            }
        }
    }

    /**
     * VariableFormulaMapper
     *
     * This inner class sores entries from the variable_formula table into the IncompleteVariable
     * helper data structure.
     */
    private static final class VariableFormulaMapper implements RowMapper<IncompleteVariable> {

        @Override
        public IncompleteVariable mapRow(ResultSet rs, int rowNum) throws SQLException {
            IncompleteVariable var = new IncompleteVariable();
            var.varName = rs.getString("variable_name");
            var.fmlName = rs.getString("formula_name");                      
            return var;
        }
    }

    public VariableDaoJdbcImpl() throws ServletException {
        super();
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Variable> findVariables(Role role, Network network, HdtSystem system) {
        logger.debug("Finding variables for role = " + role.getName() + ", network = "
                + network.getName() + ", system = " + system.getName());

        // First, we get the variable names, formula names and test variable names from the DB based
        // on the input selection.
        List<IncompleteVariable> incompleteVariableList = null;
        try {
            incompleteVariableList = this.jdbcTemplate.query(GET_FORMULA_TVCTX_SQL,
                    new Object[]{role.getName(), network.getName(), system.getName()}, new VariableFormulaMapper());            
        } catch (EmptyResultDataAccessException e) {
            // NOTE: This is only for debugging purposes. The Exception must be properly dealt with in the calling method.
            logger.debug("Empty Result set for query " + GET_FORMULA_TVCTX_SQL + " with parameters: " + role.getName() + ", "
                    + network.getName() + ", " + system.getName());
            logger.debug("Caught exception: " + e);
        }

        // Now create the list of Variable objects relevant to the input selection.
        List<Variable> variables = new ArrayList<Variable>();
        for (IncompleteVariable inclVar : incompleteVariableList) {
            try {
                Variable variable = (Variable) this.jdbcTemplate.queryForObject(GET_VARIABLE_SQL, new Object[]{inclVar.varName},
                        new VariableMapper());
                variable.setFormulaName(inclVar.fmlName);                
                variables.add(variable);
            } catch (EmptyResultDataAccessException e) {
                // NOTE: This is only for debugging purposes. The Exception must be properly dealt with in the calling method.
                logger.debug("Empty Result set for query " + GET_VARIABLE_SQL + " with parameters: " + role.getName() + ", " + network.getName());
                logger.debug("Caught exception: " + e);
            }
        }

        return variables;
    }
}

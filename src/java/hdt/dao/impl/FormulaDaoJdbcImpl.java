/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.dao.impl;

import hdt.dao.FormulaDao;
import hdt.domain.Formula;
import hdt.service.HdtQueries;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class FormulaDaoJdbcImpl extends GenericDaoJdbcImpl<Formula, Integer> implements FormulaDao {

    private final JdbcTemplate jdbcTemplate;

    private static final class FormulaMapper implements RowMapper<Formula> {

        @Override
        public Formula mapRow(ResultSet rs, int rowNum) throws SQLException {
            Formula fml = new Formula(rs.getString("formula_name"), rs.getString("formula_description"));
            fml.setFomulaJSCode(rs.getString("formula_js_code"));
            return fml;
        }
    }

    public FormulaDaoJdbcImpl() throws ServletException {
        super();

        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Formula findFormulaByName(String formulaName) {
        Formula ret = null;
        try {
            ret = this.jdbcTemplate.queryForObject(HdtQueries.GET_FORMULA_SQL, new Object[]{formulaName}, new FormulaMapper());
        } catch (EmptyResultDataAccessException e) {
            // NOTE: This is only for debugging purposes. The Exception must be properly dealt with in the calling method.
            logger.debug("Empty Result set for query " + HdtQueries.GET_FORMULA_SQL + " with parameter: " + formulaName);
            logger.debug("Caught exception: " + e);
        }
        return ret;
    }
}

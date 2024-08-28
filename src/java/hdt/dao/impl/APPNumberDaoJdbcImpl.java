/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.dao.impl;

import hdt.dao.APPNumberDao;
import hdt.domain.APPNumber;
import hdt.domain.Alternatives;
import hdt.domain.HardwareConfig;
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
public class APPNumberDaoJdbcImpl extends BaseDaoJdbcImpl implements APPNumberDao {

    private final JdbcTemplate jdbcTemplate;
    public static final String GET_APP_NUMBERS_SQL = "SELECT * FROM `app_numbers` AS t1 JOIN `hw_config_app` as t2 ON "
            + "t1.`app_number` = t2.`app_number` WHERE t2.`hw_config_name`=?";

    private static final class APPNumMapper implements RowMapper<APPNumber> {

        @Override
        public APPNumber mapRow(ResultSet rs, int rwoNum) throws SQLException {
            APPNumber retVal = new APPNumber(rs.getString("app_number"), rs.getString("app_num_description"));
            retVal.setQuantity(rs.getInt("quantity"));
            retVal.setEndOfServiceLife(rs.getString("app_num_eol"));
            retVal.setLastOrderDate(rs.getString("app_num_lod"));
            retVal.setExposedToJS(rs.getBoolean("app_exposed"));
            return retVal;
        }
    }

    public APPNumberDaoJdbcImpl() throws ServletException {
        super();
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Alternatives convertHardwareConfigToAPPNumbers(List<HardwareConfig> hwConfigList) {
        Alternatives alt = new Alternatives();

        if (hwConfigList.isEmpty()) {
            alt.setContainsEmpty(true);
        } else {
            for (HardwareConfig hwConfig : hwConfigList) {
                List<APPNumber> appNumList = this.jdbcTemplate.query(GET_APP_NUMBERS_SQL, new Object[]{hwConfig.getName()}, new APPNumMapper());

                if (appNumList.isEmpty()) {
                    // This indicates that the alternative is empty.
                    alt.setContainsEmpty(true);
                }

                // Put in reference to Hardware Config.
                alt.add(appNumList, hwConfig);
            }
        }
        
        return alt;
    }
}

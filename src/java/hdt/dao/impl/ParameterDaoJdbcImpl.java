/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.dao.impl;

import hdt.dao.ParameterDao;
import hdt.domain.BooleanParameter;
import hdt.domain.Bundle;
import hdt.domain.Context;
import hdt.domain.DoubleParameter;
import hdt.domain.HdtSystem;
import hdt.domain.IntegerParameter;
import hdt.domain.Parameter;
import hdt.domain.Network;
import hdt.domain.Role;
import hdt.service.HdtConstants;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

/**
 *
 * @author escralp
 */
@Repository
public class ParameterDaoJdbcImpl extends GenericDaoJdbcImpl<Parameter, Integer> implements ParameterDao {

    private final JdbcTemplate jdbcTemplate;    
    
    // SQL Queries.    
    private static final String GET_SECONDARY_PARAMETERS_SQL = "SELECT * FROM `parameter_values` AS t1 JOIN `parameters` as t2 ON"
            + " t1.`parameter_name`=t2.`parameter_name` WHERE (`bundle_name`=? OR `bundle_name`='" + HdtConstants.HDT_ANY_BUNDLE_CTX + "')"
            + " AND (`role_name`=? OR `role_name`='" + HdtConstants.HDT_ANY_ROLE_CTX + "')"
            + " AND (`network_name`=? OR `network_name`='" + HdtConstants.HDT_ANY_NETWORK_CTX + "')"
            + " AND (`system_name`=? OR `system_name`='" + HdtConstants.HDT_ANY_SYSTEM_CTX + "')"
            + " AND (`visibility`!=\"" + HdtConstants.Visibility.VISIBILITY_MAIN_PARAM.getVisibilityAsStr() + "\")";
        
    private static final String GET_ALL_MAIN_PARAMETERS_SQL = "SELECT * FROM `parameter_values` AS t1 JOIN `parameters` as t2 ON"
            + " t1.`parameter_name`=t2.`parameter_name` WHERE (`bundle_name`=? OR `bundle_name`='" + HdtConstants.HDT_ANY_BUNDLE_CTX + "')"
            + " AND (`role_name` IN (SELECT `role_name` from `system_role` WHERE `system_name`=?) OR `role_name`='" + HdtConstants.HDT_ANY_ROLE_CTX + "')"
            + " AND (`network_name`=? OR `network_name`='" + HdtConstants.HDT_ANY_NETWORK_CTX + "')"
            + " AND (`system_name`=? OR `system_name`='" + HdtConstants.HDT_ANY_SYSTEM_CTX + "')"
            + " AND (`visibility`=\"" + HdtConstants.Visibility.VISIBILITY_MAIN_PARAM.getVisibilityAsStr() + "\""
            + "      OR `visibility`=\"" + HdtConstants.Visibility.VISIBILITY_MAIN_CONST.getVisibilityAsStr() + "\")";
            
    private static final String GET_ROLE_MAIN_PARAMETERS_SQL = "SELECT * FROM `parameter_values` AS t1 JOIN `parameters` as t2 ON"
            + " t1.`parameter_name`=t2.`parameter_name` WHERE (`bundle_name`=? OR `bundle_name`='" + HdtConstants.HDT_ANY_BUNDLE_CTX + "')"
            + " AND (`role_name`=? OR `role_name`='" + HdtConstants.HDT_ANY_ROLE_CTX + "')"
            + " AND (`network_name`=? OR `network_name`='" + HdtConstants.HDT_ANY_NETWORK_CTX + "')"
            + " AND (`system_name`=? OR `system_name`='" + HdtConstants.HDT_ANY_SYSTEM_CTX + "')"
            + " AND (`visibility`=\"" + HdtConstants.Visibility.VISIBILITY_MAIN_PARAM.getVisibilityAsStr() + "\")";    

    /**
     * ParameterMapper
     *
     * This helper class maps a row of parameter table entries to a concrete Parameter object.
     */
    private static final class ParameterMapper implements ResultSetExtractor<List<Parameter>> {

        @Override
        public List<Parameter> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<Parameter> tmpParList = new ArrayList<Parameter>();
            while (rs.next()) {
                String type = rs.getString("parameter_type");
                Parameter par;

                Context ctx = new Context();
                ctx.setBundleContext(rs.getString("bundle_name"));
                ctx.setRoleContext(rs.getString("role_name"));
                ctx.setNetworkContext(rs.getString("network_name"));
                ctx.setSystemContext(rs.getString("system_name"));
                int visibility = rs.getInt("visibility");

                // Determine the parameter type.
                // FIXME: Not optimal, should use some kind of polymorphism-based approach, did it that way
                // to avoid "unchecked" warnings.
                if (type != null) {
                    String parameterName = rs.getString("parameter_name");
                    String parameterDescription = rs.getString("parameter_description");
                    if (type.equals(HdtConstants.HDT_TYPE_INTEGER)) {
                        par = new IntegerParameter(parameterName, parameterDescription);
                        // Create a new IntegerParameter object.
                        par.setDefaultValue(rs.getInt("parameter_default_val"));
                        par.setMinValue(rs.getInt("parameter_min_val"));
                        par.setMaxValue(rs.getInt("parameter_max_val"));
                        par.setStepValue(rs.getInt("parameter_step_val"));
                    } else if (type.equals(HdtConstants.HDT_TYPE_DOUBLE)) {
                        par = new DoubleParameter(parameterName, parameterDescription);
                        // Create a new IntegerParameter object.
                        par.setDefaultValue(rs.getDouble("parameter_default_val"));
                        par.setMinValue(rs.getDouble("parameter_min_val"));
                        par.setMaxValue(rs.getDouble("parameter_max_val"));
                        par.setStepValue(rs.getDouble("parameter_step_val"));
                    } else if (type.equals(HdtConstants.HDT_TYPE_BOOLEAN)) {
                        par = new BooleanParameter(parameterName, parameterDescription);
                        // Create a new BooleanParameter object.
                        par.setDefaultValue(rs.getBoolean("parameter_default_val"));
                    } else {
                        throw new UnsupportedOperationException("Parameter type not yet supported.");
                    }

                    par.setContext(ctx);
                    par.setVisibility(visibility);

                    tmpParList.add(par);
                }
            }

            return tmpParList;
        }
    }

    public ParameterDaoJdbcImpl() throws ServletException {
        super();
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Parameter> findSecondaryParameters(final Bundle bundle, final Role role, final Network network, final HdtSystem system) {
        logger.debug("Finding parameters for role = " + role.getName() + ", network = "
                + network.getName() + ", system = " + system.getName());

        // This is the list of Parameter objects which we'll return.

        List<Parameter> tmpParList = new ArrayList<Parameter>();
        try {
            tmpParList = (List<Parameter>) this.jdbcTemplate.query(GET_SECONDARY_PARAMETERS_SQL,
                    new Object[]{bundle.getName(), role.getName(), network.getName(), system.getName()}, new ParameterMapper());

        } catch (EmptyResultDataAccessException e) {
            // NOTE: This is only for debugging purposes. The Exception must be properly dealt with in the calling method.
            logger.debug("Empty Result Set for query " + GET_SECONDARY_PARAMETERS_SQL + " with parameters: " + role.getName() + ", "
                    + ", " + network.getName() + ", " + system.getName());
            logger.debug("Caught exception: " + e);
        }

        //
        // Postprocessing: This makes sure that if a non-main parameter, e.g. LR_NODE is defined in a "ANY_ROLE" context but also
        //                 in for a specific role, say "ADMIN", the narrower scope paramter (i.e. "ADMIN" context) takes precedence.
        Map<String, Parameter> tmpMap = new HashMap<String, Parameter>();
        for (Parameter curPar : tmpParList) {
            if (tmpMap.containsKey(curPar.getName())) {
                // A parameter of the same name is already present in the map.
                Parameter existingPar = tmpMap.get(curPar.getName());

                if (curPar.takesPrecedence(existingPar)) {
                    // Overwrite the global parameter in favour of the local one.
                    tmpMap.put(curPar.getName(), curPar);
                }
            } else {
                // If the parameter is not yet there, add it to the map.
                tmpMap.put(curPar.getName(), curPar);
            }
        }

        List<Parameter> ctxParList = new ArrayList<Parameter>(tmpMap.values());
        return ctxParList;
    }

    @Override
    public List<Parameter> findAllMainParameters(Bundle bundle, Network network, HdtSystem system) {
        logger.debug("Finding ALL main dimensioning parameters for bundle = " + bundle.getName() + ", network = "
                + network.getName() + ", system = " + system.getName());

        // FIXME: synchonizedList necessary here?
        List<Parameter> mainDimParList = Collections.synchronizedList(new ArrayList<Parameter>());
        try {
            mainDimParList = (List<Parameter>) this.jdbcTemplate.query(GET_ALL_MAIN_PARAMETERS_SQL,
                    new Object[]{bundle.getName(), system.getName(), network.getName(), system.getName()}, new ParameterMapper());
        } catch (EmptyResultDataAccessException e) {
            // NOTE: This is only for debugging purposes. The Exception must be properly dealt with in the calling method.
            logger.error("Empty Result Set for query " + GET_ALL_MAIN_PARAMETERS_SQL + " with parameters: " + bundle.getName() + ", "
                    + ", " + network.getName() + ", " + system.getName());
            logger.error("Caught exception: " + e);
        }

        return mainDimParList;
    }

    @Override
    public List<Parameter> findRoleSpecificMainParameters(Bundle bundle, Role role, Network network, HdtSystem system) {
        logger.debug("Finding role specific main parameters for role = " + role.getName() + ", network = "
                + network.getName() + ", system = " + system.getName());

        // FIXME: synchonizedList necessary here?
        List<Parameter> parList = Collections.synchronizedList(new ArrayList<Parameter>());
        try {
            parList = (List<Parameter>) this.jdbcTemplate.query(GET_ROLE_MAIN_PARAMETERS_SQL,
                    new Object[]{bundle.getName(), role.getName(), network.getName(), system.getName()}, new ParameterMapper());
        } catch (EmptyResultDataAccessException e) {
            // NOTE: This is only for debugging purposes. The Exception must be properly dealt with in the calling method.
            logger.error("Empty Result Set for query " + GET_ROLE_MAIN_PARAMETERS_SQL + " with parameters: " + bundle.getName() + ", "
                    + ", " + network.getName() + ", " + system.getName());
            logger.error("Caught exception: " + e);
        }

        return parList;
    }
}

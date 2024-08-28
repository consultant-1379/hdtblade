/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.service;

/**
 * This class is never instantiated and contains
 * various MySQL queries used in the system.
 *
 * @author Ralph Schlosser
 * @version 0.1
 * <p>FIXME: The Version should come from CVS.</p>
 * <p>date 26/10/2010</p>
 */
public class HdtQueries {

    // FIXME: All the SQL queries should be refactored and put into their respective DAO implementation.
    
    public static final String GET_DB_VERSION_SQL = "SELECT `dbversion` FROM `hdtconfiguration`";
    public static final String GET_SYSTEMS_SQL = "SELECT `system_name`, `system_description` FROM `systems`";
    public static final String GET_QUICKLINKS_SQL = "SELECT `quicklink_description`, `quicklink_url` FROM `quicklinks`";
    public static final String GET_NOTES_SQL = "SELECT `note_text`, `note_shown` FROM `notes`";
    public static final String GET_ROLES_SQL = "SELECT t1.`role_name`, t1.`role_description`, t1.`hw_config_table_name` "
            + "FROM `roles` AS t1 LEFT JOIN `system_role` AS t2 ON t1.`role_name`=t2.`role_name` WHERE system_name=?";    
    public static final String GET_APPLICABLE_NETWORKS = "SELECT t1.`network_name`, t1.`network_description` FROM `networks` AS t1 "
            + "JOIN `system_network` AS t2 ON t1.`network_name`=t2.`network_name` WHERE t2.`system_name`=?";
    
    public static final String GET_ALL_HW_CFG_SQL = "SELECT `hw_config_name`, `hw_config_description` FROM `hw_config`;";
           
    public static final String GET_FORMULA_SQL = "SELECT * FROM `formulas` WHERE `formula_name`=?";     
    public static final String GET_BUNDLES_SQL = "SELECT `bundle_name`, `bundle_description` FROM `bundles`";
}

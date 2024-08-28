/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.dao.impl;

import hdt.service.HdtConstants;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

/**
 * Encapsulates basic DB access functionality for all DB DAOs.
 * 
 * @author escralp
 */
@Repository
public abstract class BaseDaoJdbcImpl {

    protected final Log logger = LogFactory.getLog(getClass());
    private InitialContext ctx;
    public DataSource dataSource;

    public BaseDaoJdbcImpl() {
        initDataSource();
    }

    private void initDataSource() {
        try {
            ctx = new InitialContext();
            // Look up the data source on the server.
            dataSource = (DataSource) ctx.lookup(HdtConstants.HDT_DATASOURCE_NAME);
        } catch (NamingException ne) {
            logger.error("NamingException: " + ne.getMessage());
        }
    }
}

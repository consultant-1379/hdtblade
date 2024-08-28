/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.test;

import hdt.service.HdtConstants;
import hdt.service.HdtTestConstants;
import org.junit.BeforeClass;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.naming.InitialContext;
import javax.naming.Context;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author escralp
 */
// Make it a JUnit test.
// Load the Application Context. This is necessary so that we can access
// Resources outside the Application Container, e.g. use the datsource etc.
@ContextConfiguration(locations = {"file:web/WEB-INF/applicationContext.xml",
    "file:web/WEB-INF/hdt-servlet-test.xml"})
public class BaseHdtTest {

    public static final MyLoggerImpl myLogger = new MyLoggerImpl();
    
    public BaseHdtTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        // Setup the JNDI context and the datasource.
        // Create the initial context. This is important for the DAOs to work.
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                "org.apache.naming.java.javaURLContextFactory");
        System.setProperty(Context.URL_PKG_PREFIXES,
                "org.apache.naming");
        InitialContext ic = new InitialContext();

        ic.createSubcontext("java:");
        ic.createSubcontext("java:comp");
        ic.createSubcontext("java:comp/env");
        ic.createSubcontext("java:comp/env/jdbc");

        // Construct DriverManagerDataSource for HDT.
        DriverManagerDataSource ds = new DriverManagerDataSource(HdtTestConstants.MYSQL_DATASOURCE_URL,
                HdtTestConstants.MYSQL_DATASOURCE_LOGIN,
                HdtTestConstants.MYSQL_DATASOURCE_PW);
        ds.setDriverClassName(HdtTestConstants.MYSQL_DRIVER_CLASS);
        ic.bind(HdtConstants.HDT_DATASOURCE_NAME, ds);
    }

    // FIXME: I am not sure if this is correct; this is supposed to be a super class
    // with common initialisation from which all other test classes derive, hence it should
    // not be run as it doesn't contain any actual test.
    // Below is a hack to tell JUnit to not run the test so I can run
    // ALL tests this in Netbeans using Alt+F6.
    @Ignore("Not running base class")
    @Test
    public void testBaseHdt() {
    }
}

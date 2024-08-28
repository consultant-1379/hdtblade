/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author escralp
 */
public class QuickSeleniumTest extends SeleneseTestCase {

    @Before
    public void setUp() throws Exception {
        selenium = new DefaultSelenium("localhost", 4444, "*chrome", "http://localhost:8080/");
        selenium.start();
    }

    @Test
    public void testT1() throws Exception {
        selenium.open("/HDT_Prototype1/login.htm");
        selenium.type("username", "demo");
        selenium.type("password", "demo");
        selenium.click("//input[@value='Login']");
        selenium.waitForPageToLoad("30000");
        selenium.select("systemId", "label=OSS-RC 11.2");
        selenium.select("networkList", "label=LTE");
        selenium.select("bundleSelectionList", "label=Small System");
        selenium.type("customerName", "Test Customer");
        selenium.type("confName", "Test Config 1");
        selenium.click("//input[@value='Next']");
        selenium.waitForPageToLoad("30000");
        selenium.click("//input[@value='Generate Report']");
        selenium.waitForPageToLoad("30000");
    }

    @After  
    public void tearDown() throws Exception {
        selenium.stop();
    }
}

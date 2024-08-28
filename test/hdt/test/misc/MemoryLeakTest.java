/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hdt.test.misc;

import java.util.HashMap;
import java.util.Calendar;
import java.util.Random;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author escralp
 */
public class MemoryLeakTest {
    private Map myMap = null;

    public MemoryLeakTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    private Map mapReturningFunc() {
        Calendar cal = Calendar.getInstance();
        Random generator = new Random(cal.getTimeInMillis());
        Map retVal = new HashMap<String, Integer>();
        for (Integer i = 0; i < 2000; i++) {
            Integer randInt = generator.nextInt();
            retVal.put("key_" + randInt, randInt);
        }
        return retVal;
    }
        
    @Test
    //@Test(expected=NullPointerException.class)
    public void hello() {
        System.out.println("Memory leak test");

        for(Integer i = 0; i < 100; i++) {
            myMap = mapReturningFunc();
            //System.out.println("New myMap object: " + myMap);
        }
    }
}
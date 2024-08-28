/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.test.misc;

import hdt.test.BaseHdtTest;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author sral
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ScriptingTest extends BaseHdtTest {

    @Test
    public void scriptingTest() throws ScriptException {
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("JavaScript");

        Integer gr_cells = 12000;
        Integer feature1 = 1;
        Integer feature2 = 0;
        Double load_generic = 2.5;
        Double load_factor1 = 0.005;
        Double load_factor2 = 0.006;

        String formula = "var MS_CPU  = 0.0;\n if (feature1 == 1) {MS_CPU = load_generic * Math.exp(load_factor1 * gr_cells); STRING1='hello';} "
                + "else {MS_CPU = load_generic * Math.exp(load_factor1 * gr_cells); }";

        //String formula = "var ret_val = 0.0; if (feature1 == 1) {ret = load_factor1;} "
        //        + "else {ret = load_factor2; } ret_val;";
       
        engine.put("gr_cells", gr_cells);
        engine.put("feature1", feature1);
        engine.put("feature2", feature2);
        engine.put("load_generic", load_generic);
        engine.put("load_factor1", load_factor1);
        //engine.eval("load_factor = null;");
        //engine.put("load_factor1", null);
        engine.put("load_factor2", load_factor2);
        engine.put("formula", formula);
        Object result = engine.eval(formula);
        System.out.println("Calculation result: " + engine.get("MS_CPU"));
        System.out.println("String value: " + engine.get("STRING1"));        
    }
}

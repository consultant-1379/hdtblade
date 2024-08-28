/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.test.misc;

import hdt.test.BaseHdtTest;
import java.util.ArrayList;
import java.util.List;
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
public class ScriptingTest_ObjectReturn extends BaseHdtTest {

    private List<String> depList = new ArrayList<String>();

    public class JSHelper {

        private List<String> depList;        

        public JSHelper() {
            this.depList = new ArrayList<String>();
        }

        public void addDependency(String v) {
            this.depList.add(v);
        }
        
        public List<String> getDepList() {
            return depList;
        }
    }

    class JSRetObject {

        private String strVal;
        private Double numVal;

        public Double getNumVal() {
            return numVal;
        }

        public void setNumVal(Double numVal) {
            this.numVal = numVal;
        }

        public String getStrVal() {
            return strVal;
        }

        public void setStrVal(String strVal) {
            this.strVal = strVal;
        }
    }

    @Test
    public void scriptingTest() throws ScriptException {
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("JavaScript");
        JSRetObject retObj = new JSRetObject();

        Integer gr_cells = 12000;
        Integer feature1 = 1;
        Integer feature2 = 0;
        Double load_generic = 2.5;
        Double load_factor1 = 0.005;
        Double load_factor2 = 0.006;


        String script = "var MS_CPU  = 0.0;\n "
                + "if (feature1 == 1) {"
                + "MS_CPU = load_generic * Math.exp(load_factor1 * gr_cells); "
                + "STRING1='hello';"
                + "} else {"
                + "MS_CPU = load_generic * Math.exp(load_factor1 * gr_cells);"
                + "} "
                + "if (typeof(PARAM_1) == 'undefined') {"
                + "JSHelper.addDependency('PARAM_1');"
                + "throw 'MyException'"
                + " ;"
                + "}"
                + "if (typeof(feature2) == 'undefined') {"
                + "JSHelper.addDependency('feature2');"
                + "}"
                + "retObj.strVal=\"fucker\"; retObj.numVal=2.5;";
//                + "if (typeof PARAM_1 != 'undefined') {"
//                + "list.add(\"PARAM_1\");"
//                + "}";

        engine.put("gr_cells", gr_cells);
        engine.put("feature1", feature1);
        engine.put("feature2", feature2);
        engine.put("load_generic", load_generic);
        engine.put("load_factor1", load_factor1);
        //engine.eval("load_factor = null;");
        //engine.put("load_factor1", null);
        engine.put("load_factor2", load_factor2);
        engine.put("formula", script);
        engine.put("retObj", retObj);

        JSHelper h = new JSHelper();

        engine.put("JSHelper", h);

        try {
            Object result = engine.eval(script);
        } catch (javax.script.ScriptException e) {
            String name = e.toString();
            if (name.contains("MyException")) {
                System.out.println("My Exception was thrown.");
            } else {
                e.printStackTrace();
            }
        }

        System.out.println("Calculation result: " + engine.get("MS_CPU"));
        System.out.println("String value: " + engine.get("STRING1"));
        System.out.println("Result object string: " + retObj.strVal);
        System.out.println("Result object num value: " + retObj.numVal);       
        System.out.println("External object list value: " + h.getDepList());
    }
}

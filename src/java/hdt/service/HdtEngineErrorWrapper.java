/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author escralp
 */
public class HdtEngineErrorWrapper {

    /**
     * Error definitions.
     */
    public static enum HdtEngineErrorEnum {

        HDT_ERR_INTERNAL,
        HDT_ERR_NO_PARAMS,
        HDT_ERR_NO_MAIN_PARAMS,
        HDT_ERR_NO_VARIABLES,
        HDT_ERR_NO_FORMULA,
        HDT_ERR_NO_VARIABLE_RESULT,
        HDT_ERR_JS,
        HDT_ERR_NO_HW_RESULT,
        HDT_ERR_NO_APP_RESULT,
        HDT_ERR_NO_VARIABLE,
        HDT_ERR_DEPENDENCY_VARIABLE,
        HDT_ERR_DEPENDENCY_APP,
        HDT_ERR_DEPENDENCY_APP_NOT_EXPOSED,
        HDT_ERR_VARIABLE_UNDEF_TYPE;
        private static final Map<Enum, String> HDT_ERROR_MAPPING;

        static {
            Map<Enum, String> tempMap = new HashMap<Enum, String>();

            tempMap.put(HDT_ERR_INTERNAL, "Internal HDT Engine Error. Check HDT logs.");
            tempMap.put(HDT_ERR_NO_PARAMS, "No parameters defined / found. Check parameters and parameter value mappings.");
            tempMap.put(HDT_ERR_NO_MAIN_PARAMS, "No main dimensioning parameters defined / found. Check parameters and parameter value mappings.");
            tempMap.put(HDT_ERR_NO_VARIABLES, "No variables defined / found. Check variable mapping.");
            tempMap.put(HDT_ERR_NO_FORMULA, "No formula code defined / found. Check formula mapping.");
            tempMap.put(HDT_ERR_JS, "JavaScript Engine error, check JavaScript code.");
            tempMap.put(HDT_ERR_NO_VARIABLE_RESULT, "Calculation yielded -1.0, check formula.");
            tempMap.put(HDT_ERR_NO_HW_RESULT, "No hardware lookup result found, check hardware result mapping.");
            tempMap.put(HDT_ERR_NO_APP_RESULT, "No APP number lookup result found, check APP number result mapping.");
            tempMap.put(HDT_ERR_NO_VARIABLE, "Formula code found but no result variable passed back in formula code.");
            tempMap.put(HDT_ERR_DEPENDENCY_VARIABLE, "Error resolving variable dependency.");
            tempMap.put(HDT_ERR_DEPENDENCY_APP, "Error resolving APP dependency.");
            tempMap.put(HDT_ERR_DEPENDENCY_APP_NOT_EXPOSED, "Error resolving APP dependency: APP Number not exposed.");
            tempMap.put(HDT_ERR_VARIABLE_UNDEF_TYPE, "Variable has undefined return type or no return value, check formula code.");

            // Make this map impossible to modify later on.
            HDT_ERROR_MAPPING = Collections.unmodifiableMap(tempMap);
        }

        public String getMyDescription() {
            return HDT_ERROR_MAPPING.get(this);
        }
    };
}

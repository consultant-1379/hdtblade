/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hdt.service;

/**
 * This class is never instantiated and contains
 * various generic constants used in the system.
 *
 * @author Ralph Schlosser
 * @version 0.1
 * <p>FIXME: The Version should come from CVS.</p>
 * <p>date 26/10/2010</p>
 */
public class HdtConstants {

    // Some generic constants.  
    public static final String HDT_DATASOURCE_NAME = "java:comp/env/jdbc/hdtDatasource";   

    public static final String HDT_TYPE_DOUBLE = "Double";
    public static final String HDT_TYPE_INTEGER = "Integer";
    public static final String HDT_TYPE_BOOLEAN = "Boolean";
    public static final String HDT_TYPE_STRING = "String";

    public static final String HDT_ANY_ROLE_CTX = "ANY_ROLE";
    public static final String HDT_ANY_NETWORK_CTX = "ANY_NETWORK";
    public static final String HDT_ANY_SYSTEM_CTX = "ANY_SYSTEM";
    public static final String HDT_ANY_BUNDLE_CTX = "UNDEFINED";

    public static final String HDT_JS_MAIN_VARIABLE = "HDT_THIS";
    public static final String HDT_JS_VAR_DEPENDENCY_EXCEPTION = "VariableDependencyException";
    public static final String HDT_JS_APP_DEPENDENCY_EXCEPTION = "APPDependencyException";

    public static final String HDT_ENV_PRODUCT_VAL = "SELECTED_PRODUCT";
    public static final String HDT_ENV_PRODUCT_DESC = "Contains the selected product. Environment Parameter, cannot be changed.";

    public static final String HDT_ENV_NETWORK_VAL = "SELECTED_NETWORK";
    public static final String HDT_ENV_NETWORK_DESC = "Contains the selected network. Environment Parameter, cannot be changed.";

    public static final String HDT_INTERNAL_WARNING_CHANGED_PARAMETERS = "One or more Dimensioning Parameters have changed.";
    public static final String HDT_INTERNAL_WARNING_CHANGED_QUANTITY = "One or more Quantities have changed.";

    public static final int HDT_ENGINE_MAX_PASSES = 5;
    public static final Double HDT_APP_DEPENDENCY_INVALID_CONTEXT_VAL = -1.0;

    // Note: Using explicit ordering instead of ordinal() in case the individual enum items will get reordered.
    // Declare visibility classes for certain domain objects.
    // Used internally and in the JavaScript rendering code.
    public static enum Visibility {
        VISIBILITY_NONE(0),                 // The parameter is invisible
        VISIBILITY_MAIN_PARAM(1),           // This is a main parameter, it will be shown on the 1st page.
        VISIBILITY_MAIN_CONST(4),           // Parameter will be shown on 1st page but cannot be changed (constant).
        VISIBILITY_DEFAULT(2),              // Parameter is not main parameter
        VISIBILITY_ENGINE(3);               // Internal parameter only visible to the engine.

        private final int visibility;
        Visibility(int vis) {
            this.visibility = vis;
        }

        public String getVisibilityAsStr() {
            return Integer.toString(visibility);
        }

        public int getVisibilityAsInt() {
            return visibility;
        }
    }

    // These are used to set / determine the internal engine state.
    public static enum EngineState {
        CREATED,                            // Engine class has been instatiated.
        CONFIGURED,                         // Engine has been configured with initial inputs.
        INITIALISED,                        // Engine has been initialised & context parameters / variables initialised.
        CALCULATED
    }
}

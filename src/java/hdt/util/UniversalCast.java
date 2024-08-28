/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.util;

/**
 *
 * @author escralp
 */
public class UniversalCast {

    /**
     * NOTE: This is pretty tricky.  This casts a variable of type T to type X.
     * X must be a subclass of T, i.e. T is the more generic type, such as T being
     * a java.lang.Object and X a java.lang.String.
     *
     * Also c.f. http://stackoverflow.com/questions/509076/how-do-i-address-unchecked-cast-warnings
     * 
     */
    @SuppressWarnings("unchecked")
    public static <T, X extends T> X cast(T o) {
        // This performs the type cast.
        return (X) o;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.test;

/**
 *
 * @author escralp
 */
public interface MyLogger<T extends Object> {

    public static enum LogLevel {

        MAIN,
        SUB1,
        SUB2,
        SUB3,
        SUB4
    }

    public void setLevel(LogLevel lvl);

    public void log(LogLevel lvl, String logString);

    public void executeIf(LogLevel lvl, Executor ex);
}

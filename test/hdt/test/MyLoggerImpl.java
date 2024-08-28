/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.test;

import hdt.test.MyLogger.LogLevel;

/**
 *
 * @author escralp
 */
public class MyLoggerImpl implements MyLogger {

    private LogLevel myLevel;

    public MyLoggerImpl() {
        this.myLevel = LogLevel.MAIN;
    }

    @Override
    public void log(LogLevel lvl, String logString) {
        if (lvl.ordinal() <= myLevel.ordinal()) {
            System.out.println(logString);
        }
    }

    @Override
    public void setLevel(LogLevel lvl) {
        this.myLevel = lvl;
    }

    @Override
    public void executeIf(LogLevel lvl, Executor ex) {
        if (lvl.ordinal() <= myLevel.ordinal()) {
            ex.exec();
        }
    }
}

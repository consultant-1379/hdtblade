/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hdt.dao.impl;

import hdt.dao.HdtAppInfo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 *
 * @author escralp
 */
@Component
public class HdtAppInfoPropImpl implements HdtAppInfo {
    // Initialisation is taken care of by SPRING.
    private @Value("#{hdtAppInfoProp['program.VERSION']}") String version;
    private @Value("#{hdtAppInfoProp['program.BUILDNUM']}") String buildNum;
    private @Value("#{hdtAppInfoProp['program.BUILDDATE']}") String buildDate;

    public HdtAppInfoPropImpl() {        
    }

    @Override
    public String getHdtVersion() {
        return this.version;
    }
    
    @Override
    public String getHdtBuildNum() {
        return buildNum;
    }

    @Override
    public String getHdtBuildDate() {
        return buildDate;
    }
}

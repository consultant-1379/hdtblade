/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.dao;

import java.io.Serializable;

/**
 *
 * @author escralp
 */
public interface HdtDbConfigDao extends Serializable {
   
    public String getDbVersion();
}

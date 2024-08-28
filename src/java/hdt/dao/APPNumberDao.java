/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.dao;

import hdt.domain.Alternatives;
import hdt.domain.HardwareConfig;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author escralp
 */
public interface APPNumberDao extends Serializable {

    public Alternatives convertHardwareConfigToAPPNumbers(List<HardwareConfig> hwConfigList);
}

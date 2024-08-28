/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.domain;

import hdt.domain.APPAlternative.HardwareStatus;
import java.util.List;

/**
 *
 * @author escralp
 */
public class HardwareConfig extends HdtItem {

    private int hwConfigRevision;
    private APPAlternative.HardwareStatus dbHwStatus;    
      
    public HardwareConfig(String hardwareConfigName, String description) {
        super(hardwareConfigName, description);
    }

    public int getHwConfigRevision() {
        return hwConfigRevision;
    }

    public void setHwConfigRevision(int hwConfigRevision) {
        this.hwConfigRevision = hwConfigRevision;
    }

    public HardwareStatus getDbHwStatus() {
        return dbHwStatus;
    }

    public void setDbHwStatus(int status) {               
        this.dbHwStatus = APPAlternative.HardwareStatus.fromInt(status);
    }        
    
    //
    // This fuses the APP number info together with the HW config info.
    //
    public APPAlternative getAPPAlternativeMapping(List<APPNumber> altList) {
        APPAlternative alt = new APPAlternative(altList, hwConfigRevision, this.getName(), this.getDescription());
        alt.setHwStatus(dbHwStatus);
        
        return alt;
    }

    /**
     * Only for debugging purposes.
     */
    public void dumpValues() {
        System.out.println("Hardware Config Name: " + this.getName());
        System.out.println("Hardware Config Description: " + this.getDescription());
        System.out.println("Hardware Config Revision: " + this.getHwConfigRevision());
        System.out.println("Hardware Config DB Status: " + this.getDbHwStatus());
    }
}

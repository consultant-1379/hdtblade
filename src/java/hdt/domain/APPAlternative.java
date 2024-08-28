/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author escralp
 */
public class APPAlternative extends HdtItem {

    public static enum HardwareStatus {
        
        HW_STATUS_UNDEFINED(-1),
        HW_STATUS_NORMAL(0),
        HW_STATUS_EOL(1),
        HW_STATUS_DEPRECATED(2);               
        
        private final int hwStatus;
        private static final Map<Integer, HardwareStatus> mapping = new HashMap<Integer, HardwareStatus>();
        
        //
        // Define static lookup table to convert between int and HardwareStatus.
        //
        static {            
            for (HardwareStatus type : HardwareStatus.values()) {
                mapping.put(type.hwStatus, type);
            }
        }
        
        HardwareStatus(int status) {
            this.hwStatus = status;
        }
                
        public static HardwareStatus fromInt(int value) {
            HardwareStatus retVal = mapping.get(value);
            
            // If we can't map it, it's undefined.
            if (retVal == null) {
                retVal = HW_STATUS_UNDEFINED;
            }
            
            return retVal;
        }
        
    };
    private List<APPNumber> alternative;
    private HardwareStatus hwStatus;
    private int hwRevision = -1;
            
    public APPAlternative(List<APPNumber> alternative, int hwRevision, String name, String description) {
        super(name, description);
        
        this.alternative = alternative;
        this.hwRevision = hwRevision;        
    }

    public List<APPNumber> getAlternative() {
        return alternative;
    }

    public int getHwRevision() {
        return hwRevision;
    }

    public HardwareStatus getHwStatus() {
        return hwStatus;
    }

    public void setHwStatus(HardwareStatus status) {
        this.hwStatus = status;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.dto;

import hdt.service.HdtEngineErrorWrapper.HdtEngineErrorEnum;
import java.io.Serializable;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 *
 * @author escralp
 */
@JsonIgnoreProperties({"error"})
public class HdtEngineError implements Serializable {

    private HdtEngineErrorEnum error;
    private String errorDescription;
    private String additionalInfo;

    /**
     * Stores the information in the object so we can access them through JavaScript in the page rendering.
     * 
     * @param error
     * @param info
     */
    public HdtEngineError(HdtEngineErrorEnum error, String info) {
        this.error = error;
        this.errorDescription = error.getMyDescription();
        if (info == null) {
            this.additionalInfo = "None";
        } else {
            this.additionalInfo = info;
        }
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public String getErrorDescription() {
        return errorDescription;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hdt.dto;

import hdt.domain.Parameter;
import java.util.List;
import java.util.Map;

/**
 *  Utility class for Java object ---> JSON conversion.
 * 
 *  NOTE: Making the members public for ease of access.
 * 
 * @author escralp
 */
public class ParameterDataJSON {
     public Map<String, List<Parameter>> mainDimParMap;     
}

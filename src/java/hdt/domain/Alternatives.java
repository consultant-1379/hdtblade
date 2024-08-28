/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.domain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author escralp
 */
public class Alternatives {

    private List<APPAlternative> alternatives;
    int selectedAlternativeIdx;
    boolean containsEmpty;

    public Alternatives() {
        this.containsEmpty = false;
        // First element is the selected one by default.
        this.selectedAlternativeIdx = 0;
        this.alternatives = new ArrayList<APPAlternative>();
    }

    public void add(List<APPNumber> altList, HardwareConfig hwConfig) {
        APPAlternative alt = hwConfig.getAPPAlternativeMapping(altList);
        this.alternatives.add(alt);
    }

    public List<APPAlternative> getAlternatives() {
        return alternatives;
    }

    public APPAlternative getSelectedAlternative() {
        if (!alternatives.isEmpty() && selectedAlternativeIdx != -1) {
            return alternatives.get(selectedAlternativeIdx);
        } else {
            return null;
        }
    }

    public boolean getContainsEmpty() {
        return containsEmpty;
    }

    public void setContainsEmpty(boolean val) {
        this.containsEmpty = val;
    }

    public void setSelectedAlternativeIdx(int idx) {
        this.selectedAlternativeIdx = idx;
    }

    public int getSelectedAlternativeIdx() {
        return selectedAlternativeIdx;
    }
}

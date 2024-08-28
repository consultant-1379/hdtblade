/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.domain;

/**
 *
 * @author escralp
 */
public class Note extends NamedType {

    private String noteText;
    private boolean enabled;

    public Note(String noteText, boolean enabled) {
        this.noteText = noteText;
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getNoteText() {
        return noteText;
    }
}

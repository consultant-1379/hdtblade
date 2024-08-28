/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.dao;

import hdt.domain.Note;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author escralp
 */
public interface NoteDao extends Serializable {

    public List<Note> getNotes();
}

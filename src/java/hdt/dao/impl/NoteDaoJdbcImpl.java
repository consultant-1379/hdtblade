/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.dao.impl;

import hdt.dao.NoteDao;
import hdt.domain.Note;
import hdt.service.HdtQueries;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author escralp
 */
@Repository
public class NoteDaoJdbcImpl extends BaseDaoJdbcImpl implements NoteDao {

    private final JdbcTemplate jdbcTemplate;

    private static final class NoteMapper implements RowMapper<Note> {

        @Override
        public Note mapRow(ResultSet rs, int rowNum) throws SQLException {
            Note note = new Note(rs.getString("note_text"), rs.getBoolean("note_shown"));
            return note;
        }
    }

    public NoteDaoJdbcImpl() throws ServletException {
        super();

        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Note> getNotes() {
        return this.jdbcTemplate.query(HdtQueries.GET_NOTES_SQL, new NoteMapper());
    }
}

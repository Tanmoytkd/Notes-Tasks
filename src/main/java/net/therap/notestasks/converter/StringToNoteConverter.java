package net.therap.notestasks.converter;

import net.therap.notestasks.domain.Note;
import net.therap.notestasks.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author tanmoy.das
 * @since 5/4/20
 */
@Component
public class StringToNoteConverter implements Converter<String, Note> {

    @Autowired
    private NoteService noteService;

    @Override
    public Note convert(String id) {
        long noteId = Long.parseLong(id);
        return noteService.findNoteById(noteId).orElse(null);
    }
}

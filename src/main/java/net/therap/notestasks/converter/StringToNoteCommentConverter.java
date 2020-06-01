package net.therap.notestasks.converter;

import net.therap.notestasks.domain.NoteComment;
import net.therap.notestasks.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author tanmoy.das
 * @since 5/4/20
 */
@Component
public class StringToNoteCommentConverter implements Converter<String, NoteComment> {

    @Autowired
    private NoteService noteService;

    @Override
    public NoteComment convert(String id) {
        long noteCommentId = Long.parseLong(id);
        return noteService.findNoteCommentById(noteCommentId).orElse(null);
    }
}

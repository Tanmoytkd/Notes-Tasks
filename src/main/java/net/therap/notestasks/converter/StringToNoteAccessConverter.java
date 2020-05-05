package net.therap.notestasks.converter;

import net.therap.notestasks.domain.NoteAccess;
import net.therap.notestasks.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author tanmoy.das
 * @since 5/4/20
 */
@Component
public class StringToNoteAccessConverter implements Converter<String, NoteAccess> {

    @Autowired
    private NoteService noteService;

    @Override
    public NoteAccess convert(String id) {
        long noteAccessId = Long.parseLong(id);
        return noteService.findNoteAccessById(noteAccessId).orElse(null);
    }
}

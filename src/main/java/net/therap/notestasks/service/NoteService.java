package net.therap.notestasks.service;

import net.therap.notestasks.dao.NoteAccessDao;
import net.therap.notestasks.dao.NoteCommentDao;
import net.therap.notestasks.dao.NoteDao;
import net.therap.notestasks.dao.UserDao;
import net.therap.notestasks.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author tanmoy.das
 * @since 4/24/20
 */
@Service
@Transactional
public class NoteService {

    @Autowired
    private NoteDao noteDao;

    @Autowired
    private NoteCommentDao noteCommentDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private NoteAccessDao noteAccessDao;

    public void createNote(Note note) {
        User writer = note.getWriter();
        writer.getOwnNotes().add(note);

        noteDao.saveOrUpdate(note);
        userDao.saveOrUpdate(writer);
    }

    public void updateNote(Note note) {
        noteDao.saveOrUpdate(note);
    }

    public void deleteNote(Note note) {
        User writer = note.getWriter();
        writer.getOwnNotes().remove(note);
        userDao.saveOrUpdate(writer);

        note.getComments().forEach(this::deleteNoteComment);

        note.getNoteAccesses().forEach(this::deleteNoteAccess);

        noteDao.delete(note);
    }

    public void createNoteComment(NoteComment noteComment) {
        noteCommentDao.saveOrUpdate(noteComment);

        Note note = noteComment.getNote();
        note.getComments().add(noteComment);
        noteDao.saveOrUpdate(note);

        User writer = noteComment.getWriter();
        writer.getNoteComments().add(noteComment);
        userDao.saveOrUpdate(writer);
    }

    public void updateNoteComment(NoteComment noteComment) {
        noteCommentDao.saveOrUpdate(noteComment);
    }

    public void deleteNoteComment(NoteComment noteComment) {
        Note note = noteComment.getNote();
        note.getComments().remove(noteComment);
        noteDao.saveOrUpdate(note);

        User writer = noteComment.getWriter();
        writer.getNoteComments().remove(noteComment);
        userDao.saveOrUpdate(writer);

        noteCommentDao.delete(noteComment);
    }

    public void createNoteAccess(NoteAccess noteAccess) {
        noteAccessDao.saveOrUpdate(noteAccess);

        Note note = noteAccess.getNote();
        note.getNoteAccesses().add(noteAccess);
        noteAccessDao.saveOrUpdate(noteAccess);

        User user = noteAccess.getUser();
        user.getSharedNoteAccesses().add(noteAccess);
        userDao.saveOrUpdate(user);
    }

    public void updateNoteAccess(NoteAccess noteAccess) {
        noteAccessDao.saveOrUpdate(noteAccess);
    }

    public void deleteNoteAccess(NoteAccess noteAccess) {
        Note note = noteAccess.getNote();
        note.getNoteAccesses().remove(noteAccess);
        noteDao.saveOrUpdate(note);

        User user = noteAccess.getUser();
        user.getSharedNoteAccesses().remove(noteAccess);
        userDao.saveOrUpdate(user);

        noteAccessDao.delete(noteAccess);
    }

    public Optional<Note> findNoteById(long noteId) {
        return noteDao.find(noteId);
    }

    public boolean canAccessNote(User persistedCurrentUser, Note note) {
        if (persistedCurrentUser.getId() == note.getWriter().getId()) {
            return true;
        }

        return note.getNoteAccesses().stream()
                .filter(noteAccess -> !noteAccess.isDeleted())
                .filter(noteAccess -> noteAccess.getUser().getId() == persistedCurrentUser.getId())
                .anyMatch(noteAccess -> !noteAccess.getAccessLevels().isEmpty());
    }

    public boolean hasSpecificAccess(User persistedCurrentUser, Note note, AccessLevel accessLevel) {
        if (persistedCurrentUser.getId() == note.getWriter().getId()) {
            return true;
        }

        return note.getNoteAccesses().stream()
                .filter(noteAccess -> !noteAccess.isDeleted())
                .filter(noteAccess -> noteAccess.getUser().getId() == persistedCurrentUser.getId())
                .anyMatch(noteAccess -> noteAccess.getAccessLevels().contains(accessLevel));
    }

    public boolean hasReadAccess(User persistedCurrentUser, Note note) {
        return hasSpecificAccess(persistedCurrentUser, note, AccessLevel.READ);
    }

    public boolean hasWriteAccess(User persistedCurrentUser, Note note) {
        return hasSpecificAccess(persistedCurrentUser, note, AccessLevel.WRITE);
    }

    public boolean hasShareAccess(User persistedCurrentUser, Note note) {
        return hasSpecificAccess(persistedCurrentUser, note, AccessLevel.SHARE);
    }

    public boolean hasDeleteAccess(User persistedCurrentUser, Note note) {
        return hasSpecificAccess(persistedCurrentUser, note, AccessLevel.DELETE);
    }

    public boolean hasCommentDeleteAccess(User user, NoteComment noteComment) {
        User persistedUser = userDao.findByEmail(user.getEmail()).orElse(null);

        if (persistedUser.getId() == noteComment.getWriter().getId()) {
            return true;
        }

        return persistedUser.getId() == noteComment.getNote().getWriter().getId();
    }

    public Optional<NoteComment> findNoteCommentById(long noteCommendId) {
        return noteCommentDao.find(noteCommendId);
    }

    public boolean canDeleteNoteAccess(User persistedCurrentUser, NoteAccess noteAccess) {
        if (hasShareAccess(persistedCurrentUser, noteAccess.getNote())) {
            return true;
        }

        return noteAccess.getUser().getId() == persistedCurrentUser.getId();
    }

    public Optional<NoteAccess> findNoteAccessById(long noteAccessId) {
        return noteAccessDao.find(noteAccessId);
    }
}

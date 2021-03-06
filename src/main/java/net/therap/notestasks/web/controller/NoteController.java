package net.therap.notestasks.web.controller;

import net.therap.notestasks.command.SearchQuery;
import net.therap.notestasks.domain.*;
import net.therap.notestasks.service.NoteService;
import net.therap.notestasks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static net.therap.notestasks.helper.UrlHelper.getUrl;
import static net.therap.notestasks.helper.UrlHelper.redirectTo;
import static net.therap.notestasks.util.Constants.*;

/**
 * @author tanmoy.das
 * @since 5/3/20
 */
@Controller
@SessionAttributes(NOTE_COMMENT_COMMAND_NAME)
public class NoteController {

    private static final String NOTES_PAGE = "notes";
    @Autowired
    private UserService userService;

    @Autowired
    private NoteService noteService;

    @RequestMapping(value = {"/notes"}, method = RequestMethod.GET)
    public String showNotes(@SessionAttribute(CURRENT_USER_LABEL) User currentUser,
                            ModelMap model, HttpServletRequest req, HttpServletResponse resp) {
        model.addAttribute("searchQuery", new SearchQuery());
        model.addAttribute("isNotesPage", true);

        User persistedCurrentUser = userService.findUserWithSameEmail(currentUser);

        List<Note> ownNotes = persistedCurrentUser.getOwnNotes().stream()
                .filter(note -> !note.isDeleted())
                .sorted(Comparator.comparing(BasicEntity::getUpdatedOn))
                .collect(Collectors.toList());
        Collections.reverse(ownNotes);
        model.addAttribute("ownNotes", ownNotes);

        List<NoteAccess> sharedNoteAccesses = persistedCurrentUser.getSharedNoteAccesses().stream()
                .filter(noteAccess -> !noteAccess.isDeleted())
                .sorted(Comparator.comparing(noteAccess -> noteAccess.getNote().getUpdatedOn()))
                .collect(Collectors.toList());
        model.addAttribute("sharedNoteAccesses", sharedNoteAccesses);

        return NOTES_PAGE;
    }

    @RequestMapping(value = {"/note/new"}, method = RequestMethod.GET)
    public String createNote(@SessionAttribute(CURRENT_USER_LABEL) User currentUser) {
        User persistedCurrentUser = userService.findUserWithSameEmail(currentUser);

        Note note = new Note();
        note.setWriter(persistedCurrentUser);
        note.setTitle("Untitled Note");
        note.setContent(new NoteContent());

        noteService.createNote(note);

        persistedCurrentUser.getOwnNotes().add(note);
        return redirectTo(getUrl(note));
    }

    @RequestMapping(value = {"/note/update"}, method = RequestMethod.POST)
    public String updateNote(@Valid @ModelAttribute("noteCommand") Note note, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            //TODO: SHOW ERRORS
            return redirectTo(getUrl(note));
        }

        noteService.updateNote(note);

        return redirectTo(getUrl(note));
    }

    @RequestMapping(value = {"/noteAccess"}, method = RequestMethod.POST)
    public String createNoteAccess(@Valid @ModelAttribute("noteAccessCommand") NoteAccess noteAccess,
                                   Errors errors,
                                   @SessionAttribute(CURRENT_USER_LABEL) User currentUser,
                                   ModelMap model,
                                   HttpServletRequest req, HttpServletResponse resp) {
        if (errors.hasErrors()) {
            String notePage = showNote(noteAccess.getNote(), currentUser, model, req, resp);
            model.addAttribute("noteAccessCommand", noteAccess);
            return notePage;
        }

        noteService.createNoteAccess(noteAccess);

        return redirectTo(getUrl(noteAccess.getNote()));
    }

    @RequestMapping(value = {"/note/delete/{noteId}"}, method = RequestMethod.GET)
    public String deleteNote(@PathVariable("noteId") Note note,
                             @SessionAttribute(CURRENT_USER_LABEL) User currentUser) {
        User persistedCurrentUser = userService.findUserWithSameEmail(currentUser);

        if (!noteService.hasDeleteAccess(persistedCurrentUser, note)) {
            return redirectTo(NOTES_PAGE_PATH);
        }

        noteService.deleteNote(note);
        return redirectTo(NOTES_PAGE_PATH);
    }

    @RequestMapping(value = {"/note/{noteId}"}, method = RequestMethod.GET)
    public String showNote(@PathVariable("noteId") Note note,
                           @SessionAttribute(CURRENT_USER_LABEL) User currentUser,
                           ModelMap model, HttpServletRequest req, HttpServletResponse resp) {

        User persistedCurrentUser = userService.findUserWithSameEmail(currentUser);

        if (!noteService.canAccessNote(persistedCurrentUser, note)) {
            return redirectTo(NOTES_PAGE_PATH);
        }

        setupModelNotePermissions(note, model, persistedCurrentUser);

        model.addAttribute("noteCommand", note);

        List<NoteComment> comments = note.getComments().stream()
                .filter(noteComment -> !noteComment.isDeleted())
                .collect(Collectors.toList());
        model.addAttribute("noteComments", comments);

        List<NoteAccess> noteAccesses = note.getNoteAccesses().stream()
                .filter(noteAccess -> !noteAccess.isDeleted())
                .sorted(Comparator.comparing(NoteAccess::getUpdatedOn))
                .collect(Collectors.toList());
        model.addAttribute("noteAccesses", noteAccesses);

        model.addAttribute("noteService", noteService);

        NoteComment noteComment = new NoteComment();
        noteComment.setNote(note);
        noteComment.setWriter(currentUser);
        model.addAttribute(NOTE_COMMENT_COMMAND_NAME, noteComment);

        NoteAccess noteAccess = new NoteAccess();
        noteAccess.setNote(note);
        model.addAttribute("noteAccessCommand", noteAccess);

        List<User> connectedUsers = userService.findConnectedUsers(persistedCurrentUser);
        model.addAttribute("connectedUsers", connectedUsers);

        return showNotes(currentUser, model, req, resp);
    }

    private void setupModelNotePermissions(@PathVariable("noteId") Note note, ModelMap model, User persistedCurrentUser) {
        boolean hasReadAccess = noteService.hasReadAccess(persistedCurrentUser, note);
        model.addAttribute("hasReadAccess", hasReadAccess);

        boolean hasWriteAccess = noteService.hasWriteAccess(persistedCurrentUser, note);
        model.addAttribute("hasWriteAccess", hasWriteAccess);

        boolean hasShareAccess = noteService.hasShareAccess(persistedCurrentUser, note);
        model.addAttribute("hasShareAccess", hasShareAccess);

        boolean hasDeleteAccess = noteService.hasDeleteAccess(persistedCurrentUser, note);
        model.addAttribute("hasDeleteAccess", hasDeleteAccess);
    }

    @RequestMapping(value = {"/noteComment"}, method = RequestMethod.POST)
    public String createNoteComment(@Valid @ModelAttribute(NOTE_COMMENT_COMMAND_NAME) NoteComment noteComment,
                                    Errors errors,
                                    @SessionAttribute(CURRENT_USER_LABEL) User currentUser,
                                    ModelMap model, HttpServletRequest req, HttpServletResponse resp) {
        if (errors.hasErrors()) {
            String notePage = showNote(noteComment.getNote(), currentUser, model, req, resp);
            model.addAttribute(NOTE_COMMENT_COMMAND_NAME, noteComment);
            return notePage;
        }

        noteService.createNoteComment(noteComment);
        return redirectTo(getUrl(noteComment.getNote()));
    }

    @RequestMapping(value = {"/noteComment/delete/{noteCommentId}"}, method = RequestMethod.GET)
    public String deleteNoteComment(@PathVariable("noteCommentId") NoteComment noteComment,
                                    @SessionAttribute(CURRENT_USER_LABEL) User currentUser,
                                    ModelMap model, HttpServletRequest req, HttpServletResponse resp) {
        User persistedCurrentUser = userService.findUserWithSameEmail(currentUser);

        if (noteService.hasCommentDeleteAccess(persistedCurrentUser, noteComment)) {
            noteService.deleteNoteComment(noteComment);
        }

        return redirectTo(getUrl(noteComment.getNote()));
    }

    @RequestMapping(value = {"/noteAccess/delete/{noteAccessId}"}, method = RequestMethod.GET)
    public String deleteNoteAccess(@PathVariable("noteAccessId") NoteAccess noteAccess,
                                   @SessionAttribute(CURRENT_USER_LABEL) User currentUser,
                                   ModelMap model, HttpServletRequest req, HttpServletResponse resp) {
        User persistedCurrentUser = userService.findUserWithSameEmail(currentUser);

        if (noteService.canDeleteNoteAccess(persistedCurrentUser, noteAccess)) {
            noteService.deleteNoteAccess(noteAccess);
        }

        return redirectTo(NOTES_PAGE_PATH);
    }

}

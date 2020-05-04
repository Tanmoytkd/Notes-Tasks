package net.therap.notestasks.web.controller;

import net.therap.notestasks.command.SearchQuery;
import net.therap.notestasks.domain.*;
import net.therap.notestasks.service.NoteService;
import net.therap.notestasks.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

import static net.therap.notestasks.config.Constants.CURRENT_USER;
import static net.therap.notestasks.config.Constants.REDIRECT_NOTES;
import static net.therap.notestasks.helper.UrlHelper.getUrl;
import static net.therap.notestasks.helper.UrlHelper.redirectTo;

/**
 * @author tanmoy.das
 * @since 5/3/20
 */
@Controller
public class NoteController {

    @Autowired
    private UserService userService;

    @Autowired
    private NoteService noteService;

    @Autowired
    private Logger logger;

    @RequestMapping(value = {"/notes"}, method = RequestMethod.GET)
    public String showNotes(@SessionAttribute(CURRENT_USER) User currentUser,
                            ModelMap model, HttpServletRequest req, HttpServletResponse resp) {
        model.addAttribute("searchQuery", new SearchQuery());
        model.addAttribute("isNotesPage", true);

        User persistedCurrentUser = userService.refreshUser(currentUser);
        model.addAttribute(CURRENT_USER, persistedCurrentUser);

        List<Note> ownNotes = persistedCurrentUser.getOwnNotes().stream()
                .filter(note -> !note.isDeleted())
                .collect(Collectors.toList());
        model.addAttribute("ownNotes", ownNotes);

        List<NoteAccess> sharedNoteAccesses = persistedCurrentUser.getSharedNoteAccesses().stream()
                .filter(noteAccess -> !noteAccess.isDeleted())
                .collect(Collectors.toList());
        model.addAttribute("sharedNoteAccesses", sharedNoteAccesses);

        return "dashboard";
    }

    @RequestMapping(value = {"/note/new"}, method = RequestMethod.GET)
    public String createNote(@SessionAttribute(CURRENT_USER) User currentUser) {
        User persistedCurrentUser = userService.refreshUser(currentUser);

        Note note = new Note();
        note.setWriter(persistedCurrentUser);
        note.setTitle("Untitled Note");
        note.setContent(new NoteContent());

        noteService.createNote(note);

        persistedCurrentUser.getOwnNotes().add(note);
        return redirectTo(getUrl(note));
    }

    @RequestMapping(value = {"/note/update"}, method = RequestMethod.POST)
    public String updateNote(@ModelAttribute("currentNoteCommand") Note note) {

        noteService.updateNote(note);

        return redirectTo(getUrl(note));
    }

    @RequestMapping(value = {"/note/delete/{noteId}"}, method = RequestMethod.GET)
    public String deleteNote(@PathVariable("noteId") Note note,
                             @SessionAttribute(CURRENT_USER) User currentUser) {
        User persistedCurrentUser = userService.refreshUser(currentUser);

        noteService.deleteNote(note);

        persistedCurrentUser.getOwnNotes().add(note);
        return REDIRECT_NOTES;
    }

    @RequestMapping(value = {"/note/{noteId}"}, method = RequestMethod.GET)
    public String showNote(@PathVariable("noteId") Note note,
                           @SessionAttribute(CURRENT_USER) User currentUser,
                           ModelMap model, HttpServletRequest req, HttpServletResponse resp) {

        User persistedCurrentUser = userService.refreshUser(currentUser);

        if (!noteService.canAccessNote(persistedCurrentUser, note)) {
            return REDIRECT_NOTES;
        }

        boolean hasReadAccess = noteService.hasReadAccess(persistedCurrentUser, note);
        model.addAttribute("hasReadAccess", hasReadAccess);

        boolean hasWriteAccess = noteService.hasWriteAccess(persistedCurrentUser, note);
        model.addAttribute("hasWriteAccess", hasWriteAccess);

        boolean hasShareAccess = noteService.hasShareAccess(persistedCurrentUser, note);
        model.addAttribute("hasShareAccess", hasShareAccess);

        boolean hasDeleteAccess = noteService.hasDeleteAccess(persistedCurrentUser, note);
        model.addAttribute("hasDeleteAccess", hasDeleteAccess);

        model.addAttribute("currentNoteCommand", note);
        return showNotes(currentUser, model, req, resp);
    }
}

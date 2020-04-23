package net.therap.notestasks.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author tanmoy.das
 * @since 4/22/20
 */
@Entity
public class Note extends BasicEntity {


    @ManyToOne
    @JoinColumn(name = "writer_id")
    private User writer;

    @Enumerated(EnumType.STRING)
    private Privacy privacy;

    @NotEmpty
    @Size(min = 3, max = 100)
    private String title;

    @Embedded
    private NoteContent content;

    @OneToMany(mappedBy = "note", cascade = {CascadeType.ALL})
    List<NoteComment> comments;

    @OneToMany(mappedBy = "note", cascade = {CascadeType.ALL})
    private List<NoteAccess> noteAccesses;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public NoteContent getContent() {
        return content;
    }

    public void setContent(NoteContent content) {
        this.content = content;
    }

    public List<NoteAccess> getNoteAccesses() {
        return noteAccesses;
    }

    public void setNoteAccesses(List<NoteAccess> noteAccesses) {
        this.noteAccesses = noteAccesses;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public Privacy getPrivacy() {
        return privacy;
    }

    public void setPrivacy(Privacy privacy) {
        this.privacy = privacy;
    }

    public List<NoteComment> getComments() {
        return comments;
    }

    public void setComments(List<NoteComment> comments) {
        this.comments = comments;
    }
}

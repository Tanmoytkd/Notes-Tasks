package net.therap.notestasks.domain;

import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * @author tanmoy.das
 * @since 4/22/20
 */
@MappedSuperclass
public class Comment extends BasicEntity {

    @ManyToOne
    @JoinColumn(name = "writer_id")
    private User writer;

    @Embedded
    private CommentContent content;

    public CommentContent getContent() {
        return content;
    }

    public void setContent(CommentContent content) {
        this.content = content;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }
}

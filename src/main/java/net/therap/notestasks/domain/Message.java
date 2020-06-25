package net.therap.notestasks.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author tanmoy.das
 * @since 4/12/20
 */
@NamedQueries({
        @NamedQuery(name = "Message.findAll",
                query = "FROM Message message WHERE message.deleted = false")
})
@Entity
@Table(name = "message")
public class Message extends BasicEntity implements Serializable {

    private static final long serialVersionUID = 1;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @NotNull
    @Embedded
    private MessageContent content;

    @NotNull
    @Column(name = "is_seen")
    private boolean seen;

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public MessageContent getContent() {
        return content;
    }

    public void setContent(MessageContent content) {
        this.content = content;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
}

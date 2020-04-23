package net.therap.notestasks.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author tanmoy.das
 * @since 4/12/20
 */
@Entity
@Table(name = "messages")
public class Message extends BasicEntity {

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
}

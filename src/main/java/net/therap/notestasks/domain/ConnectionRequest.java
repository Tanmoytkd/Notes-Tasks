package net.therap.notestasks.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author tanmoy.das
 * @since 4/12/20
 */
@NamedQueries({
        @NamedQuery(name = "ConnectionRequest.findAll",
                query = "FROM ConnectionRequest request WHERE request.deleted = false"),
        @NamedQuery(name = "ConnectionRequest.findByExample",
                query = "FROM ConnectionRequest request WHERE request.sender = :sender " +
                        "AND request.receiver = :receiver " +
                        "AND request.deleted = false")
})
@Entity
@Table(name = "connection_request")
public class ConnectionRequest extends BasicEntity implements Serializable {

    private static final long serialVersionUID = 1;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

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
}

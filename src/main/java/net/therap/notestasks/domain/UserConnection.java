package net.therap.notestasks.domain;

import javax.persistence.*;
import java.util.List;

/**
 * @author tanmoy.das
 * @since 4/23/20
 */
@NamedQueries({
        @NamedQuery(name = "UserConnection.findByExample",
                query = "FROM UserConnection connection WHERE connection.users = :users " +
                        "AND connection.isDeleted = false"),
        @NamedQuery(name = "UserConnection.findAll",
                query = "FROM UserConnection connection WHERE connection.isDeleted = false")
})

@Entity
@Table(name = "user_connections")
public class UserConnection extends BasicEntity {

    @ManyToMany
    @JoinTable(
            name = "connections",
            joinColumns = @JoinColumn(name = "user_connection_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}

package net.therap.notestasks.domain;

import javax.persistence.*;
import java.util.List;

/**
 * @author tanmoy.das
 * @since 4/23/20
 */
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

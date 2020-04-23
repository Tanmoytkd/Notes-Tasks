package net.therap.notestasks.dao;

import net.therap.notestasks.domain.User;
import org.springframework.stereotype.Repository;

/**
 * @author tanmoy.das
 * @since 4/23/20
 */
@Repository
public class UserDao extends BasicDao<User> {

    protected UserDao() {
        super(User.class);
    }
}

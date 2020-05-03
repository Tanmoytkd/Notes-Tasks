package net.therap.notestasks.converter;

import net.therap.notestasks.domain.User;
import net.therap.notestasks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author tanmoy.das
 * @since 5/4/20
 */
@Component
public class StringToUserConverter implements Converter<String, User> {

    @Autowired
    private UserService userService;

    @Override
    public User convert(String id) {

        long userId = Long.parseLong(id);
        return userService.findUserById(userId).orElse(null);
    }
}

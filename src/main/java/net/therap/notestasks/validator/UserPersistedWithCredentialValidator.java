package net.therap.notestasks.validator;


import net.therap.notestasks.domain.User;
import net.therap.notestasks.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author tanmoy.das
 * @since 5/8/20
 */
@Component
public class UserPersistedWithCredentialValidator implements Validator {

    @Autowired
    private UserService userService;

    @Autowired
    private Logger logger;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if (!userService.findUserByEmailAndPassword(user.getEmail(), user.getPassword()).isPresent()) {
            errors.rejectValue(null, "credentialIncorrect");
        }
    }
}

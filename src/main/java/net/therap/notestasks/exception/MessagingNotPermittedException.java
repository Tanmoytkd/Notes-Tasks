package net.therap.notestasks.exception;

import net.therap.notestasks.domain.User;

/**
 * @author tanmoy.das
 * @since 4/25/20
 */
public class MessagingNotPermittedException extends RuntimeException {
    public MessagingNotPermittedException(User sender, User receiver) {
        super(produceExceptionMessage(sender, receiver));
    }

    private static String produceExceptionMessage(User sender, User receiver) {
        return sender.getName() + " is not permitted to send message to " + receiver.getName();
    }
}

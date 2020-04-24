package net.therap.notestasks.exception;

/**
 * @author tanmoy.das
 * @since 4/24/20
 */
public class InvalidUserException extends RuntimeException {

    public InvalidUserException() {
        super("User Not Valid");
    }

    public InvalidUserException(String s) {
        super(s);
    }
}


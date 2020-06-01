package net.therap.notestasks.exception;

/**
 * @author tanmoy.das
 * @since 4/24/20
 */
public class UserConnectionAlreadyExistsException extends RuntimeException {

    public UserConnectionAlreadyExistsException() {
        super("Connection Already Exists");
    }
}

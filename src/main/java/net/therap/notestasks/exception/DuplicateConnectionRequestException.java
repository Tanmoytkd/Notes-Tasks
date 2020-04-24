package net.therap.notestasks.exception;

/**
 * @author tanmoy.das
 * @since 4/24/20
 */
public class DuplicateConnectionRequestException extends RuntimeException {

    public DuplicateConnectionRequestException() {
        super("Connection Request Already Exists");
    }
}

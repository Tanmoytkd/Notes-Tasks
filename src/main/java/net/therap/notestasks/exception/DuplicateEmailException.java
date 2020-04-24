package net.therap.notestasks.exception;

public class DuplicateEmailException extends InvalidUserException {

    public DuplicateEmailException() {
        super("Email Already Exists");
    }
}

package at.ac.tuwien.sepr.groupphase.backend.exception;

public class PasswordDoesNotMatchEmailException extends RuntimeException {
    public PasswordDoesNotMatchEmailException(String message) {
        super(message);
    }
}

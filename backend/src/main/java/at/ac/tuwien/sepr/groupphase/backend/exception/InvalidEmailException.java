package at.ac.tuwien.sepr.groupphase.backend.exception;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String message) {
        super(message);
    }
}

package exception;

public class InvalidMemberException extends RuntimeException{
    public InvalidMemberException() {
        super();
    }

    public InvalidMemberException(String message) {
        super(message);
    }

    public InvalidMemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidMemberException(Throwable cause) {
        super(cause);
    }
}

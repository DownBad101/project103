package exception;

public class InvalidLoanException extends RuntimeException{
    public InvalidLoanException() {
        super();
    }

    public InvalidLoanException(String message) {
        super(message);
    }

    public InvalidLoanException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLoanException(Throwable cause) {
        super(cause);
    }
}

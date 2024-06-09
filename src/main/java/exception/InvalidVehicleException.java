package exception;

public class InvalidVehicleException extends RuntimeException {
    public InvalidVehicleException() {
        super();
    }

    public InvalidVehicleException(String message) {
        super(message);
    }

    public InvalidVehicleException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidVehicleException(Throwable cause) {
        super(cause);
    }
}

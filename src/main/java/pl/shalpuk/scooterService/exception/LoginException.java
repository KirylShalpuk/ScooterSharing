package pl.shalpuk.scooterService.exception;

public class LoginException extends RuntimeException {

    private static final long serialVersionUID = 4114470119037220187L;

    public LoginException(String message) {
        super(message);
    }
}

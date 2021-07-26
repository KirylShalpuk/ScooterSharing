package pl.shalpuk.scooterService.exception;

public class ExpiredTokenException extends RuntimeException{

    private static final long serialVersionUID = 3198698665347225353L;

    public ExpiredTokenException(String message) {
        super(message);
    }
}

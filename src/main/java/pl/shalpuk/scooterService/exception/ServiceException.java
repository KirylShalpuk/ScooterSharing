package pl.shalpuk.scooterService.exception;

public class ServiceException extends RuntimeException{

    private static final long serialVersionUID = -6192597273571701694L;

    public ServiceException(String message) {
        super(message);
    }
}

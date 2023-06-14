package exception;

public class TimeDelayException extends Exception {
    public TimeDelayException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}

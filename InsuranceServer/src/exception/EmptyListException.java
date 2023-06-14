package exception;

public class EmptyListException extends Exception{
    public EmptyListException(String message) {
        super(message);
    }
    @Override
    public String getMessage() {
        return super.getMessage();
    }
}

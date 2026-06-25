package integrado.prog2.exception;

public class FoodStoreException extends RuntimeException {

    public FoodStoreException(String message) {
        super(message);
    }

    public FoodStoreException(String message, Throwable cause) {
        super(message, cause);
    }
}

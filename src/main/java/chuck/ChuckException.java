package chuck;

/**
 * Custom exception class for Chuck application errors.
 * This exception is thrown when the application encounters specific errors
 * such as invalid commands, empty descriptions, or file operations failures.
 */
public class ChuckException extends Exception {
    public static final String ERROR_PREFIX = "You blockhead! ";

    /**
     * Creates a new ChuckException with the specified error message.
     *
     * @param message detailed message explaining the error
     */
    public ChuckException(String message) {
        super(ERROR_PREFIX + message);
    }

}

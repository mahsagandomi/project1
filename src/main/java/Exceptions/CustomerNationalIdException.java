package Exceptions;


/**
 * CustomerNationalIdException: This exception is thrown when the account national id is against a rule.
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */
public class CustomerNationalIdException extends Exception {
    /**
     * Default constructor for CustomerNationalIdException without message.
     */
    public CustomerNationalIdException() {
    }

    /**
     * Constructor with a custom message for CustomerNationalIdException.
     *
     * @param message Detailed message about the exception.
     */
    public CustomerNationalIdException(String message) {
        super(message);
    }
}


package Exceptions;

/**
 * AccountNumberException: This exception is thrown when the account number is against a rule.
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */
public class AccountNumberException extends Exception {

    /**
     * Default constructor for AccountNumberException without message.
     */
    public AccountNumberException() {
    }

    /**
     * Constructor with a custom message for AccountNumberException.
     *
     * @param message Detailed message about the exception.
     */
    public AccountNumberException(String message) {
        super(message);
    }
}

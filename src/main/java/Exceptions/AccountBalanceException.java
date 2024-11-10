package Exceptions;


/**
 * AccountBalanceException: This exception is thrown when account balance is against a rule.
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */

public class AccountBalanceException extends Exception {
    /**
     * Default constructor for AccountBalanceException without message.
     */
    public AccountBalanceException() {
    }


    /**
     * Constructor with a custom message for AccountBalanceException.
     *
     * @param message Detailed message about the exception.
     */
    public AccountBalanceException(String message) {
        super(message);
    }
}



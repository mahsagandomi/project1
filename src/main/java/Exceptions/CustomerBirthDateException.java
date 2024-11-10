package Exceptions;

/**
 * CustomerBirthDateException: This exception is thrown when the account birthdate is against a rule.
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */
public class CustomerBirthDateException extends Exception {
    /**
     * Default constructor for CustomerBirthDateException without message.
     */
    public CustomerBirthDateException() {
    }

    /**
     * Constructor with a custom message forCustomerBirthDateException.
     *
     * @param message Detailed message about the exception.
     */
    public CustomerBirthDateException(String message) {
        super(message);
    }
}


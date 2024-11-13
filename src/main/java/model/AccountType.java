package model;

/**
 * AccountType: Enum  different types of bank accounts.
 * each type with a unique identifier.
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */
public enum AccountType {
    // Savings account type with identifier "1"
    SAVINGS("1"),
    // Recurring deposit account type with identifier "2",
    RECURRINGDEPOSIT("2"),
    // Fixed deposit account type with identifier "3"
    FIXEDDEPOSITACCOUNT("3");
    // The unique number representing the account type
    private final String accountTypeNumber;

    /**
     * Constructor for AccountType enum.
     *
     * @param accountTypeNumber The unique identifier for the account type
     */

    AccountType(String accountTypeNumber) {
        this.accountTypeNumber = accountTypeNumber;
    }

    /**
     * Returns the string  of the account its account type number.
     *
     * @return The unique identifier as a string
     */
    @Override
    public String toString() {
        return accountTypeNumber;
    }
}


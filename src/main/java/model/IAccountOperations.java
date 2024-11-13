package model;

import java.time.LocalDate;

/**
 * IAccountOperations: An interface defining essential operations and properties
 * for a bank account.
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */

public interface IAccountOperations {
    /**
     * Gets the unique account number of the bank account.
     *
     * @return The account number as a String.
     */
    String getAccountNumber();

    /**
     * Retrieves the type of the bank account ( savings, ...).
     */
    AccountType getAccountType();

    /**
     * Gets the unique customer ID associated with the bank account.
     *
     * @return The customer ID as an integer.
     */
    int getAccountCustomerId();

    /**
     * Gets the date on which the account was opened.
     *
     * @return The account open date as a LocalDate object.
     */
    LocalDate getAccountOpenDate();

    /**
     * Gets the current balance of the bank account.
     *
     * @return The account balance as an integer.
     */
    int getAccountBalance();
}
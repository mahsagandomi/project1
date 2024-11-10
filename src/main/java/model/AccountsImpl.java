package model;

import Exceptions.AccountBalanceException;
import Exceptions.AccountNumberException;
import org.apache.log4j.Logger;
import utility.AccountMethods;

import java.time.LocalDate;
import java.util.function.Predicate;

/**
 * Accounts: Represents a bank account with details like account number, type, customer ID,
 * balance, and open date.
 *This class implements IAccountOperations
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */
public class AccountsImpl implements IAccountOperations {
    // Logger instance for logging error messages
    private static final Logger LOGGER = Logger.getLogger(AccountsImpl.class);

    // The unique number for the account
    private String accountNumber;
    // The type of the account ( savings, ...)
    private AccountType accountType;
    // The ID of the customer
    private int accountCustomerId;
    // The maximum allowable balance for the account
    private static final int ACCOUNT_LIMIT = 10000;
    // The date the account was opened

    private LocalDate accountOpenDate;
    // The balance of the account
    private int accountBalance;


    /**
     * Constructor to initialize an Accounts object with the provided parameters and Validates the account number and balance.
     *
     * @param accountNumber     The account number to assign to the account
     * @param accountType       The type of account (savings,...)
     * @param accountCustomerId The ID of the customer associated with this account
     * @param accountOpenDate   The date this account was opened
     * @param accountBalance    The initial balance of the account
     * @throws AccountBalanceException If the balance exceeds the account limit
     * @throws AccountNumberException  If the account number is invalid
     */

    public AccountsImpl(String accountNumber, AccountType accountType, int accountCustomerId, LocalDate accountOpenDate, int accountBalance) throws AccountBalanceException, AccountNumberException {
        // Validate and set the account number
        if (AccountMethods.validateAccountNumber(accountNumber)) {
            this.accountNumber = accountNumber;
            LOGGER.info("Account number is set to: " + accountNumber);
        } else {
            throw new AccountNumberException("Invalid account number");
        }
        // Set the account type and customer ID and account open date
        this.accountType = accountType;
        this.accountCustomerId = accountCustomerId;
        this.accountOpenDate = accountOpenDate;
        // Validate and set the account balance
        if (AccountMethods.validateAccountBalance(AccountsImpl.ACCOUNT_LIMIT, accountBalance)) {
            this.accountBalance = accountBalance;
            LOGGER.info("Account balance is set to: " + accountBalance);
        } else {
            throw new AccountBalanceException("AccountBalance is greater than accountLimit");
        }
        LOGGER.info("Account created with number: " + accountNumber + ", type: " + accountType + ", customerId: " + accountCustomerId + ", openDate: " + accountOpenDate + ", balance: " + accountBalance);

    }


    /**
     * Returns a Predicate that checks if an account's balance is greater than 1000.
     *
     * @return Predicate for account balance checking
     */
    public Predicate<AccountsImpl
            > getPredicateAccountBalance() {
        return account -> account.getAccountBalance() > 1000;
    }

    @Override
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    @Override
    public int getAccountCustomerId() {
        return accountCustomerId;
    }

    public void setAccountCustomerId(int accountCustomerId) {
        this.accountCustomerId = accountCustomerId;
    }

    public int getAccountLimit() {
        return ACCOUNT_LIMIT;
    }

    @Override
    public LocalDate getAccountOpenDate() {
        return accountOpenDate;
    }

    public void setAccountOpenDate(LocalDate accountOpenDate) {
        this.accountOpenDate = accountOpenDate;
    }

    @Override
    public int getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(int accountBalance) {
        this.accountBalance = accountBalance;
    }

    /**
     * Returns a string of the account, its account number.
     *
     * @return The account number as a string
     */
    @Override
    public String toString() {
        LOGGER.info("toString() called for account: " + accountNumber);
        return accountNumber;
    }

}

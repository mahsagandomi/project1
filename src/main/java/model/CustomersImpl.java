package model;

import Exceptions.CustomerBirthDateException;
import Exceptions.CustomerNationalIdException;
import org.apache.log4j.Logger;
import utility.CustomerMethods;

import java.time.LocalDate;

/**
 * Customers: Represents a customer with details such as ID, name, address,
 * national ID, and birthdate and validation national ID and birthdate.
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */
public class CustomersImpl implements ICustomerOperations {
    // Logger instance for logging error messages
    private static final Logger LOGGER = Logger.getLogger(CustomersImpl.class);

    // Unique code for the customer
    private int customerId;
    // Customer's first name
    private String customerName;
    // Customer's surname
    private String customerSurName;
    // Customer's  address
    private String customerAddress;
    // Postal code of the customer's address
    private int customerZipCode;
    // Unique national id for the customer
    private String customerNationalId;
    // Customer's date of birth
    private LocalDate customerBirthDate;

    /**
     * Constructor to initialize a Customers object with the parameters and Validates the national ID and birthdate.
     *
     * @param customerId         The unique ID for the customer
     * @param customerName       The first name of the customer
     * @param customerSubName    The surname the customer
     * @param customerAddress    The  address of the customer
     * @param customerZipCode    The postal code of the customer's address
     * @param customerNationalId The national ID of the customer
     * @param customerBirthDate  The birthdate of the customer
     * @throws CustomerBirthDateException  If the birthdate is before 1995
     * @throws CustomerNationalIdException If the national ID is invalid
     */

    public CustomersImpl(int customerId, String customerName, String customerSubName, String customerAddress, int customerZipCode, String customerNationalId, LocalDate customerBirthDate) throws CustomerBirthDateException, CustomerNationalIdException {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerSurName = customerSubName;
        this.customerAddress = customerAddress;
        this.customerZipCode = customerZipCode;
        // Validate national ID format
        if (CustomerMethods.validateCustomerNationalID(customerNationalId)) {
            this.customerNationalId = customerNationalId;
            LOGGER.info("Valid national ID: " + customerNationalId);
        } else {
            throw new CustomerNationalIdException("Invalid customerNationalId");
        }
        // Validate birthdate
        if (CustomerMethods.validateCustomerBirthDate(customerBirthDate)) {
            this.customerBirthDate = customerBirthDate;
            LOGGER.info("Valid birth date: " + customerBirthDate);
        } else {
            throw new CustomerBirthDateException("CustomerBirthDate must be more than 1995");
        }
        LOGGER.info("Customer created: " + this);
    }

    // Getter and setter methods for customer properties
    @Override
    public int getCustomerId() {
        return customerId;
    }

    @Override
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public String getCustomerName() {
        return customerName;
    }

    @Override
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String getCustomerSubName() {
        return customerSurName;
    }

    @Override
    public void setCustomerSurName(String customerSubName) {
        this.customerSurName = customerSubName;
    }

    @Override
    public String getCustomerAddress() {
        return customerAddress;
    }

    @Override
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    @Override
    public int getCustomerZipCode() {
        return customerZipCode;
    }

    @Override
    public void setCustomerZipCode(int customerZipCode) {
        this.customerZipCode = customerZipCode;
    }

    @Override
    public String getCustomerNationalId() {
        return customerNationalId;
    }

    public void setCustomerNationalId(String customerNationalId) {
        this.customerNationalId = customerNationalId;
    }

    public LocalDate getCustomerBirthDate() {
        return customerBirthDate;
    }

    public void setCustomerBirthDate(LocalDate customerBirthDate) {
        this.customerBirthDate = customerBirthDate;
    }

    public CustomersImpl() {
    }

    /**
     * Returns a string  of the customer, its customer's name and surname.
     *
     * @return The full name of the customer
     */

    @Override
    public String toString() {
        LOGGER.info("toString() called for customer: " + customerName + " " + customerSurName);
        return customerName + " " + customerSurName;
    }
}


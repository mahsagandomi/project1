package model;


/**
 * ICustomerOperations: An interface defining essential operations and properties
 * for a customer.
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */
public interface ICustomerOperations {

    /**
     * Gets the unique customer ID.
     *
     * @return The customer ID as an integer.
     */
    int getCustomerId();

    /**
     * Sets the unique customer ID.
     *
     * @param customerId The new customer ID.
     */
    void setCustomerId(int customerId);

    /**
     * Gets the customer's first name.
     *
     * @return The first name as a String.
     */
    String getCustomerName();

    /**
     * Sets the customer's first name.
     *
     * @param customerName The new first name of the customer.
     */
    void setCustomerName(String customerName);

    /**
     * Gets the customer's surname.
     *
     * @return The surname as a String.
     */
    String getCustomerSubName();

    /**
     * Sets the customer's surname.
     *
     * @param customerSubName The new surname of the customer.
     */
    void setCustomerSurName(String customerSubName);

    /**
     * Gets the customer's address.
     *
     * @return The address as a String.
     */
    String getCustomerAddress();

    /**
     * Sets the customer's address.
     *
     * @param customerAddress The new address of the customer.
     */
    void setCustomerAddress(String customerAddress);

    /**
     * Gets the postal code of the customer's address.
     *
     * @return The postal code as an integer.
     */
    int getCustomerZipCode();

    /**
     * Sets the postal code of the customer's address.
     *
     * @param customerZipCode The new zip code.
     */
    void setCustomerZipCode(int customerZipCode);

    /**
     * Gets the unique national ID of the customer.
     *
     * @return The national ID as a String.
     */
    String getCustomerNationalId();
}



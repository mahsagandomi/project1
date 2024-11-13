import org.junit.Test;
import utility.CustomerMethods;

import java.time.LocalDate;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the utility class {@link CustomerMethods}.
 * This class tests the validation methods for customer-related logic, including:
 * - Customer National ID validation
 * - Customer birthdate validation
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */

public class CustomerMethodTest {
    /**
     * Test to verify that a valid customer national ID passes validation.
     * A properly formatted national ID is considered valid.
     */
    @Test
    public void testValidateCustomerNationalID() {
        assertTrue(CustomerMethods.validateCustomerNationalID("0440888451"));
    }

    /**
     * Test to verify that an invalid customer national ID fails validation.
     * The ID does not meet the expected format or criteria.
     */
    @Test
    public void testValidateCustomerNationalID_Invalid() {
        assertFalse("The customer national id should be invalid", CustomerMethods.validateCustomerNationalID("1234"));
    }

    /**
     * Test to verify that a valid customer birthdate passes validation.
     * The birthdate must be in a valid range, e.g., not in the distant future or too far in the past.
     */
    @Test
    public void testValidateCustomerBirthDate() {
        assertTrue(CustomerMethods.validateCustomerBirthDate(LocalDate.of(2015, 10, 12)));
    }

    /**
     * Test to verify that an invalid customer birthdate fails validation.
     * Dates too far in the past (e.g., before a reasonable threshold) are considered invalid.
     */

    @Test
    public void testValidateCustomerBirthDateInvalid() {
        assertFalse("The customer national id should be invalid", CustomerMethods.validateCustomerBirthDate(LocalDate.of(1700, 10, 12)));
    }
}

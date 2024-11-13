import model.AccountType;
import org.junit.Test;
import utility.AccountMethods;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the utility class {@link AccountMethods}.
 * This class tests the validation methods for account-related logic, including:
 * - Account number validation
 * - Account balance validation
 * - Account type validation
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */

public class AccountMethodsTest {
    /**
     * Test to verify that a valid account number passes validation.
     * The account number should match the expected format or criteria.
     */
    @Test
    public void testValidateAccountNumber() {
        assertTrue("The account number should be valid", AccountMethods.validateAccountNumber("0440888451237986539873"));
    }

    /**
     * Test to verify that an invalid account number fails validation.
     * The account number does not match the expected format or criteria.
     */
    @Test
    public void testValidateAccountNumberInvalid() {
        assertFalse("The account number should be invalid", AccountMethods.validateAccountNumber("1440888451237986539873"));
    }

    /**
     * Test to verify that a valid account balance passes validation.
     * The available balance is sufficient for the requested amount.
     */

    @Test
    public void testValidateAccountBalance() {
        assertTrue("The account balance should be valid", AccountMethods.validateAccountBalance(1000, 200));
    }

    /**
     * Test to verify that an invalid account balance fails validation.
     * The requested amount exceeds the available balance.
     */


    @Test
    public void testValidateAccountBalanceInvalid() {
        assertFalse("The account balance should be invalid",
                AccountMethods.validateAccountBalance(1000, 1200));
    }

    /**
     * Test to verify that a valid account type passes validation.
     * A non-null account type such as SAVINGS is considered valid.
     */


    @Test
    public void testValidateAccountType() {
        assertTrue("The account type should be valid", AccountMethods.validateAccountType(AccountType.SAVINGS));
    }

    /**
     * Test to verify that an invalid account type fails validation.
     * A null account type is considered invalid.
     */

    @Test
    public void testValidateAccountTypeInvalid() {
        assertFalse("The account type should be invalid", AccountMethods.validateAccountType(null));
    }

}
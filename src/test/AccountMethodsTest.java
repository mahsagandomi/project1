import model.AccountType;
import org.junit.Test;
import utility.AccountMethods;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AccountMethodsTest {
    @Test
    public void testValidateAccountNumber() {
        assertTrue(AccountMethods.validateAccountNumber("0440888451237986539873"));
    }
    @Test
    public void testValidateAccountNumberInvalid() {
        assertFalse(AccountMethods.validateAccountNumber("1440888451237986539873"));
    }


    @Test
    public void testValidateAccountBalance() {
        assertTrue(AccountMethods.validateAccountBalance(1000, 200));
    }


    @Test
    public void testValidateAccountBalanceInvalid() {
        assertFalse("Balance exceeding the limit should fail validation",
                AccountMethods.validateAccountBalance(1000, 1200));
    }


    @Test
    public void testValidateAccountType() {
        assertTrue(AccountMethods.validateAccountType(AccountType.SAVINGS));
    }

    @Test
    public void testValidateAccountTypeInvalid() {
        assertFalse(AccountMethods.validateAccountType(null));
    }

}
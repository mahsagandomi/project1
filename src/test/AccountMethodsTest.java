import model.AccountType;
import org.junit.Test;
import utility.AccountMethods;

import static org.junit.Assert.assertTrue;

public class AccountMethodsTest {
    @Test
    public void  testValidateAccountNumber(){
        assertTrue(AccountMethods.validateAccountNumber("0440888451237986539873"));
    }

    @Test
    public void testValidateAccountBalance(){
        assertTrue(AccountMethods.validateAccountBalance(1000,200));
    }
    @Test
    public void testValidateAccountType(){
        assertTrue(AccountMethods.validateAccountType(AccountType.SAVINGS));
    }
}
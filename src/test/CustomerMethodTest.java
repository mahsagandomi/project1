import org.junit.Test;
import utility.CustomerMethods;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class CustomerMethodTest {

    @Test
    public void testValidateCustomerNationalID() {
        assertTrue(CustomerMethods.validateCustomerNationalID("0440888451"));
    }
    @Test
    public void testValidateCustomerNationalID_Invalid() {
        assertFalse(CustomerMethods.validateCustomerNationalID("1234"));
    }
    @Test
    public void testValidateCustomerBirthDate(){
        assertTrue(CustomerMethods.validateCustomerBirthDate(LocalDate.of(2015,10,12)));
    }

    @Test
    public void testValidateCustomerBirthDateInvalid() {
        assertFalse(CustomerMethods.validateCustomerBirthDate(LocalDate.of(1700,10,12)));
    }
}

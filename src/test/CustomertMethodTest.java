import model.CustomersImpl;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import utility.CustomerMethods;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CustomertMethodTest {
    CustomersImpl customer;

    @BeforeEach
    public void getInstance() throws Exception {
        customer = new CustomersImpl();
    }

    @Test
    public void testValidateCustomerNationalID() {
        assertTrue(CustomerMethods.validateCustomerNationalID("0440888451"));
    }
    @Test
    public void testValidateCustomerBirthDate(){
        assertTrue(CustomerMethods.validateCustomerBirthDate(LocalDate.of(2015,10,12)));
    }
}

import Exceptions.CustomerBirthDateException;
import Exceptions.CustomerNationalIdException;
import org.apache.log4j.Logger;
import repository.DatabaseMethods;
import utility.CustomerXmlReport;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Main3: This class serves as the third entry point for the application,
 * focused on generating an XML report of customer data retrieved from the database.
 * It utilizes the DatabaseMethods class to get customer data and the
 * CustomerXmlReport class to write this data into an XML file.
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */

public class Main3 {

    // Logger instance for logging error messages
    private static final org.apache.log4j.Logger LOGGER = Logger.getLogger(Main3.class);

    /**
     * The main method that initiates the process of generating an XML report.
     */
    public static void main(String[] args) {
        // Specify the file path for the output XML file
        File file = new File("result.xml");
        try {
            // Retrieve a ResultSet containing customer data from the database
            ResultSet resultSet = DatabaseMethods.getResultsetReport();
            // Generate the XML output from the database using the specified file
            CustomerXmlReport.writeXmlFile(file, resultSet);
        } catch (SQLException | CustomerBirthDateException | ParserConfigurationException |
                 CustomerNationalIdException | TransformerException e) {
            // Log any SQLExceptions, CustomerBirthDateExceptions, ParserConfigurationException, or CustomerNationalIdExceptions ,TransformerException
            LOGGER.error(e.getMessage());
           /* Print any SQLExceptions, CustomerBirthDateExceptions, ParserConfigurationExceptions,
             CustomerNationalIdExceptions, or TransformerExceptions that occur*/
            System.out.println(e.getMessage());
        }
    }
}

package utility;

import Exceptions.CustomerBirthDateException;
import Exceptions.CustomerNationalIdException;
import model.CustomersImpl;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


/**
 * CustomerXmlReport: A utility class for generating an XML report of customer data.
 * This class provides functionality to convert customer information from a database
 * ResultSet into an XML file format. It creates a structured XML document that includes
 * details such as customer ID, name, address, and birthdate.
 * Key Responsibilities:
 * - Reading customer data from a ResultSet object.
 * - Creating an XML document representing the customer data.
 * - Writing the generated XML document to a specified file.
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */

public class CustomerXmlReport {
    // Logger instance for logging
    private static final Logger LOGGER = Logger.getLogger(CustomerXmlReport.class);

    /**
     * Writes customer data from a ResultSet to an XML file.
     * This method processes the ResultSet containing customer information,
     * constructs an XML document, and saves it to the specified file.
     *
     * @param file      The file where the XML will be saved
     * @param resultSet The ResultSet containing customer data from the database
     * @throws ParserConfigurationException If a DocumentBuilder cannot be created
     * @throws SQLException                 If a database access error occurs
     * @throws CustomerBirthDateException   If a customer's birthdate is invalid
     * @throws CustomerNationalIdException  If a customer's national ID is invalid
     * @throws TransformerException         If an error occurs during XML transformation
     */
    public static void writeXmlFile(File file, ResultSet resultSet) throws ParserConfigurationException, SQLException, CustomerBirthDateException, CustomerNationalIdException, TransformerException {
        LOGGER.info("Starting XML file");
        // Create a DocumentBuilderFactory and a DocumentBuilder for creating the XML document
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        // Create the root element for the XML document
        Element root = document.createElement("customers");
        document.appendChild(root);

        // Process the ResultSet to extract customer information
        while (resultSet.next()) {
            // Extracting customer fields from the ResultSet
            int customerId = resultSet.getInt("customerId");
            String customerName = resultSet.getString("customerName");
            String customerSubName = resultSet.getString("customerSubName");
            String customerAddress = resultSet.getString("customerAddress");
            int customerZipCode = resultSet.getInt("customerZipCode");
            String customerNationalId = resultSet.getString("customerNationalId");
            LocalDate customerBirthDate = resultSet.getDate("customerBirthDate").toLocalDate();

            // Create a Customers object using the extracted data
            CustomersImpl customer = new CustomersImpl(customerId, customerName, customerSubName, customerAddress, customerZipCode, customerNationalId, customerBirthDate);

            // Create a new customer element in the XML document
            Element customerElement = document.createElement("customer");
            root.appendChild(customerElement);

            // Adding sub-elements for customer details
            Element idElement = document.createElement("customerId");
            idElement.appendChild(document.createTextNode(String.valueOf(customer.getCustomerId())));
            customerElement.appendChild(idElement);

            Element nameElement = document.createElement("customerName");
            nameElement.appendChild(document.createTextNode(customer.getCustomerName()));
            customerElement.appendChild(nameElement);

            Element subNameElement = document.createElement("customerSubName");
            subNameElement.appendChild(document.createTextNode(customer.getCustomerSubName()));
            customerElement.appendChild(subNameElement);

            Element addressElement = document.createElement("customerAddress");
            addressElement.appendChild(document.createTextNode(customer.getCustomerAddress()));
            customerElement.appendChild(addressElement);

            Element zipCodeElement = document.createElement("customerZipCode");
            zipCodeElement.appendChild(document.createTextNode(String.valueOf(customer.getCustomerZipCode())));
            customerElement.appendChild(zipCodeElement);

            Element nationalIdElement = document.createElement("customerNationalId");
            nationalIdElement.appendChild(document.createTextNode(customer.getCustomerNationalId()));
            customerElement.appendChild(nationalIdElement);

            Element birthDateElement = document.createElement("customerBirthDate");
            birthDateElement.appendChild(document.createTextNode(customer.getCustomerBirthDate().toString()));
            customerElement.appendChild(birthDateElement);
            LOGGER.info("Successfully added customer " + customerId);
        }

        // Save the generated XML document to the specified file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(file);
        transformer.transform(domSource, streamResult);

        System.out.println("XML created successfully!");
        LOGGER.info("XML file created successfully");

    }
}

package utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The ConfigLoader class is responsible for loading configuration properties
 * from the "application.properties" file.
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */

public class ConfigLoader {
    /**
     * This method loads the encryption password from the "application.properties" file
     * and returns it.
     *
     * @return The encryption password loaded from the properties file.
     * @throws RuntimeException If the "application.properties" file cannot be found
     *                          or if there is an error loading the properties from the file.
     */
    public static String getEncryptionPassword() {
        Properties prop = new Properties();
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("application.properties")) {
            // Check if the file exists in the classpath
            if (input == null) {
                throw new RuntimeException("Sorry, unable to find application.properties");
            }
            // Load the properties from the file
            prop.load(input);
            // Return the value of the encryption.password property
            return prop.getProperty("encryption.password");
        } catch (IOException ex) {
            // Throw a RuntimeException if there is an error loading the properties
            throw new RuntimeException("Error loading encryption password", ex);
        }
    }
}

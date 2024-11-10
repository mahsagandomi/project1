package repository;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * EncryptSqlPass: This class is used to encrypt a plain text password for use in SQL databases.
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */

public class EncryptSqlPass {
    public static void main(String[] args) {
        // Create an instance of BasicTextEncryptor to encryption
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        // Set the password used for encrypt
        basicTextEncryptor.setPassword("MysqlPass");
        // Encrypt the pass
        String encryptPass = basicTextEncryptor.encrypt("123456789");
        // Print the encrypt password
        System.out.println(encryptPass);
    }
}

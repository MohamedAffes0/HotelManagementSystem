package org.app;

/**
 * EmailChecker
 */
public class EmailChecker {
    public static boolean isValid(String emailText) {
        int altIndex = emailText.indexOf("@");
        
        if (altIndex == -1) {
            return false;
        }
        
        int dotIndex = emailText.lastIndexOf(".");

        if (dotIndex == -1) {
            return false;
        }
        
        // Si il n'y a pas de . apres le @
        if (altIndex > dotIndex) {
            return false;
        }
        
        return true;
    }
}

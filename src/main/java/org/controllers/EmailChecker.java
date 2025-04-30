package org.controllers;

/**
 * EmailChecker
 */
public class EmailChecker {
    public static boolean isValid(String emailText) {
        int altIndex = emailText.indexOf("@");
        
        if (altIndex == -1) {
            return false; // Si il n'y a pas de @
        }
        
        int dotIndex = emailText.lastIndexOf(".");

        if (dotIndex == -1) {
            return false; // Si il n'y a pas de .
        }

        if (altIndex > dotIndex) {
            return false; // Si il n'y a pas de . apres le @
        }
        
        return true;
    }
}

package org.controllers;

public class StringFloatExtract {
    
    public static String extract(String str) {
        return str.replaceAll("[^\\d.]", "");
    }
}

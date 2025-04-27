package org.controllers;

/**
 * StringNumberExtract, removes all non numeric characters from string.
 */
public class StringNumberExtract {

	public static String extract(String str) {
		return str.replaceAll("[^\\d]", "");
	}
}

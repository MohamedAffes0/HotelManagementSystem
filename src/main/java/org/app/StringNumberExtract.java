package org.app;

/**
 * StringNumberExtract, removes all non numeric characters from string.
 */
public class StringNumberExtract {

	public static String extract(String str) {
		return str.replaceAll("[^\\d]", "");
	}
}

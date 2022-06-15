package com.farawaybr.portal.regex;

import java.util.regex.Pattern;

public class RegexUtils {

	public static final Pattern MATCH_CHAR = Pattern.compile("[^0-9]");

	public static final Pattern MATCH_LINE_OF_NUMBERS = Pattern.compile("^[1*-9]*$");

	public static String removeAllChars(String string) {
		return string.replaceAll(MATCH_CHAR.pattern(), "");
	}

	public static boolean isOnlyNumbers(String string) {
		return MATCH_LINE_OF_NUMBERS.asPredicate().test(string);
	}
}

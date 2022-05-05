package com.farawaybr.portal.regex;

import java.util.regex.Pattern;

public class RegexUtils {

	private static final Pattern IDENTIFY_NUMBERS = Pattern.compile("[^0-9]");

	public static String removeAllChars(String string) {
		return string.replaceAll(IDENTIFY_NUMBERS.pattern(), "");
	}
}

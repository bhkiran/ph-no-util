package com.dexetra.phnoutils;

class Utils {
	/*
	 * Special characters
	 * 
	 * (See "What is a phone number?" doc) 'p' --- GSM pause character, same as
	 * comma 'n' --- GSM wild character 'w' --- GSM wait character
	 */
	static final char PAUSE = ',';
	static final char WAIT = ';';
	static final char WILD = 'N';

	/**
	 * Strips separators from a phone number string.
	 * 
	 * @param phoneNumber
	 *            phone number to strip.
	 * @return phone string stripped of separators.
	 */
	static String stripSeparators(String phoneNumber) {
		if (phoneNumber == null) {
			return null;
		}
		int len = phoneNumber.length();
		StringBuilder ret = new StringBuilder(len);

		for (int i = 0; i < len; i++) {
			char c = phoneNumber.charAt(i);
			// Character.digit() supports ASCII and Unicode digits (fullwidth,
			// Arabic-Indic, etc.)
			int digit = Character.digit(c, 10);
			if (digit != -1) {
				ret.append(digit);
			} else if (isNonSeparator(c)) {
				ret.append(c);
			}
		}

		return ret.toString();
	}

	/** True if c is ISO-LATIN characters 0-9, *, # , +, WILD, WAIT, PAUSE */
	final static boolean isNonSeparator(char c) {
		return (c >= '0' && c <= '9') || c == '*' || c == '#' || c == '+'
				|| c == WILD || c == WAIT || c == PAUSE;
	}

	final static long getNumId(String source) {
		int l = 9;
		String number = Utils.stripSeparators(source);
		long id = -1;
		int length = number.length();
		while (true) {
			try {
				id = Long.parseLong(number.substring(length
						- ((length >= l) ? l : length), length));
				break;
			} catch (NumberFormatException e) {
				if (l < 1) {
					id = -1;
					break;
				} else
					l--;
			} catch (Exception e) {
				id = -1;
				break;
			}
		}
		return id;
	}
}

package com.dexetra.phnoutils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public final class NumberDisintegrator {
	private final List<String> mCC;
	/*
	 * Country code in optimised order, most probable on top
	 */
	static String[] COUNTRY_CODES = new String[] { "1", "91", "86", "33", "49",
			"81", "92", "44", "46", "34", "82", "7", "39", "61", "351", "52",/* brk */
			"93", "355", "213", "376", "244", "54", "374", "297", "43", "994",
			"973", "880", "375", "32", "501", "229", "975", "591", "387",
			"267", "55", "673", "359", "226", "95", "257", "855", "237", "238",
			"236", "235", "56", "60", "57", "269", "242", "243", "682", "506",
			"385", "53", "357", "420", "45", "253", "670", "593", "20", "503",
			"240", "291", "372", "251", "500", "298", "679", "358", "689",
			"241", "220", "995", "233", "350", "30", "299", "502", "224",
			"245", "592", "509", "504", "852", "36", "62", "98", "964", "353",
			"972", "225", "962", "254", "686", "965", "996", "856", "371",
			"961", "266", "231", "218", "423", "370", "352", "853", "389",
			"261", "265", "960", "223", "356", "692", "222", "230", "262",
			"691", "373", "377", "976", "382", "212", "258", "264", "674",
			"977", "31", "599", "687", "64", "505", "227", "234", "683", "850",
			"47", "968", "680", "507", "675", "595", "51", "63", "870", "48",
			"974", "40", "250", "590", "685", "378", "239", "966", "221",
			"381", "248", "232", "65", "421", "386", "677", "252", "27", "94",
			"290", "508", "249", "597", "268", "41", "963", "886", "992",
			"255", "66", "228", "690", "676", "216", "90", "993", "688", "971",
			"256", "380", "598", "998", "678", "58", "84", "681", "967", "260",
			"263" };
	private static final String EC_PLUS = "+", EC_00 = "00", EC_011 = "011";
	private static final String TC_0 = "0", TC_1 = "1";
	final int SIZE_CC;

	NumberDisintegrator() {
		mCC = new ArrayList<String>(COUNTRY_CODES.length);
		for (int i = 0; i < COUNTRY_CODES.length; i++)
			mCC.add(COUNTRY_CODES[i]);
		SIZE_CC = mCC.size();
	}

	public NumberComponents disintegrate(String source) {
		if (source == null || source.trim().length() == 0)
			throw new IllegalStateException("invalid phone number");
		String phno = Utils.stripSeparators(source), exitcode = null, countrycode = null, number = null;
		if (phno.startsWith(EC_PLUS)) {
			exitcode = EC_PLUS;
			String pcc = phno.substring(1, (phno.length() < 4) ? phno.length()
					: 4);
			ListIterator<String> itr = mCC.listIterator();
			while (itr.hasNext()) {
				String cc = itr.next();
				if (pcc.startsWith(cc)) {
					countrycode = cc;
					number = phno.substring(1 + cc.length());
					if (itr.previousIndex() > 10)
						reArrange(itr, cc);
					break;
				}
			}
			if (number == null)
				number = phno.substring(1);
		} else if (phno.startsWith(EC_00)) {
			exitcode = EC_00;
			String pcc = phno.substring(2, (phno.length() < 5) ? phno.length()
					: 5);

			ListIterator<String> itr = mCC.listIterator();
			while (itr.hasNext()) {
				String cc = itr.next();
				if (pcc.startsWith(cc)) {
					countrycode = cc;
					number = phno.substring(2 + cc.length());
					if (itr.previousIndex() > 10)
						reArrange(itr, cc);
					break;
				}
			}

		} else if (phno.startsWith(EC_011)) {
			exitcode = EC_011;
			String pcc = phno.substring(3, 7);
			ListIterator<String> itr = mCC.listIterator();
			while (itr.hasNext()) {
				String cc = itr.next();
				if (pcc.startsWith(cc)) {
					countrycode = cc;
					number = phno.substring(3 + cc.length());
					if (itr.previousIndex() > 10)
						reArrange(itr, cc);
					break;
				}
			}

		} else if (phno.startsWith(TC_0)) {
			countrycode = TC_0;
			number = phno.substring(1);
		} else if (phno.startsWith(TC_1)) {
			countrycode = TC_1;
			number = phno.substring(1);
		} else {
			number = phno;
		}
		return new NumberComponents(source, exitcode, countrycode, number);
	}

	private void reArrange(Iterator<String> itr, String cc) {
		itr.remove();
		mCC.add(0, cc);
	}
}

package com.dexetra.phnoutils;


public class NumberComponents {
	public final String source, exitcode, countrycode, number;

	NumberComponents(String source, String exitcode, String countrycode,
			String number) {
		this.source = source;
		this.exitcode = exitcode;
		this.countrycode = countrycode;
		this.number = number;
	}

	public long getNumId() {
		return Utils.getNumId((number == null) ? source : number);
	}

	public String getNumber() {
		return (number == null) ? source : number;
	}

	private static final String SPACE = " ";

	@Override
	public String toString() {
		StringBuilder sbr = new StringBuilder();
		if (number != null) {
			if (exitcode != null)
				sbr.append(exitcode).append(SPACE);
			if (countrycode != null)
				sbr.append(countrycode).append(SPACE);
			sbr.append(number);
		} else
			sbr.append(source);
		return sbr.toString();
	}


}
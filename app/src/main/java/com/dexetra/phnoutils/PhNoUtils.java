package com.dexetra.phnoutils;

import java.util.HashMap;
import java.util.Map;

public class PhNoUtils {
	private final NumberDisintegrator mDisintegrator;
	private final Map<String, NumberComponents> mComponentCache;

	private static PhNoUtils sInstance;

	public PhNoUtils() {
		mComponentCache = new HashMap<String, NumberComponents>();
		mDisintegrator = new NumberDisintegrator();
	}

	public static PhNoUtils getInstance() {
		if (sInstance == null)
			sInstance = new PhNoUtils();
		return sInstance;
	}

	public NumberDisintegrator getDisintegrator() {
		return mDisintegrator;
	}

	public long getNumId(String number) {
		return getNumberComponents(number).getNumId();
	}

	public NumberComponents getNumberComponents(String number) {
		NumberComponents comp = mComponentCache.get(number);
		if (comp == null) {
			comp = mDisintegrator.disintegrate(number);
			mComponentCache.put(number, comp);
		}
		return comp;
	}

	
}

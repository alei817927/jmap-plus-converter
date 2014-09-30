package com.makenv.mapdc.code;

import java.util.HashMap;
import java.util.Map;

public class AbstractCodeProvider {
	protected CountryType country;
	protected Map<Integer, String> codes;

	public AbstractCodeProvider(CountryType country) {
		this.country = country;
		codes = new HashMap<>();
	}

	protected void regist(int key, String code) {
		codes.put(key, code);
	}

	public CountryType getCountry() {
		return country;
	}

	public String getCode(int key) {
		return codes.get(key);
	}
}

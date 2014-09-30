package com.makenv.mapdc.code;

public enum CountryType {
	CHINA("cn", "china");
	private final String code;
	private final String cName;

	CountryType(String code, String cName) {
		this.code = code;
		this.cName = cName;
	}

	public String getCode() {
		return code;
	}

	public String getcName() {
		return cName;
	}

}

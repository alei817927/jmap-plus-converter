package com.makenv.mapdc.code;

import java.util.HashMap;
import java.util.Map;

public class CodeService {
	private Map<CountryType, AbstractCodeProvider> codeProviders;

	public CodeService() {
		codeProviders = new HashMap<>();
		regist();
	}

	private void regist() {
		codeProviders.put(CountryType.CHINA, new ChinaCodeProvider(CountryType.CHINA));
	}

	public String getCode(CountryType country, int key) {
		if (codeProviders.containsKey(country)) {
			return codeProviders.get(country).getCode(key);
		}
		return null;
	}
}

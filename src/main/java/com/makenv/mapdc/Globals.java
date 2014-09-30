package com.makenv.mapdc;

import java.util.HashMap;
import java.util.Map;

import com.jhlabs.map.proj.Projection;
import com.jhlabs.map.proj.ProjectionFactory;
import com.makenv.mapdc.code.CodeService;
import com.makenv.mapdc.config.Config;

public class Globals {
	private static Config config;
	private static Projection projection;
	private static Map<String, Integer> counters;
	private static CodeService codeService;

	public static void init() throws Exception {
		config = new Config("mapdc.properties");
		config.init();
		projection = ProjectionFactory.fromPROJ4Specification(config.getProjection().getProjection());
		counters = new HashMap<>();
		codeService = new CodeService();
	}

	public static int getCounterAndPlus(String key) {
		int _counter = 0;
		if (counters.containsKey(key)) {
			_counter = counters.get(key);
		}
		counters.put(key, _counter + 1);
		return _counter;
	}

	public static Config getConfig() {
		return config;
	}

	public static Projection getProjection() {
		return projection;
	}

	public static CodeService getCodeService() {
		return codeService;
	}

}

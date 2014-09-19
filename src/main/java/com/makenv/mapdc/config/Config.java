package com.makenv.mapdc.config;

import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Config {
	private String propFile;
	private ProjectionConfig projection;
	private JVectorMapConfig jVectorMap;
	private ShapeFileConfig shapeFile;

	public Config(String file) {
		this.propFile = file;
		projection = new ProjectionConfig();
		jVectorMap = new JVectorMapConfig();
		shapeFile = new ShapeFileConfig();
	}

	public void init() throws Exception {
		Map<String, Object> _allConfigs = new HashMap<>();
		_allConfigs.put("Projection", projection);
		_allConfigs.put("JVectorMap", jVectorMap);
		_allConfigs.put("ShapeFile", shapeFile);
		Properties _pros = new Properties();
		_pros.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(propFile));
		Enumeration<?> _enumeration = _pros.propertyNames();
		while (_enumeration.hasMoreElements()) {
			String _key = (String) _enumeration.nextElement();
			String _value = _pros.getProperty(_key);
			_value = new String(_value.getBytes("ISO-8859-1"),"utf-8");
			String[] _classKey = _key.split("\\.");
			Object _obj = _allConfigs.get(_classKey[0]);
			Field _field = _obj.getClass().getDeclaredField(_classKey[1]);
			_field.setAccessible(true);
			Object _val = convertType(_field.getType(), _value);
			_field.set(_obj, _val);
		}
	}

	private Object convertType(Class<?> clazz, String value) {
		if (clazz.equals(int.class)) {
			return Integer.parseInt(value);
		} else if (clazz.equals(float.class)) {
			return Float.parseFloat(value);
		} else if (clazz.equals(double.class)) {
			return Double.parseDouble(value);
		} else if (clazz.equals(String.class)) {
			return value;
		}
		return null;
	}

	public ProjectionConfig getProjection() {
		return projection;
	}

	public JVectorMapConfig getjVectorMap() {
		return jVectorMap;
	}

	public ShapeFileConfig getShapeFile() {
		return shapeFile;
	}

}

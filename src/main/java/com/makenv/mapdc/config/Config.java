package com.makenv.mapdc.config;

import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.makenv.mapdc.op.OpType;
import com.makenv.mapdc.util.MapDCUtil;
import com.makenv.mapdc.util.StringUtil;

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
			_value = new String(_value.getBytes("ISO-8859-1"), "utf-8");
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
			if (StringUtil.isEmpty(value)) {
				return -1;
			}
			return Integer.parseInt(value);
		} else if (clazz.equals(float.class)) {
			if (StringUtil.isEmpty(value)) {
				return -1;
			}
			return Float.parseFloat(value);
		} else if (clazz.equals(double.class)) {
			if (StringUtil.isEmpty(value)) {
				return -1;
			}
			return Double.parseDouble(value);
		} else if (clazz.equals(String.class)) {
			if (StringUtil.isEmpty(value)) {
				return "";
			}
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

	public int getLatIndex(OpType opType) {
		switch (opType) {
		case POLYGON:
			return shapeFile.getPolygonLatIndex();
		case COMPLEX_POLYGON:
			return shapeFile.getComplexPolygonLatIndex();
		default:
			return -1;
		}
	}

	public int getLonIndex(OpType opType) {
		switch (opType) {
		case POLYGON:
			return shapeFile.getPolygonLonIndex();
		case COMPLEX_POLYGON:
			return shapeFile.getComplexPolygonLonIndex();
		default:
			return -1;
		}
	}

	public float getBufferDistance(OpType opType) {
		switch (opType) {
		case POLYGON:
			return shapeFile.getPolygonBufferDistance();
		case COMPLEX_POLYGON:
			return shapeFile.getComplexPolygonBufferDistance();
		default:
			return -1;
		}
	}

	public int getNameIndex(OpType opType) {
		switch (opType) {
		case POLYGON:
			return shapeFile.getPolygonNameIndex();
		case LINESTRING:
			return shapeFile.getLineNameIndex();
		case POINT:
			return shapeFile.getPointNameIndex();
		case COMPLEX_POLYGON:
			return shapeFile.getComplexPolygonNameIndex();
		default:
			return -1;
		}
	}

	public String getCharSet(OpType opType) {
		switch (opType) {
		case POLYGON:
			return shapeFile.getPolygonCharset();
		case LINESTRING:
			return shapeFile.getLineCharset();
		case POINT:
			return shapeFile.getPointCharset();
		case COMPLEX_POLYGON:
			return shapeFile.getComplexPolygonCharset();
		default:
			return null;
		}
	}

	public String getNameCode(OpType opType) {
		switch (opType) {
		case POLYGON:
			return shapeFile.getPolygonNameCode();
		case LINESTRING:
			return shapeFile.getLineNameCode();
		case POINT:
			return shapeFile.getPointNameCode();
		default:
			return null;
		}
	}

	public String getTargetFile(OpType opType) {
		return getTargetFile(opType, getNameCode(opType));
	}

	public String getTargetFile(OpType opType, String nameCode) {
		String pattern;
		switch (opType) {
		case POLYGON:
			pattern = jVectorMap.getPolygonFile();
			break;
		case LINESTRING:
			pattern = jVectorMap.getLineFile();
			break;
		case POINT:
			pattern = jVectorMap.getPointFile();
			break;
		case COMPLEX_POLYGON:
			pattern = jVectorMap.getComplexPolygonFile();
			break;
		default:
			return null;
		}
		Object[] arguments = new Object[] { nameCode, projection.getProj(), getLanguage(opType) };
		return MapDCUtil.format(pattern, arguments);
	}

	public String getLanguage(OpType opType) {
		String language;
		switch (opType) {
		case POLYGON:
			language = shapeFile.getPolygonLanguage();
			break;
		case LINESTRING:
			language = shapeFile.getLineLanguage();
			break;
		case POINT:
			language = shapeFile.getPointLanguage();
			break;
		case COMPLEX_POLYGON:
			language = shapeFile.getComplexPolygonLanguage();
			break;
		default:
			return null;
		}
		return language;
	}

	public String getMapName(OpType opType, String nameCode) {
		String pattern = "{0}_{1}_{2}";
		Object[] arguments = new Object[] { nameCode, projection.getProj(), getLanguage(opType) };
		return MapDCUtil.format(pattern, arguments);
	}

	public String getMapName(OpType opType) {
		return getMapName(opType, getNameCode(opType));
	}

	public boolean isNeedProcess(OpType opType) {
		switch (opType) {
		case POLYGON:
			return shapeFile.needProcessPolygon();
		case LINESTRING:
			return shapeFile.needProcessLine();
		case POINT:
			return shapeFile.needProcessPoint();
		case COMPLEX_POLYGON:
			return shapeFile.needProcessComplexPolygon();
		default:
			return false;
		}
	}
}

package com.makenv.mapdc.util;

import java.lang.reflect.Field;

public class MapDCUtil {
	public static String toString(Object obj) {
		StringBuilder _sb = new StringBuilder();
		Field[] _fields = obj.getClass().getDeclaredFields();
		int i = 0;
		for (Field _field : _fields) {
			_field.setAccessible(true);
			if (i > 0) {
				_sb.append(",");
			}
			_sb.append(_field.getName());
			_sb.append("=");
			Object _value = null;
			try {
				_value = _field.get(obj);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			if (_value != null) {
				_sb.append(_value.toString());
			}
			i++;
		}
		return _sb.toString();
	}
}

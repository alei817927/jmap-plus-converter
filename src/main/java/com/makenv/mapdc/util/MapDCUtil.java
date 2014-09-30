package com.makenv.mapdc.util;

import java.awt.geom.Point2D.Double;
import java.io.File;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.geotools.data.FeatureSource;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.makenv.mapdc.Globals;

public class MapDCUtil {
	public static String format(String pattern, Object[] arguments) {
		return MessageFormat.format(pattern, arguments);
	}

	public static List<SimpleFeature> readShpFile(String shapefile, String charset) {
		List<SimpleFeature> _features = new ArrayList<>();
		ShapefileDataStore _shpDataStore;
		try {
			_shpDataStore = new ShapefileDataStore(new File(shapefile).toURI().toURL());
			_shpDataStore.setCharset(Charset.forName(charset));
			String typeName = _shpDataStore.getTypeNames()[0];
			FeatureSource<SimpleFeatureType, SimpleFeature> featureSource = null;
			featureSource = (FeatureSource<SimpleFeatureType, SimpleFeature>) _shpDataStore.getFeatureSource(typeName);
			FeatureCollection<SimpleFeatureType, SimpleFeature> _featureCollection = featureSource.getFeatures();
			FeatureIterator<SimpleFeature> _featureIterator = _featureCollection.features();
			while (_featureIterator.hasNext()) {
				_features.add(_featureIterator.next());
			}
			_featureIterator.close();
			_shpDataStore.dispose();
		} catch (Exception e) {
			// TODO
			e.printStackTrace();
		}
		return _features;
	}

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

	/**
	 * 
	 * @description: 精度转换
	 * @param value
	 *            值
	 * @param precision
	 *            精度
	 * @return
	 */
	public static double precision(double value, int precision) {
		return new BigDecimal(value).setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 
	 * @description: 做投影
	 * @param x
	 *            经度
	 * @param y
	 *            纬度
	 * @return
	 */
	public static Double projection(double x, double y) {
		Double _src = new Double(x, y);
		Double _dst = new Double(0, 0);
		Globals.getProjection().transform(_src, _dst);
		return _dst;
	}
}

package com.makenv.mapdc.op;

import java.awt.geom.Point2D.Double;
import java.io.File;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.geotools.data.FeatureSource;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.makenv.mapdc.Globals;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;

public abstract class AbstractShapeOp {
	protected List<SimpleFeature> features;
	/** 唯一ID，用来保证path中的key唯一性 */
	protected int uniqueId;
	private String shapefile;

	public AbstractShapeOp(int id, String shapefile) {
		uniqueId = id;
		features = new ArrayList<>();
		this.shapefile = shapefile;
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
	protected double precision(double value, int precision) {
		return new BigDecimal(value).setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void init() throws Exception {
		ShapefileDataStore _shpDataStore = new ShapefileDataStore(new File(shapefile).toURI().toURL());
		_shpDataStore.setCharset(Charset.forName(Globals.getConfig().getShapeFile().getCharset()));
		String typeName = _shpDataStore.getTypeNames()[0];
		FeatureSource<SimpleFeatureType, SimpleFeature> featureSource = null;
		featureSource = (FeatureSource<SimpleFeatureType, SimpleFeature>) _shpDataStore.getFeatureSource(typeName);
		FeatureCollection<SimpleFeatureType, SimpleFeature> _featureCollection = featureSource.getFeatures();
		FeatureIterator<SimpleFeature> _featureIterator = _featureCollection.features();
		while (_featureIterator.hasNext()) {
			features.add(_featureIterator.next());
		}
		_featureIterator.close();
		_shpDataStore.dispose();
	}

	/**
	 * 
	 * @description: 获取JVectorMap数据中path的key
	 * @param id
	 * @return
	 */
	protected String getPathKey(int id) {
		return uniqueId + "-" + id;
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
	protected Double projection(double x, double y) {
		Double _src = new Double(x, y);
		Double _dst = new Double(0, 0);
		Globals.getProjection().transform(_src, _dst);
		return _dst;
	}

	/**
	 * 
	 * @description: 合并区域并做投影
	 * @return 合并且投影后的区域块
	 */
	protected Envelope mergeAndProjEnvelope() {
		Envelope _envelope = null;
		for (SimpleFeature _feature : features) {
			Geometry shape = (Geometry) _feature.getDefaultGeometry();
			if (_envelope == null) {
				_envelope = shape.getEnvelopeInternal();
			} else {
				_envelope.expandToInclude(shape.getEnvelopeInternal());
			}
		}
		Double _dst = projection(_envelope.getMaxX(), _envelope.getMaxY());
		double _maxX = _dst.getX();
		double _maxY = _dst.getY();
		_dst = projection(_envelope.getMinX(), _envelope.getMinY());
		double _minX = _dst.getX();
		double _minY = _dst.getY();
		return new Envelope(_minX, _maxX, _minY, _maxY);
	}

	public abstract void processData();
}

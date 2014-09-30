package com.makenv.mapdc.op;

import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.List;

import org.opengis.feature.simple.SimpleFeature;

import com.makenv.mapdc.Globals;
import com.makenv.mapdc.util.MapDCUtil;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;

public abstract class AbstractShapeOp {
	protected List<SimpleFeature> features;
	/** 唯一ID，用来保证path中的key唯一性 */
	protected int uniqueId;
	protected String shapefile;
	protected String charset;

	public AbstractShapeOp(int id, String shapefile) {
		uniqueId = id;
		features = new ArrayList<>();
		this.shapefile = shapefile;
		charset = Globals.getConfig().getCharSet(getOpType());
	}

	public void init() throws Exception {
		features = MapDCUtil.readShpFile(shapefile, charset);
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

	protected Envelope mergeAndProjEnvelope() {
		return mergeAndProjEnvelope(features);
	}

	/**
	 * 
	 * @description: 合并区域并做投影
	 * @return 合并且投影后的区域块
	 */
	protected Envelope mergeAndProjEnvelope(List<SimpleFeature> features) {
		Envelope _envelope = null;
		for (SimpleFeature _feature : features) {
			Geometry shape = (Geometry) _feature.getDefaultGeometry();
			if (_envelope == null) {
				_envelope = shape.getEnvelopeInternal();
			} else {
				_envelope.expandToInclude(shape.getEnvelopeInternal());
			}
		}
		Double _dst = MapDCUtil.projection(_envelope.getMaxX(), _envelope.getMaxY());
		double _maxX = _dst.getX();
		double _maxY = _dst.getY();
		_dst = MapDCUtil.projection(_envelope.getMinX(), _envelope.getMinY());
		double _minX = _dst.getX();
		double _minY = _dst.getY();
		return new Envelope(_minX, _maxX, _minY, _maxY);
	}

	public abstract void processData();

	public abstract OpType getOpType();

	public abstract Object getMapData();
}

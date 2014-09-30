package com.makenv.mapdc;

import java.awt.geom.Point2D.Double;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opengis.feature.simple.SimpleFeature;

import com.makenv.mapdc.data.InsetsData;
import com.makenv.mapdc.data.RegionData;
import com.makenv.mapdc.op.OpType;
import com.makenv.mapdc.util.MapDCUtil;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Polygon;

public class VectorMapDataProcessor {
	private double areaHeight;
	private Envelope envelope;
	private RegionData mapData;
	private List<SimpleFeature> features;
	private OpType opType;

	public VectorMapDataProcessor(List<SimpleFeature> features, Envelope envelope, OpType opType) {
		this.features = features;
		this.envelope = envelope;
		mapData = new RegionData();
		this.opType = opType;
	}

	public void doPorocess() {
		if (features.isEmpty()) {
			return;
		}
		int _width = Globals.getConfig().getjVectorMap().getWidth();
		areaHeight = (envelope.getMaxY() - envelope.getMinY()) * (_width / (envelope.getMaxX() - envelope.getMinX()));
		mapData.setWidth(_width);
		mapData.setHeight(areaHeight);
		processPathsData();
		processProjectionData();
		processInsetsData();
	}

	private void processPathsData() {
		int _precision = Globals.getConfig().getjVectorMap().getPrecision();
		int _countryNameIndex = Globals.getConfig().getNameIndex(opType);
		double _scale = (envelope.getMaxX() - envelope.getMinX()) / Globals.getConfig().getjVectorMap().getWidth();
		int i = 1;
		Double _dst;
		Map<String, Object[]> _maxAnalyzer = new HashMap<>();
		float _bufferDistance = Globals.getConfig().getBufferDistance(opType);
		for (SimpleFeature _feature : features) {
			Geometry _mShape = (Geometry) _feature.getDefaultGeometry();
			if (_bufferDistance != 0) {
				_mShape = _mShape.buffer(_bufferDistance);
				_feature.setDefaultGeometry(_mShape);
			}
			int _polygonCount = _mShape.getNumGeometries();
			double _max = 0;
			for (int n = 0; n < _polygonCount; n++) {
				Polygon _shape = (Polygon) _mShape.getGeometryN(n);
				double _len = _shape.getArea();
				if (_max < _len) {
					_max = _len;
				}
				i++;
			}
			String _name = String.valueOf(_feature.getAttribute(_countryNameIndex));
			Object[] _analyzer;
			if (_maxAnalyzer.containsKey(_name)) {
				_analyzer = _maxAnalyzer.get(_name);
				if ((double) _analyzer[1] < _max) {
					_analyzer[0] = i;
					_analyzer[1] = _max;
				}
			} else {
				_analyzer = new Object[2];
				_analyzer[0] = i;
				_analyzer[1] = _max;
				_maxAnalyzer.put(_name, _analyzer);
			}
		}
		i = 1;
		int latIndex = Globals.getConfig().getLatIndex(opType);
		int lonIndex = Globals.getConfig().getLonIndex(opType);
		for (SimpleFeature _feature : features) {
			Geometry _mShape = (Geometry) _feature.getDefaultGeometry();
			int _polygonCount = _mShape.getNumGeometries();
			for (int n = 0; n < _polygonCount; n++) {
				Polygon _shape = (Polygon) _mShape.getGeometryN(n);
				StringBuilder _sb = new StringBuilder();
				Double _lastPoint = null;
				for (Coordinate _coordinate : _shape.getCoordinates()) {
					_dst = MapDCUtil.projection(_coordinate.x, _coordinate.y);
					if (_lastPoint == null) {
						_sb.append("M");
						_sb.append(MapDCUtil.precision((_dst.getX() - envelope.getMinX()) / _scale, _precision));
						_sb.append(",");
						_sb.append(MapDCUtil.precision((envelope.getMaxY() - _dst.getY()) / _scale, _precision));
						_lastPoint = new Double();
					} else {
						_sb.append("l");
						_sb.append(MapDCUtil.precision((_dst.getX() - _lastPoint.getX()) / _scale, _precision));
						_sb.append(",");
						_sb.append(MapDCUtil.precision((_lastPoint.getY() - _dst.getY()) / _scale, _precision));

					}
					_lastPoint.setLocation(_dst.getX(), _dst.getY());
				}
				_sb.append("Z");

				Map<String, Object> _pathInfo = new HashMap<>();
				_pathInfo.put("path", _sb.toString());
				String _name = String.valueOf(_feature.getAttribute(_countryNameIndex));
				String _key = String.valueOf(i++);
				_pathInfo.put("name", _name);
				Object[] _analyzer = _maxAnalyzer.get(_name);
				if ((latIndex >= 0 && lonIndex >= 0) && (int) _analyzer[0] == i) {
					_pathInfo.put("pos", new Object[] { _feature.getAttribute(latIndex), _feature.getAttribute(lonIndex) });
				}
				mapData.addPath(_key, _pathInfo);
			}
		}
	}

	private void processProjectionData() {
		mapData.addProjection("type", Globals.getConfig().getProjection().getProj());
		mapData.addProjection("centralMeridian", Globals.getConfig().getProjection().getLon0());
	}

	private void processInsetsData() {
		InsetsData _insetsData = new InsetsData();
		_insetsData.setHeight(areaHeight);
		_insetsData.setLeft(Globals.getConfig().getjVectorMap().getLeft());
		_insetsData.setTop(Globals.getConfig().getjVectorMap().getTop());
		_insetsData.setWidth(Globals.getConfig().getjVectorMap().getWidth());
		Map<String, Object> _bbox1 = new HashMap<>();
		_bbox1.put("x", envelope.getMinX());
		_bbox1.put("y", -envelope.getMaxY());
		Map<String, Object> _bbox2 = new HashMap<>();
		_bbox2.put("x", envelope.getMaxX());
		_bbox2.put("y", -envelope.getMinY());
		_insetsData.addBbox(0, _bbox1);
		_insetsData.addBbox(1, _bbox2);
		mapData.addInsets(_insetsData);
	}

	public RegionData getMapData() {
		return mapData;
	}

}

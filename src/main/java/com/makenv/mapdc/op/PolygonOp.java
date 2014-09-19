package com.makenv.mapdc.op;

import java.awt.geom.Point2D.Double;
import java.util.HashMap;
import java.util.Map;

import org.opengis.feature.simple.SimpleFeature;

import com.makenv.mapdc.Globals;
import com.makenv.mapdc.data.InsetsData;
import com.makenv.mapdc.data.VectorMapData;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Polygon;

/**
 * 
 * @description: 多边形（区域）数据处理，只能创建一个对象
 * @author alei
 * @date 2014年9月19日
 */
public class PolygonOp extends AbstractShapeOp {
	private double areaHeight;
	private Envelope envelope;
	private VectorMapData mapData;

	public PolygonOp(int id, String shapefile, VectorMapData mapData) {
		super(id, shapefile);
		this.mapData = mapData;
	}

	@Override
	public void processData() {
		if (features.isEmpty()) {
			return;
		}
		envelope = mergeAndProjEnvelope();
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
		int _countryNameIndex = Globals.getConfig().getShapeFile().getCountryNameIndex();
		double _scale = (envelope.getMaxX() - envelope.getMinX()) / Globals.getConfig().getjVectorMap().getWidth();
		int i = 1;
		Double _dst;
		for (SimpleFeature _feature : features) {
			Geometry _mShape = (Geometry) _feature.getDefaultGeometry();
			int _polygonCount = _mShape.getNumGeometries();
			for (int n = 0; n < _polygonCount; n++) {
				Polygon _shape = (Polygon) _mShape.getGeometryN(n);
				StringBuilder _sb = new StringBuilder();
				Double _lastPoint = null;
				for (Coordinate _coordinate : _shape.getCoordinates()) {
					_dst = projection(_coordinate.x, _coordinate.y);
					if (_lastPoint == null) {
						_sb.append("M");
						_sb.append(precision((_dst.getX() - envelope.getMinX()) / _scale, _precision));
						_sb.append(",");
						_sb.append(precision((envelope.getMaxY() - _dst.getY()) / _scale, _precision));
						_lastPoint = new Double();
					} else {
						_sb.append("l");
						_sb.append(precision((_dst.getX() - _lastPoint.getX()) / _scale, _precision));
						_sb.append(",");
						_sb.append(precision((_lastPoint.getY() - _dst.getY()) / _scale, _precision));

					}
					_lastPoint.setLocation(_dst.getX(), _dst.getY());
				}
				_sb.append("Z");

				Map<String, String> _pathInfo = new HashMap<>();
				_pathInfo.put("path", _sb.toString());
				String _name = String.valueOf(_feature.getAttribute(_countryNameIndex));
				String _key = getPathKey(i++);
				_pathInfo.put("name", _name);
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

}

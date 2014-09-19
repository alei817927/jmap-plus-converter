package com.makenv.mapdc.op;

import java.awt.geom.Point2D.Double;
import java.util.HashMap;
import java.util.Map;

import org.opengis.feature.simple.SimpleFeature;

import com.makenv.mapdc.Globals;
import com.makenv.mapdc.data.VectorMapData;
import com.makenv.mapdc.util.StringUtil;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;

/**
 * 
 * @description: 路线（路网）数据处理，多个shp文件可以创建多个对象
 * @author alei
 * @date 2014年9月19日
 */
public class LineStringOp extends AbstractShapeOp {

	private Envelope envelope;
	private VectorMapData mapData;

	public LineStringOp(int id, String shapefile, VectorMapData mapData) {
		super(id, shapefile);
		this.mapData = mapData;
	}

	@Override
	public void processData() {
		if (features.isEmpty()) {
			return;
		}
		envelope = mergeAndProjEnvelope();
		processPathsData();
	}

	private void processPathsData() {
		int _precision = Globals.getConfig().getjVectorMap().getPrecision();
		int _countryNameIndex = Globals.getConfig().getShapeFile().getCountryNameIndex();
		double _scale = (envelope.getMaxX() - envelope.getMinX()) / Globals.getConfig().getjVectorMap().getWidth();
		Double _dst;
		Map<String, Integer> _names = new HashMap<>();
		int _lineInterval = Globals.getConfig().getShapeFile().getLineInterval();
		for (SimpleFeature _feature : features) {
			Geometry _mShape = (Geometry) _feature.getDefaultGeometry();
			int _polygonCount = _mShape.getNumGeometries();
			for (int n = 0; n < _polygonCount; n++) {
				LineString _shape = (LineString) _mShape.getGeometryN(n);
				StringBuilder _sb = new StringBuilder();
				Coordinate[] _coordinates = _shape.getCoordinates();
				int j = 0, _all = _coordinates.length;
				for (Coordinate _coordinate : _coordinates) {
					if ((j++) % _lineInterval == 0 || j == _all) {
						_dst = projection(_coordinate.x, _coordinate.y);
						_sb.append(precision((_dst.getX() - envelope.getMinX()) / _scale, _precision));
						_sb.append(",");
						_sb.append(precision((envelope.getMaxY() - _dst.getY()) / _scale, _precision));
						_sb.append(" ");
					}
				}

				Map<String, Object> _pathInfo = new HashMap<>();
				_pathInfo.put("path", _sb.toString());
				String _name = String.valueOf(_feature.getAttribute(_countryNameIndex));
				int _theNameIndex = 0;
				if (_names.containsKey(_name)) {
					_theNameIndex = _names.get(_name);
				} else {
					_theNameIndex = Globals.getCounterAndPlus("lineNameIndex");
					_names.put(_name, _theNameIndex);
					if (StringUtil.isEmpty(_name)) {
						mapData.addLineName("unnamed");
					} else {
						mapData.addLineName(_name);
					}
				}
				_pathInfo.put("name", _theNameIndex);
				mapData.addLine(_pathInfo);
			}
		}

	}
}

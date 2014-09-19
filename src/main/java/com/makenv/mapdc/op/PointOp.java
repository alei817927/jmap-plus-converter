package com.makenv.mapdc.op;

import java.util.ArrayList;
import java.util.List;

import org.opengis.feature.simple.SimpleFeature;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.makenv.mapdc.Globals;
import com.makenv.mapdc.util.FileUtil;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;

/**
 * 
 * @description: 点数据处理，多个shp文件可以创建多个对象
 * @author alei
 * @date 2014年9月19日
 */
public class PointOp extends AbstractShapeOp {

	public PointOp(int id, String shapefile) {
		super(id, shapefile);
	}

	@Override
	public void processData() {
		if (features.isEmpty()) {
			return;
		}
		int _countryNameIndex = Globals.getConfig().getShapeFile().getCountryNameIndex();
		List<Marker> _markers = new ArrayList<>();
		for (SimpleFeature _feature : features) {
			Geometry shape = (Geometry) _feature.getDefaultGeometry();
			if (shape instanceof Point) {
				Point _point = (Point) shape;
				Coordinate _coordinate = _point.getCoordinate();
				String _name = String.valueOf(_feature.getAttribute(_countryNameIndex));
				double[] _latLon = new double[] { _coordinate.y, _coordinate.x };
				Marker _marker = new Marker(_latLon, _name);
				_markers.add(_marker);
			} else {
				// TODO errorLog
				return;
			}
		}
		ObjectMapper _objectMapper = new ObjectMapper();
		String _content = "";
		try {
			_content = _objectMapper.writeValueAsString(_markers);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		FileUtil.writeFile(Globals.getConfig().getjVectorMap().getMarkFile(), "var markersData=" + _content + ";");
	}

	static class Marker {
		private double[] latLng;
		private String name;

		public Marker(double[] latLng, String name) {
			this.latLng = latLng;
			this.name = name;
		}

		public double[] getLatLng() {
			return latLng;
		}

		public String getName() {
			return name;
		}
	}
}

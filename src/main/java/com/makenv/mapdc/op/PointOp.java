package com.makenv.mapdc.op;

import java.util.ArrayList;
import java.util.List;

import org.opengis.feature.simple.SimpleFeature;

import com.makenv.mapdc.Globals;
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

	List<Marker> markers;

	public PointOp(int id, String shapefile) {
		super(id, shapefile);
		markers = new ArrayList<>();
	}

	@Override
	public void processData() {
		if (features.isEmpty()) {
			return;
		}
		int _nameIndex = Globals.getConfig().getShapeFile().getPointNameIndex();
		for (SimpleFeature _feature : features) {
			Geometry shape = (Geometry) _feature.getDefaultGeometry();
			if (shape instanceof Point) {
				Point _point = (Point) shape;
				Coordinate _coordinate = _point.getCoordinate();
				String _name = String.valueOf(_feature.getAttribute(_nameIndex));
				double[] _latLon = new double[] { _coordinate.y, _coordinate.x };
				Marker _marker = new Marker(_latLon, _name);
				markers.add(_marker);
			} else {
				// TODO errorLog
				return;
			}
		}
	}

	@Override
	public Object getMapData() {
		return markers;
	}

	@Override
	public OpType getOpType() {
		return OpType.POINT;
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

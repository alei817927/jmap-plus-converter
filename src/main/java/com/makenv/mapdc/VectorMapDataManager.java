package com.makenv.mapdc;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makenv.mapdc.data.VectorMapData;
import com.makenv.mapdc.op.AbstractShapeOp;
import com.makenv.mapdc.op.LineStringOp;
import com.makenv.mapdc.op.PointOp;
import com.makenv.mapdc.op.PolygonOp;
import com.makenv.mapdc.util.FileUtil;

public class VectorMapDataManager {
	private ObjectMapper mapper;

	public VectorMapDataManager() {
		mapper = new ObjectMapper();
	}

	public void buildPathData() throws Exception {
		List<AbstractShapeOp> _shpOps = new ArrayList<>();
		int i = 0;
		VectorMapData _data = new VectorMapData();
		String _polygonFile = Globals.getConfig().getShapeFile().getPolygonFile();
		_shpOps.add(new PolygonOp(++i, _polygonFile, _data));
		String _lineFile = Globals.getConfig().getShapeFile().getLineFile();
		_shpOps.add(new LineStringOp(++i, _lineFile, _data));
//		_shpOps.add(new LineStringOp(++i, "C:/Users/HP/Desktop/shp江苏D/省道_polyline.shp", _data));
		for (AbstractShapeOp _shpOp : _shpOps) {
			_shpOp.init();
			_shpOp.processData();
		}

		StringBuilder _content = new StringBuilder("jQuery.fn.vectorMap('addMap', '");
		_content.append(Globals.getConfig().getjVectorMap().getNameCountry());
		_content.append("_");
		_content.append(Globals.getConfig().getProjection().getProj());
		_content.append("_");
		_content.append(Globals.getConfig().getjVectorMap().getNameLanguage());
		_content.append("',");
		_content.append(mapper.writeValueAsString(_data));
		_content.append(");");
		FileUtil.writeFile(Globals.getConfig().getjVectorMap().getPathFile(), _content.toString());

	}

	public void buildMarkData() throws Exception {
		AbstractShapeOp _shpOp = new PointOp(0, Globals.getConfig().getShapeFile().getPointFile());
		_shpOp.init();
		_shpOp.processData();
	}

}

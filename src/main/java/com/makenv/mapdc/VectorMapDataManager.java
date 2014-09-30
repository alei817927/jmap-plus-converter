package com.makenv.mapdc;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.makenv.mapdc.code.CountryType;
import com.makenv.mapdc.data.RegionData;
import com.makenv.mapdc.op.AbstractShapeOp;
import com.makenv.mapdc.op.ComplexPolygonOp;
import com.makenv.mapdc.op.LineStringOp;
import com.makenv.mapdc.op.OpType;
import com.makenv.mapdc.op.PointOp;
import com.makenv.mapdc.op.PolygonOp;
import com.makenv.mapdc.util.FileUtil;
import com.makenv.mapdc.util.StringUtil;

public class VectorMapDataManager {
	private ObjectMapper mapper;
	private Logger logger = LoggerFactory.getLogger(VectorMapDataManager.class);

	public VectorMapDataManager() {
		mapper = new ObjectMapper();
	}

	public void buildRegionData() throws Exception {
		if (!Globals.getConfig().isNeedProcess(OpType.POLYGON)) {
			if (logger.isInfoEnabled()) {
				logger.info("VectorMapDataManager.buildRegionData was ignored!");
			}
			return;
		}
		String _targetFile = Globals.getConfig().getTargetFile(OpType.POLYGON);
		if (StringUtil.isEmpty(_targetFile)) {
			if (logger.isErrorEnabled()) {
				logger.error("JVectorMap.polygonFile in file [mapdc.properties] can't be empty!");
			}
			return;
		}
		String _polygonFile = Globals.getConfig().getShapeFile().getPolygonFile();
		PolygonOp op = new PolygonOp(1, _polygonFile);

		op.init();
		op.processData();
		String _name = Globals.getConfig().getMapName(OpType.POLYGON);
		writeFile(op.getMapData(), _targetFile, _name);
	}

	/**
	 * 
	 * @description: 可以读取多个shapefile
	 * @author: alei
	 * @throws Exception
	 */
	public void buildLineData() throws Exception {
		if (!Globals.getConfig().isNeedProcess(OpType.LINESTRING)) {
			if (logger.isInfoEnabled()) {
				logger.info("VectorMapDataManager.buildLineData was ignored!");
			}
			return;
		}
		String _targetFile = Globals.getConfig().getTargetFile(OpType.LINESTRING);
		if (StringUtil.isEmpty(_targetFile)) {
			if (logger.isErrorEnabled()) {
				logger.error("JVectorMap.lineFile in file [mapdc.properties] can't be empty!");
			}
			return;
		}
		String _lineFile = Globals.getConfig().getShapeFile().getLineFile();
		LineStringOp op = new LineStringOp(0, _lineFile);
		op.init();
		op.processData();
		StringBuilder _content = new StringBuilder("var lineData=");
		_content.append(mapper.writeValueAsString(op.getMapData()));
		_content.append(";");
		FileUtil.writeFile(_targetFile, _content.toString());
	}

	@SuppressWarnings("unchecked")
	public void buildProvinceData() throws Exception {
		if (!Globals.getConfig().isNeedProcess(OpType.COMPLEX_POLYGON)) {
			if (logger.isInfoEnabled()) {
				logger.info("VectorMapDataManager.buildProvinceData was ignored!");
			}
			return;
		}
		String _shpFile = Globals.getConfig().getShapeFile().getComplexPolygonFile();
		ComplexPolygonOp _shpOp = new ComplexPolygonOp(0, _shpFile, CountryType.CHINA);
		_shpOp.init();
		_shpOp.processData();
		Map<String, RegionData> _mapDatas = (Map<String, RegionData>) _shpOp.getMapData();
		for (String _provCode : _mapDatas.keySet()) {
			String _targetFile = Globals.getConfig().getTargetFile(OpType.COMPLEX_POLYGON, _provCode);
			if (StringUtil.isEmpty(_targetFile)) {
				if (logger.isErrorEnabled()) {
					logger.error("JVectorMap.complexPolygonFile in file [mapdc.properties] can't be empty!");
				}
				return;
			}
			String _name = Globals.getConfig().getMapName(OpType.COMPLEX_POLYGON, _provCode);
			writeFile(_mapDatas.get(_provCode), _targetFile, _name);
		}
	}

	private void writeFile(Object mapData, String fileName, String name) throws Exception {
		StringBuilder _content = new StringBuilder("jQuery.fn.vectorMap('addMap', '");
		_content.append(name);
		_content.append("',");
		_content.append(mapper.writeValueAsString(mapData));
		_content.append(");");
		FileUtil.writeFile(fileName, _content.toString());
	}

	public void buildPointData() throws Exception {
		String _shpFile = Globals.getConfig().getShapeFile().getPointFile();
		if (StringUtil.isEmpty(_shpFile)) {
			if (logger.isInfoEnabled()) {
				logger.info("VectorMapDataManager.buildPointData was ignored!");
			}
			return;
		}
		String _targetFile = Globals.getConfig().getTargetFile(OpType.POINT);
		if (StringUtil.isEmpty(_targetFile)) {
			if (logger.isErrorEnabled()) {
				logger.error("JVectorMap.pointFile in file [mapdc.properties] can't be empty!");
			}
			return;
		}
		AbstractShapeOp _shpOp = new PointOp(0, _shpFile);
		_shpOp.init();
		_shpOp.processData();

		ObjectMapper _objectMapper = new ObjectMapper();
		String _content = "";
		try {
			_content = _objectMapper.writeValueAsString(_shpOp.getMapData());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		FileUtil.writeFile(_targetFile, "var markersData=" + _content + ";");
	}

}

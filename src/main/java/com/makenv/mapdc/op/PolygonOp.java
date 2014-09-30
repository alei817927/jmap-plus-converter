package com.makenv.mapdc.op;

import com.makenv.mapdc.VectorMapDataProcessor;
import com.makenv.mapdc.data.RegionData;
import com.vividsolutions.jts.geom.Envelope;

/**
 * 
 * @description: 多边形（区域）数据处理，只能创建一个对象
 * @author alei
 * @date 2014年9月19日
 */
public class PolygonOp extends AbstractShapeOp {
	private RegionData mapData;

	public PolygonOp(int id, String shapefile) {
		super(id, shapefile);
		this.mapData = new RegionData();
	}

	@Override
	public void processData() {
		if (features.isEmpty()) {
			return;
		}
		Envelope _envelope = mergeAndProjEnvelope();
		VectorMapDataProcessor _processor = new VectorMapDataProcessor(features, _envelope, this.getOpType());
		_processor.doPorocess();
		mapData = _processor.getMapData();
	}

	@Override
	public Object getMapData() {
		return mapData;
	}

	@Override
	public OpType getOpType() {
		return OpType.POLYGON;
	}

}

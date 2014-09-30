package com.makenv.mapdc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Converter {

	private static Logger logger = LoggerFactory.getLogger(Converter.class);
	public static void main(String[] args) throws Exception {
		Globals.init();
		VectorMapDataManager _manager = new VectorMapDataManager();
		_manager.buildRegionData();
		logger.info("buildRegionData compelete.");
		_manager.buildPointData();
		logger.info("buildPointData compelete.");
		_manager.buildProvinceData();
		logger.info("buildProvinceData compelete.");
		_manager.buildLineData();
		logger.info("buildLineData compelete.");
	}

}

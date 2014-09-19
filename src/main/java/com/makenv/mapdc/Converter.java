package com.makenv.mapdc;

public class Converter {

	public static void main(String[] args) throws Exception {
		Globals.init();
		VectorMapDataManager _manager = new VectorMapDataManager();
		_manager.buildPathData();
		_manager.buildMarkData();
	}

}

package com.makenv.mapdc.op;

public enum OpType {
	//多边形
	POLYGON(1),
	//线
	LINESTRING(2),
	//点
	POINT(3),
	//复合多边，包含多个区域的地图文件类型
	COMPLEX_POLYGON(4);
	private final int index;

	OpType(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

}

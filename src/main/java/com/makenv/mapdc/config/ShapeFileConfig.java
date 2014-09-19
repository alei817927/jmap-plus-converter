package com.makenv.mapdc.config;

import com.makenv.mapdc.util.MapDCUtil;

public class ShapeFileConfig {
	private String polygonFile;
	private String lineFile;
	private String pointFile;
	private String charset;
	private int countryNameIndex;
	private int lineInterval;

	public String getPolygonFile() {
		return polygonFile;
	}

	public void setPolygonFile(String polygonFile) {
		this.polygonFile = polygonFile;
	}

	public String getLineFile() {
		return lineFile;
	}

	public void setLineFile(String lineFile) {
		this.lineFile = lineFile;
	}

	public String getPointFile() {
		return pointFile;
	}

	public void setPointFile(String pointFile) {
		this.pointFile = pointFile;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public int getCountryNameIndex() {
		return countryNameIndex;
	}

	public void setCountryNameIndex(int countryNameIndex) {
		this.countryNameIndex = countryNameIndex;
	}

	public String toString() {
		return MapDCUtil.toString(this);
	}

	public int getLineInterval() {
		return lineInterval;
	}

	public void setLineInterval(int lineInterval) {
		this.lineInterval = lineInterval;
	}

}

package com.makenv.mapdc.config;

import com.makenv.mapdc.util.MapDCUtil;

public class JVectorMapConfig {
	private int width;
	private int left;
	private int top;
	private String polygonFile;
	private String pointFile;
	private String complexPolygonFile;
	private String lineFile;
	private int precision;
	private float distance;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public String getPolygonFile() {
		return polygonFile;
	}

	public void setPolygonFile(String polygonFile) {
		this.polygonFile = polygonFile;
	}

	public String getPointFile() {
		return pointFile;
	}

	public void setPointFile(String pointFile) {
		this.pointFile = pointFile;
	}

	public String getComplexPolygonFile() {
		return complexPolygonFile;
	}

	public void setComplexPolygonFile(String complexPolygonFile) {
		this.complexPolygonFile = complexPolygonFile;
	}

	public String getLineFile() {
		return lineFile;
	}

	public void setLineFile(String lineFile) {
		this.lineFile = lineFile;
	}

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public String toString() {
		return MapDCUtil.toString(this);
	}

}

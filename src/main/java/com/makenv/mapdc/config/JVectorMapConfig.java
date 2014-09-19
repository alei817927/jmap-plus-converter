package com.makenv.mapdc.config;

import com.makenv.mapdc.util.MapDCUtil;

public class JVectorMapConfig {
	private String nameCountry;
	private String nameLanguage;
	private int width;
	private int left;
	private int top;
	private String pathFile;
	private String markFile;
	private int precision;
	private float distance;

	public String getNameCountry() {
		return nameCountry;
	}

	public void setNameCountry(String nameCountry) {
		this.nameCountry = nameCountry;
	}

	public String getNameLanguage() {
		return nameLanguage;
	}

	public void setNameLanguage(String nameLanguage) {
		this.nameLanguage = nameLanguage;
	}

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

	public String getPathFile() {
		return pathFile;
	}

	public void setPathFile(String pathFile) {
		this.pathFile = pathFile;
	}

	public String getMarkFile() {
		return markFile;
	}

	public void setMarkFile(String markFile) {
		this.markFile = markFile;
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

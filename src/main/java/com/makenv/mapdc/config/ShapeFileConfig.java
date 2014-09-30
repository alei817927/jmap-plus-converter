package com.makenv.mapdc.config;

import com.makenv.mapdc.util.StringUtil;

public class ShapeFileConfig {
	private String polygonFile;
	private String polygonCharset;
	private int polygonNameIndex;
	private float polygonBufferDistance;
	private int polygonLonIndex;
	private int polygonLatIndex;

	private String complexPolygonFile;
	private String complexPolygonCharset;
	private int complexPolygonNameIndex;
	private float complexPolygonBufferDistance;
	private int complexPolygonLatIndex;
	private int complexPolygonLonIndex;

	private String lineFile;
	private String lineDependPolygonFile;
	private String lineCharset;
	private int lineNameIndex;
	private int lineInterval;

	private String pointFile;
	private String pointCharset;
	private int pointNameIndex;

	private String polygonNameCode;
	private String polygonLanguage;
	private String complexPolygonLanguage;
	private String lineNameCode;
	private String lineLanguage;
	private String pointNameCode;
	private String pointLanguage;

	public boolean needProcessComplexPolygon() {
		return !StringUtil.isEmpty(complexPolygonFile);
	}

	public boolean needProcessPolygon() {
		return !StringUtil.isEmpty(polygonFile);
	}

	public boolean needProcessPoint() {
		return !StringUtil.isEmpty(pointFile);
	}

	public boolean needProcessLine() {
		return !StringUtil.isEmpty(lineFile) && !StringUtil.isEmpty(lineDependPolygonFile);
	}

	public String getPolygonFile() {
		return polygonFile;
	}

	public void setPolygonFile(String polygonFile) {
		this.polygonFile = polygonFile;
	}

	public String getPolygonCharset() {
		return polygonCharset;
	}

	public void setPolygonCharset(String polygonCharset) {
		this.polygonCharset = polygonCharset;
	}

	public int getPolygonNameIndex() {
		return polygonNameIndex;
	}

	public void setPolygonNameIndex(int polygonNameIndex) {
		this.polygonNameIndex = polygonNameIndex;
	}

	public float getPolygonBufferDistance() {
		return polygonBufferDistance;
	}

	public void setPolygonBufferDistance(float polygonBufferDistance) {
		this.polygonBufferDistance = polygonBufferDistance;
	}

	public int getPolygonLonIndex() {
		return polygonLonIndex;
	}

	public void setPolygonLonIndex(int polygonLonIndex) {
		this.polygonLonIndex = polygonLonIndex;
	}

	public int getPolygonLatIndex() {
		return polygonLatIndex;
	}

	public void setPolygonLatIndex(int polygonLatIndex) {
		this.polygonLatIndex = polygonLatIndex;
	}

	public int getComplexPolygonLatIndex() {
		return complexPolygonLatIndex;
	}

	public void setComplexPolygonLatIndex(int complexPolygonLatIndex) {
		this.complexPolygonLatIndex = complexPolygonLatIndex;
	}

	public int getComplexPolygonLonIndex() {
		return complexPolygonLonIndex;
	}

	public void setComplexPolygonLonIndex(int complexPolygonLonIndex) {
		this.complexPolygonLonIndex = complexPolygonLonIndex;
	}

	public String getComplexPolygonFile() {
		return complexPolygonFile;
	}

	public void setComplexPolygonFile(String complexPolygonFile) {
		this.complexPolygonFile = complexPolygonFile;
	}

	public String getComplexPolygonCharset() {
		return complexPolygonCharset;
	}

	public void setComplexPolygonCharset(String complexPolygonCharset) {
		this.complexPolygonCharset = complexPolygonCharset;
	}

	public int getComplexPolygonNameIndex() {
		return complexPolygonNameIndex;
	}

	public void setComplexPolygonNameIndex(int complexPolygonNameIndex) {
		this.complexPolygonNameIndex = complexPolygonNameIndex;
	}

	public float getComplexPolygonBufferDistance() {
		return complexPolygonBufferDistance;
	}

	public void setComplexPolygonBufferDistance(float complexPolygonBufferDistance) {
		this.complexPolygonBufferDistance = complexPolygonBufferDistance;
	}

	public String getLineFile() {
		return lineFile;
	}

	public void setLineFile(String lineFile) {
		this.lineFile = lineFile;
	}

	public String getLineDependPolygonFile() {
		return lineDependPolygonFile;
	}

	public void setLineDependPolygonFile(String lineDependPolygonFile) {
		this.lineDependPolygonFile = lineDependPolygonFile;
	}

	public String getLineCharset() {
		return lineCharset;
	}

	public void setLineCharset(String lineCharset) {
		this.lineCharset = lineCharset;
	}

	public int getLineNameIndex() {
		return lineNameIndex;
	}

	public void setLineNameIndex(int lineNameIndex) {
		this.lineNameIndex = lineNameIndex;
	}

	public int getLineInterval() {
		return lineInterval;
	}

	public void setLineInterval(int lineInterval) {
		this.lineInterval = lineInterval;
	}

	public String getPointFile() {
		return pointFile;
	}

	public void setPointFile(String pointFile) {
		this.pointFile = pointFile;
	}

	public String getPointCharset() {
		return pointCharset;
	}

	public void setPointCharset(String pointCharset) {
		this.pointCharset = pointCharset;
	}

	public int getPointNameIndex() {
		return pointNameIndex;
	}

	public void setPointNameIndex(int pointNameIndex) {
		this.pointNameIndex = pointNameIndex;
	}

	public String getPolygonNameCode() {
		return polygonNameCode;
	}

	public void setPolygonNameCode(String polygonNameCode) {
		this.polygonNameCode = polygonNameCode;
	}

	public String getPolygonLanguage() {
		return polygonLanguage;
	}

	public void setPolygonLanguage(String polygonLanguage) {
		this.polygonLanguage = polygonLanguage;
	}

	public String getComplexPolygonLanguage() {
		return complexPolygonLanguage;
	}

	public void setComplexPolygonLanguage(String complexPolygonLanguage) {
		this.complexPolygonLanguage = complexPolygonLanguage;
	}

	public String getLineNameCode() {
		return lineNameCode;
	}

	public void setLineNameCode(String lineNameCode) {
		this.lineNameCode = lineNameCode;
	}

	public String getLineLanguage() {
		return lineLanguage;
	}

	public void setLineLanguage(String lineLanguage) {
		this.lineLanguage = lineLanguage;
	}

	public String getPointNameCode() {
		return pointNameCode;
	}

	public void setPointNameCode(String pointNameCode) {
		this.pointNameCode = pointNameCode;
	}

	public String getPointLanguage() {
		return pointLanguage;
	}

	public void setPointLanguage(String pointLanguage) {
		this.pointLanguage = pointLanguage;
	}

}

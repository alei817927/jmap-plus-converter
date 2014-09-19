package com.makenv.mapdc.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.makenv.mapdc.Globals;

/**
 * 
 * @description: 转换后的地图数据对象
 * @author alei
 * @date 2014年9月17日
 */
public class VectorMapData {
	private List<InsetsData> insets;
	private Map<String, Object> paths;
	private Map<Integer, Object> lines;
	private double height;
	private double width;
	private Map<String, Object> projection;
	private List<String> lineNames;

	public VectorMapData() {
		insets = new ArrayList<>();
		paths = new HashMap<>();
		lines = new HashMap<>();
		projection = new HashMap<>();
		lineNames = new ArrayList<>();
	}

	public void addLineName(String lineName) {
		lineNames.add(lineName);
	}

	public void addProjection(String key, Object proj) {
		projection.put(key, proj);
	}

	public void addPath(String key, Object path) {
		paths.put(key, path);
	}

	public void addLine(Object path) {
		lines.put(Globals.getCounterAndPlus("lineNames"), path);
	}

	public void addInsets(InsetsData inset) {
		insets.add(inset);
	}

	public List<InsetsData> getInsets() {
		return insets;
	}

	public Map<String, Object> getPaths() {
		return paths;
	}

	public Map<String, Object> getProjection() {
		return projection;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public Map<Integer, Object> getLines() {
		return lines;
	}

	public List<String> getLineNames() {
		return lineNames;
	}

}

package com.makenv.mapdc.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.makenv.mapdc.Globals;

public class LineData {
	private List<String> lineNames;
	private Map<Integer, Object> lines;

	public LineData() {
		lineNames = new ArrayList<>();
		lines = new HashMap<>();
	}

	public void addLineName(String lineName) {
		lineNames.add(lineName);
	}

	public void addLine(Object path) {
		lines.put(Globals.getCounterAndPlus("lineNames"), path);
	}

	public Map<Integer, Object> getLines() {
		return lines;
	}

	public List<String> getLineNames() {
		return lineNames;
	}

}

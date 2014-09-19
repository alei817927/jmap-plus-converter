package com.makenv.mapdc.config;

import com.makenv.mapdc.util.MapDCUtil;

public class ProjectionConfig {
	private String proj;
	private int lat1;
	private int lat2;
	private int lat0;
	private int lon0;
	private int x0;
	private int y0;
	private String ellps;
	private String units;
	private String extra;

	public String getProj() {
		return proj;
	}

	public void setProj(String proj) {
		this.proj = proj;
	}

	public int getLat1() {
		return lat1;
	}

	public void setLat1(int lat1) {
		this.lat1 = lat1;
	}

	public int getLat2() {
		return lat2;
	}

	public void setLat2(int lat2) {
		this.lat2 = lat2;
	}

	public int getLat0() {
		return lat0;
	}

	public void setLat0(int lat0) {
		this.lat0 = lat0;
	}

	public int getLon0() {
		return lon0;
	}

	public void setLon0(int lon0) {
		this.lon0 = lon0;
	}

	public int getX0() {
		return x0;
	}

	public void setX0(int x0) {
		this.x0 = x0;
	}

	public String getEllps() {
		return ellps;
	}

	public void setEllps(String ellps) {
		this.ellps = ellps;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String[] getProjection() {
		return new String[] { "+proj=" + proj, "+lat_1=" + lat1, "+lat_2=" + lat2, "+lat_0=" + lat0, "+lon_0=" + lon0, "+x_0=" + x0, "+y_0=" + y0, "+ellps=" + ellps,
				"+units=" + units, extra };
	}

	public String toString() {
		return MapDCUtil.toString(this);
	}

}

package com.makenv.mapdc.data;


/**
 * 
 * @description: insets数据对象，标记部分会用的到
 * @author alei
 * @date 2014年9月17日
 */
public class InsetsData {
	private double width;
	private double top = 0;
	private double height;
	private Object[] bbox;
	private double left = 0;

	public InsetsData() {
		bbox = new Object[2];
	}

	public void addBbox(int index, Object obj) {
//		Assert.assertTrue(index >= 0 && index < 2);
		bbox[index] = obj;
	}

	public Object[] getBbox() {
		return bbox;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getTop() {
		return top;
	}

	public void setTop(double top) {
		this.top = top;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getLeft() {
		return left;
	}

	public void setLeft(double left) {
		this.left = left;
	}

}

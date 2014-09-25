package com.lafetra.scott.intuition.geom;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

public class Triangle extends Polygon {
	
	/**
	 * Creates a triangle with the given points.
	 * @param x1 The x cord of the first point.
	 * @param y1 The y cord of the first point. 
	 * @param x2 The x cord of the second point.
	 * @param y2 The y cord of the second point.
	 * @param x3 The x cord of the third point.
	 * @param y3 The y cord of the third point.
	 */
	public Triangle(double x1, double y1, double x2, double y2, double x3, double y3){
		
		Point[] points = new Point[3];
		points[0] = new Point(x1, y1);
		points[1] = new Point(x2, y2);
		points[2] = new Point(x3, y3);
		
		setPoints(points);
	}
	
	/**
	 * Sets the openGL shape type.
	 */
	@Override
	protected void setShape() {
		shape = GL_TRIANGLES;
	}
}

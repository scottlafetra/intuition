package com.lafetra.scott.intuition.geom;

import static org.lwjgl.opengl.GL11.GL_QUADS;

public class Rectangle extends Polygon{
	
	public double height, width;
	
	/**
	 * Constructs a rectangle.
	 * @param x The x cord of this rectangle
	 * @param y
	 * @param width
	 * @param height
	 */
	public Rectangle(double x, double y, double width, double height){
		this.height = height;
		this.width = width;
		
		Point[] points = new Point[4];
		points[0] = new Point(x         , y);
		points[1] = new Point(width + x , y);
		points[2] = new Point(width +  x, height + y);
		points[3] = new Point(x         , height + y);
		setPoints(points);
	}
	
	public double getWidth(){
		return width;
	}
	
	public double getHeight(){
		return height;
	}

	@Override
	protected void setShape() {
		shape = GL_QUADS;
	}
}

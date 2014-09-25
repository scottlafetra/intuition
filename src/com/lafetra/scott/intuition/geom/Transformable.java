package com.lafetra.scott.intuition.geom;

public interface Transformable {
	public void move(double x, double y);
	public void move(Point change);
	public void rotate(double degrees);
	public void rotateAround(double degrees, double x, double y);
	public void setCenter(double cx, double cy);
	
	public double getX();
	public double getY();
}

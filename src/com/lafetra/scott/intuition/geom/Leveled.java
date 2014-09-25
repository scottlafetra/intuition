package com.lafetra.scott.intuition.geom;

import static com.lafetra.scott.intuition.geom.TransformMatrix.*;


public class Leveled implements Transformable{
	
	private TransformMatrix offCenter;//positions thing so that the center is 0, 0
	private TransformMatrix centered;//Rotations flips, scales, etc
	private TransformMatrix uncentered;//translations and other off center operations
	
	/**
	 * Creates an object that exists on a relitive plane.
	 */
	public Leveled(){
		offCenter = idenityMx();
		centered = idenityMx();
		uncentered = idenityMx();
	}
	
	/**
	 * Returns the transformation matrix necessary to transform this object to its higher plane.
	 * @return The transformation matrix necessary to transform this object to its higher plane.
	 */
	public TransformMatrix getToHigher(){
		return offCenter.concat(centered.concat(uncentered));
	}

	@Override
	public void move(double x, double y) {
		
		uncentered = uncentered.concat(moveMx(x, y));
	}
	
	@Override
	public void move(Point change) {
		move(change.getX(), change.getY());
	}

	@Override
	public void rotate(double degrees) {
		centered = centered.concat(rotateAroundOriginMx(degrees));
	}

	@Override
	public void rotateAround(double degrees, double x, double y) {
		uncentered = uncentered.concat(rotateRelToMx(degrees, x, y));
	}

	@Override
	public void setCenter(double cx, double cy) {
		offCenter = moveMx(-cx, -cy);	
	}
	
	/**
	 * Returns the x cord of this object.
	 * @return The x cord of this object.
	 */
	public double getX(){
		return getToHigher().getArray()[2][0];
	}
	
	/**
	 * Returns the y cord of this object.
	 * @return The y cord of this object.
	 */
	public double getY(){
		return getToHigher().getArray()[2][1];
	}
}

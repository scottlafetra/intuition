package com.lafetra.scott.intuition;

import com.lafetra.scott.intuition.geom.Point;
import com.lafetra.scott.intuition.geom.TransformMatrix;

public class BoundingBox {
	
	private double leftLimit, rightLimit, upLimit, downLimit;

	public BoundingBox(double leftLimit, double rightLimit,double upLimit, double downLimit ) {
		this.leftLimit = leftLimit;
		this.rightLimit = rightLimit;
		this.upLimit = upLimit;
		this.downLimit = downLimit;
	}
	
	/**
	 * Returns the upper limit.
	 * @return The upper limit.
	 */
	public double getUpLimit(){
		return upLimit;
	}
	
	/**
	 * Returns the downwards limit.
	 * @return The downwards limit.
	 */
	public double getDownLimit(){
		return downLimit;
	}
	
	/**
	 * Returns the left most limit.
	 * @return The left most limit.
	 */
	public double getLeftLimit(){
		return leftLimit;
	}
	
	/**
	 * Returns the right most limit.
	 * @return The right most limit.
	 */
	public double getRightLimit(){
		return rightLimit;
	}
	
	/**
	 * Returns a transformed bounding box.
	 * @param toApply The transformation to apply.
	 * @return The transformed bounding box.
	 */
	public BoundingBox transform(TransformMatrix toApply){
		
		Point tl = TransformMatrix.getTransformedPoint(leftLimit ,   upLimit, toApply);//top-left
		Point br = TransformMatrix.getTransformedPoint(rightLimit, downLimit, toApply);//bottom-right
		
		return new BoundingBox(tl.getX(), br.getX(), tl.getY(), br.getY());
	}
	
	@Override
	public String toString(){
		return "Left: " + (int)getLeftLimit() + " Right: " + (int)getRightLimit() + " Up: " + (int)getUpLimit() + " Down: " + (int)getDownLimit();
	}

}

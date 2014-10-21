package com.lafetra.scott.intuition.geom;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

public class Point extends Leveled implements Transformable{

	private double x, y;//In relation to higher plane only
	
	public Point(double x, double y){
		this.x = x;
		this.y = y;	
	}
	
	public double getX(){
		return x + super.getX();
	}
	
	public double getY(){
		return y + super.getY();
	}
	

	/**
	 * Draws the point on the screen.
	 * @param trans Transformation matrix to apply.
	 */
	public void draw(ArrayList<TransformMatrix> trans) {
		TransformMatrix combined = TransformMatrix.idenityMx();
		
		for(int i = trans.size() - 1; i >= 0; i--){
			combined = combined.concat(trans.get(i));
		}
		
		Point transformed = TransformMatrix.getTransformedPoint(x, y, combined);
		
		glVertex2d(transformed.getX(), transformed.getY());
	}
}

	
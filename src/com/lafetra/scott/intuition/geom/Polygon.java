package com.lafetra.scott.intuition.geom;


import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;

import java.util.ArrayList;
import java.util.Arrays;


abstract public class Polygon extends Leveled implements Shape /*, Collidable*/{
	
	protected Point[] points;
	protected int shape;
	
	private float r, g, b, a;
	
	/**
	 * Creates a black polygon.
	 */
	public Polygon(){
		r = g = b = 0;
		a = 1;
		setShape();
	}
	
	/**
	 * Assigns points to this polygon.
	 * @param points
	 */
	protected void setPoints(Point[] points){
		this.points = points;
	}
	
	/**
	 * Draws this polygon to the screen.
	 */
	@Override
	public void draw() {
		draw(new ArrayList<TransformMatrix>());
	}

	/**
	 * Sets the type of openGL shape is to be drawn.
	 */
	abstract protected void setShape();
	
	protected int getShape(){
		return shape;
	}
	
	@Override
	public void draw(ArrayList<TransformMatrix> trans) {
		glColor4f(r, g, b, a);
		
		ArrayList<TransformMatrix> toApply = (ArrayList<TransformMatrix>) trans.clone();
		toApply.add(getToHigher());
		
		glBegin(shape);
			for(Point point : points){
				point.draw(toApply);
			}
		glEnd();
	}

	@Override
	public void setColor(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public float getColorR(){
		return r;
	}
	
	public float getColorG(){
		return g;
	}
	
	public float getColorB(){
		return b;
	}
	
	@Override
	public ArrayList<Point> getPoints(){
		ArrayList<Point> pointList = new ArrayList<Point>(Arrays.asList(points));
		
		for(int i = 0; i < pointList.size(); i++){
			pointList.set(i, TransformMatrix.getTransformedPoint(pointList.get(i).getX(), pointList.get(i).getY(), getToHigher()));
		}
		
		return pointList;
	}

}

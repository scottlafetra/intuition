package com.lafetra.scott.intuition.geom;


import static org.lwjgl.opengl.GL11.GL_LINES;

import java.util.ArrayList;

public class Line extends Polygon{
	
	public Line(double x1, double y1, double x2, double y2){
		this(new Point(x1, y1), new Point(x2, y2));
	}
	
	public Line(Point a, Point b){
		Point[] points = new Point[2];
		points[0] = a;
		points[1] = b;
		
		setPoints(points);
	}
	
	public Point getA(){
		return getPoints().get(0);
	}
	
	public Point getB(){
		return getPoints().get(1);
	}
	
	public double getSlope(){
		
		Point a = getA();
		Point b = getB();
		
		double rise = b.getY() - a.getY();
		double run  = b.getX() - a.getX();
		
		if(run == 0) 
			throw new UnsupportedOperationException("Slope is vertical");
		else 
			return rise/run;
	}
	
	public double getYIntercept(){
		return getA().getY() - getSlope()*getA().getX(); 
	}
	
	public boolean doesIntersect(Line toCheck){
		

		Point a = getA();
		Point b = getB();
		
		Point a2 = toCheck.getA();
		Point b2 = toCheck.getB();
		
		double m1, m2;//slopes
		
		m1 = 0;//Get the compiler to shut up about uninitialized
		m2 = 0;//Special cases will filter out uninitialized m1 and m2
		
		boolean m1Vertical = false;
		boolean m2Vertical = false;
		
		double minX = Math.min(a.getX(), b.getX());
		double maxX = Math.max(a.getX(), b.getX());
		//get min and max y as well
		double minY = Math.min(a.getY(), b.getY());
		double maxY = Math.max(a.getY(), b.getY());
		
		try{
			m1 = getSlope();
		} catch (UnsupportedOperationException e){
			m1Vertical = true;
		}
		
		try{
			m2 = toCheck.getSlope();
		} catch (UnsupportedOperationException e){
			m2Vertical = true;
		}
		
		//Handle special cases
		if(m1Vertical & m2Vertical)//both vertical 
			return false;
		
		if(m1Vertical){//One vertical
			
			//Redefine min and max to be from tocheck
			minX = Math.min(a2.getX(), b2.getX());
			maxX = Math.max(a2.getX(), b2.getX());
			//get min and max y as well
			minY = Math.min(a2.getY(), b2.getY());
			maxY = Math.max(a2.getY(), b2.getY());
			
			if(((a.getX() < minX && b.getX() < minX)||(a.getX() > maxX && b.getX() > maxX)) || 
			    (a.getY() > maxY && b.getY() > maxY)||(a.getY() < minY && b.getY() < minY))
				return false;
			else
				return true;
			
		} else if (m2Vertical){
			
			if(((a2.getX() < minX && b2.getX() < minX ) || (a2.getX() > maxX && b2.getX() > maxX)) ||
			    (a2.getY() > maxY && b2.getY() > maxY)||(a2.getY() < minY && b2.getY() < minY))
				return false;
			else
				return true;
		}
		
		if(m1 == 0){//one horizontal
			//Redefine min and max to be from tocheck
			minX = Math.min(a2.getX(), b2.getX());
			maxX = Math.max(a2.getX(), b2.getX());
			//get min and max y as well
			minY = Math.min(a2.getY(), b2.getY());
			maxY = Math.max(a2.getY(), b2.getY());
			
			if(((a.getY() < minY && b.getY() < minY)||(a.getY() > maxY && b.getY() > maxY)) || 
			    (a.getX() > maxX && b.getX() > maxX)||(a.getX() < minX && b.getX() < minX))
				return false;
			else
				return true;
			
		} else if(m2 == 0){
			
			if(((a2.getY() < minY && b2.getY() < minY ) || (a2.getY() > maxY && b2.getY() > maxY)) ||
			    (a2.getX() > maxX && b2.getX() > maxX)||(a2.getX() < minX && b2.getX() < minX))
						return false;
					else
						return true;
			
		}
		
		double xIntersect = (toCheck.getYIntercept() - getYIntercept())/(m1 - m2);
		
		if(xIntersect < minX || xIntersect > maxX)//Check if intercept is on line segment
			return false;
		
		return true;
		
	}

	@Override
	protected void setShape() {
		shape = GL_LINES;
	}
	
	

}

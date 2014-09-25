package com.lafetra.scott.intuition.space;

import com.lafetra.scott.intuition.geom.Rectangle;
import com.lafetra.scott.intuition.physics.TangibleGroup;

public /*abstract*/ class Ship extends TangibleGroup {
	
	private double degreesRotated;
	
	private double maxSpeed;//for free flight mode
	private double throttle;//for maneuver mode
	private double warpFactor;//for warp flight mode
	
	public enum ViewMode{
		TOPDOWN, SIDE
	}

	public Ship(double mass, ViewMode view) {
		super(mass);
		setViewMode(view);
		degreesRotated = 90;//if up on the screen is considered front while drawing
		
	}
	
	public void setViewMode(ViewMode view){
		removeAll();//Clears the current shapeGroup
		
		switch(view){
		case SIDE:
			add(new Rectangle(-50, -25, 100, 50));
			break;
		case TOPDOWN:
			add(new Rectangle(-25, -50, 50, 100));
			break;
		}
	}
	
	public void turnToFace(double x, double y){
		
		y = Game.HEIGHT - y;//Turn mouse cords into left handed cords
		
		//Get this ship's point
		double myX = getX();
		double myY = getY();
		
		double dx = x - myX;
		double dy = y - myY;
		
		double hype = Math.sqrt(dx*dx + dy*dy);//
		
		if(hype == 0) return;//Prevent dividing by zero
		
		//find the angle, unadjusted for quarters
		double angle = Math.toDegrees(Math.asin(dy/hype));
		
		System.out.println(angle);//TODO: Remove
		//System.out.println(x + "," + myX + "  |  " + y + "," + myY);//TODO: Remove
		if(dx > 0 && dy > 0){
			angle = -angle;
		} else if(dx < 0 && dy > 0){//2nd quarter
			angle = 180 + angle;
		} else if(dx < 0 && dy < 0){//3rd quarter
			angle = 180 + angle;
		} else if(dx > 0 && dy < 0){//4st quarter
			angle = 360 - angle;
		}

		//System.out.println(angle);
		rotate(angle - degreesRotated);
		
		degreesRotated = angle;
		
		
		
	}

}

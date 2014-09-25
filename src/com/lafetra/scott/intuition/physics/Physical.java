package com.lafetra.scott.intuition.physics;

import com.lafetra.scott.intuition.geom.Point;
import com.lafetra.scott.intuition.geom.Shape;
import com.lafetra.scott.intuition.geom.Transformable;

public interface Physical extends Shape{
	
	public double getMass();
	public void setMass(double m);//mass in kg

	public Point getVelocity();
	public void setVelocity(double x, double y);//m/s
	
	public Point getAcceleration();
	public void setAcceleration(double x, double y);//m/s^2
	
	public void applyForce(double fx, double fy);//force in Newtons
	public Point getCurrentForceTotals();
	
	public void calcAccel();//calculates acceleration based on force and mass
	                        //Call after all forces are applied
	
	
}

package com.lafetra.scott.intuition.physics;

import com.lafetra.scott.intuition.geom.Point;
import com.lafetra.scott.intuition.geom.ShapeGroup;

public class PhysicsGroup extends ShapeGroup implements Physical {
	
	private double mass;
	private double vx, vy;//velocity x & y;
	private double ax, ay;//acceleration x & y;
	
	private double forceX, forceY;//force x & y;
	
	
	

	public PhysicsGroup(double mass) {
		setMass(mass);
		vx = vy = ax = ay = forceX = forceY = 0;
	}

	@Override
	public double getMass() {
		return mass;
	}

	@Override
	public void setMass(double m) {
		this.mass = Math.abs(m);
	}

	@Override
	public Point getVelocity() {
		return new Point(vx, vy);
	}

	@Override
	public void setVelocity(double x, double y) {
		vx = x;
		vy = y;
	}

	@Override
	public Point getAcceleration() {
		return new Point(ax, ay);
	}

	@Override
	public void setAcceleration(double x, double y) {
		vx = x;
		vy = y;
	}

	@Override
	public void applyForce(double fx, double fy) {
		forceX += fx;
		forceY += fy;
	}

	@Override
	public void calcAccel() {
		ax = forceX/mass;
		ay = forceY/mass;
		
		forceX = forceY = 0;//Resets force count
	}

	@Override
	public Point getCurrentForceTotals() {
		return new Point(forceX, forceY);
	}

}

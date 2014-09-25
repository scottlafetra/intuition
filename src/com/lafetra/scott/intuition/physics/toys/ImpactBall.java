package com.lafetra.scott.intuition.physics.toys;

import com.lafetra.scott.intuition.BoundingBox;
import com.lafetra.scott.intuition.aztecBall.Ball;

public class ImpactBall extends Ball {
	
	private static final double IMPACT_THRESHHOLD = 1;
	
	private String id;

	public ImpactBall(float r, float g, float b) {
		super(r, g, b);
		id = "(" + r + ", " + g + ", " + b + ")";
	}
	
	@Override
	public void correctYTo(BoundingBox correct){
		if(Math.abs(getVelocity().getY()) > IMPACT_THRESHHOLD)
			System.out.println(toString() + ":  Impact Velocity: " + getVelocity().getX() + ", " + getVelocity().getY() ); 
		
		super.correctYTo(correct);
	
	}
	
	@Override
	public void correctXTo(BoundingBox correct){
		if(Math.abs(getVelocity().getX()) > IMPACT_THRESHHOLD)
			System.out.println(toString() + ":  Impact Velocity: " + getVelocity().getX() + ", " + getVelocity().getY() ); 
		
		super.correctXTo(correct);
	
	}
	
	@Override
	public String toString(){
		return id;
	}

}

package com.lafetra.scott.intuition.body;

import javax.naming.NameNotFoundException;

import com.lafetra.scott.intuition.Animatable;
import com.lafetra.scott.intuition.Animation;
import com.lafetra.scott.intuition.geom.Geom;
import com.lafetra.scott.intuition.geom.Point;
import com.lafetra.scott.intuition.geom.ShapeGroup;

public class Leg extends ShapeGroup implements Animatable{
	
	private Thigh thigh;
	private Shin shin;
	
	private Animation animation;
	private boolean animating;
	private long animateStartTime;
	private double lastHipAngle;
	private double lastKneeAngle;
	private boolean backLeg;
	
	/**
	 * Constructs a leg according to a given scale
	 * @param scale The scale to use for this.
	 * @param left True if the leg is a left leg, false if not.
	 */
	public Leg(double scale, boolean left){
		
		thigh = new Thigh(scale*2, scale*3, left);
		shin = new Shin(scale*(8.0/3.0), scale*4, left);
		
		add(thigh);
		add(shin);
		
		updatePos();
		
		animation = null;
		animateStartTime = -1;
		lastHipAngle = 0;//Defines the starting position as 0 radians.
		lastKneeAngle = 0;
		backLeg = false;
	}
	
	private void updatePos(){
		Point mnt = thigh.getMountPoint();
		shin.move(mnt.getX() - shin.getX(), mnt.getY() - shin.getY());
	}
	
	public void bend(double degrees){
		shin.rotate(degrees);
	}

	@Override
	public void startAnimation(Animation animation) {
		this.animation = animation;
		animateStartTime = System.currentTimeMillis();;
		
		animate();
	}

	@Override
	public void animate() {
		if(animateStartTime == -1) return;//If has yet to be started, exit
		
		long now = System.currentTimeMillis();
		
		switch(animation){
		
			case LEG_WALK_LEFT:
				aWalk();
				break;
			case LEG_WALK_RIGHT:
				aWalk();
				break;
		}
		
	}
//remember to add precondition to java doc
	private void aWalk() {
		
		double time = System.currentTimeMillis() - animateStartTime;
		
		if(backLeg) time -= Geom.TAU/(2.0*WALK_SPEED*WALK_ANIMATE_RATIO);//Offset by half a circle
		
		time *= WALK_ANIMATE_RATIO;
		
		double angleNow;
		
		if(animation == Animation.LEG_WALK_LEFT) time += Geom.TAU/2.0;
		
		for(;time > Geom.TAU;) time -= Geom.TAU;//TODO: do this double modulous better
		
		if(time < (2.0/3.0)*Geom.TAU)//Calculate knee bending
			angleNow = -Math.sin(time*(1.5/2.0));
		else
			angleNow =0;
		
		if(animation == Animation.LEG_WALK_LEFT) angleNow *= -1;// Reverse direction if leftward
		shin.rotate(angleNow*MAX_KNEE_ANGLE_WALK - lastKneeAngle);
		
		lastKneeAngle = angleNow*MAX_KNEE_ANGLE_WALK;
		
		
		time -= Geom.TAU/3.0;//Offset hip twist
		
		angleNow = Math.sin(time);//Calculate hip bending
		
		
		if(animation == Animation.LEG_WALK_LEFT) angleNow *= -1;// Reverse direction if leftward
		rotate(angleNow*MAX_HIP_ANGLE_WALK - lastHipAngle);
		
		lastHipAngle = angleNow*MAX_HIP_ANGLE_WALK;
			
	}

	@Override
	public boolean isAnimationDone() {
		return animating;
	}

	@Override
	public void stopAnimating() {
		animateStartTime = -1;
		
	}
	
	public Animation getAnimation(){
		return animation;
	}
	
	public void assignBackAnimation(){
		//Offset by half a cycle
		backLeg = true;
		animate();
	}
	

}

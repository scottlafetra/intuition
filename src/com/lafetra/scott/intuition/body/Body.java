package com.lafetra.scott.intuition.body;

import javax.naming.NameNotFoundException;

import com.lafetra.scott.intuition.Animatable;
import com.lafetra.scott.intuition.Animation;
import com.lafetra.scott.intuition.Game;
import com.lafetra.scott.intuition.geom.Point;
import com.lafetra.scott.intuition.geom.ShapeGroup;
import com.lafetra.scott.intuition.physics.TangibleGroup;

public class Body extends TangibleGroup implements Animatable{
	
	private Chest chest;
	private Head  head;
	private Arm   lArm;
	private Arm   rArm;
	private Leg   lLeg;
	private Leg   rLeg;
	
	private boolean inMotion;
	
	private double scale; //remove in final version
	
	public Body(double scale){
		
		super(80);
		
		this.scale = scale;
		
		chest = new Chest(scale);
		head  = new Head(scale, 0, 1, 0);
		lArm = new Arm(scale*1.25);
		rArm = new Arm(scale*1.25);
		lLeg = new Leg(scale*0.85, true);
		rLeg = new Leg(scale*0.85, false);
		
		add(chest);
		add(head);
		add(lArm);
		add(rArm);
		add(lLeg);
		add(rLeg);
		
		updatePos();
		
		inMotion = false;
	}
	
	/**
	 * Update the position of all the parts to match the mount points.
	 */
	public void updatePos(){
		Point mnt = chest.getHeadMount();
		head.move(mnt.getX() - head.getX(), mnt.getY() - head.getY());
		
		mnt = chest.getLAMount();
		lArm.move(mnt.getX() - lArm.getX(), mnt.getY() - lArm.getY());
		
		mnt = chest.getRAMount();
		rArm.move(mnt.getX() - rArm.getX(), mnt.getY() - rArm.getY());
		
		mnt = chest.getLLMount();
		lLeg.move(mnt.getX() - lLeg.getX(), mnt.getY() - lLeg.getY());
		
		mnt = chest.getRLMount();
		rLeg.move(mnt.getX() - rLeg.getX(), mnt.getY() - rLeg.getY());
		
		updateBounds();
	}
	
	public void bend(double degrees){
		head.rotate(degrees);
		lArm.rotate(degrees);
		rArm.rotate(degrees);
		lLeg.rotate(degrees);
		rLeg.rotate(degrees);
	}

	@Override
	public void startAnimation(Animation animation) throws NameNotFoundException {
		switch(animation){
		case LEG_WALK_LEFT:
			lLeg.startAnimation(Animation.LEG_WALK_LEFT);
			rLeg.startAnimation(Animation.LEG_WALK_LEFT);
			rLeg.assignBackAnimation();
			break;
		case LEG_WALK_RIGHT:
			lLeg.startAnimation(Animation.LEG_WALK_RIGHT);
			lLeg.assignBackAnimation();
			rLeg.startAnimation(Animation.LEG_WALK_RIGHT);
			break;
		default:
			throw new NameNotFoundException("Animation not applicable tothis object (body)");
			
		}
		
	}

	@Override
	public void animate() {
		lLeg.animate();
		rLeg.animate();
		
		updateBounds();
	}

	@Override
	public void stopAnimating() {
		lLeg.stopAnimating();
		rLeg.stopAnimating();
	}

	@Override
	public boolean isAnimationDone() {
		return rLeg.isAnimationDone() && lLeg.isAnimationDone();
	}
	
	public void walk(boolean left){
		
		
	}
	

}

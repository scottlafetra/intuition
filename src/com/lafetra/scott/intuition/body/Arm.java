package com.lafetra.scott.intuition.body;

import com.lafetra.scott.intuition.geom.Point;
import com.lafetra.scott.intuition.geom.ShapeGroup;

public class Arm extends ShapeGroup {
	
	private UpperArm upperArm;
	private Gauntlet gauntlet;
	
	/**
	 * Makes an arm with the given scale.
	 * @param scale The scale to use.
	 */
	public Arm(double scale){
		upperArm = new UpperArm(scale, scale*1.5);
		gauntlet = new Gauntlet(scale, scale*2);
		
		updatePos();
		
		add(upperArm);
		add(gauntlet);
	}
	
	/**
	 * Updates the position of all mounted points.
	 */
	public void updatePos(){
		Point mnt = upperArm.getMountPoint();
		gauntlet.move(mnt.getX() - gauntlet.getX(), mnt.getY() - gauntlet.getY());
	}
	
	public void bend(double degrees){
		gauntlet.rotate(degrees);
	}

}

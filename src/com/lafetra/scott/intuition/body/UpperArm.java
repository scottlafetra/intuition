package com.lafetra.scott.intuition.body;

import com.lafetra.scott.intuition.geom.Circle;
import com.lafetra.scott.intuition.geom.Point;
import com.lafetra.scott.intuition.geom.Rectangle;
import com.lafetra.scott.intuition.geom.ShapeGroup;

public class UpperArm extends ShapeGroup{
	
	private Point mountPoint;//the point to mount a gauntlet too.
	
	/**
	 * Creates an upper arm piece with the given dimensions.
	 * @param width The width of the upper arm piece.
	 * @param height The height of the upper arm piece.
	 */
	public UpperArm(double width, double height){
		
		double space = width/2.0;
		
		add(new Circle(0, space, space, 10));
		
		add(new Rectangle(-space, space, width, height - space));
		
		add(new Circle(0, height, space, 10));
		
		mountPoint = new Point(0, height);
		
	}
	
	/**
	 * Returns the point at which another limb section may be attached.
	 * @return
	 */
	public Point getMountPoint(){
		return mountPoint;
	}

}

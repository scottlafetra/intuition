package com.lafetra.scott.intuition.body;


import com.lafetra.scott.intuition.geom.Circle;
import com.lafetra.scott.intuition.geom.Point;
import com.lafetra.scott.intuition.geom.Rectangle;
import com.lafetra.scott.intuition.geom.ShapeGroup;
import com.lafetra.scott.intuition.geom.Triangle;

public class Thigh extends ShapeGroup {
	
	private Point mountPoint;//the point to mount a s too
	
	/**
	 * Creates a thigh piece with the given dimensions.
	 * @param width The width of the thigh piece.
	 * @param height The height of the thigh piece.
	 * @param left True if it is a left thigh, false if not.
	 */
	public Thigh(double width, double height, boolean left){
		
		double flankWidth = width/3.0;
		double rectWidth = width - flankWidth;
		double space = rectWidth/2.0;
		
		add(new Circle(0, space, space, 20));
		add(new Rectangle(-space,space, rectWidth, height));
		
		if(left){
			add(new Triangle(-space,space,  -space, space + height,  -space - flankWidth,space + height));
			mountPoint = new Point(-space/2.0, height - space/2.0 - 5);//the - 5 is to prevent gaps
		} else {
			add(new Triangle(+space,space,  +space, space + height,  space + flankWidth,space + height));
			mountPoint = new Point( space/2.0, height - space/2.0 - 5);//the - 5 is to prevent gaps
		}
		
		
	}
	
	/**
	 * Returns the point at which another limb section may be attached.
	 * @return
	 */
	public Point getMountPoint(){
		return mountPoint;
	}

}

package com.lafetra.scott.intuition.body;

import com.lafetra.scott.intuition.geom.Point;
import com.lafetra.scott.intuition.geom.Rectangle;
import com.lafetra.scott.intuition.geom.ShapeGroup;
import com.lafetra.scott.intuition.geom.Triangle;

public class Chest extends ShapeGroup {
	
	private Point head, leftArm, rightArm, leftLeg, rightLeg;//mount point
	
	/**
	 * Creates a chest piece with the given dimensions.
	 * @param width The width of the chest piece.
	 * @param height The height of the chest piece.
	 */
	public Chest(double width, double height){
		
		double flankWidth = width/6.0;
		double widestPoint = height * (3.0/8.0);
		double baseWidth = width - flankWidth;
		
		//Center
		add(new Rectangle(0, 0, baseWidth, height));
		//Left flank
		add(new Triangle(0,0,           -flankWidth,widestPoint, 0,widestPoint));
		add(new Triangle(0,widestPoint, -flankWidth,widestPoint, 0,height));
		//Right flank
		add(new Triangle(baseWidth,0,       width,widestPoint,  baseWidth,widestPoint));
		add(new Triangle(baseWidth,height,  width,widestPoint,  baseWidth,widestPoint));
		
		head = new Point(baseWidth/2.0, 0);
		
		leftArm  = new Point(0, height/12.0);
		rightArm = new Point(baseWidth, height/12.0);
		
		leftLeg  = new Point(baseWidth/4.0    , height - baseWidth/4.0);
		rightLeg = new Point(baseWidth*3.0/4.0, height - baseWidth/4.0);
	}
	
	public Chest(double scale){
		this(3 * scale*0.9, 4 * scale);
	}
	
	/**
	 * Returns the place for the head to be mounted.
	 * @return The place for the head to be mounted.
	 */
	public Point getHeadMount(){
		return head;
	}
	
	/**
	 * Returns the place for the left arm to be mounted.
	 * @return The place for the left arm to be mounted.
	 */
	public Point getLAMount(){
		return leftArm;
	}
	
	/**
	 * Returns the place for the right arm to be mounted.
	 * @return The place for the right arm to be mounted.
	 */
	public Point getRAMount(){
		return rightArm;
	}
	
	/**
	 * Returns the place for the left leg to be mounted.
	 * @return The place for the left leg to be mounted.
	 */
	public Point getLLMount(){
		return leftLeg;
	}
	
	/**
	 * Returns the place for the right leg to be mounted.
	 * @return The place for the right leg to be mounted.
	 */
	public Point getRLMount(){
		return rightLeg;
	}

}

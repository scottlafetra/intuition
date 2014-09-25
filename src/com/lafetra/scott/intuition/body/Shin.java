package com.lafetra.scott.intuition.body;

import com.lafetra.scott.intuition.geom.Circle;
import com.lafetra.scott.intuition.geom.Rectangle;
import com.lafetra.scott.intuition.geom.ShapeGroup;
import com.lafetra.scott.intuition.geom.Triangle;

public class Shin extends ShapeGroup {
	
	/**
	 * Creates a shin piece with the given dimensions.
	 * @param width The width of the shin piece.
	 * @param height The height of the shin piece.
	 * @param left True if it is a left shin, false if not.
	 */
	public Shin(double width, double height, boolean left){
		
		double flankWidth = width/4.0;
		double rectWidth = width - flankWidth;

		double space = rectWidth/2.0;//small space
		double sSpace = rectWidth/4.0;//small space
		
		add(new Circle(0, sSpace, sSpace, 10));
		add(new Rectangle(-sSpace, sSpace, sSpace*2, sSpace));
		
		add(new Rectangle(-space, space,  rectWidth, height));
		
		if(left){
			add(new Triangle(-space,space,  -space, space + height,  -space - flankWidth,space + height));
		} else {
			add(new Triangle(space,space,  space, space + height,  space + flankWidth,space + height));
		}
	}

}

package com.lafetra.scott.intuition.body;

import com.lafetra.scott.intuition.geom.Circle;
import com.lafetra.scott.intuition.geom.Rectangle;
import com.lafetra.scott.intuition.geom.ShapeGroup;
import com.lafetra.scott.intuition.geom.Triangle;

public class Gauntlet extends ShapeGroup {
	
	/**
	 * Constructs a gauntlet with the specified dimensions.
	 * @param width The width of the gauntlet.
	 * @param length The length of the gauntlet.
	 */
	public Gauntlet(double width, double length){
		
		double handRadius = ((length*3.0)/8.0)/2.0;
		double flankWidth = (width*1.0)/8.0;
		double rectWidth = width - 2.0*flankWidth;
		double rectLength = length - 2.0*handRadius;
		
		
		add(new Rectangle(-rectWidth/2.0, 0,  rectWidth, rectLength));
		add(new Triangle(-rectWidth/2.0, 0,  -rectWidth/2.0 - flankWidth,0,  -rectWidth/2.0, rectLength ));
		add(new Triangle(rectWidth/2.0, 0,  rectWidth/2.0 + flankWidth,0,  rectWidth/2.0, rectLength ));
		
		add(new Circle(0, rectLength + handRadius - 2,  handRadius, 15));//the - 2 is to prevent gaps
		add(new Circle(0, rectLength + handRadius - 2,  handRadius, 15));//the - 2 is to prevent gaps
		add(new Circle(0, rectLength + handRadius - 2,  handRadius, 15));//the - 2 is to prevent gaps
		add(new Circle(0, rectLength + handRadius - 2,  handRadius, 15));//the - 2 is to prevent gaps
		add(new Circle(0, rectLength + handRadius - 2,  handRadius, 15));//the - 2 is to prevent gaps
		add(new Circle(0, rectLength + handRadius - 2,  handRadius, 15));//the - 2 is to prevent gaps
		add(new Circle(0, rectLength + handRadius - 2,  handRadius, 15));//the - 2 is to prevent gaps
		add(new Circle(0, rectLength + handRadius - 2,  handRadius, 15));//the - 2 is to prevent gaps
		add(new Circle(0, rectLength + handRadius - 2,  handRadius, 15));//the - 2 is to prevent gaps
		add(new Circle(0, rectLength + handRadius - 2,  handRadius, 15));//the - 2 is to prevent gaps
		add(new Circle(0, rectLength + handRadius - 2,  handRadius, 15));//the - 2 is to prevent gaps
		add(new Circle(0, rectLength + handRadius - 2,  handRadius, 15));//the - 2 is to prevent gaps
	}
}

package com.lafetra.scott.intuition.geom;

import static com.lafetra.scott.intuition.geom.Geom.TAU;

public class Circle extends ShapeGroup {
	
	/**
	 * Creates a circle with the given properties.
	 * @param x The x cord of the center of the circle.
	 * @param y The x cord of the center of the circle.
	 * @param radius The radius of the circle.
	 * @param resolution How many triangles to use to construct the circle out of.
	 */
	public Circle(double x, double y, double radius, int resolution){
		
		if(resolution < 1) throw new IllegalArgumentException();
		
		double lastX = radius;
		double lastY = 0;
		
		double increment = TAU/resolution;
		
		for(int c = 1; c <= resolution; c++){
			
			double newX = Math.cos(increment * c) * radius;
			double newY = Math.sin(increment * c) * radius;;
			
			add(new Triangle(x, y, x + lastX, y + lastY, x + newX, y + newY));
			
			lastX = newX;
			lastY = newY;
		}
	}
}

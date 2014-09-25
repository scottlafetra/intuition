package com.lafetra.scott.intuition.body;

import com.lafetra.scott.intuition.geom.Circle;
import com.lafetra.scott.intuition.geom.Rectangle;
import com.lafetra.scott.intuition.geom.ShapeGroup;
import com.lafetra.scott.intuition.geom.Triangle;

public class Head extends ShapeGroup {
	
	/**
	 * Constructs a head of the size indicated.	
	 * @param size The size in radius of head
	 * @param r The r value of the color of the head.
	 * @param g The g value of the color of the head.
	 * @param b The b value of the color of the head.
	 */
	public Head(double size, float r, float g, float b){
		
		Rectangle flare = new Rectangle(size/2.0, size/2.0, size, size);
		flare.setColor(r, g, b);
		flare.move(-size * 2.25, -size * 3.25);
		
		Triangle tri = new Triangle(-2.5*size, 0,  -2.5*size, -2.5*size,  0, -2.5*size);
		tri.move(size, 0);
		add(tri);
		
		add(new Circle(0, -size, size, 25));
		
		add(flare);
	}
}

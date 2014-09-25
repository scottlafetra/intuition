package com.lafetra.scott.intuition;

import com.lafetra.scott.intuition.geom.Line;


public interface Collidable extends Bounded {
	public boolean inBounds(double x, double y);
	public boolean inBounds(BoundingBox bounds);
	public boolean inBoundsX(BoundingBox bounds);
	public boolean inBoundsY(BoundingBox bounds);
	public boolean isIntersecting(Line line);
	
	
	public void correctXTo(BoundingBox correct);//Corrects the position to not collide
	public void correctYTo(BoundingBox correct);//Corrects the position to not collide
}

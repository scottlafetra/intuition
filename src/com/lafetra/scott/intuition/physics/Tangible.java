package com.lafetra.scott.intuition.physics;

import com.lafetra.scott.intuition.BoundingBox;
import com.lafetra.scott.intuition.Collidable;

public interface Tangible extends Physical, Collidable {
	
	public void smearMove(double x, double y);//Moves while smearing the bounding box
	public void smearRotate(double degrees);
	public void releaseSmear(); //Undoes the smear
	public BoundingBox getTotalBounds();//gets bounds + smear
	
	public BoundingBox getXSmear();//smears in the x or y direction
	public BoundingBox getYSmear();
	
	public void setFixed(boolean fixed);
	public boolean isFixed();//True if not movable by a physics operator
	
	public void setContact(boolean contact);
	public boolean getContact();
	
}

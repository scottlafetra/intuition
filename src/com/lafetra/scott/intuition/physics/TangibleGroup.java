package com.lafetra.scott.intuition.physics;

import java.util.ArrayList;

import com.lafetra.scott.intuition.BoundingBox;
import com.lafetra.scott.intuition.geom.Line;
import com.lafetra.scott.intuition.geom.Point;
import com.lafetra.scott.intuition.geom.Shape;
import com.lafetra.scott.intuition.geom.TransformMatrix;

public class TangibleGroup extends PhysicsGroup implements Tangible{
	
	private BoundingBox smearBox;
	private BoundingBox bounds;
	private boolean boundsOutOfDate;//true if bounds must be updated
	private boolean fixed;
	private boolean inContact;

	public TangibleGroup(double mass) {
		super(mass);
		bounds = new BoundingBox(0,0,0,0);
		smearBox = null;
		updateBounds();
		fixed = false;
		inContact = false;
	}

	

	@Override
	public void smearMove(double x, double y) {
		smearBox = bounds.transform(getToHigher());
		
		move(x, y);
	}
	
	@Override
	public void smearRotate(double degrees) {
		smearBox = bounds.transform(getToHigher());
		
		rotate(degrees);
	}

	@Override
	public void releaseSmear() {
		smearBox = null;
	}
	
	@Override
	public void add(Shape shape){
		super.add(shape);
		boundsOutOfDate = true;
	}
	
	@Override
	public void rotate(double degrees){
		super.rotate(degrees);
		boundsOutOfDate = true;
	}
	
	@Override
	public void rotateAround(double degrees, double x, double y){
		super.rotateAround(degrees, x, y);
		boundsOutOfDate = true;
	}

	@Override
	public BoundingBox getBoundBox() {
		
		if(boundsOutOfDate) 
			updateBounds();
		
		return bounds.transform(getToHigher());
	}
	
	public void updateBounds(){
		
		smearBox = bounds.transform(getToHigher());
		
		ArrayList<Point> points = getLocalPoints();
		
		if(points.size() == 0){
			bounds = new BoundingBox(0, 0, 0, 0);
			
		} else {
			Point firstPt = points.get(0);
			
			double leftMost   = firstPt.getX();
			double rightMost   = firstPt.getX();
			double upMost     = firstPt.getY();
			double downMost   = firstPt.getY();
			
			for(int i = 1; i < points.size(); i++){
				Point pt = points.get(i);
				
				leftMost   = Math.min(pt.getX(), leftMost);
				rightMost  = Math.max(pt.getX(), rightMost);
				upMost     = Math.min(pt.getY(), upMost);
				downMost   = Math.max(pt.getY(), downMost);
			}
			
			
			
			bounds = new BoundingBox(leftMost, rightMost, upMost, downMost);
		}
		
		boundsOutOfDate = false;
	}
	
	@Override
	public boolean inBounds(double x, double y) {
		if(boundsOutOfDate)
			updateBounds();
		
		BoundingBox toUse = getTotalBounds();
		
		return !(x < toUse.getLeftLimit() 
		 	  || x > toUse.getRightLimit()
			  || y > toUse.getDownLimit()
			  || y < toUse.getUpLimit()   );
	}

	@Override
	public boolean inBounds(BoundingBox check) {
		
		BoundingBox toUse = getTotalBounds();
		
		if(check.getRightLimit() <= toUse.getLeftLimit() || check.getLeftLimit() >= toUse.getRightLimit()) return false;//Check x axis
		
		if(check.getDownLimit() <= toUse.getUpLimit() || check.getUpLimit() >= toUse.getDownLimit()) return false;//Check y axis
		
		return true;
	}
	
	@Override
	public boolean inBoundsX(BoundingBox check){
		BoundingBox toUse = getTotalBounds();
		
		return !(check.getRightLimit() < toUse.getLeftLimit() || check.getLeftLimit() > toUse.getRightLimit());
	}
	
	@Override
	public boolean inBoundsY(BoundingBox check){
		BoundingBox toUse = getTotalBounds();
		
		return !(check.getUpLimit() < toUse.getDownLimit() || check.getUpLimit() > toUse.getDownLimit());
	}
	

	@Override
	public boolean isIntersecting(Line line) {//NOTE: Does not check if line is completely within bounds
		
		BoundingBox toUse = getTotalBounds();
		
		//set up the sides as lines
		ArrayList<Line> sides = new ArrayList<Line>(4);
		
		sides.add(new Line(toUse.getLeftLimit(), toUse.getUpLimit(), toUse.getLeftLimit(), toUse.getDownLimit()));//left
		sides.add(new Line(toUse.getRightLimit(), toUse.getUpLimit(), toUse.getRightLimit(), toUse.getDownLimit()));//right
		
		sides.add(new Line(toUse.getLeftLimit(), toUse.getUpLimit(), toUse.getRightLimit(), toUse.getUpLimit()));//up
		sides.add(new Line(toUse.getLeftLimit(), toUse.getDownLimit(), toUse.getRightLimit(), toUse.getDownLimit()));//down
		
		for(Line side : sides){
			if(side.doesIntersect(line))
				return true;
		}
		
		return false;
	}
	
	@Override
	/**
	 * Returns the combined smearBox and bounds bounding box.
	 * @return
	 */
	public BoundingBox getTotalBounds(){
		
		if(boundsOutOfDate)
			updateBounds();
		
		if(smearBox == null)
			return bounds.transform(getToHigher());
		
		double left, right, up, down;
		
		BoundingBox toUse = bounds.transform(getToHigher());
		
		left  = Math.min(toUse.getLeftLimit() , smearBox.getLeftLimit());
		right = Math.max(toUse.getRightLimit(), smearBox.getRightLimit());
		up    = Math.min(toUse.getUpLimit()   , smearBox.getUpLimit());
		down  = Math.max(toUse.getDownLimit() , smearBox.getDownLimit());
		
		return new BoundingBox(left, right, up, down);
	}
	
	public BoundingBox getXSmear(){
		if(boundsOutOfDate)
			updateBounds();
		
		if(smearBox == null)
			return bounds.transform(getToHigher());
		
		double left, right;
		
		BoundingBox toUse = bounds.transform(getToHigher());
		
		left  = Math.min(toUse.getLeftLimit() , smearBox.getLeftLimit());
		right = Math.max(toUse.getRightLimit(), smearBox.getRightLimit());
		
		return new BoundingBox(left, right, toUse.getUpLimit(), toUse.getDownLimit());
	}
	
	public BoundingBox getYSmear(){
		if(boundsOutOfDate)
			updateBounds();
		
		if(smearBox == null)
			return bounds.transform(getToHigher());
		
		double up, down;
		
		BoundingBox toUse = bounds.transform(getToHigher());
		
		up    = Math.min(toUse.getUpLimit()   , smearBox.getUpLimit());
		down  = Math.max(toUse.getDownLimit() , smearBox.getDownLimit());
		
		return new BoundingBox(toUse.getLeftLimit(), toUse.getRightLimit(), up, down);
	}



	@Override
	public void correctXTo(BoundingBox correct) {
		Point vel = getVelocity();
		
		BoundingBox toUse = bounds.transform(getToHigher());//Adjust the bounds to be relative to higher level
		
		//correct x location
		if(vel.getX() > 0){
			move(correct.getLeftLimit() - toUse.getRightLimit() ,  0);
			setVelocity(0, getVelocity().getY());
			
		} else if (vel.getX() < 0){
			move(correct.getRightLimit() - toUse.getLeftLimit() , 0);
			setVelocity(0, getVelocity().getY());
		} 
		
		 
	}
	
	@Override
	public void correctYTo(BoundingBox correct){
		Point vel = getVelocity();
		
		BoundingBox toUse = bounds.transform(getToHigher());//Adjust the bounds to be relative to higher level
		
		//correct y location
		if(vel.getY() > 0){
				move(0, correct.getUpLimit() - toUse.getDownLimit());
				setVelocity(getVelocity().getX(), 0);
			
		} else if (vel.getY() < 0){
				move(0, correct.getDownLimit() - toUse.getUpLimit());
				setVelocity(getVelocity().getX(), 0);
		}
	}



	@Override
	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}



	@Override
	public boolean isFixed() {
		return fixed;
	}



	@Override
	public void setContact(boolean contact) {
		
		//TODO: remove
		if(inContact && !contact)
			System.out.println("beep!");
			
		inContact = contact;
		
		
	}

	@Override
	public boolean getContact() {
		return inContact;
	}
}

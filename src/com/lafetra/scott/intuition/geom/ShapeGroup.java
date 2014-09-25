package com.lafetra.scott.intuition.geom;

import java.util.ArrayList;

public class ShapeGroup extends Leveled implements Shape{
	
	private float r, g, b;
	
	double cx, cy;
	
	protected ArrayList<Shape> members;
	
	private boolean isColorSet; 
	
	public ShapeGroup(){
		
		members = new ArrayList<Shape>();
		
		isColorSet = false;
	}
	
	public void add(Shape shape){
		members.add(shape);
		
		if(isColorSet) 
			shape.setColor(r, g, b);
	}
	
	public void removeAll(){
		members = new ArrayList<Shape>();
	}
	
	public void setCenter(double cx, double cy){
		this.cx = cx;
		this.cy = cy;
	}
	
	@Override
	public void draw() {
		
		draw(new ArrayList<TransformMatrix>());
	}

	@Override
	public void draw(ArrayList<TransformMatrix> trans) {
		
		ArrayList<TransformMatrix> toApply = (ArrayList<TransformMatrix>) trans.clone();
		toApply.add(getToHigher());
		
		for(Shape shape : members){
			shape.draw(toApply);
		}
	}

	@Override
	public void setColor(float r, float g, float b) {
		isColorSet = true;
		
		this.r = r;
		this.g = g;
		this.b = b;
		
		for(Shape shape : members){
			shape.setColor(r, g, b);
		}
	}

	@Override
	public ArrayList<Point> getPoints() {
		if(members.size() == 0) return new ArrayList<Point>();
		
		ArrayList<Point> points = getLocalPoints();
		
		for(int i = 0; i < points.size(); i++){
			points.set(i, TransformMatrix.getTransformedPoint(points.get(i).getX(), points.get(i).getY(), getToHigher()));
		}
		
		return points;
	}
	
	//Sends the specified shape to be drawn last
	public void setToFront(Shape shape){
		members.remove(shape);
		members.add(shape);
	}
	
	protected ArrayList<Point> getLocalPoints() {
		if(members.size() == 0) return new ArrayList<Point>();
		
		ArrayList<Point> points = members.get(0).getPoints();
		
		for(int i = 1; i < members.size(); i++){
			points.addAll(members.get(i).getPoints());
		}
		
		return points;
	}
	
	

}

package com.lafetra.scott.intuition.aztecBall;

import com.lafetra.scott.intuition.geom.Line;
import com.lafetra.scott.intuition.geom.Rectangle;
import com.lafetra.scott.intuition.geom.ShapeGroup;
import com.lafetra.scott.intuition.physics.PhysicsOperator;
import com.lafetra.scott.intuition.physics.TangibleGroup;

public class Goal extends TangibleGroup {
	
	private Rectangle mainBox;
	private TangibleGroup topTrim;
	private TangibleGroup bottomTrim;
	private Line goalTrip;

	public Goal(double height) {
		super(1);
		setFixed(true);
		
		double width = height/2.5;
		
		mainBox = new Rectangle(0, 0, width, height);
		mainBox.move(-width/2.0, 0);
		add(mainBox);
		
		double trimHeight = height/5.0;
		
		topTrim = new TangibleGroup(1);
		topTrim.setFixed(true);
		topTrim.add(new Rectangle(0, 0, width + trimHeight, trimHeight));
		topTrim.move(-trimHeight/2.0 - width/2.0, 0);
		add(topTrim);
		
		bottomTrim = new TangibleGroup(1);
		bottomTrim.setFixed(true);
		bottomTrim.add(new Rectangle(0, 0, width + trimHeight, trimHeight));
		bottomTrim.move(-trimHeight/2.0 - width/2.0, height - trimHeight);
		add(bottomTrim);
		
		goalTrip = new Line(0,trimHeight, 0,height-trimHeight);
		
		
	}
	
	public void addTo(ShapeGroup scene, PhysicsOperator physics){
		scene.add(mainBox);
		scene.add(topTrim);
		scene.add(bottomTrim);
		
		physics.addTan(topTrim);
		physics.addTan(bottomTrim);
	}
	
	@Override
	public void move(double x, double y){
		mainBox.move(x, y);
		topTrim.move(x, y);
		bottomTrim.move(x, y);
		goalTrip.move(x, y);
	}
	
	public Line getTripLine(){
		return goalTrip;
	}
	
	

}

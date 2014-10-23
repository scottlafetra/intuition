package com.lafetra.scott.intuition.physics;

import java.util.ArrayList;

import com.lafetra.scott.intuition.Game;
import com.lafetra.scott.intuition.geom.Point;


//Could optimize axis independent collision
public class PhysicsOperator {
	
	public static final double METERS_PER_PIXEL = 1.0/0.0032;
	public static final double FRICTION_COIFICIANT = 0.5;
	
	private ArrayList<Physical> nonTangible;
	private ArrayList<Tangible> tangible;
	private long lastTime;
	
	private boolean gravity;
	
	public PhysicsOperator(){
		nonTangible = new ArrayList<Physical>();
		tangible =    new ArrayList<Tangible>();
		lastTime = System.currentTimeMillis();
		gravity = true;
	}
	
	/**
	 * Adds a non tangible object to operate.
	 * @param toAdd The object to add.
	 */
	public void add(Physical toAdd){
		nonTangible.add(toAdd);
	}
	
	
	/**
	 * Adds a tangible object to operate.
	 * @param toAdd The object to add.
	 */
	public void addTan(Tangible toAdd){
		tangible.add(toAdd);
	}
	
	/**
	 * Removes an object from operation.
	 * @param toRemove Object to remove.
	 */
	public void remove(Physical toRemove){
		if(!nonTangible.remove(toRemove))
			tangible.remove(toRemove);
	}
	
	/**
	 * Processes physical items
	 */
	public void process(){
		double time = (System.currentTimeMillis() - lastTime)/1000.0;//Get time elapsed in seconds
		
		//Add gravity for members
		for(Physical item : nonTangible){
			if(gravity)
				applyGravity(item);
		}
		for(Tangible item : tangible){//And friction
			if(!item.isFixed()){
				if(gravity)
					applyGravity(item);
				
				applyFriction(item);
			}
		}
		
		//Calculate acceleration for members
		for(Physical item : nonTangible){
			item.calcAccel();
		}
		for(Physical item : tangible){
			item.calcAccel();
		}
		
		//Calculate velocity based on acceleration and time
		for(Physical item : nonTangible){
			calcVelAndPos(item);
		}
		for(Tangible item : tangible){
			boolean contact = false; // keep track of contact
			
			calcVelAndPos(item);
			
			Point vel = item.getVelocity();
			item.smearMove(vel.getX()*time*METERS_PER_PIXEL, 0);//Move x
			
			if(vel.getX() != 0){
				//Check Collision x
				for(Tangible item2 : tangible){
					
					if(item == item2) continue;
					
					if(item.inBounds(item2.getTotalBounds())){
						if(!item2.isFixed())
							transferForce(item, item2, true);
						item.correctXTo(item2.getTotalBounds());
						contact = true;
					}
				}
			}
			
			item.releaseSmear();
			
			vel = item.getVelocity();
			item.smearMove(0, vel.getY()*time*METERS_PER_PIXEL);//Move y
			
			//Check Collision y
			if(vel.getY() != 0){
				for(Tangible item2 : tangible){
					
					if(item == item2) continue;
					
					if(item.inBounds(item2.getTotalBounds())){
						if(!item2.isFixed())
							transferForce(item, item2, false);
						item.correctYTo(item2.getTotalBounds());
						contact = true;
					}
				}
			}
			
			item.setContact(contact);//Finish up contact
			
			item.releaseSmear();
		}
		lastTime = System.currentTimeMillis();
	}
	
	/**
	 * Calculates position and velocity.
	 * @param item
	 */
	private void calcVelAndPos(Physical item){
		double time = (System.currentTimeMillis() - lastTime)/1000.0;//Get time elapsed in seconds
		
		Point vel = item.getVelocity();
		Point accel = item.getAcceleration();
		item.setVelocity(vel.getX()+ accel.getX() * time, vel.getY()+ accel.getY() * time);
		
	}
	
	private void applyGravity(Physical item){
		item.applyForce(0, 9.8*item.getMass());
	}
	
	private void applyFriction(Tangible item){
		if(!item.getContact()) return;//Return imediently if item is not in contact
		
		double frictionForce = 9.8*item.getMass()*FRICTION_COIFICIANT;
		double currentForce = item.getCurrentForceTotals().getX();
		
		if(Math.abs(item.getVelocity().getX()) < 0.03){
			frictionForce = Math.min(frictionForce, Math.abs(currentForce));
			if(frictionForce == 0)//Snap small velocities to 0
				item.setVelocity(0, item.getVelocity().getY());
		}
		
		if(item.getVelocity().getX() > 0)//set direction
			frictionForce *= (-1.0);
		
		//System.out.println(frictionForce + " : " + currentForce + " V: " + item.getVelocity().getX()); //NOTE: Diagnostic info
		item.applyForce(frictionForce, 0);
		
	}
	
	public void setGravity(boolean toSet){
		gravity = toSet;
	}
	
	private void transferForce(Tangible from, Tangible to, boolean x){//For transfering all force via static collision
		
		if(x){
			to.setVelocity((from.getMass() * from.getVelocity().getX())/to.getMass() + to.getVelocity().getX(), 0);
		} else {
			to.setVelocity(0, (from.getMass() * from.getVelocity().getY())/to.getMass() + to.getVelocity().getY());
		}
		
		
	}

}

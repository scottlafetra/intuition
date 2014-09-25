package com.lafetra.scott.intuition;

import java.util.ArrayList;

import javax.naming.NameNotFoundException;

import org.lwjgl.*;
import org.lwjgl.opengl.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.lafetra.scott.intuition.body.*;
import com.lafetra.scott.intuition.geom.Circle;
import com.lafetra.scott.intuition.geom.Line;
import com.lafetra.scott.intuition.geom.Point;
import com.lafetra.scott.intuition.geom.Rectangle;
import com.lafetra.scott.intuition.geom.ShapeGroup;
import com.lafetra.scott.intuition.geom.Triangle;
import com.lafetra.scott.intuition.level.Backround;
import com.lafetra.scott.intuition.physics.PhysicsGroup;
import com.lafetra.scott.intuition.physics.PhysicsOperator;
import com.lafetra.scott.intuition.physics.TangibleGroup;

import static org.lwjgl.opengl.GL11.*;


public class Game{
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	public static Backround back;
	
	private ShapeGroup scene;
	private ShapeGroup group;
	public static Body body;
	private Rectangle bodyBox;
	
	private PhysicsOperator physics;
	private TangibleGroup platform;
	
	public static double scaleVar;
	
	
	public Game(){
		try{
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setTitle("Intuition");
			Display.create();
		}
		catch(LWJGLException e){
			e.printStackTrace();
		}
		
		physics = new PhysicsOperator();
		platform = new TangibleGroup(50);
		platform.setFixed(true);
		platform.updateBounds();
		platform.add(new Rectangle(0, 0, WIDTH/2.0, 30));
		platform.move(0, HEIGHT - 30);
		
		back = new Backround(0f/255f, 51f/255f, 204f/255f);
		group = new ShapeGroup();
		scene = new ShapeGroup();
		
		/*
		TangibleGroup test = new TangibleGroup(50);
		test.add(new Rectangle(0, 0, 30, 30));
		Rectangle rect = new Rectangle(0, 0, 30, 30);
		rect.move(50, 50);
		test.add(rect);*/
		
		scaleVar = 1.0;
		
		body = new Body(25);
		/*try {
			body.startAnimation(Animation.LEG_WALK_RIGHT);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}*/
		
		updateBodyBox();
		
		TangibleGroup box = new TangibleGroup(50);
		box.add(new Rectangle(0, 0, 30, 30));
		
		box.setFixed(true);
		//physics.addTan(box);
		physics.addTan(body);
		physics.addTan(platform);
		//physics.addTan(test);
		
		//scene.add(test);
		scene.add(box);
		scene.add(body);
		scene.add(platform);
		
		//Init OpenGL
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);//Depth is 1, -1 because 2D
		glMatrixMode(GL_MODELVIEW);
	}
	
	/**
	 * Runs the game.
	 */
	public void run(){
		while(!Display.isCloseRequested()){
			events();
			physics.process();
			updateBodyBox();
			render();
		}
		Display.destroy();
	}
	
	/**
	 * Handles events for the game.
	 */
	public void events(){
		
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			Display.destroy();
			System.exit(0);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){ scene.move(-5,  0); } 
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){  scene.move( 5,  0); }
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)){    scene.move( 0,  5); } 
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){  scene.move( 0, -5); } 
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){ body.applyForce(  0,-1000); } 
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){ body.applyForce(  0, 1000); } 
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){ body.applyForce(-1000,  0); } 
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){ body.applyForce( 1000,  0); }
	}
	
	/**
	 * Renders the scene to the screen.
	 */
	public void render(){
		
		//body.animate();
		
		glClear(GL_COLOR_BUFFER_BIT);
		
		back.draw();
		bodyBox.draw();
		scene.draw();

		
		Display.update();
		Display.sync(60);//max FPS
	}
	
	public static void main(String[] args){
		Game game = new Game();
		game.run();
	}
	
	private void updateBodyBox(){// for tests only
		BoundingBox box = body.getBoundBox();
		bodyBox = new Rectangle(box.getLeftLimit(), box.getUpLimit(),
				box.getRightLimit() - box.getLeftLimit(), box.getDownLimit() - box.getUpLimit());
		bodyBox.setColor(1, 0, 0);
	}
	
}
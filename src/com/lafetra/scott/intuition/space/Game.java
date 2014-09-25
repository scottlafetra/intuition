package com.lafetra.scott.intuition.space;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.lafetra.scott.intuition.geom.Rectangle;
import com.lafetra.scott.intuition.geom.ShapeGroup;
import com.lafetra.scott.intuition.level.Backround;
import com.lafetra.scott.intuition.physics.PhysicsOperator;
import com.lafetra.scott.intuition.physics.TangibleGroup;

public class Game {
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	private final double THRUST_FORCE = 50;
	
	public static Backround back;
	
	private PhysicsOperator physics;
	private ShapeGroup scene;
	
	private Ship ship;

		
	public Game(){
		try{
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setTitle("Space");
			Display.create();
		}
		catch(LWJGLException e){
			e.printStackTrace();
		}
		
		//Init OpenGL
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);//Depth is 1, -1 because 2D
		glMatrixMode(GL_MODELVIEW);
		
		back = new Backround(0, 0, 1);
		physics = new PhysicsOperator();
			physics.setGravity(false);
		scene = new ShapeGroup();
		
		ship = new Ship(50, Ship.ViewMode.TOPDOWN);
		ship.move(WIDTH/2.0, HEIGHT/2.0);
		physics.addTan(ship);
		scene.add(ship);
		
		
	}
	
	public void run(){
		while(!Display.isCloseRequested()){
			events();
			physics.process();
			ship.turnToFace(Mouse.getX(), Mouse.getY());
			render();
		}
		Display.destroy();
	}

	private void render(){
		glClear(GL_COLOR_BUFFER_BIT);
		
		back.draw();
		scene.draw();

		Display.update();
		Display.sync(60);//max FPS
	}
	
	private void events(){
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			Display.destroy();
			System.exit(0);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){ ship.applyForce( THRUST_FORCE,  0); } 
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){ ship.applyForce(-THRUST_FORCE,  0); }
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){ ship.applyForce( 0, -THRUST_FORCE); } 
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){ ship.applyForce( 0,  THRUST_FORCE); } 
	}
	
	public static void main(String[] args){
		Game game = new Game();
		game.run();
	}

}

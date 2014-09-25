package com.lafetra.scott.intuition.physics;


import static org.lwjgl.opengl.GL11.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.lafetra.scott.intuition.aztecBall.Ball;
import com.lafetra.scott.intuition.geom.Geom;
import com.lafetra.scott.intuition.geom.Rectangle;
import com.lafetra.scott.intuition.geom.ShapeGroup;
import com.lafetra.scott.intuition.level.Backround;
import com.lafetra.scott.intuition.physics.PhysicsOperator;
import com.lafetra.scott.intuition.physics.TangibleGroup;
import com.lafetra.scott.intuition.physics.toys.ImpactBall;
import com.lafetra.scott.intuition.text.CharacterLoader;
import com.lafetra.scott.intuition.text.GraphicCharacter;

public class PhysicsBox {
	
	enum State{
		PLAYING, RED_WIN, BLUE_WIN, GREEN_WIN
	}
	
	enum Color{
		RED, GREEN, BLUE
	}
	
	public static boolean twoPlayers;
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	private static final double MOVE_FORCE = 150;
	
	private final int WALL_WIDTH = 30;
	
	public static Backround back;
	
	private PhysicsOperator physics;
	private ShapeGroup scene;
	
	private ArrayList<Ball> balls;
	
	private Random rand;
	private boolean primed;

	
	public PhysicsBox(){
		try{
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setTitle("Colors");
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
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		back = new Backround(0.5f, 0, 0.5f);
		physics = new PhysicsOperator();
		scene = new ShapeGroup();
		
		//Create walls and ceiling
		
		TangibleGroup lWall = new TangibleGroup(1);
		lWall.add(new Rectangle(0, 0, WALL_WIDTH, HEIGHT - WALL_WIDTH));
		
		TangibleGroup rWall = new TangibleGroup(1);
		rWall.add(new Rectangle(0, 0, WALL_WIDTH, HEIGHT - WALL_WIDTH));
		rWall.move(WIDTH - WALL_WIDTH, WALL_WIDTH);
		
		TangibleGroup ceiling =  new TangibleGroup(1);
		ceiling.add(new Rectangle(0, 0, WIDTH - WALL_WIDTH, WALL_WIDTH));
		ceiling.move(WALL_WIDTH, 0);
		
		TangibleGroup floor =  new TangibleGroup(1);
		floor.add(new Rectangle(0, 0, WIDTH, WALL_WIDTH));
		floor.move(0, HEIGHT - WALL_WIDTH);
		
		lWall.setFixed(true);
		rWall.setFixed(true);
		ceiling.setFixed(true);
		floor.setFixed(true);
		
		scene.add(floor);
		scene.add(ceiling);
		scene.add(rWall);
		scene.add(lWall);
		
		physics.addTan(floor);
		physics.addTan(ceiling);
		physics.addTan(rWall);
		physics.addTan(lWall);
		
		balls = new ArrayList<Ball>();
		rand = new Random();
		primed = false;
		
	}
	
	public void run(){
		while(!Display.isCloseRequested()){
			events();
			physics.process();
			
			for(Ball ball : balls){
				
				switch(rand.nextInt(4)){
				case 0:
					ball.applyForce(MOVE_FORCE, 0);
					break;
					
				case 1:
					ball.applyForce(-MOVE_FORCE, 0);
					break;
					
				case 2:
					ball.applyForce(0, MOVE_FORCE);
					break;
				
				case 3:
					ball.applyForce(0, -MOVE_FORCE);
					break;
				}
			}
			
			render();
		}
		Display.destroy();
	}
	
	
	
	private void render(){
		glClear(GL_COLOR_BUFFER_BIT);
		
		back.draw();
		scene.draw();

		Display.update();
		Display.sync(120);//max FPS
	}
	
	private void events(){
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			Display.destroy();
			System.exit(0);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){ 
			primed = true;
		} else {
			if(primed){
				
				Ball newBall = new Ball(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
				newBall.move(WIDTH/2.0, HEIGHT*(1/4.0));
				balls.add(newBall);
				scene.add(newBall);
				physics.addTan(newBall);
				
				primed = false;
			}
		}
		
		/*
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){ redBall.applyForce( MOVE_FORCE,  0); } 
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){ redBall.applyForce(-MOVE_FORCE,  0); }
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){ redBall.applyForce( 0, -MOVE_FORCE); } 
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){ redBall.applyForce( 0,  MOVE_FORCE); } 
		
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){ blueBall.applyForce( MOVE_FORCE,  0); } 
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){  blueBall.applyForce(-MOVE_FORCE,  0); }
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)){    blueBall.applyForce( 0, -MOVE_FORCE); } 
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){  blueBall.applyForce( 0,  MOVE_FORCE); }
		*/
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PhysicsBox game = new PhysicsBox();
		game.run();
	}

}

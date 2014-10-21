package com.lafetra.scott.intuition.aztecBall;
//Current Issue to fix:
//When loading a transparent texture, everything is drawn transparently.

import static org.lwjgl.opengl.GL11.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.lafetra.scott.intuition.geom.Geom;
import com.lafetra.scott.intuition.geom.Rectangle;
import com.lafetra.scott.intuition.geom.ShapeGroup;
import com.lafetra.scott.intuition.level.Backround;
import com.lafetra.scott.intuition.physics.PhysicsOperator;
import com.lafetra.scott.intuition.physics.TangibleGroup;
import com.lafetra.scott.intuition.text.CharacterLoader;
import com.lafetra.scott.intuition.text.GraphicCharacter;
import com.lafetra.scott.intuition.text.GraphicString;

public class Game {
	
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
	
	private static final float PERCENT_PER_SCORE = 0.1f;
	private static final long WIN_ANIMATE_TIME= 1 * 1000;
	
	private final int WALL_WIDTH = 30;
	
	public static Backround back;
	
	private State state;
	private long winTime;
	
	private boolean threePlayer;
	
	private float redPoints;
	private float bluePoints;
	private float greenPoints;
	
	private PhysicsOperator physics;
	private ShapeGroup scene;
	
	private static Goal goal;
	private Ball redBall;
	private Ball blueBall;
	private Ball greenBall;
	
	private GraphicString testString;//TODO: remove

	
	public Game(){
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
		
		glClearColor(1, 0, 0, 1);
		
		state = State.PLAYING;
		threePlayer = false;
		redPoints = 0.5f;
		bluePoints = 0.5f;
		greenPoints = 0f;
		back = new Backround(redPoints, 0, bluePoints);
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
		
		//Add red ball
		redBall = new Ball(1, 0, 0);
		redBall.move(WIDTH*(1.0/4.0) - WALL_WIDTH/2.0, HEIGHT*(1.0/4.0));
		scene.add(redBall);
		physics.addTan(redBall);
		
		//Add blue ball
		blueBall = new Ball(0, 0, 1);
		blueBall.move(WIDTH*(3.0/4.0) - WALL_WIDTH/2.0, HEIGHT*(1.0/4.0));
		scene.add(blueBall);
		physics.addTan(blueBall);
		
		//Add goal
		goal = new Goal(HEIGHT*(3/16.0));
		goal.move(WIDTH/2.0, HEIGHT*(1.0/4.0) - WALL_WIDTH/2.0 - 60);
		goal.addTo(scene, physics);
		
		//TODO:Remove, for testing only
		try{
			CharacterLoader loader = new CharacterLoader("text.png");//TODO: Make dynamic, remove small text case if it is not necessary
			testString = new GraphicString("abcdef", 150, "text.png");
			testString.move(WIDTH/8.0, HEIGHT/2.0);
			scene.add(testString);
			
		} catch (FileNotFoundException e){
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}	
	}
	
	public void run(){
		while(!Display.isCloseRequested()){
			events();
			physics.process();
			
			if(state == State.PLAYING)
				checkScoring();
			else
				winAnimation();
			
			render();
		}
		Display.destroy();
	}
	
	private void winAnimation() {
		
		
		long time = System.currentTimeMillis() - winTime;
		
		if((threePlayer && redPoints == (1f/3f) && greenPoints == (1f/3f) && bluePoints == (1f/3f)) || (time > WIN_ANIMATE_TIME && !threePlayer)){// if( three player win || two player win)
			state = State.PLAYING;
			return;
		}
		
		
		if(threePlayer){
			if(state == State.BLUE_WIN){
				bluePoints  = (float) (0.5*Math.sin(((float)time/WIN_ANIMATE_TIME)*0.75*Geom.TAU  - Geom.TAU*(3.0/4.0)) + 0.5);
				redPoints = 1 - bluePoints;
				greenPoints = 1 - bluePoints;
					
			} else if (state == State.GREEN_WIN){
				greenPoints  = (float) (0.5*Math.sin(((float)time/WIN_ANIMATE_TIME)*0.75*Geom.TAU  - Geom.TAU*(3.0/4.0)) + 0.5);
				redPoints = 1 - greenPoints;
				bluePoints = 1 - greenPoints;
				
			} else {
				redPoints  = (float) (0.5*Math.sin(((float)time/WIN_ANIMATE_TIME)*0.75*Geom.TAU  - Geom.TAU*(3.0/4.0)) + 0.5);
				bluePoints = 1 - redPoints;
				greenPoints = 1 - redPoints;
			}
			
			redPoints   = halfCenterToThirdCenter(redPoints);
			greenPoints = halfCenterToThirdCenter(greenPoints);
			bluePoints  = halfCenterToThirdCenter(bluePoints);
			
		} else {
			if(state == State.BLUE_WIN){
				bluePoints  = (float) (0.5*Math.sin(((float)time/WIN_ANIMATE_TIME)*0.75*Geom.TAU  - Geom.TAU*(3.0/4.0)) + 0.5);
				redPoints = 1 - bluePoints;
			} else {
				redPoints  =  (float) (0.5*Math.sin(((float)time/WIN_ANIMATE_TIME)*0.75*Geom.TAU  - Geom.TAU*(3.0/4.0)) + 0.5);
				bluePoints = 1 - redPoints;
			}
		}
		
		updateBackColor();
	}
	
	private float halfCenterToThirdCenter(float x){
		if(x < (1f/3f)){
			
			return x*(2f/3f);
			
		} else {
			
			return (x - 0.5f)*(4f/3f) + (1f/3f);
			
		}
	}

	private void render(){
		glClear(GL_COLOR_BUFFER_BIT);  
		
		back.draw();
		scene.draw();
		//testChar.draw();

		Display.update();
		Display.sync(120);//max FPS
	}
	
	private void events(){
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			Display.destroy();
			System.exit(0);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){ redBall.applyForce( MOVE_FORCE,  0); } 
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){ redBall.applyForce(-MOVE_FORCE,  0); }
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){ redBall.applyForce( 0, -MOVE_FORCE); } 
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){ redBall.applyForce( 0,  MOVE_FORCE); } 
		
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){ blueBall.applyForce( MOVE_FORCE,  0); } 
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){  blueBall.applyForce(-MOVE_FORCE,  0); }
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)){    blueBall.applyForce( 0, -MOVE_FORCE); } 
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){  blueBall.applyForce( 0,  MOVE_FORCE); }
		
		if(threePlayer){
			if(Keyboard.isKeyDown(Keyboard.KEY_K)){ greenBall.applyForce( MOVE_FORCE,  0); } 
			if(Keyboard.isKeyDown(Keyboard.KEY_H)){ greenBall.applyForce(-MOVE_FORCE,  0); }
			if(Keyboard.isKeyDown(Keyboard.KEY_U)){ greenBall.applyForce( 0, -MOVE_FORCE); } 
			if(Keyboard.isKeyDown(Keyboard.KEY_J)){ greenBall.applyForce( 0,  MOVE_FORCE); }
		} else {
			if(Keyboard.isKeyDown(Keyboard.KEY_K)||
			   Keyboard.isKeyDown(Keyboard.KEY_H)||
			   Keyboard.isKeyDown(Keyboard.KEY_U)||
			   Keyboard.isKeyDown(Keyboard.KEY_J))
				addThridPlayer();
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_G)){  addThridPlayer(); }
	}
	
	private void checkScoring(){
		if(redBall.isScoring(redBall.isIntersecting(goal.getTripLine())))
			score(Color.RED);
		
		if(blueBall.isScoring(blueBall.isIntersecting(goal.getTripLine())))
			score(Color.BLUE);
		
		if(threePlayer){
			if(greenBall.isScoring(greenBall.isIntersecting(goal.getTripLine())))
				score(Color.GREEN);
		}
	}
	
	private void score(Color scoreColor){
		
		
		if(redPoints == 1 || bluePoints == 1 || greenPoints == 1 ){
			//Win or loose calculations
			if(scoreColor == Color.RED && redPoints == 1 ){
				state = State.RED_WIN;
			} else if (scoreColor == Color.BLUE && bluePoints == 1){
				state = State.BLUE_WIN;
			} else if (scoreColor == Color.GREEN && greenPoints == 1){
				state = State.GREEN_WIN;
			}
			
			if(state != State.PLAYING){//If there was a win
				winTime = System.currentTimeMillis();
			}
				
		}
		
		changePoints(scoreColor, PERCENT_PER_SCORE);
		
		if(redPoints > 1) redPoints = 1;
		if(bluePoints > 1) bluePoints = 1;
		if(greenPoints > 1) greenPoints = 1;
		
		updateBackColor();
	}
	
	private void updateBackColor(){
		back.setColor(redPoints, greenPoints, bluePoints);
	}
	
	private void addThridPlayer(){//Only does something if not third player already
		
		if(!threePlayer){
			threePlayer = true;
			
			setPoints(Color.RED, (1f/3f));
			setPoints(Color.GREEN, (1f/3f));
			setPoints(Color.BLUE, (1f/3f));
			updateBackColor();
			
			//Add green ball
			greenBall = new Ball(0, 1, 0);
			greenBall.move(WIDTH*(1.0/2.0) - WALL_WIDTH/2.0, HEIGHT*(1.0/2.0));
			scene.add(greenBall);
			physics.addTan(greenBall);
			
			scene.setToFront(goal);
		}
	}
	
	private void setPoints(Color color, float amount){
		//Set bounds
		if(amount < 0) amount = 0;
		if(amount > 1) amount = 1;
		
		switch(color){
		case BLUE:
			bluePoints = amount;
			break;
		case GREEN:
			greenPoints = amount;
			break;
		case RED:
			redPoints = amount;
			break;
		
		}
	}
	
	private void changePoints(Color color, float change){
		switch(color){
		case BLUE:
			setPoints(Color.BLUE, bluePoints + change);
			break;
		case GREEN:
			setPoints(Color.GREEN, greenPoints + change);
			break;
		case RED:
			setPoints(Color.RED, redPoints + change);
			break;
		}
		
		//Offset the others
		if(threePlayer){
			if(color != color.RED  ) setPoints(Color.RED,     redPoints - change/2f);
			if(color != color.GREEN) setPoints(Color.GREEN, greenPoints - change/2f);
			if(color != color.BLUE)  setPoints(Color.BLUE,   bluePoints - change/2f);
		} else {
			if(color != color.RED  ) setPoints(Color.RED,     redPoints - change);
			if(color != color.BLUE)  setPoints(Color.BLUE,   bluePoints - change);
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Game game = new Game();
		game.run();
	}

}

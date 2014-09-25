package com.lafetra.scott.intuition;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import com.lafetra.scott.intuition.level.Backround;
import com.lafetra.scott.intuition.text.CharacterLoader;
import com.lafetra.scott.intuition.text.GraphicCharacter;
 
public class NinjaTest {
 
	public Backround back;
	
	private boolean bind;
	
	private Texture texture;
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	private GraphicCharacter letter;
 
	
	public NinjaTest(){
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH,HEIGHT));
			Display.setTitle("Ninja Test");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
 
		glEnable(GL_TEXTURE_2D);               
        
    	glEnable(GL_BLEND);
    	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        	
		glMatrixMode(GL_MODELVIEW);
 
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
		
		try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("test.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		back = new Backround(0, 0, 1);
		
		try {
			CharacterLoader loader = new CharacterLoader("text.png");
			letter = loader.getChar('b');
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		bind = true;
	}
 
	
	public void run() {
		while (!Display.isCloseRequested()) {
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
				Display.destroy();
				System.exit(0);
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_B)){
				bind = !bind;
			}
			
			glClear(GL_DEPTH_BUFFER_BIT);
			glClear(GL_COLOR_BUFFER_BIT);
			
			back.draw();
			
			if(bind)
				texture.bind();
			else
				System.out.println("No bind");
			
			
			 
			glBegin(GL_QUADS);
				
				glColor3f(1, 1, 1);
				
				glTexCoord2f(0,0);
				glVertex2f(100,100);
				
				glTexCoord2f(1,0);
				glVertex2f(200,100);
				
				glTexCoord2f(1,1);
				glVertex2f(200,200);
				
				glTexCoord2f(0,1);
				glVertex2f(100,200);
			glEnd();
 
			//letter.draw();
			
			Display.update();
			Display.sync(120);
 
		}
	}
 
 
	/**
	 * Main Class
	 */
	public static void main(String[] argv) {
		NinjaTest textureExample = new NinjaTest();
		textureExample.run();
	}
}
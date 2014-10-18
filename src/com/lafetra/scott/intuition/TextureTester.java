package com.lafetra.scott.intuition;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import com.lafetra.scott.intuition.geom.Rectangle;
import com.lafetra.scott.intuition.geom.ShapeGroup;

public class TextureTester {
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	private Texture texture;
	private Rectangle rect;
	private Rectangle rect2;
	private ShapeGroup scene;

	public TextureTester() {
		try{
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setTitle("Texture Test");
			Display.create();
		} catch(LWJGLException e){
			e.printStackTrace();
		}
		
		//Init OpenGL
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);//Depth is 1, -1 because 2D
		glMatrixMode(GL_MODELVIEW);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glEnable(GL_TEXTURE_2D);
		
		glClearColor(1, 0, 0, 1);
		
		try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("text.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		scene = new ShapeGroup();
		
		rect = new Rectangle(0, 0, 200, 100);
		rect.move(WIDTH/2.0, HEIGHT/2.0);
		scene.add(rect);
		
		rect2 = new Rectangle(0, 0, 200, 100);
		scene.add(rect2);
		
	}
	
	public void run(){
		while(!Display.isCloseRequested()){
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
				Display.destroy();
				System.exit(0);
			}
			
			glClear(GL_COLOR_BUFFER_BIT);
			
			
			
			//drawTexture();
			
			scene.draw();
			
			
			Display.update();
			Display.sync(120);//max FPS
		}
	}
	
	private void drawTexture(){
		
		texture.bind();
		
		glColor4f(1, 1, 1, 1);//reset color
		
		glBegin(GL_QUADS);
			glTexCoord2f(0, 0);
			glVertex2d(100, 100);
			
			glTexCoord2f(1, 0);
			glVertex2d(200, 100);
			
			glTexCoord2f(1, 1);
			glVertex2d(200, 200);
		
			glTexCoord2f(0, 1);
			glVertex2d(100, 200);
		glEnd();
	}

	public static void main(String[] args) {
		new TextureTester().run();
		
	}

}

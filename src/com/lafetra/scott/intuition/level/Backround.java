package com.lafetra.scott.intuition.level;
import static org.lwjgl.opengl.GL11.*;

import com.lafetra.scott.intuition.Game;

public class Backround {
	
	private float r, g, b;
	
	public Backround(float r, float g, float b){
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public void draw(){
		glBegin(GL_QUADS);
		glColor3f(r, g, b);
		
			glVertex2f(0,          0);      // Top left corner
	        glVertex2f(0,          Game.HEIGHT);    // Top right corner
	        glVertex2f(Game.WIDTH, Game.HEIGHT);  // Bottom right corner
	        glVertex2f(Game.WIDTH, 0);    // Bottom left corner
		glEnd();
	}
	
	public void setColor(float r, float g, float b){
		this.r = r;
		this.g = g;
		this.b = b;
	}

}

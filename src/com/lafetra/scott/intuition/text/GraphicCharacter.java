package com.lafetra.scott.intuition.text;



import static org.lwjgl.opengl.GL11.*;
import static com.lafetra.scott.intuition.text.CharacterLoader.*;

import java.util.ArrayList;

import com.lafetra.scott.intuition.aztecBall.Game;
import com.lafetra.scott.intuition.geom.Rectangle;
import com.lafetra.scott.intuition.geom.TransformMatrix;

public class GraphicCharacter extends Rectangle {
	
	private int leftBound, rightBound, row;
	
	GraphicCharacter(int leftBound, int rightBound, int row, int height) {
		super(0, 0, (rightBound - leftBound)*(height/(float)CharacterLoader.HEIGHT), height);
		
		this.leftBound  = leftBound;
		this.rightBound = rightBound;
		this.row = row;
	}
	
	@Override
	public void draw(ArrayList<TransformMatrix> trans) {
		
		ArrayList<TransformMatrix> toApply = (ArrayList<TransformMatrix>) trans.clone();
		toApply.add(getToHigher());
	
		
		float leftBoundf  = (float)leftBound/(float)(texture.getTextureWidth());
		float rightBoundf = (float)rightBound/(float)(texture.getTextureWidth());
		glEnable(GL_TEXTURE_2D);
		texture.bind();
		
		glColor4f(1, 1, 1, 1);//reset color
		
		glBegin(GL_QUADS);
		
			glTexCoord2f(leftBoundf, (CharacterLoader.HEIGHT*row)/(float)texture.getTextureHeight());
			points[0].draw(toApply);
			
			glTexCoord2f(rightBoundf, (CharacterLoader.HEIGHT*row)/(float)texture.getTextureHeight());
			points[1].draw(toApply);
			
			glTexCoord2f(rightBoundf, (CharacterLoader.HEIGHT*(row + 1))/(float)texture.getTextureHeight());
			points[2].draw(toApply);
			
			glTexCoord2f(leftBoundf, (CharacterLoader.HEIGHT*(row + 1))/(float)texture.getTextureHeight());
			points[3].draw(toApply);
			
		glEnd();
		
		glDisable(GL_TEXTURE_2D);
	}
	

}

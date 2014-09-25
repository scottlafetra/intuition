package com.lafetra.scott.intuition.text;



import static org.lwjgl.opengl.GL11.*;
import static com.lafetra.scott.intuition.text.CharacterLoader.*;

import java.util.ArrayList;

import com.lafetra.scott.intuition.geom.Rectangle;
import com.lafetra.scott.intuition.geom.TransformMatrix;

public class GraphicCharacter extends Rectangle {
	
	private int leftBound, rightBound;
	
	GraphicCharacter(int leftBound, int rightBound) {
		super(0, 0, rightBound - leftBound, CharacterLoader.CHAR_HEIGHT);
		
		this.leftBound  = leftBound;
		this.rightBound = rightBound;
	}
	
	@Override
	public void draw(ArrayList<TransformMatrix> trans) {
		
		ArrayList<TransformMatrix> toApply = (ArrayList<TransformMatrix>) trans.clone();
		toApply.add(getToHigher());
	
		
		float leftBoundf  = (float)leftBound/(float)(texture.getImageWidth());
		float rightBoundf = (float)rightBound/(float)(texture.getImageWidth());
		
		texture.bind();
		
		glBegin(GL_QUADS);
		
			glColor4f(1, 1, 1, 1);//reset color
		
			glTexCoord2f(leftBoundf, 0);
			points[0].draw(toApply);
			
			glTexCoord2f(rightBoundf, 0);
			points[1].draw(toApply);
			
			glTexCoord2f(rightBoundf, 1);
			points[2].draw(toApply);
			
			glTexCoord2f(leftBoundf, 1);
			points[3].draw(toApply);
			
		glEnd();
	}
	

}

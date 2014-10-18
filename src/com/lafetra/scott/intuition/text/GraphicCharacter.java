package com.lafetra.scott.intuition.text;



import static org.lwjgl.opengl.GL11.*;
import static com.lafetra.scott.intuition.text.CharacterLoader.*;

import java.util.ArrayList;

import com.lafetra.scott.intuition.geom.Rectangle;
import com.lafetra.scott.intuition.geom.TransformMatrix;

public class GraphicCharacter extends Rectangle {
	
	private int leftBound, rightBound, row, rowHeight;
	
	GraphicCharacter(int leftBound, int rightBound, int row, int height) {
		super(0, 0, rightBound - leftBound, height);
		
		this.leftBound  = leftBound;
		this.rightBound = rightBound;
		this.row = row;
		this.rowHeight = height/CharacterLoader.CHAR_ROWS;
	}
	
	@Override
	public void draw(ArrayList<TransformMatrix> trans) {
		
		ArrayList<TransformMatrix> toApply = (ArrayList<TransformMatrix>) trans.clone();
		toApply.add(getToHigher());
	
		
		float leftBoundf  = (float)leftBound/(float)(texture.getImageWidth());
		float rightBoundf = (float)rightBound/(float)(texture.getImageWidth());
		glEnable(GL_TEXTURE_2D);
		texture.bind();
		
		glColor4f(1, 1, 1, 1);//reset color
		
		glBegin(GL_QUADS);
		
			glTexCoord2f(leftBoundf, (rowHeight*row)/(float)height);
			points[0].draw(toApply);
			
			glTexCoord2f(rightBoundf, (rowHeight*row)/(float)height);
			points[1].draw(toApply);
			
			glTexCoord2f(rightBoundf, (rowHeight*(row + 1))/(float)height);
			points[2].draw(toApply);
			
			glTexCoord2f(leftBoundf, (rowHeight*(row + 1))/(float)height);
			points[3].draw(toApply);
			
		glEnd();
		
		glDisable(GL_TEXTURE_2D);
	}
	

}

package com.lafetra.scott.intuition.text;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.lafetra.scott.intuition.geom.Point;
import com.lafetra.scott.intuition.geom.ShapeGroup;

public class GraphicString extends ShapeGroup {
	
	private static final int SPACE_WIDTH = 200;
	
	public Point addPoint;//where to add new characters

	public GraphicString() {
		
		addPoint = new Point(0, 0);
	}
	
	public GraphicString(String init, int size, String texLocation) throws FileNotFoundException, IOException{
		
		this();
		
		CharacterLoader loader = new CharacterLoader(texLocation);
		
		for(char c; init.length() > 0; init = init.substring(1)){
			
			c = init.charAt(0);
			if(c == ' ')
				addPoint.move(SPACE_WIDTH, 0);
			else if(c == '\n')
				addNewline();
			else
				add(loader.getChar(c, size));
		}
	}
	
	public void add(GraphicCharacter character){
		
		super.add(character);
		character.move(addPoint);
		addPoint.move(character.getWidth(), 0);
	}
	
	public void addNewline(){
		
		addPoint.move(0 - addPoint.getX(), CharacterLoader.HEIGHT);
	}
	

}

package com.lafetra.scott.intuition.text;

import com.lafetra.scott.intuition.geom.Point;
import com.lafetra.scott.intuition.geom.ShapeGroup;

public class GraphicString extends ShapeGroup {
	
	private static final int SPACE_WIDTH = 75;
	
	public Point addPoint;//where to add new characters
	
	private int size;

	public GraphicString(int size) {
		
		addPoint = new Point(0, 0);
		this.size = size;
	}
	
	public GraphicString(String init, int size, CharacterLoader loader){
		this(size);
		
		for(char c; init.length() > 0; init = init.substring(1)){
			
			c = init.charAt(0);
			if(c == ' ')
				addPoint.move(SPACE_WIDTH*(size/(float)CharacterLoader.HEIGHT), 0);
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
		addPoint.move(0 - addPoint.getX(), size);
	}
	

}

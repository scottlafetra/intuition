package com.lafetra.scott.intuition.text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class CharacterLoader {
	
	public static final int WIDTH = 200;
	public static final int HEIGHT = 200;
	public static final int MAX_WIDTH = 2000;
	
	public static final int CHARS_PER_ROW = 10;
	public static final int CHAR_ROWS = 3;
	
	public static final int KERNING = 15;//20 is max due to w
	
	private static HashMap<String, Integer>   charSpecificWidths = null;
	
	protected static Texture texture;

	public CharacterLoader(String imgLocation) throws FileNotFoundException, IOException {
		
		initCharCorrections();
		loadTexture(imgLocation);
	}
	
	private void loadTexture(String imgLocation) throws FileNotFoundException, IOException{
		
		texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(imgLocation));
	}
	
	private void initCharCorrections(){
		charSpecificWidths = new HashMap();
		
		charSpecificWidths.put("a", new Integer(126));
		charSpecificWidths.put("b", new Integer(108));
		charSpecificWidths.put("c", new Integer(100));
		charSpecificWidths.put("d", new Integer(108));
		charSpecificWidths.put("e", new Integer(108));
		charSpecificWidths.put("f", new Integer(108));
		charSpecificWidths.put("g", new Integer(108));
		charSpecificWidths.put("h", new Integer(108));
		charSpecificWidths.put("i", new Integer(26));
		charSpecificWidths.put("j", new Integer(63));
		charSpecificWidths.put("k", new Integer(111));
		charSpecificWidths.put("l", new Integer(108));
		charSpecificWidths.put("m", new Integer(126));
		charSpecificWidths.put("n", new Integer(108));
		charSpecificWidths.put("o", new Integer(117));
		charSpecificWidths.put("p", new Integer(108));
		charSpecificWidths.put("q", new Integer(126));
		charSpecificWidths.put("r", new Integer(112));
		charSpecificWidths.put("s", new Integer(108));
		charSpecificWidths.put("t", new Integer(108));
		charSpecificWidths.put("u", new Integer(108));
		charSpecificWidths.put("v", new Integer(126));
		charSpecificWidths.put("w", new Integer(180));
		charSpecificWidths.put("x", new Integer(117));
		charSpecificWidths.put("y", new Integer(117));
		charSpecificWidths.put("z", new Integer(108));
		
		charSpecificWidths.put(".", new Integer(36));
		charSpecificWidths.put(":", new Integer(19));
		charSpecificWidths.put("-", new Integer(53));
		charSpecificWidths.put(",", new Integer(37));
		
	}
	
	public int getCharCorection(char c){
		return WIDTH - (charSpecificWidths.get(String.valueOf(c)) + KERNING);
	}
	
	public GraphicCharacter getChar(char toGet, int size){//size in px height
		
		if((toGet >= 'a') && (toGet <= 'z')){
			
			return new GraphicCharacter(((toGet - 'a') * WIDTH)%MAX_WIDTH, ((toGet - 'a' + 1) * WIDTH - getCharCorection(toGet))%MAX_WIDTH, (toGet - 'a')/CHARS_PER_ROW, size);//Adds the 'a' back in for correction lookup
			
		} else if(toGet == '.'){	
			return new GraphicCharacter((26 * WIDTH)%MAX_WIDTH, (27 * WIDTH - getCharCorection('.'))%MAX_WIDTH, 2, size);
			
		} else if(toGet == ':'){
			return new GraphicCharacter((27 * WIDTH)%MAX_WIDTH, (28 * WIDTH - getCharCorection(':'))%MAX_WIDTH, 2, size);
			
		} else if(toGet == '-'){
			return new GraphicCharacter((28 * WIDTH)%MAX_WIDTH, (29 * WIDTH - getCharCorection('-'))%MAX_WIDTH, 2, size);
			
		} else if(toGet == ','){
			return new GraphicCharacter((29 * WIDTH)%MAX_WIDTH, (30 * WIDTH - getCharCorection(','))%MAX_WIDTH, 2, size);
			
		} else throw new IllegalArgumentException(toGet + " is not an accepted character!");
	}
	
	

}

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
	
	public static final int CHAR_HEIGHT = 200;
	public static final int CHAR_WIDTH = 200;
	public static final int MAX_TEXT_WIDTH = 2000;
	public static final int CHARACTERS_PER_ROW = 10;
	private static HashMap<String, Integer>   charCorrections = null;
	
	protected static Texture texture;

	public CharacterLoader(String imgLocation) throws FileNotFoundException, IOException {
		initCharCorrections();
		loadTexture(imgLocation);
	}
	
	private void loadTexture(String imgLocation) throws FileNotFoundException, IOException{
		
		texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(imgLocation));
	}
	
	private void initCharCorrections(){
		charCorrections = new HashMap();
		
		charCorrections.put("a", new Integer(0));
		charCorrections.put("b", new Integer(0));
		charCorrections.put("c", new Integer(0));
		charCorrections.put("d", new Integer(0));
		charCorrections.put("e", new Integer(0));
		charCorrections.put("f", new Integer(0));
		charCorrections.put("g", new Integer(0));
		charCorrections.put("h", new Integer(0));
		charCorrections.put("i", new Integer(0));
		charCorrections.put("j", new Integer(0));
		charCorrections.put("k", new Integer(0));
		charCorrections.put("l", new Integer(0));
		charCorrections.put("m", new Integer(0));
		charCorrections.put("n", new Integer(0));
		charCorrections.put("o", new Integer(0));
		charCorrections.put("p", new Integer(0));
		charCorrections.put("q", new Integer(0));
		charCorrections.put("r", new Integer(0));
		charCorrections.put("s", new Integer(0));
		charCorrections.put("t", new Integer(0));
		charCorrections.put("u", new Integer(0));
		charCorrections.put("v", new Integer(0));
		charCorrections.put("w", new Integer(0));
		charCorrections.put("x", new Integer(0));
		charCorrections.put("y", new Integer(0));
		charCorrections.put("z", new Integer(0));
		
		charCorrections.put(".", new Integer(0));
		charCorrections.put(":", new Integer(0));
		charCorrections.put("-", new Integer(0));
		charCorrections.put(",", new Integer(0));
		
	}
	
	public GraphicCharacter getChar(char toGet){
		
		if((toGet >= 'a') && (toGet <= 'z')){
			
			return new GraphicCharacter(((toGet - 'a') * CHAR_WIDTH)%MAX_TEXT_WIDTH, (((toGet - 'a') + 1) * CHAR_WIDTH - charCorrections.get(String.valueOf(toGet)))%MAX_TEXT_WIDTH, (toGet - 'a')/CHARACTERS_PER_ROW);//Adds the 'a' back in for correction lookup
			
		} else if(toGet == '.'){	
			return new GraphicCharacter((27 * CHAR_WIDTH)%MAX_TEXT_WIDTH, (28 * CHAR_WIDTH - charCorrections.get('.'))%MAX_TEXT_WIDTH, 3);
			
		} else if(toGet == ':'){
			return new GraphicCharacter((28 * CHAR_WIDTH)%MAX_TEXT_WIDTH, (29 * CHAR_WIDTH - charCorrections.get(':'))%MAX_TEXT_WIDTH, 3);
			
		} else if(toGet == '-'){
			return new GraphicCharacter((29 * CHAR_WIDTH)%MAX_TEXT_WIDTH, (30 * CHAR_WIDTH - charCorrections.get('-'))%MAX_TEXT_WIDTH, 3);
			
		} else if(toGet == ','){
			return new GraphicCharacter((30 * CHAR_WIDTH)%MAX_TEXT_WIDTH, (31 * CHAR_WIDTH - charCorrections.get(','))%MAX_TEXT_WIDTH, 3);
			
		} else throw new IllegalArgumentException(toGet + " is not an accepted character!");
	}
	
	

}

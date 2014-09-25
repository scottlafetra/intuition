package com.lafetra.scott.intuition.aztecBall;

public class Ball extends Block {
	
	public static final double MASS = 10;
	public static final double WIDTH = Game.HEIGHT/16.0;
	public static final double TRIM_WIDTH = WIDTH/8.0;
	
	private boolean primed;//for point scoring

	public Ball(float r, float g, float b) {
		super(MASS, WIDTH, WIDTH, r, g, b);
		primed = false;
	}
	
	/**
	 * Handles ball scoring.
	 * @param tripping If the ball is tripping the goal currently.
	 * @return True if a point is scored.
	 */
	public boolean isScoring(boolean tripping){
		if(tripping){
			primed = true;
			return false;
		} else if(primed){
			primed = false;
			return true;
		}
		
		return false;
	}
}
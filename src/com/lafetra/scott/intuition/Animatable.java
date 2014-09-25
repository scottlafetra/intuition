package com.lafetra.scott.intuition;

import javax.naming.NameNotFoundException;

import com.lafetra.scott.intuition.geom.Geom;

public interface Animatable {
	
	public static final double WALK_SPEED = 1;
	public static final double WALK_ANIMATE_RATIO = 0.005;
	public static final double MAX_KNEE_ANGLE_WALK = 20;
	public static final double MAX_HIP_ANGLE_WALK = 10;
	public static final double MAX_KNEE_ANGLE_RUN = 70;
	public static final double MAX_HIP_ANGLE_RUN = 45;
	
	public void startAnimation(Animation animation) throws NameNotFoundException;
	public void animate() throws NameNotFoundException;
	public void stopAnimating();
	public boolean isAnimationDone();
}

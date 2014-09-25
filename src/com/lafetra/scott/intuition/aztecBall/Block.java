package com.lafetra.scott.intuition.aztecBall;

import com.lafetra.scott.intuition.aztecBall.Game.Color;
import com.lafetra.scott.intuition.geom.Rectangle;
import com.lafetra.scott.intuition.physics.TangibleGroup;

public class Block extends TangibleGroup {

	public Block(double mass, double height, double width, float r, float g, float b) {
		super(mass);
		
		final double TRIM_WIDTH = width/8.0;
		
		add(new Rectangle(0, 0, width, height));
		
		Rectangle fill = new Rectangle(0, 0, width - TRIM_WIDTH*2, height - TRIM_WIDTH*2);
		fill.move(TRIM_WIDTH, TRIM_WIDTH);
		fill.setColor(r, g, b);
		add(fill);
		
	}

}

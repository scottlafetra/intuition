package com.lafetra.scott.intuition;

import java.util.ArrayList;

import com.lafetra.scott.intuition.geom.TransformMatrix;

public interface Renderable {
	public void draw(ArrayList<TransformMatrix> trans);//Passes the transform matrix needed to get to the higher scope's cords
	public void draw();//Passes the transform matrix needed to get to the higher scope's cords
	public void setColor(float r, float g, float b);
}

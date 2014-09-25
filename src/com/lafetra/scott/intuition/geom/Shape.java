package com.lafetra.scott.intuition.geom;

import java.util.ArrayList;

import com.lafetra.scott.intuition.Renderable;

public interface Shape extends Renderable, Transformable{
	
	public ArrayList<Point> getPoints();//Returns all points

}

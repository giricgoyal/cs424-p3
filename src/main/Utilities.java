package main;

import processing.core.PVector;

public class Utilities {

	public static boolean isWall = false;
	
	public static float Converter(float pixel) {
		if (isWall)
			return 5*pixel;
		else
			return pixel;
	}
	
	public static float markerWidth = Converter(30);
	public static float markerHeight = Converter(30);
	
	public static PVector mapSize;
    public static PVector mapOffset;
}

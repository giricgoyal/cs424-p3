package main;

import processing.core.PFont;
import processing.core.PShape;
import processing.core.PVector;

public class Utilities {

	public static boolean isWall = false;
	
	public static float Converter(float pixel) {
		if (isWall)
			return 5*pixel;
		else
			return pixel;
	}
	
	public static float width = Converter(1632);
	public static float height = Converter(461);
	
	public static float markerWidth = Converter(30);
	public static float markerHeight = Converter(40);
	
	public static float popUpWidth;
	public static float popUpHeight;
	
	public static float popUpX;
	public static float popUpY;
	
	public static PVector mapSize;
    public static PVector mapOffset;
    
    public static PFont font;
    
    public static PShape markerShape;
}

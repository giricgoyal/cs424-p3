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
	
	//public static float width = Converter(1632);
	public static float width = Converter(1360);
	public static float height = Converter(461);
	
	public static float markerWidth ;
	public static float markerHeight;
	
	public static float popUpWidth;
	public static float popUpHeight;
	
	public static float popUpX;
	public static float popUpY;
	
	public static PVector mapSize;
    public static PVector mapOffset;
    
    public static PFont font;
    
    public static PShape markerShape;
    
    public static int yearMin = 2001;
    public static int yearMax = 2010;
    
    public static int barWidth = (int)(Positions.histographWidth/10 - Utilities.Converter(5));

    
    public static int[] colorCodes =
    {
		0xAA8DD3C7, 
		0xAAFFFFB3, 
		0xAABEBADA,
		0xAAFB8072,
		0xAA80B1D3,
		0xAAFDB462,
		0xAAB3DE69,
		0xAAFCCDE5,
		0xAAD9D9D9,
		0xAABC808D,
		0xAACCEBC5,
		0xAAFFED56
	};
    
    public static float minActiveLatitude;
    public static float maxActiveLatitude;
    public static float minActiveLongitude;
    public static float maxActiveLongitude;

    public static int lowerBound = Integer.MAX_VALUE;
    public static int upperBound = Integer.MIN_VALUE;
    

}

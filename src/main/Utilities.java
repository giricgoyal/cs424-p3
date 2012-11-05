package main;

import com.modestmaps.InteractiveMap;

import processing.core.PFont;
import processing.core.PShape;
import processing.core.PVector;

public class Utilities {

	public static boolean isWall =false;
	
	public static float Converter(float pixel) {
		if (isWall)
			return 5*pixel;
		else
			return pixel;
	}
	
	public static float width = Converter(1632);
	//public static float width = Converter(1360);
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
		0xBB8DD3C7, 
		0xBBFFED56,
		0xBBFDB462,
		0xBBFB8072,
		0xBB80B1D3,
		0xBBBEBADA,
		0xBBB3DE69,
		0xBBFCCDE5,
		0xBBD9D9D9,
		0xBBBC808D,
		0xBBCCEBC5,
		0xBBFFFFB3,
	};
    
    public static float minActiveLatitude;
    public static float maxActiveLatitude;
    public static float minActiveLongitude;
    public static float maxActiveLongitude;

    
    public static int lowerBound = Integer.MAX_VALUE;
    public static int upperBound = Integer.MIN_VALUE;
    
    public static String defaultFocusAttribute = "Age";
    public static String focusAttribute = "Age";

    public static int zoomCity = 12;
    public static int zoomState = 9;
    
    public static Program program;
}

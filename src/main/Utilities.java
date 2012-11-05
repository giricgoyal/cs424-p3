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
	
	//public static float width = Converter(1632);
	public static float width = Converter(1360);
	public static float height = Converter(461);
	
	public static float markerWidth ;
	public static float markerHeight;
	
	public static float popUpWidth;
	public static float popUpHeight;
	
	public static float popUpX;
	public static float popUpY;
	
	public static PVector mapSize = new PVector(width/2, height);
    public static PVector mapOffset = new PVector(0,0);
    
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

    
    public static int lowerBound = 0;
    public static int upperBound = Integer.MIN_VALUE;
    
    public static String defaultFocusAttribute = "Age";
    public static String focusAttribute = "Age";

    public static int zoomCity = 12;
    public static int zoomState = 9;
    
    public static Program program;
    
    public static String[] histOptions = null;
	public static int activeYear = 2005;
	
	public static String hist1String = "# Crashes for Years 2001 to 2010";
	public static String hist2String = "# Crashes for the Year";
}

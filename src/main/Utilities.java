package main;

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


	public static Float[] perStatePopulation = {
		(float) 4611.5,
		(float) 670.5,
		(float) 0.0,
		(float) 5890.4,
		(float) 2801.9,
		(float) 35919.2,
		(float) 0.0,
		(float) 4708.7,
		(float) 3510.8,
		(float) 1577.3,
		(float) 1850.3,
		(float) 117786.5,
		(float) 19054.8,
		(float) 0.0,
		(float) 11295.3,
		(float) 11447.6,
		(float) 112649.7,
		(float) 16308.1,
		(float) 12980.8,
		(float) 22766.5,
		(float) 24203.3,
		(float) 24477.6,
		(float) 21316.2,
		(float) 25593.1,
		(float) 26443.9,
		(float) 29991.8,
		(float) 25147.4,
		(float) 22908.5,
		(float) 25817.5,
		(float) 3947.8,
		(float) 31769.2,
		(float) 32446.7,
		(float) 31296.3,
		(float) 38654.3,
		(float) 31946.7,
		(float) 319185,
		(float) 38857.3,
		(float) 3650.6,
		(float) 311470.6,
		(float) 43591.2,
		(float) 43652,
		(float) 412493,
		(float) 0.0,
		(float) 41061.9,
		(float) 44336.1,
		(float) 4782.4,
		(float) 46047.2,
		(float) 423177.3,
		(float) 42511.5,
		(float) 5620.7,
		(float) 57611.4,
		(float) 0.0,
		(float) 56338.4,
		(float) 51825.8,
		(float) 55558.1,
		(float) 5524.9};

	
}


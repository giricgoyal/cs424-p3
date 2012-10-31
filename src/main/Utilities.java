package main;

public class Utilities {

	public static boolean isWall = false;
	
	public static float Converter(float pixel) {
		if (isWall)
			return 5*pixel;
		else
			return pixel;
	}
	
	public static float markerWidth = Converter(10);
	public static float markerHeight = Converter(10);
}

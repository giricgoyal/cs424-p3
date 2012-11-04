package main;

public class Positions {
	
	
	public static float histographWidth = Utilities.width/4 - Utilities.Converter(80);
	public static float histographHeight = Utilities.height/3 - Utilities.Converter(40);
	public static float histograph1X = Utilities.width/2 + Utilities.width/8 + Utilities.Converter(20);
	public static float histograph1Y = Utilities.height/6;
	
	public static float histograph2X = Utilities.width/2 + Utilities.width/4 + Utilities.width/8 + Utilities.Converter(20);
	public static float histograph2Y = Utilities.height/6;
	
	public static float keyboardX = Utilities.width/2 + Utilities.width/4 + Utilities.Converter(40);
	public static float keyboardY = Utilities.height/3*2 + Utilities.Converter(30);
	public static float keyboardWidth = Utilities.width/4 - Utilities.Converter(50);
	public static float keyboardHeight = Utilities.height/3 - Utilities.Converter(40);
	
	
	public static float medallionX = Utilities.width/2+ Utilities.Converter(20);
	public static float medallionY = histograph1Y+histographHeight+Utilities.Converter(10);
	public static float medallionSide = Utilities.height*0.4f;
	

	//public static float keyboardX = Utilities.width*0.6f;
	//public static float keyboardY = Utilities.height*0.6f;
	//public static float keyboardWidth = Utilities.width*0.22f;
	//public static float keyboardHeight = Utilities.height*0.25f;
	
	public static float textBoxX = Utilities.width/2 + Utilities.width/4 + Utilities.Converter(44);
	public static float textBoxY = Utilities.height/3*2;
	public static float textBoxWidth = Utilities.width/4 - Utilities.Converter(58);
	public static float textBoxHeight = Utilities.height*0.05f;
	
	public static float suggestionBoxX;
	public static float suggestionBoxY;
	public static float suggestionBoxWidth;
	public static float suggestionBoxHeight;

}

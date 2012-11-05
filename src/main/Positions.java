package main;

public class Positions {
	
	
	public static float histographWidth = Utilities.width/4 - Utilities.Converter(80);
	public static float histographHeight = Utilities.height/3;
	public static float histograph1X = Utilities.width/2 + Utilities.width/8 + Utilities.Converter(20);
	public static float histograph1Y = Utilities.height/6 +  Utilities.Converter(30);
	
	public static float histograph2X = Utilities.width/2 + Utilities.width/4 + Utilities.width/8 + Utilities.Converter(20);
	public static float histograph2Y = Utilities.height/6 + Utilities.Converter(30);
	
	public static float keyboardX = Utilities.width/2 + Utilities.width/4 + Utilities.Converter(40);
	public static float keyboardY = Utilities.height/3*2 + Utilities.Converter(30);
	public static float keyboardWidth = Utilities.width/4 - Utilities.Converter(50);
	public static float keyboardHeight = Utilities.height/3 - Utilities.Converter(40);
	
	
	public static float medallionX = Utilities.width/2+ Utilities.Converter(40);
	public static float medallionY = histograph1Y+histographHeight - Utilities.Converter(20);
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
	
	public static float circleButtonSize = Utilities.Converter(30);
	public static float circleButtonVSpacing = Utilities.Converter(10);
	
	public static float buttonProviderX = Utilities.mapOffset.x+ Utilities.mapSize.x + Utilities.Converter(10);
	public static float buttonProviderY = Utilities.mapOffset.y+ Utilities.mapSize.y - circleButtonVSpacing - circleButtonSize;
	public static float buttonProviderWidth = circleButtonSize;
	public static float buttonProviderHeight = circleButtonSize;
	
	public static float buttonDecX = buttonProviderX;
	public static float buttonDecY = buttonProviderY - circleButtonVSpacing- circleButtonSize;
	public static float buttonDecWidth = circleButtonSize;
	public static float buttonDecHeight = circleButtonSize;
	
	public static float buttonIncX = buttonProviderX;
	public static float buttonIncY = buttonDecY - circleButtonVSpacing- circleButtonSize;
	public static float buttonIncWidth = circleButtonSize;
	public static float buttonIncHeight = circleButtonSize;
	
	public static float buttonMinusX = buttonProviderX;
	public static float buttonMinusY = buttonIncY - circleButtonVSpacing- circleButtonSize;
	public static float buttonMinusWidth = circleButtonSize;
	public static float buttonMinusHeight = circleButtonSize;
	
	public static float buttonPlusX = buttonProviderX;
	public static float buttonPlusY = buttonMinusY - circleButtonVSpacing- circleButtonSize; ;
	public static float buttonPlusWidth = circleButtonSize;
	public static float buttonPlusHeight = circleButtonSize;
	
	
	
	public static float timelineX = buttonDecX + buttonDecWidth + Utilities.Converter(1);
	public static float timelineY = buttonDecY;
	public static float timelineWidth = buttonDecWidth;
	public static float timelineHeight = buttonDecHeight;

}

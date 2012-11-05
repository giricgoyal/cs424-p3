package main;

public class Positions {
	
	public static float histogramAreaWidth = Utilities.width/2 - Utilities.Converter(40);
	public static float histogramAreaHeight = Utilities.height/5 + Utilities.Converter(40);
	public static float histogramAreaX = Utilities.width/2 + Utilities.width/4;
	public static float histogramAreaY = Utilities.height/6; 
	
	public static float histographWidth = histogramAreaWidth/2 - Utilities.Converter(80);
	public static float histographHeight = histogramAreaHeight - Utilities.Converter(60);
	public static float histograph1X = histogramAreaX - histogramAreaWidth/4 + Utilities.Converter(70);
	public static float histograph1Y = histogramAreaY - Utilities.Converter(5);
	
	public static float histograph2X = histograph1X + histogramAreaWidth/2 - Utilities.Converter(50);
	public static float histograph2Y = histograph1Y;
	

	//public static float keyboardX = Utilities.width*0.6f;
	//public static float keyboardY = Utilities.height*0.6f;
	//public static float keyboardWidth = Utilities.width*0.22f;
	//public static float keyboardHeight = Utilities.height*0.25f;
	
	public static float suggestionBoxX;
	public static float suggestionBoxY;
	public static float suggestionBoxWidth;
	public static float suggestionBoxHeight;
	
	public static float circleButtonSize = Utilities.Converter(25);
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
	
	public static float medallionX = buttonProviderX+buttonProviderWidth+circleButtonVSpacing;
	public static float medallionY = buttonPlusY;
	public static float medallionSide = buttonProviderY + buttonProviderHeight - medallionY;
	
	public static float buttonKeyX = medallionX+medallionSide +2*circleButtonVSpacing;
	public static float buttonKeyY = medallionY;
	public static float buttonKeyWidth = Utilities.width/6;
	public static float buttonKeyHeight = Utilities.height*0.05f;
	
	public static float buttonVSpacing =  (Utilities.height-buttonKeyY) / 4 - buttonKeyHeight;
	
	public static float buttonFilterX = buttonKeyX;
	public static float buttonFilterY = buttonKeyY + buttonKeyHeight+buttonVSpacing;
	public static float buttonFilterWidth = buttonKeyWidth;
	public static float buttonFilterHeight = buttonKeyHeight;
	
	public static float buttonUpdateX = buttonFilterX;
	public static float buttonUpdateY = buttonFilterY + buttonFilterHeight+buttonVSpacing;
	public static float buttonUpdateWidth = buttonFilterWidth;
	public static float buttonUpdateHeight = buttonFilterHeight;
	
	public static float textBoxX = buttonKeyX + buttonKeyWidth + circleButtonVSpacing;
	public static float textBoxY = buttonKeyY;
	public static float textBoxWidth = Utilities.width - textBoxX - circleButtonVSpacing;
	public static float textBoxHeight = Utilities.height*0.05f;
	
	public static float keyboardX = textBoxX;
	public static float keyboardY = textBoxY+textBoxHeight+circleButtonVSpacing;
	public static float keyboardWidth = textBoxWidth;
	public static float keyboardHeight = Utilities.height - keyboardY - circleButtonVSpacing;
	
	public static float timelineX = Utilities.mapOffset.x+Utilities.mapSize.x+ buttonVSpacing;
	public static float timelineY = histogramAreaHeight+2*buttonVSpacing;
	public static float timelineWidth = Utilities.width - timelineX-buttonVSpacing;
	public static float timelineHeight = buttonPlusY-circleButtonVSpacing-timelineY;

}

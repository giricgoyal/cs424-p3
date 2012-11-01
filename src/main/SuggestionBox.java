/**
 * Class to create the suggestion and text box
 * @author giric
 */


package main;


import processing.core.PApplet;
import processing.core.PConstants;

public class SuggestionBox extends BasicControl {
	
	float maximumHeight;
	int textBoxBorderColor;
	int textBoxBackgroundColor;
	int suggestionBoxBorderColor;
	int suggestionBoxBackgroundColor;
	int textBoxTextColor;
	
	String textBoxText;
	
	/**
	 * temp test string
	 */
	String[] testString = {"hello","yoyo honey singh","holaaa","wadddhhuppp","blah blah","uncle","haloween","home"};
	
	
	public SuggestionBox(PApplet parent, float x, float y, float width,	float height) {
		super(parent, x, y, width, height);
		// TODO Auto-generated constructor stub
		this.textBoxText = "";
		this.textBoxBorderColor = Colors.black;
		this.textBoxBackgroundColor = Colors.white;
		this.suggestionBoxBackgroundColor = Colors.light;
		this.suggestionBoxBorderColor = Colors.dark;
		this.textBoxTextColor = Colors.black;
		
		Positions.suggestionBoxX = myX;
		Positions.suggestionBoxY = myY - Utilities.Converter(1) - myHeight*5;
		Positions.suggestionBoxWidth = myWidth;
		Positions.suggestionBoxHeight = myHeight*5;

		
	}

	//text box for searches
	
	
	public void draw() {
		
		parent.strokeWeight(Utilities.Converter(1));
		parent.stroke(textBoxBorderColor);
		parent.fill(textBoxBackgroundColor);
		parent.rectMode(PConstants.CORNER);
		parent.rect(myX, myY, myWidth, myHeight);
		parent.rect(Positions.suggestionBoxX, Positions.suggestionBoxY, Positions.suggestionBoxWidth, Positions.suggestionBoxHeight);
		parent.textAlign(PConstants.LEFT, PConstants.CENTER);
		parent.textSize((float) myHeight*0.6f);
		parent.fill(textBoxTextColor);
		parent.text(textBoxText, Positions.textBoxX, Positions.textBoxY + Positions.textBoxHeight/2);
		
		int count = 0;
		int matchCount = 0;
		
		if (!textBoxText.isEmpty()) {
			parent.textAlign(PConstants.LEFT, PConstants.CENTER);
			parent.textSize(Positions.suggestionBoxHeight/5*0.6f);
			parent.fill(Colors.black);
			while(count<testString.length) {
				if(testString[count].contains(textBoxText)){
					parent.text(testString[count], Positions.suggestionBoxX, Positions.suggestionBoxY + myHeight*(5-matchCount) - myHeight/2);
					matchCount++;
				}
				if (matchCount == 5) {
					break;
				}
			count++;
			}
		}
		else {
			while(count<5) {
				parent.text(testString[count], Positions.suggestionBoxX, Positions.suggestionBoxY + myHeight*(5-matchCount) - myHeight/2);
				matchCount++;
			count++;
			}
		}
	}
	
	/**
	 * method to update the textbox (autoComplete0
	 * @param charNum
	 */
	
	public void updateTextBox(int charNum) {
		System.out.println(charNum);
		if (charNum == -1) {
			if (textBoxText.isEmpty()) {
				System.out.println("empty");
			}
			else {
				textBoxText = textBoxText.substring(0, textBoxText.length()-1);
			}
		}
		else {
			textBoxText = textBoxText.concat(String.valueOf((char)charNum));
		}
		System.out.println(textBoxText);
	}
	
	
	/**
	 * implementing the click function
	 * @param x = mouseX
	 * @param y = mouseY
	 */
	public void Click(float x, float y) {
		System.out.print("click");
		int count = 0;
		int matchCount = 0;
		String clickedString = "";
		if (!textBoxText.isEmpty()) {
			while(count<testString.length) {
				if(testString[count].contains(textBoxText)){
					if(x > Positions.suggestionBoxX && x < Positions.suggestionBoxX + Positions.suggestionBoxWidth) {
						if(y > Positions.suggestionBoxY - myHeight*(4-matchCount) && y < Positions.suggestionBoxY + myHeight*(5-matchCount)) {
							clickedString = testString[count];
						}
					}
					matchCount++;
				}
			count++;
			}
		}
		else {
			while(count<5) {
				if(testString[count].contains(textBoxText)){
					if(x > Positions.suggestionBoxX && x < Positions.suggestionBoxX + Positions.suggestionBoxWidth) {
						if(y > Positions.suggestionBoxY - myHeight*(4-matchCount) && y < Positions.suggestionBoxY + myHeight*(5-matchCount)) {
							clickedString = testString[count];
						}
					}
				//parent.text(testString[count], Positions.suggestionBoxX, Positions.suggestionBoxY + myHeight*(5-matchCount) - myHeight/2);
				matchCount++;
				}
			count++;
			}
		}
		
		/**
		 * get the name of the string upon click
		 * currently just displaying on the console
		 * modify below to assign a variable or something
		 */
		
		
		System.out.println(clickedString);
		
	}
}

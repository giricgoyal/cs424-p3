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
	
	
	public SuggestionBox(PApplet parent, float x, float y, float width,	float height) {
		super(parent, x, y, width, height);
		// TODO Auto-generated constructor stub
		this.textBoxText = "";
		this.textBoxBorderColor = Colors.black;
		this.textBoxBackgroundColor = Colors.white;
		this.suggestionBoxBackgroundColor = Colors.light;
		this.suggestionBoxBorderColor = Colors.dark;
		this.textBoxTextColor = Colors.black;
		
		
		
	}

	//text box for searches
	
	
	public void draw() {
		
		parent.strokeWeight(Utilities.Converter(1));
		parent.stroke(textBoxBorderColor);
		parent.fill(textBoxBackgroundColor);
		parent.rectMode(PConstants.CORNER);
		parent.rect(myX, myY, myWidth, myHeight);
		parent.textAlign(PConstants.LEFT, PConstants.CENTER);
		parent.textSize((float) myHeight*0.6f);
		parent.fill(textBoxTextColor);
		parent.text(textBoxText, Positions.suggestionBoxX, Positions.suggestionBoxY + Positions.suggestionBoxHeight/2);
		
		
	}
	
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
	
	public void updateSuggestionBox() {
		
	
	}
}

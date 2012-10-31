/**
 * 
 */
package main;

import processing.core.PApplet;
import processing.core.PConstants;

/**
 * @author giric
 *
 */
public class PopUp {

	public PApplet parent;
	float coordX;
	float coordY;
	int color;
	float upperLeftX;
	float upperLeftY;
	float upperRightX;
	float upperRightY;
	float lowerRightX;
	float lowerRightY;
	float lowerLeftX;
	float lowerLeftY;
	float height;
	float width;
	float triangleLeftX;
	float triangleLeftY;
	float triangleRightX;
	float triangleRightY;
	
	float totalHeight;
	float totalWidth;
	
	boolean check;
	boolean upper;
	boolean lower;
	boolean left;
	boolean right;
	
	public PopUp(PApplet p, float x, float y,int color) {
		
		this.parent = p;
		this.color  = color;
		this.coordX = x;
		this.coordY = y;
		
		
		this.width =  Utilities.Converter(300);
		this.height =  Utilities.Converter(170);		
		
		this.upperLeftX = coordX -  Utilities.Converter(50);
		this.upperLeftY = coordY -  Utilities.Converter(225);
		
		this.upperRightX = upperLeftX + width;
		this.upperRightY = upperLeftY;
		
		this.lowerRightX = upperRightX;
		this.lowerRightY = upperRightY + height;
		
		this.lowerLeftX = upperLeftX;
		this.lowerLeftY = upperLeftY + height;
		
		this.triangleLeftX = coordX +  Utilities.Converter(10);
		this.triangleLeftY = upperLeftY + height;
		
		this.triangleRightX = coordX +  Utilities.Converter(25);
		this.triangleRightY = upperLeftY + height;
		
		this.check = true;
		this.upper = this.lower = this.left = this.right = false;
		
		Utilities.popUpWidth = this.width;
		Utilities.popUpHeight = this.height;
		Utilities.popUpX = this.upperLeftX;
		Utilities.popUpY = this.upperLeftY;
		
		/**
		 * check if popUp is intersected by the top side of the window
		 * if yes, translate the popUp
		 */
		if (upperLeftY < Utilities.mapOffset.y) {
			
			this.upper = true;
			this.upperLeftY = coordY + Utilities.Converter(230);
			this.upperRightY = upperLeftY;
			this.lowerRightY = upperRightY - height;
			this.lowerLeftY = lowerRightY;
			this.triangleLeftY = upperLeftY - height;
			this.triangleRightY = upperLeftY - height;
			Utilities.popUpY = this.lowerLeftY;
			
		}
		
		/**
		 * check if popUp is intersected by the right side of the window
		 * if yes, translate the popUp
		 */
		
		if (upperRightX > Utilities.mapSize.x+Utilities.mapOffset.x) {
			this.right = true;
			this.triangleLeftX = coordX - Utilities.Converter(10);
			this.triangleRightX = coordX - Utilities.Converter(25);
			this.upperLeftX = coordX - width;
			this.upperRightX = upperLeftX + width;
			this.lowerLeftX = upperLeftX;
			this.lowerRightX = upperRightX;
			Utilities.popUpX = this.upperLeftX;
			
		}
		

		/**
		 * check if popUp is intersected by the left side of the window
		 * if yes, translate the popUp;
		 */
		
		if(upperLeftX < 0) {
			this.left = true;
			this.triangleLeftX = coordX + Utilities.Converter(10);
			this.triangleRightX = coordX + Utilities.Converter(25);
			this.upperLeftX = coordX + Utilities.Converter(5);
			this.upperRightX = upperLeftX + width;
			this.lowerLeftX = upperLeftX;
			this.lowerRightX = upperRightX;
			Utilities.popUpX = this.upperLeftX;
			
		}
		

		
	}
	
	private void displayText() {
		parent.textFont(Utilities.font, Utilities.Converter(14));
		parent.fill(Colors.white);
		parent.textAlign(PConstants.LEFT, PConstants.TOP);
		parent.text("sampleText\nTesting\nCheck\nCheck\n\n**Information goes here**", Utilities.popUpX, Utilities.popUpY);
	}
	
	public void invertCheck() {
		check = !check;
	}
	
	public void draw() {
		parent.noStroke();
		parent.fill(color);
		parent.beginShape();
		parent.vertex(coordX, coordY);
		parent.vertex(triangleLeftX, triangleLeftY);
		parent.vertex(lowerLeftX, lowerLeftY);
		parent.vertex(upperLeftX,upperLeftY);
		parent.vertex(upperRightX, upperRightY);
		parent.vertex(lowerRightX, lowerRightY);
		parent.vertex(triangleRightX, triangleRightY);
		parent.endShape();		
		displayText();
	}
	
	public float getX() {
		return upperLeftX;
	}
	
	public float getY() {
		return upperLeftY;
	}
	
	public boolean getCheck() {
		return check;
	}
}

/**
 * 
 */
package main;

import processing.core.PApplet;

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
		this.height =  Utilities.Converter(200);
		
		this.upperLeftX = coordX -  Utilities.Converter(50);
		this.upperLeftY = coordY -  Utilities.Converter(250);
		
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
		
		/**
		 * check if popUp is intersected by the top side of the window
		 * if yes, translate the popUp
		 */
		if (upperLeftY < 0) {
			
			this.upper = true;
			this.upperLeftY = coordY + Utilities.Converter(250);
			this.upperRightY = upperLeftY;
			this.lowerRightY = upperRightY - height;
			this.lowerLeftY = lowerRightY;
			this.triangleLeftY = upperLeftY - height;
			this.triangleRightY = upperLeftY - height;
			
		}
		
		/**
		 * check if popUp is intersected by the right side of the window
		 * if yes, translate the popUp
		 */
		
		if (upperRightX > Utilities.mapSize.x+Utilities.mapOffset.x) {
			this.right = true;
			
		}
	}
	
	private void displayText() {
		
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

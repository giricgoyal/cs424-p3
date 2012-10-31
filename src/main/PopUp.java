/**
 * 
 */
package main;

import processing.core.PApplet;

/**
 * @author giric
 *
 */


//CHANGE ALL THE CONSTANTS TO Utilities.Converter(constant)  !!!
public class PopUp {

	public PApplet parent;
	float coordX;
	float coordY;
	int color;
	float upperX;
	float upperY;
	float height;
	float width;
	boolean check;
	
	public PopUp(PApplet p, float x, float y, int color) {
		this.parent = p;
		this.coordX = x;
		this.coordY = y;
		this.color  = color;
		upperX = coordX - 50;
		upperY = coordY - 250;
		width = 300;
		height = 200;
		check = true;
	}
	
	private void createShape() {
		parent.noStroke();
		parent.fill(color);
		parent.beginShape();
			parent.vertex(coordX, coordY);
			parent.vertex(coordX + 10, upperY + height);
			parent.vertex(coordX + 25, upperY + height);
		parent.endShape();
		parent.beginShape();
			parent.vertex(upperX,upperY);
			parent.vertex(upperX + width, upperY);
			parent.vertex(upperX + width, upperY + height);
			parent.vertex(upperX, upperY + height);
		parent.endShape();		
	}
	
	public void invertCheck() {
		check = !check;
	}
	
	public void draw() {
		createShape();
	}
	
	public float getX() {
		return upperX;
	}
	
	public float getY() {
		return upperY;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public boolean getCheck() {
		return check;
	}
}

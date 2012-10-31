/**
 * Class to create markers on the map
 * @author giric
 */


package main;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;

import com.modestmaps.geo.Location;

public class Marker{
	//Marker of a single crash on the map.
	//Needs: Shape, color, x, y, coordX, coordY
	public Location location;
	public PApplet parent;
	
	float coordX;
	float coordY;
	public PShape shape;
	public PShape layer1;
	int color;
	
	
	
	public Marker(PApplet p, float x, float y, int color) {
		// TODO Auto-generated constructor stub	
		this.parent = p;
		this.color = color;
		this.coordX = x;
		this.coordY = y;
		shape = parent.loadShape("marker.svg");			//for createShape1
		layer1 = shape.getChild("svg_4");				//for createShape1
	}
	
	
	private void createShape1() {
		shape.disableStyle();
		parent.fill(color);
		parent.shapeMode(PConstants.CORNER);
		parent.shape(shape, coordX - shape.width/50,coordY - shape.height/50, shape.width/50, shape.height/50);
		parent.fill(0);
		parent.shape(layer1, coordX - shape.width/50,coordY - shape.height/50, layer1.width/50, layer1.height/50);
	}

	public void draw() {
		// TODO Auto-generated method stub
		createShape1();
	}
	
	public float getWidth() {
		return shape.width/50;
	}
	
	public float getHeight() {
		return shape.height/50;
	}
	
	/**
	 * diego's trial code, you might need to comment out later
	 * @param x
	 * @param y
	 */
	
	public void draw(float x, float y) {
		parent.fill(0xFF0000FF);
		parent.noStroke();
		parent.ellipse(x, y, Utilities.Converter(10), Utilities.Converter(10));
	}
	
	public Marker(PApplet p, Location l) {this.parent=p; this.location=l;}
	
}

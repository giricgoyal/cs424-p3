/**
 * Class to create markers on the map
 * @author giric
 */


package main;

import java.io.File;
import java.util.Properties;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;

import com.modestmaps.geo.Location;

public class Marker{
	//Marker of a single crash on the map.
	//Needs: Shape, color, x, y, coordX, coordY
	public Location location;
	public PApplet parent;
	//coord
	public float coordX;
	public float coordY;
	
	//pixels
	public float x;
	public float y;
	public PShape shape;
	public PShape shapeBorderLayer;
	public int color;
	public boolean isOpen;
	
	public PopUp popUp;
	
	
	
	public Marker(PApplet p, float cx, float cy, int color) {
		// TODO Auto-generated constructor stub	
		this.parent = p;
		this.color = color;
		this.coordX = cx;
		this.coordY = cy;
		this.shape = parent.loadShape("marker.svg");
		this.shapeBorderLayer = shape.getChild("svg_4");
		this.isOpen=false;
	}


	public void draw() {
		if (shape!=null)  {
			shape.disableStyle();
			//DRAW THE MARKER
			parent.fill(color);
			parent.shapeMode(PConstants.CORNER);
			parent.shape(shape, x, y, Utilities.markerWidth, Utilities.markerHeight);
			parent.fill(Colors.black);
			parent.shape(shapeBorderLayer, x, y, Utilities.markerWidth, Utilities.markerHeight);
			
			if (isOpen) {
				popUp = new PopUp(parent, x,y,color);
				popUp.draw();
			}
		}
	}


	
	public Marker(PApplet p, Location l) {this.parent=p; this.location=l;}
	
}

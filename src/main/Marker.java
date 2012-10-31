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
	
	//pixels
	public float x;
	public float y;
	public PShape shape;
	public PShape shapeBorderLayer;
	public int color;
	public boolean isOpen;
	
	public PopUp popUp;
	
	
	
	public Marker(PApplet p, Location l, int color) {
		// TODO Auto-generated constructor stub	
		this.parent = p;
		this.color = color;
		this.location=l;
		this.shape = Utilities.markerShape;
		
		this.isOpen=false;
	}


	public void draw() {
		if (shape!=null)  {
			shape.disableStyle();
			//DRAW THE MARKER
			parent.fill(color);
			parent.shapeMode(PConstants.CENTER);
			parent.shape(shape, x-Utilities.markerWidth/2, y-Utilities.markerHeight/2, Utilities.markerWidth, Utilities.markerHeight);
			
			if (isOpen) {
				popUp = new PopUp(parent, x,y,color);
				popUp.draw();
			}
		}
	}
	
}

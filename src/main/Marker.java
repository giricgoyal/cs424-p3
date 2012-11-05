/**
 * Class to create markers on the map
 * @author giric
 */
package main;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;

import com.modestmaps.geo.Location;

import db.QueryManager;

public class Marker{
	//Marker of a single crash on the map.
	//Needs: Shape, color, x, y, coordX, coordY
	public Location location;
	public PApplet parent;
	
	//pixels
	public float x;
	public float y;
	public int id;
	public String infoText=null;
	public PShape shape;
	public PShape shapeBorderLayer;
	public int color;
	public boolean isOpen;
	
	public PopUp popUp;
	
	
	
	public Marker(PApplet p, int id, Location l, int color) {
		// TODO Auto-generated constructor stub	
		this.parent = p;
		this.color = color;
		this.location=l;
		this.shape = Utilities.markerShape;
		this.id=id;
		
		this.isOpen=false;
		
		Utilities.markerWidth = Utilities.Converter(shape.width/50);
		Utilities.markerHeight = Utilities.Converter(shape.height/50);
	}


	public void draw() {
		if (shape!=null)  {
			shape.disableStyle();
			//DRAW THE MARKER
			if (x > 0 && x < Utilities.mapSize.x && y > 0 && y < Utilities.mapSize.y){
				parent.fill(color);
				parent.shapeMode(PConstants.CENTER);
				parent.shape(shape, x, y-Utilities.markerHeight/2, Utilities.markerWidth, Utilities.markerHeight);
				
				parent.fill(Colors.black);
				parent.ellipseMode(PConstants.CENTER);
				parent.ellipse(x, y, Utilities.Converter(5), Utilities.Converter(5));
				
				if (isOpen) {
					popUp = new PopUp(parent, x,y,color);
					if (this.infoText==null) {
						QueryManager qm=new QueryManager(parent);
						this.infoText=qm.getDataCrashes(this.id);
					}
					popUp.draw(this.infoText);
				}
			}
		}
	}
	
}

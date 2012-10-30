package main;

import processing.core.PApplet;

import com.modestmaps.geo.Location;

public class Marker{
	//Marker of a single crash on the map.
	//Needs: Shape, color, x, y, coordX, coordY
	public Location location;
	public PApplet parent;
	public void draw(float x, float y) {
		parent.fill(0xFF0000FF);
		parent.noStroke();
		parent.ellipse(x, y, Utilities.Converter(10), Utilities.Converter(10));
	}
	
	public Marker(PApplet p, Location l) {this.parent=p; this.location=l;}
}

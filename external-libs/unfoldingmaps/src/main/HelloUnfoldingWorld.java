package main;

import processing.core.PApplet;
import codeanticode.glgraphics.GLConstants;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.OpenStreetMap;
import de.fhpotsdam.unfolding.utils.MapUtils;

/**
 * Hello Unfolding World.
 * 
 * Download the distribution with examples for many more examples and features.
 */
@SuppressWarnings("serial")
public class HelloUnfoldingWorld extends PApplet {

	UnfoldingMap map;

    static public void main(String args[]) {
	    PApplet.main(new String[] { "main.HelloUnfoldingWorld" });
    }
	
	public void setup() {
		size(1800, 900, GLConstants.GLGRAPHICS);

		map = new UnfoldingMap(this, new OpenStreetMap.OpenStreetMapProvider());
		map.zoomAndPanTo(new Location(41f, -87f), 10);

		MapUtils.createDefaultEventDispatcher(this, map);
		
		map.addMarkers(new SimplePointMarker(new Location (41.87019997595688,-87.64834642410278)));
	}

	public void draw() {
		background(0);
		map.draw();
	}

}

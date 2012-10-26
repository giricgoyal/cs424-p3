package main;

import codeanticode.glgraphics.GLConstants;
import codeanticode.glgraphics.GLGraphics;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;


import omicronAPI.OmicronAPI;
import processing.core.*;

@SuppressWarnings("serial")
public class Program extends PApplet {
	
    static public void main(String args[]) {
    	System.out.println("premain");
	    PApplet.main(new String[] { "main.Program" });
	    System.out.println("postmain");
    }
   	
	OmicronAPI omicronManager;
	TouchListener touchListener;
    
	boolean isWall=false;
    
	UnfoldingMap map;

	public void setup() {
		if (isWall) {
			size(8160, 2304, P3D);
			initOmicron();
		}
		else {
			size(1632, 461, P3D);						
		}
		
		map = new UnfoldingMap(this,0,0,width/2, height);
		map.mapDisplay.setProvider( new Microsoft.AerialProvider());
		MapUtils.createDefaultEventDispatcher(this, map);
	}

	public void draw() {
    	map.draw();
    	/*Location location = map.getLocation(mouseX, mouseY);
    	fill(0);
    	text(location.getLat() + ", " + location.getLon(), mouseX, mouseY);*/
    	
    	if (isWall) {
    		omicronManager.process();
    	}
    }
	
	public void initOmicron() {
		// Creates the OmicronAPI object. This is placed in init() since we want to use fullscreen
		omicronManager = new OmicronAPI(this);
		  
		// Removes the title bar for full screen mode (present mode will not work on Cyber-commons wall)
		omicronManager.setFullscreen(true);
		
		// Make the connection to the tracker machine
		omicronManager.ConnectToTracker(7001, 7340, "131.193.77.159");
		// Create a listener to get events
		touchListener = new TouchListener();
		touchListener.setThings(this);
		// Register listener with OmicronAPI
		omicronManager.setTouchListener(touchListener);
	}
	
	public void touchDown(int ID, float xPos, float yPos, float xWidth, float yWidth){
	//	myClick(xPos, yPos);
		System.out.println("TouchDown");
		
	}
	
	public void touchUp(int ID, float xPos, float yPos, float xWidth, float yWidth){
	//	myClick(xPos, yPos);
		System.out.println("TouchUp");
	}
	
	public void touchMove(int ID, float xPos, float yPos, float xWidth, float yWidth){
	//	myClick(xPos, yPos);
		System.out.println("TouchMove");
	}
}

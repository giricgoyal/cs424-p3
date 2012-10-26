package main;

import java.util.Hashtable;

import omicronAPI.OmicronAPI;
import processing.core.*;
import com.modestmaps.*;
import com.modestmaps.geo.*;
import com.modestmaps.providers.*;

@SuppressWarnings("serial")
public class Program extends PApplet {
	
    static public void main(String args[]) {
    	System.out.println("premain");
	    PApplet.main(new String[] { "main.Program" });
	    System.out.println("postmain");
    }
    
    PVector mapSize;
    PVector mapOffset;
   	
	OmicronAPI omicronManager;
	TouchListener touchListener;
    
	boolean isWall=false;
    
	InteractiveMap map;

	public void setup() {
		
		if (isWall) {
			size(8160, 2304);
			initOmicron();
		}
		else {
			size(1632, 461);						
		}
		
		initMap();
	}

	public void draw() {		
		map.draw();
		this.fill(0);
		this.rectMode(PConstants.CORNERS);
		this.rect(0, 0, width, mapOffset.y);
		this.rect(0, 0, mapOffset.x, height);
		this.rect(0, mapOffset.y+mapSize.y, width, height);
		this.rect(mapOffset.x+mapSize.x, 0, width, height);
    	
    	if (isWall) {
    		omicronManager.process();
    	}
    }
	
	int currentProvider=0;
	final int zoomInterState = 4;
	final int zoomState = 7;
	final int zoomCity = 11;	
	Location locationUSA = new Location(38.962f,  -93.928f); 
	Location locationIllinois = new Location(40.4298f,  -88.9244f); 
	Location locationChicago = new Location(41.85f,  -87.65f); 
	public void initMap() {
		// OpenStreetMap would be like this:
		//map = new InteractiveMap(this, new OpenStreetMapProvider());
		// but it's a free open source project, so don't bother their server too much
  
		// AOL/MapQuest provides open tiles too
		// see http://developer.mapquest.com/web/products/open/map for terms
		// and this is how to use them:
		
		// zoom 0 is the whole world, 19 is street level
		mapSize = new PVector( width/2, height );
		mapOffset = new PVector(0,0);
		map =  new InteractiveMap(this, new Microsoft.RoadProvider(), mapOffset.x, mapOffset.y, mapSize.x, mapSize.y );
		map.panTo(locationChicago);
		map.setZoom(zoomInterState);
		setMapProvider(currentProvider);
	}
	
	void setMapProvider(int newProviderID){
		switch( newProviderID ){
	    	case 0: map.setMapProvider( new Microsoft.RoadProvider() ); break;
	    	case 1: map.setMapProvider( new Microsoft.HybridProvider() ); break;
	    	case 2: map.setMapProvider( new Microsoft.AerialProvider() ); break;
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
	
	public void keyPressed()
	{
	  if (key=='+')
	  {
		  switch(map.getZoom()) {
		  	case zoomInterState:
		  		map.setZoom(zoomState);
		  		break;
		  	case zoomState:
		  		map.setZoom(zoomCity);
		  		break;
		  }
	  }
	  if (key=='-')
	  {
		  switch(map.getZoom()) {
		  	case zoomCity:
		  		map.setZoom(zoomState);
		  		break;
		  	case zoomState:
		  		map.setZoom(zoomInterState);
		  		break;
		  }
		  
	  }
	
	  if (key==' ') {
		  currentProvider=(currentProvider+1)%3;
		  setMapProvider(currentProvider);
	  }		  
	}
	//***********************************
	//--> HERE BEGINS THE MOUSE STUFF <--
	//***********************************
	

	// See TouchListener on how to use this function call
	// In this example TouchListener draws a solid ellipse
	// Ths functions here draws a ring around the solid ellipse

	// NOTE: Mouse pressed, dragged, and released events will also trigger these
//	       using an ID of -1 and an xWidth and yWidth value of 10.

	// Touch position at last frame
	PVector lastTouchPos = new PVector();
	PVector lastTouchPos2 = new PVector();
	int touchID1;
	int touchID2;

	PVector initTouchPos = new PVector();
	PVector initTouchPos2 = new PVector();
	
	@SuppressWarnings("rawtypes")
	Hashtable touchList;

	// see if we're over any buttons, and respond accordingly:
	public void mouseClicked() {
	  /*if (in.mouseOver()) {
	    map.zoomIn();
	  }
	  else if (out.mouseOver()) {
	    map.zoomOut();
	  }
	  else if (up.mouseOver()) {
	    map.panUp();
	  }
	  else if (down.mouseOver()) {
	    map.panDown();
	  }
	  else if (left.mouseOver()) {
	    map.panLeft();
	  }
	  else if (right.mouseOver()) {
	    map.panRight();
	  }*/
	}
	
	@SuppressWarnings("unchecked")
	public void touchDown(int ID, float xPos, float yPos, float xWidth, float yWidth){
	  noFill();
	  stroke(255,0,0);
	  ellipse( xPos, yPos, xWidth * 2, yWidth * 2 );
	  
	  // Update the last touch position
	  lastTouchPos.x = xPos;
	  lastTouchPos.y = yPos;
	  
	  // Add a new touch ID to the list
	  Touch t = new Touch( ID, xPos, yPos, xWidth, yWidth );
	  touchList.put(ID,t);
	  
	  if( touchList.size() == 1 ){ // If one touch record initial position (for dragging). Saving ID 1 for later
	    touchID1 = ID;
	    initTouchPos.x = xPos;
	    initTouchPos.y = yPos;
	  }
	  else if( touchList.size() == 2 ){ // If second touch record initial position (for zooming). Saving ID 2 for later
	    touchID2 = ID;
	    initTouchPos2.x = xPos;
	    initTouchPos2.y = yPos;
	  }
	}// touchDown

	public void touchMove(int ID, float xPos, float yPos, float xWidth, float yWidth){
	  noFill();
	  stroke(0,255,0);
	  ellipse( xPos, yPos, xWidth * 2, yWidth * 2 );
	  
	  if( touchList.size() < 2 ){
	    // Only one touch, drag map based on last position
	    map.tx += (xPos - lastTouchPos.x)/map.sc;
	    map.ty += (yPos - lastTouchPos.y)/map.sc;
	  } else if( touchList.size() == 2 ){
	    // Only two touch, scale map based on midpoint and distance from initial touch positions
	    
	    float sc = dist(lastTouchPos.x, lastTouchPos.y, lastTouchPos2.x, lastTouchPos2.y);
	    float initPos = dist(initTouchPos.x, initTouchPos.y, initTouchPos2.x, initTouchPos2.y);
	    
	    PVector midpoint = new PVector( (lastTouchPos.x+lastTouchPos2.x)/2, (lastTouchPos.y+lastTouchPos2.y)/2 );
	    sc -= initPos;
	    sc /= 5000;
	    sc += 1;
	    //println(sc);
	    float mx = (midpoint.x - mapOffset.x) - mapSize.x/2;
	    float my = (midpoint.y - mapOffset.y) - mapSize.y/2;
	    map.tx -= mx/map.sc;
	    map.ty -= my/map.sc;
	    map.sc *= sc;
	    map.tx += mx/map.sc;
	    map.ty += my/map.sc;
	  } else if( touchList.size() >= 5 ){
	    
	    // Zoom to entire USA
	    map.setCenterZoom(locationUSA, 6);  
	  }
	  
	  // Update touch IDs 1 and 2
	  if( ID == touchID1 ){
	    lastTouchPos.x = xPos;
	    lastTouchPos.y = yPos;
	  } else if( ID == touchID2 ){
	    lastTouchPos2.x = xPos;
	    lastTouchPos2.y = yPos;
	  } 
	  
	  // Update touch list
	  Touch t = new Touch( ID, xPos, yPos, xWidth, yWidth );
	  touchList.put(ID,t);
	}// touchMove

	void touchUp(int ID, float xPos, float yPos, float xWidth, float yWidth){
	  noFill();
	  stroke(0,0,255);
	  ellipse( xPos, yPos, xWidth * 2, yWidth * 2 );
	  
	  // Remove touch and ID from list
	  touchList.remove(ID);
	}// touchUp

}

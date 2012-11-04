package main;

import java.util.ArrayList;
import java.util.Hashtable;

import omicronAPI.OmicronAPI;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import types.DataQuad;

import com.modestmaps.InteractiveMap;
import com.modestmaps.core.Point2f;
import com.modestmaps.geo.Location;
import com.modestmaps.providers.AbstractMapProvider;
import com.modestmaps.providers.Microsoft;

import db.QueryManager;

@SuppressWarnings("serial")
public class Program extends PApplet {
    static public void main(String args[]) {
    	System.out.println("premain");
	    PApplet.main(new String[] { "main.Program" });
	    System.out.println("postmain");
    }
   	
	OmicronAPI omicronManager;
	TouchListener touchListener;
    
	InteractiveMap map;

	ArrayList<Marker> markerList; 
	
	ArrayList<BasicControl> controls;
	
	QueryManager queryManager;
	ArrayList<DataQuad> results;
	int year=2005;
	GridManager gm;
	
	Button buttonPlus, buttonMinus;
	Button buttonIncYear, buttonDecYear;
	
	
	/**
	 * temp addition
	 */
	Keyboard keyboard;
	SuggestionBox sb;
	Histograph h1, h2;
	
	
	
	
	
	/**
	 * added by: giric 
	 * trial markers
	 *
	Marker marker1, marker2;
	PopUp popUp;
	float markerX, markerY;
	int markerColor;
	public boolean checkPopUp = false;

	**
	 * uptil here
	 */
	
	
	public void initApp() {
		markerList = new ArrayList<Marker>();
		queryManager = new QueryManager(this);
		Utilities.markerShape=loadShape("marker.svg");
		long timer= System.currentTimeMillis();
		results=queryManager.getCrashes(2, 200, -200, 0);
		System.out.println(System.currentTimeMillis()-timer);
		timer=System.currentTimeMillis();
		Utilities.font=this.loadFont("Helvetica-Bold-100.vlw");
		gm = new GridManager(this,map,results);
		gm.computeGridValues();
		System.out.println(System.currentTimeMillis()-timer);
		timer=System.currentTimeMillis();
	}	
	public void initControls() {
		controls=new ArrayList<BasicControl>();
		
		MedallionSelector ms = new MedallionSelector(this, "Penis", new String[] {"A","B", "C","DDD"},Positions.medallionX, Positions.medallionY, Positions.medallionSide);
		controls.add(ms);
		
		//Keyboard keyboard = new Keyboard(this, Positions.keyboardX, Positions.keyboardY, Positions.keyboardWidth, Positions.keyboardHeight);
		keyboard = new Keyboard(this, Positions.keyboardX, Positions.keyboardY, Positions.keyboardWidth, Positions.keyboardHeight);
		controls.add(keyboard);
		
		//SuggestionBox sb = new SuggestionBox(this, Positions.suggestionBoxX, Positions.suggestionBoxY, Positions.suggestionBoxWidth, Positions.suggestionBoxHeight);
		sb = new SuggestionBox(this, Positions.textBoxX, Positions.textBoxY, Positions.textBoxWidth, Positions.textBoxHeight);
		controls.add(sb);
		
		h1 = new Histograph(this, Positions.histograph1X, Positions.histograph1Y, Positions.histographWidth, Positions.histographHeight);
		controls.add(h1);
		
		h2 = new Histograph(this, Positions.histograph2X, Positions.histograph2Y, Positions.histographWidth, Positions.histographHeight);
		controls.add(h2);
	}
	
	public void setup() {		
		//SET SIZE
		size((int)Utilities.width,(int)Utilities.height, JAVA2D);
		if (Utilities.isWall) {
			initOmicron();
		}
		
		//CALLING NOSMOOTH IS IMPORTANT FOR THE MAP WIT P3D
		noSmooth();
		//CREATE MAP
		initMap();
		//CREATE SUPPORT VARS
		initApp();
		//CONTROLS
		initControls();
		
		//BUTTON TEST
		buttonPlus = new Button(this, Positions.buttonPlusX,Positions.buttonPlusY,Positions.buttonPlusWidth,Positions.buttonPlusHeight);
		//buttonPlus = new Button(this, Utilities.width/2,Utilities.height-40,100,40);
		buttonPlus.setName("+");
		buttonMinus  = new Button(this, Positions.buttonMinusX,Positions.buttonMinusY,Positions.buttonMinusWidth,Positions.buttonMinusHeight);
		//buttonMinus  = new Button(this, Utilities.width/2,Utilities.height-80,100,40);
		buttonMinus.setName("-");
		
		buttonDecYear = new Button(this, Positions.buttonDecX, Positions.buttonDecY, Positions.buttonDecWidth, Positions.buttonDecHeight);
		buttonDecYear.setName("<");
		buttonIncYear = new Button(this, Positions.buttonIncX,Positions.buttonIncY, Positions.buttonIncWidth,Positions.buttonIncHeight);
		buttonIncYear.setName(">");
		

		//MARKER TESTING
		//markerList.add(new Marker(this,(locationChicago),this.color(0x80454580)));
		
	}

	public void draw() {
		noStroke();
		//DRAW MAP + CONTOUR
		map.draw();		
		this.fill(Colors.windowBackground);
		this.rectMode(PConstants.CORNERS);
		this.rect(0, 0, width, Utilities.mapOffset.y);
		this.rect(0, 0, Utilities.mapOffset.x, height);
		this.rect(0, Utilities.mapOffset.y+Utilities.mapSize.y, width, height);
		this.rect(Utilities.mapOffset.x+Utilities.mapSize.x, 0, width, height);
		
		//UPDATE MARKERS POSITIONS AND DRAW+
		for (Marker m: markerList) {	
			Point2f p = map.locationPoint(m.location);
			if (isIn(p.x, p.y, Utilities.mapOffset.x, Utilities.mapOffset.y, Utilities.mapSize.x, Utilities.mapSize.y)) {
				m.x=p.x;
				m.y=p.y;
				m.draw();
			}
		}
		
		//DRAW CONTROLS
		for (BasicControl bc: controls) {
			bc.draw();
		}
		
		
		//PROCESS OMICRON
    	if (Utilities.isWall) {
    		omicronManager.process();
    	}
    	//drawNewMexico();
    	gm.drawGrid();
    	gm.drawCircles(year);
    	
    	textFont(Utilities.font, 30);
    	fill(Colors.white);
    	text(year, Utilities.width*0.7f, Utilities.height*0.1f);
    	buttonPlus.draw();
    	buttonMinus.draw();
    	buttonDecYear.draw();
    	buttonIncYear.draw();

    }
	
	
	
	//INITIAL CONFIGURATION OF THE MAP
	int currentProviderIndex;
	AbstractMapProvider[] providers;
	
	Location locationUSA = new Location(38.962f,  -94.928f);
	
	//INIT MAP SIZE AND POSITION
	public void initMap() {
		Utilities.mapSize = new PVector( width/2, height );
		Utilities.mapOffset = new PVector(0,0);
		
		providers = new AbstractMapProvider[3];
		providers[0] = new Microsoft.AerialProvider();
		providers[1] = new Microsoft.HybridProvider();
		providers[2] = new Microsoft.RoadProvider();
		/*providers[3] = new Yahoo.AerialProvider();
		providers[4] = new Yahoo.RoadProvider();
		providers[5] = new OpenStreetMapProvider();*/
		currentProviderIndex=0;
		
		map =  new InteractiveMap(this, providers[currentProviderIndex], Utilities.mapOffset.x, Utilities.mapOffset.y, Utilities.mapSize.x, Utilities.mapSize.y );
		map.panTo(locationUSA);
		map.setZoom(zoomInterState);
		oldCenter = map.getCenter();
	}
	
	//******************************************
	//--> HERE BEGINS THE ZOOMING/TIME MANAGEMENT <--
	//******************************************
	
	// zoom 0 is the whole world, 19 is street level
	final int zoomInterState = 4;
	final int zoomState = 7;
	final int zoomCity = 11;
	final int maxZoom = 19;
	final int minZoom = 1;
	
	final int maxYear = 2010;
	final int minYear = 2001;
	
	
	public void zoomIn () {
		if (map.getZoom()<maxZoom) {
			map.zoomIn();
			gm.computeGridValues();
		}
	}

	public void zoomOut () {
		if (map.getZoom()>minZoom) {
			map.zoomOut();
			gm.computeGridValues();
		}
	}
	
	public void nextYear() {
		if (year<maxYear) {
			year++;
		}
	}
	
	public void prevYear() {
		if (year>minYear) {
			year--;
		}
	}
	
	public void switchProvider() {
		currentProviderIndex=(currentProviderIndex+1)%providers.length;
		map.setMapProvider(providers[currentProviderIndex]);
	}
	
	public void keyPressed()
	{
	  switch(key) {
	  	case '+':
	  		zoomIn();
	  		break;
	  		
	  	case '-':
	  		zoomOut();
	  		break;
	  		
	  	case ' ':
	  		switchProvider();
	  		break;
	  }
	  
	  switch (keyCode) {
	  	case RIGHT:
	  		nextYear();
	  		break;
	  	
	  	case LEFT:
	  		prevYear();
	  		break;
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
	
	public boolean isIn(float mx, float my, float bx, float by, float bw, float bh) {
		return (bx <= mx && mx <= bx+bw && by <= my && my <= by+bh); }
	
	public boolean isIn(float mx, float my, float bx, float by, float bw, float bh, float tolerance) {
		return (bx*(1-tolerance) <= mx && mx <= bx+bw*(1+tolerance) && by*(1-tolerance) <= my && my <= by+bh*(1+tolerance)); }
	
	PVector lastTouchPos = new PVector();
	PVector lastTouchPos2 = new PVector();
	int touchID1;
	int touchID2;

	PVector initTouchPos = new PVector();
	PVector initTouchPos2 = new PVector();
	
	@SuppressWarnings("rawtypes")
	Hashtable touchList;

	public boolean mapHasMoved=false;
	public int mapDragHack = 1;
	public Location oldCenter;
	
	public void mouseDragged() {
		if (isIn(mouseX, mouseY, Utilities.mapOffset.x, Utilities.mapOffset.y, Utilities.mapSize.x, Utilities.mapSize.y)){
			mapDragHack++;
			if (mapDragHack%10==0) {
				mapDragHack=1;
				
				map.setCenter(map.pointLocation(map.locationPoint(map.getCenter()).x - (mouseX - lastTouchPos.x), map.locationPoint(map.getCenter()).y - (mouseY - lastTouchPos.y))); 
				
				/*Location newCenter;
				if (oldCenter!=null)
					newCenter = map.pointLocation(map.locationPoint(oldCenter).x-(mouseX - lastTouchPos.x),map.locationPoint(oldCenter).y-(mouseY - lastTouchPos.y));
				else
					newCenter = map.pointLocation(map.getCenter())
				map.setCenter(newCenter);
				oldCenter=newCenter;*/
				lastTouchPos.x=mouseX;
				lastTouchPos.y=mouseY;
				System.out.println("LSX: "+lastTouchPos.x+" LSY: "+lastTouchPos.y);
				mapHasMoved=true;
			}   
		}
	}
	
	
	// see if we're over any buttons, and respond accordingly:
	public void mousePressed() {

	//CLICK ON THE MAP: if we are clicking on the map, check if we are clicking on a marker.
	//IF so, toggle its opening.
	  if (isIn(mouseX, mouseY, Utilities.mapOffset.x, Utilities.mapOffset.y, Utilities.mapSize.x, Utilities.mapSize.y)){
		  
		  lastTouchPos.x = mouseX;
		  lastTouchPos.y = mouseY;
		  System.out.println("LSX: "+lastTouchPos.x+" LSY: "+lastTouchPos.y);
		  
		  /*for (Marker m: markerList) {
			  if (isIn(mouseX, mouseY, m.x-Utilities.markerWidth/2, m.y-Utilities.markerHeight, Utilities.markerWidth, Utilities.markerHeight, 0.05f)) {
				  m.isOpen=!m.isOpen;
			  }
		  }*/
		  //markerList.add(new Marker(this, map.pointLocation(mouseX, mouseY)));
	  }		 
	  
	  if (isIn(mouseX,mouseY,Positions.keyboardX, Positions.keyboardY, Positions.keyboardWidth, Positions.keyboardHeight)) {
		  sb.updateTextBox(keyboard.Click(mouseX, mouseY));
	  }
	  
	  if (isIn(mouseX, mouseY, Positions.suggestionBoxX, Positions.suggestionBoxY, Positions.suggestionBoxWidth, Positions.suggestionBoxHeight)) {
		  sb.Click(mouseX, mouseY);
	  }
	  
	  if(buttonPlus.isInRectangle(mouseX, mouseY)){
		  buttonPlus.setSelected(!buttonPlus.isSelected());
		  zoomIn();
	  }
	  if(buttonMinus.isInRectangle(mouseX, mouseY)){
		  buttonMinus.setSelected(!buttonMinus.isSelected());
		  zoomOut();
	  }
	  if (buttonDecYear.isInRectangle(mouseX, mouseY)){
		  buttonDecYear.setSelected(!buttonDecYear.isSelected());
		  if(year>2001) {year--;}
	  }
	  if  (buttonIncYear.isInRectangle(mouseX, mouseY)){
		  buttonIncYear.setSelected(!buttonIncYear.isSelected());
		  if(year<2010) {year++;}
	  }
	}
	
	public void mouseReleased() {
		//MAP CLICK:
		if (isIn(mouseX,mouseY,Utilities.mapOffset.x, Utilities.mapOffset.y, Utilities.mapSize.x, Utilities.mapSize.y)) {			
			
		}
		
		if (mapHasMoved) {
			gm.computeGridValues();
			mapHasMoved=false;
			System.out.println("Updated Grid!");
		}
	}
	
	@SuppressWarnings("unchecked")
	public void touchDown(int ID, float xPos, float yPos, float xWidth, float yWidth){
	  noFill();
	  stroke(255,0,0);
	  ellipse( xPos, yPos, xWidth * 2, yWidth * 2 );
	  
	  // Update the last touch position
	  //lastTouchPos.x = xPos;
	  //lastTouchPos.y = yPos;
	  
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

	@SuppressWarnings("unchecked")
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
	    float mx = (midpoint.x - Utilities.mapOffset.x) - Utilities.mapSize.x/2;
	    float my = (midpoint.y - Utilities.mapOffset.y) - Utilities.mapSize.y/2;
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
	
	/*public void drawCookCounty() {
		noStroke();
		fill(0,0,255,150);
		beginShape();
		vertex(map.locationPoint(new Location(42.154099f,-88.238197f)).x, map.locationPoint(new Location(42.154099f,-88.238197f)).y);
		vertex(map.locationPoint(new Location(42.154202f, -88.199303f)).x, map.locationPoint(new Location(42.154202f, -88.199303f)).y);
		vertex(map.locationPoint(new Location(42.153355f, -87.760063f)).x, map.locationPoint(new Location(42.153355f, -87.760063f)).y);
		vertex(map.locationPoint(new Location(42.128601f, -87.741997f)).x, map.locationPoint(new Location(42.128601f, -87.741997f)).y);
		vertex(map.locationPoint(new Location(42.107700f, -87.724602f)).x, map.locationPoint(new Location(42.107700f, -87.724602f)).y);
		vertex(map.locationPoint(new Location(42.075699f, -87.682304f)).x, map.locationPoint(new Location(42.075699f, -87.682304f)).y);
		vertex(map.locationPoint(new Location(42.067001f, -87.676506f)).x, map.locationPoint(new Location(42.067001f, -87.676506f)).y);
		vertex(map.locationPoint(new Location(42.034302f, -87.669800f)).x, map.locationPoint(new Location(42.034302f, -87.669800f)).y);
		vertex(map.locationPoint(new Location(42.006302f, -87.657303f)).x, map.locationPoint(new Location(42.006302f, -87.657303f)).y);
		vertex(map.locationPoint(new Location(41.918598f, -87.628197f)).x, map.locationPoint(new Location(41.918598f, -87.628197f)).y);
		vertex(map.locationPoint(new Location(41.893612f, -87.613976f)).x, map.locationPoint(new Location(41.893612f, -87.613976f)).y);
		vertex(map.locationPoint(new Location(41.868900f, -87.616203f)).x, map.locationPoint(new Location(41.868900f, -87.616203f)).y);
		vertex(map.locationPoint(new Location(41.845200f, -87.609398f)).x, map.locationPoint(new Location(41.845200f, -87.609398f)).y);
		vertex(map.locationPoint(new Location(41.824402f, -87.598503f)).x, map.locationPoint(new Location(41.824402f, -87.598503f)).y);
		vertex(map.locationPoint(new Location(41.799599f, -87.580101f)).x, map.locationPoint(new Location(41.799599f, -87.580101f)).y);
		vertex(map.locationPoint(new Location(41.785999f, -87.576302f)).x, map.locationPoint(new Location(41.785999f, -87.576302f)).y);
		vertex(map.locationPoint(new Location(41.765999f, -87.560600f)).x, map.locationPoint(new Location(41.765999f, -87.560600f)).y);
		vertex(map.locationPoint(new Location(41.748199f, -87.530701f)).x, map.locationPoint(new Location(41.748199f, -87.530701f)).y);
		vertex(map.locationPoint(new Location(41.715168f, -87.528160f)).x, map.locationPoint(new Location(41.715168f, -87.528160f)).y);
		vertex(map.locationPoint(new Location(41.708302f, -87.524002f)).x, map.locationPoint(new Location(41.708302f, -87.524002f)).y);
		vertex(map.locationPoint(new Location(41.708279f, -87.523972f)).x, map.locationPoint(new Location(41.708279f, -87.523972f)).y);
		vertex(map.locationPoint(new Location(41.470081f, -87.525635f)).x, map.locationPoint(new Location(41.470081f, -87.525635f)).y);
		vertex(map.locationPoint(new Location(41.469898f, -87.790398f)).x, map.locationPoint(new Location(41.469898f, -87.790398f)).y);
		vertex(map.locationPoint(new Location(41.539799f, -87.790199f)).x, map.locationPoint(new Location(41.539799f, -87.790199f)).y);
		vertex(map.locationPoint(new Location(41.538300f, -87.792198f)).x, map.locationPoint(new Location(41.538300f, -87.792198f)).y);
		vertex(map.locationPoint(new Location(41.558601f, -87.792702f)).x, map.locationPoint(new Location(41.558601f, -87.792702f)).y);
		vertex(map.locationPoint(new Location(41.556702f, -87.909401f)).x, map.locationPoint(new Location(41.556702f, -87.909401f)).y);
		vertex(map.locationPoint(new Location(41.643902f, -87.912003f)).x, map.locationPoint(new Location(41.643902f, -87.912003f)).y);
		vertex(map.locationPoint(new Location(41.641499f, -88.027603f)).x, map.locationPoint(new Location(41.641499f, -88.027603f)).y);
		vertex(map.locationPoint(new Location(41.685501f, -88.029106f)).x, map.locationPoint(new Location(41.685501f, -88.029106f)).y);
		vertex(map.locationPoint(new Location(41.686798f, -87.966705f)).x, map.locationPoint(new Location(41.686798f, -87.966705f)).y);
		vertex(map.locationPoint(new Location(41.693600f, -87.955505f)).x, map.locationPoint(new Location(41.693600f, -87.955505f)).y);
		vertex(map.locationPoint(new Location(41.694801f, -87.948997f)).x, map.locationPoint(new Location(41.694801f, -87.948997f)).y);
		vertex(map.locationPoint(new Location(41.699200f, -87.942398f)).x, map.locationPoint(new Location(41.699200f, -87.942398f)).y);
		vertex(map.locationPoint(new Location(41.702999f, -87.942200f)).x, map.locationPoint(new Location(41.702999f, -87.942200f)).y);
		vertex(map.locationPoint(new Location(41.702301f, -87.940102f)).x, map.locationPoint(new Location(41.702301f, -87.940102f)).y);
		vertex(map.locationPoint(new Location(41.714802f, -87.914200f)).x, map.locationPoint(new Location(41.714802f, -87.914200f)).y);
		vertex(map.locationPoint(new Location(41.873001f, -87.920403f)).x, map.locationPoint(new Location(41.873001f, -87.920403f)).y);
		vertex(map.locationPoint(new Location(41.993999f, -87.920601f)).x, map.locationPoint(new Location(41.993999f, -87.920601f)).y);
		vertex(map.locationPoint(new Location(41.986198f, -88.262802f)).x, map.locationPoint(new Location(41.986198f, -88.262802f)).y);
		vertex(map.locationPoint(new Location(42.066601f, -88.263306f)).x, map.locationPoint(new Location(42.066601f, -88.263306f)).y);
		vertex(map.locationPoint(new Location(42.066898f, -88.237907f)).x, map.locationPoint(new Location(42.066898f, -88.237907f)).y);
		endShape();

	

	}
	
	/*public void drawNewMexico() {
		noStroke();
		float color = 0;
		float min = Integer.MAX_VALUE;
		float max = Integer.MIN_VALUE;
		for (DataTriple t : results) {
			if (t.getState().equals("35")) {
				int count = t.getCount();
				if (t.getYear()==year)
					color = count;
				
				if (count>max) max=count;
				if (count<min) min=count;
			}		
		}
		color = map(color, min, max, 0, 255);
		fill(color,0,0,100);
		beginShape();
	
		vertex(map.locationPoint(new Location(32.441967f,-109.048882f)).x,map.locationPoint(new Location(32.441967f,-109.048882f)).y);
		vertex(map.locationPoint(new Location(32.779480f,-109.050728f)).x,map.locationPoint(new Location(32.779480f,-109.050728f)).y);
		vertex(map.locationPoint(new Location(33.205101f,-109.049904f)).x,map.locationPoint(new Location(33.205101f,-109.049904f)).y);
		vertex(map.locationPoint(new Location(33.783249f,-109.049721f)).x,map.locationPoint(new Location(33.783249f,-109.049721f)).y);
		vertex(map.locationPoint(new Location(34.591740f,-109.048012f)).x,map.locationPoint(new Location(34.591740f,-109.048012f)).y);
		vertex(map.locationPoint(new Location(34.954613f,-109.045998f)).x,map.locationPoint(new Location(34.954613f,-109.045998f)).y);
		vertex(map.locationPoint(new Location(35.996655f,-109.047195f)).x,map.locationPoint(new Location(35.996655f,-109.047195f)).y);
		vertex(map.locationPoint(new Location(36.996643f,-109.047821f)).x,map.locationPoint(new Location(36.996643f,-109.047821f)).y);
		vertex(map.locationPoint(new Location(36.999474f,-108.371834f)).x,map.locationPoint(new Location(36.999474f,-108.371834f)).y);
		vertex(map.locationPoint(new Location(36.998772f,-107.471855f)).x,map.locationPoint(new Location(36.998772f,-107.471855f)).y);
		vertex(map.locationPoint(new Location(36.997520f,-107.410217f)).x,map.locationPoint(new Location(36.997520f,-107.410217f)).y);
		vertex(map.locationPoint(new Location(36.999073f,-106.889778f)).x,map.locationPoint(new Location(36.999073f,-106.889778f)).y);
		vertex(map.locationPoint(new Location(36.989491f,-106.860657f)).x,map.locationPoint(new Location(36.989491f,-106.860657f)).y);
		vertex(map.locationPoint(new Location(36.991493f,-106.471588f)).x,map.locationPoint(new Location(36.991493f,-106.471588f)).y);
		vertex(map.locationPoint(new Location(36.992275f,-105.991425f)).x,map.locationPoint(new Location(36.992275f,-105.991425f)).y);
		vertex(map.locationPoint(new Location(36.994541f,-105.712891f)).x,map.locationPoint(new Location(36.994541f,-105.712891f)).y);
		vertex(map.locationPoint(new Location(36.992580f,-105.212532f)).x,map.locationPoint(new Location(36.992580f,-105.212532f)).y);
		vertex(map.locationPoint(new Location(36.993183f,-105.145615f)).x,map.locationPoint(new Location(36.993183f,-105.145615f)).y);
		vertex(map.locationPoint(new Location(36.994446f,-103.993111f)).x,map.locationPoint(new Location(36.994446f,-103.993111f)).y);
		vertex(map.locationPoint(new Location(36.999741f,-103.077377f)).x,map.locationPoint(new Location(36.999741f,-103.077377f)).y);
		vertex(map.locationPoint(new Location(36.998505f,-102.997223f)).x,map.locationPoint(new Location(36.998505f,-102.997223f)).y);
		vertex(map.locationPoint(new Location(36.492344f,-102.996918f)).x,map.locationPoint(new Location(36.492344f,-102.996918f)).y);
		vertex(map.locationPoint(new Location(36.491566f,-103.026802f)).x,map.locationPoint(new Location(36.491566f,-103.026802f)).y);
		vertex(map.locationPoint(new Location(36.056026f,-103.023560f)).x,map.locationPoint(new Location(36.056026f,-103.023560f)).y);
		vertex(map.locationPoint(new Location(35.742287f,-103.022118f)).x,map.locationPoint(new Location(35.742287f,-103.022118f)).y);
		vertex(map.locationPoint(new Location(35.623604f,-103.021797f)).x,map.locationPoint(new Location(35.623604f,-103.021797f)).y);
		vertex(map.locationPoint(new Location(35.177208f,-103.025650f)).x,map.locationPoint(new Location(35.177208f,-103.025650f)).y);
		vertex(map.locationPoint(new Location(34.964718f,-103.024750f)).x,map.locationPoint(new Location(34.964718f,-103.024750f)).y);
		vertex(map.locationPoint(new Location(34.745266f,-103.022156f)).x,map.locationPoint(new Location(34.745266f,-103.022156f)).y);
		vertex(map.locationPoint(new Location(34.307743f,-103.029144f)).x,map.locationPoint(new Location(34.307743f,-103.029144f)).y);
		vertex(map.locationPoint(new Location(33.826088f,-103.032761f)).x,map.locationPoint(new Location(33.826088f,-103.032761f)).y);
		vertex(map.locationPoint(new Location(33.565742f,-103.038239f)).x,map.locationPoint(new Location(33.565742f,-103.038239f)).y);
		vertex(map.locationPoint(new Location(33.377728f,-103.042603f)).x,map.locationPoint(new Location(33.377728f,-103.042603f)).y);
		vertex(map.locationPoint(new Location(32.953533f,-103.048836f)).x,map.locationPoint(new Location(32.953533f,-103.048836f)).y);
		vertex(map.locationPoint(new Location(32.515430f,-103.059547f)).x,map.locationPoint(new Location(32.515430f,-103.059547f)).y);
		vertex(map.locationPoint(new Location(32.084995f,-103.055191f)).x,map.locationPoint(new Location(32.084995f,-103.055191f)).y);
		vertex(map.locationPoint(new Location(32.001900f,-103.057968f)).x,map.locationPoint(new Location(32.001900f,-103.057968f)).y);
		vertex(map.locationPoint(new Location(32.004154f,-103.332092f)).x,map.locationPoint(new Location(32.004154f,-103.332092f)).y);
		vertex(map.locationPoint(new Location(32.006104f,-103.728973f)).x,map.locationPoint(new Location(32.006104f,-103.728973f)).y);
		vertex(map.locationPoint(new Location(32.005890f,-103.980896f)).x,map.locationPoint(new Location(32.005890f,-103.980896f)).y);
		vertex(map.locationPoint(new Location(32.007278f,-104.018814f)).x,map.locationPoint(new Location(32.007278f,-104.018814f)).y);
		vertex(map.locationPoint(new Location(32.003151f,-104.850563f)).x,map.locationPoint(new Location(32.003151f,-104.850563f)).y);
		vertex(map.locationPoint(new Location(32.004269f,-104.921799f)).x,map.locationPoint(new Location(32.004269f,-104.921799f)).y);
		vertex(map.locationPoint(new Location(32.001553f,-106.002708f)).x,map.locationPoint(new Location(32.001553f,-106.002708f)).y);
		vertex(map.locationPoint(new Location(32.000645f,-106.377846f)).x,map.locationPoint(new Location(32.000645f,-106.377846f)).y);
		vertex(map.locationPoint(new Location(32.000988f,-106.623077f)).x,map.locationPoint(new Location(32.000988f,-106.623077f)).y);
		vertex(map.locationPoint(new Location(31.980228f,-106.649513f)).x,map.locationPoint(new Location(31.980228f,-106.649513f)).y);
		vertex(map.locationPoint(new Location(31.972118f,-106.632057f)).x,map.locationPoint(new Location(31.972118f,-106.632057f)).y);
		vertex(map.locationPoint(new Location(31.913998f,-106.633202f)).x,map.locationPoint(new Location(31.913998f,-106.633202f)).y);
		vertex(map.locationPoint(new Location(31.895102f,-106.643532f)).x,map.locationPoint(new Location(31.895102f,-106.643532f)).y);
		vertex(map.locationPoint(new Location(31.844635f,-106.615578f)).x,map.locationPoint(new Location(31.844635f,-106.615578f)).y);
		vertex(map.locationPoint(new Location(31.817728f,-106.614441f)).x,map.locationPoint(new Location(31.817728f,-106.614441f)).y);
		vertex(map.locationPoint(new Location(31.786198f,-106.538971f)).x,map.locationPoint(new Location(31.786198f,-106.538971f)).y);
		vertex(map.locationPoint(new Location(31.784981f,-107.282997f)).x,map.locationPoint(new Location(31.784981f,-107.282997f)).y);
		vertex(map.locationPoint(new Location(31.786804f,-108.202660f)).x,map.locationPoint(new Location(31.786804f,-108.202660f)).y);
		vertex(map.locationPoint(new Location(31.343742f,-108.210060f)).x,map.locationPoint(new Location(31.343742f,-108.210060f)).y);
		vertex(map.locationPoint(new Location(31.343348f,-109.045006f)).x,map.locationPoint(new Location(31.343348f,-109.045006f)).y);
		vertex(map.locationPoint(new Location(32.441967f,-109.048882f)).x,map.locationPoint(new Location(32.441967f,-109.048882f)).y);
		endShape();
	}
	*/
	

}

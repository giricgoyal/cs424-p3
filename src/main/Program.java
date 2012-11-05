package main;

import java.util.ArrayList;
import java.util.Hashtable;

import main.Piechart.KeyValue;

import omicronAPI.OmicronAPI;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import types.DataCrashInstance;
import types.DataQuad;

import com.modestmaps.InteractiveMap;
import com.modestmaps.core.Point2f;
import com.modestmaps.geo.Location;
import com.modestmaps.providers.AbstractMapProvider;
import com.modestmaps.providers.Microsoft;
//import com.sun.medialib.mlib.mediaLibException;

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
	public InteractiveMap map;

	ArrayList<Marker> markerList;

	ArrayList<BasicControl> controls;

	QueryManager queryManager;
	ArrayList<DataCrashInstance> results;
	GridManager gm;

	

	/**
	 * temp addition
	 */
	Keyboard keyboard;
	SuggestionBox sb;
	Histograph h1, h2;
	DropUpMenu dropUpMenu, dropUpMenu2;
	MedallionSelector ms;
	Button buttonPlus, buttonMinus, updateQueryButton;
	Button buttonIncYear, buttonDecYear;
	Button buttonProvider;
	
	Timeline timeline;
	
	/**
	 * added by: giric trial markers
	 * 
	 * Marker marker1, marker2; PopUp popUp; float markerX, markerY; int
	 * markerColor; public boolean checkPopUp = false;
	 * 
	 ** 
	 * uptil here
	 */

	public void initApp() {
		Utilities.program=this;
		touchList = new Hashtable<Integer, Touch>();

		// DB
		queryManager = new QueryManager(this);

		long timer = System.currentTimeMillis();
		results = queryManager.getCrashesALL();
		System.out.println(System.currentTimeMillis() - timer);
		timer = System.currentTimeMillis();

		// MARKERS
		Utilities.markerShape = loadShape("marker.svg");
		markerList = updateMarkerList();
		
		
		// GRID
		gm = new GridManager(this, map, results);
		System.out.println("InitApp1");
		gm.computeGridValues();
		System.out.println(System.currentTimeMillis() - timer);
		
		// OTHER
		Utilities.font = this.loadFont("Helvetica-Bold-100.vlw");
	}

	public void initHistogram() {
		/*h1.setData(queryManager.getHisogramCrashes(Utilities.minActiveLatitude,
				Utilities.maxActiveLatitude, Utilities.minActiveLongitude,
				Utilities.maxActiveLongitude));
		h2.setData(queryManager.getHisogramFatalities(
				Utilities.minActiveLatitude, Utilities.maxActiveLatitude,
				Utilities.minActiveLongitude, Utilities.maxActiveLongitude));
		 */
		//h1.setBounds();
		//h2.setBounds();
		h1.setString(Utilities.hist1String);
		h2.setString(Utilities.hist2String);
		h1.setData(results);
		h2.setData(results);

		

	}

	public void initControls() {
		controls = new ArrayList<BasicControl>();

		ms = new MedallionSelector(this, Utilities.defaultFocusAttribute,
				new String[] { "A", "B", "C", "DDD" }, Positions.medallionX,
				Positions.medallionY, Positions.medallionSide);
		controls.add(ms);

		timeline = new Timeline(this, 800, 200, 600, 100, gm);
		timeline.updateData(gm);
		controls.add(timeline);
		
		// Keyboard keyboard = new Keyboard(this, Positions.keyboardX,
		// Positions.keyboardY, Positions.keyboardWidth,
		// Positions.keyboardHeight);
		keyboard = new Keyboard(this, Positions.keyboardX, Positions.keyboardY,
				Positions.keyboardWidth, Positions.keyboardHeight);
		controls.add(keyboard);

		// SuggestionBox sb = new SuggestionBox(this, Positions.suggestionBoxX,
		// Positions.suggestionBoxY, Positions.suggestionBoxWidth,
		// Positions.suggestionBoxHeight);
		sb = new SuggestionBox(this, Positions.textBoxX, Positions.textBoxY,
				Positions.textBoxWidth, Positions.textBoxHeight,this);
		controls.add(sb);

		h1 = new Histograph(this, Positions.histograph1X,
				Positions.histograph1Y, Positions.histographWidth,
				Positions.histographHeight);
		controls.add(h1);

		h2 = new Histograph(this, Positions.histograph2X,
				Positions.histograph2Y, Positions.histographWidth,
				Positions.histographHeight);
		controls.add(h2);

		initHistogram();
		
		
		dropUpMenu2 = new DropUpMenu(this, Positions.buttonKeyX, Positions.buttonKeyY, Positions.buttonKeyWidth, Positions.buttonKeyHeight, ms, false);
		controls.add(dropUpMenu2);

		dropUpMenu = new DropUpMenu(this, Positions.buttonFilterX, Positions.buttonFilterY, Positions.buttonFilterWidth, Positions.buttonFilterHeight, ms, true);
		controls.add(dropUpMenu);

		
		

		// BUTTON TEST
		buttonPlus = new Button(this, Positions.buttonPlusX,
				Positions.buttonPlusY, Positions.buttonPlusWidth,
				Positions.buttonPlusHeight);
		// buttonPlus = new Button(this,
		// Utilities.width/2,Utilities.height-40,100,40);
		buttonPlus.setName("+");
		buttonMinus = new Button(this, Positions.buttonMinusX,
				Positions.buttonMinusY, Positions.buttonMinusWidth,
				Positions.buttonMinusHeight);
		// buttonMinus = new Button(this,
		// Utilities.width/2,Utilities.height-80,100,40);
		buttonMinus.setName("-");

		buttonDecYear = new Button(this, Positions.buttonDecX,
				Positions.buttonDecY, Positions.buttonDecWidth,
				Positions.buttonDecHeight);
		buttonDecYear.setName("<");
		buttonIncYear = new Button(this, Positions.buttonIncX,
				Positions.buttonIncY, Positions.buttonIncWidth,
				Positions.buttonIncHeight);
		buttonIncYear.setName(">");
		
		buttonProvider = new Button (this, Positions.buttonProviderX,Positions.buttonProviderY,Positions.buttonProviderWidth, Positions.buttonProviderHeight);
		buttonProvider.setName("P");
		
		updateQueryButton = new Button(this, Positions.buttonUpdateX,Positions.buttonUpdateY,Positions.buttonUpdateWidth, Positions.buttonUpdateHeight);
		updateQueryButton.setName("Update!");
		updateQueryButton.setElpsFalse();

		controls.add(buttonPlus);
		controls.add(buttonMinus);
		controls.add(buttonIncYear);
		controls.add(buttonDecYear);
		controls.add(updateQueryButton);
		controls.add(buttonProvider);
	}

	public ArrayList <Marker> updateMarkerList() {
		ArrayList <Marker> ret = new ArrayList<Marker>();
		for (DataCrashInstance dci : results) {
			if (dci.getYear()==Utilities.activeYear && Utilities.minActiveLatitude <= dci.getLatitude() && dci.getLatitude() <= Utilities.maxActiveLatitude && Utilities.minActiveLongitude <= dci.getLongitude() && dci.getLongitude() <= Utilities.maxActiveLongitude) {
				//SEARCH FOR THE COLOR
				
				String attValue=null;
				if (Utilities.focusAttribute.equals("Month")) {
					attValue=dci.getMonth();
				} else if (Utilities.focusAttribute.equals("Day of Week")) {
					attValue=dci.getDay_of_week();
				} else if (Utilities.focusAttribute.equals("Age")) {
					attValue=dci.getAge();
				}else if (Utilities.focusAttribute.equals("Light Condition")) {
					attValue=dci.getLight_condition();
				} else if (Utilities.focusAttribute.equals("Alcohol Involved")) {
					attValue=dci.getAlchol_involved();
				} else if (Utilities.focusAttribute.equals("Sex")) {
					attValue=dci.getSex();
				} else if (Utilities.focusAttribute.equals("Hour")) {
					attValue=dci.getHour();
				} else if (Utilities.focusAttribute.equals("Weather")) {
					attValue=dci.getWeather();
				}
				
				int index = FilterValues.attributesHasMap.get(Utilities.focusAttribute);
				int colorOfMarker=0;
				for (int i=0;i<FilterValues.filtersValue[index].length;i++) {
					if (FilterValues.filtersValue[index][i].getToShowVaue().equals(attValue)) {
						colorOfMarker=Utilities.colorCodes[i]; break;
					}
				}
				
				Marker m = new Marker(this, dci.getId(), new Location (dci.getLatitude(), dci.getLongitude()), colorOfMarker);
				
				m.x = map.locationPoint(m.location).x;
				m.y = map.locationPoint(m.location).y;
				ret.add(m);
			}			
		}
		
		return ret;
	}
	
	public void setup() {
		size((int) Utilities.width, (int) Utilities.height, JAVA2D);
		if (Utilities.isWall) {
			initOmicron();
		}

		// CALLING NOSMOOTH IS IMPORTANT FOR THE MAP WIT P3D
		noSmooth();
		System.out.println("Pre App");
		// CREATE MAP
		initMap();
		System.out.println("SETUP DONE1!");
		// CREATE SUPPORT VARS
		initApp();
		System.out.println("SETUP DONE2!");
		// CONTROLS
		initControls();

		System.out.println("SETUP DONE3!");

		// MARKER TESTING
		// markerList.add(new
		// Marker(this,(locationChicago),this.color(0x80454580)));

	}

	public void draw() {
		noStroke();
		// DRAW MAP + CONTOUR
		map.draw();
		this.fill(Colors.windowBackground);
		this.rectMode(PConstants.CORNERS);
		this.rect(0, 0, width, Utilities.mapOffset.y);
		this.rect(0, 0, Utilities.mapOffset.x, height);
		this.rect(0, Utilities.mapOffset.y + Utilities.mapSize.y, width, height);
		this.rect(Utilities.mapOffset.x + Utilities.mapSize.x, 0, width, height);

		if (map.getZoom()>=zoomThreshold) {
			// UPDATE MARKERS POSITIONS AND DRAW+
			for (Marker m : markerList) {
				m.draw();
			}
		}
		else {
			gm.drawGrid();
			gm.drawCircles(Utilities.activeYear);
		}
		
		//BEFORE OF THE CONTROLS, UNIFY THE MEDALLION AND ITS BUTTON
		fill(Colors.filterColor);
		noStroke();
		beginShape();
		vertex(ms.myX + ms.myWidth / 2,  ms.myY + ms.myWidth / 2);
		vertex(Positions.buttonFilterX, Positions.buttonFilterY);
		vertex(Positions.buttonFilterX, Positions.buttonFilterY+Positions.buttonFilterHeight);
		endShape();

		// DRAW CONTROLS
		for (BasicControl bc : controls) {
			bc.draw();
		}
		
		
		// PROCESS OMICRON
		if (Utilities.isWall) {
			omicronManager.process();
		}

		textFont(Utilities.font, 30);
		fill(Colors.white);
		text(Utilities.activeYear, Utilities.width * 0.7f, Utilities.height * 0.1f);
	
	}

	// INITIAL CONFIGURATION OF THE MAP
	int currentProviderIndex;
	AbstractMapProvider[] providers;

	Location locationUSA = new Location(38.962f, -94.928f);

	// INIT MAP SIZE AND POSITION
	public void initMap() {

		providers = new AbstractMapProvider[3];
		providers[0] = new Microsoft.AerialProvider();
		providers[1] = new Microsoft.HybridProvider();
		providers[2] = new Microsoft.RoadProvider();
		/*
		 * providers[3] = new Yahoo.AerialProvider(); providers[4] = new
		 * Yahoo.RoadProvider(); providers[5] = new OpenStreetMapProvider();
		 */
		currentProviderIndex = 0;

		map = new InteractiveMap(this, providers[currentProviderIndex],
				Utilities.mapOffset.x, Utilities.mapOffset.y,
				Utilities.mapSize.x, Utilities.mapSize.y);
		map.panTo(locationUSA);
		map.setZoom(minZoom);

		updateCoordinatesLimits();
	}

	public void updateCoordinatesLimits() {
		Location topLeft = map.pointLocation(Utilities.mapOffset.x,
				Utilities.mapOffset.y);
		Location bottomRight = map.pointLocation(Utilities.mapOffset.x
				+ Utilities.mapSize.x, Utilities.mapOffset.y
				+ Utilities.mapSize.y);
		Utilities.maxActiveLatitude = Math.max(topLeft.lat, bottomRight.lat);
		Utilities.minActiveLatitude = Math.min(topLeft.lat, bottomRight.lat);
		Utilities.maxActiveLongitude = Math.max(topLeft.lon, bottomRight.lon);
		Utilities.minActiveLongitude = Math.min(topLeft.lon, bottomRight.lon);
	}

	// ******************************************
	// --> HERE BEGINS THE ZOOMING/TIME MANAGEMENT <--
	// ******************************************

	// zoom 0 is the whole world, 19 is street level
	public final int zoomCity = 12;
	public final int zoomThreshold = 11;
	public final int maxZoom = 16;
	//final int minZoom = 6;
	public final int minZoom = 3;
	
	final int maxYear = 2010;
	final int minYear = 2001;

	public void zoomIn() {
		if (map.getZoom() < maxZoom) {
			map.zoomIn();
			gm.computeGridValues();timeline.updateData(gm);
			updateCoordinatesLimits();
			System.out.println("Current zoom level: " + map.getZoom());
			if (map.getZoom()>=zoomThreshold) {
				markerList=updateMarkerList();
			}
			h1.setData(results);
			h2.setData(results);
		}
	}

	public void zoomOut() {
		if (map.getZoom() > minZoom) {
			map.zoomOut();
			
			gm.computeGridValues();timeline.updateData(gm);
			updateCoordinatesLimits();
			System.out.println("Current zoom level: " + map.getZoom());
			if (map.getZoom()>=zoomThreshold) {
				markerList=updateMarkerList();
			}
			h2.setData(results);
			h1.setData(results);
		}
	}

	public void nextYear() {
		if (Utilities.activeYear < maxYear) {
			Utilities.activeYear++;
			h2.setData(results);
			if (map.getZoom()>=zoomThreshold) {
				markerList=updateMarkerList();
			}
		}
	}

	public void prevYear() {
		if (Utilities.activeYear > minYear) {
			Utilities.activeYear--;
			h2.setData(results);
			if (map.getZoom()>=zoomThreshold) {
				markerList=updateMarkerList();
			}
		}
	}

	public void switchProvider() {
		currentProviderIndex = (currentProviderIndex + 1) % providers.length;
		map.setMapProvider(providers[currentProviderIndex]);
	}

	public void keyPressed() {
		switch (key) {
		case '+':
			zoomIn();
			//initHistogram();
			break;

		case '-':
			zoomOut();
			//initHistogram();
			break;

		case ' ':
			switchProvider();
			break;
		}

		switch (keyCode) {
		case RIGHT:
			nextYear();
			//initHistogram();
			break;

		case LEFT:
			prevYear();
			//initHistogram();
			break;
		}
	}

	// ***********************************
	// --> HERE BEGINS THE MOUSE STUFF <--
	// ***********************************

	// See TouchListener on how to use this function call
	// In this example TouchListener draws a solid ellipse
	// Ths functions here draws a ring around the solid ellipse

	// NOTE: Mouse pressed, dragged, and released events will also trigger these
	// using an ID of -1 and an xWidth and yWidth value of 10.

	// Touch position at last frame

	public void initOmicron() {
		// Creates the OmicronAPI object. This is placed in init() since we want
		// to use fullscreen
		omicronManager = new OmicronAPI(this);

		// Removes the title bar for full screen mode (present mode will not
		// work on Cyber-commons wall)
		omicronManager.setFullscreen(true);

		// Make the connection to the tracker machine
		omicronManager.ConnectToTracker(7001, 7340, "131.193.77.159");
		// Create a listener to get events
		touchListener = new TouchListener();
		touchListener.setThings(this);
		// Register listener with OmicronAPI
		omicronManager.setTouchListener(touchListener);
	}

	public boolean isIn(float mx, float my, float bx, float by, float bw,
			float bh) {
		return (bx <= mx && mx <= bx + bw && by <= my && my <= by + bh);
	}

	public boolean isIn(float mx, float my, float bx, float by, float bw,
			float bh, float tolerance) {
		return (bx * (1 - tolerance) <= mx && mx <= bx + bw * (1 + tolerance)
				&& by * (1 - tolerance) <= my && my <= by + bh
				* (1 + tolerance));
	}

	int touchID1;
	int touchID2;
	PVector initTouchPos = new PVector();
	PVector initTouchPos2 = new PVector();
	PVector lastTouchPos = new PVector();
	PVector lastTouchPos2 = new PVector();
	int mapDragHack=1;
	
	@SuppressWarnings("rawtypes")
	Hashtable touchList;

	public boolean mapHasMoved = false;

	public void myPressed(int id, float mx, float my) {
		
	//	System.out.println("MY PRESSED");
		
		if (isIn(mx, my, Utilities.mapOffset.x, Utilities.mapOffset.y,
				Utilities.mapSize.x, Utilities.mapSize.y)) {

			lastTouchPos.x = mx;
			lastTouchPos.y = my;
			float epsilon = Utilities.Converter(15);
			for (Marker m: markerList) {
				if (m.x-epsilon<=mx&& mx<=m.x+epsilon && m.y-epsilon <= my && my <= m.y+epsilon) {
					m.isOpen=!m.isOpen;
				}
			}
		}

		if (isIn(mx, my, Positions.keyboardX, Positions.keyboardY,
				Positions.keyboardWidth, Positions.keyboardHeight)) {
			sb.updateTextBox(keyboard.Click(mx, my));
		}

		if (isIn(mx, my, Positions.suggestionBoxX,
				Positions.suggestionBoxY, Positions.suggestionBoxWidth,
				Positions.suggestionBoxHeight)) {
			sb.Click(mx, my);
		}

		if (buttonPlus.isInRectangle(mx, my)) {
			buttonPlus.setSelected(!buttonPlus.isSelected());
			zoomIn();
			//initHistogram();
		}
		if (buttonMinus.isInRectangle(mx, my)) {
			buttonMinus.setSelected(!buttonMinus.isSelected());
			zoomOut();
			//initHistogram();
		}
		if (buttonDecYear.isInRectangle(mx, my)) {
			buttonDecYear.setSelected(!buttonDecYear.isSelected());
			prevYear();
			//initHistogram();
		}
		if (buttonIncYear.isInRectangle(mx, my)) {
			buttonIncYear.setSelected(!buttonIncYear.isSelected());
			nextYear();
			//initHistogram();
		}
		if (dropUpMenu.isInRectangle(mx, my)) {
			dropUpMenu.setSelected(!dropUpMenu.isSelected());
		}
		if (dropUpMenu.isSelected()) {
			dropUpMenu.setSelectedName(dropUpMenu.selected(mx, my));
		}
		if (dropUpMenu2.isInRectangle(mx, my)) {
			dropUpMenu2.setSelected(!dropUpMenu2.isSelected());
		}
		if (dropUpMenu2.isSelected()) {
			int a=dropUpMenu2.selected(mx, my);
			dropUpMenu2.setSelectedName(a);
			Utilities.focusAttribute=dropUpMenu2.getSelectedName();

		}
		if (updateQueryButton.isInRectangle(mx, my)) {
			updateQueryButton.setSelected(!updateQueryButton.isSelected());

			ms.pushFilters();
			results = queryManager.getCrashesALL();
			gm = new GridManager(this, map, results);
			gm.computeGridValues();
			
			timeline.updateData(gm);
			
			h1.setData(results);
			h2.setData(results);
		}
		if (isIn(mx, my, Positions.medallionX, Positions.medallionY,
				Positions.medallionSide, Positions.medallionSide)) {
			ms.onClick(mx, my);
		}
		
		if (isIn(mx, my, Positions.buttonProviderX,Positions.buttonProviderY,Positions.buttonProviderWidth,Positions.buttonProviderHeight)) {
			buttonProvider.setSelected(!buttonProvider.isSelected());
			switchProvider();
		}
		
	}
	
	
	
	public void myDragged(int id, float mx, float my) {
		// DRAG ON THE MAP!
		if (isIn(mx, my, Utilities.mapOffset.x, Utilities.mapOffset.y,	Utilities.mapSize.x, Utilities.mapSize.y)) {
			mapDragHack++;
			if (mapDragHack % 10 == 0) {
				mapDragHack = 1;

				System.out.println("OLD CENTER: "+map.getCenter().lat+ " "+map.getCenter().lon);
				
				map.setCenter(map.pointLocation(
						map.locationPoint(map.getCenter()).x
								- (mx - lastTouchPos.x),
						map.locationPoint(map.getCenter()).y
								- (my - lastTouchPos.y)));

				System.out.println("NEW CENTER: "+map.getCenter().lat+ " "+map.getCenter().lon);
				
				lastTouchPos.x = mx;
				lastTouchPos.y = my;
				mapHasMoved = true;
			}
		}
	}

	public void myReleased(int id, float mx, float my) {
		touchList.remove(id);
		if (mapHasMoved) {
			gm.computeGridValues();timeline.updateData(gm);
			//initHistogram();
			if (map.getZoom()>=zoomThreshold) {
				markerList=updateMarkerList();
			}
			markerList=updateMarkerList();
			
			updateCoordinatesLimits();
			
			mapHasMoved=false;
		}
	}

	public void myClicked(int id, float mx, float my) {
	}

	public void mouseDragged() {
		myDragged(-1, mouseX, mouseY);
	}

	public void mousePressed() {
		//System.out.println("MOUSE PRESSED");
		myPressed(-1, mouseX, mouseY);
	}

	public void mouseClicked() {
		myClicked(-1, mouseX, mouseY);
	}

	public void mouseReleased() {
		// MAP CLICK:
		myReleased(-1, mouseX, mouseY);
	}

	@SuppressWarnings("unchecked")
	public void touchDown(int ID, float xPos, float yPos, float xWidth,
			float yWidth) {
		
		Touch t = new Touch(ID, xPos, yPos, xWidth, yWidth);
		touchList.put(ID,t);
		System.out.println("Added Touch "+ID);
		
		if (touchList.size() == 1) { // If one touch record initial position
			// (for dragging). Saving ID 1 for later
			touchID1 = ID;
			initTouchPos.x = mouseX;
			initTouchPos.y = mouseY;
		} else if (touchList.size() == 2) { 
			// If second touch record initial
			// position (for zooming). Saving ID
			// 2 for later
			touchID2 = ID;
			initTouchPos2.x = mouseX;
			initTouchPos2.y = mouseY;
		}
		
		if (touchList.size() >= 5) {
		
		// Zoom to entire USA
			map.setCenterZoom(locationUSA, minZoom);
		}
		
		if (ID == touchID1) {
			lastTouchPos.x = mouseX;
			lastTouchPos.y = mouseY;
		} else if (ID == touchID2) {
			lastTouchPos2.x = mouseX;
			lastTouchPos2.y = mouseY;
		}
		
		myPressed(ID, xPos, yPos);
	}

	@SuppressWarnings("unchecked")
	public void touchMove(int ID, float xPos, float yPos, float xWidth,
			float yWidth) {
		myDragged(ID, xPos, yPos);
	}

	public void touchUp(int ID, float xPos, float yPos, float xWidth,
			float yWidth) {
		touchList.remove(ID);
		System.out.println("Released Touch "+ID);
		myReleased(ID, xPos, yPos);
	}

}

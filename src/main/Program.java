package main;

import java.util.ArrayList;
import java.util.Hashtable;

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
	InteractiveMap map;

	ArrayList<Marker> markerList;

	ArrayList<BasicControl> controls;

	QueryManager queryManager;
	ArrayList<DataCrashInstance> results;
	int year = 2005;
	GridManager gm;

	Button buttonPlus, buttonMinus, updateQueryButton;
	Button buttonIncYear, buttonDecYear;

	/**
	 * temp addition
	 */
	Keyboard keyboard;
	SuggestionBox sb;
	Histograph h1, h2;
	DropUpMenu dropUpMenu;
	MedallionSelector ms;

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
		touchList = new Hashtable<Integer, Touch>();
		
		// MARKERS
		markerList = new ArrayList<Marker>();
		Utilities.markerShape = loadShape("marker.svg");

		// DB
		queryManager = new QueryManager(this);

		long timer = System.currentTimeMillis();
		results = queryManager.getCrashesALL();
		System.out.println(System.currentTimeMillis() - timer);
		timer = System.currentTimeMillis();

		// GRID
		gm = new GridManager(this, map, results);
		System.out.println("InitApp1");
		gm.computeGridValues();
		System.out.println(System.currentTimeMillis() - timer);

		// OTHER
		Utilities.font = this.loadFont("Helvetica-Bold-100.vlw");
	}

	public void initHistogram() {
		h1.setData(queryManager.getHisogramCrashes(Utilities.minActiveLatitude,
				Utilities.maxActiveLatitude, Utilities.minActiveLongitude,
				Utilities.maxActiveLongitude));
		h2.setData(queryManager.getHisogramFatalities(
				Utilities.minActiveLatitude, Utilities.maxActiveLatitude,
				Utilities.minActiveLongitude, Utilities.maxActiveLongitude));

		h1.setBounds();
		h2.setBounds();

		h1.setString("Crashes (#)", "Year");
		h2.setString("Fatalities (#)", "Year");

	}

	public void initControls() {
		controls = new ArrayList<BasicControl>();

		ms = new MedallionSelector(this, Utilities.defaultFocusAttribute,
				new String[] { "A", "B", "C", "DDD" }, Positions.medallionX,
				Positions.medallionY, Positions.medallionSide);
		controls.add(ms);

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
				Positions.textBoxWidth, Positions.textBoxHeight);
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

		dropUpMenu = new DropUpMenu(this, Utilities.width / 3 * 2,
				Utilities.height / 2, 100, 20, ms);
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

		updateQueryButton = new Button(this, Utilities.width / 3 * 2,
				Utilities.height / 2 + 20, 100, 20);
		updateQueryButton.setName("Update!");
		updateQueryButton.setElpsFalse();

		controls.add(buttonPlus);
		controls.add(buttonMinus);
		controls.add(buttonIncYear);
		controls.add(buttonDecYear);
		controls.add(updateQueryButton);
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

		// UPDATE MARKERS POSITIONS AND DRAW+
		for (Marker m : markerList) {
			Point2f p = map.locationPoint(m.location);
			if (isIn(p.x, p.y, Utilities.mapOffset.x, Utilities.mapOffset.y,
					Utilities.mapSize.x, Utilities.mapSize.y)) {
				m.x = p.x;
				m.y = p.y;
				m.draw();
			}
		}

		// DRAW CONTROLS
		for (BasicControl bc : controls) {
			bc.draw();
		}

		// PROCESS OMICRON
		if (Utilities.isWall) {
			omicronManager.process();
		}
		// drawNewMexico();
		gm.drawGrid();
		gm.drawCircles(year);

		textFont(Utilities.font, 30);
		fill(Colors.white);
		text(year, Utilities.width * 0.7f, Utilities.height * 0.1f);
	}

	// INITIAL CONFIGURATION OF THE MAP
	int currentProviderIndex;
	AbstractMapProvider[] providers;

	Location locationUSA = new Location(38.962f, -94.928f);

	// INIT MAP SIZE AND POSITION
	public void initMap() {
		Utilities.mapSize = new PVector(width / 2, height);
		Utilities.mapOffset = new PVector(0, 0);

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
		map.setZoom(zoomInterState);

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
	final int zoomInterState = 4;
	final int zoomState = 7;
	final int zoomCity = 11;
	final int maxZoom = 19;
	final int minZoom = 1;

	final int maxYear = 2010;
	final int minYear = 2001;

	public void zoomIn() {
		if (map.getZoom() < maxZoom) {
			map.zoomIn();
			gm.computeGridValues();
			updateCoordinatesLimits();
			System.out.println("Current zoom level: " + map.getZoom());
		}
	}

	public void zoomOut() {
		if (map.getZoom() > minZoom) {
			map.zoomOut();
			gm.computeGridValues();
			updateCoordinatesLimits();
			System.out.println("Current zoom level: " + map.getZoom());
		}
	}

	public void nextYear() {
		if (year < maxYear) {
			year++;
		}
	}

	public void prevYear() {
		if (year > minYear) {
			year--;
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
			initHistogram();
			break;

		case '-':
			zoomOut();
			initHistogram();
			break;

		case ' ':
			switchProvider();
			break;
		}

		switch (keyCode) {
		case RIGHT:
			nextYear();
			initHistogram();
			break;

		case LEFT:
			prevYear();
			initHistogram();
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

	@SuppressWarnings("rawtypes")
	Hashtable touchList;

	public boolean mapHasMoved = false;

	public void myPressed(int id, float mx, float my) {
		Touch t = new Touch(id, mx, my, 0, 0);
		touchList.put(id, t);

		if (touchList.size() == 1) { // If one touch record initial position
										// (for dragging). Saving ID 1 for later
			touchID1 = id;
			initTouchPos.x = mx;
			initTouchPos.y = my;
		} else if (touchList.size() == 2) { // If second touch record initial
											// position (for zooming). Saving ID
											// 2 for later
			touchID2 = id;
			initTouchPos2.x = mx;
			initTouchPos2.y = my;
		}

		if (touchList.size() >= 5) {

			// Zoom to entire USA
			map.setCenterZoom(locationUSA, 6);
		}

		if (id == touchID1) {
			lastTouchPos.x = mx;
			lastTouchPos.y = my;
		} else if (id == touchID2) {
			lastTouchPos2.x = mx;
			lastTouchPos2.y = my;
		}

		// CLICK ON THE MAP: if we are clicking on the map, check if we are
		// clicking on a marker.
		// IF so, toggle its opening.
		if (isIn(mouseX, mouseY, Utilities.mapOffset.x, Utilities.mapOffset.y,
				Utilities.mapSize.x, Utilities.mapSize.y)) {

			lastTouchPos.x = mouseX;
			lastTouchPos.y = mouseY;
		}

		if (isIn(mouseX, mouseY, Positions.keyboardX, Positions.keyboardY,
				Positions.keyboardWidth, Positions.keyboardHeight)) {
			sb.updateTextBox(keyboard.Click(mouseX, mouseY));
		}

		if (isIn(mouseX, mouseY, Positions.suggestionBoxX,
				Positions.suggestionBoxY, Positions.suggestionBoxWidth,
				Positions.suggestionBoxHeight)) {
			sb.Click(mouseX, mouseY);
		}

		if (buttonPlus.isInRectangle(mouseX, mouseY)) {
			buttonPlus.setSelected(!buttonPlus.isSelected());
			zoomIn();
			initHistogram();
		}
		if (buttonMinus.isInRectangle(mouseX, mouseY)) {
			buttonMinus.setSelected(!buttonMinus.isSelected());
			zoomOut();
			initHistogram();
		}
		if (buttonDecYear.isInRectangle(mouseX, mouseY)) {
			buttonDecYear.setSelected(!buttonDecYear.isSelected());
			prevYear();
			initHistogram();
		}
		if (buttonIncYear.isInRectangle(mouseX, mouseY)) {
			buttonIncYear.setSelected(!buttonIncYear.isSelected());
			nextYear();
			initHistogram();
		}
		if (dropUpMenu.isInRectangle(mouseX, mouseY)) {
			dropUpMenu.setSelected(!dropUpMenu.isSelected());
		}
		if (dropUpMenu.isSelected()) {
			dropUpMenu.setSelectedName(dropUpMenu.selected(mouseX, mouseY));
		}
		if (updateQueryButton.isInRectangle(mouseX, mouseY)) {
			updateQueryButton.setSelected(!updateQueryButton.isSelected());

			ms.pushFilters();
			results = queryManager.getCrashesALL();
			gm = new GridManager(this, map, results);
			gm.computeGridValues();
		}
	}
	int mapDragHack=1;
	public void myDragged(int id, float mx, float my) {
		// DRAG ON THE MAP!
		if (isIn(mx, my, Utilities.mapOffset.x, Utilities.mapOffset.y,
				Utilities.mapSize.x, Utilities.mapSize.y)) {
			// MOUSE CLICK
			if (id < 0) {
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
			} else {// OMICRON
				if (touchList.size() == 2) {
					// Only two touch, scale map based on midpoint and distance
					// from initial touch positions
					float sc = dist(lastTouchPos.x, lastTouchPos.y,
							lastTouchPos2.x, lastTouchPos2.y);
					float initPos = dist(initTouchPos.x, initTouchPos.y,
							initTouchPos2.x, initTouchPos2.y);

					PVector midpoint = new PVector(
							(lastTouchPos.x + lastTouchPos2.x) / 2,
							(lastTouchPos.y + lastTouchPos2.y) / 2);
					sc -= initPos;
					sc /= 5000;
					sc += 1;
					// println(sc);
					float mX = (midpoint.x - Utilities.mapOffset.x)
							- Utilities.mapSize.x / 2;
					float mY = (midpoint.y - Utilities.mapOffset.y)
							- Utilities.mapSize.y / 2;
					/*map.tx -= mX / map.sc;
					map.ty -= mY / map.sc;
					map.sc *= sc;
					map.tx += mX / map.sc;
					map.ty += mY / map.sc;*/
				}
			}
		}
		// Update touch list
		/*
		 * Touch t = new Touch(id, mx, my, 0, 0); touchList.put(id, t);
		 */
	}

	public void myReleased(int id, float mx, float my) {
		touchList.remove(id);
		if (mapHasMoved) {
			gm.computeGridValues();
			initHistogram();
			mapHasMoved=false;
		}
		System.out.println("RELEASED");
	}

	public void myClicked(int id, float mx, float my) {
		if (isIn(mouseX, mouseY, Positions.medallionX, Positions.medallionY,
				Positions.medallionSide, Positions.medallionSide)) {
			ms.onClick(mouseX, mouseY);
		}
	}

	public void mouseDragged() {
		myDragged(-1, mouseX, mouseY);
	}

	public void mousePressed() {
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
		myPressed(ID, xPos, yPos);
	}

	@SuppressWarnings("unchecked")
	public void touchMove(int ID, float xPos, float yPos, float xWidth,
			float yWidth) {
		myDragged(ID, xPos, yPos);
	}

	public void touchUp(int ID, float xPos, float yPos, float xWidth,
			float yWidth) {
		myReleased(ID, xPos, yPos);
	}

}

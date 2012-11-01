package main;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;

import com.modestmaps.InteractiveMap;
import com.modestmaps.geo.Location;

import db.DataQuad;

public class GridManager {
	
	//GRID
	public int gridVLine=10;
	public int gridHLine=5;
	public float gridVStep=Utilities.mapSize.y / (gridHLine-1);
	public float gridHStep=Utilities.mapSize.x / (gridVLine-1);;
	public int[] gridValues;
	public int minGridValue;
	public int maxGridValue;
	
	public PApplet parent;
	public InteractiveMap map;
	public ArrayList<DataQuad> results;
	
	public GridManager(PApplet p, InteractiveMap m, ArrayList<DataQuad> res) {
		parent=p;
		map=m;
		results=res;
	}

	public void computeGridValues(int year) {
		gridValues  = new int[(gridVLine-1)*(gridHLine-1)];
		
		maxGridValue=Integer.MIN_VALUE;
		minGridValue=Integer.MAX_VALUE;
		/*
		 * Location[][][] locs = new Location[gridVLine-1][gridHLine-1][2];
		for (int i=0;i<locs.length; i++){
			for (int j=0; j<locs[0].length;j++) {
				locs[i][j][0]=map.pointLocation(Utilities.mapOffset.x+i*gridHStep, Utilities.mapOffset.y+j*gridVStep);
				locs[i][j][1]=map.pointLocation(Utilities.mapOffset.x+(i+1)*gridHStep, Utilities.mapOffset.y+(j+1)*gridVStep);
			}
		}
		
		for(DataQuad dq : results) {
			if (dq.getYear()==year) {
				int i;
				for (i=0;i<locs.length;i++) {
					if (locs[i][0][1].lat <= dq.getLatitude() && locs[i+1][0][1].lat >= dq.getLatitude())
						break;
				}
				int j;
				for (int j=0;j<locs[0].length;j++) {
					if (locs[i][j][0].lon <= dq.getLatitude() && locs[i+1][0][1].lat >= dq.getLatitude())
						break;
				}
			}
			
		}*/
		
		for (int i=0;i<gridVLine-1;i++) {
			for (int j=0;j<gridHLine-1;j++) {
				Location a =map.pointLocation(Utilities.mapOffset.x+i*gridHStep, Utilities.mapOffset.y+j*gridVStep);
				Location b =map.pointLocation(Utilities.mapOffset.x+(i+1)*gridHStep, Utilities.mapOffset.y+(j+1)*gridVStep);
				int count = 0;
				for (DataQuad dq : results) {
					//WATCH THE SIGNS OF THE COMPARISONS!!!
					if (dq.getYear()==year && b.lat<=dq.getLatitude() && dq.getLatitude() <= a.lat && a.lon<=dq.getLongitude() && dq.getLongitude() <= b.lon) {
						count++;
					}
						
				}				
				if (count > maxGridValue) maxGridValue=count;
				if (count < minGridValue) minGridValue=count;
				gridValues[i*(gridHLine-1)+j]=count;
			}
		}
	}
	
	public void drawCircles() {
		parent.textFont(Utilities.font, Utilities.Converter(15));
		parent.fill(Colors.white);		
		float maxDiam = Math.min(gridHStep, gridVStep);
		parent.ellipseMode(PConstants.CENTER);
		parent.strokeWeight(Utilities.Converter(1));
		parent.fill(0x88FFFFFF);
		for (int i=0;i<gridVLine-1;i++) {
			for (int j=0;j<gridHLine-1;j++) {
				float diameter= maxDiam * gridValues[i*(gridHLine-1)+j] / maxGridValue;
				parent.ellipse((float)(Utilities.mapOffset.x+(i+0.5)*gridHStep), (float)(Utilities.mapOffset.y+(j+0.5)*gridVStep), diameter, diameter);
			}
		}
	}
	
	public void drawGrid() {
		parent.fill(Colors.white);
		parent.strokeWeight(Utilities.Converter(1));
		
		for (int i=0;i<gridVLine;i++) {
			parent.line(Utilities.mapOffset.x+gridHStep*i, Utilities.mapOffset.y, Utilities.mapOffset.x+gridHStep*i, Utilities.mapOffset.y+Utilities.mapSize.y);
		}
		
		for (int i=0;i<gridHLine;i++) {
			parent.line(Utilities.mapOffset.x, Utilities.mapOffset.y+gridVStep*i, Utilities.mapOffset.x+Utilities.mapSize.x, Utilities.mapOffset.y+gridVStep*i);
		}
	}
}

package main;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import types.DataQuad;

import com.modestmaps.InteractiveMap;
import com.modestmaps.geo.Location;


public class GridManager {
	
	//GRID
	public int gridVLine=20;
	public int gridHLine=10;
	public float gridVStep=Utilities.mapSize.y / (gridHLine-1);
	public float gridHStep=Utilities.mapSize.x / (gridVLine-1);;
	public int[][][] gridValues; //i, j, year
	
	public PApplet parent;
	public InteractiveMap map;
	public ArrayList<DataQuad> results;
	
	Piechart [][][] pies;
	
	public GridManager(PApplet p, InteractiveMap m, ArrayList<DataQuad> res) {
		parent=p;
		map=m;
		results=res;
		pies=new Piechart[gridHLine-1][gridVLine-1][10];
		for (int i=0;i<pies.length;i++) {
			for (int j=0;j<pies[i].length;j++) {
				for (int k=0;k<pies[i][j].length;k++) {
					pies[i][j][k]=new Piechart(parent, Utilities.Converter(20), (float)(Utilities.mapOffset.x+(j+0.5)*gridHStep), (float)(Utilities.mapOffset.y+(i+0.5)*gridVStep));
					pies[i][j][k].values=new int[]{0,0,0,0,0,0,0};
				}
			}
		}
	}

	public void computeGridValues() {		
		gridValues  = new int[gridHLine-1][gridVLine-1][10];
		for (int i=0;i<gridValues.length;i++) {
			for (int j=0;j<gridValues[i].length;j++) {
				for (int k=0;k<gridValues[i][j].length;k++) {
					gridValues[i][j][k]=0;
				}
			}
		}
		
		Location[][] locs = new Location[gridHLine][gridVLine];
		for (int i=0;i<locs.length; i++){
			for (int j=0; j<locs[0].length;j++) {
				locs[i][j]=map.pointLocation(Utilities.mapOffset.x+j*gridHStep, Utilities.mapOffset.y+i*gridVStep);
			}
		}
		

		System.out.println("Looking at "+locs[0][0].lat+" "+locs[0][0].lon+" up to "+locs[locs.length-1][locs[0].length-1].lat+" "+locs[locs.length-1][locs[0].length-1].lon);;
		
		
		for(DataQuad dq : results) {
			//LOCATE THE CORRECT ROW:
			int r;
			for (r=0;r<locs.length-1;r++) {
				if (locs[r+1][0].lat <= dq.getLatitude() &&  dq.getLatitude() <= locs[r][0].lat)
					break;
			}
			
			int c;
			for (c=0;c<locs[r].length-1;c++) {
				if (locs[r][c].lon <= dq.getLongitude() && dq.getLongitude() <= locs[r][c+1].lon)
					break;
			}
			
			//CHECK FOR GARBAGE OF OUTER STATES
			if ((r!=locs.length-1)&&(c!=locs[r].length-1)) {			
				//INCREMENT
				gridValues[r][c][dq.getYear()-2001]++;
				if (dq.getDOW()>=1 && dq.getDOW()<=7)
					pies[r][c][dq.getYear()-2001].values[dq.getDOW()-1] ++ ;
			}
		}
	}
	
	public void drawCircles(int year) {
		parent.textFont(Utilities.font, Utilities.Converter(15));
		parent.fill(Colors.white);		
		float maxDiam = Math.min(gridHStep, gridVStep);
		float maxGridValue = Integer.MIN_VALUE;
		int yearIndex = year-2001;
		for (int i=0;i<gridValues.length;i++)
			for (int j=0;j<gridValues[i].length;j++)
				if (gridValues[i][j][yearIndex]>maxGridValue) maxGridValue=gridValues[i][j][yearIndex];
		
		parent.ellipseMode(PConstants.CENTER);
		parent.strokeWeight(Utilities.Converter(1));
		for (int i=0;i<gridValues.length;i++) {
			for (int j=0;j<gridValues[i].length;j++) {
				float diameter= maxDiam * gridValues[i][j][yearIndex] / maxGridValue;
				pies[i][j][yearIndex].diameter=diameter;
				//parent.fill(0x77EE1010);
				//parent.ellipse((float)(Utilities.mapOffset.x+(j+0.5)*gridHStep), (float)(Utilities.mapOffset.y+(i+0.5)*gridVStep), diameter, diameter);
				pies[i][j][yearIndex].draw();
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

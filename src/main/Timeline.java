package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PConstants;
import types.DataCrash;

public class Timeline extends BasicControl {

	GridManager gm;
	HashMap <String, Float[]> data;
	float[] total;
	float max;
	float myPlotWidth;
	float myPlotHeight;
	
	String[] options;
	
	public Timeline(PApplet p, float x, float y, float w, float h, GridManager gm) {
		super(p,x,y,w,h);
		this.gm=gm;
		
		myPlotWidth =myWidth;
		myPlotHeight = (float) (myHeight*0.9);
	}
	
	public void updateData(GridManager gm) {
		this.gm=gm;
		this.data=new HashMap<String, Float[]>();
		

		int index = FilterValues.attributesHasMap.get(Utilities.focusAttribute);
		DataCrash[] temp = FilterValues.filtersValue[index];
		options = new String[temp.length];
		for (int i = 0; i < options.length; i++) {
			options[i] = temp[i].getToShowVaue();
			
			Float[] f = new Float[10];
			Arrays.fill(f, new Float(0));
			
			data.put(options[i], f);
		}

		
		//i IS ROW
		for (int i=0;i<gm.pies.length;i++) {
			//j IS COLUMN
			for (int j=0;j<gm.pies[i].length;j++) {
				//k IS YEAR
				for (int k=0;k<gm.pies[i][j].length;k++) {
					//o is OPTION
					for (int o=0;o<gm.pies[i][j][k].values.size();o++) {
						Float[] oldCounts = data.get(gm.pies[i][j][k].values.get(o).key);
						
						data.remove(gm.pies[i][j][k].values.get(o).key);
						
						Piechart pc = gm.pies[i][j][k];
						ArrayList<Piechart.KeyValue> kvs = pc.values;
						
						Piechart.KeyValue kv = kvs.get(o);
						float v = kv.value;
						oldCounts[k] +=v;
						data.put(gm.pies[i][j][k].values.get(o).key, oldCounts);						
					}
				}
				
			}
		}
		
		
		//Compute MAX
		total = new float[] {0,0,0,		0,0,0,		0,0,0,0};
		max = Float.MIN_VALUE;
		
		for (int i=0;i<10;i++) {
			for (int o=0;o<data.size();o++) {
				total[i]+=data.get(options[o])[i];
				
			}
			if (total[i]>max)
				max=total[i];
		}
	}
	
	public void draw() {
		//Compute the data by iterating over the gridmanager's pies:
		parent.noStroke();
		float xStep = myPlotWidth / 9;
		
		//ACTUAL DRAW
		float[] oldY  = new float[10];
		Arrays.fill(oldY, myY+myPlotHeight);
		
		for (int o=0;o<data.size();o++) {
			parent.fill(Utilities.colorCodes[o%Utilities.colorCodes.length]);
			
			
			float newY2=0;
			for (int k=0;k<9;k++) {
				parent.beginShape();
				
				float newY1 = oldY[k]- data.get(options[o])[k] * myPlotHeight / max;
				parent.vertex(myX + xStep*k, newY1);
				
				
				newY2 = oldY[k+1]- data.get(options[o])[k+1] * myPlotHeight / max; 
				parent.vertex(myX+xStep*(k+1), newY2);
				
				parent.vertex(myX+xStep*(k+1), oldY[k+1]);
				parent.vertex(myX+xStep*(k), oldY[k]);
				
				oldY[k] = newY1;
				
				parent.endShape();
			}
			oldY[9] = newY2;
			
		}
		
		//DRAW YEARS
		parent.fill(Colors.white);
		parent.textSize(myHeight-myPlotHeight);
		parent.stroke(Colors.white);
		parent.textAlign(PConstants.CENTER);
		for (int i=0;i<10;i++) {
			parent.text((2001+i)+"", myX+i*xStep, myY+myHeight);
			//DRAW EVENTS
			parent.strokeWeight(Utilities.Converter(1));
			if (EventsManager.showEvents)			
				//CHECK IF LINES SHOULD BE THICKER OR NOT
				for (int w=0;w<EventsManager.eventsYears.length;w++)
					if (EventsManager.eventsYears[w]==2001+i) {
						parent.strokeWeight(Utilities.Converter(5));
						if (EventsManager.eventsShow[w]) {
							parent.text(EventsManager.eventsName[w], myX+i*xStep, EventsManager.eventsY[w]);
						}
					}
			
			parent.line(myX+i*xStep, myY, myX+i*xStep, myY+myPlotHeight);
		}
		
		//DRAW Y AXIS
		/*parent.textAlign(PConstants.RIGHT);
		float yStep = myPlotHeight / 5;
		for (int i=0;i<=5;i++) {
			parent.text( ((int)(max/5*i)) +"", myX, myY+myPlotHeight-yStep*i);
		}*/
		
	}
}

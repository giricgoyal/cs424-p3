/**
 * class to implement histograph
 */
package main;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import types.DataYearPair;

/**
 * @author giric
 *
 */
public class Histograph extends BasicControl {
	
	/**
	 * sample string to plot an example. 
	 * add an argument in the constructor to draw histograms for different information as per the need
	 */
	//public String[][] sampleData = {{"2001","2002","2003","2004","2005","2006","2007","2008","2009","2010"},
	//		{"20","40","160","10","90","100","45","40","0","70"}};
	
	private ArrayList<DataYearPair> sampleData;
	
	public Histograph(PApplet parent, float x, float y, float width,float height) {
		super(parent, x, y, width, height);
		sampleData = new ArrayList<DataYearPair>();
		// TODO Auto-generated constructor stub
	}
	
	public void setData(ArrayList<DataYearPair> sampleData){
		this.sampleData = sampleData;
	}
	
	private float getMin() {
		float min = PConstants.MAX_FLOAT;
		for (int count = 0; count < sampleData.size(); count++) {
			if (min > sampleData.get(count).getValue()) {
				min = sampleData.get(count).getValue();
			}
		}
		return min;
	}
	
	private float getMax() {
		float max = PConstants.MIN_FLOAT;
		for (int count = 0; count < sampleData.size(); count++) {
			if (max < sampleData.get(count).getValue()) {
				max = sampleData.get(count).getValue();
			}
		}
		return max;
		
	}

	@SuppressWarnings("static-access")
	private void drawDataBars() {
		parent.rectMode(PConstants.CENTER);
		parent.fill(Colors.dark);
		parent.stroke(Colors.light);
		parent.strokeWeight(Utilities.Converter(1));
		parent.rect(myX, myY, myWidth, myHeight);
		
		for (int column = 0; column < sampleData.size(); column++) {
			parent.noStroke();
			parent.fill(0x800000ED);
			float value = sampleData.get(column).getValue();
			float year = sampleData.get(column).getYear();
			float x = parent.map((int)year, Utilities.yearMin, Utilities.yearMax, myX - myWidth/2 + Utilities.Converter(15), myX + myWidth/2 - Utilities.Converter(15));
			float y = parent.map(value, getMin(), getMax(), myY + myHeight/2, myY - myHeight/2 + Utilities.Converter(10));
			parent.rectMode(PConstants.CORNERS);
			parent.rect(x-Utilities.barWidth/2, y, x + Utilities.barWidth/2, myY + myHeight/2);
			parent.fill(Colors.white);
			parent.textAlign(PConstants.CENTER, PConstants.TOP);
			parent.textSize(Utilities.Converter(8));
			parent.text((int)year, x, myY + myHeight/2 + Utilities.Converter(10));
		}
		
		for (float value = 0; value < getMax(); value += getMax()/10) {
			float y = parent.map(value, getMin(), getMax(), myY + myHeight/2, myY - myHeight/2 + Utilities.Converter(10));
			parent.textAlign(PConstants.RIGHT, PConstants.CENTER);
			parent.text((int)value, myX - myWidth/2 - Utilities.Converter(10), y);
			
		}
		
		parent.text("Year", myX, myY + myHeight/2 + Utilities.Converter(25));
		
	
	}
	
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		drawDataBars();
		
	}

}

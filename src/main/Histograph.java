/**
 * class to implement histograph
 */
package main;

import java.util.ArrayList;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

import processing.core.PApplet;
import processing.core.PConstants;
import types.DataCrashInstance;
import types.DataYearPair;

/**
 * @author giric
 *
 */
public class Histograph extends BasicControl {
	
	int lowerBound;
	int upperBound;
	String xLabel;
	String yLabel;
	String mainLabel;
	/**
	 * sample string to plot an example. 
	 * add an argument in the constructor to draw histograms for different information as per the need
	 */
	//public String[][] sampleData = {{"2001","2002","2003","2004","2005","2006","2007","2008","2009","2010"},
	//		{"20","40","160","10","90","100","45","40","0","70"}};
	
	//private ArrayList<DataYearPair> sampleData;
	private ArrayList<DataCrashInstance> sampleData;
	
	Hashtable allCrashes;
	Hashtable specificCrashes;
	
	public Histograph(PApplet parent, float x, float y, float width,float height) {
		super(parent, x, y, width, height);
		//sampleData = new ArrayList<DataYearPair>();
		sampleData = new ArrayList<DataCrashInstance>();
		allCrashes = new Hashtable();
		// TODO Auto-generated constructor stub
	}
	
	/*
	public void setData(ArrayList<DataYearPair> sampleData){
		this.sampleData = sampleData;
	}
	*/
	
	public void setData(ArrayList<DataCrashInstance> sampleData,String selectedName) {
		this.sampleData = sampleData;
		int optionCount = 0;
		
		if (Utilities.histOptions != null){
			int index = FilterValues.attributesHasMap.get(selectedName);
			int[] array = new int[Utilities.histOptions.length];
			System.out.println(sampleData.size());
			for (int count = 0; count < sampleData.size(); count++) {
				while(optionCount < Utilities.histOptions.length) {
					System.out.println(Utilities.histOptions[optionCount]);
					System.out.println("# : " + sampleData.get(count).getByIndex(index));
					//if (Utilities.histOptions[optionCount].compareToIgnoreCase(sampleData.get(count).getByString(Utilities.selectedName)) == 0){
					//	array[optionCount]++;
					//}
					optionCount++;
				}
			}
			for (int i=0; i<array.length; i++) 
			System.out.println("hollA" + array[i]);
		}
	}
	
	private float getMin() {
		float min = PConstants.MAX_FLOAT;
		for (int count = 0; count < sampleData.size(); count++) {
			//if (min > sampleData.get(count).getValue()) {
			//	min = sampleData.get(count).getValue();
			//}
		}
		return min;
	}
	
	private float getMax() {
		float max = PConstants.MIN_FLOAT;
		for (int count = 0; count < sampleData.size(); count++) {
			//if (max < sampleData.get(count).getValue()) {
			//	max = sampleData.get(count).getValue();
			//}
		}
		return max;
		
	}

	@SuppressWarnings("static-access")
	private void drawDataBars() {
		/**
		 * draw the rectangle outline window
		 */
		parent.rectMode(PConstants.CENTER);
		parent.fill(Colors.dark);
		parent.stroke(Colors.light);
		parent.strokeWeight(Utilities.Converter(1));
		parent.rect(myX, myY, myWidth, myHeight);
		
		
		/**
		 * draw the line graph
		 */
		parent.beginShape();
		for (int column = 0; column < sampleData.size(); column++) {
			
			//float value = sampleData.get(column).getValue();
			float year = sampleData.get(column).getYear();
			float x = parent.map((int)year, Utilities.yearMin, Utilities.yearMax, myX - myWidth/2 + Utilities.Converter(15), myX + myWidth/2 - Utilities.Converter(15));
			//float y = parent.map(value, Utilities.lowerBound, Utilities.upperBound, myY + myHeight/2, myY - myHeight/2 + Utilities.Converter(10));
		
			parent.noFill();
			parent.stroke(Colors.transparentWhite);
			parent.strokeWeight(Utilities.Converter(1));
			//parent.vertex(x, y);

		}
		parent.endShape();
		
		/**
		 * Draw the data bars
		 */
		for (int column = 0; column < sampleData.size(); column++) {
			
			//float value = sampleData.get(column).getValue();
			float year = sampleData.get(column).getYear();
			float x = parent.map((int)year, Utilities.yearMin, Utilities.yearMax, myX - myWidth/2 + Utilities.Converter(15), myX + myWidth/2 - Utilities.Converter(15));
			//float y = parent.map(value, Utilities.lowerBound, Utilities.upperBound, myY + myHeight/2, myY - myHeight/2 + Utilities.Converter(10));
						
			/**
			 * Bars
			 */
			parent.noStroke();
			parent.fill(0x800000ED);
			parent.rectMode(PConstants.CORNERS);
			//parent.rect(x-Utilities.barWidth/2, y, x + Utilities.barWidth/2, myY + myHeight/2);
			
			/**
			 * X-axis units: Year
			 */
			parent.fill(Colors.white);
			parent.textAlign(PConstants.CENTER, PConstants.TOP);
			parent.textSize(Utilities.Converter(8));
			parent.text((int)year, x, myY + myHeight/2 + Utilities.Converter(10));
			
			/**
			 * Points on the bars
			 */
			parent.stroke(Colors.transparentWhite);
			parent.strokeWeight(Utilities.Converter(5));
			//parent.point(x, y);
			
		}
		
		/**
		 * Y-axis units: No. of Crashes or fatalities
		 */
		for (int value = Utilities.lowerBound; value <= Utilities.upperBound; value += (Utilities.upperBound-Utilities.lowerBound)/5) {
			float y = parent.map(value, Utilities.lowerBound, Utilities.upperBound, myY + myHeight/2, myY - myHeight/2 + Utilities.Converter(10));
			parent.textAlign(PConstants.RIGHT, PConstants.CENTER);
			parent.text(value, myX - myWidth/2 - Utilities.Converter(10), y);
			
		}
		
		/**
		 * X-axis label: "Year"
		 */
		parent.textAlign(PConstants.CENTER, PConstants.TOP);
		parent.text(xLabel, myX, myY + myHeight/2 + Utilities.Converter(25));
		
		/**
		 * Main label: "Crashes/Fatalities"
		 */
		parent.textSize(Utilities.Converter(10));
		parent.text(mainLabel, myX, Utilities.Converter(10));
	
	}
	
	public void setBounds(){
		float min = getMin();
		float max = getMax();
		if (min > 0) {
			this.lowerBound = (int)(min - min % 1);
			this.upperBound = (int)(max + 1 - max % 1);
		}
		if (min>10){
			this.lowerBound = (int)(min - min % 10);
			this.upperBound = (int)(max + 10 - max % 10);
		}
		if (min > 100) {
			this.lowerBound = (int)(min - min % 100);
			this.upperBound = (int)(max + 100 - max % 100);
		}
		if (min > 1000) {
			this.lowerBound = (int)(min - min % 1000);
			this.upperBound = (int)(max + 1000 - max % 1000);
		}
		if (min > 10000) {
			this.lowerBound = (int)(min - min % 10000);
			this.upperBound = (int)(max + 10000 - max % 10000);
		}
		if (min > 100000) {
			this.lowerBound = (int)(min - min % 100000);
			this.upperBound = (int)(max + 100000 - max % 100000);
		}
		//if (Utilities.lowerBound > this.lowerBound) {
			Utilities.lowerBound = this.lowerBound;
		//}
		//if (Utilities.upperBound < this.upperBound) {
			Utilities.upperBound = this.upperBound;
		//}	
	}
	
	public void setString(String mainLabel, String xLabel) {
		this.mainLabel = mainLabel;
		this.xLabel = xLabel;
	}
	
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		drawDataBars();
		
	}

}

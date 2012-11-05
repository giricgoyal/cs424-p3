/**
 * class to implement histograph
 */
package main;

import java.util.ArrayList;
import java.util.Enumeration;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

import processing.core.PApplet;
import processing.core.PConstants;
import types.DataCrash;
import types.DataCrashInstance;
import types.DataPair;
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
	
	private int fieldCount;
	
	Hashtable allYearCrashes;
	Hashtable activeYearCrashes;
	
	Enumeration enumerationAll;
	Enumeration enumerationActive;
	
	
	public Histograph(PApplet parent, float x, float y, float width,float height) {
		super(parent, x, y, width, height);
		//sampleData = new ArrayList<DataYearPair>();
		sampleData = new ArrayList<DataCrashInstance>();
		
		// TODO Auto-generated constructor stub
	}
	
	/*
	public void setData(ArrayList<DataYearPair> sampleData){
		this.sampleData = sampleData;
	}
	*/
	
	public void setData(ArrayList<DataCrashInstance> sampleData) {
		this.sampleData = sampleData;
		allYearCrashes = new Hashtable();
		activeYearCrashes = new Hashtable();
		
		int index = FilterValues.attributesHasMap.get(Utilities.defaultFocusAttribute);
		System.out.println("Index : " + index);
		if (Utilities.histOptions != null){
			index = FilterValues.attributesHasMap.get(Utilities.focusAttribute);
		}
		
		DataCrash[] temp = FilterValues.filtersValue[index];
		Utilities.histOptions = new String[temp.length];
		for (int i=0; i<Utilities.histOptions.length;i++) {
			Utilities.histOptions[i] = temp[i].getToShowVaue();
		}
		System.out.println("Check");
		int[] arrayAllYears = new int[Utilities.histOptions.length];
		int[] arrayActiveYear = new int[Utilities.histOptions.length];
		for (int count = 0; count < sampleData.size(); count++) {
			int optionCount = 0;
			while(optionCount < Utilities.histOptions.length) {
				if (Utilities.histOptions[optionCount].compareToIgnoreCase(sampleData.get(count).getByIndex(index)) == 0){
					arrayAllYears[optionCount]++;
				}
				if (Utilities.histOptions[optionCount].compareToIgnoreCase(sampleData.get(count).getByIndex(index)) == 0 && Utilities.activeYear == sampleData.get(count).getYear()){
					arrayActiveYear[optionCount]++;
				}
				optionCount++;	
			}
			
			
		}
		for (int count=0; count < Utilities.histOptions.length; count++) {
		
			allYearCrashes.put(Utilities.histOptions[count], new DataPair(Utilities.histOptions[count], arrayAllYears[count]));
			activeYearCrashes.put(Utilities.histOptions[count], new DataPair(Utilities.histOptions[count], arrayActiveYear[count]));
			
		}
		
		setBounds();
		
		Utilities.barWidth = (int)((int)Positions.histographWidth/Utilities.histOptions.length);
		
	}
	
	private float getMin() {
		float min = PConstants.MAX_FLOAT;
		enumerationAll = allYearCrashes.keys();
		enumerationActive = activeYearCrashes.keys();
		while(enumerationAll.hasMoreElements()){
			DataPair dataPair = (DataPair)allYearCrashes.get(enumerationAll.nextElement());
			if (min > dataPair.getValue()) {
				min = dataPair.getValue();
			}
		}
		System.out.println("min : " + min);
		return min;
	}
	
	private float getMax() {
		float max = PConstants.MIN_FLOAT;
		enumerationAll = allYearCrashes.keys();
		enumerationActive = activeYearCrashes.keys();
		while(enumerationAll.hasMoreElements()){
			DataPair dataPair = (DataPair)allYearCrashes.get(enumerationAll.nextElement());
			if (max <  dataPair.getValue()) {
				max =  dataPair.getValue();
			}
		}
		System.out.println("max : " + max);
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
		 * Draw the data bars
		 */
		enumerationAll = allYearCrashes.keys();
		enumerationActive = activeYearCrashes.keys();
		fieldCount = 0;
		while (enumerationAll.hasMoreElements()){
			DataPair dataPair = (DataPair)allYearCrashes.get(enumerationAll.nextElement());
			
			float value = dataPair.getValue();
			String field = dataPair.getField();
			float x = parent.map(fieldCount++, 0, allYearCrashes.size(), myX - myWidth/2 + Utilities.Converter(15), myX + myWidth/2 - Utilities.Converter(15));
			float y = parent.map(value, Utilities.lowerBound, Utilities.upperBound, myY + myHeight/2, myY - myHeight/2 + Utilities.Converter(10));
						
			/**
			 * Bars
			 */
			parent.noStroke();
			parent.fill(0x800000ED);
			parent.rectMode(PConstants.CORNERS);
			parent.rect(x, y, x + Utilities.barWidth - Utilities.Converter(10), myY + myHeight/2);
			
			/**
			 * X-axis units
			 */
			parent.fill(Colors.white);
			parent.textAlign(PConstants.CENTER, PConstants.TOP);
			parent.textSize(Utilities.Converter(8));
			parent.text(field, x + Utilities.barWidth/2, myY + myHeight/2 + Utilities.Converter(10));
			
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
		 * X-axis label
		 */
		parent.textAlign(PConstants.CENTER, PConstants.TOP);
		if (Utilities.histOptions != null)
			parent.text(Utilities.focusAttribute, myX, myY + myHeight/2 + Utilities.Converter(25));
		else
			parent.text(Utilities.defaultFocusAttribute, myX, myY + myHeight/2 + Utilities.Converter(25));
		
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
			//Utilities.lowerBound = this.lowerBound;
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

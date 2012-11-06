/**
 * class to implement histograph
 */
package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;

import javax.swing.text.Position;

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
	
	float lowerBound;
	float upperBound;
	String xLabel;
	String yLabel;
	String mainLabel;
	String focusAttribute;
	String defaultFocusAttribute;
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
	Hashtable hashTemp;
	
	Enumeration enumerationAll;
	Enumeration enumerationActive;
	Enumeration enumTemp;
	
	
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
		this.focusAttribute = Utilities.focusAttribute;
		this.defaultFocusAttribute = Utilities.defaultFocusAttribute;
		allYearCrashes = new Hashtable();
		activeYearCrashes = new Hashtable();
		
		int index = FilterValues.attributesHasMap.get(this.defaultFocusAttribute);
		
		if (Utilities.histOptions != null){
			index = FilterValues.attributesHasMap.get(this.focusAttribute);
		}
		//System.out.println("Index : " + index);
		DataCrash[] temp = FilterValues.filtersValue[index];
		Utilities.histOptions = new String[temp.length];
		for (int i=0; i<Utilities.histOptions.length;i++) {
			Utilities.histOptions[i] = temp[i].getToShowVaue();
		}
		//System.out.println("Check");
		float[] arrayAllYears = new float[Utilities.histOptions.length];
		float[] arrayActiveYear = new float[Utilities.histOptions.length];
		Arrays.fill(arrayAllYears, 0);
		Arrays.fill(arrayActiveYear, 0);
		//System.out.println(Utilities.activeYear);
		for (int count = 0; count < sampleData.size(); count++) {
			int optionCount = 0;
			while(optionCount < Utilities.histOptions.length) {
				if (sampleData.get(count).getLatitude() > Utilities.minActiveLatitude && sampleData.get(count).getLatitude() < Utilities.maxActiveLatitude && sampleData.get(count).getLongitude() > Utilities.minActiveLongitude && sampleData.get(count).getLongitude() < Utilities.maxActiveLongitude){
					if (Utilities.histOptions[optionCount].compareToIgnoreCase(sampleData.get(count).getByIndex(index)) == 0){
						arrayAllYears[optionCount] += 1/Utilities.perStatePopulation[sampleData.get(count).getStateFIPS()-1];
					}
					if (Utilities.histOptions[optionCount].compareToIgnoreCase(sampleData.get(count).getByIndex(index)) == 0 && Utilities.activeYear == sampleData.get(count).getYear()){
						arrayActiveYear[optionCount] += 1/Utilities.perStatePopulation[sampleData.get(count).getStateFIPS()-1];
					}
				}	
				optionCount++;
			}
			
			
		}
		for (int count=0; count < Utilities.histOptions.length; count++) {
		
			allYearCrashes.put(Utilities.histOptions[count], new DataPair(Utilities.histOptions[count], arrayAllYears[count]));
			activeYearCrashes.put(Utilities.histOptions[count], new DataPair(Utilities.histOptions[count], arrayActiveYear[count]));
			
		}
		
		setBounds();
		
		Utilities.barWidth = (int)((int)Positions.histographWidth/Utilities.histOptions.length - Utilities.Converter(2));
		if (Utilities.histOptions.length == 2) {
			Utilities.barWidth = (int)((int)Positions.histographWidth/Utilities.histOptions.length - Utilities.Converter(8));
		}
	}
	
	private float getMin() {
		float min = PConstants.MAX_FLOAT;
		enumerationAll = allYearCrashes.keys();
		enumerationActive = activeYearCrashes.keys();
		if (mainLabel.compareToIgnoreCase(Utilities.hist1String) == 0) {
			enumTemp = enumerationAll;
			hashTemp = allYearCrashes;
		}
		else if (mainLabel.compareToIgnoreCase(Utilities.hist2String)==0) {
			enumTemp = enumerationActive;
			hashTemp = activeYearCrashes;
		}
		while(enumTemp.hasMoreElements()){
			DataPair dataPair = (DataPair)hashTemp.get(enumTemp.nextElement());
			if (min > dataPair.getValue()) {
				min = dataPair.getValue();
			}
		}
		//System.out.println("min : " + min);
		return min;
	}
	
	private float getMax() {
		float max = PConstants.MIN_FLOAT;
		enumerationAll = allYearCrashes.keys();
		enumerationActive = activeYearCrashes.keys();
		if (mainLabel.compareToIgnoreCase(Utilities.hist1String) == 0) {
			enumTemp = enumerationAll;
			hashTemp = allYearCrashes;
		}
		else if (mainLabel.compareToIgnoreCase(Utilities.hist2String)==0) {
			enumTemp = enumerationActive;
			hashTemp = activeYearCrashes;
		}
	
		while(enumTemp.hasMoreElements()){
			DataPair dataPair = (DataPair)hashTemp.get(enumTemp.nextElement());
			if (max <  dataPair.getValue()) {
				max =  dataPair.getValue();
			}
		}
		
		//System.out.println("max : " + max);
		return max;
		
	}

	@SuppressWarnings("static-access")
	private void drawDataBars() {
		String newMainLabel = "";
		/**
		 * draw the rectangle outline window
		 */
		
		parent.rectMode(PConstants.CENTER);
		parent.noFill();
		parent.stroke(Colors.light);
		parent.strokeWeight(Utilities.Converter(1));
		parent.rect(Positions.histogramAreaX, Positions.histogramAreaY, Positions.histogramAreaWidth, Positions.histogramAreaHeight);
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
		if (mainLabel.compareToIgnoreCase(Utilities.hist1String) == 0) {
			enumTemp = enumerationAll;
			hashTemp = allYearCrashes;
			newMainLabel = mainLabel;
		}
		else if (mainLabel.compareToIgnoreCase(Utilities.hist2String)==0) {
			enumTemp = enumerationActive;
			hashTemp = activeYearCrashes;
			newMainLabel = mainLabel + " " + Utilities.activeYear;
		}
		
		int w=0;
		//while (enumTemp.hasMoreElements()){
		while(fieldCount<Utilities.histOptions.length){
			DataPair dataPair = (DataPair)hashTemp.get(Utilities.histOptions[fieldCount]);
			
			float value = dataPair.getValue();
			String field = dataPair.getField();
			
			float x = parent.map(fieldCount, 0, hashTemp.size(), myX - myWidth/2 + Utilities.Converter(15), myX + myWidth/2 - Utilities.Converter(15));
			float y = parent.map(value, 0, this.upperBound, myY + myHeight/2, myY - myHeight/2 + Utilities.Converter(10));
			//System.out.println(value);	
			/**
			 * Bars
			 */
			parent.noStroke();
			parent.fill(Utilities.colorCodes[fieldCount%Utilities.colorCodes.length]);
			parent.rectMode(PConstants.CORNERS);
			parent.rect(x, y, x + Utilities.barWidth - Utilities.Converter(10), myY + myHeight/2);
			
			/**
			 * X-axis units
			 */
			parent.fill(Colors.white);
			parent.textAlign(PConstants.CENTER, PConstants.TOP);
			parent.textSize(Utilities.Converter(7));
			/*
			if (field.contains("-")){
				field = field.replaceAll("-"," ");
			}
			if (field.contains(" " + " ")){
				field = field.replaceAll(" " + " ", " ");
			}
			if (field.contains(" ")){
				field = field.replaceAll(" ","\n");
			}
			
			if (field.contains(" ")){
				field = field.replaceAll(" ", "\n");
			}
			if (field.contains("\n\n")) {
				field = field.replaceAll("\n\n" ,"\n");
			}
			if (field.contains("\n\n\n")) {
				field = field.replaceAll("\n\n\n" ,"\n");
			}
			if (field.contains("\n\n\n\n")){
				field = field.replaceAll("\n\n\n\n", "\n");
			}*/
			field = field.trim();
			if (this.focusAttribute.compareToIgnoreCase("Light Condition") == 0) {
				field = field.replaceAll("-", "");
			}
			String tempField = "";
			while(field.contains(" ")) {
				tempField = tempField.concat(field.substring(0,field.indexOf(" ")));
				tempField = tempField.concat("\n");
				System.out.println(field.substring(0,field.indexOf(" ")));
				field = field.substring(field.indexOf(" "));
				field = field.trim();
			}
			
			tempField = tempField.concat(field);
			
			//System.out.println("field: " + tempField);
			if (this.focusAttribute.compareToIgnoreCase("Month")==0 || this.focusAttribute.compareToIgnoreCase("day of week")==0) {
				tempField = tempField.substring(0,3);
			}
			parent.text(tempField, x + Utilities.barWidth/2, myY + myHeight/2 + Utilities.Converter(5));
			fieldCount++;
		}
		
		
		/**
		 * Y-axis units: No. of Crashes or fatalities
		 */
		/*
		for (int value = 0; value <= this.upperBound; value += (this.upperBound)/5) {
			float y = parent.map(value, 0, this.upperBound, myY + myHeight/2, myY - myHeight/2 + Utilities.Converter(10));
			parent.textAlign(PConstants.RIGHT, PConstants.CENTER);
			parent.text(value, myX - myWidth/2 - Utilities.Converter(10), y);
		
		}
		*/
		/**
		 * X-axis label
		 */
		parent.textAlign(PConstants.CENTER, PConstants.TOP);
		parent.textSize(Utilities.Converter(9));
		if (Utilities.histOptions != null)
			parent.text(this.focusAttribute, myX, myY + myHeight/2 + Utilities.Converter(25));
		else
			parent.text(this.defaultFocusAttribute, myX, myY + myHeight/2 + Utilities.Converter(25));
		
		/**
		 * Main label: "Crashes/Fatalities"
		 */
		parent.textAlign(PConstants.CENTER, PConstants.TOP);
		parent.textSize(Utilities.Converter(10));
		parent.text(newMainLabel, myX, Positions.histogramAreaY - Positions.histogramAreaHeight/2 + Utilities.Converter(10));
	
	}
	
	public void setBounds(){
		float min = getMin();
		float max = getMax();
		
		this.lowerBound = min;
		this.upperBound = max;
		/*
		if (max > 0) {
			this.lowerBound = min;
			this.upperBound = max;
		}
		if (max>10){
			this.lowerBound = min;
			this.upperBound = max;
		}
		if (max > 100) {
			this.lowerBound = min;
			this.upperBound = (int)(max + 100 - max % 100);
		}
		if (max > 1000) {
			this.lowerBound = (int)(min - min % 1000);
			this.upperBound = (int)(max + 1000 - max % 1000);
		}
		if (max > 10000) {
			this.lowerBound = (int)(min - min % 10000);
			this.upperBound = (int)(max + 10000 - max % 10000);
		}
		if (max > 100000) {
			this.lowerBound = (int)(min - min % 100000);
			this.upperBound = (int)(max + 100000 - max % 100000);
		}
		//if (Utilities.lowerBound > this.lowerBound) {
			//Utilities.lowerBound = this.lowerBound;
		//}		 
		//if (Utilities.upperBound < this.upperBound) {
		*/
		//	Utilities.upperBound = this.upperBound;
		//}	
	}
	
	public void setString(String mainLabel) {
		this.mainLabel = mainLabel;
		//this.xLabel = xLabel;
	}
	
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		drawDataBars();
		
	}

}

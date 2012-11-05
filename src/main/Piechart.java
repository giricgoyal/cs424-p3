package main;

import processing.core.PApplet;
import java.util.*;

public class Piechart extends BasicControl{
	
	public float diameter;
	public ArrayList<KeyValue> values;
	
	public float initPercentage1;
	public float finalPercentage1;
	public float initPercentage2;
	public float finalPercentage2;
	public float X;
	public float Y;
	
	public PApplet parent;
	
	public class KeyValue {
		public String key;
		public int value;
		public KeyValue (String k, int v) {
			this.key=k;
			this.value=v;
		}
	}
	
	public Piechart(PApplet parent, float diameter, float X, float Y) {
		super(parent,X-diameter/2,Y-diameter/2,diameter,diameter);
		this.parent=parent;
		this.diameter = diameter;
		this.X = X;
		this.Y = Y;
		this.values=new ArrayList<Piechart.KeyValue>();
	}

	public void draw() {
		// Draw Outline
		parent.stroke(Colors.black);
		parent.strokeWeight(Utilities.Converter(1));
		parent.noFill();
		parent.arc(X, Y, diameter, diameter, PApplet.radians(0), PApplet.radians(360));

		//COMPUTE MAX
		int total=0;
		for (int i=0;i<values.size();i++)
			total+=values.get(i).value;
		
		//DRAW
		float presentArc = 270;
		parent.strokeWeight(Utilities.Converter(1));
		for (int i=0;i<values.size();i++) {
			parent.fill(Utilities.colorCodes[i%Utilities.colorCodes.length]);
			float slice= 360*values.get(i).value/(float)total;
			parent.arc(X, Y, diameter, diameter, PApplet.radians(presentArc), PApplet.radians(presentArc+slice));
			
			parent.fill(Colors.black);
			parent.line(X, Y, X+diameter/2 * PApplet.cos(PApplet.radians(presentArc)), Y+diameter/2 * PApplet.sin(PApplet.radians(presentArc)));
			presentArc+= slice;
			parent.line(X, Y, X+diameter/2 * PApplet.cos(PApplet.radians(presentArc)), Y+diameter/2 * PApplet.sin(PApplet.radians(presentArc)));
		}
	}
	
	public void initValues(String ck) {
		//WE NEED TO PUSH A NEW KEYVALUE FOR EACH ISTANCE OF THE KEY
		int index = FilterValues.attributesHasMap.get(ck);
		this.values=new ArrayList<Piechart.KeyValue>();
		
		for (int i=0;i<FilterValues.filtersValue[index].length;i++) {
			KeyValue kv = new KeyValue(FilterValues.filtersValue[index][i].getToShowVaue(), 0);
			values.add(i, kv);
		}		
	}
}

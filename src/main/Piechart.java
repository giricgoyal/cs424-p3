package main;

import processing.core.PApplet;

public class Piechart extends BasicControl{
	
	public float diameter;
	public int [] values;
	public int [] colors;
	public float initPercentage1;
	public float finalPercentage1;
	public float initPercentage2;
	public float finalPercentage2;
	public float X;
	public float Y;
	
	public PApplet parent;
	
	public Piechart(PApplet parent, float diameter, float X, float Y) {
		super(parent,X-diameter/2,Y-diameter/2,diameter,diameter);
		this.parent=parent;
		this.diameter = diameter;
		this.X = X;
		this.Y = Y;
		this.colors=new int[] {
			0xAA8DD3C7, 
			0xAAFFFFB3, 
			0xAABEBADA,
			0xAAFB8072,
			0xAA80B1D3,
			0xAAFDB462,
			0xAAB3DE69,
			0xAAFCCDE5,
			0xAAD9D9D9,
			0xAABC808D,
			0xAACCEBC5,
			0xAAFFED56
		};
		this.values=new int[0];
	}

	public void draw() {
		// Draw Outline
		parent.stroke(Colors.black);
		parent.strokeWeight(Utilities.Converter(1));
		parent.noFill();
		parent.arc(X, Y, diameter, diameter, PApplet.radians(0), PApplet.radians(360));

		//COMPUTE MAX
		int total=0;
		for (int i=0;i<values.length;i++)
			total+=values[i];
		
		//DRAW
		float presentArc = 270;
		parent.strokeWeight(Utilities.Converter(1));
		for (int i=0;i<values.length;i++) {
			parent.fill(colors[i%colors.length]);
			float slice= 360*values[i]/(float)total;
			parent.arc(X, Y, diameter, diameter, PApplet.radians(presentArc), PApplet.radians(presentArc+slice));
			
			parent.fill(Colors.black);
			parent.line(X, Y, X+diameter/2 * PApplet.cos(PApplet.radians(presentArc)), Y+diameter/2 * PApplet.sin(PApplet.radians(presentArc)));
			presentArc+= slice;
			parent.line(X, Y, X+diameter/2 * PApplet.cos(PApplet.radians(presentArc)), Y+diameter/2 * PApplet.sin(PApplet.radians(presentArc)));
		}
	}
}

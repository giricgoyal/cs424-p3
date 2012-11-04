package main;

import java.util.ArrayList;

import processing.core.*;
import types.DataCrash;

/**
 * Class implementing the multiple boolean selector medallion. 
 */

/**
 * @author Akrome
 * 
 */
public class MedallionSelector extends BasicControl {

	// Name of the central token
	public String key;
	// Names of the radial tokens
	public String[] options;
	// statuses of the tokens
	private boolean[] values;
	private boolean centralValue;

	// The parent.color of a node
	private int nodeColor;
	// The parent.color of the selected nodes aura
	private int auraColor;
	// The parent.color of the line connecting the nodes
	private int lineColor;
	// The int of the text
	private int fontColor;
	// The font of the text
	private PFont font;

	float epsilonDrop = Utilities.Converter(5);

	double scalingFactor = 1.5;

	// The radius of the centers of the nodes:
	private int radius;
	// The starting offset in radiant
	private int offset;
	// The order of the insertions
	private boolean clockwise=true;

	// Coordinates
	private float tokenDiameter;

	private class Position {
		public double x;
		public double y;

		public Position(double x, double y) {
			this.x = x;
			this.y = y;
		}
	}

	private Position[] positions;

	public MedallionSelector(PApplet p, String key, String[] options, float medallionX,
			float medallionY, float medallionSide) {

		super(p, medallionX, medallionY, medallionSide, medallionSide);

		this.key = key;
		this.options = options;
		for (int i = 0; i < this.options.length; i++) {
			options[i] = options[i].replace(' ', '\n');
		}
		this.positions = new Position[options.length];

		this.values = new boolean[this.options.length];
		for (int i = 0; i < values.length; i++) {
			values[i] = true;
		}
		this.centralValue = false;

		this.tokenDiameter = this.myWidth * 2 / 12;
		this.radius = (int) (this.myWidth * 5 / 12);
		this.offset = (int) (Math.PI * 3 / 2);
		this.clockwise = false;

		this.nodeColor = parent.color(0xFFC4CBB7);
		this.auraColor = parent.color(0xFFEBEFC9);
		this.lineColor = parent.color(0xFF101010);
		this.fontColor = parent.color(0xFF101010);
		
		this.font=Utilities.font;
		parent.textSize(Utilities.Converter(10));
	}
	
	public void pushFilters() {		
		// SAVE VALUES
		System.out.println("Saving "+getKey());
		int indexW = FilterValues.attributesHasMap.get(getKey());
		int i = 0;
		for (DataCrash dc : FilterValues.filtersValue[indexW]) {
			dc.setOn(getValues()[i]);
			i++;
		}

		for (DataCrash dc : FilterValues.filtersValue[indexW]) {
			System.out.println("DC: " + dc.getToShowVaue() + " with dbv: "
					+ dc.getDatabaseValue() + " is " + dc.isOn());
		}
	}

	@Override
	public void draw() {
		// Delete the present object
		parent.fill(Colors.windowBackground);
		parent.rectMode(PConstants.CORNER);
		parent.noStroke();
		parent.fill(myX, myY, myWidth, myHeight);

		// Draw the central ellipse first
		int centralTokenX = (int) (myX + myWidth / 2);
		int centralTokenY = (int) (myY + myWidth / 2);
		parent.ellipseMode(PConstants.CENTER);
		parent.fill(nodeColor);
		parent.ellipse(centralTokenX, centralTokenY, tokenDiameter,
				tokenDiameter);

		parent.fill(fontColor);
		parent.textFont(font);
		parent.textAlign(PConstants.CENTER);

		float currentFontSize = font.getSize();
		while (parent.textWidth(key) > tokenDiameter * 0.8) {
			currentFontSize *= 0.8;
			parent.textSize(currentFontSize);
		}
		parent.text(key, centralTokenX, centralTokenY
				+ (float) (tokenDiameter * 0.15));
		parent.textSize(font.getSize());

		int order = this.clockwise ? 1 : -1;
		double angularStep = 2 * Math.PI / options.length;
		for (int i = 0; i < options.length; i++) {
			int tokenX = (int) (centralTokenX + radius
					* Math.cos(offset + angularStep * i * order));
			int tokenY = (int) (centralTokenY + radius
					* Math.sin(offset + angularStep * i * order));

			positions[i] = new Position(tokenX, tokenY);
			// If the node is active, we must draw the Aura behind it:
			if (values[i]) {
				parent.fill(auraColor);
				parent.ellipse(tokenX, tokenY, (float) (tokenDiameter * 1.2),
						(float) (tokenDiameter * 1.2));
			}

			// Draw the Node
			parent.fill(nodeColor);
			parent.ellipse(tokenX, tokenY, tokenDiameter, tokenDiameter);

			// Write the Node
			parent.fill(fontColor);
			parent.textAlign(PConstants.CENTER, PConstants.CENTER);
			
			currentFontSize = font.getSize();
			while (parent.textWidth(options[i]) > tokenDiameter * 0.8) {
				currentFontSize *= 0.8;
				parent.textSize(currentFontSize);
			}

			if (options[i].contains("\n"))
				parent.text(options[i], tokenX, tokenY - epsilonDrop
						+ (float) (tokenDiameter / scalingFactor * 0.15));
			else
				parent.text(options[i], tokenX, tokenY
						+ (float) (tokenDiameter / scalingFactor * 0.15));

			parent.textSize(font.getSize());

			parent.stroke(lineColor);
			parent.line(
					(float) (centralTokenX + tokenDiameter / 2
							* Math.cos(offset + angularStep * i)),
					(float) (centralTokenY + tokenDiameter / 2
							* Math.sin(offset + angularStep * i)),
					(float) (centralTokenX + (radius - tokenDiameter / 2)
							* Math.cos(offset + angularStep * i)),
					(float) (centralTokenY + (radius - tokenDiameter / 2)
							* Math.sin(offset + angularStep * i)));
		}
	}

	// Click handler
	public boolean onClick(float mX, float mY) {
		// Checking for an hit
		boolean hit = false;

		// We must check if the click fell inmyWidth the central circle
		float centralTokenX = myX + myWidth / 2;
		float centralTokenY = myY + myWidth / 2;
		double actualDiameter = tokenDiameter;
		if (Math.abs(Math.pow(centralTokenX - mX, 2)
				+ Math.pow(centralTokenY - mY, 2)) <= Math.pow(
				tokenDiameter / 2, 2)) {
			// Central token hit: update the value of all the statuses to the
			// centralValue, then switch it
			for (int i = 0; i < values.length; i++) {
				values[i] = centralValue;
			}
			centralValue = !centralValue;
			// Hit found
			hit = true;
		}

		if (!hit) {
			// Otherwise, check if we fell in one of the other values
			double angularStep = 2 * Math.PI / options.length;
			int order = this.clockwise ? 1 : -1;

			for (int i = 0; i < values.length; i++) {
				int tokenX = (int) positions[i].x;
				int tokenY = (int) positions[i].y;

				if (Math.abs(Math.pow(tokenX - mX, 2)
						+ Math.pow(tokenY - mY, 2)) <= Math.pow(
						tokenDiameter / 2, 2)) {
					// Update that value
					values[i] = !values[i];
					hit = true;
					break;
				}
			}
		}
		return hit;
	}

	public double getOffset() {
		return this.offset;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String[] getOptions() {
		return options;
	}

	public void setOptions(String[] options) {
		this.options = options;
		for (int i = 0; i < this.options.length; i++) {
			options[i] = options[i].replace(' ', '\n');
		}
		this.positions = new Position[options.length];
		this.values = new boolean[this.options.length];
		for (int i = 0; i < values.length; i++) {
			values[i] = true;
		}

	}

	public boolean[] getValues() {
		return values;
	}

	public void setValues(boolean[] values) {
		this.values = values;
	}

	public boolean isCentralValue() {
		return centralValue;
	}

	public void setCentralValue(boolean centralValue) {
		this.centralValue = centralValue;
	}

	public int getNodeColor() {
		return nodeColor;
	}

	public void setNodeColor(int nodeColor) {
		this.nodeColor = nodeColor;
	}

	public int getAuraColor() {
		return auraColor;
	}

	public void setAuraColor(int auraColor) {
		this.auraColor = auraColor;
	}

	public int getLineColor() {
		return lineColor;
	}

	public void setLineColor(int lineColor) {
		this.lineColor = lineColor;
	}

	public int getFontColor() {
		return fontColor;
	}

	public void setFontColor(int fontColor) {
		this.fontColor = fontColor;
	}

	public PFont getFont() {
		return font;
	}

	public void setFont(PFont font) {
		this.font = font;
	}

	public double getScalingFactor() {
		return scalingFactor;
	}

	public boolean isClockwise() {
		return clockwise;
	}

	public void setClockwise(boolean clockwise) {
		this.clockwise = clockwise;
	}

	public float getTokenDiameter() {
		return tokenDiameter;
	}

	public void setTokenDiameter(int tokenDiameter) {
		this.tokenDiameter = tokenDiameter;
	}

	public ArrayList<String> getStatus() {
		ArrayList<String> result = new ArrayList<String>();
		for (int i = 0; i < values.length; i++)
			if (values[i])
				result.add(options[i]);

		return result;
	}
}

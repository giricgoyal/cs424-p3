package main;

import processing.core.PApplet;
import processing.core.PConstants;

//No special characters

public class Keyboard extends BasicControl {

	char[][] lowerCaseButtons = {
			{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'},
			{ 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p' },
			{ 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l' },
			{ 'z', 'x', 'c', 'v', 'b', 'n', 'm', '<', '>'}, 
			{ ' '}};

	/*char[][] lowerCaseButtons = {
			{'q', 'w', 'e', 'r', 't'}, 
			{'y', 'u', 'i', 'o', 'p'},
			{'a', 's', 'd', 'f', 'g'}, 
			{'h', 'j', 'k', 'l', 'z'}, 
			{'x', 'c', 'v', 'b', 'n'}, 
			{'m', '.', '˂', '^', ' '}};
	
	char[][] lowerCaseButtons = {
			{'a', 'b', 'c', 'd', 'e'}, 
			{'f', 'g', 'h', 'i', 'j'},
			{'k', 'l', 'm', 'n', 'o'}, 
			{'p', 'q', 'r', 's', 't'}, 
			{'u', 'v', 'w', 'x', 'y'}, 
			{'z', '.', '˂', '^', ' '}};*/

	
	char[][] upperCaseButtons = {
			{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'},
			{ 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P' },
			{ 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L' },
			{ 'Z', 'X', 'C', 'V', 'B', 'N', 'M', '<', '>'}, 
			{ ' '} };
			
	/*char[][] upperCaseButtons = {
			{'Q', 'W', 'E', 'R', 'T'}, 
			{'Y', 'U', 'I', 'O', 'P'},
			{'A', 'S', 'D', 'F', 'G'}, 
			{'H', 'J', 'K', 'L', 'Z'}, 
			{'X', 'C', 'V', 'B', 'N'}, 
			{'M', '.', '˂', '^', ' '}};
	
	char[][] upperCaseButtons = {
			{'A', 'B', 'C', 'D', 'E'}, 
			{'F', 'G', 'H', 'I', 'J'},
			{'K', 'L', 'M', 'N', 'O'}, 
			{'P', 'Q', 'R', 'S', 'T'}, 
			{'U', 'V', 'W', 'X', 'Y'}, 
			{'Z', '.', '˂', '^', ' '}};*/

	int enterButtonRow = 3;
	int enterButtonColumn = 8;

	int backspaceButtonRow = 3;
	int backspaceButtonColumn = 7;

	float horizontalSpacing;
	float verticalSpacing;

	float buttonsHeight;

	public class ButtonLocation {
		public float X;
		public float Y;
		public float Width;
		public float Height;

		public ButtonLocation(float f, float g, float buttonWidth, float buttonsHeight) {
			this.X = f;
			this.Y = g;
			this.Width = buttonWidth;
			this.Height = buttonsHeight;
		}
	}

	ButtonLocation[][] buttonsLocations;

	int buttonsBackgroundColor;
	int buttonsTextColor;
	int buttonsBorderColor;

	boolean upperCase;

	public Keyboard(PApplet parent, float keyboardX, float keyboardY, float keyboardWidth, float keyboardY2) {

		super(parent,keyboardX,keyboardY,keyboardWidth,keyboardY2);

		// Setup the spacing
		this.horizontalSpacing = Utilities.Converter(5);
		this.verticalSpacing = Utilities.Converter(5);

		// Set the color of the buttons
		this.buttonsBackgroundColor = Colors.white;
		this.buttonsTextColor = Colors.black;
		this.buttonsBorderColor = Colors.white;

		// Set the initial state of the UpperCasing
		this.upperCase = false;

		// Initialize the buttons locations within the keyboard
		buttonsLocations = new ButtonLocation[lowerCaseButtons.length][];

		buttonsHeight = (float) ((this.myHeight - this.verticalSpacing)
				/ lowerCaseButtons.length - this.verticalSpacing);

		for (int i = 0; i < lowerCaseButtons.length; i++) {
			buttonsLocations[i] = new ButtonLocation[lowerCaseButtons[i].length];
			for (int j = 0; j < lowerCaseButtons[i].length; j++) {
				float buttonWidth = (float) ((this.myWidth - this.horizontalSpacing)
						/ lowerCaseButtons[i].length - horizontalSpacing);

				buttonsLocations[i][j] = new ButtonLocation(this.myX
						+ this.horizontalSpacing
						+ (horizontalSpacing + buttonWidth) * j, this.myY
						+ this.verticalSpacing
						+ (verticalSpacing + buttonsHeight) * i, buttonWidth,
						this.buttonsHeight);
			}
		}
	}

	public void draw() {
		parent.stroke(buttonsBorderColor);
		parent.fill(buttonsBackgroundColor);
		parent.strokeWeight(1);
		parent.rectMode(PConstants.CORNER);
		parent.textAlign(PConstants.LEFT,PConstants.CENTER);
		parent.textSize((float) (this.buttonsHeight*0.6));
		if (!upperCase) {
			for (int i = 0; i < lowerCaseButtons.length; i++) {
				for (int j = 0; j < lowerCaseButtons[i].length; j++) {
					parent.fill(this.buttonsBackgroundColor);
					parent.rect(buttonsLocations[i][j].X, buttonsLocations[i][j].Y,
							buttonsLocations[i][j].Width, this.buttonsHeight);

					parent.fill(this.buttonsTextColor);
					parent.text(lowerCaseButtons[i][j] + "", (float) (buttonsLocations[i][j].X
							+ buttonsLocations[i][j].Width *0.2),
							buttonsLocations[i][j].Y,
							buttonsLocations[i][j].Width, this.buttonsHeight);
				}
			}
		} else {
			for (int i = 0; i < upperCaseButtons.length; i++) {
				for (int j = 0; j < upperCaseButtons[i].length; j++) {
					parent.fill(this.buttonsBackgroundColor);
					parent.rect(buttonsLocations[i][j].X, buttonsLocations[i][j].Y,
							buttonsLocations[i][j].Width, this.buttonsHeight);

					parent.fill(this.buttonsTextColor);
					parent.text(upperCaseButtons[i][j] + "", (float) (buttonsLocations[i][j].X
							+ buttonsLocations[i][j].Width *0.2),
							buttonsLocations[i][j].Y,
							buttonsLocations[i][j].Width, this.buttonsHeight);
				}
			}
		}
	}

	public int Click(float X, float Y) {
		// This handler returns the value of the pressed char, given X and Y.
		// Note that it ALWAYS return a char, so if the space in between buttons
		// is pushed, it returns the best estimate
		// First, we estimate the row, because they are all equally tall;

		float verticalStep = verticalSpacing + buttonsHeight;
		int numberOfSteps = (int) ((myHeight - verticalSpacing) / (verticalStep));
		int verticalCursor = (int) (myY + verticalSpacing / 2 + verticalStep);
		int row = 0;
		for (row = 0; row < numberOfSteps; row++) {
			// If the click happened after the present line, but before the next
			// one, then we have a hit
			if (Y <= verticalCursor)
				// HIT!
				break;
			else
				verticalCursor += verticalStep;
		}
		// Now in Row we have the row of the click.
		// We must now calculate the horizontal displacement, given that each
		// row has different buttons width, but all the buttons in a row are
		// equally width;
		if (row == numberOfSteps)
			row--;

		float buttonWidth = buttonsLocations[row][0].Width;
		float horizontalStep = horizontalSpacing + buttonWidth;
		numberOfSteps = (int) ((myWidth - horizontalSpacing) / (horizontalStep));
		int horizontalCursor = (int) (myX + horizontalSpacing / 2 + horizontalStep);
		int column = 0;
		for (column = 0; column < numberOfSteps; column++) {
			// If the click happened after the present line, but before the next
			// one, then we have a hit
			if (X < horizontalCursor)
				// HIT!
				break;
			else
				horizontalCursor += horizontalStep;
		}
		if (column == numberOfSteps)
			column--;
		// Now we must check if we have pushed the Switch button or not
		if (row == backspaceButtonRow && column == backspaceButtonColumn) {
			return (-1);
		} else if (row == enterButtonRow && column == enterButtonColumn) {
			// ENTER button!
			return (-2);
		} else {
			if (upperCase)
				return (int) (upperCaseButtons[row][column]);
			else
				return (int) (lowerCaseButtons[row][column]);
		}
	}
}

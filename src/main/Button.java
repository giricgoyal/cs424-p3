package main;

import processing.core.PApplet;
import processing.core.PConstants;

/**
 * Button class. Create a button and assign a name to it.
 * If you want the button change color while selected call setShowClicked()
 * @author Claudio
 *
 */
public class Button extends BasicControl {
	private boolean selected;
	private boolean showClicked;
	private boolean addStroke;
	private String name;

	public Button(PApplet parent, float x, float y, float width, float height) {
		super(parent, Utilities.Converter(x), Utilities.Converter(y), Utilities.Converter(width), Utilities.Converter(height));
	}
	
	@Override
	public void draw() {
		parent.pushStyle();
		if(showClicked) parent.fill(selected?Colors.medium:Colors.dark);
		else parent.fill(Colors.medium);
		if(addStroke) {
			parent.stroke(Colors.medium);
			parent.strokeWeight(Utilities.Converter(2));
		}
		parent.rectMode(PConstants.CORNER);
		parent.ellipseMode(PConstants.CORNER);
		parent.ellipse(myX, myY, myWidth, myHeight);
		//parent.rect(myX, myY, myWidth, myHeight);
		parent.textAlign(PConstants.CENTER,PConstants.CENTER);
		parent.fill(Colors.light);
		parent.textSize(Utilities.Converter(20));
		parent.text(name, (myWidth)/2+myX, (myHeight)/2+myY);
		parent.popStyle();
	}
	
	public boolean isSelected(){
		return selected;
	}
	
	public boolean isInRectangle(float posX,float posY){
		return (myX<posX && posX<myX+myWidth && myY<posY && posY<myY+myHeight) ? true: false;
	}
	
	public void setSelected(boolean selected){
		this.selected = selected;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public void setShowClick(){
		this.showClicked=true;
	}
	
	public void setStroke(){
		this.addStroke=true;
	}
}

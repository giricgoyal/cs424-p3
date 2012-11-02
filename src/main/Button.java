package main;

import processing.core.PApplet;
import processing.core.PConstants;

public class Button extends BasicControl {
	private boolean selected;
	private String name;

	public Button(PApplet parent, float x, float y, float width, float height) {
		super(parent, x, y, width, height);
	}
	
	@Override
	public void draw() {
		parent.pushStyle();
		parent.fill(Colors.dark);
		parent.rectMode(PConstants.CORNER);
		parent.rect(myX, myY, myWidth, myHeight);
		float offset = name.contains("\n")?0:(parent.textDescent()/2);
		parent.fill(Colors.light);
		parent.text(name, (myWidth)/2+myX-parent.textWidth(name)/2, (myHeight)/2+myY+offset);
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
	
}

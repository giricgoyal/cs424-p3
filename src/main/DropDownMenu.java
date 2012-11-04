package main;

import processing.core.PApplet;
import processing.core.PConstants;

public class DropDownMenu extends BasicControl {
	private String selectedName = "ciao";
	private boolean selected;

	public DropDownMenu(PApplet parent, float x, float y, float width,
			float height) {
		super(parent, Utilities.Converter(x), Utilities.Converter(y), Utilities.Converter(width), 
				Utilities.Converter(height));
	}

	@Override
	public void draw() {
		parent.pushStyle();
		parent.rectMode(PConstants.CORNER);
		parent.fill(Colors.medium);
		parent.rect(myX, myY, myWidth, myHeight);
		parent.textSize(parent.textAscent()/4*3);
		parent.textSize(Utilities.Converter(parent.textAscent()));
		parent.textAlign(PConstants.CENTER,PConstants.CENTER);
		parent.fill(Colors.light);
		parent.textSize(Utilities.Converter(parent.textAscent()));
		parent.text(selectedName, (myWidth)/2+myX, (myHeight)/2+myY);
		if(selected) drawMenu();
		parent.popStyle();
	}
	
	private void drawMenu(){
		parent.pushStyle();
		parent.rectMode(PConstants.CORNER);
		parent.textAlign(PConstants.CENTER,PConstants.CENTER);
		parent.textSize(parent.textAscent()/4*3);
		parent.textSize(Utilities.Converter(parent.textAscent()));
		for(int i=1; i<=FilterValues.attributes.length; i++){
			parent.fill(Colors.medium);
			parent.rect(myX, myY+i*myHeight, myWidth, myHeight);
			parent.fill(Colors.light);
			parent.text(FilterValues.attributes[i-1], (myWidth)/2+myX, (myHeight)/2+myY+i*myHeight);
		}
		parent.popStyle();

	}
	
	public boolean isInRectangle(float posX,float posY){
		return (myX<posX && posX<myX+myWidth && myY<posY && posY<myY+myHeight) ? true: false;
	}
	
	public void setSelected(boolean selected){
		this.selected = selected;
	}
	
	public boolean isSelected(){
		return selected;
	}

	public int selected(float posX, float posY){
		for(int i=1; i<=FilterValues.attributes.length; i++){
			if(myX<posX && posX<myX+myWidth && myY+(i)*myHeight<posY && posY<myY+(i+1)*myHeight)
				return i;
		}
		return -1;
	}
	
	public void setSelectedName(int i){
		if(i==-1) return;
		selectedName=FilterValues.attributes[i-1];
	}
	
	
}

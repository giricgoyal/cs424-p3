package main;

import processing.core.PApplet;
import processing.core.PConstants;

/**
 * 
 * @author Claudio
 *
 */
public class DropUpMenu extends BasicControl {
	private String selectedName;
	private boolean selected;
	
	MedallionSelector medallion;

	public DropUpMenu(PApplet parent, float x, float y, float width,
			float height, MedallionSelector m) {
		super(parent, Utilities.Converter(x), Utilities.Converter(y), Utilities.Converter(width), 
				Utilities.Converter(height));
		
		selectedName = FilterValues.attributes[0];
		medallion=m;
	}

	@Override
	public void draw() {
		parent.pushStyle();
		parent.rectMode(PConstants.CORNER);
		parent.fill(Colors.medium);
		parent.rect(myX, myY, myWidth, myHeight);
		parent.textAlign(PConstants.CENTER,PConstants.CENTER);
		parent.fill(Colors.light);
		parent.textSize(Utilities.Converter(15));
		parent.text(selectedName, (myWidth)/2+myX, (myHeight)/2+myY);
		if(selected) drawMenu();
		parent.popStyle();
	}
	
	private void drawMenu(){
		parent.pushStyle();
		parent.rectMode(PConstants.CORNER);
		parent.textAlign(PConstants.CENTER,PConstants.CENTER);
		parent.textSize(Utilities.Converter(10));
		for(int i=1; i<=FilterValues.attributes.length; i++){
			parent.fill(Colors.medium);
			parent.rect(myX, myY-i*myHeight, myWidth, myHeight);
			parent.fill(Colors.light);
			parent.text(FilterValues.attributes[i-1], (myWidth)/2+myX, (myHeight)/2+myY-i*myHeight);
		}
		parent.popStyle();

	}
	
	public boolean isInRectangle(float posX,float posY){
		boolean isin = (myX<posX && posX<myX+myWidth && myY<posY && posY<myY+myHeight);
		return  isin ? true: false;
	}
	
	public void setSelected(boolean selected){
		this.selected = selected;
	}
	
	public boolean isSelected(){
		System.out.println(selected);
		return selected;
	}

	public int selected(float posX, float posY){
		for(int i=1; i<=FilterValues.attributes.length; i++){
			if(myX<posX && posX<myX+myWidth && myY-(i)*myHeight<posY && posY<myY-(i-1)*myHeight){
				System.out.println(i);
				return i;
			}
		}
		return -1;
	}
	
	public void setSelectedName(int i){
		if(i==-1) return;
		selectedName=FilterValues.attributes[i-1];
		selected=false;
		
		//TODO: ADD HERE CALL TO THE MEDALLION!
	}
	
	public void updateMedallion() {
	}
	
	
}
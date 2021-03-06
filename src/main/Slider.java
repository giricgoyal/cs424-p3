package main;

import processing.core.PApplet;
import processing.core.PConstants;

public class Slider extends BasicControl{
	public float knobX;
	public float xeff;
	public float yeff;
	public float weff;
	public float heff;
	private boolean selected;
	
	
	public Slider(PApplet parent, float x, float y, float width, float height) {
		super(parent, x, y, width, height);
		xeff=x;
		knobX=xeff;
		yeff=y;
		weff=width;
		heff=height;
	}

	@Override
	public void draw() {
		parent.pushStyle();
		parent.rectMode(PConstants.CORNER);
		parent.fill(100);
		parent.rect(xeff, yeff, weff, heff);
		drawKnob();
		drawLines();
		parent.popStyle();
	}
	
	private void drawKnob(){
		parent.rectMode(PConstants.CORNER);
		parent.fill(1);
		parent.rect(knobX,yeff,heff,heff);
	}
	
	private void drawLines(){
		float interval = weff/10;
		for(int i=1;i<10;i++){
			parent.fill(255);
			parent.textAlign(PConstants.CENTER);
			parent.textSize(Utilities.Converter(10));
			parent.text(i*10,xeff+i*interval,myY+myHeight+myHeight/2);
			parent.stroke(255);
			parent.strokeWeight(Utilities.Converter(1));
			parent.line(xeff+i*interval,yeff,xeff+i*interval,yeff+heff);
			
		}
	}
	
	
	
	public boolean isOnKnob(float posX,float posY){
		return (posX>knobX && posX<knobX+heff && posY>yeff && posY<yeff+heff)?true:false;
	}
	
	public void setKnobPosition(float mx){
		if(mx<xeff)
			knobX=xeff;
		else if(mx>xeff+weff-heff)
			knobX=xeff+weff-heff;
		else knobX=mx;
		System.out.println(getIntensity());
	}
	
	public float getIntensity(){
		float val= (float) (knobX-xeff)/(weff-heff);
		return val;
	}
	
	public void setKnobSelected(boolean value){
		selected = value;
	}
	
	public boolean isKnobSelected(){
		return selected;
	}

}

package main;

import processing.core.PApplet;
import processing.core.PConstants;
import types.DataCrash;

/**
 * 
 * @author Claudio
 * 
 */
public class DropUpMenu extends BasicControl {
	private String selectedName;
	private boolean selected;
	private boolean activeMedallion;

	MedallionSelector medallion;

	public DropUpMenu(PApplet parent, float x, float y, float width,
			float height, MedallionSelector m,boolean activeMedallion) {
		super(parent, x,y,width,height);

		selectedName = Utilities.defaultFocusAttribute;
		this.activeMedallion=activeMedallion;
		
		if(activeMedallion){
		medallion = m;
		updateMedallion();
		}
	}

	@Override
	public void draw() {
		parent.pushStyle();
		parent.rectMode(PConstants.CORNER);
		if (activeMedallion)
			parent.fill(Colors.filterColor);
		else
			parent.fill(Colors.medium);
		
		parent.rect(myX, myY, myWidth, myHeight);
		parent.textAlign(PConstants.CENTER, PConstants.CENTER);
		parent.fill(Colors.light);
		parent.textSize(Utilities.Converter(13));
		parent.text(selectedName, (myWidth) / 2 + myX, (myHeight) / 2 + myY);
		if (selected)
			drawMenu();
		parent.popStyle();
	}

	private void drawMenu() {
		parent.pushStyle();
		parent.rectMode(PConstants.CORNER);
		parent.textAlign(PConstants.CENTER, PConstants.CENTER);
		parent.textSize(Utilities.Converter(10));
		for (int i = 1; i <= FilterValues.attributes.length; i++) {
			if (activeMedallion)
				parent.fill(Colors.filterColor,200);
			else
				parent.fill(Colors.medium,200);
			parent.rect(myX, myY - i * myHeight, myWidth, myHeight);
			parent.fill(Colors.white);
			parent.text(FilterValues.attributes[i - 1], (myWidth) / 2 + myX,
					(myHeight) / 2 + myY - i * myHeight);
		}
		parent.popStyle();

	}

	public boolean isInRectangle(float posX, float posY) {
		boolean isin = (myX < posX && posX < myX + myWidth && myY < posY && posY < myY
				+ myHeight);
		return isin ? true : false;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}

	public int selected(float posX, float posY) {
		for (int i = 1; i <= FilterValues.attributes.length; i++) {
			if (myX < posX && posX < myX + myWidth
					&& myY - (i) * myHeight < posY
					&& posY < myY - (i - 1) * myHeight) {
				return i;
			}
		}
		return -1;
	}

	public void setSelectedName(int i) {
		if (i == -1)
			return;
		selectedName = FilterValues.attributes[i - 1];
		selected = false;

		// TODO: ADD HERE CALL TO THE MEDALLION!
		System.out.println("Medallion is "+activeMedallion);
		if(activeMedallion){
		medallion.pushFilters();
		updateMedallion();
		
		}
		//updateHistogram();
	}

	public void updateMedallion() {
		
		// GO UPDATING
		medallion.setKey(selectedName);
		int index = FilterValues.attributesHasMap.get(selectedName);
		DataCrash[] temp = FilterValues.filtersValue[index];
		String[] medOptions = new String[temp.length];
		for (int i = 0; i < medOptions.length; i++)
			medOptions[i] = temp[i].getToShowVaue();

		medallion.setOptions(medOptions);
	}
	
	public void updateHistogram() {
		int index = FilterValues.attributesHasMap.get(selectedName);
		DataCrash[] temp = FilterValues.filtersValue[index];
		Utilities.histOptions = new String[temp.length];
		for (int i=0; i<Utilities.histOptions.length;i++) {
			Utilities.histOptions[i] = temp[i].getToShowVaue();
		}
	}
	
	public String getSelectedName(){
		return selectedName;
	}

}
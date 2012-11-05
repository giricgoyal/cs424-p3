package db;

import java.util.ArrayList;

import main.FilterValues;
import processing.core.PApplet;
import types.DataCrashInstance;
import types.DataQuad;
import types.DataYearPair;

/**
 * Call database queries from here
 * @author Claudio
 *
 */
public class QueryManager {
	DatabaseManager db;
	String pieAttribute;
	
	public QueryManager(PApplet context){
		db = new DatabaseManager(context);
		pieAttribute = "day_of_week";
	}
	
	public ArrayList<DataCrashInstance> getCrashes(float latitude_min,float latitude_max,float longitude_min,float longitude_max){
		return db.getCrashes(latitude_min, latitude_max, longitude_min, longitude_max, getFiltersWhere());
	}
	
	public ArrayList<DataYearPair> getHisogramCrashes(float latitude_min,float latitude_max,float longitude_min,float longitude_max){
		return db.getHistogramCrashes(latitude_min, latitude_max, longitude_min, longitude_max, getFiltersWhere());
	}
	
	public ArrayList<DataYearPair> getHisogramFatalities(float latitude_min,float latitude_max,float longitude_min,float longitude_max){
		return db.getHistogramFatalities(latitude_min, latitude_max, longitude_min, longitude_max, getFiltersWhere());
	}
	
	public ArrayList<DataQuad> getCrashesALL(){
		return db.getCrashesALL(getFiltersWhere());
	}
	
	//Filters
	private String getFiltersWhere(){
		FilterValues filter = new FilterValues();
		String filtersWhere = "";
		for(int i=0;i<filter.filtersValue.length;i++){
			if(existTrue(i))
				filtersWhere += " (";
		
			for(int j=0;j<filter.filtersValue[i].length;j++){
				if(filter.filtersValue[i][j].isOn())
					filtersWhere+=" ("+filter.filtersValue[i][j].getDatabaseValue()+") or ";
			}
			if(existTrue(i))
				filtersWhere = filtersWhere.substring(0,filtersWhere.length()-3);
			if(existTrue(i))
				filtersWhere += ") and";
		}
		if(filtersWhere.length()>2)
			filtersWhere = filtersWhere.substring(0,filtersWhere.length()-3);
		return filtersWhere;
	}
	
	private String getPieAttribute(){
		return pieAttribute;
	}
	
	public void setPieAttrubte(){
		
	}
	
	private boolean existTrue(int i){
		for(int j = 0;j<FilterValues.filtersValue[i].length;j++){
			if(FilterValues.filtersValue[i][j].isOn())
				return true;
		}
		
		return false;
	}
}

package db;

import java.util.ArrayList;

import main.FilterValues;
import processing.core.PApplet;
import types.DataQuad;
import types.DataYearPair;

/**
 * Call database queries from here
 * @author Claudio
 *
 */
public class QueryManager {
	DatabaseManager db;
	
	public QueryManager(PApplet context){
		db = new DatabaseManager(context);
	}
	
	public ArrayList<DataQuad> getCrashes(float latitude_min,float latitude_max,float longitude_min,float longitude_max){
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
			for(int j=0;j<filter.filtersValue[i].length;j++){
				if(filter.filtersValue[i][j].isOn())
					filtersWhere+=" ("+filter.filtersValue[i][j].getDatabaseValue()+") or ";
			}
		}
		if(filtersWhere.length()>2)
			filtersWhere = filtersWhere.substring(0,filtersWhere.length()-3);
		return filtersWhere;
	}
}

package db;

import java.util.ArrayList;

import main.FilterValues;

public class QueryManager {
	
	public static ArrayList<DataQuad> query(float latitude_min,float latitude_max,float longitude_min,float longitude_max){
		//query database with filters
		ArrayList<DataQuad> array = new ArrayList<DataQuad>();
		return array;
	}
	
	//Filters
	public static String getFiltersWhere(){
		FilterValues filter = new FilterValues();
		String filtersWhere = null;
		for(int i=0;i<filter.filtersValue.length;i++){
			for(int j=0;j<filter.filtersValue[i].length;j++){
				if(filter.filtersValue[i][j].isOn())
					filtersWhere+=" or ("+filter.filtersValue[i][j].getDatabaseValue()+") ";
			}
		}
		return filtersWhere;
		
	}
}

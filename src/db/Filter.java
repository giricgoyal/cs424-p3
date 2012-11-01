package db;

import java.util.ArrayList;

import data.DataFilter;

public class Filter {
	ArrayList<DataFilter> filters;

	public Filter(ArrayList<DataFilter> filters) {
		this.filters = filters;
	}
	
	public String getWhere(){
		String where = "";
		for(DataFilter d:filters){
			where+=" "+d.getAttribute()+"="+d.getValue()+" "+d.getConnector();
		}
		return where.substring(0, where.length()-filters.get(filters.size()-1).getConnector().length());
	}
	
}

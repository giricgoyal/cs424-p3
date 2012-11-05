package db;
import java.util.ArrayList;

import main.Utilities;

import processing.core.PApplet;
import types.DataCrashInstance;
import types.DataQuad;
import types.DataState;
import types.DataTriple;
import types.DataYearPair;
import de.bezier.data.sql.MySQL;
/**
 * Database class. To use it just create an instance and call query method you
 * are interested in.
 * 
 * @author Claudio
 * 
 */
public class DatabaseManager {
	private MySQL msql;

	/**
	 * Constructor. Create an instance of database.
	 * 
	 * @param context
	 */
	public DatabaseManager(PApplet context) {
		String user = "organic";
		String pass = "sharpcheddar";
		String database = "crash";
		String localhost = "inacarcrash.cnrtm99w3c2x.us-east-1.rds.amazonaws.com";
		msql = new MySQL(context, localhost, database, user, pass);
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * Retreive 
	 * 
	 * @return
	 */
	public ArrayList<DataCrashInstance> getCrashes(float latitude_min,float latitude_max,float longitude_min,float longitude_max, String filters) {
		ArrayList<DataCrashInstance> array = new ArrayList<DataCrashInstance>();
		if(filters.length()>1)  filters = " and "+filters;
		String _pie_chart = "day_of_week";
		String pie_chart = "";
		if(_pie_chart.length()>0) pie_chart = ", "+_pie_chart;
		String query;
		if (msql.connect()) {
			query = "select latitude, longitude, _year, id, "+
					"month, day_of_week, age, light_condition, alcohol_involved, body_type, " +
					"sex, hour, weather "+
					" from krashes" +
					" where latitude>"+
					latitude_min+" and latitude<" +
					latitude_max+" and longitude>"+
					longitude_min+" and longitude<"+
					longitude_max+filters;
							//and _year=2005"+
			System.out.println(query);
			msql.query(query);
			createArrayFromQueryCrashInstance(array, msql);
		} else {
		}
		return array;
	}
	
	/**
	 * Retreive 
	 * 
	 * @return
	 */
	public ArrayList<DataCrashInstance> getCrashesALL(String filters) {
		ArrayList<DataCrashInstance> array = new ArrayList<DataCrashInstance>();
		if(filters.length()>1)  filters = " and "+filters;
		String _pie_chart = "day_of_week";
		String pie_chart = "";
		if(_pie_chart.length()>0) pie_chart = ", "+_pie_chart;
		String query;
		
		int modularSample = (Utilities.program.maxZoom-Utilities.program.map.getZoom())*5;
		if (modularSample==0) modularSample=1;
		//modularSample=1;
		
		if (msql.connect()) {
			query = "select latitude, longitude, _year, id, "+
					"month, day_of_week, age, light_condition, alcohol_involved, body_type, " +
					"sex, hour, weather "+
					" from krashes" +
					" where id%"+modularSample+"=0"
					+filters;
							//and _year=2005"+
			System.out.println(query);
			msql.query(query);
			createArrayFromQueryCrashInstance(array, msql);
		} else {
		}
		return array;
	}
	/**
	 * Retreive 
	 * 
	 * @return
	 */
	public ArrayList<DataState> getStates(String word) {
		ArrayList<DataState> array = new ArrayList<DataState>();
		String query;
		if (msql.connect()) {
			query = " select name, lat, lon " +
					" from states " +
					" where name like \"%"+word+"%\"";
			msql.query(query);
			createArrayFromQueryState(array, msql);
		} else {
		}
		return array;
	}
	
	public ArrayList<DataYearPair> getHistogramCrashes(float latitude_min,float latitude_max,float longitude_min,float longitude_max,
			String filters){
		ArrayList<DataYearPair> array = new ArrayList<DataYearPair>();
		if(filters.length()>1)  filters = " and "+filters;
		String query;
		if (msql.connect()) {
			query = "select _year, count(id) from krashes " +
					" where latitude>"+
					latitude_min+" and latitude<" +
					latitude_max+" and longitude>"+
					longitude_min+" and longitude<"+
					longitude_max + " "+
					filters +
					" group by _year ";
			System.out.println(query);
			msql.query(query);
			createArrayFromQueryStateHC(array, msql);
		} else {
		}
		return array;
		}
	
	public ArrayList<DataYearPair> getHistogramFatalities(float latitude_min,float latitude_max,float longitude_min,float longitude_max,
			String filters){
		ArrayList<DataYearPair> array = new ArrayList<DataYearPair>();
		if(filters.length()>1)  filters = " and "+filters;
		String query;
		if (msql.connect()) {
			query = "select _year, sum(number_of_fatalities) from krashes " +
					" where latitude>"+
					latitude_min+" and latitude<" +
					latitude_max+" and longitude>"+
					longitude_min+" and longitude<"+
					longitude_max + " "+
					filters +
					" group by _year";
			System.out.println(query);
			msql.query(query);
			createArrayFromQueryStateHC(array, msql);
		} else {
		}
		return array;
		}
	
	public String getCrashData(int id){
		String query;
		if (msql.connect()) {
			query = "select k.day, month.month, _year,bt.value, states.name, hour.value,ntl.code, ts.speed, rsc.value, drf_1.value, drf_2.value,di.value, ai.value "  +
					"from krashes as k, month as month, states as states, hour as hour,	travel_speed as ts, roadway_surface_condition as rsc, number_of_travel_lanes as ntl, first_harmful_event as fhe, most_harmful_event as mhe, driver_related_factors_1 as drf_1, driver_related_factors_1 as drf_2, drug_involved as di, alcohol_involved as ai, body_type as bt " +
					"where k.month=month.code and k._state=states.id and k.hour=hour.code and " +
					"k.travel_speed=ts.code and k.roadway_surface_condition=rsc.code and k.driver_related_factors_1=drf_1.code and " +
					"k.driver_related_factors_2=drf_2.code and k.drug_involved=di.code and " +
					"k.alcohol_involved=ai.code and k.id="+id+" and bt.code=k.body_type and k.`number_of_travel_lanes`=ntl.code " +
					"group by k.id";
			System.out.println(query);
			msql.query(query);
		} else {
		}
		return generateData(msql);
		}
	
	private String generateData(MySQL msql) {
		msql.next();
		String day=msql.getString(1);
		String month=msql.getString(2);
		String year=msql.getString(3);
		String body_type=msql.getString(4);
		if(body_type.length()>21){
			body_type=body_type.substring(0, 20);
			body_type+="...";
		}
		String state=msql.getString(5);
		String hour = msql.getString(6);
		String ntl=msql.getString(7);

		if(ntl.length()>21){
			ntl=ntl.substring(0, 20);
			ntl+="...";
		}
		String travel_speed=msql.getString(8);
		String rsc=msql.getString(9);
		if(rsc.length()>21){
			rsc=rsc.substring(0, 20);
			rsc+="...";
		}
		String drf_1=msql.getString(10);
		if(drf_1.length()>21){
			drf_1=drf_1.substring(0, 20);
			drf_1+="...";
		}
		String drf_2=msql.getString(11);
		if(drf_2.length()>21){
			drf_2=drf_2.substring(0, 20);
			drf_2+="...";
		}
		String di=msql.getString(12);
		if(di.length()>21){
			di=di.substring(0, 20);
			di+="...";
		}
		String ai=msql.getString(13);
		if(ai.length()>21){
			ai=ai.substring(0, 20);
			ai+="...";
		}
		String data = "Crash Information:\n" +
				"Date:\t\t"+month+"/"+day+"/"+year+"\n"+
				"Hour:\t\t" + hour+"\n"+
				"Vehicle Type:\t" + body_type+"\n"+
				"Speed:\t\t" + travel_speed + "\n"+
				"Surface:\t"+ rsc + "\n"+
				"Notes:\t\t"+ drf_1+"\n\t "+
				"\t  and "+drf_2+"\n"+
				"Drug Involved:\t"+di+"\n"+
				"Alcohol:\t"+ai;
		return data;
	}
	/*
	 * 
	 */
	/**
	 * Retreive 
	 * 
	 * @return
	 */
	public ArrayList<DataTriple> get() {
		ArrayList<DataTriple> array = new ArrayList<DataTriple>();
		String query;
		if (msql.connect()) {
			query = "SELECT count(distinct _case), _year, _state from crashes "
					+ "group by _year";
			msql.query(query);
			createArrayFromQueryT(array, msql);
		} else {
		}
		return array;
	}
	
	/**
	 * 
	 */
	
	/**
	 * Converts a msql list into an array list.
	 * 
	 * @param array
	 * @param msql
	 * @return array containing the values retrieved from the database
	 */
	private void createArrayFromQueryQ(ArrayList<DataQuad> array,
			MySQL msql) {
		while (msql.next()) {
			array.add(new DataQuad(msql.getFloat("latitude"), msql.getFloat("longitude"), msql.getInt("id"), msql.getInt("_year"),msql.getInt(5)));
		}
	}
	
	private void createArrayFromQueryState(ArrayList<DataState> array,
			MySQL msql) {
		while (msql.next()) {
			array.add(new DataState(msql.getString("name"),msql.getFloat("lat"),msql.getFloat("lon")));
		}
	}
	
	private void createArrayFromQueryStateHC(ArrayList<DataYearPair> array,
			MySQL msql) {
		while (msql.next()) {
			array.add(new DataYearPair(msql.getInt("_year"),msql.getFloat(2)));
		}
	}
	
	private void createArrayFromQueryT(ArrayList<DataTriple> array,
			MySQL msql) {
		while (msql.next()) {
			array.add(new DataTriple(msql.getInt(1),msql.getInt(2),msql.getString(3)));
		}
	}
	
	private void createArrayFromQueryCrashInstance(ArrayList<DataCrashInstance> array,
			MySQL msql) {
		while (msql.next()) {
			array.add(new DataCrashInstance(msql.getFloat("latitude"),msql.getFloat("longitude"),
					msql.getInt("_year"),msql.getInt("id"),msql.getInt("month"),msql.getInt("day_of_week"),
					msql.getInt("age"),msql.getInt("light_condition"),msql.getInt("alcohol_involved"),
					msql.getInt("body_type"),msql.getInt("sex"),
					msql.getInt("hour"),msql.getInt("weather")));
		}
	}

	/**
	 * 
	 */
	private String join(ArrayList<String> a, String separator) {
		String out = "";
		for (String s : a) {
			out += s + separator;
		}
		return out.substring(0, out.length() - separator.length());
	}

	
}

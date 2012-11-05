package db;
import java.util.ArrayList;

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
					"month, day_of_week, age, light_condition, alchol_involved, body_type, " +
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
		if (msql.connect()) {
			query = "select latitude, longitude, _year, id, "+
					"month, day_of_week, age, light_condition, alchol_involved, body_type, " +
					"sex, hour, weather "+
					" from krashes" +
					" where id%300=1 "
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
					msql.getInt("_year"),msql.getInt("id"),msql.getString("month"),msql.getString("day_of_week"),
					msql.getInt("age"),msql.getString("light_condition"),msql.getString("alchol_involved"),
					msql.getInt("body_type"),msql.getString("sex"),
					msql.getInt("hour"),msql.getString("weather")));
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

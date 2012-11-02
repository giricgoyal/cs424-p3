package db;
import java.util.ArrayList;

import processing.core.PApplet;
import types.DataQuad;
import types.DataState;
import types.DataTriple;
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
	public ArrayList<DataQuad> getCrashes(float latitude_min,float latitude_max,float longitude_min,float longitude_max) {
		ArrayList<DataQuad> array = new ArrayList<DataQuad>();
		String query;
		if (msql.connect()) {
			query = "select latitude, longitude, _case, _year, day_of_week" +
					" from crashes" +
					" where latitude>"+
					latitude_min+" and latitude<" +
					latitude_max+" and longitude>"+
					longitude_min+" and longitude<"+
					longitude_max+//" and _year=2005"+
					" group by _case, _state, id" +
					" order by _year";
			System.out.println(query);
			msql.query(query);
			createArrayFromQueryQ(array, msql);
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
	 * Converts a msql list into an array list.
	 * 
	 * @param array
	 * @param msql
	 * @return array containing the values retrieved from the database
	 */
	private void createArrayFromQueryQ(ArrayList<DataQuad> array,
			MySQL msql) {
		while (msql.next()) {
			array.add(new DataQuad(msql.getFloat(1), msql.getFloat(2), msql.getInt(3), msql.getInt(4),msql.getInt(5)));
		}
	}
	
	private void createArrayFromQueryState(ArrayList<DataState> array,
			MySQL msql) {
		while (msql.next()) {
			array.add(new DataState(msql.getString("name"),msql.getFloat("lat"),msql.getFloat("lon")));
		}
	}
	
	private void createArrayFromQueryT(ArrayList<DataTriple> array,
			MySQL msql) {
		while (msql.next()) {
			array.add(new DataTriple(msql.getInt(1),msql.getInt(2),msql.getString(3)));
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

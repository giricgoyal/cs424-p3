import java.util.ArrayList;

import processing.core.PApplet;
import de.bezier.data.sql.*;

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
		String user = "root";
		String pass = "root";
		String database = "car-crash";
		msql = new MySQL(context, "localhost", database, user, pass);
	}

	/**
	 * Returns **** xD don't use this.
	 * 
	 * @return
	 */
	public ArrayList<DataTriple> get() {
		ArrayList<DataTriple> array = new ArrayList<DataTriple>();
		String query;
		if (msql.connect()) {
			query = "SELECT count(distinct istatenum), iaccyr, istatenum from crashes "
					+ "group by iaccyr, istatenum";
			msql.query(query);
			array = createArrayFromQuery(array, msql);
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
	private ArrayList<DataTriple> createArrayFromQuery(ArrayList<DataTriple> array,
			MySQL msql) {
		while (msql.next()) {
			array.add(new DataTriple(msql.getString(1), msql.getInt(2), msql
					.getString(3)));
		}
		return array;
	}
}

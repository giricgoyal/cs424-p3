package db;
/**
 * Data Triple is a data sample retrieved from the database.
 * It contains 3 values.
 * @author Claudio
 *
 */
public class DataTriple{
	int count;
	int year;
	String state;
	
	public DataTriple(int count, int year, String state){
		this.count=count;
		this.year=year;
		this.state=state;
	}
	
	public int getCount(){
		return count;
	}
	
	public int getYear(){
		return year;
	}
	
	public String getState(){
		return state;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setState(String state) {
		this.state = state;
	}
}

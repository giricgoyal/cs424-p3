/**
 * Data Triple is a data sample retrieved from the database.
 * It contains 3 values.
 * @author Claudio
 *
 */
public class DataTriple{
	String count;
	int year;
	String state;
	
	public DataTriple(String count, int year, String state){
		this.count=count;
		this.year=year;
		this.state=state;
	}
	
	public String getCount(){
		return count;
	}
	
	public int getYear(){
		return year;
	}
	
	public String getState(){
		return state;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setState(String state) {
		this.state = state;
	}
}

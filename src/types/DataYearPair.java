package types;

public class DataYearPair {
	private int year;
	private float value;
	public DataYearPair(int year, float value) {
		super();
		this.year = year;
		this.value = value;
	}
	public int getYear() {
		return year;
	}
	public float getValue() {
		return value;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public void setValue(float value) {
		this.value = value;
	}
	
}

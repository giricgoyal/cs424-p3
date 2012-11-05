package types;

public class DataPair {
	private String field;
	private float value;
	public DataPair(String field, float value) {
		super();
		this.field = field;
		this.value = value;
	}
	public String getField() {
		return field;
	}
	public float getValue() {
		return value;
	}
	public void setYear(String field) {
		this.field = field;
	}
	public void setValue(float value) {
		this.value = value;
	}
	
}

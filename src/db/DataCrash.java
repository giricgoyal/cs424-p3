package db;

public class DataCrash {
	private String databaseValue;
	private String toShowVaue;
	private	boolean on;
	
	public DataCrash(String databaseValue, String toShowVaue, boolean on) {
		super();
		this.databaseValue = databaseValue;
		this.toShowVaue = toShowVaue;
		this.on = on;
	}
	
	public String getDatabaseValue() {
		return databaseValue;
	}
	public String getToShowVaue() {
		return toShowVaue;
	}
	public boolean isOn() {
		return on;
	}
	public void setDatabaseValue(String databaseValue) {
		this.databaseValue = databaseValue;
	}
	public void setToShowVaue(String toShowVaue) {
		this.toShowVaue = toShowVaue;
	}
	public void setOn(boolean on) {
		this.on = on;
	}
	
	
}

package db;


public class DataFilter {
	String attribute;
	String value;
	String connector;
	
	public DataFilter(String attribute, String value, String connector) {
		super();
		this.attribute = attribute;
		this.value = value;
		this.connector = connector;
	}
	
	public String getAttribute() {
		return attribute;
	}

	public String getValue() {
		return value;
	}
	
	/**
	 * and, or
	 * @return
	 */
	public String getConnector(){
		return connector;
	}
}

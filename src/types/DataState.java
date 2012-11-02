package types;

public class DataState {
	private String name;
	private float latitude;
	private float longitude;
	
	public DataState(String name, float latitude, float longitude) {
		super();
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public String getName() {
		return name;
	}
	public float getLatitude() {
		return latitude;
	}
	public float getLongitude() {
		return longitude;
	}

}

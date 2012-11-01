package data;
/**
 * 
 * @author Claudio
 *
 */
public class DataQuad {
	float longitude;
	float latitude;
	int year;
	int _case;
	
	public DataQuad(float latitude, float longitude, int _case, int year) {
		this.longitude = longitude;
		this.latitude = latitude;
		this.year = year;
		this._case = _case;
	}
	public float getLongitude() {
		return longitude;
	}
	public float getLatitude() {
		return latitude;
	}
	public int getYear() {
		return year;
	}
	public int getCase() {
		return _case;
	}
	
}

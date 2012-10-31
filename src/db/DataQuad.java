package db;
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
	
	public DataQuad(float longitude, float latitude, int year, int _case) {
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
	public int get_case() {
		return _case;
	}
	
}

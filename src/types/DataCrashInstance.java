package types;

public class DataCrashInstance {
	private float latitude;
	private float longitude;
	private int _year;
	private int id;
	private String month;
	private String day_of_week;
	private String age;
	private String light_condition;
	private String alchol_involved;
	private String body_type;
	private String sex;
	private String hour;
	private String weather;

	public DataCrashInstance(float latitude, float longitude, int _year,
			int id, String month, String day_of_week, int age,
			String light_condition, String alchol_involved, int body_type,
			String sex, int hour, String weather) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this._year = _year;
		this.id = id;
		this.month = month;
		this.day_of_week = day_of_week;
		this.age = computeAge(age);
		this.light_condition = light_condition;
		this.alchol_involved = alchol_involved;
		this.body_type = computeBodyType(body_type);
		this.sex = sex;
		this.hour = computeHour(hour);
		this.weather = weather;
	}
	
	
	public float getLatitude() {
		return latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public int get_year() {
		return _year;
	}

	public int getId() {
		return id;
	}

	public String getMonth() {
		return month;
	}

	public String getDay_of_week() {
		return day_of_week;
	}

	public String getAge() {
		return age;
	}

	public String getLight_condition() {
		return light_condition;
	}

	public String getAlchol_involved() {
		return alchol_involved;
	}

	public String getBody_type() {
		return body_type;
	}

	public String getSex() {
		return sex;
	}

	public String getHour() {
		return hour;
	}

	public String getWeather() {
		return weather;
	}
	
	private String computeAge(int age){
		if(age >= 1 && age <= 20)
			return "Young";
		if(age >= 20 && age <=60)
			return "Adult";
		if(age >=60 )
			return "Elder";
		return "Young";
	}
	
	private String computeBodyType(int body_type){
		if(body_type == 1 && body_type == 2 && body_type == 3 && body_type == 4 && body_type == 5 && body_type == 6 && body_type == 7 && body_type == 8 && body_type == 9)
			return "Car";
		if(body_type == 6 && body_type == 11 && body_type == 16)
			return "Station Wagon";
		if(body_type == 20 && body_type == 21 && body_type == 22 && 
				body_type == 23 && body_type == 24 && body_type == 25 && body_type == 28 &&  
				body_type == 29 && body_type == 41 && body_type == 42 &&  
				body_type == 45 && body_type == 48 && body_type == 50 && body_type == 51 &&
				body_type == 58 && body_type == 59 && body_type == 60 &&
				body_type == 61 && body_type == 62 && body_type == 63 && body_type == 64 &&
				body_type == 65 && body_type == 66 && body_type == 71 &&
				body_type == 72 && body_type == 73 && body_type == 7)
			return "Trucks";
		if(body_type == 14 &&
				body_type == 15 &&
				body_type == 90 &&
				body_type == 91)
			return "Jeep";
		if(body_type == 80 && body_type == 81 && 
				 body_type == 81 && body_type == 82 && body_type == 83 && body_type == 88 && 
				 body_type == 89)
			return "Motorcycle";
		return "Car";
	}
	
	private String computeHour(int hour){
		if(hour>=1 && hour<= 6)
			return " [1-6] a.m.";
		if(hour>=7 && hour<= 12)
			return " [7-12] a.m.";
		if(hour>=13 && hour <= 18)
			return " [1-6] p.m.";
		if(hour >= 19 && hour <=24)
			return " [7-12] p.m.";
		return " [1-6] a.m.";
	}

}

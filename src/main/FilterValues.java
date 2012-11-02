package main;
import types.DataCrash;

public class FilterValues {
	
	public FilterValues(){
	}
	
	private DataCrash monthInstance1 = new DataCrash(" month = 1 ","January",false);
	private DataCrash monthInstance2= new DataCrash(" month = 2 ","February",false);
	private DataCrash monthInstance3 = new DataCrash(" month = 3 ","March",false);
	private DataCrash monthInstance4 = new DataCrash(" month = 4 ","April",false);
	private DataCrash monthInstance5 = new DataCrash(" month = 5 ","May",false);
	private DataCrash monthInstance6 = new DataCrash(" month = 6 ","June",false);
	private DataCrash monthInstance7 = new DataCrash(" month = 7 ","July",false);
	private DataCrash monthInstance8 = new DataCrash(" month = 8 ","August",false);
	private DataCrash monthInstance9 = new DataCrash(" month = 9 ","September",false);
	private DataCrash monthInstance10 = new DataCrash(" month = 10 ","October",false);
	private DataCrash monthInstance11 = new DataCrash(" month = 11 ","November",false);
	private DataCrash monthInstance12 = new DataCrash(" month = 12 ","December",false);

	private DataCrash dayOfWeekInstance1 = new DataCrash(" day_of_week = 1 ","Sunday",false);
	private DataCrash dayOfWeekInstance2 = new DataCrash(" day_of_week = 2 ","Monday",false);
	private DataCrash dayOfWeekInstance3 = new DataCrash(" day_of_week = 3 ","Tuesday",false);
	private DataCrash dayOfWeekInstance4 = new DataCrash(" day_of_week = 4 ","Wednesday",false);
	private DataCrash dayOfWeekInstance5 = new DataCrash(" day_of_week = 5 ","Thursday",false);
	private DataCrash dayOfWeekInstance6 = new DataCrash(" day_of_week = 6 ","Friday",false);
	private DataCrash dayOfWeekInstance7 = new DataCrash(" day_of_week = 7 ","Saturday",false);
	
	private DataCrash lightConditionInstace1 = new DataCrash(" light_condition = 1 ","Daylight",false);
	private DataCrash lightConditionInstace2 = new DataCrash(" light_condition = 2 ","Dark - Not Lighted",false);
	private DataCrash lightConditionInstace3 = new DataCrash(" light_condition = 3 ","Dark - Lighted",false);
	private DataCrash lightConditionInstace4 = new DataCrash(" light_condition = 4 ","Dawn",false);
	private DataCrash lightConditionInstace5 = new DataCrash(" light_condition = 5 ","Dusk",false);
	private DataCrash lightConditionInstace6 = new DataCrash(" light_condition = 6 ","Dark - Unknown Lighting",false);
	
	private DataCrash young = new DataCrash(" age >= 1 and age <= 20","Young",false);
	private DataCrash adult = new DataCrash(" age >= 20 and age <= 60","Adult",false);
	private DataCrash elder = new DataCrash(" age >= 60","Elder",false);
	
	private DataCrash alcholInvolvedInstance1 = new DataCrash(" alchol_involved = 0 ", " No ", false);
	private DataCrash alcholInvolvedInstance2 = new DataCrash(" alchol_involved = 1 ", " Yes ", true);
	
	private DataCrash sexInstance1 = new DataCrash(" alchol_involved = 1 ", " Male ", false);
	private DataCrash sexInstance2 = new DataCrash(" alchol_involved = 2 ", " Female ", true);
	
	private DataCrash bodyTypeInstance1 = new DataCrash(" body_type = 1 or body_type = 2 or body_type = 3 or" +
			"body_type = 4 or body_type = 5 or body_type = 6 or body_type = 7 or" +
			"body_type = 8 or body_type = 9 ","Car",false); 
	private DataCrash bodyTypeInstance2 = new DataCrash(" body_type = 6 or body_type = 11 or body_type = 16 " +
			"","Station Wagon",false); 
	DataCrash bodyTypeInstance3 = new DataCrash(" body_type = 20 or body_type = 21 or body_type = 22 or " +
			"body_type = 23 or body_type = 24 or body_type = 25 or body_type = 28 or " +
			"body_type = 29 or body_type = 41 or body_type = 42 or " +
			"body_type = 45 or body_type = 48 or body_type = 50 or body_type = 51 " +
			"body_type = 58 or body_type = 59 or body_type = 60 "+
			"body_type = 61 or body_type = 62 or body_type = 63 or body_type = 64 " +
			"body_type = 65 or body_type = 66 or body_type = 71 "+
			"body_type = 72 or body_type = 73 or body_type = 7 ","Trucks",false);
	private DataCrash bodyTypeInstance4 = new DataCrash(" body_type = 14 or body_type = 15 or" +
			" body_type = 90 or body_type = 91 ","Jeep",false);
	private DataCrash bodyTypeInstance5 = new DataCrash(" body_type = 80 or body_type = 81 or" +
			" body_type = 81 or body_type = 82 or body_type = 83 or body_type = 88 or" +
			" body_type = 89","Motorcycle",false);
	
	private DataCrash hourInstance1 = new DataCrash(" hour >= 1 and hour <= 6 ", " [1-6] a.m.", false);
	private DataCrash hourInstance2 = new DataCrash(" hour >= 7 and hour <=12 ", " [7-12] a.m.", false);
	private DataCrash hourInstance3 = new DataCrash(" hour >= 13 and hour <=18 ", " [1-6] p.m.", false);
	private DataCrash hourInstance4 = new DataCrash(" hour >= 19 and hour <=24 ", " [7-12] p.m.", false);

	private DataCrash weatherInstance1 = new DataCrash(" weather = 1 ", "Sunny", false);
	private DataCrash weatherInstance2 = new DataCrash(" weather = 2 ", "Rainy", false);
	private DataCrash weatherInstance3 = new DataCrash(" weather = 3 ", "Hail", false);
	private DataCrash weatherInstance4 = new DataCrash(" weather = 4 or weather = 11 ", "Snow", false);
	private DataCrash weatherInstance5 = new DataCrash(" weather = 5 ", "Fog", false);
	private DataCrash weatherInstance6 = new DataCrash(" weather = 6 ", "Windy", false);
	private DataCrash weatherInstance7 = new DataCrash(" weather = 10 ", "Cloudy", false);

	
	private DataCrash[] month = {
			monthInstance1,monthInstance2,monthInstance3,monthInstance4,monthInstance5,
			monthInstance6,monthInstance7,monthInstance8,monthInstance9,monthInstance10,
			monthInstance11,monthInstance12
			};
	private DataCrash[] day_of_week = {
			dayOfWeekInstance1,dayOfWeekInstance2,dayOfWeekInstance3,dayOfWeekInstance4,dayOfWeekInstance5,
			dayOfWeekInstance6,dayOfWeekInstance7
			};

	private DataCrash[] age = {
			young,adult,elder
	};

	private DataCrash[] light_condition = {
			lightConditionInstace1,lightConditionInstace2,lightConditionInstace3,
			lightConditionInstace4,lightConditionInstace5,lightConditionInstace6
	};
	
	private DataCrash[] alchol_involved = {
			alcholInvolvedInstance1,alcholInvolvedInstance2
	};
	
	private DataCrash[] body_type = {
			bodyTypeInstance1,bodyTypeInstance2,bodyTypeInstance3,bodyTypeInstance4,bodyTypeInstance5
	};
	
	private DataCrash[] sex = {
			sexInstance1,sexInstance2
	};
	
	
	private DataCrash[] hour = {
			hourInstance1,hourInstance2,hourInstance3,hourInstance4
	};
	
	private DataCrash[] weather = {
			weatherInstance1,weatherInstance2,weatherInstance3,weatherInstance4,
			weatherInstance5,weatherInstance6,weatherInstance7
	};
			
			
	public DataCrash[][] filtersValue = {
			month,
			day_of_week,
			age,
			light_condition,
			alchol_involved,
			body_type,
			sex,
			hour,
			weather
	};
	
	public String[] attributes={
			"Month",
			"Day of Week",
			"Age",
			"Light Condition",
			"Alchol Involved",
			"Vehicle Type",
			"Sex",
			"Hour",
			"Weather"
	};
}

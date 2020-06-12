package util;
import java.util.Calendar;

// Author: Bianco Simone	v1.5

public class Daytime {

	private byte day;
	private byte month;
	private int year;
	private byte hours;
	private byte minutes;
	private byte seconds;

	public Daytime(int day, int mth, int yrs) {

		setYear(yrs);
		setMonth(mth);
		setDay(day);
		setHours(0);
		setMinutes(0);
		setSeconds(0);
	}

	public Daytime(int day, int mth, int yrs, int hrs, int min) {

		setYear(yrs);
		setMonth(mth);
		setDay(day);
		setHours(hrs);
		setMinutes(min);
		setSeconds(0);
	}

	public Daytime(int day, int mth, int yrs, int hrs, int min, int sec) {

		setYear(yrs);
		setMonth(mth);
		setDay(day);
		setHours(hrs);
		setMinutes(min);
		setSeconds(sec);
	}

	public int getDay() {

		return day;
	}

	public int getMonth() {

		return month;
	}

	public int getYear() {

		return year;
	}

	public int getHours() {

		return hours;
	}

	public int getMinutes() {

		return minutes;
	}

	public int getSeconds() {

		return seconds;
	}

	public String getDate() {

		String date = "";

		if(day < 10) {
			date += "0"+day;
		}
		else date += day;

		date += "/";

		if(month < 10) {
			date += "0"+month;
		}
		else date += month;

		date += "/"+year;
		
		return date;
	}

	public String getTime() {

		String time = "";

		if(hours < 10) {
			time += "0"+hours;
		}
		else time += hours;

		time += ":";

		if(minutes < 10) {
			time += "0"+minutes;
		}
		else time += minutes;

		time += ":";

		if(seconds < 10) {
			time += "0"+seconds;
		}
		else time += seconds;

		return time;
	}

	public long getTimeInSeconds() {

		return (long)(hours*60+minutes)*60+seconds;
	}

	public static Daytime getNow() {

		Calendar c = Calendar.getInstance();

		return new Daytime(c.get(Calendar.DATE), c.get(Calendar.MONTH)+1,
				c.get(Calendar.YEAR), c.get(Calendar.HOUR_OF_DAY), 
				c.get(Calendar.MINUTE), c.get(Calendar.SECOND));
	}

	private void setDay(int day) {

		int thisMonth = getMonth();
		byte thisMonthMaxDay;

		// get month's max day
		if(thisMonth == 2) {	

			if(getYear() % 4 == 0) {

				thisMonthMaxDay = 29;
			}
			else thisMonthMaxDay = 28;
		}
		else if(thisMonth == 4 || thisMonth == 6 || thisMonth == 9 || thisMonth == 11) {

			thisMonthMaxDay = 30;
		}
		else thisMonthMaxDay = 31;

		// check if day is in this month
		if(day < 1 || day > thisMonthMaxDay) {	

			throw new IllegalArgumentException("Invalid day ("+day+"/"+getMonth()+"/"+getYear()+")");
		}
		else {

			this.day = (byte)day;
		}
	}

	private void setMonth(int month) {

		if(month < 1 || month > 12) {

			throw new IllegalArgumentException("Invalid month ("+month+")");
		}
		else {
			this.month = (byte)month;
		}
	}

	private void setYear(int year) {

		if(year < 0) {

			throw new IllegalArgumentException("Invalid year ("+year+")");
		}
		else {
			this.year = year;
		}
	}

	private void setHours(int hours) {

		if(hours < 0 || hours > 23) {

			throw new IllegalArgumentException("Invalid hour ("+hours+")");
		}
		else {
			this.hours = (byte)hours;
		}
	}

	private void setMinutes(int minutes) {

		if(minutes < 0 || minutes > 59) {

			throw new IllegalArgumentException("Invalid minutes ("+minutes+")");
		}
		else {
			this.minutes = (byte)minutes;
		}
	}

	private void setSeconds(int seconds) {

		if(seconds < 0 || seconds > 59) {

			throw new IllegalArgumentException("Invalid seconds ("+seconds+")");
		}
		else {
			this.seconds = (byte)seconds;
		}
	}

	public boolean isAfter(Daytime date) {

		boolean isAfter = false;
		
		if(getYear() > date.getYear()) {
			
			isAfter = true;
		}
		else if (getYear() == date.getYear() && getMonth() > date.getMonth()) {
			
			isAfter = true;
		}
		else if(getYear() == date.getYear() && getMonth() == date.getMonth() && getDay() > date.getDay()) {
			
			isAfter = true;
		}
		else if(getYear() == date.getYear() && getMonth() == date.getMonth() && getDay() == date.getDay()){
			
			isAfter = getTimeInSeconds() > date.getTimeInSeconds();
		}
		
		return isAfter;
	}

	public boolean isBefore(Daytime date) {

		boolean isBefore = false;
		
		if(getYear() < date.getYear()) {
			
			isBefore = true;
		}
		else if (getYear() == date.getYear() && getMonth() < date.getMonth()) {
			
			isBefore = true;
		}
		else if(getYear() == date.getYear() && getMonth() == date.getMonth() && getDay() < date.getDay()) {
			
			isBefore = true;
		}
		else if(getYear() == date.getYear() && getMonth() == date.getMonth() && getDay() == date.getDay()){
			
			isBefore = getTimeInSeconds() < date.getTimeInSeconds();
		}
		
		return isBefore;
	}

	@Override
	public String toString() {

		return getDate() + " " + getTime();
	}
}

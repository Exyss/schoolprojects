package archive;

import java.util.Vector;
import util.Daytime;

public class Archive {

	private Vector<Day> days = null;

	public Archive() {

		this.days = new Vector<Day>();
		addDay(getToday());
	}

	public Day getDay(int index) {

		//Vector.get(index) already throws an ArrayIndexOutOfBounds

		return days.get(index);
	}

	public Day getToday() {

		Daytime todayDate = Daytime.getNow();

		for(int i=0; i<getQuantity(); i++) {
			//search today

			if(getDay(i).getDate().equals(todayDate.getDate())){

				return getDay(i);	//if found return today
			}
		}

		//else create and return today
		Day today = new Day(todayDate);
		return today;
	}

	public int getQuantity() {

		return days.size();
	}

	public boolean isEmpty() {

		return days.isEmpty();
	}

	public void addDay(Day d) {

		if(isEmpty()) {

			days.add(d);
		}
		else {

			for(int i=0; i<getQuantity(); i++) {
				//check if this day already exists in this archive

				if(!d.isEmpty() && d.getDate().equals(getDay(i).getDate())) {

					throw new IllegalArgumentException(d.getDate()+" already exists in this archive");
				}
			}

			days.add(d);
		}
	}

	public void addActivity(Activity activity) {

		for(int i=0; i<getQuantity(); i++) {
			//check if this activity's deadline already exists in this archive

			if(activity.getDeadline().getDate().equals(getDay(i).getDate())) {

				getDay(i).addActivity(activity);
				return;
			}
		}

		Day d = new Day();
		d.addActivity(activity);
		addDay(d);
	}

	public Day removeDay(Day day) {

		for(int i=0; i<getQuantity(); i++) {
			
			if (getDay(i).toString().equals(day.toString())) {
				//find day
				return removeDay(i);
			}
		}
		return null;
	}

	public Day removeDay(int index) {

		return days.remove(index);
	}

	public Activity removeActivity(Activity activity) {

		for(int i=0; i<getQuantity(); i++) {
			//find day with same date

			for(int j=0; j<getDay(i).getQuantity(); j++) {
				//find activity

				if (getDay(i).getActivity(j).toString().equals(activity.toString())) {

					return getDay(i).removeActivity(j);
				}
			}
		}

		return null;
	}

	public void removeEmptyDays() {

		for(int i=0; i<getQuantity(); i++) {

			if(getDay(i).isEmpty() && !getDay(i).getDate().equals(getToday().getDate())){
				//today must never be removed
				removeDay(i);
			}
		}
	}


	public void sortByOldest() {

		removeEmptyDays();
		boolean moved = false;

		//modified bubble sort
		do {
			moved = false;

			for(int i=0; i<getQuantity()-1; i++) {

				//create temp daytimes to compare them
				String[] tmp = getDay(i).getDate().split("/");
				Daytime firstDay = new Daytime(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2]));
				tmp = getDay(i+1).getDate().split("/");
				Daytime secondDay = new Daytime(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2]));

				if(firstDay.isAfter(secondDay)){
					//if the i day is after the i+1 day, move the i day to the end
					addDay(removeDay(i));

					moved = true;
					break;
				}
			}
		}while(moved);
		

		//sort activities
		for(int i=0; i<getQuantity(); i++) {
			
			getDay(i).sortByOldest();
		}
	}

	public void sortByLatest() {

		removeEmptyDays();
		boolean moved = false;

		//modified bubble sort
		do {
			moved = false;

			for(int i=0; i<getQuantity()-1; i++) {

				//create temp daytimes to compare them
				String[] tmp = getDay(i).getDate().split("/");
				Daytime firstDay = new Daytime(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2]));
				tmp = getDay(i+1).getDate().split("/");
				Daytime secondDay = new Daytime(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2]));

				if(firstDay.isBefore(secondDay)){
					//if the i day is before the i+1 day, move the i day to the end

					addDay(removeDay(i));
					moved = true;
					break;
				}
			}
		}while(moved);
		
		//sort activities
		for(int i=0; i<getQuantity(); i++) {
			
			getDay(i).sortByLatest();
		}
	}

	public void empty() {

		Day keepTodayPointer = getToday();
		
		int initQuantity = getQuantity();
		// needed due to quantity reducing while removing elements

		for(int i=0; i<initQuantity; i++) {

			removeDay(0);
		}

		addDay(keepTodayPointer);
		//empty today
		getToday().empty();
	}

	@Override
	public String toString() {

		return "Quantity: " + getQuantity();
	}
}

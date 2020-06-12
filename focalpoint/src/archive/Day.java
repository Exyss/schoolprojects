package archive;

import java.util.Vector;

import util.Daytime;

public class Day {

	private Vector<Activity> activities = null;
	private String date = null;

	public Day() {

		this.activities = new Vector<Activity>();
	}

	public Day(Daytime date) {

		this.activities = new Vector<Activity>();
		this.date = date.getDate();
	}

	public Activity getActivity(int index) {

		//Vector.get(index) already throws an ArrayIndexOutOfBounds

		return activities.get(index);
	}

	public String getDate() {

		return date;
	}

	

	public int getQuantity() {

		return activities.size();
	}
	
	public int getCompletedInTime() {
		
		int completedInTime = 0;

		for(int i=0; i<getQuantity(); i++) {

			if(getActivity(i).getStatus().equals("Completed in time")) {

				completedInTime++;
			}
		}
		
		return completedInTime;
	}
	
	public float getProductivity() {

		if(isEmpty()) {

			return 0f;
		}
		else {

			
			return (float)getCompletedInTime()/getQuantity();
		}
	}

	public boolean isEmpty() {

		return activities.isEmpty();
	}

	public void addActivity(Activity a) {

		if(isEmpty()) {

			date = a.getDeadline().getDate();
			activities.add(a);
		}
		else {
			if(a.getDeadline().getDate().equals(getDate())) {
				//if the new activity has the same deadline date

				for (int i=0; i<getQuantity(); i++) {
					//check if the new activity is a duplicate

					if (a.toString().equals(getActivity(i).toString())) {

						throw new IllegalArgumentException("This activity already exists");
					}
				}
				
				activities.add(a);
			}
			else {

				throw new IllegalArgumentException("Invalid activity deadline (Date must be "+getDate()+")");
			}
		}
	}

	public Activity removeActivity(int index) {

		return activities.remove(index);
	}

	public void completeActivity(int index) {

		getActivity(index).complete();
	}

	public void setDescription(int index, String description) {

		getActivity(index).setDescription(description);
	}

	public void sortByOldest() {

		boolean moved = false;

		//modified bubble sort
		do {
			moved = false;

			for(int i=0; i<getQuantity()-1; i++) {

				if(getActivity(i).getDeadline().isAfter(getActivity(i+1).getDeadline())){
					//if the i activity is after the i+1 activity, move the i activity to the end

					addActivity(removeActivity(i));
					moved = true;
					break;
				}
			}
		}while(moved);
	}

	public void sortByLatest() {

		boolean moved = false;

		//modified bubble sort
		do {
			moved = false;

			for(int i=0; i<getQuantity()-1; i++) {

				if(getActivity(i).getDeadline().isBefore(getActivity(i+1).getDeadline())){
					//if the i activity is after the i+1 activity, move the i activity to the end

					addActivity(removeActivity(i));
					moved = true;
					break;
				}
			}
		}while(moved);
	}
	
	public void empty() {

		int initQuantity = getQuantity();
			// needed due to quantity reducing while removing elements
		
		for(int i=0; i<initQuantity; i++) {

			removeActivity(0);
		}
	}
	
	@Override
	public String toString() {

		return "Date: " + getDate()+", Total Activities: " + getQuantity()+", Completed in time: " + getCompletedInTime()+", Productivity: "+String.format("%.02f", getProductivity()*100)+"%";
	}
}

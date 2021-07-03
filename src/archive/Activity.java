package archive;

import util.Daytime;

public class Activity{

	private Daytime deadline = null;
	private Daytime completionDate = null;
	private String description = null;

	public Activity(Daytime deadline, Daytime complDate, String description) {

		setDeadline(deadline);
		setDescription(description);
		setCompletionDate(complDate);
	}

	public Activity(Daytime deadline, String description) {

		setDeadline(deadline);
		setDescription(description);
	}

	public Daytime getDeadline() {
		return deadline;
	}

	public Daytime getCompletionDate() {
		return completionDate;
	}

	public String getDescription() {

		return description;
	}

	public String getStatus() {

		String status = "";

		if(getCompletionDate() == null) {

			status = "Not completed";
		}
		else if(completionDate.isAfter(deadline)) {

			status = "Completed late";
		}
		else {
			status = "Completed in time";
		}

		return status;
	}

	private void setDeadline(Daytime deadline) {

		this.deadline = deadline;
	}

	private void setCompletionDate(Daytime complDate) {

		this.completionDate = complDate;
	}

	public void setDescription(String description) {

		if(getCompletionDate() == null) {

			if(description.isEmpty()) {

				throw new IllegalArgumentException("Description must not be empty");
			}
			else if(description.indexOf(",") != -1 || description.indexOf("<") != -1 || description.indexOf(">") != -1){

				throw new IllegalArgumentException("Description must not contain commas or angle brackets");
			}
			else {

				//set only if valid
				this.description = description;
			}
		}
		else {
			throw new IllegalStateException("Activity already completed");
		}


	}

	public void complete() {
		//if activity is completed before deadline

		if(getCompletionDate() == null) {
			setCompletionDate(Daytime.getNow());
		}
		else {
			throw new IllegalStateException("Activity already completed");
		}
	}

	@Override
	public String toString() {

		String toString = "Deadline: " + deadline;

		if(getCompletionDate() != null) {
			toString += ", Completed on: "+getCompletionDate();
		}

		return toString +=", Description: " + description + ", Status: "+getStatus();
	}
}

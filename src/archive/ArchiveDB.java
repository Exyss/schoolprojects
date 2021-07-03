package archive;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import util.Daytime;

public class ArchiveDB extends Archive{

	private File database = null;

	public ArchiveDB(){

		super();
		this.database = new File("data/archive.csv");

		//create archive if it doens't exist
		if(!database.exists()) {

			try {

				File folder = new File("data");
				if(!folder.exists() || !folder.isDirectory()) {

					folder.mkdir();
				}

				database.createNewFile();
				
			} catch (IOException e) {
				throw new IllegalStateException("Couldn't create archive");
			}
		}

		load();
	}

	private void load() {

		empty();

		String[] args = new String[13];	
		//dd, mm, yy, hh, min, sec, dd, mm, yy, hh, min, sec, text

		Activity activity;
		Daytime deadline;
		Daytime completionDate;

		//setup reader
		Scanner scan;
		try {
			scan = new Scanner(database, "UTF-8");
		}
		catch(Exception e) {
			throw new IllegalStateException("Archive not found");
		}


		//load archive
		try{
			while(scan.hasNextLine()) {

				args = scan.nextLine().split(",");

				deadline = new Daytime(
						Integer.valueOf(args[0]),	//day
						Integer.valueOf(args[1]),	//month
						Integer.valueOf(args[2]),	//year
						Integer.valueOf(args[3]),	//hours
						Integer.valueOf(args[4]),	//minutes
						Integer.valueOf(args[5]));	//seconds

				if(args[6].equals("")) {	//if doesn't have a completion date

					activity = new Activity(deadline, args[12]);
				}
				else {

					completionDate = new Daytime(
							Integer.valueOf(args[6]),	//day
							Integer.valueOf(args[7]),	//month
							Integer.valueOf(args[8]),	//year
							Integer.valueOf(args[9]),	//hours
							Integer.valueOf(args[10]),	//minutes
							Integer.valueOf(args[11]));	//seconds

					activity = new Activity(deadline, completionDate, args[12]);
				}

				addActivity(activity);

			}
			scan.close();
		}
		catch(IllegalArgumentException ie) {
			scan.close();
			throw new IllegalStateException("Unexpected parameter or duplicate activity");

		}
		catch(Exception e) {
			scan.close();
			throw new IllegalStateException("Couldn't load archive");
		}
	}

	public void save() throws IOException {

		//setup writer
		PrintWriter pw = new PrintWriter(database, "UTF-8");

		//write all activities
		removeEmptyDays();
		try{
			for(int i=0; i<getQuantity(); i++) {	// for each day

				for(int j=0; j<getDay(i).getQuantity(); j++) {	// for each activity in a day

					pw.println(getArgs(getDay(i).getActivity(j)));
				}
			}
			pw.close();
		}
		catch(Exception e) {
			pw.close();
		}
	}

	public void defragment() throws IOException{

		sortByLatest();
		save();
		load();
	}

	private String getArgs(Activity a) {

		String line = "";

		line += a.getDeadline().getDate().replaceAll("/", ",");
		line += ","+a.getDeadline().getTime().replaceAll(":", ",");

		if(a.getCompletionDate() == null) {
			line += ",,,,,,";	//write empty args
		}
		else {
			line += ","+a.getCompletionDate().getDate().replaceAll("/", ",");
			line += ","+a.getCompletionDate().getTime().replaceAll(":", ",");
		}

		line += ","+a.getDescription();

		return line;
	}
}

package main;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;

import archive.Activity;
import archive.Archive;
import archive.Day;
import util.Daytime;

public class ActivityReminder extends Thread{

	private Day today = null;
	private Archive archive = null;

	public ActivityReminder(Archive archive) {

		super();

		this.archive = archive;
		this.today = archive.getToday();
	}

	private void checkActivities() {

		Daytime now = Daytime.getNow();
		
		//check if date has changed
		if(!today.getDate().equals(now.getDate())) {
			
			this.today = archive.getToday();
		}
		
		Activity a;
		long diff = 0;

		for(int i=0; i<today.getQuantity(); i++) {

			a = today.getActivity(i);
			
			if(a.getStatus().equals("Not completed")) {
				
				diff = a.getDeadline().getTimeInSeconds() - now.getTimeInSeconds();
				//System.out.println(diff);
	
				if(diff > 9*60 && diff <= 10*60) {	//if imminent within 9-10 mins
	
					try {
						displayReminder(a);
					}
					catch(AWTException e) {
	
						System.err.println("Can't display reminder");
					}
				}
			}
		}
	}

	private void displayReminder(Activity a) throws AWTException {

		//send UI notification
		/*
		System.out.println("\n-----------------");
		System.out.println("REMINDER: \""+a.getText()+"\", Within: "+a.getDeadline());
		System.out.println("\n-----------------");
		 */

		//send Windows notification
		//create system tray icon
		SystemTray tray = SystemTray.getSystemTray();
		Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
		TrayIcon trayIcon = new TrayIcon(image, "Reminder");

		trayIcon.setImageAutoSize(true);
		trayIcon.setToolTip("Activity Reminder");

		//display notification
		tray.add(trayIcon);
		trayIcon.displayMessage("Expiring in 10 minutes", a.getDescription(), MessageType.INFO);
		tray.remove(trayIcon);
	}

	@Override
	public void run() {

		for(;;) {
			checkActivities();

			try {
				//System.out.println("SLEEP");
				sleep(60*1000);
			} catch (InterruptedException e) {

				return;	//end thread if it gets interrupted
			}
		}
	}
}

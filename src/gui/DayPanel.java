package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import archive.Day;
import main.FocalPoint;

@SuppressWarnings("serial")
public class DayPanel extends JPanel{

	private FocalPoint mainRef = null;
	private Day day = null;

	public DayPanel(FocalPoint mainRef, Day day) {

		this.mainRef = mainRef;
		this.day = day;

		this.setMaximumSize(new Dimension(950, 50));
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

		setInfo();
		setButtons();
	}

	private void setInfo() {

		JLabel date = new JLabel(day.getDate(), SwingConstants.CENTER);
		JLabel total = new JLabel(String.valueOf(day.getQuantity()), SwingConstants.CENTER);
		JLabel inTime = new JLabel(String.valueOf(day.getCompletedInTime()), SwingConstants.CENTER);
		JLabel productivity = new JLabel(String.format("%.02f", day.getProductivity()*100)+"%", SwingConstants.CENTER);

		//set fixed sizes
		mainRef.setFixedSize(date, new Dimension(100, 30));
		mainRef.setFixedSize(total, new Dimension(100, 30));
		mainRef.setFixedSize(inTime, new Dimension(100, 30));
		mainRef.setFixedSize(productivity, new Dimension(100, 30));

		this.add(date);
		this.add(total);
		this.add(inTime);
		this.add(productivity);
	}

	private void setButtons() {

		JButton remove;
		if(day.getDate().equals(mainRef.getDB().getToday().getDate())) {

			remove = new JButton("Empty");
		}
		else {
			remove = new JButton("Remove");
		}

		mainRef.setFixedSize(remove, new Dimension(90, 30));

		remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int confirm;

				if(day.getDate().equals(mainRef.getDB().getToday().getDate())) {
					//if is today
					confirm = JOptionPane.showOptionDialog(null, "Are you sure you want to empty this day?", "Remove confirmation", JOptionPane.YES_NO_OPTION,  JOptionPane.WARNING_MESSAGE, null, null, null);
				}
				else {
					confirm = JOptionPane.showOptionDialog(null, "Are you sure you want to remove this day?", "Remove confirmation", JOptionPane.YES_NO_OPTION,  JOptionPane.WARNING_MESSAGE, null, null, null);
				}

				if(confirm == 0)	removeDay();	//remove if yes was selected
			}});

		this.add(remove);

		//set colors
		remove.setBackground(new Color(191, 215, 234));
	}

	private void removeDay() {

		if(day.getDate().equals(mainRef.getDB().getToday().getDate())) {
			//if is today
			mainRef.getDB().getToday().empty();
		}
		else {
			mainRef.getDB().removeDay(day);
		}

		try{
			mainRef.getDB().defragment();
		}
		catch (IOException e) {

			throw new IllegalStateException("Couldn't defragment file");
		}

		mainRef.loadDayScroller();
	}
}
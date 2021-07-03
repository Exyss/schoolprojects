package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import archive.*;
import gui.*;

@SuppressWarnings("serial")
public class FocalPoint extends JFrame{

	private ArchiveDB db = null;
	private ActivityReminder ar = null;

	private String filter = null;

	public FocalPoint(String database){

		//set entities
		super("Focal Point");
		this.db = new ArchiveDB();
		this.ar = new ActivityReminder(db);
		ar.start();

		setClosingOperations();
		this.filter = "All days";

		//set frame
		this.setSize(1000, 600);
		this.setResizable(false);
		this.setLocation(175, 75);
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		loadActivityScroller();
		this.setVisible(true);

	}

	public ArchiveDB getDB() {

		return db;
	}

	public void loadActivityScroller() {

		this.getContentPane().removeAll();
		setTopPanel();
		setActivityScroller();
		this.getContentPane().revalidate();
		this.getContentPane().repaint();
	}

	public void loadEditingPanel(Activity toEdit) {

		this.getContentPane().removeAll();
		setTopPanel();
		setEditingPanel(toEdit);
		this.getContentPane().revalidate();
		this.getContentPane().repaint();
	}

	public void loadDayScroller() {

		this.getContentPane().removeAll();
		setTopPanel();
		setDayScroller();
		this.getContentPane().revalidate();
		this.getContentPane().repaint();

	}

	private void setTopPanel() {

		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 75, 15));
		topPanel.setSize(new Dimension(1000, 145));
		topPanel.setBorder(LineBorder.createBlackLineBorder());

		//set filter
		JComboBox<String> avaibleDays = new JComboBox<String>();
		avaibleDays.addItem("All days");

		db.removeEmptyDays();
		boolean doesStillExist = false;
		for(int i=0; i<db.getQuantity(); i++) {
			//add all existing days
			avaibleDays.addItem(db.getDay(i).getDate());

			if (db.getDay(i).getDate().equals(filter)) 		doesStillExist = true;
		}
		if (doesStillExist) {
			//if the old filter still exists
			avaibleDays.setSelectedItem(filter);
		}
		else {
			avaibleDays.setSelectedItem("All days");
		}

		avaibleDays.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				filter = (String)avaibleDays.getSelectedItem();
				loadActivityScroller();
			}});

		//set show productivity panel button
		JButton showProdButton = new JButton("Show productivity");
		showProdButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				loadDayScroller();
			}});

		//set new activity button
		JButton newActButton = new JButton("New activity");
		newActButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				loadEditingPanel(null);
			}});

		//set fixed sizes
		setFixedSize(avaibleDays, new Dimension(150, 30));
		setFixedSize(showProdButton, new Dimension(150, 30));
		setFixedSize(newActButton, new Dimension(150, 30));

		topPanel.add(avaibleDays);
		topPanel.add(showProdButton);
		topPanel.add(newActButton);

		this.getContentPane().add(topPanel);

		//set colors
		topPanel.setBackground(new Color(34, 94, 106));
		avaibleDays.setBackground(new Color(191, 215, 234));
		newActButton.setBackground(new Color(191, 215, 234));
		showProdButton.setBackground(new Color(191, 215, 234));
		avaibleDays.setBorder(LineBorder.createBlackLineBorder());
		newActButton.setBorder(LineBorder.createBlackLineBorder());
		showProdButton.setBorder(LineBorder.createBlackLineBorder());
	}

	private void setActivityScroller() {

		ActivityScroller as = new ActivityScroller(this, filter);
		this.getContentPane().add(as);
	}

	private void setDayScroller() {

		DayScroller pp = new DayScroller(this);
		this.getContentPane().add(pp);
	}

	private void setEditingPanel(Activity toEdit) {

		EditingPanel ep = new EditingPanel(this, toEdit);
		this.getContentPane().add(ep);
	}

	public void setFixedSize(Component item, Dimension fixedSize) {

		item.setMaximumSize(fixedSize);
		item.setMinimumSize(fixedSize);
		item.setPreferredSize(fixedSize);
	}

	private void setClosingOperations() {

		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				int confirm = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?", "Close program", JOptionPane.YES_NO_OPTION,  JOptionPane.WARNING_MESSAGE, null, null, null);
				if(confirm == 0)	onExit();	//exit if yes was selected
			}});
	}

	private void onExit() {

		try{
			db.save();
			ar.interrupt();
			System.exit(0);
		}
		catch(Exception ex) {
			System.err.println(ex.getMessage());
		}
	}


	public static void main(String[] args){

		new FocalPoint("activities");
	}
}
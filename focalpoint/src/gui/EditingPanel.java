package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

import archive.Activity;
import main.FocalPoint;
import util.Daytime;

@SuppressWarnings("serial")
public class EditingPanel extends JPanel{

	private FocalPoint mainRef = null;	
	private Activity toEdit = null;
	private boolean isCreating;

	private JComboBox<String> dd = null;
	private JComboBox<String> mm = null;
	private JComboBox<String> yy = null;
	private JComboBox<String> hh = null;
	private JComboBox<String> min = null;
	private JComboBox<String> sec = null;
	private JTextField desc = null;


	public EditingPanel(FocalPoint mainRef, Activity toEdit) {

		this.mainRef = mainRef;
		this.toEdit = toEdit;

		if(toEdit == null) {
			this.isCreating = true;
		}
		else {
			this.isCreating = false;
		}

		this.setPreferredSize(new Dimension(1000, 455));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.add(Box.createRigidArea(new Dimension(1000, 20)));
		setFirstPanel();
		setSecondPanel();
		setThirdPanel();
		setInitArgs();
		this.add(Box.createRigidArea(new Dimension(1000, 50)));
	}

	private void setFirstPanel() {

		//set date panel
		JPanel firstPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 70, 10));
		
		//set panel label
		JPanel labelPanel = new  JPanel(new FlowLayout(FlowLayout.CENTER, 70, 5));
		if (isCreating) {
			labelPanel.add(new JLabel("Set new activity deadline", SwingConstants.CENTER));
		}
		else labelPanel.add(new JLabel("Edit activity deadline", SwingConstants.CENTER));
		
		//set day field
		dd = new JComboBox<String>();
		for(int i=1; i<=31; i++) {

			if(i < 10) {
				dd.addItem("0"+i);
			}
			else {
				dd.addItem(""+i);
			}
		}
		JPanel ddPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		JLabel ddLabel = new JLabel("Day", SwingConstants.CENTER);
		mainRef.setFixedSize(ddPanel, new Dimension(70, 60));
		mainRef.setFixedSize(ddLabel, new Dimension(70, 30)); 
		mainRef.setFixedSize(dd, new Dimension(70, 30)); 
		ddPanel.add(ddLabel);
		ddPanel.add(dd);

		//set month field
		mm = new JComboBox<String>();
		for(int i=1; i<=12; i++) {

			if(i < 10) {
				mm.addItem("0"+i);
			}
			else {
				mm.addItem(""+i);
			}
		}
		//set dd max day
		mm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dd.removeItem("31");
				dd.removeItem("30");
				dd.removeItem("29");

				int selectedMM = Integer.parseInt((String)mm.getSelectedItem());
				if(selectedMM == 4 || selectedMM == 6 || selectedMM == 9 || selectedMM == 11) {
					dd.addItem("29");
					dd.addItem("30");
				}
				else if(selectedMM == 2 && (Integer.parseInt((String)yy.getSelectedItem()) % 4 == 0)) {
					dd.addItem("29");
				}
				else {
					dd.addItem("29");
					dd.addItem("30");
					dd.addItem("31");
				}
			}});
		JPanel mmPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		JLabel mmLabel = new JLabel("Month", SwingConstants.CENTER);
		mainRef.setFixedSize(mmPanel, new Dimension(70, 60));
		mainRef.setFixedSize(mmLabel, new Dimension(70, 30)); 
		mainRef.setFixedSize(mm, new Dimension(70, 30)); 
		mmPanel.add(mmLabel);
		mmPanel.add(mm);

		//set year field
		yy = new JComboBox<String>();
		int currentYY = Daytime.getNow().getYear();
		for(int i=currentYY; i<=currentYY+10; i++) {

			yy.addItem(""+i);
		}
		JPanel yyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		JLabel yyLabel = new JLabel("Year", SwingConstants.CENTER);
		mainRef.setFixedSize(yyPanel, new Dimension(70, 60));
		mainRef.setFixedSize(yyLabel, new Dimension(70, 30)); 
		mainRef.setFixedSize(yy, new Dimension(70, 30)); 
		yyPanel.add(yyLabel);
		yyPanel.add(yy);

		firstPanel.add(ddPanel);
		firstPanel.add(mmPanel);
		firstPanel.add(yyPanel);

		this.add(labelPanel);
		this.add(firstPanel);
	}

	private void setSecondPanel() {

		//set time panel
		JPanel secondPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 70, 10));

		//set hour field
		hh = new JComboBox<String>();
		for(int i=0; i<=23; i++) {

			if(i < 10) {
				hh.addItem("0"+i);
			}
			else {
				hh.addItem(""+i);
			}
		}
		JPanel hhPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		JLabel hhLabel = new JLabel("Hours", SwingConstants.CENTER);
		mainRef.setFixedSize(hhPanel, new Dimension(70, 60));
		mainRef.setFixedSize(hhLabel, new Dimension(70, 30)); 
		mainRef.setFixedSize(hh, new Dimension(70, 30)); 
		hhPanel.add(hhLabel);
		hhPanel.add(hh);

		//set minutes field
		min = new JComboBox<String>();
		for(int i=0; i<=59; i++) {

			if(i < 10) {
				min.addItem("0"+i);
			}
			else {
				min.addItem(""+i);
			}
		}
		JPanel minPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		JLabel minLabel = new JLabel("Minutes", SwingConstants.CENTER);
		mainRef.setFixedSize(minPanel, new Dimension(70, 60));
		mainRef.setFixedSize(minLabel, new Dimension(70, 30)); 
		mainRef.setFixedSize(min, new Dimension(70, 30)); 
		minPanel.add(minLabel);
		minPanel.add(min);

		//set seconds field
		sec = new JComboBox<String>();
		for(int i=0; i<=59; i++) {

			if(i < 10) {
				sec.addItem("0"+i);
			}
			else {
				sec.addItem(""+i);
			}
		}
		JPanel secPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		JLabel secLabel = new JLabel("Seconds", SwingConstants.CENTER);
		mainRef.setFixedSize(secPanel, new Dimension(70, 60));
		mainRef.setFixedSize(secLabel, new Dimension(70, 30)); 
		mainRef.setFixedSize(sec, new Dimension(70, 30)); 
		secPanel.add(secLabel);
		secPanel.add(sec);

		secondPanel.add(hhPanel);
		secondPanel.add(minPanel);
		secondPanel.add(secPanel);

		this.add(secondPanel);
	}

	private void setThirdPanel() {

		JPanel thirdPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));

		//set desc field
		desc = new JTextField();
		desc.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {}
			public void focusGained(FocusEvent e) {	desc.selectAll();
			}});
		JPanel descPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		JLabel descLabel = new JLabel("Description", SwingConstants.CENTER);
		mainRef.setFixedSize(descPanel, new Dimension(400, 60));
		mainRef.setFixedSize(descLabel, new Dimension(400, 30)); 
		mainRef.setFixedSize(desc, new Dimension(400, 30)); 
		descPanel.add(descLabel);
		descPanel.add(desc);

		thirdPanel.add(descPanel);

		//set buttons
		JButton confirmButton = new JButton("Confirm");
		mainRef.setFixedSize(confirmButton, new Dimension(130, 30));
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editActivity();
			}});

		JButton cancelButton = new JButton("Cancel");
		mainRef.setFixedSize(cancelButton, new Dimension(130, 30));
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainRef.loadActivityScroller();
			}});

		Box buttons = Box.createHorizontalBox();
		buttons.add(confirmButton);
		buttons.add(cancelButton);

		this.add(thirdPanel);
		this.add(buttons);

		//set colors
		confirmButton.setBackground(new Color(191, 215, 234));
		cancelButton.setBackground(new Color(191, 215, 234));
	}


	private void setInitArgs() {

		//set default args
		if (isCreating) {
			Daytime today = Daytime.getNow();

			//month
			if(today.getMonth() < 10) {
				mm.setSelectedItem("0"+today.getMonth());
			}
			else mm.setSelectedItem(""+today.getMonth());
			
			//year
			yy.setSelectedItem(""+today.getYear());

			//day	-	Day must be set as last due to months having different lengths
			if(today.getDay() < 10) {
				dd.setSelectedItem("0"+today.getDay());
			}
			else dd.setSelectedItem(""+today.getDay());
			
			//time
			hh.setSelectedItem("12");
			min.setSelectedItem("00");
			sec.setSelectedItem("00");
			
			desc.setText("Add description...");
		}
		//set old args
		else {
			Daytime oldDeadline = toEdit.getDeadline();

			//month
			if(oldDeadline.getMonth() < 10) {
				mm.setSelectedItem("0"+oldDeadline.getMonth());
			}
			else mm.setSelectedItem(""+oldDeadline.getMonth());

			//year
			if(oldDeadline.getYear() < 10) {
				yy.setSelectedItem("0"+oldDeadline.getYear());
			}
			else yy.setSelectedItem(""+oldDeadline.getYear());
			
			//day	-	Day must be set as last due to months having different lengths
			if(oldDeadline.getDay() < 10) {
				dd.setSelectedItem("0"+oldDeadline.getDay());
			}
			else dd.setSelectedItem(""+oldDeadline.getDay());

			//hours
			if(oldDeadline.getHours() < 10) {
				hh.setSelectedItem("0"+oldDeadline.getHours());
			}
			else hh.setSelectedItem(""+oldDeadline.getHours());

			//minutes
			if(oldDeadline.getMinutes() < 10) {
				min.setSelectedItem("0"+oldDeadline.getMinutes());
			}
			else min.setSelectedItem(""+oldDeadline.getMinutes());

			//seconds
			if(oldDeadline.getSeconds() < 10) {
				sec.setSelectedItem("0"+oldDeadline.getSeconds());
			}
			else sec.setSelectedItem(""+oldDeadline.getSeconds());
			
			desc.setText(toEdit.getDescription());
		}
	}

	private void editActivity() {

		Activity newActivity = null;
		try{

			if(desc.getText().length() > 50) {
				throw new IllegalArgumentException("Max 50 chars");
			}			

			Daytime newDeadline = new Daytime(
					Integer.parseInt((String)dd.getSelectedItem()),
					Integer.parseInt((String)mm.getSelectedItem()),
					Integer.parseInt((String)yy.getSelectedItem()),
					Integer.parseInt((String)hh.getSelectedItem()),
					Integer.parseInt((String)min.getSelectedItem()),
					Integer.parseInt((String)sec.getSelectedItem()));

			newActivity = new Activity(newDeadline, desc.getText());


			if(!isCreating)		mainRef.getDB().removeActivity(toEdit);
			mainRef.getDB().addActivity(newActivity);
			mainRef.getDB().defragment();
			mainRef.loadActivityScroller();
		}
		catch(NumberFormatException ne) {
			JOptionPane.showMessageDialog(this, "Wrong number format", "Error", JOptionPane.ERROR_MESSAGE);;
		}
		catch(IllegalArgumentException ie) {
			JOptionPane.showMessageDialog(this, ie.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);;
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);;
		}
	}
}

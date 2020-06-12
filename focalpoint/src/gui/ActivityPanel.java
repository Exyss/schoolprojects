package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import archive.Activity;
import main.FocalPoint;

@SuppressWarnings("serial")
public class ActivityPanel extends JPanel{

	private FocalPoint mainRef = null;
	private Activity activity = null;

	private JLabel deadline = null;
	private JLabel completion = null;
	private JLabel status = null;
	private JLabel description= null;

	public ActivityPanel(FocalPoint mainRef, Activity activity) {

		this.mainRef = mainRef;
		this.activity = activity;

		this.setMaximumSize(new Dimension(950, 50));
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

		setInfo();
		setButtons();

		//set colors
		//this.setBackground(new Color(253, 252, 220));
	}

	private void setInfo() {

		deadline = new JLabel(activity.getDeadline().toString(), SwingConstants.CENTER);

		if (activity.getCompletionDate() == null) {
			completion = new JLabel(activity.getStatus(), SwingConstants.CENTER);
		}
		else {
			completion = new JLabel(activity.getCompletionDate().toString(), SwingConstants.CENTER);
		}

		status = new JLabel(activity.getStatus(), SwingConstants.CENTER);
		description = new JLabel(activity.getDescription(), SwingConstants.CENTER);

		//set fixed sizes
		mainRef.setFixedSize(deadline, new Dimension(130, 30));
		mainRef.setFixedSize(completion, new Dimension(130, 30));
		mainRef.setFixedSize(status, new Dimension(130, 30));
		mainRef.setFixedSize(description, new Dimension(200, 30));

		this.add(deadline);
		this.add(completion);
		this.add(status);
		this.add(description);
	}

	private void setButtons() {

		JButton complete = new JButton("Complete");
		JButton edit = new JButton("Edit");
		JButton remove = new JButton("Remove");

		//set fixed sizes
		mainRef.setFixedSize(complete, new Dimension(90, 30));
		mainRef.setFixedSize(edit, new Dimension(90, 30));
		mainRef.setFixedSize(remove, new Dimension(90, 30));

		complete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				completeActivity();
			}
		});

		edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editActivity();
			}
		});

		remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int confirm = JOptionPane.showOptionDialog(null, "Are you sure you want to remove this activity?", "Remove confirmation", JOptionPane.YES_NO_OPTION,  JOptionPane.WARNING_MESSAGE, null, null, null);
				if(confirm == 0)	removeActivity();	//remove if yes was selected
			}
		});


		if(activity.getCompletionDate() != null) {

			this.add(Box.createRigidArea(new Dimension(90, 30)));
			this.add(remove);
			this.add(Box.createRigidArea(new Dimension(90, 30)));
		}
		else {
			this.add(complete);
			this.add(remove);
			this.add(edit);
		}

		//set colors
		complete.setBackground(new Color(191, 215, 234));
		remove.setBackground(new Color(191, 215, 234));
		edit.setBackground(new Color(191, 215, 234));
	}


	private void completeActivity() {
		activity.complete();
		JOptionPane.showMessageDialog(this, "Activity completed");

		completion.setText(activity.getCompletionDate().toString());
		status.setText(activity.getStatus());

		try{
			mainRef.getDB().save();
		}
		catch(Exception ex) {
			System.err.println(ex.getMessage());
		}
		mainRef.loadActivityScroller();
	}

	private void editActivity() {

		mainRef.loadEditingPanel(this.activity);
	}

	private void removeActivity() {

		mainRef.getDB().removeActivity(activity);

		try{
			mainRef.getDB().defragment();
		}
		catch (IOException e) {

			throw new IllegalStateException("Couldn't defragment file");
		}

		//set all fields to null
		activity = null;
		deadline = null;
		status = null;
		completion = null;
		description = null;

		mainRef.loadActivityScroller();
	}
}
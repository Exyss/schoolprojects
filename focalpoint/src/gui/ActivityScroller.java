package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import main.FocalPoint;

@SuppressWarnings("serial")
public class ActivityScroller extends JPanel{

	private FocalPoint mainRef = null;
	private String filter = null;

	public ActivityScroller(FocalPoint mainRef, String filter) {

		this.mainRef = mainRef;
		this.filter = filter;

		this.setSize(1000, 455);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		setLabelPanel();
		setScroller();
	}

	private void setLabelPanel() {

		JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		labelPanel.setMaximumSize(new Dimension(1000, 55));
		labelPanel.setBorder(LineBorder.createBlackLineBorder());

		JLabel deadline = new JLabel("Deadline", SwingConstants.CENTER);
		JLabel completion = new JLabel("Completed on", SwingConstants.CENTER);
		JLabel status = new JLabel("Status", SwingConstants.CENTER);
		JLabel description = new JLabel("Description", SwingConstants.CENTER);
		JLabel actions = new JLabel("Actions", SwingConstants.CENTER);

		//set fixed sizes
		mainRef.setFixedSize(deadline, new Dimension(130, 30));
		mainRef.setFixedSize(completion, new Dimension(130, 30));
		mainRef.setFixedSize(status, new Dimension(130, 30));
		mainRef.setFixedSize(description, new Dimension(200, 30));
		mainRef.setFixedSize(actions, new Dimension(290, 30));

		labelPanel.add(deadline);
		labelPanel.add(completion);
		labelPanel.add(status);
		labelPanel.add(description);
		labelPanel.add(actions);
		labelPanel.add(Box.createRigidArea(new Dimension (10, 0)));

		this.add(labelPanel, BorderLayout.NORTH);
		
		//set colors
		labelPanel.setBackground(new Color(34, 94, 106));
		deadline.setForeground(Color.WHITE);
		completion.setForeground(Color.WHITE);
		status.setForeground(Color.WHITE);
		description.setForeground(Color.WHITE);
		actions.setForeground(Color.WHITE);
	}

	private void setScroller() {

		//set scrollbar
		JPanel activityContainer = new JPanel();
		activityContainer.setAutoscrolls(true);

		JScrollPane aPanelScroller = new JScrollPane(activityContainer);
		aPanelScroller.setPreferredSize(new Dimension(1000, 470));
		aPanelScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		aPanelScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		aPanelScroller.getVerticalScrollBar().setUnitIncrement(17);

		if(!mainRef.getDB().isEmpty()) {

			for(int i=0; i<mainRef.getDB().getQuantity(); i++) {
				for(int j=0; j<mainRef.getDB().getDay(i).getQuantity(); j++) {

					if(filter.equals("All days") || mainRef.getDB().getDay(i).getActivity(j).getDeadline().getDate().toString().equals(filter)) {
						//if the filter equals "All days" or this day's date

						activityContainer.add(new ActivityPanel(mainRef, mainRef.getDB().getDay(i).getActivity(j)));
						//activityContainer.add(new JSeparator());
					}
				}
			}
		}

		activityContainer.setLayout(new BoxLayout(activityContainer, BoxLayout.Y_AXIS));

		if (activityContainer.getComponentCount() == 0) {
			//if no activities where added

			activityContainer.add(new JLabel("Oops.. Looks like you have no activities scheduled", JLabel.CENTER));
		}

		this.add(aPanelScroller, BorderLayout.SOUTH);
		
		//set colors
		//activityContainer.setBackground(new Color(253, 252, 220));
	}
}

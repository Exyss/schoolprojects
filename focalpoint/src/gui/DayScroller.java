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
public class DayScroller extends JPanel{

	private FocalPoint mainRef = null;

	public DayScroller(FocalPoint mainRef) {

		this.mainRef = mainRef;

		this.setSize(1000, 455);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		setLabelPanel();
		setScroller();
	}

	private void setLabelPanel() {

		JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		labelPanel.setMaximumSize(new Dimension(1000, 55));
		labelPanel.setBorder(LineBorder.createBlackLineBorder());

		JLabel date = new JLabel("Date", SwingConstants.CENTER);
		JLabel total = new JLabel("Total Activities", SwingConstants.CENTER);
		JLabel inTime = new JLabel("Completed", SwingConstants.CENTER);
		JLabel productivity = new JLabel("Productivity", SwingConstants.CENTER);
		JLabel actions = new JLabel("Actions", SwingConstants.CENTER);

		//set fixed sizes
		mainRef.setFixedSize(date, new Dimension(100, 30));
		mainRef.setFixedSize(total, new Dimension(100, 30));
		mainRef.setFixedSize(inTime, new Dimension(100, 30));
		mainRef.setFixedSize(productivity, new Dimension(100, 30));
		mainRef.setFixedSize(actions, new Dimension(90, 30));

		labelPanel.add(date);
		labelPanel.add(total);
		labelPanel.add(inTime);
		labelPanel.add(productivity);
		labelPanel.add(actions);
		labelPanel.add(Box.createRigidArea(new Dimension (10, 0)));

		this.add(labelPanel, BorderLayout.NORTH);

		//set colors
		labelPanel.setBackground(new Color(34, 94, 106));
		date.setForeground(Color.WHITE);
		total.setForeground(Color.WHITE);
		inTime.setForeground(Color.WHITE);
		productivity.setForeground(Color.WHITE);
		actions.setForeground(Color.WHITE);
	}

	private void setScroller() {

		//set scrollbar
		JPanel dayContainer = new JPanel();
		dayContainer.setAutoscrolls(true);

		JScrollPane dPanelScroller = new JScrollPane(dayContainer);
		dPanelScroller.setPreferredSize(new Dimension(1000, 470));
		dPanelScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		dPanelScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		dPanelScroller.getVerticalScrollBar().setUnitIncrement(17);

		if(!mainRef.getDB().isEmpty()) {

			mainRef.getDB().sortByLatest();
			
			for(int i=0; i<mainRef.getDB().getQuantity(); i++) {

				dayContainer.add(new DayPanel(mainRef, mainRef.getDB().getDay(i)));
			}
		}

		dayContainer.setLayout(new BoxLayout(dayContainer, BoxLayout.Y_AXIS));

		if (dayContainer.getComponentCount() == 0) {
			//if no activities where added

			dayContainer.add(new JLabel("Oops.. Looks like you have no past activities", JLabel.CENTER));
		}

		this.add(dPanelScroller, BorderLayout.SOUTH);
	}
}

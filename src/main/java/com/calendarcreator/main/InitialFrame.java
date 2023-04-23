package com.calendarcreator.main;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.text.ParseException;
import java.util.*;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.calendarcreator.core.CreateCalendar;
import com.calendarcreator.core.CreateXLS;
import com.calendarcreator.model.WeeklyUnit;
import com.calendarcreator.model.Course;

public class InitialFrame extends JFrame {

	private javax.swing.JCheckBox jCheckFri;
	private javax.swing.JCheckBox jCheckMon;
	private javax.swing.JCheckBox jCheckSat;
	private javax.swing.JCheckBox jCheckThu;
	private javax.swing.JCheckBox jCheckTue;
	private javax.swing.JCheckBox jCheckWed;
	private javax.swing.JTextArea jTextAreaUnits;
	private javax.swing.JTextField jTextCourse;
	private javax.swing.JTextField jTextYearStart;
	private List<Integer> deliveryWeekDates;
	private javax.swing.JTextField jTextYearEnd;

	public InitialFrame() {
		initComponents();
		setTitle("Create Calendar");
	}

	private void initComponents() {

		javax.swing.JLabel jLabelCourse = new javax.swing.JLabel();
		jTextCourse = new javax.swing.JTextField();
		javax.swing.JLabel jLabelYear = new javax.swing.JLabel();
		jTextYearStart = new javax.swing.JTextField();
		javax.swing.JLabel jLabelDeliveryDay = new javax.swing.JLabel();
		javax.swing.JLabel jLabelUnits = new javax.swing.JLabel();
		javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
		jTextAreaUnits = new javax.swing.JTextArea();
		javax.swing.JButton jButtonCreate = new javax.swing.JButton();
		jCheckMon = new javax.swing.JCheckBox();
		jCheckTue = new javax.swing.JCheckBox();
		jCheckWed = new javax.swing.JCheckBox();
		jCheckFri = new javax.swing.JCheckBox();
		jCheckThu = new javax.swing.JCheckBox();
		jCheckSat = new javax.swing.JCheckBox();
		javax.swing.JLabel jLabelTo = new javax.swing.JLabel();
		jTextYearEnd = new javax.swing.JTextField();
		deliveryWeekDates = new LinkedList<>();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jLabelCourse.setText("Course:");

		jLabelYear.setText("Year:");

		jLabelDeliveryDay.setText("Delivery:");

		jLabelUnits.setText("Units:");

		jTextAreaUnits.setColumns(20);
		jTextAreaUnits.setRows(5);
		jScrollPane1.setViewportView(jTextAreaUnits);

		jButtonCreate.setText("Create");
		jButtonCreate.addActionListener(evt -> jButtonCreateActionPerformed());

		ItemListener itemListener = itemEvent -> {
			if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
				if (itemEvent.getItemSelectable().equals(jCheckMon)) {
					deliveryWeekDates.add(Calendar.MONDAY);
				} else if (itemEvent.getItemSelectable().equals(jCheckTue)) {
					deliveryWeekDates.add(Calendar.TUESDAY);
				} else if (itemEvent.getItemSelectable().equals(jCheckWed)) {
					deliveryWeekDates.add(Calendar.WEDNESDAY);
				} else if (itemEvent.getItemSelectable().equals(jCheckThu)) {
					deliveryWeekDates.add(Calendar.THURSDAY);
				} else if (itemEvent.getItemSelectable().equals(jCheckFri)) {
					deliveryWeekDates.add(Calendar.FRIDAY);
				} else if (itemEvent.getItemSelectable().equals(jCheckSat)) {
					deliveryWeekDates.add(Calendar.SATURDAY);
				}
			} else {
				if (itemEvent.getItemSelectable().equals(jCheckMon)) {
					deliveryWeekDates.remove((Object) Calendar.MONDAY);
				} else if (itemEvent.getItemSelectable().equals(jCheckTue)) {
					deliveryWeekDates.remove((Object) Calendar.TUESDAY);
				} else if (itemEvent.getItemSelectable().equals(jCheckWed)) {
					deliveryWeekDates.remove((Object) Calendar.WEDNESDAY);
				} else if (itemEvent.getItemSelectable().equals(jCheckThu)) {
					deliveryWeekDates.remove((Object) Calendar.THURSDAY);
				} else if (itemEvent.getItemSelectable().equals(jCheckFri)) {
					deliveryWeekDates.remove((Object) Calendar.FRIDAY);
				} else if (itemEvent.getItemSelectable().equals(jCheckSat)) {
					deliveryWeekDates.remove((Object) Calendar.SATURDAY);
				}
			}
		};

		jCheckMon.setText("Mon");
		jCheckMon.addItemListener(itemListener);

		jCheckTue.setText("Tue");
		jCheckTue.addItemListener(itemListener);

		jCheckWed.setText("Wed");
		jCheckWed.addItemListener(itemListener);

		jCheckThu.setText("Thu");
		jCheckThu.addItemListener(itemListener);

		jCheckFri.setText("Fri");
		jCheckFri.addItemListener(itemListener);

		jCheckSat.setText("Sat");
		jCheckSat.addItemListener(itemListener);

		jLabelTo.setText("to");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
								.createSequentialGroup().addContainerGap().addGroup(
										layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
														jLabelCourse).addComponent(jLabelYear)
												.addComponent(
														jLabelDeliveryDay)
												.addComponent(jLabelUnits))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(layout.createSequentialGroup().addGroup(layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(layout.createSequentialGroup()
																.addComponent(jTextYearStart, javax.swing.GroupLayout.PREFERRED_SIZE,
																		87, javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(jLabelTo)
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(jTextYearEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 80,
																		javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(layout.createSequentialGroup().addComponent(jCheckMon)
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(jCheckTue)
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(jCheckWed)
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(jCheckThu)
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(jCheckFri, javax.swing.GroupLayout.PREFERRED_SIZE, 47,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(jCheckSat, javax.swing.GroupLayout.PREFERRED_SIZE, 56,
																		javax.swing.GroupLayout.PREFERRED_SIZE)))
												.addGap(0, 0, Short.MAX_VALUE))
										.addGroup(layout.createSequentialGroup()
												.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(jScrollPane1).addComponent(jTextCourse))
												.addContainerGap())))
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
								layout.createSequentialGroup()
										.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(jButtonCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 85,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(38, 38, 38)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addContainerGap()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(jLabelCourse).addComponent(jTextCourse, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(jLabelYear)
						.addComponent(jTextYearStart, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jLabelTo).addComponent(jTextYearEnd, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(jLabelDeliveryDay)
						.addComponent(jCheckMon, javax.swing.GroupLayout.PREFERRED_SIZE, 18,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jCheckTue, javax.swing.GroupLayout.PREFERRED_SIZE, 18,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jCheckWed).addComponent(jCheckFri).addComponent(jCheckThu)
						.addComponent(jCheckSat))
				.addGap(10, 10, 10)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								layout.createSequentialGroup().addComponent(jLabelUnits).addGap(0, 0, Short.MAX_VALUE))
						.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jButtonCreate)
				.addContainerGap()));

		pack();
	}

	/**
	 * TODO create a grid for the units and number of weeks that it would take
	 */
	private void jButtonCreateActionPerformed() {
		if (jTextCourse.getText().isEmpty() || jTextYearStart.getText().isEmpty()
				|| jTextAreaUnits.getText().isEmpty()
				|| (!jCheckMon.isSelected() && !jCheckTue.isSelected() && !jCheckWed.isSelected()
				&& !jCheckThu.isSelected() && !jCheckFri.isSelected() && !jCheckSat.isSelected())) {
			JOptionPane.showMessageDialog(this, "Please, complete the form");
		} else {
			//map with list of unit per year, key being year
			Map<Integer, List<WeeklyUnit>> mapCC = new HashMap<>();
			int yearStart = Integer.parseInt(jTextYearStart.getText());
			int yearEnd = Integer.parseInt(jTextYearEnd.getText());
			for (int i = yearStart; i <= yearEnd ; i++) {
				/*
				  every line is a different unit, if unit lasts more than a week,
				  user needs to repeat the unit in the textPanel
				 */
				String[] units = jTextAreaUnits.getText().trim().split("\n");
				List<String> listUnit = new LinkedList<>(Arrays.asList(units));

				var course = new Course();
				course.setYear(i);
				course.setUnits(listUnit);
				try {
					var createCalendar = new CreateCalendar(course);
					List<WeeklyUnit> weeklyUnitList = createCalendar.createCalendar();
					mapCC.put(i, weeklyUnitList);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}

			JFileChooser jFileChooser = new JFileChooser("Save");
			int save = jFileChooser.showSaveDialog(this);
			if (save == 0) {
				File file = jFileChooser.getSelectedFile();

				for (int i = yearStart; i <= yearEnd; i++) {
					try {
						String path = file.getPath();
						int index = path.indexOf(".xls");
						if (index < 0) {
							path += i + ".xls";
						} else {
							path = path.substring(0, index) + i + path.substring(index);
						}
						CreateXLS.createCalendar(mapCC.get(i), deliveryWeekDates, path, i,
								jTextCourse.getText().trim());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				JOptionPane.showMessageDialog(this, "The file has been saved successfully!");
				cleanForm();
			}
		}
	}

	private void cleanForm() {
		jTextCourse.setText("");
		jTextYearStart.setText("");
		jCheckMon.setSelected(false);
		jCheckTue.setSelected(false);
		jCheckWed.setSelected(false);
		jCheckThu.setSelected(false);
		jCheckFri.setSelected(false);
		jCheckSat.setSelected(false);
		jTextAreaUnits.setText("");
		deliveryWeekDates.clear();
	}

	public static void main(String[] args) {
		new InitialFrame().setVisible(true);
	}
}

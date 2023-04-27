package com.calendarcreator.main;

import com.calendarcreator.core.CreateCalendar;
import com.calendarcreator.core.CreateXLS;
import com.calendarcreator.model.Course;
import com.calendarcreator.model.WeeklyUnit;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.text.ParseException;
import java.util.*;

public class InitialFrame extends JFrame {

    private JCheckBox jCheckFri;
    private JCheckBox jCheckMon;
    private JCheckBox jCheckSat;
    private JCheckBox jCheckThu;
    private JCheckBox jCheckTue;
    private JCheckBox jCheckWed;
    private JTextArea jTextAreaUnits;
    private JTextField jTextCourse;
    private JTextField jTextYearStart;
    private List<Integer> deliveryWeekDates;
    private JTextField jTextYearEnd;

    public InitialFrame() {
        initComponents();
        setTitle("Create Calendar");
    }

    public static void main(String[] args) {
        new InitialFrame().setVisible(true);
    }

    private void initComponents() {

        JLabel jLabelCourse = new JLabel();
        jTextCourse = new JTextField();
        JLabel jLabelYear = new JLabel();
        jTextYearStart = new JTextField();
        JLabel jLabelDeliveryDay = new JLabel();
        JLabel jLabelUnits = new JLabel();
        JScrollPane jScrollPane1 = new JScrollPane();
        jTextAreaUnits = new JTextArea();
        JButton jButtonCreate = new JButton();
        jCheckMon = new JCheckBox("Mon");
        jCheckTue = new JCheckBox("Tue");
        jCheckWed = new JCheckBox("Wed");
        jCheckThu = new JCheckBox("Thu");
        jCheckFri = new JCheckBox("Fri");
        jCheckSat = new JCheckBox("Sat");
        JLabel jLabelTo = new JLabel();
        jTextYearEnd = new JTextField();
        deliveryWeekDates = new LinkedList<>();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

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
                    deliveryWeekDates.remove(Calendar.MONDAY);
                } else if (itemEvent.getItemSelectable().equals(jCheckTue)) {
                    deliveryWeekDates.remove(Calendar.TUESDAY);
                } else if (itemEvent.getItemSelectable().equals(jCheckWed)) {
                    deliveryWeekDates.remove(Calendar.WEDNESDAY);
                } else if (itemEvent.getItemSelectable().equals(jCheckThu)) {
                    deliveryWeekDates.remove(Calendar.THURSDAY);
                } else if (itemEvent.getItemSelectable().equals(jCheckFri)) {
                    deliveryWeekDates.remove(Calendar.FRIDAY);
                } else if (itemEvent.getItemSelectable().equals(jCheckSat)) {
                    deliveryWeekDates.remove(Calendar.SATURDAY);
                }
            }
        };

        jCheckMon.addItemListener(itemListener);
        jCheckTue.addItemListener(itemListener);
        jCheckWed.addItemListener(itemListener);
        jCheckThu.addItemListener(itemListener);
        jCheckFri.addItemListener(itemListener);
        jCheckSat.addItemListener(itemListener);

        jLabelTo.setText("to");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout
                    .createSequentialGroup().addContainerGap().addGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(
                    jLabelCourse).addComponent(jLabelYear)
                    .addComponent(
                    jLabelDeliveryDay)
                    .addComponent(jLabelUnits))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup().addGroup(layout
                    .createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                    .addComponent(jTextYearStart, GroupLayout.PREFERRED_SIZE,
                    87, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabelTo)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jTextYearEnd, GroupLayout.PREFERRED_SIZE, 80,
                    GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup().addComponent(jCheckMon)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jCheckTue)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jCheckWed)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jCheckThu)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jCheckFri, GroupLayout.PREFERRED_SIZE, 47,
                    GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jCheckSat, GroupLayout.PREFERRED_SIZE, 56,
                    GroupLayout.PREFERRED_SIZE)))
                    .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1).addComponent(jTextCourse))
                    .addContainerGap())))
                    .addGroup(GroupLayout.Alignment.TRAILING,
                    layout.createSequentialGroup()
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonCreate, GroupLayout.PREFERRED_SIZE, 85,
                    GroupLayout.PREFERRED_SIZE)
                    .addGap(38, 38, 38))
                );
                layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout
                    .createSequentialGroup().addContainerGap()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelCourse).addComponent(jTextCourse, GroupLayout.PREFERRED_SIZE,
                    GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelYear)
                    .addComponent(jTextYearStart, GroupLayout.PREFERRED_SIZE,
                    GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelTo).addComponent(jTextYearEnd, GroupLayout.PREFERRED_SIZE,
                    GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDeliveryDay)
                    .addComponent(jCheckMon, GroupLayout.PREFERRED_SIZE, 18,
                    GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckTue, GroupLayout.PREFERRED_SIZE, 18,
                    GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckWed).addComponent(jCheckFri).addComponent(jCheckThu)
                    .addComponent(jCheckSat))
                    .addGap(10, 10, 10)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(
                    layout.createSequentialGroup().addComponent(jLabelUnits).addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(jButtonCreate)
                    .addContainerGap())
                );
        pack();
        cleanForm();
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
            for (int i = yearStart; i <= yearEnd; i++) {
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
        jTextYearEnd.setText("");
        jCheckMon.setSelected(false);
        jCheckTue.setSelected(false);
        jCheckWed.setSelected(false);
        jCheckThu.setSelected(false);
        jCheckFri.setSelected(false);
        jCheckSat.setSelected(false);
        jTextAreaUnits.setText("");
        deliveryWeekDates.clear();
    }
}

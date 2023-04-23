package com.calendarcreator.core;

import com.calendarcreator.model.CalendarDictionary;
import com.calendarcreator.model.Course;
import com.calendarcreator.model.WeeklyUnit;

import java.text.ParseException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class CreateCalendar {

    private Course course;

    public CreateCalendar(Course course){
        this.course = course;
    }
    /**
     * Creates a list with every week's unit and putting a three weeks holiday every
     * 10 weeks of class
     * @return a list of one year of Course
     * @throws ParseException exception from simpleDateFormat
     */
    public List<WeeklyUnit> createCalendar() throws ParseException {

        int normalWeek = 0;
        var dateFormat = CalendarDictionary.DATE_FORMAT;

        Calendar startDate = Calendar.getInstance();
        startDate.setTime(dateFormat.parse(CalendarDictionary.FIRST_DAY + course.getYear()));

        Calendar endWeekDate = Calendar.getInstance();
        endWeekDate.setTime(startDate.getTime());

        //set the end date to Saturday as last day of the week
        while(endWeekDate.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
            endWeekDate.add(Calendar.DAY_OF_MONTH, 1);
        }

        List<WeeklyUnit> weeklyUnitList = new LinkedList<>();
        int countWeekUnit = 0;

        while (startDate.get(Calendar.YEAR) <= course.getYear()) {
            WeeklyUnit weeklyUnit = new WeeklyUnit();
            weeklyUnit.setStartDate(dateFormat.format(startDate.getTime()));
            weeklyUnit.setEndDate(dateFormat.format(endWeekDate.getTime()));

            if (isPublicHoliday(dateFormat.format(startDate.getTime()), dateFormat.format(endWeekDate.getTime()))) {
                weeklyUnit.setUnitName(CalendarDictionary.HOLIDAY);
                weeklyUnit.setHoliday(true);
                normalWeek = 0;
            } else {
                weeklyUnit.setUnitName(course.getUnits().get(countWeekUnit));
                if (normalWeek < 10) {
                    normalWeek++;
                    countWeekUnit++;
                } else if (normalWeek == 10 || normalWeek == 11) {
                    weeklyUnit.setUnitName(CalendarDictionary.HOLIDAY);
                    weeklyUnit.setHoliday(true);
                    normalWeek++;
                } else {
                    weeklyUnit.setUnitName(CalendarDictionary.HOLIDAY);
                    weeklyUnit.setHoliday(true);
                    normalWeek = 0;
                }
            }

            if (countWeekUnit == course.getUnits().size()) {
                countWeekUnit = 0;
            }

            weeklyUnitList.add(weeklyUnit);
            endWeekDate.add(Calendar.WEEK_OF_YEAR, 1);
            startDate.add(Calendar.WEEK_OF_YEAR, 1);
        }
        return weeklyUnitList;
    }

    /**
     * TODO Create a algorithm to find the easter holiday
     * Check if there is a public holiday in between the given period
     * @param startDate start of the period
     * @param endDate end of the period
     * @return a boolean true if there is a public holiday in between those given dates,
     * and false if there is no public holiday
     * @throws ParseException from simpleDateFormat
     */
    private boolean isPublicHoliday(String startDate, String endDate) throws ParseException {
        var dateFormat = CalendarDictionary.DATE_FORMAT;

        Calendar unitStartDate = Calendar.getInstance();
        unitStartDate.setTime(dateFormat.parse(startDate));

        Calendar unitEndDate = Calendar.getInstance();
        unitEndDate.setTime(dateFormat.parse(endDate));

        while (unitStartDate.before(unitEndDate) || unitStartDate.equals(unitEndDate)) {
            if (unitStartDate.get(Calendar.DAY_OF_MONTH) == 25 && unitStartDate.get(Calendar.MONTH) == Calendar.DECEMBER
                    || unitStartDate.get(Calendar.DAY_OF_MONTH) == 1 && unitStartDate.get(Calendar.MONTH) == Calendar.JANUARY
                    || unitStartDate.get(Calendar.DAY_OF_MONTH) == 2 && unitStartDate.get(Calendar.MONTH) == Calendar.JANUARY
                    || unitStartDate.get(Calendar.DAY_OF_MONTH) == 19 && unitStartDate.get(Calendar.MONTH) == Calendar.SEPTEMBER
                    && unitStartDate.get(Calendar.YEAR) == 2016
                    || unitStartDate.get(Calendar.DAY_OF_MONTH) == 26 && unitStartDate.get(Calendar.MONTH) == Calendar.SEPTEMBER
                    && unitStartDate.get(Calendar.YEAR) == 2016
                    || unitStartDate.get(Calendar.WEEK_OF_YEAR) == 1) {
                return true;
            }
            unitStartDate.add(Calendar.DAY_OF_YEAR, 1);
        }
        return false;
    }
}

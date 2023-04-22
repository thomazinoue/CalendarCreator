package com.calendarcreator.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import com.calendarcreator.model.CalendarCourse;
import com.calendarcreator.model.CalendarDictionary;
import com.calendarcreator.model.Course;
import com.calendarcreator.model.CourseIntake;

public class CreateCalendar {

	/**
	 * Creates a list with every week's unit and putting a three weeks holiday every
	 * 10 weeks of class
	 * @param course data given by the user
	 * @return a list of one year of Course
	 * @throws ParseException exception from simpleDateFormat
	 */
	public static List<CalendarCourse> createCalendarWithHolidays(Course course) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		int normalWeek = 0;

		Calendar startDate = Calendar.getInstance();
		startDate.setTime(sdf.parse(CalendarDictionary.FIRST_DAY + course.getYearStart()));

		Calendar endWeekDate = Calendar.getInstance();
		endWeekDate.setTime(sdf.parse(sdf.format(startDate.getTime())));

		//set the end date to Saturday as last day of the week
		while(endWeekDate.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
			endWeekDate.add(Calendar.DAY_OF_MONTH, 1);
		}

		List<CalendarCourse> calendarCourses = new LinkedList<>();
		int countWeekUnit = 0;

		while (startDate.get(Calendar.YEAR) <= course.getYearStart()) {
			CalendarCourse calendarCourse = new CalendarCourse();
			calendarCourse.setStartDate(sdf.format(startDate.getTime()));
			calendarCourse.setEndDate(sdf.format(endWeekDate.getTime()));

			if (isPublicHoliday(sdf.format(startDate.getTime()), sdf.format(endWeekDate.getTime()))) {
				calendarCourse.setUnitName(CalendarDictionary.HOLIDAY);
				calendarCourse.setHoliday(true);
				normalWeek = 0;
			} else {
				calendarCourse.setUnitName(course.getUnits().get(countWeekUnit).getName());
				if (normalWeek < 10) {
					normalWeek++;
					countWeekUnit++;
				} else if (normalWeek == 10 || normalWeek == 11) {
					calendarCourse.setUnitName(CalendarDictionary.HOLIDAY);
					calendarCourse.setHoliday(true);
					normalWeek++;
				} else {
					calendarCourse.setUnitName(CalendarDictionary.HOLIDAY);
					calendarCourse.setHoliday(true);
					normalWeek = 0;
				}
			}

			if (countWeekUnit == course.getUnits().size()) {
				countWeekUnit = 0;
			}

			calendarCourses.add(calendarCourse);
			endWeekDate.add(Calendar.WEEK_OF_YEAR, 1);
			startDate.add(Calendar.WEEK_OF_YEAR, 1);
		}
		return calendarCourses;
	}

	/**
	 *	create a list of intake from a list of the units and holidays throughout the year
	 * @param calendarCourses list of course and holidays
	 * @param units number of units
	 * @param year year of the course
	 * @return a list of course intake
	 * @throws ParseException from simpleDateFormat
	 */
	public static List<CourseIntake> createCourseIntake(List<CalendarCourse> calendarCourses,
			int units, int year) throws ParseException {
		List<CourseIntake> courseIntakes = new LinkedList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		for (CalendarCourse calendarCourse : calendarCourses) {
			if (!calendarCourse.isHoliday()) {
				CourseIntake courseIntake = new CourseIntake();
				courseIntake.setStartDate(calendarCourse.getStartDate());

				Calendar startDate = Calendar.getInstance();
				startDate.setTime(sdf.parse(calendarCourse.getStartDate()));

				if (startDate.get(Calendar.YEAR) > year) {
					break;
				}

				int qtholiday = 0;
				String lastEndDate = "";
				for (CalendarCourse calendarCourseEnd : calendarCourses) {
					Calendar startDateEnd = Calendar.getInstance();
					startDateEnd.setTime(sdf.parse(calendarCourseEnd.getStartDate()));

					if (startDate.before(startDateEnd)) {
						if (calendarCourse.getUnitName().equals(calendarCourseEnd.getUnitName())) {
							courseIntake.setEndDate(lastEndDate);
							courseIntake.setDuration(units + qtholiday);
							courseIntakes.add(courseIntake);
							qtholiday = 0;
							break;
						} else if (calendarCourseEnd.isHoliday()) {
							qtholiday++;
						} else {
							lastEndDate = calendarCourseEnd.getEndDate();
						}
					}
				}
			}
		}

		return courseIntakes;
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
	public static boolean isPublicHoliday(String startDate, String endDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		Calendar unitStartDate = Calendar.getInstance();
		unitStartDate.setTime(sdf.parse(startDate));

		Calendar unitEndDate = Calendar.getInstance();
		unitEndDate.setTime(sdf.parse(endDate));

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

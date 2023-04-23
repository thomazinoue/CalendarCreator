package com.calendarcreator.model;

import java.text.SimpleDateFormat;

public class CalendarDictionary {

	public static final String HOLIDAY = "Holiday";
	public static final String FIRST_DAY = "01/01/";
	public static final String[] DAYS = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
	public static final String[] MONTHS = { "January", "February", "March", "April", "May", "June", "July", "August",
			"September", "October", "November", "December" };
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
}

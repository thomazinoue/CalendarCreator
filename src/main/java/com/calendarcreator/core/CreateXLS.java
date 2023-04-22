package com.calendarcreator.core;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import com.calendarcreator.model.CalendarCourse;
import com.calendarcreator.model.CalendarDictionary;
import com.calendarcreator.model.CourseIntake;

public class CreateXLS {

	/**
	 * create the spreadsheet with all content for the year
	 * @param calendarCourses list courses and holidays
	 * @param courseIntakes list of every intake class
	 * @param deliveryWeekDates week days of classes
	 * @param filename name and location of the spreadsheet
	 * @param year year of the couorse
	 * @param nameCourse name of the course
	 * @throws Exception IO and Parse exception
	 */
	public static void createCalendar(List<CalendarCourse> calendarCourses, List<CourseIntake> courseIntakes,
			List<Integer> deliveryWeekDates, String filename, int year, String nameCourse)
			throws Exception{
		Calendar calendar = Calendar.getInstance();
		HSSFWorkbook wb = new HSSFWorkbook();
		Map<String, CellStyle> styles = createStyles(wb);
		int rownum = 0;
		Sheet sheet = wb.createSheet("Calendar");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		Row headerRow = sheet.createRow(rownum++);
		headerRow.setHeightInPoints(60);
		Cell titleCell = headerRow.createCell(0);
		titleCell.setCellValue(nameCourse);
		titleCell.setCellStyle(styles.get("title"));
		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$" + rownum + ":$O$" + rownum));

		for (int month = 0; month < 12; month++) {
			calendar.set(Calendar.MONTH, month);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.set(Calendar.YEAR, year);
			sheet.setDisplayGridlines(false);
			sheet.setPrintGridlines(false);
			sheet.setFitToPage(true);
			sheet.setHorizontallyCenter(true);
			PrintSetup printSetup = sheet.getPrintSetup();
			printSetup.setLandscape(true);
			sheet.setAutobreaks(true);
			printSetup.setFitHeight((short) 1);
			printSetup.setFitWidth((short) 1);

			headerRow = sheet.createRow(rownum++);
			headerRow.setHeightInPoints(40);
			titleCell = headerRow.createCell(0);
			titleCell.setCellValue(CalendarDictionary.MONTHS[month]);
			titleCell.setCellStyle(styles.get("title"));
			sheet.addMergedRegion(CellRangeAddress.valueOf("$A$" + rownum + ":$G$" + rownum));

			Row monthRow = sheet.createRow(rownum++);
			for (int i = 0; i < CalendarDictionary.DAYS.length; i++) {
				Cell monthCell = monthRow.createCell(i);
				monthCell.setCellValue(CalendarDictionary.DAYS[i]);
				monthCell.setCellStyle(styles.get("month"));
			}

			int cnt = 1;
			int day = 1;
			for (int j = 0; j < 6; j++) {
				Row row = sheet.createRow(rownum++);
				row.setHeightInPoints(50);
				String weekDescription = "";
				boolean isHoliday = false;

				for (CalendarCourse calendarCourse : calendarCourses) {
					Calendar startDate = Calendar.getInstance();
					startDate.setTime(sdf.parse(calendarCourse.getStartDate()));

					Calendar endDate = Calendar.getInstance();
					endDate.setTime(sdf.parse(calendarCourse.getEndDate()));

					if (startDate.before(calendar) && endDate.after(calendar)
							|| (startDate.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)
									&& startDate.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)
									&& startDate.get(Calendar.YEAR) == calendar.get(Calendar.YEAR))
							|| (endDate.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)
									&& endDate.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)
									&& endDate.get(Calendar.YEAR) == calendar.get(Calendar.YEAR))) {
						if (calendarCourse.isHoliday()) {
							weekDescription = CalendarDictionary.HOLIDAY;
							isHoliday = true;
						} else {
							weekDescription = calendarCourse.getUnitName();
						}
						break;
					}
				}
				for (int i = 0; i < CalendarDictionary.DAYS.length; i++) {
					Cell dayCell = row.createCell(i);
					int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);

					if (cnt >= day_of_week && calendar.get(Calendar.MONTH) == month) {
						dayCell.setCellValue(day);
						calendar.set(Calendar.DAY_OF_MONTH, ++day);
						if (isHoliday) {
							if (calendar.get(Calendar.WEEK_OF_YEAR) != 1
									&& calendar.get(Calendar.MONTH) == Calendar.JANUARY) {
								dayCell.setCellStyle(styles.get("workday_left"));
							} else {
								dayCell.setCellStyle(styles.get("holiday"));
							}
						} else if (deliveryWeekDates.contains(day_of_week)) {
							dayCell.setCellStyle(styles.get("assessment_day"));
						} else {
							dayCell.setCellStyle(styles.get("workday_left"));
						}
					} else {
						dayCell.setCellStyle(styles.get("grey_left"));
					}
					cnt++;
				}
				Cell descriptionCell = row.createCell(CalendarDictionary.DAYS.length + 1);
				descriptionCell.setCellValue(weekDescription);
				descriptionCell.setCellStyle(styles.get("description"));
				sheet.addMergedRegion(CellRangeAddress.valueOf("$I$" + rownum + ":$O$" + rownum));

				if (calendar.get(Calendar.MONTH) > month)
					break;
			}
		}

		rownum = 0;
		int cellNum = 0;
		sheet = wb.createSheet("Course Intake");
		headerRow = sheet.createRow(rownum++);
		for (String header : CalendarDictionary.COURSE_INTAKE_HEADER) {
			Cell headerCell = headerRow.createCell(cellNum++);
			headerCell.setCellValue(header);
			headerCell.setCellStyle(styles.get("courseIntakeHeader"));
		}

		for (CourseIntake courseIntake : courseIntakes) {
			headerRow = sheet.createRow(rownum++);
			Cell cell = headerRow.createCell(0);
			cell.setCellValue(courseIntake.getStartDate());
			cell.setCellStyle(styles.get("courseIntake"));
			cell = headerRow.createCell(1);
			cell.setCellValue(courseIntake.getEndDate());
			cell.setCellStyle(styles.get("courseIntake"));
			cell = headerRow.createCell(2);
			cell.setCellValue(courseIntake.getDuration());
			cell.setCellStyle(styles.get("courseIntake"));
		}

		FileOutputStream out = new FileOutputStream(filename);
		wb.write(out);
		out.close();
	}

	/**
	 * create the styles for the spreadsheet
	 * @param wb create a spreadsheet file
	 * @return a map with style for spreadsheet
	 */
	private static Map<String, CellStyle> createStyles(HSSFWorkbook wb) {
		Map<String, CellStyle> styles = new HashMap<>();

		short borderColor = IndexedColors.GREY_50_PERCENT.getIndex();

		CellStyle style;
		Font titleFont = wb.createFont();
		titleFont.setFontHeightInPoints((short) 24);
		titleFont.setColor(IndexedColors.DARK_BLUE.getIndex());
		style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFont(titleFont);
		styles.put("title", style);

		Font monthFont = wb.createFont();
		monthFont.setFontHeightInPoints((short) 12);
		monthFont.setColor(IndexedColors.WHITE.getIndex());
		monthFont.setBold(true);
		HSSFPalette palette = wb.getCustomPalette();
		HSSFColor myColor = palette.findSimilarColor(56, 59, 61);
		short palIndex = myColor.getIndex();
		style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFillForegroundColor(palIndex);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setFont(monthFont);
		styles.put("month", style);

		Font font = wb.createFont();
		font.setFontHeightInPoints((short) 14);
		font.setBold(true);
		style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setVerticalAlignment(VerticalAlignment.TOP);
		style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setBorderLeft(BorderStyle.THIN);
		style.setLeftBorderColor(borderColor);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBottomBorderColor(borderColor);
		style.setFont(font);
		styles.put("weekend_left", style);

		style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setVerticalAlignment(VerticalAlignment.TOP);
		style.setBorderLeft(BorderStyle.THIN);
		style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setLeftBorderColor(borderColor);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBottomBorderColor(borderColor);
		style.setFont(font);
		styles.put("workday_left", style);

		myColor = palette.findSimilarColor(48, 180, 87);
		palIndex = myColor.getIndex();
		style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setVerticalAlignment(VerticalAlignment.TOP);
		style.setBorderLeft(BorderStyle.THIN);
		style.setFillForegroundColor(palIndex);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setLeftBorderColor(borderColor);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBottomBorderColor(borderColor);
		style.setFont(font);
		styles.put("assessment_day", style);

		myColor = palette.findSimilarColor(255, 131, 0);
		palIndex = myColor.getIndex();
		style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setVerticalAlignment(VerticalAlignment.TOP);
		style.setBorderLeft(BorderStyle.THIN);
		style.setFillForegroundColor(palIndex);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setLeftBorderColor(borderColor);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBottomBorderColor(borderColor);
		style.setFont(font);
		styles.put("holiday", style);

		style = wb.createCellStyle();
		style.setBorderLeft(BorderStyle.THIN);
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBottomBorderColor(borderColor);
		styles.put("grey_left", style);

		style = wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setBorderRight(BorderStyle.THIN);
		style.setRightBorderColor(borderColor);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBottomBorderColor(borderColor);
		styles.put("grey_right", style);

		font = wb.createFont();
		font.setFontHeightInPoints((short) 14);
		style = wb.createCellStyle();
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		styles.put("description", style);

		font = wb.createFont();
		font.setFontHeightInPoints((short) 12);
		style = wb.createCellStyle();
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		styles.put("courseIntake", style);

		font = wb.createFont();
		font.setFontHeightInPoints((short) 14);
		font.setBold(true);
		style = wb.createCellStyle();
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		styles.put("courseIntakeHeader", style);

		return styles;
	}
}

package util;
import java.util.Calendar;

/**
 * Represents a date in the format mm/dd/yyyy.
 * Handles various operations such as date validation, leap year checks, and comparisons.
 *
 * @author Syona Bhandari
 * @author Rhemie Patiak
 */
public class Date implements Comparable<Date> {
    // constants
    private static int SIXMONTHS = 6;       // the six-month range for date validation
    private static int TWELVEMONTHS = 12;   // maximum number of months in a year

    // instance variables
    private int year;       // the year of the date
    private int month;      // the month of the date
    private int day;        // the day of the month

    /**
     * Constructs a Date object in mm/dd/yyyy format.
     * If the date is not in this format, it is considered invalid.
     *
     * @param month The month of the date (1-12).
     * @param day   The day of the month (1-31).
     * @param year  The year of the date.
     */
    public Date(int month, int day, int year) {
        this.month = month;
        this.day = day;
        this.year = year;
    }

    // private methods

    /**
     * Checks if the year is a leap year.
     * A leap year occurs every 4 years, except for years divisible by 100 but not by 400.
     *
     * @return True if the year is a leap year, otherwise false.
     */
    //if this is true february has 29 days, else february has 28 days
    private boolean isLeapYear() {
        final int QUADRENNIAL = 4;
        final int CENTENNIAL = 100;
        final int QUATERCENTENNIAL = 400;

        return ((year % QUADRENNIAL == 0) && (year % CENTENNIAL != 0) || (year % QUATERCENTENNIAL == 0));
    }

    // public methods

    /**
     * Returns the year of the date.
     *
     * @return The year of the date.
     */
    public int getYear() {
        return year;
    }

    /**
     * Returns the month of the date.
     *
     * @return The month of the date (1-12).
     */
    public int getMonth() {
        return month;
    }

    /**
     * Returns the day of the date.
     *
     * @return The day of the month (1-31).
     */
    public int getDay() {
        return day;
    }

    /**
     * Sets the day of the date.
     *
     * @param day The day of the month (1-31).
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * Sets the month of the date.
     *
     * @param month The month of the date (1-12).
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * Sets the year of the date.
     *
     * @param year The year of the date.
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Determines if two date objects are equal based on their day, month, and year.
     *
     * @param obj The object to compare against.
     * @return True if the dates are equal, otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Date date) {
            return ((month == date.month) && (day == date.day) && (year == date.year));
        }
        return true;
    }

    /**
     * Provides a string representation of the Date in mm/dd/yyyy format.
     *
     * @return A string representing the date.
     */
    @Override
    public String toString() {
        return String.format("%d/%d/%d", month, day, year);
    }

    /**
     * Compares this Date with another Date for sorting purposes.
     * Comparison is done by year first, then month, then day.
     *
     * @param date The date to compare to.
     * @return A negative integer, zero, or a positive integer if this date is
     *          less than, equal to, or greater than the specified date.
     */
    @Override
    public int compareTo(Date date) {
        if(this.year != date.year) return Integer.compare(this.year, date.year);
        if(this.month != date.month) return Integer.compare(this.month, date.month);
        return Integer.compare(this.day, date.day);
    }

    /**
     * Checks if this date is today.
     *
     * @return True if the date is today, otherwise false.
     */
    public boolean isToday() {
        Date today = new Date((Calendar.getInstance().get(Calendar.MONTH) + 1),
                (Calendar.getInstance().get(Calendar.DAY_OF_MONTH)),
                (Calendar.getInstance().get(Calendar.YEAR)));
        return this.compareTo(today) == 0;
    }

    /**
     * Checks if this date is before today.
     *
     * @return True if the date is before today, otherwise false.
     */
    public boolean isBeforeToday() {
        Date today = new Date((Calendar.getInstance().get(Calendar.MONTH) + 1),
                (Calendar.getInstance().get(Calendar.DAY_OF_MONTH)),
                (Calendar.getInstance().get(Calendar.YEAR)));
        return this.compareTo(today) < 0;
    }

    /**
     * Checks if this date falls on a weekend (Saturday or Sunday).
     *
     * @return True if the date is on a weekend, otherwise false.
     */
    public boolean isWeekend() {
        Calendar testDate = Calendar.getInstance();
        testDate.set(year, (month - 1), day);
        int dayOfWeek = testDate.get(Calendar.DAY_OF_WEEK);
        return (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY);
    }

    /**
     * Checks if this date is within the next six months from today.
     *
     * @return True if the date is within six months, otherwise false.
     */
    public boolean isIn6Months() {
        Calendar todayDate = Calendar.getInstance();
        todayDate.add(Calendar.MONTH, SIXMONTHS);
        Date sixMonths = new Date((todayDate.get(Calendar.MONTH) + 1),
                (todayDate.get(Calendar.DAY_OF_MONTH)),
                (todayDate.get(Calendar.YEAR)));
        return this.compareTo(sixMonths) <= 0;
    }

    /**
     * Checks if the date is valid.
     * This includes checks for invalid months, invalid days for specific months,
     * leap year adjustments, and future date constraints.
     *
     * @return True if the date is valid, otherwise false.
     */
    public boolean isValid() {
        // checks if the month is valid
        if (month < 1 || month > TWELVEMONTHS) {
            //System.out.println("Invalid month.");
            return false; // Invalid month
        }
        // array for days in each month, adjusted for leap year if necessary
        int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (isLeapYear()) {
            daysInMonth[1] = 29; // February has 29 days in a leap year
        }

        // check if date is valid for the given month
        if (day < 1 || day > daysInMonth[month - 1]) {
            //System.out.println("Invalid day for the given month.");
            return false; // Invalid day
        }
        return true;
    }
}




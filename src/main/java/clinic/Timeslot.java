package clinic;

/**
 * This class represents a specific time in the clinic's scheduling system.
 * It implements Comparable, allowing timeslots to be compared by hour and minute.
 * It also provides methods to set and retrieve the hour and minute.
 *
 * @author Syona Bhandari
 * @author Rhemie Patiak
 */
public class Timeslot implements Comparable<Timeslot> {
    // instance variables
    private int hour;
    private int minute;

    /**
     * Constructs a Timeslot object with the given hour and minute.
     *
     * @param hour The hour of the timeslot. (0-23 format)
     * @param minute The minute of the timeslot. (0-59)
     */
    public Timeslot(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    // public methods

    /**
     * Gets the hour of the timeslot.
     *
     * @return The hour in 24-hour format
     */
    public int getHour() {
        return hour;
    }

    /**
     * Gets the minute of the timeslot.
     *
     * @return The minute.
     */
    public int getMinute() {
        return minute;
    }

    /**
     * Sets the hour of the timeslot.
     *
     * @param hour The hour to set (0-23 format).
     */
    public void setHour(int hour) {
        this.hour = hour;
    }

    /**
     * Sets the minute of the timeslot.
     *
     * @param minute The minute to set.
     */
    public void setMinute(int minute) {
        this.minute = minute;
    }

    /**
     * Checks if this timeslot is equal to another object.
     * Two timeslots are considered equal if they have the same hour and minute.
     *
     * @param obj The object to compare with.
     * @return True if the timeslots are equal, otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Timeslot timeslot) {
            return ((hour == timeslot.hour) && (minute == timeslot.minute));
        }
        return true;
    }

    /**
     * Returns a string representation of the timeslot in the format:
     * HH:MM AM/PM (where AM/PM depends on the hour).
     *
     * @return A string representing the timeslot in a human-readable format.
     */
    @Override
    public String toString() {
        // determines whether the time is AM or PM
        String amORpm = (hour < 12) ? "AM" : "PM";
        // adjust hour to 12-hour format
        int displayHour = (hour == 0 || hour == 12) ? 12 : hour % 12;
        // return formatted string of the timeslot
        return String.format("%d:%02d %s", displayHour, minute, amORpm);
    }

    /**
     * Compares this timeslot with another timeslot based on hour and minute.
     *
     * @param timeslot The other timeslot to compare to.
     * @return a negative integer, zero, or a positive integer as this profile is less than, equal to,
     *          or greater than the specified profile.
     */
    @Override
    public int compareTo(Timeslot timeslot) {
        if(this.hour != timeslot.hour) return Integer.compare(this.hour, timeslot.hour);
        return Integer.compare(this.minute, timeslot.minute);
    }
}

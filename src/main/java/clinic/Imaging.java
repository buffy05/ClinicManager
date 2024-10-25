package clinic;

import util.Date;

/**
 * Represents an imaging appointment in the clinic, extending the Appointment class.
 * This class includes additional data for imaging services, specifically the Radiology room.
 * It inherits the date, timeslot, patient, and provider information from the Appointment class.
 *
 * Extends Appointment class.
 *
 * @author Syona Bhandari
 * @author Rhemnie Patiak
 */
public class Imaging extends Appointment {
    // instance variables
    private Radiology room;  // Imaging room for the radiology service (CATSCAN, ULTRASOUND, XRAY)

    /**
     * Constructor to initialize an imaging appointment with a date, timeslot, patient, provider, and radiology room.
     *
     * @param date      The date of the appointment.
     * @param timeslot  The timeslot of the appointment.
     * @param patient   The patient profile for the appointment.
     * @param provider  The provider for the appointment.
     * @param room      The radiology room (CATSCAN, ULTRASOUND, XRAY) for the imaging service.
     */
    public Imaging(Date date, Timeslot timeslot, Person patient, Person provider, Radiology room) {
        super(date, timeslot, patient, provider);
        this.room = room;
    }

    // public methods

    /**
     * Getter for the radiology room of the imaging appointment.
     *
     * @return The radiology room (CATSCAN, ULTRASOUND, XRAY).
     */
    public Radiology getRoom() {
        return room;
    }

    /**
     * Setter for the radiology room of the imaging appointment.
     *
     * @param room The new radiology room (CATSCAN, ULTRASOUND, XRAY) for the appointment.
     */
    public void setRoom(Radiology room) {
        this.room = room;
    }

    /**
     * Provides a textual representation of the imaging appointment.
     * Format: 10/30/2024 10:45 AM John Doe 12/13/1989 [PATEL, BRIDGEWATER, Somerset 08807, FAMILY, XRAY]
     *                  (DATE)    (TIMESLOT)    (PROFILE)         (PROVIDER)      (RADIOLOGY)
     *
     * @return A string representing the imaging appointment details.
     */
    @Override
    public String toString() {
        return super.toString() + " [" + room.toString() + " ]";
    }

    /**
     * Compares this imaging appointment to another appointment.
     * Compares by date first, then timeslot, and finally provider (by last name).
     * Radiology room is not included in the comparison, as it's assumed appointments are sorted
     * primarily by date, timeslot, and provider.
     *
     * @param appointment The other appointment to compare against.
     * @return A negative integer, zero, or a positive integer as this imaging appointment
     *          is less than, equal to, or greater than the specified appointment.
     */
    @Override
    public int compareTo(Appointment appointment) {
        return super.compareTo(appointment);
    }

    /**
     * Determines if two imaging appointments are equal based on their date, timeslot, patient, and room.
     *
     * @param obj The other object to compare against.
     * @return True if the appointments are equal, otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Imaging otherImaging)) return false;
        return super.equals(otherImaging);
    }
}

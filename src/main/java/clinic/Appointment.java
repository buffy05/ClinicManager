package clinic;

import util.Date;

/**
 * The Appointment class represents a single appointment, holding details about the date, timeslot, patient,
 * and provider.
 *
 *  @author Syona Bhandari
 *  @author Rhemie Patiak
 */
public class Appointment implements Comparable <Appointment> {
    private Date date;              // date of the appointment
    private Timeslot timeslot;      // the timeslot of the appointment
    private Person patient;        // the profile of the patient
    private Person provider;      // the provider that's attending the appointment

    /**
     * Constructor to initialize an appointment with a date, timeslot, patient, and provider.
     *
     * @param date          The date of the appointment.
     * @param timeslot      The timeslot of the appointment.
     * @param patient       The patient profile for the appointment.
     * @param provider      The provider for the appointment.
     */
    public Appointment(Date date, Timeslot timeslot, Person patient, Person provider) {
        this.date = date;
        this.timeslot = timeslot;
        this.patient = patient;
        this.provider = provider;
    }

    /**
     * Getter for the date of the appointment.
     *
     * @return The date of the appointment.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Getter for the timeslot of the appointment.
     *
     * @return The timeslot of the appointment.
     */
    public Timeslot getTimeslot() {
        return timeslot;
    }

    /**
     * Setter to update the timeslot of the appointment.
     *
     * @param timeslot The new timeslot for the appointment.
     */
    public void setTimeslot(Timeslot timeslot) {
        this.timeslot = timeslot;
    }

    /**
     * Getter for the patient profile of the appointment.
     *
     * @return The patient profile of the appointment.
     */
    public Person getPatient() {
        return patient;
    }

    /**
     * Getter for the provider of the appointment.
     *
     * @return The provider of the appointment.
     */
    public Person getProvider() {
        return provider;
    }


    /**
     * Sets the provider for the appointment.
     *
     * @param provider the provider to assign to this appointment
     */
    public void setProvider(Person provider) {
        this.provider = provider;
    }

    /**
     * Determines if two appointments are equal based on their date, timeslot, and patient.
     *
     * @param obj The other appointment object to compare against.
     * @return True if the appointments are equal, otherwise false.
     */
    @Override
    public boolean equals (Object obj) {
        if(obj instanceof Appointment appointment) {
            return ((this.date.equals(appointment.date))
                    && (this.timeslot.equals(appointment.timeslot))
                    && ((this.patient.equals(appointment.patient))));
        }
        return false;
    }

    /**
     * Provides a textual representation of the appointment.
     * Format: 10/30/2024 10:45 AM John Doe 12/13/1989 [PATEL, BRIDGEWATER, Somerset 08807, FAMILY]
     *                  (DATE)    (TIMESLOT)    (PROFILE)         (PROVIDER)
     *
     * @return A string representing the appointment details.
     */
    @Override
    public String toString() {
        return date.toString() + " " + timeslot.toString() + " " + patient.toString() + " " + provider.toString();
    }

    /**
     * Compares this appointment to another appointment.
     * Compares by date first, then timeslot, and finally provider (by last name).
     *
     * @param appointment The other appointment to compare against.
     * @return A negative integer, zero, or a positive integer as this appointment
     *          is less than, equal to, or greater than the specified appointment.
     */
    @Override
    public int compareTo(Appointment appointment) {
        if(!this.date.equals(appointment.date)) return this.date.compareTo(appointment.date);
        if(!this.timeslot.equals(appointment.timeslot)) return this.timeslot.compareTo(appointment.timeslot);
        return this.provider.compareTo(appointment.provider);
    }
}

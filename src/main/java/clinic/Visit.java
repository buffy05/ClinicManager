package clinic;

/**
 * This class represents a Visit, which contains an Appointment and a reference
 * to the next Visit (creating a linked list of visits).
 *
 * The Visit class is used in the Patient class to create a linked list of completed appointments.
 *
 * @author Syona Bhandari
 * @author Rhemie Patiak
 */
public class Visit {
    // instance variables
    private Appointment appointment;
    private Visit next;

    /**
     * Constructs a new Visit with a given appointment.
     *
     * @param appointment The appointment associated with the visit.
     */
    public Visit(Appointment appointment) {
        this.appointment = appointment;
        this.next = null;
    }

    // public methods

    /**
     * Gets the appointment associated with this visit.
     *
     * @return The appointment of this visit.
     */
    public Appointment getAppointment() {
        return appointment;
    }

    /**
     * Gets the next visit in the linked list.
     *
     * @return The next visit.
     */
    public Visit getNext() {
        return next;
    }

    /**
     * Sets the next visit in the linked list.
     *
     * @param next The next visit to be set.
     */
    public void setNext(Visit next) {
        this.next = next;
    }

    /**
     * Returns a string representation of the visit, including the patient's profile.
     *
     * @return A string representation of the visit.
     */
    @Override
    public String toString() {
        //return appointment.getPerson.toString();
        return null;
    }
}

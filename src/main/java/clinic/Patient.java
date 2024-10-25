package clinic;

/**
 * The Patient class represents a patient in the clinic system.
 * It extends the Person class and includes additional details about the patient's visit history.
 *
 * Each patient has a profile (inherited from Person) and a linked list of visits.
 * The visits are tracked to manage appointments and records.
 *
 * @author Syona Bhandari
 * @author Rhemie Patiak
 */
public class Patient extends Person {
    // instance variable
    private Visit visit;        // linked list of visits associated with the patient

    /**
     * Constructs a Patient object with the given profile and an initial visit.
     *
     * @param profile The profile of the patient, inherited from the Person class.
     */
    public Patient(Profile profile) {
        super(profile);
        this.visit = null;
    }

    // public methods

    /**
     * Adds a visit to the patient's visit history.
     * If the patient has previous visits, the new visit is added to the end of the linked list.
     *
     * @param newVisit The new visit to be added.
     */
    public void addVisit(Visit newVisit) {
        if (visit == null) {
            visit = newVisit;
        } else {
            Visit current = visit;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newVisit);
        }
    }

    /**
     * Retrieves the patient's visit history, returning the first visit in the list.
     *
     * @return The first visit in the patient's linked list of visits.
     */
    public Visit getVisitHistory() {
        return visit;
    }

    /**
     * Returns a string representation of the patient, including their profile information.
     *
     * @return A string representing the patient.
     */
    @Override
    public String toString() {
        return "Patient: " + super.toString();
    }
}

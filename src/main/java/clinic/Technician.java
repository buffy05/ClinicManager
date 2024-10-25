package clinic;

/**
 * This class represents a healthcare technician in the clinic system.
 * It extends the Provider class, adding a specific rate per visit for the technician's services
 *
 * @author Syona Bhandari
 * @author Rhemie Patiak
 */
public class Technician extends Provider {
    // instance variable
    private int ratePerVisit;  // charging rate per visit for the technician

    /**
     * Constructs a new Technician object with the given profile, location, and rate per visit.
     *
     * @param profile      The profile of the technician (name, date of birth).
     * @param location     The location where the technician works.
     * @param ratePerVisit The charging rate per visit for the technician.
     */
    public Technician(Profile profile, Location location, int ratePerVisit) {
        super(profile, location);
        this.ratePerVisit = ratePerVisit;
    }

    // public methods

    /**
     * Returns the rate per visit for the technician.
     *
     * @return The rate per visit for the technician.
     */
    @Override
    public int rate() {
        return ratePerVisit;  // Return the predefined rate for the technician
    }

    /**
     * Returns a string representation of the Technician.
     *
     * @return A string with the technician's profile, location, and rate per visit.
     */
    @Override
    public String toString() {
        return "[" + super.toString() + " [rate: $" + ratePerVisit + "]";
    }
}

package clinic;

/**
 * Enum representing the medical specialties available at the clinic.
 * Every type of service has an associated fee for each appointment.
 *
 * @author Rhemie Patiak
 * @author Syona Bhandari
 */
public enum Specialty {
    // enum constants with their corresponding charges
    FAMILY(250),
    PEDIATRICIAN(300),
    ALLERGIST(350);

    // instance variable to store the fee for every specific medical field
    private final int charge;

    /**
     * Constructor to initialize the specialty along with its cost.
     *
     * @param charge The charge associated with the specialty.
     */
    Specialty(int charge) {
        this.charge = charge;
    }

    // public methods

    /**
     * Get the charge for the specialty.
     *
     * @return The charge for this specialty.
     */
    public int getCharge() {
        return charge;
    }

    /**
     * Returns a string representation of the specialty and its charge.
     *
     * @return A string with the specialty and its charge.
     */
    @Override
    public String toString() {
        return this.name();
    }
}
package clinic;

/**
 * The Doctor class represents a doctor who is a type of healthcare provider.
 * It extends the Provider class to include additional information specific to doctors,
 * such as their medical specialty and their National Provider Identification (NPI).
 *
 * @author Syona Bhandari
 * @author Rhemie Patiak
 */
public class Doctor extends Provider {
    // instance variables
    private Specialty specialty;  // encapsulates the rate per visit based on the specialty
    private String npi;           // National Provider Identification unique to the doctor

    /**
     * Constructs a new Doctor object with the given profile, location, specialty, and NPI.
     *
     * @param profile   The profile of the doctor (name, date of birth).
     * @param location  The location where the doctor practices.
     * @param specialty The medical specialty of the doctor.
     * @param npi       The National Provider Identification unique to the doctor.
     */
    public Doctor(Profile profile, Location location, Specialty specialty, String npi) {
        super(profile, location);
        this.specialty = specialty;
        this.npi = npi;
    }

    // public methods

    /**
     * Retrieves the National Provider Identification (NPI) for the doctor.
     *
     * @return The NPI of the doctor.
     */
    public String getNpi() {
        return npi;
    }

    /**
     * Returns the rate per visit for the doctor based on their specialty.
     *
     * @return The rate per visit for the doctor.
     */
    @Override
    public int rate() {
        return specialty.getCharge();  // Get the rate based on the doctor's specialty
    }

    /**
     * Returns a string representation of the Doctor.
     *
     * @return A string with the doctor's profile, location, specialty, and NPI.
     */
    @Override
    public String toString() {
        return "[" + super.toString() + " [" + specialty + ", #" + npi + "]";
    }
}
package clinic;

/**
 * The Provider class represents a healthcare provider in the clinic.
 * It extends the Person class to include additional information like the practice location.
 * This class is abstract and defines an abstract method to get the provider's charging rate.
 *
 * @author Syona Bhandari
 * @author Rhemie Patiak
 */
public abstract class Provider extends Person {
    // instance variable
    private Location location;  // The practice location of the provider
    private int totalCharges = 0; //tracks total expected charges

    /**
     * Constructor to initialize a provider with a profile and location.
     *
     * @param profile  The profile of the provider (contains first name, last name, and date of birth).
     * @param location The location of the provider's practice.
     */
    public Provider(Profile profile, Location location) {
        super(profile);
        this.location = location;
    }

    // public methods

    /**
     * Getter for the provider's location.
     *
     * @return The location of the provider.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Abstract method to return the provider's charging rate.
     * Subclasses must provide their own implementation of this method.
     *
     * @return The charging rate per visit.
     */
    public abstract int rate();

    public void addCharges(int charge) {
        totalCharges += charge;
    }

    public void subtractCharges(int charge) {
        totalCharges -= charge;
    }

    public int getTotalCharges() {
        return totalCharges;
    }

    /**
     * Provides a string representation of the provider, including the profile and location.
     * Format: "firstName lastName dateOfBirth [Location]"
     *
     * @return A string representing the provider.
     */
    @Override
    public String toString() {
        return super.toString() + ", " + location.toString() + "]";
    }
}

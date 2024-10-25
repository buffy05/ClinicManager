package clinic;

/**
 * Enum representing clinic locations, with a county and zip code for each location.
 * All locations are final and unchangeable.
 * Provides getter methods for county and zip, and overrides the toString method
 * to provide a readable format.
 *
 * @author Syona Bhandari
 * @author Rhemie Patiak
 */
public enum Location {
    // enum constants
    BRIDGEWATER("Somerset", "08807"),
    EDISON("Middlesex", "08817"),
    PISCATAWAY("Middlesex", "08854"),
    PRINCETON("Mercer", "08542"),
    MORRISTOWN("Morris", "07960"),
    CLARK("Union", "07066");

    // instance variables
    private final String county;    // the county of the location
    private final String zip;       // the zip code of the location.

    /**
     * Constructor for the Location enum.
     * Initializes the county and zip code for each location.
     *
     * @param county  The county in which the location resides.
     * @param zip     The zip code for the location.
     */
    Location(String county, String zip) {
        this.county = county;
        this.zip = zip;
    }

    // public methods

    /**
     * Gets the county of the location.
     *
     * @return The county of the location.
     */
    public String getCounty() {
        return county;
    }

    /**
     * Gets the zip code of the location.
     *
     * @return The zip code of the location.
     */
    public String getZip() {
        return zip;
    }

    /**
     * Gets the city name (which is the name of the enum constant).
     *
     * @return The city name.
     */
    public String getCity() {
        return name();
    }

    /**
     * Provides a string representation of the location in the format:
     * LOCATION_NAME, county zip
     *
     * @return A string representing the location's name, county, and zip code.
     */
    @Override
    public String toString() {
        return name() + ", " + county + " " + zip;
    }
}

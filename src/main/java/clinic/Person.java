package clinic;

/**
 * This class represents a general person in the system.
 * It serves as a superclass for both Patient and Provider, which
 * contains a Profile object with the person/s identifying information.
 *
 * @author Rhemie Patiak
 * @author Syona Bhandari
 */

public class Person implements Comparable<Person> {
    // instance variable
    protected Profile profile;      // profile object containing the person's name and DOB

    /**
     * Constructs a new Person object with the given profile.
     *
     * @param profile the profile of the person, containing their identifying information.
     */
    public Person(Profile profile) {
        this.profile = profile;
    }

    // public methods

    /**
     * Returns the profile of the person.
     *
     * @return the Profile object of the person
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     * Compares this Person object with another Person based on their profile.
     * It uses the compareTo method of the Profile class.
     *
     * @param other the other Person object to compare to
     * @return an integer representing the comparison result: -1, 0,and 1
     */
    @Override
    public int compareTo(Person other) {
        return this.profile.compareTo(other.profile);
    }

    /**
     * Checks if this Person object is equal to another object.
     * Two persons are considered equal if their profiles are equal.
     *
     * @param obj the object to compare to
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person person = (Person) obj;
        return profile.equals(person.profile);
    }

    /**
     * Returns a string representation of the Person object.
     * The string representation is provided by the Profile's toString method.
     *
     * @return a string describing the person's profile
     */
    @Override
    public String toString() {
        return profile.toString();
    }
}

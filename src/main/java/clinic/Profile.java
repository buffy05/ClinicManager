package clinic;

import util.Date;

/**
 * The Profile class represents a patient's or provider's profile.
 * It contains personal information such as first name, last name, and date of birth.
 * The class is used for sorting profiles and comparing them by last name, first name, and date of birth.
 * It implements Comparable to allow sorting of profiles.
 *
 * @author Rhemie Patiak
 * @author Syona Bhandari
 */
public class Profile implements Comparable<Profile> {
    // instance variables
    private String fname;
    private String lname;
    private Date dob;

    /**
     * Constructor to initialize the profile with the given first name, last name, and date of birth.
     *
     * @param fname First name of the patient.
     * @param lname Last name of the patient.
     * @param dob Date of birth of the patient.
     */
    public Profile(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    // public methods

    /**
     * Returns the full name of the person in the format: "FIRST LAST"
     *
     * @return The full name in uppercase.
     */
    public String getFullName() {
        return fname.toUpperCase() + " " + lname.toUpperCase();
    }

    /**
     * Retrieves the first name of the person
     *
     * @return The first name.
     */
    public String getFname() {
        return fname;
    }

    /**
     * Retrieves the last name of the person.
     *
     * @return The last name.
     */
    public String getLname() {
        return lname;
    }

    public Date getDob() {
        return dob;
    }

    /**
     * Examines two profiles in order of last name, first name, and date of birth for comparison.
     *
     * @param other The profile to compare this profile to.
     * @return a negative integer, zero, or a positive integer as this profile is less than, equal to,
     *          or greater than the specified profile.
     */
    @Override
    public int compareTo(Profile other) {
        int lastNameComparison = this.lname.compareToIgnoreCase(other.lname);
        if (lastNameComparison != 0) {
            return lastNameComparison > 0 ? 1 : -1;
        }

        int firstNameComparison = this.fname.compareToIgnoreCase(other.fname);
        if (firstNameComparison != 0) {
            return firstNameComparison > 0 ? 1 : -1;
        }

        return this.dob.compareTo(other.dob);
    }

    /**
     * Checks if this profile is equal to another object.
     * Two profiles are considered equal if their first name, last name, and date of birth match.
     *
     * @param obj The object to compare with this profile.
     * @return true if the profiles are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Profile other = (Profile) obj;
        return this.fname.equalsIgnoreCase(other.fname) &&
                this.lname.equalsIgnoreCase(other.lname) &&
                this.dob.equals(other.dob);
    }

    /**
     * Returns a string representation of the profile in the format: "firstName lastName dateOfBirth".
     *
     * @return A string representing the profile.
     */
    @Override
    public String toString() {
        return fname + " " + lname + " " + dob;
    }
}
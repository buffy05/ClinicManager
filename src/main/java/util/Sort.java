package util;

import clinic.Appointment;
import clinic.Location;
import clinic.Patient;
import clinic.Provider;

public class Sort {


    //will later a
    public static void sortAppointments(List<Appointment> listA, List<Provider> listP, String key) {
        switch(key) {
            case "PA":
                sortByDateTimeProvider(listA);
                break;
            case "PP":
                sortByPatientDateTime(listA);
                break;
            case "PL":
                sortByCountyDateTime(listA, listP);
                break;
            default:
                throw new IllegalArgumentException("Invalid sort key");
        }
    }

    private static int compareDTP(Appointment app1, Appointment app2) {
        return app1.compareTo(app2);
    }

    private static int comparePDT(Appointment app1, Appointment app2) {
        //compare by patient profile using Profile compareTo
        int patientCompare = app1.getPatient().compareTo(app2.getPatient());
        if(patientCompare != 0) return patientCompare;

        //if they're equal, compare by appointment date
        int dateCompare = app1.getDate().compareTo(app2.getDate());
        if(dateCompare != 0) return dateCompare;

        //if the above to prereqs are equal, compare by timeslot
        return app1.getTimeslot().compareTo(app2.getTimeslot());
    }

    private static Location getLocation(Appointment app, List<Provider> listP) {
        for(Provider provider : listP) {
            //System.out.println(provider);
            if(app.getProvider().equals(provider)) return provider.getLocation();
        }
        return null;
    }

    private static int compareCDT(Appointment app1, Appointment app2, List<Provider> listP) {
        //compare by county
        Location location1 = getLocation(app1, listP);
        //System.out.println(location1);
        Location location2 = getLocation(app2, listP);
        //System.out.println(location2);
        if(location1 == null || location2 == null) return 0;
        int countyCompare = location1.getCounty().compareToIgnoreCase(location2.getCounty());
        if(countyCompare != 0) return countyCompare;

        //if they're equal, compare by appointment date
        int dateCompare = app1.getDate().compareTo(app2.getDate());
        if(dateCompare != 0) return dateCompare;

        //if all are equal return
        if(countyCompare == 0 && dateCompare == 0
                && (app1.getTimeslot().compareTo(app2.getTimeslot()) == 0)) {
            return app1.getPatient().getProfile().getDob().compareTo(app2.getPatient().getProfile().getDob());
        }

        //if the above to prereqs are equal, compare by timeslot
        return app1.getTimeslot().compareTo(app2.getTimeslot());
    }


    private static void sortByDateTimeProvider(List<Appointment> list) {
        for(int i = 0; i < list.size()-1; i++) {
            for(int j = 0; j < list.size() - 1; j++) {
                if(compareDTP(list.get(j), list.get(j+1)) > 0) {
                    Appointment temp = list.get(j);
                    list.set(j, list.get(j+1));
                    list.set(j+1, temp);
                }
            }
        }
    }

    private static void sortByPatientDateTime(List<Appointment> list) {
        for(int i = 0; i < list.size()-1; i++) {
            for(int j = 0; j < list.size() - 1; j++) {
                if(comparePDT(list.get(j), list.get(j+1)) > 0) {
                    Appointment temp = list.get(j);
                    list.set(j, list.get(j+1));
                    list.set(j+1, temp);
                }
            }
        }
    }

    private static void sortByCountyDateTime(List<Appointment> listA, List<Provider> listP) {
        for(int i = 0; i < listA.size()-1; i++) {
            for(int j = 0; j < listA.size() - 1; j++) {
                if(compareCDT(listA.get(j), listA.get(j+1), listP) > 0) {
                    Appointment temp = listA.get(j);
                    listA.set(j, listA.get(j+1));
                    listA.set(j+1, temp);
                }
            }
        }
    }

    //to sort for PC command, but does not list the credit amounts, need to do that in clinic manager class
    public static void sortProviders(List<Provider> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1; j++) {
                if (list.get(j).compareTo(list.get(j+1)) > 0) {
                    Provider temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }

    //to sort for PS command, but does not list the credit amounts, need to do that in clinic manager class
    public static void sortPatients(List<Patient> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1; j++) {
                if (list.get(j).compareTo(list.get(j+1)) > 0) {
                    Patient temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }
} 



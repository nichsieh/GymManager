package gym;

import java.util.ArrayList;

/**
 * Fitness class includes methods to check in members for Pilates, Spinning, or Cardio class,
 * methods to drop members from certain fitness classes,
 * and printing the list of participants within a certain fitness class.
 * @author Kayla Kam and Nicole Hsieh.
 */

/*
- adding/dropping members into a fitness class.
- class should now have the instructor, name, and time.
 */
public class FitnessClass {
    private String fitnessclassname;

    private String instructorName;

    private Location gymlocation;

    private Time time;

    private ArrayList<Member> guests;
    private ArrayList<Member> classmembers;

    /**
     * Creating constructors of a fitness class.
     *
     * The array of class members will be empty since we are constructing the array first before adding members.
     * @param fitnessclassname
     * @param instructorName
     * @param time
     * @param gymlocation
     */
    public FitnessClass(String fitnessclassname, String instructorName, Time time, Location gymlocation){
        this.fitnessclassname = fitnessclassname;
        this.instructorName = instructorName.toUpperCase();
        this.gymlocation = gymlocation;
        this.time = time;
        this.classmembers = new ArrayList<>();
        this.guests = new ArrayList<>();
    }

    /**
     * Gets the time of a certain fitness class.
     *
     * @return time of Fitness Class.
     */
    public Time getTime(){
        return time;
    }

    /**
     * get location
     * @return location
     */
    public Location getLocation(){ return gymlocation; }

    /**
     * get instructor name
     * @return string
     */
    public String getInstructorName(){ return this.instructorName; }

    /**
     * Gets the name of the fitness class.
     *
     * @return fitnessclassname of Fitness Class.
     */
    public String getFitnessclassname(){
        return fitnessclassname;
    }

    /**
     * get class members
     * @return arraylist of members
     */
    public ArrayList<Member> getClassmembers(){ return classmembers; }

    /**
     * get guests
     * @return arraylist of members
     */
    public ArrayList<Member> getGuests(){ return guests; }

    /**
     * displays members in a given fitness class
     * @return string with all the members
     */
    private String displayingClassMembers(){
        String stringresult = "- Participants -";
        for(Member m : classmembers){
            stringresult += "\n " + m.toString();
        }
        return stringresult;
    }

    /**
     * displays guests in a given fitness class
     * @return string with all the guests
     */
    private String displayingGuests(){
        String guestsresult = "- Guests -";
        for(Member m : guests){
            guestsresult += "\n " + m.toString();
        }
        return guestsresult;
    }

    /**
     *
     * @return String in the format of the fitness class name, instructor, the time of the class, and the gym's location.
     */
    public String displayfitnessClasses(){
        String returning = converttoString();

        if(classmembers.isEmpty() == false){
            returning += "\n" + displayingClassMembers();
        }
        if (guests.isEmpty() == false){
            returning += "\n" + displayingGuests();
        }
        return returning;
    }

    /**
     * Converts the classmembers array list from Member[] to String[].
     * @return stringfitnessclass a String [] of the classmembers array.
     */
    public String converttoString(){
        String upperlocation = gymlocation.name().toUpperCase();
        if(time.minutes == 00){
            String returningstring = this.fitnessclassname + " - " + instructorName + ", " + time.hour + ":" + time.minutes + "0, " + upperlocation;
            return returningstring;
        }
        String returningstring = this.fitnessclassname + " - " + instructorName + ", " + time.hour + ":" + time.minutes + ", " + upperlocation;
        return returningstring;
    }

    /**
     * Adds a member to the fitness class list.
     *
     * @param member
     */
    public boolean addMember(Member member){
        if(!classmembers.contains(member)){
            classmembers.add(member);
            return true;
        }
        return false;
    }

    /**
     * add guest to a fitness class
     * @param member
     * @return boolean
     */
    public boolean addGuests(Member member){
        if(!guests.contains(member)){
            guests.add(member);
            member.removeGuest(member);
            return true;
        }
        return false;
    }

    /**
     * check in member to a class
     * @param guest
     * @param fitnessClass
     * @param member
     * @return string with any error messages
     */
    public static String checkIn(boolean guest, FitnessClass fitnessClass, Member member) {
        String returning = "";
//        return "check in";
        if(!guest) {
            boolean added = fitnessClass.addMember(member);
            if (added) {
                returning += (member.getFirstName() + " " + member.getLastName() + " checked in " + fitnessClass.getFitnessclassname() +
                        ", " + fitnessClass.getTime().hour + ":" + fitnessClass.getTime().minutes + ", " +
                        fitnessClass.getLocation()) + "\n";
                returning += "\n" + fitnessClass.displayfitnessClasses();
                return returning;
            } else {
                return member.getFirstName() + " " + member.getLastName() + " already checked in.";
            }
        } else {
            if((member).getGuest() != Family.NO_MORE_GUEST_PASS) {
                if (fitnessClass.getTime().minutes == 00){
                    returning += member.getFirstName() + " " + member.getLastName() + " (guest) checked in " + fitnessClass.getFitnessclassname() +
                            ", " + fitnessClass.getTime().hour + ":0" + fitnessClass.getTime().minutes + ", " + fitnessClass.getLocation();
                } else{
                    returning += member.getFirstName() + " " + member.getLastName() + " (guest) checked in " + fitnessClass.getFitnessclassname() +
                            ", " + fitnessClass.getTime().hour + ":" + fitnessClass.getTime().minutes + ", " + fitnessClass.getLocation() + "\n";
                }
                fitnessClass.addGuests(member);
                returning += fitnessClass.displayfitnessClasses();
                return returning;
            } else {
                return member.getFirstName() + " " + member.getLastName() + " ran out of guest pass.";
            }
        }
    }

    /**
     * drops class member
     * @param member
     * @return
     */
    public boolean dropClassMember(Member member){
        if(classmembers.contains(member)){
            classmembers.remove(member);
            return true;
        }
        return false;
    }

    /**
     * drops a guest from a class
     * @param member
     * @return
     */
    public boolean dropGuests(Member member){
        if(guests.contains(member)){
            guests.remove(member);
            return true;
        }
        return false;
    }

    /**
     * drops a member from a class
     * @param guest
     * @param fitnessClass
     * @param member
     * @return
     */
    public static String drop(boolean guest, FitnessClass fitnessClass, Member member){
        if(!guest) {
            if(fitnessClass.dropClassMember(member)) {
                return member.getFirstName() + " " + member.getLastName() + " done with the class.";
            } else {
                return member.getFirstName() + " " + member.getLastName() + " did not check in.";
            }
        } else{
            if(fitnessClass.dropGuests(member)) {
                member.addGuest(member);
                return member.getFirstName() + " " + member.getLastName() + " Guest done with the class.";
            } else {
                return member.getFirstName() + " " + member.getLastName() +" has no guests checked into this class";
            }
        }
    }

    /**
     * checks if two fitness classes are equal
     * @param object
     * @return
     */
    public boolean equals(FitnessClass object){

        if(!(object instanceof FitnessClass)) {
            return false;
        }

        if(object.getInstructorName().equalsIgnoreCase(instructorName) == false){
            return false;
        }
        else if(object.getFitnessclassname().equalsIgnoreCase(fitnessclassname) == false){
            return false;
        }

        return object.getLocation() == gymlocation;
    }

}
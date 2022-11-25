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
    public class FitnessClass2 {
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
        public FitnessClass2(String fitnessclassname, String instructorName, Time time, Location gymlocation){
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

        public Location getLocation(){ return gymlocation; }

        public String getInstructorName(){ return this.instructorName; }

        /**
         * Gets the name of the fitness class.
         *
         * @return fitnessclassname of Fitness Class.
         */
        public String getFitnessclassname(){
            return fitnessclassname;
        }

        public ArrayList<Member> getClassmembers(){ return classmembers; }

        public ArrayList<Member> getGuests(){ return guests; }

        private String displayingClassMembers(){
            String stringresult = "- Participants -";
            for(Member m : classmembers){
                stringresult += "\n " + m.toString();
            }
            return stringresult;
        }

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

        public boolean addGuests(Member member){
            if(!guests.contains(member)){
                guests.add(member);
                //what is the remove guest method for?
                //I think it's to deduct the amount of guest passes a member has after using it.
                member.removeGuest(member);
                return true;
            }
            return false;
        }

        public static void checkIn(String command, FitnessClass2 fitnessClass, Member member) {
            if(command.equalsIgnoreCase("C")) {
                if (fitnessClass.addMember(member)) {
                    System.out.println(member.getFirstName() + " " + member.getLastName() + " checked in " + fitnessClass.getFitnessclassname() +
                            ", " + fitnessClass.getTime().hour + ":" + fitnessClass.getTime().minutes + ", " + fitnessClass.getLocation() );
                    System.out.println(fitnessClass.displayfitnessClasses());

                } else {
                    System.out.println(member.getFirstName() + " " + member.getLastName() + " already checked in.");
                }
            } else {
                if((member).getGuest() != Family.NO_MORE_GUEST_PASS) {
                    System.out.println(member.getFirstName() + " " + member.getLastName() + " (guest) checked in " + fitnessClass.getFitnessclassname() +
                            ", " + fitnessClass.getTime().hour + ":" + fitnessClass.getTime().minutes + ", " + fitnessClass.getLocation());
                    fitnessClass.addGuests(member);
                    System.out.println(fitnessClass.displayfitnessClasses());
                } else {
                    System.out.println(member.getFirstName() + " " + member.getLastName() + " ran out of guest pass.");
                }
            }
        }

        public boolean dropClassMember(Member member){
            if(classmembers.contains(member)){
                classmembers.remove(member);
                return true;
            }
            return false;
        }

        public boolean dropGuests(Member member){
            if(guests.contains(member)){
                guests.remove(member);
                return true;
            }
            return false;
        }

        public static void drop(String command, FitnessClass2 fitnessClass, Member member){
            if(command.equalsIgnoreCase("D")) {
                if(fitnessClass.dropClassMember(member)) {
                    System.out.println(member.getFirstName() + " " + member.getLastName() + " done with the class.");
                } else {
                    System.out.println(member.getFirstName() + " " + member.getLastName() + " did not check in.");
                }
            } else{
                if(fitnessClass.dropGuests(member)) {
                    System.out.println(member.getFirstName() + " " + member.getLastName() + " Guest done with the class.");
                    member.addGuest(member);
                } else {
                    System.out.println(member.getFirstName() + " " + member.getLastName() +" has no guests checked into this class");
                }
            }
        }

        public boolean equals(FitnessClass2 object){

            if(!(object instanceof FitnessClass2)) {
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
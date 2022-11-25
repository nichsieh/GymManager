//package gym;
//
//import java.io.File;
//import java.util.Scanner;
//import java.util.StringTokenizer;
//
///**
// User Interface of Gym Manager
// ....
// @author Nicole Hsieh, Kayla Kam
// ...
// */
//public class GymManager {
//    private String inputCommand, command, firstName, lastName, dobString, locationString,
//            fitnessClassString, instructorName;
//    private Date dob, expiration;
//    private Member newMember;
//    private final MemberDatabase memberDatabase = new MemberDatabase();
//    private final ClassSchedule classSchedule = new ClassSchedule();
//
//    /**
//     * method that runs the Gym Manager
//     * Accessed by running RunProject1
//     */
//    public void run() {
//        System.out.println("Gym Manager running...\n");
//        Scanner input = new Scanner(System.in); // scans the command prompt
//        String retuning = "";
//
//        do { //quits when input is Q
//            inputCommand = input.nextLine(); // reads the first line of the command prompt
//
//            if ( inputCommand.isEmpty() ) {
//                System.out.println();
//                continue;
//            }
//
//            tokenize(inputCommand);
//
//            switch (command) {
//                case "Q":
//                    break;
//                default:
//                    performCommand(command, retuning); // takes the command and finds what it will perform
//            }
//
//        } while ( !inputCommand.equals("Q") );
//
//        System.out.println("Gym Manager Terminated.");
//    }
//
//
//
//    /**
//     * Tokenizes the input command line string. Separates it into the command, first name,
//     * last name, date of birth, expiration date, location depending on the amount of inputs in a
//     * given String
//     * @param inputCommand String taken from the command line
//     */
//    private void tokenize( String inputCommand ) {
//        StringTokenizer input = new StringTokenizer(inputCommand, " "); // creates a token seperated by a space
//        int count = input.countTokens(); // count the number of inputs
//
//        if (count == 1) { //P, PN, PC, PD, S, Q
//            command = input.nextToken();
//        } else if (count == 4) { // R
//            command = input.nextToken();
//            firstName = input.nextToken();
//            lastName = input.nextToken();
//            dobString = input.nextToken();
//
//            firstName = capitalize(firstName);
//            lastName = capitalize(lastName);
//            dob = new Date(dobString);
//        } else if (count == 5) { // A, AF, AP, R
//            command = input.nextToken();
//            firstName = input.nextToken();
//            lastName = input.nextToken();
//            dobString = input.nextToken();
//            locationString = input.nextToken();
//
//            firstName = capitalize(firstName);
//            lastName = capitalize(lastName);
//            dob = new Date(dobString);
//        } else if (count == 7){ // C, CG, D, CG
//            command = input.nextToken();
//            fitnessClassString = input.nextToken();
//            instructorName = input.nextToken();
//            locationString = input.nextToken();
//            firstName = input.nextToken();
//            lastName = input.nextToken();
//            dobString = input.nextToken();
//
//            fitnessClassString = capitalize(fitnessClassString);
//            instructorName = capitalize(instructorName);
//            firstName = capitalize(firstName);
//            lastName = capitalize(lastName);
//            dob = new Date(dobString);
//        } else {
//            command = input.nextToken();
//        }
//    }
//
//    /**
//     * method to capitalize a string
//     * @param word string
//     * @return String
//     */
//    private String capitalize(String word){
//        return word.substring(0,1).toUpperCase() + word.substring(1).toLowerCase();
//    }
//
//    /**
//     * checks if the date of birth is valid, if it is in the future and if it is 18 years or older.
//     * @param dob as a Date
//     * @return boolean true if it is a valid date, in the future and older than 18, false if it is not
//     */
//    private boolean inspectDOB( Date dob ){
//        if ( !Date.isValid(dob) ){
//            System.out.println("DOB " + dob + ": invalid calendar date!");
//            return false;
//        }
//        if ( !Date.isValidBirthday(dob) ){
//            System.out.println("DOB " + dob + ": must be 18 or older to join!");
//            return false;
//        }
//        if ( Date.inFuture(dob) ){
//            System.out.println("DOB " + dob + ": cannot be today or a future date!");
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * checks if the location is one of the gym location
//     * @param locationString as a String
//     * @return boolean true if it is a gym location, false if it is not
//     */
//    private static boolean inspectLocation( String locationString ){
//        if ( Location.validLocation(locationString) == null ){
//            System.out.println(locationString + "- invalid location!");
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * method to add member to the member database
//     * - inspects the DOB and if the location are valid
//     * - creates an expiration date and a member object depending on the membership type
//     * - checks if the member is in the memberdatabase already, if not adds to the memberdatabase
//     * @param command
//     */
//    private void addMember(String command){
//        if ( !inspectDOB(dob) ){ // inspectDOB method if returns false then the dob is not valid
//            return;
//        }
//        if ( !inspectLocation(locationString) ){ // inspectLocation method if returns false then the location is not valid
//            return;
//        }
//
//        // creates an expiration date for the new member depending on their membership
//        // creates the member object depending on the type of membership
//        if (command.equalsIgnoreCase("A")){
//            expiration = Date.createExpiration("Member");
//            newMember = new Member(firstName, lastName, dob, expiration, locationString);
//        } else if (command.equalsIgnoreCase("AF")){
//            expiration = Date.createExpiration("Family");
//            newMember = new Family(firstName, lastName, dob, expiration, locationString);
//        } else {
//            expiration = Date.createExpiration("Premium");
//            newMember = new Premium(firstName, lastName, dob, expiration, locationString);
//
//        }
//
//        // checks if the member created is in the memberdata base or not
//        boolean success;
//        success = memberDatabase.add( newMember );
//        if ( !success ){
//            System.out.println( newMember.getFirstName() + " " + newMember.getLastName() + " is already in the database." );
//        } else{
//            System.out.println( newMember.getFirstName() + " " + newMember.getLastName() + " added." );
//        }
//    }
//
//    /**
//     * Uses the command string taken from the method in tokenize and performs adding a member,
//     * removing a member if not calls fitnessClassCommand method.
//     * @param command a String that indicates the task
//     */
//    private String performCommand( String command, String returning ){
//        boolean success;
//        switch( command ) {
//            case "A":
//            case "AF":
//            case "AP":
//                addMember(command);
//                break;
//            case "R":
//                if ( MemberDatabase.getSize() <= 0 ){
//                    System.out.println("Member Database is empty!");
//                } else {
//                    Member newMember = new Member(firstName, lastName, dob, Date.createExpiration(""), "edison");
//                    // location can be any because we are only comparing the firstname, lastname and dob
//                    success = memberDatabase.remove(newMember);
//                    if ( !success ){
//                        System.out.println(firstName + " " + lastName + " is not in the database.");
//                    } else if ( success ){
//                        System.out.println(firstName + " " + lastName + " removed.");
//                    }
//                }
//                break;
//            default:
//                fitnessClassCommand(command, returning);
//                break;
//        }
//
//        return returning;
//    }
//
//    /**
//     * Uses the command string taken from the method in tokenize and displays fitness schedule,
//     * checks a member into a class, drops a class for a member, if not calls printCommand method.
//     * @param command a String that indicates the task
//     */
//    private String fitnessClassCommand( String command, String returning){
//        switch( command ) {
//            case "S":
//                if (classSchedule.isEmpty()){
//                    returning += "Fitness class schedule is empty.";
//                    System.out.println("Fitness class schedule is empty.");
//                } else{
//                    returning += "\n-Fitness classes-";
////                    System.out.println("\n-Fitness classes-");
//                    classSchedule.printfitnessclasses(returning);
////                    System.out.println("\n-end of list-");
//                    returning += "\n-end of list-";
//                }
//                break;
//            case "LS":
//                returning += classSchedule.loadFile(returning);
//                break;
//            case "LM":
//                returning += memberDatabase.loadFile(returning);
//                break;
//            case "C":
//            case "CG":
//                newMember = new Member(firstName, lastName, dob, Date.createExpiration(""), "edison");
//                checkIn(command, newMember);
//                break;
//            case "D":
//            case "DG":
//                drop(command);
//                break;
//            default:
//                printCommand(command, returning);
//                break;
//        }
//        return returning;
//    }
//
//    /**
//     * Uses the command string taken from the method in tokenize and prints the member database
//     * without sorting, sorting by county name and zip code, sorting by last name then first name,
//     * sorting by membership expiration date. If not the command is invalid.
//     * @param command a String that indicates the task
//     */
//    private String printCommand(String command, String returning){
//        boolean empty = false;
//        if (MemberDatabase.getSize() <= 0){
////            System.out.println("Member Database is empty!");
//            empty = true;
//        }
//
//        switch(command){
//            case "P":
//                if (!empty){
//                    returning = "\n-list of members-\n";
////                    System.out.println("\n-list of members-");
//                    returning += memberDatabase.print(returning);
////                    System.out.println("-end of list-");
//                    returning += "-end of list-\n";
//                } else{
//                    returning += "Member Database is empty!";
//                }
//                break;
//            case "PC":
//                if (!empty){
//                    returning = "\n-list of members sorted by county and zipcode-";
////                    System.out.println("\n-list of members sorted by county and zipcode-");
//                    returning += memberDatabase.printByCounty(returning);
////                    System.out.println("-end of list-");
//                    returning += "-end of list-\n";
//                } else{
//                    returning += "Member Database is empty!";
//                }
//                break;
//            case "PN":
//                if (!empty){
//                    returning = "\n-list of members sorted by last name, and first name-";
////                    System.out.println("\n-list of members sorted by last name, and first name-");
//                    returning += memberDatabase.printByName(returning);
////                    System.out.println("-end of list-");
//                    returning += "-end of list-\n";
//                } else{
//                    returning += "Member Database is empty!";
//                }
//                break;
//            case "PD":
//                if (!empty){
//                    returning = "\n-list of members sorted by membership expiration date-";
////                    System.out.println("\n-list of members sorted by membership expiration date-");
//                    returning += memberDatabase.printByExpirationDate(returning);
////                    System.out.println("-end of list-");
//                    returning += "-end of list-\n";
//                } else{
//                    returning += "Member Database is empty!";
//                }
//                break;
//            case "PF":
//                if (!empty){
//                    returning = "\n-list of members with membership fees-";
////                    System.out.println("\n-list of members with membership fees-");
//                    returning += memberDatabase.printWithMembership(returning);
////                    System.out.println("-end of list-");
//                    returning += "-end of list-\n";
//                } else{
//                    returning += "Member Database is empty!";
//                }
//                break;
//            default:
//                returning += "\n" + command + " is an invalid command!";
////                System.out.println(command + " is an invalid command!");
//                break;
//        }
//        return returning;
//    }
//
//
//
//
//
//
//
//    /**
//     * method to drop a fitness class
//     * @param command
//     */
//    private void drop(String command){
//        newMember = new Member(firstName, lastName, dob, Date.createExpiration(""), "edison");
//
//        if (!ClassSchedule.findClass(fitnessClassString, instructorName, locationString)){
//            return;
//        }
//        FitnessClass fitnessClass = ClassSchedule.getFitnessClass(fitnessClassString, instructorName, Location.getLocation(locationString));
//
//        if (memberDatabase.getMember(newMember) == null){
//            System.out.println(newMember.getFirstName() + " " + newMember.getLastName() + " " + newMember.getDOB() + " is not in the database.");
//            return;
//        }
//        newMember = MemberDatabase.getMember(newMember);
//
//        fitnessClass.drop(command, fitnessClass, newMember);
//
//    }
//
//    /**
//     * checkIn method it checks all the conditions first then does the check in for the fitness class
//     * @param command
//     * @param newMember
//     */
//    private void checkIn(String command, Member newMember){
//
//        boolean guest = false;
//        if (command == "CG"){
//            guest = true;
//        }
//
//        if ( !inspectDOB(dob) ){ // inspectDOB method if returns false then the dob is not valid
//            return;
//        }
//        if ( !inspectLocation(locationString) ){ // inspectLocation method if returns false then the location is not valid
//            return;
//        }
//
//        if (!ClassSchedule.findClass(fitnessClassString, instructorName, locationString)){
//            return;
//        }
//        FitnessClass fitnessClass = ClassSchedule.getFitnessClass(fitnessClassString, instructorName, Location.getLocation(locationString));
//
//        if (memberDatabase.getMember(newMember) == null){
//            System.out.println(newMember.getFirstName() + " " + newMember.getLastName() + " " + newMember.getDOB() + " is not in the database.");
//            return;
//        }
//        newMember = MemberDatabase.getMember(newMember);
//
//        if (!Date.inFuture(newMember.getExpiration())){
//            System.out.println(newMember.getFirstName() + " " + newMember.getLastName() + " " + newMember.getDOB() + " membership expired.");
//            return;
//        }
//        if(!membershipLocationValid(newMember, fitnessClass , guest)) {
//            return;
//        }
//        if(!guest && timeConflicts(newMember, fitnessClass)) {
//            return;
//        }
//        fitnessClass.checkIn(command, fitnessClass, newMember);
//
//    }
//
//    /**
//     * method to check if it is the correct location,
//     * if it is not a guest then check it in as a restricted location,
//     * if it is a guest then guest is checking in to the restricted location
//     *
//     * @param member
//     * @param fitnessClass
//     * @param guest
//     * @return true if it passes all valid, false is not
//     */
//    private boolean membershipLocationValid(Member member, FitnessClass fitnessClass, boolean guest) {
//        if(member.getLocation().equals(fitnessClass.getLocation())) {
//            return true;
//        }
//
//        if(!guest) {
//            if(!(member instanceof Family)){
//                System.out.println(member.getFirstName() + " " + member.getLastName() + " checking in " + fitnessClass.getLocation()
//                        + " - standard membership location restriction.");
//                return false;
//            }
//            return true;
//        } else { //if they are guest.
//            if (member instanceof Family){
//                //change location comparison method.
//                //if zip code is the same, return 0, meaning they are equal. Location is therefore equal.
//                if((member.getLocation().compareLocation(fitnessClass.getLocation())) == 0){
//                    return true;
//                }
//                else{
//                    System.out.println(member.getFirstName() + " " + member.getLastName() + "Guest checking in " + fitnessClass.getLocation() +
//                            " - guest location restriction.");
//                    return false;
//                }
//            } else {
//                System.out.println("Standard membership - guest check in is not allowed.");
//                return false;
//            }
//        }
//    }
//
//    /**
//     * method to check if there are any time Conflicts
//     * @param member
//     * @param fitnessclass
//     * @return boolean true if there is a time conflict, false if there isn't
//     */
//    private boolean timeConflicts(Member member, FitnessClass fitnessclass) {
//        for(FitnessClass fitnessClassptr : classSchedule.getClasses()) {
//            if(!fitnessClassptr.getClassmembers().contains(member) || fitnessClassptr.equals(fitnessclass)) {
//                continue;
//            }
//            if(fitnessClassptr.getTime() == fitnessclass.getTime()) {
//                // Time conflict - SPINNING - EMMA, 9:30, FRANKLIN, 08873, SOMERSET
//                System.out.println("Time conflict - " + fitnessclass.getFitnessclassname() + " - " + fitnessclass.getInstructorName() +
//                        ", " + fitnessclass.getTime().hour + ":" + fitnessclass.getTime().minutes + ", " + fitnessclass.getLocation() +
//                        ", 0" + fitnessclass.getLocation().getZip() + ", " + fitnessclass.getLocation().getCounty());
//                return true;
//            }
//        }
//        return false;
//    }
//}
package com.example.project3;

import gym.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.format.DateTimeFormatter;

/**
 * Gym Manager Controller. Has all the main components and methods that are linked directly to the GUI
 * replaced Gym Manager from project 2
 */
public class GymManagerController {
    @FXML
    private TextArea displayOutput, SortOutput, Input_Output_Message, Input_Output_Message_CheckIn;
    @FXML
    private TextField Input_First_Name, Input_Last_Name, Input_Location_of_Gym,
            Input_First_Name_CheckIn, Input_Last_Name_CheckIn, Input_Location_of_Gym_CheckIn, Input_Instructor_Name_CheckIn, Input_Class_Type_CheckIn;
    @FXML
    private RadioButton Standard_Membership, Family_Membership, Premium_Membership, Guest, NotGuest;
    @FXML
    private DatePicker Input_Date_of_Birth, Input_Date_of_Birth_CheckIn;

    private String firstName, lastName, dobString, locationString,
            fitnessClassString, instructorName;
    private Date dob, expiration;
    private Member newMember;
    MemberDatabase mlist = new MemberDatabase();
    ClassSchedule classSchedule = new ClassSchedule();

    /**
     * Load Members from memberlist.txt file
     * Activated from pressing "Load Members" Button
     */
    public void loadMembers(){
        String returning = "";
        returning += gym.MemberDatabase.loadFile();
        displayOutput.setText(returning);
    }

    /**
     * Display Members and their information with no sorting
     * Activated from pressing "Display Members" Button
     */
    public void displayMembers(){
        String returning = "";
        if (gym.MemberDatabase.isEmpty()){
            returning = "Member Database is empty!";
        } else{
            returning += "\n-list of members-" + gym.MemberDatabase.print(returning) + "\n-end of list-\n";
        }
        displayOutput.setText(returning);
    }

    /**
     * Display Members and their information, sorting it by last name then first name
     * Activated from pressing "Display Members by Name"
     */
    public void displayMembersByName(){
        String returning = gym.MemberDatabase.printByName();
        SortOutput.setText(returning);
    }

    /**
     * Display Members and their information, sorting it by county then zip code
     * Activated from pressing "Display Members by County"
     */
    public void displayMembersByCounty(){
        String returning = gym.MemberDatabase.printByCounty();
        SortOutput.setText(returning);
    }

    /**
     * Display Members and their information, sorting it by expiration date
     * Activated from pressing "Display Members by Expiration Date"
     */
    public void printByExpirationDate(){
        String returning = gym.MemberDatabase.printByExpirationDate();
        SortOutput.setText(returning);
    }

    /**
     * Display Members and their information with the Membership fee
     * Activated from pressing "Display Members with fees"
     */
    public void printWithMembership(){
        String returning = gym.MemberDatabase.printWithMembership();
        SortOutput.setText(returning);
    }

    /**
     * Load the fitness classes from a classSchedule.txt file
     * Activated from pressing "Load Classes"
     */
    public void loadClasses(){
        String returning = gym.ClassSchedule.loadFile();
        displayOutput.setText(returning);
    }

    /**
     * Display the fitness classes available
     * Activated from pressing "Display Classes"
     */
    public void displayClasses(){
        String returning = "";
        if (gym.ClassSchedule.isEmpty()){
            returning += "Fitness class schedule is empty.";
        } else{
            returning += "\n-Fitness classes-" + gym.ClassSchedule.printfitnessclasses(returning) + "\n-end of list-\n";
        }
        displayOutput.setText(returning);
    }

    /**
     * Checks that all the fields in the Check-In/Drop tab is filled or not
     * @return String with an error message or empty string if all fields are filled
     */
    private String checkFieldsAddRemove(){
        String returning = "";

        firstName = Input_First_Name.getText().trim();
        lastName = Input_Last_Name.getText().trim();
        locationString = Input_Location_of_Gym.getText().trim();

        if (firstName.isBlank()){
            returning += "First Name can't be blank";
        } else if (lastName.isBlank()){
            returning += "Last Name can't be blank";
        } else if (Input_Date_of_Birth.getValue() == null){
            returning += "Date of Birth can't be blank";
        } else if (locationString.isBlank()){
            returning += "Location can't be blank";
        } else if (!Standard_Membership.isSelected() && !Family_Membership.isSelected() && !Premium_Membership.isSelected()){
            returning += "Select a membership type";
        }

        if (!returning.isBlank()){
            return returning;
        }

        firstName = capitalize(firstName);
        lastName = capitalize(lastName);
        dobString = Input_Date_of_Birth.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        dob = new Date(dobString);
        locationString = capitalize(locationString);
        return returning;
    }

    /**
     * Adds member and their information to the member database or gives error message
     * Activated by pressing "Add Member" button
     */
    public void addMemberButton(){
        String returning = "";
        returning += checkFieldsAddRemove();
        if (returning.isBlank()){
            returning += addMember();
        }
        Input_Output_Message.setText(returning);
    }

    /**
     * checks all the conditions before adding the member
     * then calls memberdatabase to add the members
     * @return String with an error message or empty string if all conditions are met
     */
    private String addMember(){
        String returning = "";

        returning = inspectDOB(dob);
        if ( !returning.isBlank() ){ // inspectDOB method if returns false then the dob is not valid
            return returning;
        }
        returning = inspectLocation(locationString);
        if ( !returning.isBlank() ){ // inspectLocation method if returns false then the location is not valid
            return returning;
        }

        // creates an expiration date for the new member depending on their membership
        // creates the member object depending on the type of membership
        if (Standard_Membership.isSelected()){
            expiration = Date.createExpiration("Member");
            newMember = new Member(firstName, lastName, dob, expiration, locationString);
        } else if (Family_Membership.isSelected()){
            expiration = Date.createExpiration("Family");
            newMember = new Family(firstName, lastName, dob, expiration, locationString);
        } else if (Premium_Membership.isSelected()){
            expiration = Date.createExpiration("Premium");
            newMember = new Premium(firstName, lastName, dob, expiration, locationString);
        }

        // checks if the member created is in the member database or not
        boolean success;
        success = mlist.add( newMember );
        if ( !success ){
            returning += newMember.getFirstName() + " " + newMember.getLastName() + " is already in the database.";
        } else{
            returning += newMember.getFirstName() + " " + newMember.getLastName() + " added.";
        }

        return returning;
    }

    /**
     * Removes member and their information to the member database or gives error message
     * Activated by pressing "Remove Member" button
     */
    public void removeMemberButton(){
        String returning = "";
        returning += checkFieldsAddRemove();
        if (returning.isBlank()) {
            returning += removeMember();
        }
        Input_Output_Message.setText(returning);
    }

    /**
     * checks all the conditions before removing the member
     * then calls memberdatabase to remove the members
     * @return String with an error message or empty string if all conditions are met
     */
    private String removeMember(){
        String returning = "";

        if ( gym.MemberDatabase.isEmpty()){
            returning += "Member Database is empty!";
        } else {
            Member newMember = new Member(firstName, lastName, dob, Date.createExpiration(""), "edison");
            // location can be any because we are only comparing the firstname, lastname and dob
            boolean success = mlist.remove(newMember);
            if ( !success ){
                returning += firstName + " " + lastName + " is not in the database.";
            } else if ( success ){
                returning += firstName + " " + lastName + " removed.";
            }
        }
        return returning;
    }

    /**
     * Checks that all the fields in the Check-In/Drop tab is filled or not
     * @return String with an error message or empty string if all fields are filled
     */
    private String checkFieldsCheckInDrop(){
        String returning = "";

        firstName = Input_First_Name_CheckIn.getText().trim();
        lastName = Input_Last_Name_CheckIn.getText().trim();
        instructorName = Input_Instructor_Name_CheckIn.getText().trim();
        fitnessClassString = Input_Class_Type_CheckIn.getText().trim();
        locationString = Input_Location_of_Gym_CheckIn.getText().trim();

        if (firstName.isBlank()){
            returning += "First Name can't be blank";
        } else if (lastName.isBlank()){
            returning += "Last Name can't be blank";
        } else if (Input_Date_of_Birth_CheckIn.getValue() == null){
            returning += "Date of Birth can't be blank";
        } else if (instructorName.isBlank()){
            returning += "Instructor Name can't be blank";
        } else if (fitnessClassString.isBlank()){
            returning += "Class Type can't be blank";
        } else if (!Guest.isSelected() && !NotGuest.isSelected()){
            returning += "Check if the member is a guest or not";
        }else if (locationString.isBlank()){
            returning += "Location can't be blank";
        }

        if (!returning.isBlank()){
            return returning;
        }
        firstName = capitalize(firstName);
        lastName = capitalize(lastName);
        dobString = Input_Date_of_Birth_CheckIn.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        dob = new Date(dobString);
        instructorName = capitalize(instructorName);
        fitnessClassString = capitalize(fitnessClassString);
        locationString = capitalize(locationString);
        newMember = new Member(firstName, lastName, dob, Date.createExpiration(""), "edison");
        return returning;
    }

    /**
     * Checks In member and their information to the fitness class or gives error message
     * Activated by pressing "Check In Member" button
     */
    public void checkInButton(){
        String returning = "";
        returning += checkFieldsCheckInDrop();
        if (returning.isBlank()){
            returning += checkIn();
        }
        Input_Output_Message_CheckIn.setText(returning);
    }

    /**
     * checks all the conditions before checking in a member to the fitness class
     * @return String with an error message or empty string if all conditions are met
     */
    public String checkIn(){
        String returning = "";

        boolean guest = false;
        if (Guest.isSelected()){
            guest = true;
        } else if (NotGuest.isSelected()){
            guest = false;
        }

        returning = inspectDOB(dob);
        if ( !returning.isBlank() ){ // inspectDOB method if returns false then the dob is not valid
            return returning;
        }
        returning = inspectLocation(locationString);
        if ( !returning.isBlank() ){ // inspectLocation method if returns false then the location is not valid
            return returning;
        }

        returning = ClassSchedule.findClass(fitnessClassString, instructorName, locationString);
        if (!returning.isBlank()){
            return returning;
        }
        FitnessClass fitnessClass = ClassSchedule.getFitnessClass(fitnessClassString, instructorName, Location.getLocation(locationString));

        if (mlist.getMember(newMember) == null){
            return newMember.getFirstName() + " " + newMember.getLastName() + " " + newMember.getDOB() + " is not in the database.";
        }
        newMember = mlist.getMember(newMember);

        if (!Date.inFuture(newMember.getExpiration())){
            return newMember.getFirstName() + " " + newMember.getLastName() + " " + newMember.getDOB() + " membership expired.";
        }

        returning = membershipLocationValid(newMember, fitnessClass , guest);
        if(!returning.isBlank()) {
            return returning;
        }

        returning = timeConflicts(newMember, fitnessClass);
        if(!guest && !returning.isBlank()) {
            return returning;
        }

        returning += fitnessClass.checkIn(guest, fitnessClass, newMember);
        return returning;
    }

    /**
     * Checks if the location is valid for the membership type
     * @param member
     * @param fitnessClass
     * @param guest
     * @return String with an error message or empty string if all conditions are met
     */
    private String membershipLocationValid(Member member, FitnessClass fitnessClass, boolean guest) {
        if(member.getLocation().equals(fitnessClass.getLocation())) {
            return "";
        }

        if(!guest) {
            if(!(member instanceof Family)){
                return member.getFirstName() + " " + member.getLastName() + " checking in " + fitnessClass.getLocation()
                        + " - standard membership location restriction.";
            }
            return "";
        } else { //if they are guest.
            if (member instanceof Family){
                //change location comparison method.
                //if zip code is the same, return 0, meaning they are equal. Location is therefore equal.
                if((member.getLocation().compareLocation(fitnessClass.getLocation())) == 0){
                    return "";
                }
                else{
                    return member.getFirstName() + " " + member.getLastName() + " Guest checking in " + fitnessClass.getLocation() +
                            " - guest location restriction.";
                }
            } else {
                return "Standard membership - guest check in is not allowed.";
            }
        }
    }

    /**
     * Checks if the member has any time conflicts
     * @param member
     * @param fitnessclass
     * @return String with an error message or empty string if all conditions are met
     */
    private String timeConflicts(Member member, FitnessClass fitnessclass) {
        for(FitnessClass fitnessClassptr : classSchedule.getClasses()) {
            if(!fitnessClassptr.getClassmembers().contains(member) || fitnessClassptr.equals(fitnessclass)) {
                continue;
            }
            if(fitnessClassptr.getTime() == fitnessclass.getTime()) {
                // Time conflict - SPINNING - EMMA, 9:30, FRANKLIN, 08873, SOMERSET
                return "Time conflict - " + fitnessclass.getFitnessclassname() + " - " + fitnessclass.getInstructorName() +
                        ", " + fitnessclass.getTime().hour + ":" + fitnessclass.getTime().minutes + ", " + fitnessclass.getLocation() +
                        ", 0" + fitnessclass.getLocation().getZip() + ", " + fitnessclass.getLocation().getCounty();
            }
        }
        return "";
    }

    /**
     * Drops member and their information to the fitness class schedule or gives error message
     * Activated by pressing "Drop Member" button
     */
    public void dropButton(){
        String returning = "";
        returning += checkFieldsCheckInDrop();
        if (returning.isBlank()){
            returning += drop();
        }
        Input_Output_Message_CheckIn.setText(returning);
    }

    /**
     * checks all the conditions before dropping a member to the fitness class
     * @return
     */
    private String drop(){
        String returning = "";
        boolean guest = false;
        if (Guest.isSelected()){
            guest = true;
        } else if (NotGuest.isSelected()){
            guest = false;
        }
        returning += ClassSchedule.findClass(fitnessClassString, instructorName, locationString);
        if (!returning.isBlank()){
            return returning;
        }
        FitnessClass fitnessClass = ClassSchedule.getFitnessClass(fitnessClassString, instructorName, Location.getLocation(locationString));

        if (mlist.getMember(newMember) == null){
            return newMember.getFirstName() + " " + newMember.getLastName() + " " + newMember.getDOB() + " is not in the database.";
        }
        newMember = mlist.getMember(newMember);

        return fitnessClass.drop(guest, fitnessClass, newMember);
    }

    /**
     * capitalizes a string. Used to standardize all the text fields
     * @param word
     * @return capitalized String
     */
    private String capitalize(String word){
        return word.substring(0,1).toUpperCase() + word.substring(1).toLowerCase();
    }

    /**
     * checks if the date of birth is valid or not
     * @param dob
     * @return String with an error message or empty string if all conditions are met
     */
    private String inspectDOB( Date dob ){
        if ( !Date.isValid(dob) ){
            return "DOB " + dob + ": invalid calendar date!";
        }
        if ( !Date.isValidBirthday(dob) ){
            return "DOB " + dob + ": must be 18 or older to join!";
        }
        if ( Date.inFuture(dob) ){
            return "DOB " + dob + ": cannot be today or a future date!";
        }
        return "";
    }

    /**
     * checks if the location is valid or not
     * @param locationString
     * @return String with an error message or empty string if all conditions are met
     */
    private String inspectLocation( String locationString ){
        if ( Location.validLocation(locationString) == null ){
            return locationString + "- invalid location!";
        }
        return "";
    }
}

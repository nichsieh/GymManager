package gym;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * - prints classes/participants and time
 * - has a master array with all the fitness classes
 * - adds the fitnessclasses from the classSchedule.txt
 * @author Kayla Kam and Nicole Hsieh.
 */
public class ClassSchedule {

    private static FitnessClass[] fitnessclasses;

    private static int classnum;

    private static String[] listofinstructors = {"Emma", "Davis", "Jennifer", "Kim", "Denise"};

    private static String[] classtypes = {"Pilates", "Spinning", "Cardio"};

    /**
     * class schedule constructor
     */
    public ClassSchedule() {
        fitnessclasses = new FitnessClass[4];
        classnum = 0;
    }

    /**
     * get fitness class
     * @return
     */
    public FitnessClass[] returnfitnessclass() {
        return fitnessclasses;
    }

    /**
     * get class list
     * @param instructor
     * @param location
     * @param classtype
     * @return
     */
    public FitnessClass getclasslist(String instructor, Location location, String classtype) {
        FitnessClass newfitnessclass = new FitnessClass(classtype, instructor, Time.MORNING, location);

        for(FitnessClass fitnessclass : fitnessclasses){
            if(newfitnessclass.equals(fitnessclass)){
                return fitnessclass;
            }
        }
        return null;
    }

    /**
     * get instructors
     * @return
     */
    public static String[] getinstructors(){
        return listofinstructors;
    }

    /**
     * get type of classes
     * @return
     */
    public static String[] getTypesofClasses(){
        return classtypes;
    }

    /**
     * method to check if the class schedule is empty
     * @return
     */
    public static boolean isEmpty(){
        return classnum == 0;
    }

    /**
     * method to add a fitness class
     * @param newfitnessclass
     */
    private static void addFitnessClass(FitnessClass newfitnessclass){
        if(fitnessclasses[fitnessclasses.length-1] != null){
            growarray();
        }
        for(int i = 0; i < fitnessclasses.length; i++){
            if(fitnessclasses[i] == null){
                fitnessclasses[i] = newfitnessclass;
                break;
            }
        }
        classnum++;
    }

    /**
     * method to loaf file from a classSchedule.txt
     */
    public static String loadFile(){
        String returning = "";
        File file = new File("src/main/classSchedule.txt");
        if (file.exists()){
            Scanner reader;
            try {
                reader = new Scanner(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            while (reader.hasNextLine()) {
                // Pilates Jennifer morning Bridgewater
                String inputLine = reader.nextLine();
                StringTokenizer input = new StringTokenizer(inputLine, " ");
                String fitnessclass = input.nextToken();
                String instructorname = input.nextToken();
                String time = input.nextToken();
                String locationString = input.nextToken();

                FitnessClass newfitnessclass = new FitnessClass(fitnessclass, instructorname, Time.getTime(time), Location.getLocation(locationString));
                addFitnessClass(newfitnessclass);
            }

            returning += "\n-list of members loaded-" + printfitnessclasses(returning) + "\n-end of list-\n";

        } else{
//            System.out.println("file doesn't exist");
            returning += "file doesn't exist\n";
        }
        return returning;
    }

    /**
     * method to grow the array from the fitnessclasses array
     */
    private static void growarray(){
        FitnessClass[] temp = new FitnessClass[fitnessclasses.length+4];
        for(int i = 0; i < fitnessclasses.length; i++){
            temp[i] = fitnessclasses[i];
        }
        fitnessclasses = temp;
    }

    /**
     * method to print out the fitnessclasses
     */
    public static String printfitnessclasses(String returning){
        if (isEmpty()){
            returning += "Fitness class schedule is empty.";
        } else{
            for(int i = 0; i < fitnessclasses.length; i++){
                if (fitnessclasses[i] != null){
                    returning += "\n" + fitnessclasses[i].displayfitnessClasses();
                }
            }
        }
        return returning;
    }

    /**
     * method to get a specific class
     * @return
     */
    public FitnessClass[] getClasses() {
        FitnessClass[] temporaryArray = new FitnessClass[classnum];

        int i = 0;
        for(FitnessClass fitnessClass : fitnessclasses) {
            if(fitnessClass != null) {
                temporaryArray[i] = fitnessClass;
                i++;
            }
        }

        return temporaryArray;
    }

    /**
     * method to get a certain fitnessclass
     * @param className
     * @param instructor
     * @param location
     * @return
     */
    public static FitnessClass getFitnessClass(String className, String instructor, Location location) {
        FitnessClass course = new FitnessClass(className, instructor, Time.MORNING, location);
        for(FitnessClass fitnessclass : fitnessclasses) {
            if(course.equals(fitnessclass)) {
                return fitnessclass;
            }
        }
        return null;
    }

    /**
     * method to find a specific class and check if the intructor, class and location are valid
     * @param classNameInput
     * @param instructorInput
     * @param locationInput
     * @return
     */
    public static String findClass(String classNameInput, String instructorInput, String locationInput) {
        boolean validInstructor = false;
        for(String instructor:listofinstructors) {
            if(instructorInput.equalsIgnoreCase(instructor)) {
                validInstructor = true;
                break;
            }
        }
        if(!validInstructor) {
            return instructorInput + " - instructor does not exist.";
        }

        boolean validClass = false;
        for(String className:classtypes) {
            if(classNameInput.equalsIgnoreCase(className)) {
                validClass = true;
                break;
            }
        }
        if(!validClass) {
            return classNameInput + " - class does not exist.";
        }

        Location classLocation;
        classLocation = Location.getLocation(locationInput);
        if(classLocation == null) {
            return locationInput + " - invalid location.";
        }

        if((getFitnessClass(classNameInput, instructorInput, classLocation)) == null) {
            return classNameInput + " by " + instructorInput + " does not exist at " + locationInput;
        } else {
            return "";
        }
    }

}
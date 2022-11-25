package gym;

import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * Date class includes year, month, and day in the constructors.
 *
 * The methods within this class contain validating the date (year, month, and day), comparing dates,
 * and checking if the dates are equal.
 *
 * This class imports the Calendar class and the String Tokenizer class.
 *
 * @author Kayla Kam
 * @author Nicole Hsieh.
 */
public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;

    /**
     * creates an object with today's date
     */
    public Date() {
        Calendar today = Calendar.getInstance();
        this.year = today.get(Calendar.YEAR);
        this.month = today.get(Calendar.MONTH) + 1;
        this.day = today.get(Calendar.DATE);
    }

    /**
     * Date object constructor. Takes a string in format of mm/dd/yyyy and creates the Date object.
     * @param date as a String in the format mm/dd/yyyy
     */
    public Date(String date) {
        StringTokenizer newDate = new StringTokenizer( date, "/" );
        this.month = Integer.parseInt(newDate.nextToken());
        this.day = Integer.parseInt(newDate.nextToken());
        this.year = Integer.parseInt(newDate.nextToken());
    }

    /**
     * Get the year.
     * @return year as int.
     */
    public int getYear(){
        return year;
    }

    /**
     * Set the year.
     * @param year as int.
     */
    public void setYear(int year){
        this.year = year;
    }

    /**
     * Get the month.
     * @return month as int.
     */
    public int getMonth(){
        return month;
    }

    /**
     * Set the month.
     * @param month as int.
     */
    public void setMonth(int month){
        this.month = month;
    }

    /**
     * Get the day.
     * @return day as int.
     */
    public int getDay(){
        return day;
    }

    /**
     * Set the day.
     * @param day as int.
     */
    public void setDay(int day){
        this.day = day;
    }

    /**
     * toString method to show a date.
     * It is shown in the following format: 3/30/2023
     * @return string of a date
     */
    public String toString(){
        return month + "/" + day + "/" + year;
    }

    /**
     * compares the last and first names of 2 members.
     * @param date the object to be compared.
     * @return integer. If 1 then it is greater than, if -1 then it is less than, if 0 they are the same.
     */
    @Override
    public int compareTo(Date date) {
        if (this.year > date.getYear())
            return 1;
        else if (this.year < date.getYear())
            return -1;
        else { //years are the same
            if (this.month > date.getMonth())
                return 1;
            else if (this.month < date.getMonth())
                return -1;
            else { // years and month are the same
                if (this.day > date.getDay())
                    return 1;
                else if (this.day < date.getDay())
                    return -1;
            }
        }
        return 0; // same date
    }

    /**
     * compares a given date to make sure the month, day, and year is the same.
     * @param obj from the Object class.
     * @return True if the given date object is equal to the official date it is being compared to.
     *         False if the condition isn't met or the object is null.
     */
    public boolean equals(Object obj){
        if (this == null){
            return false;
        }
        if (obj instanceof Date){
            Date date = (Date) obj;
            if (this == date){
                return true;
            } if (day == date.day && month == date.month && year == date.year){
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the given year is a leap year.
     * @param testYear which is an int that represents a given year.
     * @return true if the year is detected to be a leap. False if the year is not a leap year.
     */
    private static boolean isLeapYear(int testYear){

        if(testYear % 4 == 0){
            if(testYear % 100 == 0){
                if(testYear % 400 != 0){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Check if the date is valid.
     * @param date from Date
     * @return boolean. If true then the date is valid, if false the date is not valid.
     */
    public static boolean isValid(Date date) { //check if a date is a valid calendar date
        Calendar currDate = Calendar.getInstance();

        final int yearMax = currDate.get(Calendar.YEAR);
        final int currMonth = currDate.get(Calendar.MONTH) + 1;
        final int currDay = currDate.get(Calendar.DAY_OF_MONTH);

        int daysPerMonthMax = 0;

        int testMonth = date.getMonth();
        int testYear = date.getYear();
        int testDay = date.getDay();
        boolean isLeapYear = false;

        if(testMonth < 1 || testMonth > 12){
            return false;
        }
        if(testYear < 1900){
            return false;
        }

        final int JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV, DEC;
        JAN = 1;
        FEB = 2;
        MAR = 3;
        APR = 4;
        MAY = 5;
        JUN = 6;
        JUL = 7;
        AUG = 8;
        SEP = 9;
        OCT = 10;
        NOV = 11;
        DEC = 12;

        final int[] monthsWith31Days = {JAN, MAR, MAY, JUL, AUG, OCT, DEC};
        final int[] monthsWith30Days = {APR, JUN, SEP, NOV};

        if(testMonth == FEB){
            isLeapYear = isLeapYear(testYear);
            if(isLeapYear){
                daysPerMonthMax = 29;
            }
            else{
                daysPerMonthMax = 28;
            }
        }

        for(int i = 0; i < monthsWith31Days.length; i++){
            if(testMonth == monthsWith31Days[i]){
                daysPerMonthMax = 31;
                break;
            }
        }
        for(int i = 0; i < monthsWith30Days.length; i++){
            if(testMonth == monthsWith30Days[i]){
                daysPerMonthMax = 30;
                break;
            }
        }

        if(testDay < 1 || testDay > daysPerMonthMax){
            return false;
        }

        return true;
    }

    /**
     * Check if the date was 18 years ago.
     * @param dob date of birth in Date
     * @return boolean if true the date is 18 years ago or more,
     *         if false then the date is within 18 years
     */
    public static boolean isValidBirthday(Date dob){
        Calendar currDate = Calendar.getInstance();
        int currYear = currDate.get(Calendar.YEAR);
        int dobYear = dob.getYear();
        int age = currYear - dobYear;
        int currMonth = currDate.get(Calendar.MONTH) + 1;
        int dobMonth = dob.getMonth();
        if (dobMonth > currMonth) {
            age--;
        } else if (currMonth == dobMonth) {
            int currDay = currDate.get(Calendar.DAY_OF_MONTH);
            int dobDay = dob.getDay();
            if (dobDay > currDay) {
                age--;
            }
        }

        if (age < 18 && age >= 0){
            return false;
        }
        return true;
    }

    /**
     * Check if the date is ahead of today's date.
     * @param date that is going to be compared with today.
     * @return boolean if true the date is in the future,
     * if false then the date is not the future.
     */
    public static boolean inFuture(Date date){
        Calendar currDate = Calendar.getInstance();
        int currYear = currDate.get(Calendar.YEAR);
        int testYear = date.getYear();
        if (currYear > testYear){
            return false;
        } else if ( currYear == testYear ){
            int currMonth = currDate.get(Calendar.MONTH) + 1;
            int testMonth = date.getMonth();
            if (currMonth > testMonth){
                return false;
            } else if (currMonth == testMonth){
                int currDay = currDate.get(Calendar.DAY_OF_MONTH);
                int testDay = date.getDay();
                if (currDay > testDay){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * create expiration date depending on the type of membership
     * @param type
     * @return
     */
    public static Date createExpiration(String type){
        Date expiration = new Date();
        if (type.equalsIgnoreCase("Premium")){
            expiration.year += 1;
        } else{
            expiration.month += 3;
            if (expiration.month > 12){
                int difference = expiration.month - 12;
                expiration.year += 1;
                expiration.month = difference;
            }
        }
        return expiration;
    }

    /**
     * Testbed main for the Date class
     * @param args which is the input for commands.
     */
    public static void main( String[] args ){
        System.out.println("Testbed for the isValid method.");
        int count = 0;
        boolean expectedOutcome;
        boolean outcome;
        Date testDate;

        System.out.println("Test " + count + ": year before 1900.");
        testDate = new Date("12/1/1899");
        expectedOutcome = false;
        outcome = isValid(testDate);
        testCheck(expectedOutcome, outcome);
        count++;

        System.out.println("Test " + count + ": month is greater than 12");
        testDate = new Date("15/1/1999");
        expectedOutcome = false;
        outcome = isValid(testDate);
        testCheck(expectedOutcome, outcome);
        count++;

        System.out.println("Test " + count + ": month is 0");
        testDate = new Date("0/1/1999");
        expectedOutcome = false;
        outcome = isValid(testDate);
        testCheck(expectedOutcome, outcome);
        count++;

        System.out.println("Test " + count + ": month is a negative number");
        testDate = new Date("-12/1/2000");
        expectedOutcome = false;
        outcome = isValid(testDate);
        testCheck(expectedOutcome, outcome);
        count++;

        System.out.println("Test " + count + ": Months that only have 30 days shouldn't allow a date with 31 days");
        testDate = new Date("4/31/2000");
        expectedOutcome = false;
        outcome = isValid(testDate);
        testCheck(expectedOutcome, outcome);
        count++;

        System.out.println("Test " + count + ": Months that have 31 days should allow a date with 31 days");
        testDate = new Date("1/31/2000");
        expectedOutcome = true;
        outcome = isValid(testDate);
        testCheck(expectedOutcome, outcome);
        count++;

        System.out.println("Test " + count + ": Months that have 31 days shouldn't allow a date with 32 days");
        testDate = new Date("1/32/2000");
        expectedOutcome = false;
        outcome = isValid(testDate);
        testCheck(expectedOutcome, outcome);
        count++;

        System.out.println("Test " + count + ": February on a leap year should allow day 29");
        testDate = new Date("2/29/2024");
        expectedOutcome = true;
        outcome = isValid(testDate);
        testCheck(expectedOutcome, outcome);
        count++;

        System.out.println("Test " + count + ": February not on a leap year shouldn't allow day 29");
        testDate = new Date("2/29/2001");
        expectedOutcome = false;
        outcome = isValid(testDate);
        testCheck(expectedOutcome, outcome);
        count++;

        System.out.println("Test " + count + ": February not on a leap year should allow day 28");
        testDate = new Date("2/28/2001");
        expectedOutcome = true;
        outcome = isValid(testDate);
        testCheck(expectedOutcome, outcome);
        count++;

        System.out.println("Test " + count + ": Should not allow negative days");
        testDate = new Date("1/-5/2001");
        expectedOutcome = false;
        outcome = isValid(testDate);
        testCheck(expectedOutcome, outcome);
        count++;

        System.out.println("Test " + count + ": Should not allow day 0");
        testDate = new Date("1/0/2001");
        expectedOutcome = false;
        outcome = isValid(testDate);
        testCheck(expectedOutcome, outcome);
        count++;

        System.out.println("Test " + count + ": Should allow a valid date");
        testDate = new Date("5/21/2001");
        expectedOutcome = true;
        outcome = isValid(testDate);
        testCheck(expectedOutcome, outcome);
    }

    /**
     * A method called by testbed main to see if a outcome and the expected outcome are the same. If so, it prints pass.
     * If not, it prints out fail.
     * @param expectedOutcome
     * @param outcome
     */
    private static void testCheck(boolean expectedOutcome, boolean outcome) {
        if (expectedOutcome == outcome) {
            System.out.println("PASS");
        } else {
            System.out.println("FAIL");
        }
    }
}
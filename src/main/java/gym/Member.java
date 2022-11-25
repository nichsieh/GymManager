package gym;

/**
 * This class instantiates a Standard Member which will be held in the MemberDatabase.
 * Each member will have a first name, last name, date of birth, membership expiration date and gym location.
 * Methods in this class include:
 *  - getting and setting first names, last names, date of birth, membership expiration date and gym location.
 *  - textual representation of a member
 *  - equalsTo()
 *  - compareTo()
 * @author Nicole Hsieh, Kayla Kam
 */
public class Member implements Comparable<Member>{
    private String fname;
    private String lname;
    private Date dob;
    private Date expiration;
    private Location location;
    private int guest;
    private static double fee;
    private boolean oneTimeFeePaid = false;
    private final double oneTimeFee = 29.99;
    private final double monthlyFee = 39.99;

    /**
     * Standard Member object constructor.
     * @param fname first name as a String
     * @param lname last name as a String
     * @param dob date of birth as a Date
     * @param expiration expiration date as a Date
     * @param location location as Location
     */
    public Member(String fname, String lname, Date dob, Date expiration, String location){
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
        this.expiration = expiration;
        if (location == null){
            this.location = Location.EDISON;
        } else{
            this.location = Location.validLocation(location);
        }
        this.guest = 0;
        this.fee = membershipFee();
    }

    /**
     * Get the member's First Name.
     * @return member's first name as String.
     */
    public String getFirstName(){
        return fname;
    }

    /**
     * Set the member's First Name.
     * @param fname - member's first name as String.
     */
    public void setFirstName(String fname){
        this.fname = fname;
    }

    /**
     * Get the member's Last Name.
     * @return member's last name as String.
     */
    public String getLastName(){
        return lname;
    }

    /**
     * Set the member's Last Name.
     * @param lname - member's last name as String.
     */
    public void setLastName(String lname){
        this.lname = lname;
    }

    /**
     * Get the member's Date of Birth.
     * @return dob - member's date of birth as Date.
     */
    public Date getDOB(){
        return dob;
    }

    /**
     * Set the member's Date of Birth.
     * @param dob - member's date of birth as Date.
     */
    public void setDOB(Date dob){
        this.dob = dob;
    }

    /**
     * Get the member's membership Expiration Date.
     * @return expiration - member's membership Expiration Date as Date.
     */
    public Date getExpiration(){
        return expiration;
    }

    /**
     * Set the member's Date of Birth.
     * @param expiration - member's membership Expiration Date as Date.
     */
    public void setExpiration(Date expiration){
        this.expiration = expiration;
    }

    /**
     * Get the member's Gym Location.
     * @return location - member's Gym Location as Location.
     */
    public Location getLocation(){
        return location;
    }

    /**
     * Set the member's Gym Location.
     * @param location - member's Gym Location as Location.
     */
    public void setLocation(String location){
        this.location = Location.validLocation(location);
    }

    /**
     * method to get the number of guests
     * @return int - number of guests
     */
    public int getGuest(){
        return guest;
    }

    /**
     * method sets a number of guests
     * @param guest
     */
    public void setGuest(int guest){
        this.guest = guest;
    }

    /**
     * method to get the membership fee
     * @return
     */
    public double getMembershipFee(){
        return fee;
    }

    /**
     * method to removes a guest pass from the membership
     */
    public void removeGuest(Member member){
        member.setGuest(member.getGuest() - 1);
    }

    /**
     * method to adds a guest pass from the membership
     */
    public void addGuest(Member member){
        member.setGuest(member.getGuest() + 1);
    }

    /**
     * toString method to show the member's first name, last name, date of birth,
     * membership expiration and gym location.
     * It is shown in the following format:
     *      John Doe, DOB: 1/20/2004, Membership expires 3/30/2023, Location: BRIDGEWATER, 08807, SOMERSET
     * @returns the member object as a string
     */
    @Override
    public String toString() { //+ ", Location: " + location.toString()
        return fname + " " + lname + ", DOB: " + dob.toString() + ", Membership expires "
                + expiration.toString() + ", Location: " + location + ", 0" +
                (int)location.getZip() + ", " + location.getCounty();
    }

    /**
     * method returns true if the two first names, last names and dates of birth are equal.
     * @param obj of the person to be compared
     * @return true if the two people have the same first name, last name and date of birth, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Member){
            Member member = (Member) obj;
            if (!this.fname.toLowerCase().equals(member.fname.toLowerCase())) {
                return false;
            } if (!this.lname.toLowerCase().equals(member.lname.toLowerCase())){
                return false;
            } if (!this.dob.equals(member.getDOB())){
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * compares the last and first names of 2 members.
     * @param member the object to be compared.
     * @return integer. If 1 then member2 is before member1, if -1 then member1 is before member2, if 0 they are the same.
     */
    @Override
    public int compareTo(Member member) {
        String member1 = this.lname + " " + this.fname;
        String member2 = member.lname + " " + member.fname;
        int comparison = member1.compareTo(member2);
        if (comparison == 0){
            comparison = 0;
        } else if (comparison > 0){
            comparison = 1;
        } else{
            comparison = -1;
        }
        return comparison;
    }

    /**
     * method to intialize the membership fee for the standard membership
     * @return double
     */
    public double membershipFee() {
        if (oneTimeFeePaid == true){
            fee = monthlyFee*3;
        } else {
            fee = monthlyFee*3 + oneTimeFee;
        }
        return fee;
    }

    /**
     * Testbed main for the Member class
     */
    public static void main( String[] args ){
        System.out.println("Testbed for membershipFee method.");
        int count = 1;
        Member member;

        System.out.println("Test " + count + ": ");
        member = new Member("Mary", "Lindsey", new Date("12/1/1989"), Date.createExpiration("Member"), "Franklin");
        System.out.printf("%.2f\n", member.membershipFee());

        System.out.println("Testbed for the compareTo method.");
        count = 1;
        int expectedOutcome;
        int outcome;
        Member member1;
        Member member2;

        System.out.println("Test " + count + ": member1's last name comes before member2's last name");
        member1 = new Member("Mary", "Lindsey", new Date("12/1/1989"), new Date("5/31/2023"), "Franklin");
        member2 = new Member("April", "March", new Date("3/31/1990"), new Date("6/30/2023"), "piscataway");
        expectedOutcome = -1;
        outcome = member1.compareTo(member2);
        testCheck(expectedOutcome, outcome);
        count++;

        System.out.println("Test " + count + ": member2's last name comes before member1's last name");
        member1 = new Member("April", "March", new Date("3/31/1990"), new Date("6/30/2023"), "piscataway");
        member2 = new Member("Roy", "Brooks", new Date("9/9/1977"), new Date("9/30/2023"), "somerville");
        expectedOutcome = 1;
        outcome = member1.compareTo(member2);
        testCheck(expectedOutcome, outcome);
        count++;

        System.out.println("Test " + count + ": both members have the same last name, member1's first name comes before member2's first name");
        member1 = new Member("Kate", "Lindsey", new Date("7/15/1977"), new Date("12/31/2023"), "somerville");
        member2 = new Member("Mary", "Lindsey", new Date("12/1/1989"), new Date("5/31/2023"), "Franklin");
        expectedOutcome = -1;
        outcome = member1.compareTo(member2);
        testCheck(expectedOutcome, outcome);
        count++;

        System.out.println("Test " + count + ": both members have the same last name, member2's first name comes before member1's first name");
        member1 = new Member("Mary", "Lindsey", new Date("7/15/1977"), new Date("12/31/2023"), "somerville");
        member2 = new Member("Kate", "Lindsey", new Date("12/1/1989"), new Date("5/31/2023"), "Franklin");
        expectedOutcome = 1;
        outcome = member1.compareTo(member2);
        testCheck(expectedOutcome, outcome);
        count++;

        System.out.println("Test " + count + ": both members have the same last name, both members have the same first name");
        member1 = new Member("Roy", "Brooks", new Date("9/9/1977"), new Date("9/30/2023"), "somerville");
        member2 = new Member("Roy", "Brooks", new Date("9/9/1977"), new Date("9/30/2023"), "somerville");
        expectedOutcome = 0;
        outcome = member1.compareTo(member2);
        testCheck(expectedOutcome, outcome);
    }

    /**
     * A method that prints out if the expected outcome and the outcome is the same.
     *
     * @param expectedOutcome
     * @param outcome
     */
    private static void testCheck(int expectedOutcome, int outcome){
        if (expectedOutcome == outcome){
            System.out.println("PASS");
        } else {
            System.out.println("FAIL");
        }
    }

}
package gym;

/**
 * This class is the Family membership object extended from the Member object
 */
public class Family extends Member{
    private int guest;
    private static double fee;
    public static int NO_MORE_GUEST_PASS = 0;
    private boolean oneTimeFeePaid = false;
    private final double oneTimeFee = 29.99;
    private final double monthlyFee = 59.99;

    /**
     * Family Membership object constructor.
     *
     * @param fname      first name as a String
     * @param lname      last name as a String
     * @param dob        date of birth as a Date
     * @param expiration expiration date as a Date
     * @param location   location as Location
     */
    public Family(String fname, String lname, Date dob, Date expiration, String location) {
        super(fname, lname, dob, expiration, location);
        this.guest = 1;
        this.fee = membershipFee();
    }

    /**
     * method to get the membership fee
     * @return
     */
    public double getMembershipFee(){
        return fee;
    }

    /**
     * method to get the number of guests
     * @return int - number of guests
     */
    @Override
    public int getGuest(){
        return guest;
    }

    /**
     * method sets a number of guests
     * @param guest
     */
    @Override
    public void setGuest(int guest){
        this.guest = guest;
    }

    /**
     * method to create a string representation of the Family membership
     * @return String
     */
    @Override
    public String toString() {
        if (this.getClass().getName().compareTo("gym.Premium") == 0){
            return super.toString();
        } else {
            return super.toString() + ", (Family) Guess-pass remaining: " + this.guest;
        }
    }

    /**
     * method to return the initial fee for the family membership
     * @return double
     */
    @Override
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
        Family member;

        System.out.println("Test " + count + ": ");
        member = new Family("Mary", "Lindsey", new Date("12/1/1989"), Date.createExpiration("Family"), "Franklin");
        System.out.println(member.membershipFee());

        System.out.println("Testbed for the removeGuest method.");
        count = 1;
        System.out.println("Test " + count + ": ");
        member = new Family("Mary", "Lindsey", new Date("12/1/1989"), Date.createExpiration("Family"), "Franklin");
        System.out.println(member.toString());
        member.removeGuest(member);
        System.out.println(member.toString());

        System.out.println("Testbed for the addGuest method.");
        count = 1;
        System.out.println("Test " + count + ": ");
        member = new Family("April", "March", new Date("3/31/1990"), Date.createExpiration("Family"), "piscataway");
        System.out.println(member.toString());
        member.addGuest(member);
        System.out.println(member.toString());

        System.out.println("Testbed for the compareTo method.");
        count = 1;
        int expectedOutcome;
        int outcome;
        Family member1;
        Family member2;

        System.out.println("Test " + count + ": member1's last name comes before member2's last name");
        member1 = new Family("Mary", "Lindsey", new Date("12/1/1989"), new Date("5/31/2023"), "Franklin");
        member2 = new Family("April", "March", new Date("3/31/1990"), new Date("6/30/2023"), "piscataway");
        expectedOutcome = -1;
        outcome = member1.compareTo(member2);
        testCheck(expectedOutcome, outcome);
        count++;

        System.out.println("Test " + count + ": member2's last name comes before member1's last name");
        member1 = new Family("April", "March", new Date("3/31/1990"), new Date("6/30/2023"), "piscataway");
        member2 = new Family("Roy", "Brooks", new Date("9/9/1977"), new Date("9/30/2023"), "somerville");
        expectedOutcome = 1;
        outcome = member1.compareTo(member2);
        testCheck(expectedOutcome, outcome);
        count++;

        System.out.println("Test " + count + ": both members have the same last name, member1's first name comes before member2's first name");
        member1 = new Family("Kate", "Lindsey", new Date("7/15/1977"), new Date("12/31/2023"), "somerville");
        member2 = new Family("Mary", "Lindsey", new Date("12/1/1989"), new Date("5/31/2023"), "Franklin");
        expectedOutcome = -1;
        outcome = member1.compareTo(member2);
        testCheck(expectedOutcome, outcome);
        count++;

        System.out.println("Test " + count + ": both members have the same last name, member2's first name comes before member1's first name");
        member1 = new Family("Mary", "Lindsey", new Date("7/15/1977"), new Date("12/31/2023"), "somerville");
        member2 = new Family("Kate", "Lindsey", new Date("12/1/1989"), new Date("5/31/2023"), "Franklin");
        expectedOutcome = 1;
        outcome = member1.compareTo(member2);
        testCheck(expectedOutcome, outcome);
        count++;

        System.out.println("Test " + count + ": both members have the same last name, both members have the same first name");
        member1 = new Family("Roy", "Brooks", new Date("9/9/1977"), new Date("9/30/2023"), "somerville");
        member2 = new Family("Roy", "Brooks", new Date("9/9/1977"), new Date("9/30/2023"), "somerville");
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

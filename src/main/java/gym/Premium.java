package gym;

/**
 * This class is the Premium membership object extended from the Member object
 */
public class Premium extends Family {
    private int guest;
    private static double fee;
    private boolean oneTimeFeePaid = false;
    private final double oneTimeFee = 0;
    private final double monthlyFee = 59.99;

    /**
     * Premium Member object constructor.
     *
     * @param fname      first name as a String
     * @param lname      last name as a String
     * @param dob        date of birth as a Date
     * @param expiration expiration date as a Date
     * @param location   location as Location
     */
    public Premium(String fname, String lname, Date dob, Date expiration, String location) {
        super(fname, lname, dob, expiration, location);
        this.guest = 3;
        this.fee = membershipFee();
    }

    /**
     * method to get the membership fee
     * @return
     */
    public double getMembershipFee(){
        return membershipFee();
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
     * method to create a string representation of the Premium membership
     * @return String
     */
    @Override
    public String toString() {
        // Jonnathan Wei, DOB: 9/21/1992, Membership expires 9/28/2023, Location: BRIDGEWATER, 08807, SOMERSET, (Premium) Guess-pass remaining: 1
        return super.toString() + ", (Premium) Guess-pass remaining: " + this.guest;
    }

    /**
     * method to return the initial fee for the Premium membership
     * @return double
     */
    @Override
    public double membershipFee() {
        if (oneTimeFeePaid == true){
            fee = monthlyFee*11;
        } else {
            fee = monthlyFee*11 + oneTimeFee;
        }
        return fee;
    }

    public static void main(String[] args){
        System.out.println("Testbed for membershipFee method.");
        int count = 1;
        Premium member;

        System.out.println("Test " + count + ": ");
        member = new Premium("Mary", "Lindsey", new Date("12/1/1989"), Date.createExpiration("Premium"), "Franklin");
        System.out.println(member.membershipFee());

        System.out.println("Testbed for the removeGuest method.");
        count = 1;
        System.out.println("Test " + count + ": ");
        member = new Premium("Mary", "Lindsey", new Date("12/1/1989"), Date.createExpiration("Premium"), "Franklin");
        System.out.println(member);
        member.removeGuest(member);
        System.out.println(member);

        System.out.println("Testbed for the addGuest method.");
        count = 1;
        System.out.println("Test " + count + ": ");
        member = new Premium("April", "March", new Date("3/31/1990"), new Date("6/30/2023"), "piscataway");
        System.out.println(member);
        member.addGuest(member);
        System.out.println(member);

        System.out.println("Testbed for the membershipFee method.");
        count = 1;
        System.out.println("Test " + count + ": membership fee");
        member = new Premium("April", "March", new Date("3/31/1990"), new Date("6/30/2023"), "piscataway");
        System.out.println(member.membershipFee());

        System.out.println("Testbed for the compareTo method.");
        count = 1;
        int expectedOutcome;
        int outcome;
        Premium member1;
        Premium member2;

        System.out.println("Test " + count + ": member1's last name comes before member2's last name");
        member1 = new Premium("Mary", "Lindsey", new Date("12/1/1989"), new Date("5/31/2023"), "Franklin");
        member2 = new Premium("April", "March", new Date("3/31/1990"), new Date("6/30/2023"), "piscataway");
        expectedOutcome = -1;
        outcome = member1.compareTo(member2);
        testCheck(expectedOutcome, outcome);
        count++;

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
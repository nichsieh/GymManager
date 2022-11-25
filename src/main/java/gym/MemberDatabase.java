package gym;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * This class instantiates a member database where all the information of the gym's members will be located.
 * Methods in this class include:
 * finding a member, adding a member, removing a member, printing the information of all member
 * (no sorting, by county, by expiration or by name) and growing the database.
 * @author Nicole Hsieh, Kayla Kam
 */
public class MemberDatabase {
    private static Member[] mlist;
    private static int size;

    /**
     * Getter method that returns the member database
     * @return mlist, member array containing the member database
     */
    public static Member[] getMlist(){
        return mlist;
    }

    /**
     * Constructs the member database in size 4
     */
    public MemberDatabase() {
        int capacity = 4;
        mlist = new Member[capacity];
        size = 0;
    }

    /**
     * Get the size of the member database.
     * @return int, the size/length of the member database.
     */
    public static int getSize(){
        return size;
    }

    /**
     * checks if the member data base is empty
     * @return
     */
    public static boolean isEmpty(){
        if (getSize() <= 0){
            return true;
        }
        return false;
    }

    /**
     * method to return a desired member
     * @param member
     * @return Member or null if not found the member
     */
    public static Member getMember(Member member){
        for ( int i = 0; i < size; i++ ){
            if ( mlist[i].equals(member) ){
                return mlist[i];
            }
        }
        return null;
    }

    /**
     * finds if a member is in the database
     * @param member member object to be found.
     * @return int, -1 if not found or returns the index of where the member is located.
     */
    private static int find(Member member) {
        int notFound = -1;
        for ( int i = 0; i < size; i++ ){
            if ( mlist[i].equals(member) ){
                return i;
            }
        }
        return notFound;
    }

    /**
     * increases the size of and array by 4 once the array is full.
     * @param array a list of members
     * @return biggerMlist member list
     */
    public static Member[] grow(Member[] array) {
        int capacity = 4;
        Member[] biggerMlist = new Member[mlist.length + capacity];
        for ( int i = 0; i < mlist.length; i++ ){
            biggerMlist[i] = mlist[i];
        }
        return biggerMlist;
    }

    /**
     * increases the size of the mlist array by 4 once the array is full.
     */
    public static void grow(){
        int capacity = 4;
        Member[] biggerMlist = new Member[mlist.length + capacity];
        for ( int i = 0; i < mlist.length; i++ ){
            biggerMlist[i] = mlist[i];
        }
        mlist = biggerMlist;
    }

    /**
     * add a new member to the database
     * @param member member object to be added to the database.
     * @return boolean if true the member has been added, otherwise there has been a problem/the member is already in the database
     */
    public static boolean add(Member member) {
        int notFound = -1;
        if ( find(member) == notFound ){
            size++;
            if ( size >= mlist.length ){
                grow();
            }
            mlist[size-1] = member;
            return true;
        }
        return false;
    }

    /**
     * remove any gaps in the member database
     * @param index of the gap
     */
    private void removeGap( int index ){
        for ( int i = index; i < size; i++ ){
            if (mlist[i + 1] != null){
                mlist[i] = mlist[i + 1];
            }
        }
    }

    /**
     * remove a member from the database.
     * @param member member object to be removed from the database.
     * @return boolean if true the member has been removed, otherwise there has been a problem/the member was not found in the database
     */
    public boolean remove( Member member ) {
        int notFound = -1;
        int index = find(member);
        if ( index != notFound ){
            mlist[index] = null;
            removeGap(index);
            size--;
            return true;
        }
        return false;
    }

    /**
     * LM command to load members from a text file and add it to the member database
     */
    public static String loadFile() {
        String returning = "";
        File file = new File("src/main/memberList.txt");
        if (file.exists()){
            Scanner reader;
            try {
                reader = new Scanner(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            while (reader.hasNextLine()) {
                String inputLine = reader.nextLine();
                StringTokenizer input = new StringTokenizer(inputLine, " ");
                String firstName = input.nextToken();
                String lastName = input.nextToken();
                String dobString = input.nextToken();
                String expirationString = input.nextToken();
                String locationString = input.nextToken();
                Member member = new Member( firstName, lastName, new Date(dobString), new Date(expirationString), locationString );
                add(member);
            }

            returning = "\n-list of members loaded-" + print(returning) + "\n-end of list-\n";

        } else{
//            System.out.println("file doesn't exist");
            returning += "file doesn't exist";
        }

        return returning;
    }

    /**
     * checks if the database is empty, if database is not empty prints array.
     */
    public static String print(String returning) { //print the array contents as is
        if (isEmpty()){
            returning = "Member Database is empty!";
        } else{
            for ( int i = 0; i < size; i++ ){
                returning += "\n" + mlist[i].toString();
            }
        }
        return returning;
    }

    /**
     * method that counts how many members in the member database are from middlesex county
     * @param mlist member database array
     * @return integer counter how many people are from middlesex county in the list
     */
    private static int countyCounter(Member[] mlist) {
        int counter = 0;
        for (int i = 0; i < size; i++) {
            if (mlist[i].getLocation().getCounty().equals(Location.EDISON.getCounty())) { //counts how many middlesex county members
                counter++;
            }
        }
        return counter;
    }

    /**
     * Swaps two members in the memberdata base
     * @param index1 integer containing the index of the member to be swapped
     * @param index2 integer containing the index of the other member to be swapped
     */
    private static void swap(int index1, int index2){
        Member temp = mlist[index1];
        mlist[index1] = mlist[index2];
        mlist[index2] = temp;
    }

    /**
     * sorts the subarray by zipcode for printByCounty
     * @param start - integer. Index where the sorting should start
     * @param end - integer. Index where the sorting should end
     */
    private static void sortSubarrayByCounty(int start, int end){
        for ( int compared = 1; compared < end + 1; ++compared ) {
            Member memberHold = mlist[compared];
            int key = (int) mlist[compared].getLocation().getZip();
            int curr = compared - 1;

            while ( curr >= start && mlist[curr].getLocation().getZip() > key ) {
                mlist[curr + 1] = mlist[curr];
                curr--;
            }
            mlist[curr + 1] = memberHold;
        }
    }

    /**
     * checks if the database is empty. If not empty, sorts by county then zip code.
     * If the locations are in the same county, order by the zip code.
     */
    public static String printByCounty(){
        String returning = "";
        if (isEmpty()){
            returning = "Member Database is empty!";
        } else{
            int counter = countyCounter(mlist);
            int ptr1 = 0;
            int ptr2 = counter-1;
            int indexMiddlesex = -1;
            int indexSomerset = -1;
            boolean foundSomerset = false;
            boolean foundMiddlesex = false;
            while ((ptr1 < counter) && (ptr2 < size)){
                if (mlist[ptr1].getLocation().getCounty().equals(Location.EDISON.getCounty())){
                    ptr1++;
                } else{
                    if (!foundSomerset) { // if there is a somerset in the first half of the array
                        foundSomerset = true;
                        indexSomerset = ptr1;
                    }
                }
                if (mlist[ptr2].getLocation().getCounty().equals(Location.BRIDGEWATER.getCounty())){
                    ptr2++;
                } else {
                    if (!foundMiddlesex) { // if there is a middlesex in the second half of the array
                        foundMiddlesex = true;
                        indexMiddlesex = ptr2;
                    }
                }
                if ((foundSomerset && foundMiddlesex)) {
                    swap(indexSomerset, indexMiddlesex);
                    foundSomerset = false;
                    foundMiddlesex = false;
                }
            }
            sortSubarrayByCounty(0, counter - 1);
            sortSubarrayByCounty(counter, size - 1);

            returning = "\n-list of members sorted by county and zipcode-" + print(returning) + "\n-end of list-\n";
        }

        return returning;
    }

    /**
     * prints all the members and their information,
     * sorting by the expiration dates of their membership.
     * If two expiration dates are the same, the order doesn't matter.
     */
    public static String printByExpirationDate() { //sort by the expiration date
        String returning = "";
        if (isEmpty()){
            returning = "Member Database is empty!";
        } else {
            for (int i = 1; i < size; ++i) {
                Member memberHold = mlist[i];
                Date member2ptr = mlist[i].getExpiration();
                int j = i - 1;
                int comparison = mlist[j].getExpiration().compareTo(member2ptr);
                while (j >= 0 && comparison == 1) {
                    mlist[j + 1] = mlist[j];
                    j--;
                    mlist[j + 1] = memberHold;
                    if (j >= 0 ){
                        comparison = mlist[j].getExpiration().compareTo(member2ptr);
                    }
                }
            }
            returning = "\n-list of members sorted by membership expiration date-" + print(returning) + "\n-end of list-\n";
        }

        return returning;
    }

    /**
     * prints all the members and their information, sorting by the last name then the first name.
     * If two members have the same last name, order by the first name.
     */
    public static String printByName() { //sort by last name and then first name
        String returning = "";
        if (isEmpty()){
            returning = "Member Database is empty!";
        } else{
            for (int i = 1; i < size; ++i) { // insertion sort
                Member member2ptr = mlist[i]; //pointer for member2
                int j = i - 1; //pointer for member before member2
                int comparison = mlist[j].compareTo(member2ptr);
                // if -1 then member2 is before sortedByName[j], if 0 they are the same, if 1 then sortedByName[j] is before member2
                while (j >= 0 && comparison == 1) {
                    mlist[j + 1] = mlist[j];
                    j--;
                    mlist[j + 1] = member2ptr;
                    if (j >= 0 ){
                        comparison = mlist[j].compareTo(member2ptr);
                    }
                }
            }
            returning = "\n-list of members sorted by last name, and first name-" + print(returning) + "\n-end of list-\n";
        }

        return returning;
    }

    /**
     * prints all the members and their information and the membership fees
     * @return
     */
    public static String printWithMembership(){
        String returning = "";
        if (isEmpty()){
            returning = "Member Database is empty!";
        } else{
            returning = "\n-list of members with membership fees-";
            for ( int i = 0; i < size; i++ ){
                returning += "\n" + mlist[i].toString() + ", Membership fee: $" + mlist[i].membershipFee();
            }
            returning += "\n-end of list-\n";
        }

        return returning;
    }

}
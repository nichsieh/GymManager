package gym;
/**
 * Time class includes hours and minutes of different classes i.e. Pilates, Spinning, and Cardio.
 * This class also includes constructors.
 *
 * @author Kayla Kam and Nicole Hsieh.
 */
public enum Time { //define the time of a fitness class in hh:mm
    MORNING (9, 30),
    AFTERNOON (14, 00),
    EVENING (14, 00);


    public final int hour;
    public final int minutes;

    /**
     * Time constructor receives the integer values of hours and minutes and creates the Time.
     * @param hour integer value of the hour
     * @param minutes integer value of the minutes
     */
    Time(int hour, int minutes){
        this.hour = hour;
        this.minutes = minutes;
    }

    /**
     * get time method
     * @param time
     * @return
     */
    public static Time getTime(String time) {
        Time[] alltimes = Time.values();
        for(Time timeptr : alltimes) {
            if(time.equalsIgnoreCase(timeptr.name())) {
                return timeptr;
            }
        }
        return null;
    }
}
package gym;

/**
 * Location class includes constructors and methods to get the zip code, county, and validate the location.
 * @author Kayla Kam and Nicole Hsieh.
 */
public enum Location {
    BRIDGEWATER (08807.0, "Somerset"),
    EDISON (08837.0, "Middlesex"),
    FRANKLIN (08873.0, "Somerset"),
    PISCATAWAY (08854.0, "Middlesex"),
    SOMERVILLE (08876.0, "Somerset");

    private final double zip;
    private final String county;

    /**
     * Time constructor receives the integer values of hours and minutes and creates the Time.
     * @param zip double value that has the zip code of the location.
     * @param county String value of the name of the county.
     */
    Location(double zip, String county){
        this.zip = zip;
        this.county = county;
    }

    /**
     * A method that gives the zip code (int version) of a location.
     * @return this.zip an int that contains the zipcode of a specific location.
     */
    public int getZip() {
        return (int)this.zip;
    }

    /**
     * A method that gives the county name of a location.
     * @return this.county String variable representing the name of the location.
     */
    public String getCounty() {
        return this.county;
    }

    /**
     * A method that returns the location data of a certain town.
     * @param location which is a String variable representing a given location name.
     * @return a variable of the Location class that contains the town's information (i.e. zip and county).
     *         If none of the strings equals the location name then it returns null.
     */
    public static Location validLocation(String location){
        if (location.toUpperCase().equals(Location.BRIDGEWATER.name())){
            return Location.BRIDGEWATER;
        } else if (location.toUpperCase().equals(Location.EDISON.name())){
            return Location.EDISON;
        } else if (location.toUpperCase().equals(Location.FRANKLIN.name())){
            return Location.FRANKLIN;
        } else if (location.toUpperCase().equals(Location.PISCATAWAY.name())){
            return Location.PISCATAWAY;
        } else if (location.toUpperCase().equals(Location.SOMERVILLE.name())){
            return Location.SOMERVILLE;
        }
        return null;
    }

    public static Location getLocation(String location){
        Location [] locations = Location.values();
        for(Location locationString : locations){
            if(location.equalsIgnoreCase(locationString.name())){
                return locationString;
            }
        }
        return null;
    }

    public int compareLocation(Location location) {
        String firstCounty = (this.getCounty()).toLowerCase();
        String secondCounty = (location.getCounty()).toLowerCase();
        int firstZipCode = this.getZip();
        int secondZipCode = location.getZip();
        if (firstCounty.compareTo(secondCounty) == 0) {
            if (firstZipCode > secondZipCode) {
                return 1;
            } else if (firstZipCode < secondZipCode) {
                return -1;
            } else {
                return 0;
            }
        }
        return firstCounty.compareTo(secondCounty);
    }

}

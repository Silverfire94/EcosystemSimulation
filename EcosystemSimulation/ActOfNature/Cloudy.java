package ActOfNature;


/**
 * This simulates cloudy weather.
 *
 * @author Mark Emmanuel and Vaibhavkumar Patel
 * @version 2.0
 */
public class Cloudy extends Weather
{
    // The ID of the class
    private static int ID = 1;

    /**
     * Constructor for objects of class Cloudy.
     */
    public Cloudy()
    {
        viewDistance = 1;
        growthRate = 1;
    }

    /**
     * Checks if the given ID is same as the class ID.
     * @param givenID The ID at the location in the map.
     * @Return Returns true if the class ID is the same as the givenID.
     */
     public boolean matchId(int givenId){
        return givenId == ID;
    }
    
    /**
     * Creates a new Cloudy object.
     * @Return Returns a Cloudy object.
     */
    public Weather createWeather(){
        return new Cloudy();
    }
}

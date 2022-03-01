package ActOfNature;


/**
 * This simulates sunny weather.
 *
 * @author Mark Emmanuel and Vaibhavkumar Patel
 * @version 2.0
 */
public class Sunny extends Weather
{
    // The ID of the class
    private static int ID = 0;
    
    /**
     * Constructor for creating Sunny objects.
     */
    public Sunny(){
        growthRate = 3;
        viewDistance = 2; 
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
     * Creates a Sunny object.
     * @Return Returns a Sunny Object.
     */
    public Weather createWeather(){
        return new Sunny();
    }
}

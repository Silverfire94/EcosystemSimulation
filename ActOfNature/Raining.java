package ActOfNature;


/**
 * This class simulates raining.
 *
 * @author Mark Emmanuel and Vaibhavkumar Patel
 * @version 2.0
 */
public class Raining extends Weather
{
    // The ID of this class
    private static int ID = 2;
    
    /**
     * Constructor for objects of class Raining.
     */
    public Raining()
    {
        growthRate = 2; 
        viewDistance = 0;
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
     * Creates a new Raining object.
     * @Return Returns a Raining object.
     */
    public Weather createWeather(){
        return new Raining();
    }

}


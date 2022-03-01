package ActOfNature;


/**
 * This simulates the Weather which affects the growth rate of plants
 * and how well the animals can see.
 *
 * @author Mark Emmanuel & Vaibhavkumar Patel.
 * @version 2.0
 */
public abstract class Weather
{
    //Used for plants
    protected static final int DEFAULT_GROWTH_RATE = 1;
    protected int growthRate = DEFAULT_GROWTH_RATE;
    
    //Used for animals
    protected static final int DEFAULT_VIEW_DISTANCE= 1;
    protected int viewDistance = DEFAULT_VIEW_DISTANCE;
    
    /**
     * Creates a Weather object.
     */
    public Weather(){
        
    }
    
    /**
     * @Return How fast will the plants grow.
     */
    public int getGrowthRate(){
        return growthRate;
    }
    
    /**
     * @Return How visible is it in the weather.
     */
    public int getViewDistance(){
        return viewDistance;
    }

    /**
     * This creates a new Weather object of the subclass type.
     * @Return The new weather object.
     */
    abstract public Weather createWeather();
    
    /**
     * This checks if the ID given matches the ID of the class
     * @param givenID This is the ID at the location in the map
     * @Return Returns true if the class ID is the same as the givenID
     */
    abstract public boolean matchId(int givenID);

}

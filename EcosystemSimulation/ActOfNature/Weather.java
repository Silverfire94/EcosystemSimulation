package ActOfNature;


/**
 * Write a description of class Weather here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Weather
{
    //Used for plants
    protected static final int DEFAULT_GROWTH_RATE = 1;
    protected int growthRate = DEFAULT_GROWTH_RATE;
    //Used for animals
    protected static final int DEFAULT_VIEW_DISTANCE= 1;
    protected int viewDistance = DEFAULT_VIEW_DISTANCE;
    public Weather(){
        
    }
    
    public int getGrowthRate(){
        return growthRate;
    }
    
    public int getViewDistance(){
        return viewDistance;
    }

    abstract public Weather createWeather();
    
    abstract public boolean matchId(int givenID);

}

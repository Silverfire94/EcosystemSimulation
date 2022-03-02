package Lands;
import Plants.Plant;
import Utility.Field;
import Utility.Location;
import ActOfNature.Weather;

/**
 * Type of land - ground, where ground animals spawn in on.
 *
 * @author Mark Emmanuel and Vaibhavkumar Patel
 * @version 2.0
 */
public class Ground extends Land
{
    private static int ID = 0;
    private Plant plant;
    private Weather weather;

    /**
     * Constructor for objects of class Ground
     */
    public Ground(Field field, Location location, Plant plant, Weather weather)
    {
        super(field, location);
        this.plant = plant;
        this.weather = weather;
    }
    /**
     * Dummy constructor.
     */
    public Ground(){
        
    }
    /**
     * @param field takes in a field where the land object will be created. 
     * @param location takes in a location where the land object will be fixed.
     */
    public Land createLand(Field field, Location location){
        return new Ground(field, location, null, null);
    }
    
    /**
     * @retrun A boolean which is dependent on if the ID match.
     */
    public boolean matchId(int givenId){
        return givenId == ID;
    }
    
    /**
     * @retrun the plant object at this land
     */
    public Plant getPlant()
    {
        return plant;
    }

    /**
     * @param plant takes in a plant object to add to this land. 
     */
    public void addPlant(Plant plant)
    {
        this.plant = plant;
    }
    
    /**
     * @return the weather types at this land. 
     */
    public Weather getWeather()
    { 
        return weather;
    }
    /**
     * @param weather takes in a weather object to add to this land.
     */
    public void addWeather(Weather weather){
        this.weather = weather;
    }
}

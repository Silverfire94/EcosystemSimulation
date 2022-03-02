package Lands;
import Plants.Plant;
import Utility.Field;
import Utility.Location;
import ActOfNature.Weather;

/**
* This is a type land called Water it is where water animals 
* and land animals would interact.
*
* @author Mark Emmanuel and Vaibhavkumar patel 
* @version 2022.03.01 (3).
*/
public class Water extends Land
{
    private static int ID = 2;
    private Plant plant;
    private Weather weather;

    /**
     * Main Constructor of this class. 
     * 
     * @param field the field at which Water object will be placed on.
     * @param location the location of Water object.
     * @param plant the type of plant on top Water.
     * @param weather the weather on top of this land type.
     */
    public Water(Field field, Location location, Plant plant , Weather weather){
        super(field, location);
        this.plant = plant;
        this.weather = weather;
    }

    /**
     * Dummy Constructor.
     */
    public Water()
    {}
    
    /**
     * @param field takes in a field where the land object will be created. 
     * @param location takes in a location where the land object will be fixed.
     */
    public Land createLand(Field field, Location location){
        return new Water(field, location, null, null);
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

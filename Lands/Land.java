package Lands;

import Plants.Plant;
import Utility.Field;
import Utility.Location;
import ActOfNature.Weather;
/**
 * Objects of this class store plants and weather that are
 * at this location.
 *
 * @author Mark Emmanuel and Vaibhavkumar Patel
 * @version 2.0
 */
public abstract class Land
{
    private Location location; 
    private Field field;
    
    public Land(Field field, Location location){
        this.field = field;
        setLocation(location);
    }
    
    public Land(){
        
    }
    
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Return the land's field.
     * @return the land's field.
     */
    protected Field getField(){
        return field;
    }
    
    /**
     * Return the land's location.
     * @return the land's location.
     */
    public Location getLocation()
    {
        return location;
    }
    
    /**
     * This return the ID of the land.
     */
    abstract public boolean matchId(int givenId);
    
    abstract public Land createLand(Field field, Location location);
    
    abstract public Plant getPlant();
    
    abstract public void addPlant(Plant plant);
    
    abstract public Weather getWeather();
    
    abstract public void addWeather(Weather weather);
}

package Lands;

import Plants.Plant;
import Utility.Field;
import Utility.Location;
import ActOfNature.Weather;
/**
 * Objects of this class store plants and weather that are
 * at the location of the land object.
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
    /**
     * Dummy constructor. 
     */
    public Land()
    {}
    /**
     * SetLocation method changes the location to the new location. 
     * @param newLocation The new location of the land object.
     */
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
     * @return boolean value if ID matches.
     */
    abstract public boolean matchId(int givenId);
    
    /**
     * The method creates new land objects. 
     * @param field the where the land object will be created.
     * @param location the location where the land object be created.
     * @return A land object.
     */
    abstract public Land createLand(Field field, Location location);
    /**
     * @return A plant object at the location.
     */
    abstract public Plant getPlant();
    
    /**
     * @param plant takes in a plant to add to a land.
     */
    abstract public void addPlant(Plant plant);
    
    /**
     * @return Weather object at the location.
     */
    abstract public Weather getWeather();
    
    /** 
     * @param weather takes in a plant object to add to land.
     */
    abstract public void addWeather(Weather weather);
}

package Lands;
import Plants.Plant;
import Utility.Field;
import Utility.Location;
import ActOfNature.Weather;
/**
 * Write a description of class Water here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Water extends Land
{
    private static int ID = 2;
    private Plant plant;
    private Weather weather;
    /**
     * Constructor for objects of class Water
     */
    public Water(Field field, Location location, Plant plant, Weather weather)
    {
     super(field, location);
     this.plant = plant;
     this.weather = weather;
    }
    
    public Water()
    {}
    
    public Land createLand(Field field, Location location){
        return new Water(field, location, null, null);
    }
    
    public boolean matchId(int givenId){
        return givenId == ID;
    }
    
    public Plant getPlant(){
        return plant;
    }
    
    public void addPlant(Plant plant){
        this.plant = plant;
    }
    
     public Weather getWeather(){ 
        return weather;
    }
    
    public void addWeather(Weather weather){
        this.weather = weather;
    }
}

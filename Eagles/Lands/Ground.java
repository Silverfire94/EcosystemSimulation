package Lands;
import Plants.Plant;
import Utililty.Field;
import Utililty.Location;
import ActOfNature.Weather;

/**
 * Write a description of class Ground here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Ground extends Land
{
    // instance variables - replace the example below with your own
    private static int Id = 0;
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
    
    public Ground(){
        
    }
    
    public Land createLand(Field field, Location location){
        return new Ground(field, location, null, null);
    }
    
    public boolean matchId(int givenId){
        return givenId == Id;
    }
    
    public Plant getPlant()
    {
        return plant;
    }
    
    public void addPlant(Plant plant)
    {
        this.plant = plant;
    }
    
    public Weather getWeather()
    { 
        return weather;
    }
    
    public void addWeather(Weather weather){
        this.weather = weather;
    }
}

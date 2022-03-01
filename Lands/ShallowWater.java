package Lands;

import java.util.List;
import Lands.Land;
import Plants.Plant;
import Utility.Field;
import Utility.Location;
import ActOfNature.Weather;
import java.util.Iterator;

public class ShallowWater extends Land
{
    private static int ID = 1;
    private Plant plant;
    private Weather weather;
    
    public ShallowWater(Field field, Location location, Plant plant , Weather weather){
        super(field, location);
        this.plant = plant;
        this.weather = weather;
    }
    
    public ShallowWater()
    {}
    
    public Land createLand(Field field, Location location){
        return new ShallowWater(field, location, null, null);
    }
    
    public boolean matchId(int givenId){
        return givenId == ID;
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


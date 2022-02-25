import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

/**
 * Write a description of class AnimalLand here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class AnimalLand
{
  private HashMap<Class , List<Class>> landmap;
 
    public AnimalLand(){
        landmap = new HashMap<>();
        List pythonBiome = new ArrayList<Land>();
        pythonBiome.add(Ground.class);
        pythonBiome.add(ShallowWater.class);
        
        List sharkBiome = new ArrayList<Land>();
        sharkBiome.add(Water.class);
        sharkBiome.add(ShallowWater.class);
        
        List pigBiome = new ArrayList<Land>();
        pigBiome.add(Ground.class); 
        
        List eagleBiome = new ArrayList<Land>();
        eagleBiome.add(Ground.class);
        eagleBiome.add(Water.class);
        eagleBiome.add(ShallowWater.class);
        
        List humanBiome = new ArrayList<Land>();
        humanBiome.add(Ground.class);
        humanBiome.add(ShallowWater.class); 
        
        List fishBiome = new ArrayList<Land>();
        sharkBiome.add(Water.class);
        sharkBiome.add(ShallowWater.class);
        
        landmap.put(Python.class, pythonBiome);
        landmap.put(Shark.class, sharkBiome);
        landmap.put(Pig.class, pigBiome);
        landmap.put(Eagles.class, eagleBiome);
        landmap.put(Human.class, humanBiome);
        landmap.put(Fish.class, fishBiome);
    }
    
    public HashMap<Class, List<Class>> getLandMap(){
        return landmap;
    }
}


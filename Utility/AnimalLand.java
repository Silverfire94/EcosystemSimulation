package Utility;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import Animals.*;
import Lands.*;
import Plants.*;

/**
 * This holds what biome can each organism exist in.
 *
 * @author Mark Emmanuel and Vaibhavkumar Patel
 * @version 2.0
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
        fishBiome.add(Water.class);
        fishBiome.add(ShallowWater.class);
        
        List grassBiome = new ArrayList<Land>();
        grassBiome.add(Ground.class);
        
        List seaweedBiome = new ArrayList<Land>();
        seaweedBiome.add(Water.class);
        seaweedBiome.add(ShallowWater.class);
        
        landmap.put(Python.class, pythonBiome);
        landmap.put(Shark.class, sharkBiome);
        landmap.put(Pig.class, pigBiome);
        landmap.put(Eagles.class, eagleBiome);
        landmap.put(Human.class, humanBiome);
        landmap.put(Fish.class, fishBiome);
        landmap.put(Grass.class, grassBiome);
        landmap.put(Seaweed.class, seaweedBiome);
    }
    
    /**
     * @Return The hashMap linking an organism and the lands it can live on.
     */
    public HashMap<Class, List<Class>> getLandMap(){
        return landmap;
    }
}


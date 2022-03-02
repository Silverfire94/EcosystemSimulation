package Utility;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import Animals.*;
import Plants.*;

/**
 * This class maps all the animals and what they eat. 
 * 
 * @author Vaihbavkumar patel(k21076223) and mark emmanuel(k21009638).
 * @version (3) 2022.03.01.
 */
public class Foodweb
{
    // This hashMap what animals each animal can eat.
    private HashMap<Class, List<Food>> foodmap;
    
    /**
     * Constructor for creating Foodweb objects.
     */
    public Foodweb(){
        foodmap = new HashMap<>();
        
        List pythonEats = new ArrayList<Food>();
        pythonEats.add(new Food(Pig.class, false, 250));
        pythonEats.add(new Food(Fish.class, true, 150));
        
        List humanEats = new ArrayList<Food>();
        humanEats.add(new Food(Pig.class, true, 250));
        humanEats.add(new Food(Python.class, true, 150));
        humanEats.add(new Food(Fish.class, true, 150)); 
        
        List eagleEats = new ArrayList<Food>();
        eagleEats.add(new Food(Pig.class, true, 250));
        eagleEats.add(new Food(Python.class, false, 200));
        eagleEats.add(new Food(Human.class, false, 150));
        eagleEats.add(new Food(Fish.class, true, 150));
        
        List sharkEats = new ArrayList<Food>(); 
        sharkEats.add(new Food(Human.class, false, 150)); 
        sharkEats.add(new Food(Fish.class, true, 100));
        
        List pigEats = new ArrayList<Food>();
        pigEats.add(new Food(Grass.class, true, 5));
        
        List fishEats = new ArrayList<Food>();
        fishEats.add(new Food(Seaweed.class, true, 9));
        
        // This adds each list to the HashMap
        foodmap.put(Python.class, pythonEats);
        foodmap.put(Human.class, humanEats);
        foodmap.put(Eagles.class, eagleEats);
        foodmap.put(Shark.class, sharkEats);
        foodmap.put(Pig.class, pigEats);
        foodmap.put(Fish.class, fishEats);
    }
    
    /**
     * This gives us a list of organisms that the animal can eat.
     * @param animalType The class of the animal, whose prey we want.
     * @Return List of classes that are prey to the animal.
     */
    public List<Food> getFood(Class animalType)
    {
        return foodmap.get(animalType);
    }   
}

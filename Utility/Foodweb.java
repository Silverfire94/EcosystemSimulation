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
 * @version 1 2/12/2022.
 */
public class Foodweb
{
    private HashMap<Class, List<Food>> foodmap;
    
    public Foodweb(){
        foodmap = new HashMap<>();
        List pythonEats = new ArrayList<Food>();
        pythonEats.add(new Food(Pig.class, false, 50));
        pythonEats.add(new Food(Fish.class, true, 30));
        
        List humanEats = new ArrayList<Food>();
        humanEats.add(new Food(Pig.class, true, 40));
        humanEats.add(new Food(Python.class, true, 40));
        humanEats.add(new Food(Eagles.class, true, 30));
        humanEats.add(new Food(Fish.class, true, 20)); 
        
        List eagleEats = new ArrayList<Food>();
        eagleEats.add(new Food(Pig.class, true, 40));
        eagleEats.add(new Food(Python.class, false, 40));
        eagleEats.add(new Food(Human.class, false, 50));
        eagleEats.add(new Food(Fish.class, true, 30));
        
        List sharkEats = new ArrayList<Food>(); 
        sharkEats.add(new Food(Human.class, false, 40)); 
        sharkEats.add(new Food(Fish.class, true, 30));
        
        List pigEats = new ArrayList<Food>();
        pigEats.add(new Food(Grass.class, true, 10));
        
        List fishEats = new ArrayList<Food>();
        fishEats.add(new Food(Seaweed.class, true, 10));
        
        foodmap.put(Python.class, pythonEats);
        foodmap.put(Human.class, humanEats);
        foodmap.put(Eagles.class, eagleEats);
        foodmap.put(Shark.class, sharkEats);
        foodmap.put(Pig.class, pigEats);
        foodmap.put(Fish.class, fishEats);
    }
    
    public List<Food> getFood(Class animalType)
    {
        return foodmap.get(animalType);
    }   
}

package Utililty;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import Animals.Eagles;
import Animals.Fish;
import Animals.Human;
import Animals.Pig;
import Animals.Python;
import Animals.Shark;
import Plants.Grass;

/**
 * This class maps all the animals and what they eat. 
 * 
 * @author Vaihbavkumar patel(k21076223) and mark emmanuel(k21009638).
 * @version 1 2/12/2022.
 */
public class Foodweb
{
    private HashMap<Class, List<Food>> foodmap;
    // Pig 20
    // Python 40
    // Human 50
    // Eagle 30
    
    public Foodweb(){
        foodmap = new HashMap<>();
        List pythonEats = new ArrayList<Food>();
        pythonEats.add(new Food(Pig.class, true, 20));
        
        List humanEats = new ArrayList<Food>();
        humanEats.add(new Food(Pig.class, true, 20));
        humanEats.add(new Food(Python.class, true, 40));
        humanEats.add(new Food(Eagles.class, true, 30));
        humanEats.add(new Food(Fish.class, true, 10)); 
        
        List eagleEats = new ArrayList<Food>();
        eagleEats.add(new Food(Pig.class, true, 20));
        eagleEats.add(new Food(Python.class, false, 40));
        eagleEats.add(new Food(Human.class, false, 50));
        
        List sharkEats = new ArrayList<Food>(); 
        sharkEats.add(new Food(Human.class, false, 20)); 
        sharkEats.add(new Food(Fish.class, true, 10));
        
        List pigEats = new ArrayList<Food>();
        pigEats.add(new Food(Grass.class, true, 10));
        
        foodmap.put(Python.class, pythonEats);
        foodmap.put(Human.class, humanEats);
        foodmap.put(Eagles.class, eagleEats);
        foodmap.put(Shark.class, sharkEats);
        foodmap.put(Pig.class, pigEats);
    }
    
    public List<Food> getFood(Class animalType)
    {
        return foodmap.get(animalType);
    }   
}

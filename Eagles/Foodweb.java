import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

/**
 * This class maps all the animals and what they eat. 
 * 
 * @author Vaihbavkumar patel(k21076223) and mark emmanuel(k21009638).
 * @version 1 2/12/2022.
 */
public class Foodweb
{
    private HashMap<Class, List<Food>> foodmap;
    private static Class type = Pig.class;
    
    // Pig 20
    // Python 40
    // Human 50
    // Eagle 30
    
    public Foodweb(){
        foodmap = new HashMap<>();
        List PythonEats = new ArrayList<Food>();
        PythonEats.add(new Food(Pig.class, true, 20));
        
        List humanEats = new ArrayList<Food>();
        humanEats.add(new Food(Pig.class, true, 20));
        humanEats.add(new Food(Python.class, true, 40));
        humanEats.add(new Food(Eagles.class, true, 30));
        
        List eagleEats = new ArrayList<Food>();
        eagleEats.add(new Food(Pig.class, true, 20));
        eagleEats.add(new Food(Python.class, false, 40));
        eagleEats.add(new Food(Human.class, false, 50));
        
        foodmap.put(Python.class, PythonEats);
        foodmap.put(Human.class, humanEats);
        foodmap.put(Eagles.class, eagleEats);
    }
    
    public List<Food> getFood(Class animalType)
    {
        return foodmap.get(animalType);
    }
    
    public static void testMethod()
    {
        Pig Pig = new Pig();
        if(Pig.getClass() == type)
        {
            System.out.println("This is a Pig");
        }
        else
        {
            System.out.println("This is not a Pig");
        }
        
        if(1000000 < Double.POSITIVE_INFINITY)
        {
            System.out.println("Smaller");
        }
    }
}

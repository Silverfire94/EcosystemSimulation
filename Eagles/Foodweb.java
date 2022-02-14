import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

/**
 * This class maps all the animals and what they eat. 
 * 
 * @author Vaihbavkumar patel(k21076223) and mark emmauel(k21009638).
 * @version 1 2/12/2022.
 */
public class Foodweb
{
    private HashMap<Object, List<Object>> foodmap; 
    
    public Foodweb(){
        foodmap = new HashMap<>();
        List foxEats = new ArrayList<Object>();
        foxEats.add(Rabbit.class);
        
        List humanEats = new ArrayList<Object>();
        humanEats.add(Rabbit.class);
        humanEats.add(Fox.class);
        
        foodmap.put(Fox.class, foxEats);
        foodmap.put(Fox.class, humanEats);
    }
    
    
}

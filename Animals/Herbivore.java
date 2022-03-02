package Animals;
import java.util.Iterator;
import java.util.List;

import Lands.Land;
import Plants.Plant;
import Utility.*;
/**
 * 
 * This class models specific methods and attributes only 
 * related to the carnivore's in the simulation.
 *
 * @author Vaibhavkumar Patel (k21076223) and mark Emmanuel(k21009628)
 * @version 2022.03.01
 */
public abstract class Herbivore extends Animal
{
     /**
     * Constructor for objects of class herbivore
     */
    public Herbivore(boolean male, Field animalfield, Location location, Field islandField)
    {
        super(male, animalfield, location, islandField);
    }
    /**
     * Dummy constructor
     */
    public Herbivore()
    {}

    /**
     * This method is used to find the location of the for a herbivore.
     * @param foodweb is foodweb object that has the info about the food the herbivore can eat.
     * @return the location of the food.
     */
    protected Location findFood(Foodweb foodweb)
    {
        Field islandField = getIslandField();
        Iterator<Location> it = moveAbleLands().iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Plant plant = ((Land) islandField.getObjectAt(where)).getPlant();
            List<Food> foodTypes = foodweb.getFood(this.getClass());
            if(foodTypes != null){
                for (Food food : foodTypes)
                {
                    Class plantType = food.getFoodType();
                    if (plant != null && plant.getClass() == plantType)
                    {
                        // Checks if this animal is strong against the food
                        if(plant.isBig() && foodLevel < whenHungery)
                        {
                            plant.setSmall();
                            foodLevel += food.getFoodValue();
                            return where;
                        }
                    }
                }
            }
        }
        return null;
    }
}

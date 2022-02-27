import java.util.Iterator;
import java.util.List;

/**
 * Abstract class Herbivore - write a description of the class here
 *
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class Herbivore extends Animal
{
    public Herbivore(boolean male, Field field, Location location, Field islandField)
    {
        super(male, field, location, islandField);
    }

    public Herbivore()
    {

    }

    protected Location findFood(Foodweb foodweb)
    {
        Field field = getField();
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

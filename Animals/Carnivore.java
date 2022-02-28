package Animals;


import java.util.Iterator;
import java.util.List;

import Utililty.Field;
import Utililty.Food;
import Utililty.Foodweb;
import Utililty.Location;

/**
 * Write a description of class Herbivore here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Carnivore extends Animal
{

    /**
     * Constructor for objects of class Herbivore
     */
    public Carnivore(boolean male, Field field, Location location, Field islandField)
    {
        super(male, field, location, islandField);
    }
    
    /**
     * Dummy Constructor
     */
    public Carnivore()
    {
        //Nothing here
    }
    
    protected Location findFood(Foodweb foodweb)
    {
        Field field = getField();
        Iterator<Location> it = moveAbleLands().iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            List<Food> foodtypes = foodweb.getFood(this.getClass());
            for (Food food : foodtypes)
            {
                Class animaltype = food.getFoodType();
                if (animal != null && animal.getClass() == animaltype)
                {
                    Animal animalToEat = (Animal) animal;
                    // Checks if this animal is strong against the food
                    if(animalToEat.isAlive() && foodLevel < whenHungery &&
                    (food.getStrongAgainst() || animalToEat.getIsWeak()))
                    {
                        animalToEat.setDead();
                        foodLevel += food.getFoodValue();
                        return where;
                    }
                }
            }
        }
        return null;
    }
}

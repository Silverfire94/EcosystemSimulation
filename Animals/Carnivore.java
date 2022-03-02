package Animals;

import java.util.Iterator;
import java.util.List;

import Utility.*;

/**
 * This class models specific methods and attributes only 
 * related to the carnivore's in the simulation.
 *
 * @author David J. Barnes, Michael Kölling, Vaibhavkumar patel(k21076223)
 * and mark Emmanuel(k21009638).
 * @version 2022.03.01 (3)
 */
public abstract class Carnivore extends Animal
{
    /**
     * Constructor for objects of class Canivore
     */
    public Carnivore(boolean male, Field animalField, Location location, Field islandField)
    {
        super(male, animalField, location, islandField);
    }
    
    /**
     * Dummy Constructor
     */
    public Carnivore()
    {
        //Nothing here
    }
    /**
     * THis method return the location at which there is food.
     * @param foodweb this is the web of what animal eats what.
     */
    protected Location findFood(Foodweb foodweb)
    {
        Field animalField = getField();
        Iterator<Location> it = moveAbleLands().iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = animalField.getObjectAt(where);
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

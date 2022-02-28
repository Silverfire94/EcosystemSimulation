package Animals;
import java.util.List;
import java.util.Iterator;
import java.util.Random;

import Utililty.Field;
import Utililty.Foodweb;
import Utililty.Location;

/**
 * A simple model of a fox.
 * Sharks age, move, eat rabbits, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Shark extends Carnivore
{
    // Characteristics shared by all sharks (class variables).

    // The age at which a fox can start to breed.
    private static final int BREEDING_AGE = 15;
    // The age to which a fox can live.
    private static final int MAX_AGE = 1500;
    // The likelihood of a fox breeding.
    private static final double BREEDING_PROBABILITY = 0.16;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 3;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a fox can go before it has to eat again.
    private static final int RABBIT_FOOD_VALUE = 20;

    // A shared random number generator to control breeding.
    //private static final Random rand = Randomizer.getRandom();

    // The spawn probability of fox
    private static double Spawn_Probability = 0.30;

    // Individual characteristics (instance fields).

    // The fox's food level, which is increased by eating rabbits.
    private int foodLevel;

    /**
     * Create a fox. A fox can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Shark(boolean randomAge,boolean male, Field field, Location location, Field islandField)
    {
        super(male, field, location, islandField);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
            foodLevel = rand.nextInt(40);
        }
        else {
            age = 0;
            foodLevel = 20;
        }
    }

    /**
     * Another constructor for sharks.
     */
    public Shark()
    {}

    /**
     * This allows us to create a new animal
     * @return Returns a reference of the animal we created
     */
    public Animal createAnimal(boolean randomAge, boolean male, Field field, Location location, Field islandField)
    {
        return new Shark(randomAge,male,field, location, islandField);
    }

    /**
     * This is what the fox does most of the time: it hunts for
     * rabbits. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newSharks A list to return newly born sharks.
     */
    public void act(List<Animal> newSharks, Foodweb foodweb, boolean isDay)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            if(isDay){
                giveBirth(newSharks);            
                // Move towards a source of food if found.

                Location newLocation = findFood(foodweb);
                if(newLocation == null) { 
                    // No food found - try to move to a free location.
                    newLocation = getMoveAbleLand();
                }
                // See if it was possible to move.
                if(newLocation != null) {
                    setLocation(newLocation);
                }
                else {
                    // Overcrowding.
                    setDead();
                }
            }
        }
    }

    /**
     * Increase the age. This could result in the fox's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }

    /**
     * Make this fox more hungry. This could result in the fox's death.
     */
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * Check whether or not this fox is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newSharks A list to return newly born sharks.
     */
    private void giveBirth(List<Animal> newSharks)
    {
        // New sharks are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        Field islandField = getIslandField();

        List<Location> partners = field.adjacentLocations(getLocation());
        int births = breed();
        for(Location partner: partners){
            if(field.getObjectAt(partner) instanceof Shark){            
                Shark shark = (Shark) field.getObjectAt(partner); 
                if(this.getGender() != shark.getGender()){
                    for(int b = 0; b < births && moveAbleLands().size() > 0; b++) {
                        Location loc = moveAbleLands().remove(0);
                        Shark young = new Shark(false, setGender(), field, loc, islandField);
                        newSharks.add(young);
                    }
                }
            }
        }
    }

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    private int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    /**
     * A fox can breed if it has reached the breeding age.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }

    /**
     * @return The probability of the rabbit spawning
     */
    public double getSpawn()
    {
        return Spawn_Probability;
    }

}

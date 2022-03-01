package Animals;
import java.util.List;
import Lands.Land;
import Utility.*;

/**
 * A simple model of a Python.
 * A Python eats weak pigs, and Fishes.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Python extends Carnivore
{
    // Characteristics shared by all Pythons (class variables).

    // The age at which a Python can start to breed.
    private static final int BREEDING_AGE = 6;
    // The age to which a Python can live.
    private static final int MAX_AGE = 3650;
    // The likelihood of a Python breeding.
    private static final double BREEDING_PROBABILITY = 0.12;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 3;
    // The eye strength is the measure of how far they are moving. 
    private static final int EYE_STRENGTH = 2;
    // The spawn probability of Python
    private static double Spawn_Probability = 0.40;


    /**
     * Create a Python. A Python can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the Python will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Python(boolean randomAge,boolean male, Field field, Location location,Field islandField)
    {
        super(male, field, location, islandField);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
        else {
            age = 0;
        }
        foodLevel = rand.nextInt(40);

        whenHungery = 60;
    }

    /**
     * Another constructor for Pythons.
     */

    public Python(){
    }

    /**
     * This allows us to create a new animal
     * @return Returns a reference of the animal we created
     */
    public Animal createAnimal(boolean randomAge, boolean male, Field field, Location location, Field islandField)
    {
        return new Python(randomAge,male,field, location, islandField);
    }

    /**
     * This is what the Python does most of the time: it hunts for
     * rabbits. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newPythons A list to return newly born Pythons.
     */
    public void act(List<Animal> newPythons, Foodweb foodweb, boolean isDay)
    {
        incrementAge();
        incrementHunger();
        Field islandField = getIslandField();
        if(isAlive()) {
            if(isDay){
                giveBirth(newPythons);
                checkAdjacentAnimals();
                // Move towards a source of food if found.
                Location newLocation = findFood(foodweb);
                Land land = (Land) islandField.getObjectAt(getLocation());
                if(land.getWeather().getViewDistance() <= EYE_STRENGTH){  
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
    }

    /**
     * Increase the age. This could result in the Python's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
        if(age < 10) {
            isWeak = true;
        }
        else {
            isWeak = false;
        }
    }

    /**
     * Make this Python more hungry. This could result in the Python's death.
     */
    private void incrementHunger()
    {
        foodLevel--;
        diseaseHunger();
        if(foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * Check whether or not this Python is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newPythons A list to return newly born Pythons.
     */
    private void giveBirth(List<Animal> newPythons)
    {
        // New Pythons are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        Field islandField = getIslandField();

        List<Location> partners = field.adjacentLocations(getLocation());
        int births = breed();
        for(Location partner: partners){
            if(field.getObjectAt(partner) instanceof Python){            
                Python python = (Python) field.getObjectAt(partner); 
                if(this.getGender() != python.getGender()){
                    for(int b = 0; b < births && moveAbleLands().size() > 0; b++) {
                        Location loc = moveAbleLands().remove(0);
                        Python young = new Python(false, setGender(), field, loc, islandField);
                        newPythons.add(young);
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
     * A Python can breed if it has reached the breeding age.
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

package Animals;
import java.util.List;
import java.util.Random;

import Lands.Land;
import Utility.Field;
import Utility.Foodweb;
import Utility.Location;

/**
 * A simple model of a Pig.
 * Pigs eats grass, they breed, move and die. 
 * they are also affected by disease.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Pig extends Herbivore
{
    // Characteristics shared by all Pigs (class variables).
    // The age at which a Pig can start to breed.
    private static final int BREEDING_AGE = 100;
    // The age to which a Pig can live.
    private static final int MAX_AGE = 500;
    // The likelihood of a Pig breeding.
    private static final double BREEDING_PROBABILITY = 0.05;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    // The eye strength is the measure of how far they are moving. 
    private static final int EYE_STRENGTH = 0;
    // The spawn probability of Pig
    private static double Spawn_Probability = 0.15;
    // The probability of movement at night. 
    private static final double NIGHT_MOVEMENT = 0.05;
    
    /**
     * Create a new Pig. A Pig may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the Pig will have a random age.
     * @param male The gender of the pig. 
     * @param animalField the animalField currently occupied. 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Pig(boolean randomAge,boolean male, Field field, Location location, Field islandField)
    {
        super(male, field, location, islandField);
        age = 0;
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
        foodLevel = rand.nextInt(40) + 30;
        
        whenHungery = 70;
    }

    /**
     * Another constructor for Pigs. 
     */
    public Pig()
    {}

    /**
     * This allows us to create a new animal
     * @return Returns a reference of the animal we created
     */
    public Animal createAnimal(boolean randomAge,boolean male, Field field, Location location, Field islandField)
    {
        return new Pig(randomAge, male, field, location, islandField);
    }

    /**
     * This is what the Pig does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newPigs A list to return newly born Pigs.
     */
    public void act(List<Animal> newPigs, Foodweb foodweb, boolean isDay)
    {
        incrementAge();
        incrementHunger();
        Field islandField = getIslandField();
        if(isAlive()) {
            if(isDay || NIGHT_MOVEMENT >= rand.nextDouble()){
                giveBirth(newPigs);
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
     * Increase the age.
     * This could result in the Pig's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    
    /**
     * Increase the hunger.
     * This could result in the Pig's death
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
     * Check whether or not this Pig is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newPigs A list to return newly born Pigs.
     */
    private void giveBirth(List<Animal> newPigs)
    {
        // New Pigs are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        Field islandField = getIslandField();

        List<Location> partners = field.adjacentLocations(getLocation());
        int births = breed();
        for(Location partner: partners){
            if(field.getObjectAt(partner) instanceof Pig){            
                Pig pig = (Pig) field.getObjectAt(partner); 
                if(this.getGender() != pig.getGender()){
                    for(int b = 0; b < births && moveAbleLands().size() > 0; b++) {
                        Location loc = moveAbleLands().remove(0);
                        Pig young = new Pig(false, setGender(), field, loc, islandField);
                        newPigs.add(young);
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
     * A Pig can breed if it has reached the breeding age.
     * @return true if the Pig can breed, false otherwise.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }

    /**
     * @return The probability of the Pig spawning
     */
    public double getSpawn()
    {
        return Spawn_Probability;
    }

}

package Animals;

import java.util.List;

import Utility.Field;
import Utility.Foodweb;
import Utility.Location;

/**
 * A simple model of a Fish.
 * Fishes eat seawedd.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Fish extends Herbivore
{
    // Characteristics shared by all fishes (class variables).

    // The age at which a Fish can start to breed.
    private static final int BREEDING_AGE = 180;
    // The age to which a Fish can live.
    private static final int MAX_AGE = 1600;
    // The likelihood of a Fish breeding.
    private static final double BREEDING_PROBABILITY = 0.25;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    // The spawn probability of Fish
    private static double Spawn_Probability = 0.3;
    // The probability of movement at night. 
    private static final double NIGHT_MOVEMENT = 0.5;
    /**
     * Create a new Fish. A Fish may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the Fish will have a random age.
     * @param male The gender of the Fish.
     * @param animalField The animalField currently occupied.
     * @param location The location within the animalField.
     * @param islandField The islandField currently occupied.
     */
    public Fish(boolean randomAge,boolean male, Field animalField, Location location, Field islandField)
    {
        super(male, animalField, location, islandField);
        age = 0;
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
        foodLevel = rand.nextInt(40) + 10;

        whenHungery = 70;
    }

    /**
     * Another constructor for Fishes. 
     */
    public Fish()
    {}

    /**
     * This is what the Fish does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newFishes A list to return newly born fishes.
     */
    public void act(List<Animal> newFishes, Foodweb foodweb, boolean isDay)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            if(isDay || NIGHT_MOVEMENT >= rand.nextDouble()){
            giveBirth(newFishes);
            checkAdjacentAnimals();
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
            }}
        }
    }

    /**
     * This allows us to create a new animal
     * @return Returns a reference of the animal we created
     */
    public Animal createAnimal(boolean randomAge,boolean male, Field animalField, Location location, Field islandField)
    {
        return new Fish(randomAge, male, animalField, location, islandField);
    }
     
    /**
     * @return The probability of the Fish spawning
     */
    public double getSpawn()
    {
        return Spawn_Probability;
    }
     /**
     * Check whether or not this Fish is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newFishes A list to return newly born fishes.
     */
    private void giveBirth(List<Animal> newFishes)
    {
        // New fishes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field animalField = getField();
        Field islandField = getIslandField();

        List<Location> partners = animalField.adjacentLocations(getLocation());
        int births = breed();
        for(Location partner: partners){
            if(animalField.getObjectAt(partner) instanceof Fish){            
                Fish fish = (Fish) animalField.getObjectAt(partner); 
                if(this.getGender() != fish.getGender()){
                    for(int b = 0; b < births && moveAbleLands().size() > 0; b++) {
                        Location loc = moveAbleLands().remove(0);
                        Fish young = new Fish(false, setGender(), animalField, loc, islandField);
                        newFishes.add(young);
                    }
                }
            }
        }
    }

    /**
     * Increase the age.
     * This could result in the Fish's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }

    /**
     * Incease the hunder of each animal.
     */
    private void incrementHunger()
    {
        foodLevel--;
        diseaseHunger();
        if(foodLevel <= 0){
            setDead();
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
     * A Fish can breed if it has reached the breeding age.
     * @return true if the Fish can breed, false otherwise.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE;
   
}
}
package Animals;
import java.util.List;
import java.util.Iterator;
import java.util.Random;

import Utility.Field;
import Utility.Foodweb;
import Utility.Location;

/**
 * The Shark class they eat Fishes and weak Humans. 
 * 
 * @author David J. Barnes, Michael Kölling, Vaibhavkumar patel(k21076223)
 * and mark Emmanuel(k21009638).
 * 
 * @version 2016.02.29 (3)
 */
public class Shark extends Carnivore
{
    // Characteristics shared by all sharks (class variables).

    // The age at which a Sharks can start to breed.
    private static final int BREEDING_AGE = 10;
    // The age to which a fox can live. 
    private static final int MAX_AGE = 2500;
    // The likelihood of a fox breeding.
    private static final double BREEDING_PROBABILITY = 0.16;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 3;
    // The spawn probability of fox
    private static double Spawn_Probability = 0.30;
    
    //--------Individual characteristics (instance fields)--------//
    // The fox's food level, which is increased by eating rabbits.
    private int foodLevel;

    /**
     * Create a fox. A fox can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param animalField The animalField currently occupied.
     * @param location The location within the animalField.
     */
    public Shark(boolean randomAge,boolean male, Field animalField, Location location, Field islandField)
    {
        super(male, animalField, location, islandField);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }else {
            age = 0;
        }
        foodLevel = rand.nextInt(40);
        whenHungery = 70;
    }

    /**
     * Another constructor for sharks.
     */
    public Shark()
    {}

    /**
     * This allows us to create a new animal
     * @return Returns a reference of the animal object we created.
     */
    public Animal createAnimal(boolean randomAge, boolean male, Field animalField, Location location, Field islandField)
    {
        return new Shark(randomAge,male,animalField, location, islandField);
    }

    /**
     * This is what the sharks does most of the time: it hunts for
     * fishes, humans. In the process, it might breed, die of hunger,
     * or die of old age.
     * 
     * @param newSharks A list to return newly born sharks.
     * @param foodweb A foodweb object that stores animals shark can eat.
     * @param isDay A boolean variable that stores if the time is currently day.
     */
    public void act(List<Animal> newSharks, Foodweb foodweb, boolean isDay)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newSharks); //breeding
            checkAdjacentAnimals(); // for disease 
            Location newLocation = findFood(foodweb); //try to find food
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
     * Make this shark more hungry. This could result in the shark's death.
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
     * Check whether or not this shark is ready to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newSharks A list to return newly born sharks.
     */
    private void giveBirth(List<Animal> newSharks)
    {
        // New sharks are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field animalField = getField();
        Field islandField = getIslandField();

        List<Location> partners = animalField.adjacentLocations(getLocation());
        int births = breed();
        for(Location partner: partners){
            if(animalField.getObjectAt(partner) instanceof Shark){            
                Shark shark = (Shark) animalField.getObjectAt(partner); 
                if(this.getGender() != shark.getGender()){
                    for(int b = 0; b < births && moveAbleLands().size() > 0; b++) {
                        Location loc = moveAbleLands().remove(0);
                        Shark young = new Shark(false, setGender(), animalField, loc, islandField);
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
     * A shark can breed if it has reached the breeding age.
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

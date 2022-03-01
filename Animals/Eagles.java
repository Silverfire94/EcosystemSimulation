package Animals;
import java.util.List;

import Lands.Land;
import Utility.Field;
import Utility.Foodweb;
import Utility.Location;

/**
 * The Eagels class they eat Fishes, Pigs, Pythons and weak Humans.
 * 
 * @author David J. Barnes, Michael Kölling, Vaibhavkumar patel(k21076223)
 * and mark Emmanuel(k21009638).
 * @version 2022.03.01 (3)
 * 
 */
public class Eagles extends Carnivore
{
    // Characteristics shared by all eagles (class variables).

    // The age at which a eagle can start to breed.
    private static final int BREEDING_AGE = 8;
    // The age to which a eagle can live changed now to years.
    private static final int MAX_AGE = 3000;
    // The likelihood of a eagle breeding.
    private static final double BREEDING_PROBABILITY = 0.3;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 3;
    // The eye strength is the measure of how far they are moving. 
    private static final int EYE_STRENGTH = 2;
    // The spawn probability of eagle
    private static double Spawn_Probability = 0.3;

    //------------ Individual characteristics (instance animalFields) ------------//
    // The eagle's food level, which is increased by eating rabbits.
    private int foodLevel;

    /**
     * Create a eagle. A eagle can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the eagle will have random age and hunger level.
     * @param male If true, the eagle will be classes as male and vice versa.
     * @param animalField The animalField currently occupied.
     * @param location The location within the animalField.
     * @param islandField The islandField which the eagles are in. 
     */
    public Eagles(boolean randomAge,boolean male, Field animalField, Location location, Field islandField)
    {
        super(male, animalField, location, islandField);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }else {
            age = 0;
        }
        foodLevel = rand.nextInt(30);
        whenHungery = 60;
    }

    /**
     * Another constructor for eagles.
     */
    public Eagles()
    {}

    /**
     * This is what the eagle does most of the time: it hunts for
     * rabbits. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param animalField The animalField currently occupied.
     * @param newEagles A list to return newly born eagles.
     */
    public void act(List<Animal> newEagles, Foodweb foodweb, boolean isDay)
    {
        incrementAge();
        incrementHunger();
        Field islandField = getIslandField();
        if(isAlive()) {
            //checks if daytime.
            if(isDay){ 
                giveBirth(newEagles); //starts to breed. 
                checkAdjacentAnimals(); //checks for disease
                Location newLocation = findFood(foodweb); //trys to find food
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
                    }}
            }
        }
    }

    /**
     * This allows us to create a new animal
     * @return Returns a reference of the animal we created
     * 
     */
    public Animal createAnimal(boolean randomAge, boolean male, Field animalField, Location location, Field islandField)
    {
        return new Eagles(randomAge,male,animalField, location, islandField);
    }

    /**
     * @return The probability of the rabbit spawning
     */
    public double getSpawn()
    {
        return Spawn_Probability;
    }

    /**
     * Check whether or not this eagle is ready to give birth at this step.
     * Also check if the partner is of the opposite gender.
     * New births will be made into free adjacent locations.
     * @param newEagles A list to return newly born eagles.
     */
    private void giveBirth(List<Animal> newEagles)
    {
        // New eagles are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field animalField = getField();
        Field islandField = getIslandField();
        List<Location> partners = animalField.adjacentLocations(getLocation());
        int births = breed();
        for(Location partner: partners){
            if(animalField.getObjectAt(partner) instanceof Eagles){            
                Eagles eagle = (Eagles) animalField.getObjectAt(partner); 
                if(this.getGender() != eagle.getGender()){
                    for(int b = 0; b < births && moveAbleLands().size() > 0; b++) {
                        Location loc = moveAbleLands().remove(0);
                        Eagles young = new Eagles(false, setGender(), animalField, loc, islandField);
                        newEagles.add(young);
                    }
                }
            }
        }
    }

    /**
     * Increase the age. This could result in the eagle's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }

    /**
     * Make this eagle more hungry. This could result in the eagle's death.
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
     * A eagle can breed if it has reached the breeding age.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }

}

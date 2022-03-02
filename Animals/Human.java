package Animals;
import java.util.List;
import Lands.Land;
import Utility.*;


/**
 * Write a description of class Human here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Human extends Carnivore
{
    // Characteristics shared by all humans (class variables).

    // The age at which a human can start to breed.
    private static final int BREEDING_AGE = 80;
    // The age to which a human can live.
    private static final int MAX_AGE = 3500;
    // The likelihood of a human breeding.
    private static final double BREEDING_PROBABILITY = 0.25;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    // The eye strength is the measure of how far they are moving. 
    private static final int EYE_STRENGTH = 2;
    // The probability of movement at night. 
    private static final double NIGHT_MOVEMENT = 0.25;
    // The spawn probability of human
    private static double Spawn_Probability = 0.17;

    /**
     * Create a human. A human can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the Human will have random age and hunger level.
     * @param male the gender of the Human.
     * @param animalField The animalField currently occupied.
     * @param location The location within the animalField.
     * @param islandField The islandField currently occupied.
     */
    public Human(boolean randomAge,boolean male, Field animalField, Location location, Field islandField)
    {
        super(male, animalField, location, islandField);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        } else {
            age = 0;
        }
        foodLevel = rand.nextInt(60) + 65;

        whenHungery = 150;
    }
    /**
     * Another constructor for foxes.
     */
    public Human()
    {}
    
    /**
     * This is what the human does most of the time: it hunts for
     * rabbits. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param animalField The animalField currently occupied.
     * @param newHuman A list to return newly born foxes.
     * @param isDay A boolean variable to check weather it is day or not.
     */
    public void act(List<Animal> newHumans, Foodweb foodweb, boolean isDay)
    {
        incrementAge();
        incrementHunger();
        Field islandField = getIslandField();
        if(isAlive()) {
            if(isDay || NIGHT_MOVEMENT >= rand.nextDouble()){
                giveBirth(newHumans);
                checkAdjacentAnimals();
                // Move towards a source of food if found.
                Land land = (Land) islandField.getObjectAt(getLocation());
                if(land.getWeather().getViewDistance() <= EYE_STRENGTH){  
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
    }
    /**
     * This allows us to create a new animal
     * @return Returns a reference of the animal objectwe created
     */
    public Animal createAnimal(boolean randomAge, boolean male, Field animalField, Location location, Field islandField)
    {
        return new Human(randomAge,male,animalField, location, islandField);
    }

    /**
     * @return The probability of the rabbit spawning
     */
    public double getSpawn()
    {
        return Spawn_Probability;
    }

    
    /**
     * Check whether or not this human is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newHumans A list to return newly born foxes.
     */
    private void giveBirth(List<Animal> newHumans)
    {
        // New humans are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field animalField = getField();
        Field islandField = getIslandField();

        List<Location> partners = animalField.adjacentLocations(getLocation());
        int births = breed();
        for(Location partner: partners){
            if(animalField.getObjectAt(partner) instanceof Human){            
                Human human = (Human) animalField.getObjectAt(partner); 
                if(this.getGender() != human.getGender()){
                    for(int b = 0; b < births && moveAbleLands().size() > 0; b++) {
                        Location loc = moveAbleLands().remove(0);
                        Human young = new Human(false, setGender(), animalField, loc, islandField);
                        newHumans.add(young);
                    }
                }
            }
        }
    }

    /**
     * Increase the age. This could result in the Human's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
        if(age < 5) {
            isWeak = true;
        }
        else {
            isWeak = false;
        }
    }

    /**
     * Make this human more hungry. This could result in the fox's death.
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
     * A human can breed if it has reached the breeding age.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }
}

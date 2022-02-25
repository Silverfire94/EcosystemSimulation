import java.util.List;
import java.util.Random;

/**
 * A simple model of a Pig.
 * Pigs age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Pig extends Animal
{
    // Characteristics shared by all Pigs (class variables).

    // The age at which a Pig can start to breed.
    private static final int BREEDING_AGE = 5;
    // The age to which a Pig can live.
    private static final int MAX_AGE = 60;
    // The likelihood of a Pig breeding.
    private static final double BREEDING_PROBABILITY = 0.15;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    
    // A shared random number generator to control breeding.
    //private static final Random rand = Randomizer.getRandom();
    
    // The spawn probability of Pig
    private static double Spawn_Probability = 0.30;
    
    

    /**
     * Create a new Pig. A Pig may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the Pig will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Pig(boolean randomAge,boolean male, Field field, Location location, Land land)
    {
        super(male, field, location, land);
        age = 0;
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
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
    public Animal createAnimal(boolean randomAge,boolean male, Field field, Location location, Land land)
    {
        return new Pig(randomAge, male, field, location, land);
    }
    
    /**
     * This is what the Pig does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newPigs A list to return newly born Pigs.
     */
    public void act(List<Animal> newPigs, Foodweb foodweb)
    {
        incrementAge();
        if(isAlive()) {
            giveBirth(newPigs);            
            // Try to move into a free location.
            Location newLocation = getField().freeAdjacentLocation(getLocation());
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
     * Check whether or not this Pig is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newPigs A list to return newly born Pigs.
     */
    private void giveBirth(List<Animal> newPigs)
    {
        // New Pigs are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        List<Location> partners = field.adjacentLocations(getLocation());
        int births = breed();
        
        for(Location partner: partners){
            if(field.getObjectAt(partner) instanceof Pig){            
                Pig Pig = (Pig) field.getObjectAt(partner);
                if(this.getGender() != Pig.getGender()){
                    for(int b = 0; b < births && free.size() > 0; b++) {
                        Location loc = free.remove(0);
                        Pig young = new Pig(false, setGender(), field, loc, land);
                        newPigs.add(young);
                    }
                }
            }
        }
    }
    
    public void move(){
        
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

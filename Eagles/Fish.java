import java.util.List;
import java.util.Random;

/**
 * A simple model of a rabbit.
 * Fishes age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Fish extends Animal
{
    // Characteristics shared by all fishes (class variables).

    // The age at which a rabbit can start to breed.
    private static final int BREEDING_AGE = 5;
    // The age to which a rabbit can live.
    private static final int MAX_AGE = 60;
    // The likelihood of a rabbit breeding.
    private static final double BREEDING_PROBABILITY = 0.12;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 5;
    
    // A shared random number generator to control breeding.
    //private static final Random rand = Randomizer.getRandom();
    
    // The spawn probability of Fish
    private static double Spawn_Probability = 0.16;
    
    

    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Fish(boolean randomAge,boolean male, Field field, Location location)
    {
        super(male, field, location);
        age = 0;
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
    }
    
    /**
     * Another constructor for Fishes. 
     */
    public Fish()
    {}
    
    /**
     * This allows us to create a new animal
     * @return Returns a reference of the animal we created
     */
    public Animal createAnimal(boolean randomAge,boolean male, Field field, Location location)
    {
        return new Fish(randomAge, male, field, location);
    }
    
    /**
     * This is what the rabbit does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newFishes A list to return newly born fishes.
     */
    public void act(List<Animal> newFishes, Foodweb foodweb)
    {
        incrementAge();
        if(isAlive()) {
            giveBirth(newFishes);            
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
     * This could result in the rabbit's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    
    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newFishes A list to return newly born fishes.
     */
    private void giveBirth(List<Animal> newFishes)
    {
        // New fishes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        List<Location> partners = field.adjacentLocations(getLocation());
        int births = breed();
        
        for(Location partner: partners){
            if(field.getObjectAt(partner) instanceof Fish){            
                Fish rabbit = (Fish) field.getObjectAt(partner);
                if(this.getGender() != rabbit.getGender()){
                    for(int b = 0; b < births && free.size() > 0; b++) {
                        Location loc = free.remove(0);
                        Fish young = new Fish(false, setGender(), field, loc);
                        newFishes.add(young);
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
     * A rabbit can breed if it has reached the breeding age.
     * @return true if the rabbit can breed, false otherwise.
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

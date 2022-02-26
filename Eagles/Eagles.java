import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * class for the egales they eat rabbits and the snakes. 
 * 
 * @author Vaibhavkumar Patel(k21076223) and Mark Emmanuel(k21009628).
 * @version 2022.02.14 (1)
 */
public class Eagles extends Animal
{
    // Characteristics shared by all eagles (class variables).
    
    // The age at which a eagle can start to breed.
    private static final int BREEDING_AGE = 18;
    // The age to which a eagle can live.
    private static final int MAX_AGE = 100;
    // The likelihood of a eagle breeding.
    private static final double BREEDING_PROBABILITY = 0.1;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 3;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a eagle can go before it has to eat again.
    
    // A shared random number generator to control breeding.
    //private static final Random rand = Randomizer.getRandom();
    
    // The spawn probability of eagle
    private static double Spawn_Probability = 0.15;
    
    // Individual characteristics (instance fields).

    // The eagle's food level, which is increased by eating rabbits.
    //private int foodLevel;
    
      /**
     * Create a eagle. A eagle can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the eagle will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Eagles(boolean randomAge,boolean male, Field field, Location location, Field islandField)
    {
        super(male, field, location, islandField);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
            foodLevel = rand.nextInt(30);
        }
        else {
            age = 0;
            foodLevel = 20;
        }
        
        whenHungery = 60;
    }
    
    /**
     * Another constructor for eagles.
     */
    public Eagles()
    {}
    
    /**
     * This allows us to create a new animal
     * @return Returns a reference of the animal we created
     */
    public Animal createAnimal(boolean randomAge, boolean male, Field field, Location location, Field islandField)
    {
        return new Eagles(randomAge,male,field, location, islandField);
    }
    
    /**
     * This is what the eagle does most of the time: it hunts for
     * rabbits. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newEagles A list to return newly born eagles.
     */
    public void act(List<Animal> newEagles, Foodweb foodweb)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newEagles);            
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
        if(foodLevel <= 0) {
            setDead();
        }
    }
    
    // /**
     // * Look for rabbits adjacent to the current location.
     // * Only the first live rabbit is eaten.
     // * @return Where food was found, or null if it wasn't.
     // */
    // private Location findFood(Foodweb foodweb)
    // {
        // Field field = getField();
        // List<Location> adjacent = field.adjacentLocations(getLocation());
        // Iterator<Location> it = adjacent.iterator();
        // while(it.hasNext()) {
            // Location where = it.next();
            // Object animal = field.getObjectAt(where);
            // List<Food> foodtypes = foodweb.getFood(this.getClass());
            // for (Food food : foodtypes)
            // {
                // Class animaltype = food.getAnimalType();
                // if (animal != null && animal.getClass() == animaltype)
                // {
                    // Animal animalToEat = (Animal) animal;
                    // // Checks if this animal is strong against the food
                    // if(animalToEat.isAlive() && 
                        // (food.getStrongAgainst() || animalToEat.getIsWeak()))
                    // {
                        // animalToEat.setDead();
                        // foodLevel = food.getFoodValue();
                        // return where;
                    // }
                // }
            // }
        // }
        // return null;
    // }
    
    /**
     * Check whether or not this eagle is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newEagles A list to return newly born eagles.
     */
    private void giveBirth(List<Animal> newEagles)
    {
        // New eagles are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        Field islandField = getIslandField();
        
        List<Location> partners = field.adjacentLocations(getLocation());
        int births = breed();
        for(Location partner: partners){
        if(field.getObjectAt(partner) instanceof Eagles){            
            Eagles eagle = (Eagles) field.getObjectAt(partner); 
            if(this.getGender() != eagle.getGender()){
                for(int b = 0; b < births && moveAbleLands().size() > 0; b++) {
                    Location loc = moveAbleLands().remove(0);
                    Eagles young = new Eagles(false, setGender(), field, loc, islandField);
                    newEagles.add(young);
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
     * A eagle can breed if it has reached the breeding age.
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

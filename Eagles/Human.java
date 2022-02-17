import java.util.List;
import java.util.Iterator;

/**
 * Write a description of class Human here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Human extends Animal
{
    // Characteristics shared by all humans (class variables).

    // The age at which a human can start to breed.
    private static final int BREEDING_AGE = 18;
    // The age to which a human can live.
    private static final int MAX_AGE = 120;
    // The likelihood of a human breeding.
    private static final double BREEDING_PROBABILITY = 0.08;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 3;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a human can go before it has to eat again.
    private static final int RABBIT_FOOD_VALUE = 60;

    // A shared random number generator to control breeding.
    //private static final Random rand = Randomizer.getRandom();

    // The spawn probability of human
    private static double Spawn_Probability = 0.10;

    // Individual characteristics (instance fields).

    // The fox's food level, which is increased by eating rabbits.
    // private int foodLevel;

    /**
     * Create a human. A human can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the human will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Human(boolean randomAge,boolean male, Field field, Location location)
    {
        super(male, field, location);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
            foodLevel = rand.nextInt(50);
        }
        else {
            age = 0;
            foodLevel = 20;
        }
        
        whenHungery = 70;
    }

    /**
     * Another constructor for foxes.
     */
    public Human()
    {}

    /**
     * This allows us to create a new animal
     * @return Returns a reference of the animal we created
     */
    public Animal createAnimal(boolean randomAge, boolean male, Field field, Location location)
    {
        return new Human(randomAge,male,field, location);
    }

    /**
     * This is what the human does most of the time: it hunts for
     * rabbits. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newHuman A list to return newly born foxes.
     */
    public void act(List<Animal> newHumans, Foodweb foodweb)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newHumans);            
            // Move towards a source of food if found.
            Location newLocation = findFood(foodweb);
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
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
     * Check whether or not this human is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newHumans A list to return newly born foxes.
     */
    private void giveBirth(List<Animal> newHumans)
    {
        // New humans are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        List<Location> partners = field.adjacentLocations(getLocation());
        int births = breed();
        for(Location partner: partners){
            if(field.getObjectAt(partner) instanceof Human){            
                Human human = (Human) field.getObjectAt(partner); 
                if(this.getGender() != human.getGender()){
                    for(int b = 0; b < births && free.size() > 0; b++) {
                        Location loc = free.remove(0);
                        Human young = new Human(false, setGender(), field, loc);
                        newHumans.add(young);
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
     * A human can breed if it has reached the breeding age.
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

package Plants;


import Utility.*;
import Lands.Land;
/**
 * This simulates how Seaweed acts in the simulation.
 *
* @author Vaihbavkumar patel(k21076223) and mark emmanuel(k21009638).
 * @version (3) 2022.03.01.
 */
public class Seaweed extends Plant
{
    // The likelyhood of the plant spawning.
    private static double spawnProbability = 1;
    // The limit of its size.
    private static final int SIZELIMIT = 60;
    // The growth rate.
    private int growthRate = 1;
    // The location of the plant.
    private Location location;

    /**
     * Constructor for objects of class Seaweed.
     * @param randomsize If the plant is spawned in with random size or not.
     * @param Location the plants location 
     */
    public Seaweed(boolean randomSize, Location location)
    {
        super();
        this.location = location; 
        if(randomSize){
            size = rand.nextInt(SIZELIMIT);
        }
    }
    
    /**
     * A dummy constructor.
     */
    public Seaweed()
    {}

    /**
     * This creates new grass.
     * @param randomSize True if the size should be random.
     * @param location The location where the plant will be stored at.
     * @Return The new Grass object.
     */
    public Plant createPlant(boolean randomSize, Location location)
    {
        return new Seaweed(randomSize, location);
    }

    /**
     * This method is for the actions taken by the plant
     * during each step.
     * @param isDay True if it is dayTime.
     * @param islandField The field in which the plant is stored in. 
     */
    public void act(boolean isDay, Field islandField)
    {      
        if(isDay){
            grow();
        }
    }

    /**
     * This allow the size of the seaweed to increase
     * until it is above the growthLimit.
     */
    public void grow()
    {
        if(size < SIZELIMIT){
            size += growthRate;
        }
        else
        {
            setBig();
        }
    }
    
    /**
     * Indicate that the plant is no longer big
     */
    public void setSmall()
    {
        super.setSmall();
        size = 0;
    }

    /**
     * @Return The spawn probability of seaweed.
     */
    public double getSpawn()
    {
        return spawnProbability;
    }
}

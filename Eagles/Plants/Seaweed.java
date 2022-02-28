package Plants;


import Utililty.*;
import Lands.Land;
/**
 * Write a description of class Seaweed here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Seaweed extends Plant
{
    // instance variables - replace the example below with your own
    // The likelyhood of the plant spawning
    private static double spawnProbability = 1;
    // The limit of its size
    private static final int SIZELIMIT = 15;
    // The growth rate
    private int growthRate = 1;
    
    private Location location;

    /**
     * Constructor for objects of class Grass
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
     * 
     */
    public Seaweed()
    {
        
    }

    /**
     * @Return The spawn probability of grass
     */
    public double getSpawn()
    {
        return spawnProbability;
    }

    /**
     * This creates new grass
     * @Return The new Grass object
     */
    public Plant createPlant(boolean randomSize, Location location)
    {
        return new Seaweed(randomSize, location);
    }

    /**
     * This method is for the actions taken by the obejct of this 
     * class during each step.
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
}

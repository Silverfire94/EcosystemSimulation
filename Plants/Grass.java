package Plants;

import Utility.*;
import Lands.Land;
/**
 * This simulates how grass acts in the simulation.
 *
 * @author Mark Emmanuel and Vaibhavkumar Patel
 * @version 2.0
 */
public class Grass extends Plant
{
    // The likelyhood of the plant spawning.
    private static double spawnProbability = 1;
    // The limit of its size.
    private static final int SIZELIMIT = 10;
    // The growth rate.
    private int growthRate = 1;
    // Its location in the islandField.
    private Location location;

    /**
     * Constructor for objects of class Grass
     */
    public Grass(boolean randomSize, Location location)
    {
        super();
        this.location = location; 
        if(randomSize){
            size = rand.nextInt(SIZELIMIT);
        }
    }
    
    /**
     * A dummy constuctor.
     */
    public Grass()
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
        return new Grass(randomSize, location);
    }

    /**
     * Checks if it is daytime before growing the plant.
     * Additionally checks the weather at that location and changes the growth rate.
     * 
     * @param isDay True if it is daytime.
     * @param islandField The field in which the plant is stored in.
     */
    public void act(boolean isDay, Field islandField)
    {
        Land land = (Land) islandField.getObjectAt(location);
        growthRate = land.getWeather().getGrowthRate();        
        if(isDay){
            grow();
        }
    }

    /**
     * This allow the size of the grass to increase
     * until it is above the growthLimit.
     */
    public void grow()
    {
        if(size <= SIZELIMIT){
            size += growthRate;
        }
        else
        {
            setBig();
        }
    }
    
    /**
     * Indicate that the plant is no longer big.
     */
    public void setSmall()
    {
        super.setSmall();
        size = 0;
    }
}

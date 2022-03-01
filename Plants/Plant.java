package Plants;
import java.util.Random;

import Utility.*;

/**
 * This simulates how a plant behaves in the simulation.
 *
 * @author Mark Emmanuel and Vaibhavkumar Patel
 * @version 2.0
 */
public abstract class Plant
{
    // Whether the plant is big enough to display
    private boolean isBig;
    // The size of the plant
    protected int size;
    // The amount of water
    protected int whenThirsty;
    // A shared random number generator to control the begining size.
    protected static final Random rand = Randomizer.getRandom();
    
    /**
     * Constructor for creating Plant objects.
     */
    public Plant()
    {
        isBig = true;
    }
    
    /**
     * Check whether the plant isBig.
     * @return true if the plant is big.
     */
    public boolean isBig()
    {
        return isBig;
    }

    /**
     * Indicate that the plant is no longer big.
     */
    public void setSmall()
    {
        isBig = false;
    }
    
    /**
     * This sets that the plant is big enough.
     */
    protected void setBig()
    {
        isBig = true;
    }
    
    /**
     * @Return The size of the plant.
     */
    protected int getSize()
    {
        return size;
    }
    
    /**
     * @Return The probability of the plant spawning.
     */
    abstract public double getSpawn();
    
    /**
     * Creates a new Plant object.
     * @param randomSize True if the we want the size to be random.
     * @param location The location at where plant is stored at.
     * @Return The plant object.
     */
    abstract public Plant createPlant(boolean randomSize , Location location);

    /**
     * This controls how the plant acts every step.
     * @param isDay True if it is daytime.
     * @param islandField The field in which the plant is stored.
     */    
    abstract public void act(boolean isDay, Field islandField);
}

package Plants;
import java.util.Random;

import Utililty.*;
import Utililty.Randomizer;

/**
 * Interface class Actor - write a description of the class here
 *
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class Plant
{
    // instance variables - replace the example below with your own
    // Whether the plant is big enough to display
    private boolean isBig;
    // The size of the plant
    protected int size;
    // The amount of water
    protected int whenThirsty;
    // A shared random number generator to control the begining size.
    protected static final Random rand = Randomizer.getRandom();
    public Plant()
    {
        isBig = true;
    }
    
    /**
     * Check whether the plant isBig
     * @return true if the plant is big
     */
    public boolean isBig()
    {
        return isBig;
    }

    /**
     * Indicate that the plant is no longer big
     */
    public void setSmall()
    {
        isBig = false;
    }
    
    /**
     * 
     */
    protected void setBig()
    {
        isBig = true;
    }
    
    /**
     * @Return The size of the plant
     */
    protected int getSize()
    {
        return size;
    }
    
    /**
     * This return the probability of spawning the plant
     */
    abstract public double getSpawn();
    
    /**
     * Alls the animal to create new animals
     */
    abstract public Plant createPlant(boolean randomSize , Location location);

    /**
     * This controls how the plant acts every step
     */    
    abstract public void act(boolean isDay, Field islandField);
}

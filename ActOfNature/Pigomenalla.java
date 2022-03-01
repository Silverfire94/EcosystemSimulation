package ActOfNature;

import Animals.Pig;
/**
 * Write a description of class PigDisease here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Pigomenalla extends Disease
{
    private static double SPAWN_PROBABILITY = 0.3;
    private static double SPREAD_PROBABILITY = 0.1;
    
    /**
     * Constructor for objects of class PigDisease
     */
    public Pigomenalla()
    {
        super();
        setHungerIncrease(2);
        
        susceptibleAnimals.add(Pig.class);
    }
    
    public double getSpawn()
    {
        return SPAWN_PROBABILITY;
    }
    
    public Disease createDisease()
    {
        return new Pigomenalla();
    }
    
    public double getSpreadProbability()
    {
        return SPREAD_PROBABILITY;
    }
}

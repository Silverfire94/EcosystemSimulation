package ActOfNature;

import Animals.Pig;
/**
 * This simulates a disease that only affects pigs.
 *
 * @author Mark Emmanuel and Vaibhavkumar Patel
 * @version 2.0
 */
public class Pigomenalla extends Disease
{
    // How likely is the disease to spawn in an animal.
    private static double SPAWN_PROBABILITY = 0.3;
    // How likely is the disease to spread to another animal.
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
    
    /**
     * @Return The spawn probability of the disease.
     */
    public double getSpawn()
    {
        return SPAWN_PROBABILITY;
    }
    
    /**
     * Creates a new Pigomenalla object.
     * @Return A Pigomenalla object.
     */
    public Disease createDisease()
    {
        return new Pigomenalla();
    }
    
    /**
     * @Return The probability of the disease spreading.
     */
    public double getSpreadProbability()
    {
        return SPREAD_PROBABILITY;
    }
}

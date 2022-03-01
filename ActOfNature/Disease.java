package ActOfNature;
import java.util.List;
import java.util.ArrayList;


/**
 * This simulates a disease which affects animals by
 * reducing their food level faster.
 *
 * @author Mark Emmanuel and Vaibhavkumar Patel
 * @version 2.0
 */
public abstract class Disease
{
    // This determine how fast the organism gets hungry
    private int hungerIncrease;
    // This is a list of animals susceptable to this disease
    protected List<Class> susceptibleAnimals = new ArrayList<>();
    
    /**
     * Constructor for objects of class Diesease
     */
    public Disease()
    {
        
    }

    /**
     * @Return The value of the hungerIncrease.
     */
    public int getHungerIncrease()
    {
        return hungerIncrease;
    }
    
    /**
     * @Return The list of animals that can be infected with the disease.
     */
    public List<Class> getSusceptableAnimals()
    {
        return susceptibleAnimals;
    }
    
    /**
     * This sets the amount by which the foodLevel decreases.
     * @param value The setting value.
     */
    protected void setHungerIncrease(int value)
    {
        hungerIncrease = value;
    }

    /**
     * @Return The spawn probability of the disease.
     */
    abstract public double getSpawn();
    
    /**
     * @Return This creates a new Disease object and returns it.
     */
    abstract public Disease createDisease();
    
    /**
     * @Return The spreading probability of the disease.
     */
    abstract public double getSpreadProbability();
}

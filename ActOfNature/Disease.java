package ActOfNature;
import java.util.List;
import java.util.ArrayList;


/**
 * Write a description of class Diesease here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Disease
{
    // This determine how fast the organism gets hungry
    private int hungerIncrease;
    protected List<Class> susceptibleAnimals = new ArrayList<>();
    

    /**
     * Constructor for objects of class Diesease
     */
    public Disease()
    {
        
    }

    public int getHungerIncrease()
    {
        return hungerIncrease;
    }
    
    public List<Class> getSusceptableAnimals()
    {
        return susceptibleAnimals;
    }
    
    protected void setHungerIncrease(int value)
    {
        hungerIncrease = value;
    }
    
    abstract public double getSpawn();
    
    abstract public Disease createDisease();
    
    abstract public double getSpreadProbability();
}

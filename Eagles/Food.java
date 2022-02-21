
/**
 * Write a description of class Food here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Food
{
    // instance variables - replace the example below with your own
    private Class animalType;
    private boolean strongAgainst;
    private int foodValue;

    /**
     * Constructor for objects of class Food
     */
    public Food(Class animalType, boolean strongAgainst, int foodValue)
    {
        this.animalType = animalType;
        this.strongAgainst = strongAgainst;
        this.foodValue = foodValue;
    }

    /**
     * @Return The class of the animal
     */
    public Class getAnimalType()
    {
        return animalType;
    }
    
    /**
     * @Return The class of the animal
     */
    public boolean getStrongAgainst()
    {
        return strongAgainst;
    }
    
    /**
     * @Return The class of the animal
     */
    public int getFoodValue()
    {
        return foodValue;
    }
}

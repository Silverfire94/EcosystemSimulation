package Utility;
/**
 * Describes how the predator interacts with the prey.
 *
 * @author Mark Emmanuel and Vaibhavkumar Patel
 * @version 2.0
 */
public class Food
{
    // Class of the prey.
    private Class foodType;
    // True if the organism doesn't need to be weak.
    private boolean strongAgainst;
    // The amount of food that the organism recieves when
    // eating the prey.
    private int foodValue;

    /**
     * Constructor for objects of class Food
     */
    public Food(Class foodType, boolean strongAgainst, int foodValue)
    {
        this.foodType = foodType;
        this.strongAgainst = strongAgainst;
        this.foodValue = foodValue;
    }

    /**
     * @Return The class of the animal
     */
    public Class getFoodType()
    {
        return foodType;
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

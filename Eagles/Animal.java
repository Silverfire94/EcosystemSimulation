import java.util.List;
import java.util.Random;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public abstract class Animal 
{
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    // The gender of the animal.
    protected boolean male;
    // The age of the animal. 
    protected int age;
    // A shared random number generator to control breeding.
    protected static final Random rand = Randomizer.getRandom();

    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(boolean male, Field field, Location location)
    {
        alive = true;
        this.field = field;
        this.male = male;
        setLocation(location);
    }
    
    /**
     * Creates a new animal for the animalTypes array
     */
    public Animal()
    {
        
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    abstract public void act(List<Animal> newAnimals);

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }

    /**
     * Returns the gender of the animal. 
     */
    protected boolean setGender(){ 
        if(rand.nextDouble() <= 0.5){
            male = true;  
        }else{
            male = false; 
        }
        return male;
    }
    
    protected boolean getGender(){
        return male;
    }
    
    protected int getAge(){
        return age;    
    }
    
    /**
     * This return the probability of spawning the animal
     */
    abstract public double getSpawn();
    
    /**
     * Alls the animal to create new animals
     */
    abstract public Animal createAnimal(boolean randomAge,boolean male, Field field, Location location);
    
    
}

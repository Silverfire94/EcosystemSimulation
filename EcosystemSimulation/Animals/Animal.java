package Animals;
import java.util.List;
import java.util.Random;

import Lands.Land;
import Utililty.AnimalLand;
import Utililty.Field;
import Utililty.Foodweb;
import Utililty.Location;
import Utililty.Randomizer;

import java.util.Iterator;
import java.util.ArrayList;

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
    //
    private Field islandField;
    // The gender of the animal.
    protected boolean male;
    // The age of the animal. 
    protected int age;
    // Telling us if the animal is weak
    protected boolean isWeak;
    // This is the current food that the animal has
    protected int foodLevel;
    // The maximum food that an animal can eat
    protected int whenHungery;
    // A shared random number generator to control breeding.
    protected static final Random rand = Randomizer.getRandom();
    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(boolean male, Field field, Location location, Field islandField)
    {
        alive = true;
        this.field = field;
        this.male = male;
        this.islandField = islandField;
        setLocation(location);
        isWeak = false;
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
    abstract public void act(List<Animal> newAnimals, Foodweb foodweb, boolean isDay);

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    public boolean isAlive()
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
    public boolean setGender(){ 
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
    abstract public Animal createAnimal(boolean randomAge,boolean male, Field field, Location location, Field islandField);

    /**
     * 
     */
    protected List<Location> moveAbleLands()
    {
        Field animalField = getField();
        AnimalLand animalLand = new AnimalLand();
        
        List<Location> freeLand = islandField.adjacentLocations(getLocation());
        List<Location> freeAdjacentLoc = field.getFreeAdjacentLocations(getLocation());
        List<Location> freeLocations = new ArrayList();
        List<Class> movableLand = animalLand.getLandMap().get(this.getClass());
        
        Iterator<Location> it1 = freeLand.iterator();
        Iterator<Location> it2 = freeAdjacentLoc.iterator();
        
        
        while(it1.hasNext()){
                Location whereFreeLand = it1.next();
                Land land = (Land) islandField.getObjectAt(whereFreeLand);
                boolean found = false;
                for(Class landClass : movableLand){
                    if(land.getClass() == landClass){
                        found = true;
                    }
                }
                
                if(!found){
                    it1.remove();
                }
        }
        
        
        
        while(it2.hasNext()){
            Location whereNoAnimal = it2.next();
            for(Location location : freeLand){
                if(location.equals(whereNoAnimal)){
                    freeLocations.add(location);
                }
            }
        }
        
        
        return freeLocations;
    }
    
    protected Location getMoveAbleLand()
    {
        // The available free ones.
        List<Location> free = moveAbleLands();
        if(free.size() > 0) {
            return free.get(0);
        }
        else {
            return null;
        }
    }

    public Field getIslandField(){
        return islandField;
    }

    public boolean getIsWeak()
    {
        return isWeak;
    }

    abstract protected Location findFood(Foodweb foodweb);
    
    // protected Location findFood(Foodweb foodweb)
    // {
        // Field field = getField();
        // Iterator<Location> it = moveAbleLands().iterator();
        // while(it.hasNext()) {
            // Location where = it.next();
            // Object animal = field.getObjectAt(where);
            // List<Food> foodtypes = foodweb.getFood(this.getClass());
            // for (Food food : foodtypes)
            // {
                // Class animaltype = food.getAnimalType();
                // if (animal != null && animal.getClass() == animaltype)
                // {
                    // Animal animalToEat = (Animal) animal;
                    // // Checks if this animal is strong against the food
                    // if(animalToEat.isAlive() && foodLevel < whenHungery &&
                    // (food.getStrongAgainst() || animalToEat.getIsWeak()))
                    // {
                        // animalToEat.setDead();
                        // foodLevel += food.getFoodValue();
                        // return where;
                    // }
                // }
            // }
        // }
        // return null;
    // }
    
    

}

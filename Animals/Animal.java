package Animals;
import java.util.List;
import java.util.Random;

import Lands.Land;
import Utility.*;
import ActOfNature.Disease;

import java.util.Iterator;
import java.util.ArrayList;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes, Michael Kölling, Vaibhavkumar patel(k21076223)
 * and mark Emmanuel(k21009638).
 * @version 2022.03.01 (3)
 */
public abstract class Animal 
{   

    //---------------private field -------------//
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's animalField.
    private Field animalField;
    // The animal's position in the animalField.
    private Location location;
    // The land field the animals walk on.
    private Field islandField;
    // The gender of the animal.
    //-------------------------------------------//
    
    //---------------protected field--------------//
    protected boolean male;
    // The age of the animal. 
    protected int age;
    // Telling us if the animal is weak
    protected boolean isWeak;
    // This is the current food that the animal has
    protected int foodLevel;
    // The maximum food that an animal can eat
    protected int whenHungery;
    // This stores a disease
    protected List<Disease> disease;
    // A shared random number generator to control breeding.
    protected static final Random rand = Randomizer.getRandom();
    //-------------------------------------------//

    /**
     * Create a new animal at location in animalField.
     * 
     * @param animalField The animalField currently occupied.
     * @param location The location within the animalField.
     */
    public Animal(boolean male, Field animalField, Location location, Field islandField)
    {
        alive = true;
        this.animalField = animalField;
        this.male = male;
        this.islandField = islandField;
        setLocation(location);
        isWeak = false;
        disease = new ArrayList<>();
        
    }

    /**
     * Creates a new animal for the animalTypes array
     */
    public Animal()
    {}
    
    // --------------------------abstract method --------------------//
    /**
     * This return the probability of spawning the animal
     */
    abstract public double getSpawn();

    /**
     * Alls the animal to create new animals
     */
    abstract public Animal createAnimal(boolean randomAge,boolean male, Field animalField, Location location, Field islandField);
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    abstract public void act(List<Animal> newAnimals, Foodweb foodweb, boolean isDay);
    
    /**
     * Used by the animals to find food.
     * @param foodweb used to check what animal can eat what.
     * @return A location of the food.
     */
    abstract protected Location findFood(Foodweb foodweb);
    
    
    // -----------------------Public methods -------------------//
    /**
     * Used to simulate disease
     * @param givenDisease is the dieseasee that will be given to an animal.
     */
    public void addDisease(Disease givenDisease)
    {
        boolean diseaseNotFound = true;
        for(Disease dis : disease){
            if(dis.getClass() == givenDisease.getClass()){
                diseaseNotFound = false;
            }
        }
        if(diseaseNotFound){
            disease.add(givenDisease);
            isWeak = true;
        }
        
    }

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    public boolean isAlive()
    {
        return alive;
    }

    /**
     * @return the gender of the animal. 
     */
    public boolean setGender(){ 
        if(rand.nextDouble() <= 0.5){
            male = true;  
        }else{
            male = false; 
        }
        return male;
    }

    /**
     * 
     * @return the boolean value isWeak.
     */
    public boolean getIsWeak()
    {
        return isWeak;
    }

    /**
    * 
    * @return the list of disease an animal has.
    */
    public List<Disease> getDiseases()
    {
        return disease;
    }

    /**
     * 
     * @return the islandField that the animal is on.
     */
    public Field getIslandField(){
        return islandField;
    }

    //--------------Protected methods ----------//
    /**
     * This method returns the free location around an animal that it can walk on.
     * @return List<Location> that are free for animal to move to.
     */
    protected List<Location> moveAbleLands()
    {
        Habitat animalLand = new Habitat();
        //-------------List-------------------//
        List<Location> freeLand = islandField.adjacentLocations(getLocation());
        List<Location> freeAdjacentLoc = animalField.getFreeAdjacentLocations(getLocation());
        List<Location> freeLocations = new ArrayList();
        List<Class> movableLand = animalLand.getLandMap().get(this.getClass());

        //---------------Iterators-----------//
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
        // return a list of adjacent location that are free and the animal can walk on.
        return freeLocations;
    }


    /**
     * This simulates a spread of disease.
     * By checking the adjacent animals if the animals are infected then 
     * the animal will have a chance of being infected.
     */
    protected void checkAdjacentAnimals()
    {
        Field animalField = getField();
        Iterator<Location> it = animalField.adjacentLocations(getLocation()).iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = animalField.getObjectAt(where);
                if (animal != null && animal.getClass() == this.getClass())
                {
                    Animal adjacentAnimal = (Animal) animal;
                    List<Disease> diseases = new ArrayList<>(adjacentAnimal.getDiseases());
                    
                    for(Disease disease : diseases){
                        if(rand.nextDouble() <= disease.getSpreadProbability()){
                            addDisease(disease);
                        }
                    }
                }
        }
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the animalField.
     * 
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            animalField.clear(location);
            location = null;
            animalField = null;
        }
    }

    /**
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }

    /**
     * Place the animal at the new location in the given animalField.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            animalField.clear(location);
        }
        location = newLocation;
        animalField.place(this, newLocation);
    }

    /**
     * @return The animal's animalField.
     */
    protected Field getField()
    {
        return animalField;
    }

    /** 
     * @return The gender of the animal.
     */
    protected boolean getGender(){
        return male;
    }

    /**
     * 
     * @return The age of the animal.
     */
    protected int getAge(){
        return age;    
    }

    /**
     * 
     * @return returns a single location where the animal can move to.
     */
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

    
    /**
     * This decrements food level quicker than usual due to a disease.
     */
    protected void diseaseHunger()
    {
        for(Disease dis : disease){
            foodLevel -= dis.getHungerIncrease();
        }
    }
    
}

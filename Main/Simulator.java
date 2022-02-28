package Main;
import java.util.Random;

import Animals.*;
import Lands.*;
import Plants.*;
import Utililty.*;
import ActOfNature.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;
import java.util.HashMap;


/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing Pigs and Pythones.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 100;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 100;
    //----------------------------------------//
    // List of animals in the field.
    private List<Animal> animals;
    // List of lands in the islandField.
    private List<Land> lands;
    // List of plants in the field
    private List<Plant> plants;
    // List of animalTypes in the field
    private List<Animal> animalTypes;
    // List of plantTypes in the islandField
    private List<Plant> plantTypes;
    // List of landTypes in the islandField.
    private List<Land> landTypes; 
    // List of WeatherTypes in the islandField.
    private List<Weather> weatherTypes;
    //----------------------------------------//
    // The food web of the ecosystem
    private Foodweb foodweb;
    //----------------------------------------//
    // The map object used to spawn in the type of land. 
    private Map map;
    // The current state of the field.
    private Field field;
    // The current state of the island field.
    private Field islandField;
    //----------------------------------------//
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;

    private HashMap<Land, Location> x;

    public static void main(String [] args){
        Simulator simulator = new Simulator(100, 100);
        simulator.runLongSimulation();
    }

    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }

        animals = new ArrayList<>();
        animalTypes = new ArrayList<>();
        plantTypes = new ArrayList<>();
        lands = new ArrayList<>();
        landTypes = new ArrayList<>();
        weatherTypes = new ArrayList<>();
        
        field = new Field(depth, width);
        islandField = new Field(depth, width);
        map = new Map(depth, width);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.setColor(Pig.class, Color.PINK);
        view.setColor(Python.class, Color.YELLOW);
        view.setColor(Human.class, Color.white);
        view.setColor(Eagles.class, Color.MAGENTA);
        view.setColor(Fish.class, Color.ORANGE);
        view.setColor(Shark.class, Color.red);
        // land 
        view.setColor(Water.class, Color.BLUE); 
        view.setColor(Ground.class, new Color(150,75,0));
        view.setColor(ShallowWater.class, Color.CYAN);
        
        // plants 
        view.setColor(Grass.class, Color.GREEN);
        view.setColor(Seaweed.class, new Color(0, 0, 0, 0));
        
        // weather - Change the Alpha value to see the clouds and the rainy cloud.
        view.setColor(Raining.class, new Color(15, 15, 15, 0));
        view.setColor(Cloudy.class, new Color(128, 128, 128, 0));
        view.setColor(Sunny.class, new Color(0,0,0,0));
       
        // Putting all animal types in the animal types array.
        animalTypes.add(new Pig());
        //animalTypes.add(new Python());
        animalTypes.add(new Human());
        animalTypes.add(new Eagles());
        //animalTypes.add(new Fish());
        animalTypes.add(new Shark());
        
        // Putting all land types in the land types array.
        landTypes.add(new Ground());
        landTypes.add(new Water());
        landTypes.add(new ShallowWater());
        
        // Putting all plant types in the plant types array.
        plantTypes.add(new Grass());
        plantTypes.add(new Seaweed());
        
        // Putting all weather types in the weather types array.
        weatherTypes.add(new Sunny());
        weatherTypes.add(new Cloudy());
        weatherTypes.add(new Raining());
        
        // Creating a new foodweb
        foodweb = new Foodweb();

        // Setup a valid starting point.
        reset();
    }

    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation()
    {
        simulate(4000);
    }

    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
            //delay(60);   // uncomment this to run more slowly
        }
    }

    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * Python and Pig.
     */
    public void simulateOneStep()
    {
        step++;

        //boolean isDay = (step % 2) > 0;

        // Provide space for newborn animals.
        List<Animal> newAnimals = new ArrayList<>();        
        // Let all Pythons act.
        for(Iterator<Animal> it = animals.iterator(); it.hasNext(); ) {
            Animal animal = it.next();
            animal.act(newAnimals, foodweb, Time.isDay(5, step));
            if(! animal.isAlive()) {
                it.remove();
            }
        }
        
        for(Iterator<Land> it = lands.iterator(); it.hasNext(); ) {
            Land land = it.next();
            if(land.getPlant() != null){
                land.getPlant().act(Time.isDay(5, step), islandField);
            }
        }
        
        if(Time.isDay2(10,step)){
            simulateWeather();
        }
        // Add the newly born Pythones and Pythons to the main lists.
        animals.addAll(newAnimals);
        view.showStatus(step, field, islandField);
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        animals.clear();
        lands.clear();
        drawLand();
        populatePlants();
        populate();
        simulateWeather();

        // Show the starting state in the view.
        view.showStatus(step, field, islandField);
    }

    /**
     * Randomly populate the field with Pythones and Pythons.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        AnimalLand animalLand = new AnimalLand();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                //returns a random animal object.
                Animal animal = animalTypes.get(rand.nextInt(animalTypes.size()));
                List<Class> spawnLand = animalLand.getLandMap().get(animal.getClass());
                for(Class landClass : spawnLand){
                    if(rand.nextDouble() <= animal.getSpawn()) {
                        //spawns the animal at the location and then adds the animal to the animal list.            
                        Location location = new Location(row, col);
                        Land typeOfLand = (Land) islandField.getObjectAt(row, col);
                        if(typeOfLand.getClass() == landClass){
                            animals.add(animal.createAnimal(true, animal.setGender(), field, location, islandField));
                        }
                    }
                }
            }
        }
    }
    
    private void populatePlants()
    {
        Random rand = Randomizer.getRandom();
        AnimalLand plantLand = new AnimalLand();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                //returns a random animal object.
                Plant plant = plantTypes.get(rand.nextInt(plantTypes.size()));
                List<Class> spawnLand = plantLand.getLandMap().get(plant.getClass());
                for(Class landClass : spawnLand){
                    if(rand.nextDouble() <= plant.getSpawn()) {
                        //spawns the animal at the location and then adds the animal to the animal list.
                        Location location = new Location(row, col);
                        Land typeOfLand = (Land) islandField.getObjectAt(row, col);
                        if(typeOfLand.getClass() == landClass){
                            typeOfLand.addPlant(plant.createPlant(true, location));
                        }
                    }
                }
            }
        }
    }

    /**
     * Maybe can merge with the method above.
     */
    private void drawLand()
    {
        int [][] landmap = map.GenerateMap();
        Random rand = Randomizer.getRandom();
        islandField.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                for(Land land : landTypes){
                    Location location = new Location(row, col);
                    if(land.matchId(landmap[row][col])){
                        lands.add(land.createLand(islandField, location));   
                    }
                }
            }   
        }
    }
   
    private void simulateWeather()
    {
        int [][] weatherMap = map.GenerateMap();
        Random rand = Randomizer.getRandom();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                for(Weather weather : weatherTypes){
                    Location location = new Location(row, col);
                    if(weather.matchId(weatherMap[row][col])){
                        Land land = (Land) islandField.getObjectAt(location);
                        land.addWeather(weather.createWeather());
                    }
                }
            }   
        }
    }
    
    
    
    
    
    
    
    // public void updateLand()
    // {
        // for(Land land : lands){
            // if(land instanceof Ground){
                // List<Location> adjacent = islandField.adjacentLocations(land.getLocation());
                // Iterator<Location> it = adjacent.iterator();
                // while(it.hasNext()){
                    // Location where = it.next();
                    // Land adjacentLand = (Land) islandField.getObjectAt(where);
                    // if(adjacentLand != null && adjacentLand.getClass() == Water.class){
                        // int i = lands.indexOf(adjacentLand);
                        // lands.set(i,landTypes.get(2).createLand(islandField, where));
                    // }
                // } 
            // }
        // }
    // }

    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    private void delay(int millisec)
    {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }
}

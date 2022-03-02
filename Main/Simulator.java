package Main;
import java.util.Random;

import Animals.*;
import Lands.*;
import Plants.*;
import Utility.*;
import ActOfNature.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;
import java.util.HashMap;


/**
 * A simple predator-prey simulator, based on a rectangular animalField
 * containing animals and plants.
 * 
 * @author David J. Barnes , Michael Kölling,
 * Vaihbavkumar patel(k21076223) and mark emmanuel(k21009638).
 * @version (3) 2022.03.01.
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 100;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 100;
    //----------------------------------------//
    // List of animals in the animalField.
    private List<Animal> animals;
    // List of lands in the islandField.
    private List<Land> lands;
    // List of animalTypes in the animalField
    private List<Animal> animalTypes;
    // List of plantTypes in the islandField
    private List<Plant> plantTypes;
    // List of landTypes in the islandField.
    private List<Land> landTypes; 
    // List of WeatherTypes in the islandField.
    private List<Weather> weatherTypes;
    // List of DiseaseTypes in the animalField.
    private List<Disease> diseaseTypes;
    //----------------------------------------//
    // The food web of the ecosystem
    private Foodweb foodweb;
    //----------------------------------------//
    // The map object used to spawn in the type of land. 
    private Map map;
    // The current state of the animalField.
    private Field animalField;
    // The current state of the island animalField.
    private Field islandField;
    //----------------------------------------//
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;

    /**
     * This runs a long simulation when run.
     */
    public static void main(String [] args){
        Simulator simulator = new Simulator(100, 100);
        simulator.runLongSimulation();
    }

    /**
     * Construct a simulation animalField with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }

    /**
     * Create a simulation animalField with the given size.
     * @param depth Depth of the animalField. Must be greater than zero.
     * @param width Width of the animalField. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }

        // Initializing the instance animalFields
        animals = new ArrayList<>();
        animalTypes = new ArrayList<>();
        plantTypes = new ArrayList<>();
        lands = new ArrayList<>();
        landTypes = new ArrayList<>();
        weatherTypes = new ArrayList<>();
        diseaseTypes = new ArrayList<>();
        
        animalField = new Field(depth, width);
        islandField = new Field(depth, width);
        map = new Map(depth, width);

        // Create a view of the state of each location in the animalField.
        view = new SimulatorView(depth, width);
        
        // Sets the colour for each animal
        view.setColor(Pig.class, Color.PINK);
        view.setColor(Python.class, Color.YELLOW);
        view.setColor(Human.class, Color.black);
        view.setColor(Eagles.class, Color.MAGENTA);
        view.setColor(Fish.class, Color.ORANGE);
        view.setColor(Shark.class, Color.red);
        // Sets the colour for each land 
        view.setColor(Water.class, Color.BLUE); 
        view.setColor(Ground.class, new Color(150,75,0));
        view.setColor(ShallowWater.class, Color.CYAN);
        
        // Sets the colour for each plants 
        view.setColor(Grass.class, Color.GREEN);
        view.setColor(Seaweed.class, new Color(0, 0, 0, 0));
        
        // Sets the colour for each weather
        // Change the Alpha value to see the clouds and the rainy cloud.
        view.setColor(Raining.class, new Color(15, 15, 15, 40));
        view.setColor(Cloudy.class, new Color(128, 128, 128, 20));
        view.setColor(Sunny.class, new Color(0,0,0,0));
       
        // Putting all animal types in the animal types array.
        animalTypes.add(new Pig());
        animalTypes.add(new Python());
        animalTypes.add(new Human());
        animalTypes.add(new Eagles());
        animalTypes.add(new Fish());
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
        
        // Puting all disease types in the disease types array.
        diseaseTypes.add(new Pigomenalla());
        
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
        simulate(1500);
    }

    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.isViable(animalField); step++) {
            simulateOneStep();
            //delay(60);   // uncomment this to run more slowly
        }
    }

    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole animalField updating the state of each
     * Python and Pig.
     */
    public void simulateOneStep()
    {
        step++;
        int day = 5; // This sets the number of steps in the day

        // Provide space for newborn animals.
        List<Animal> newAnimals = new ArrayList<>();        
        // Let all the animals act.
        for(Iterator<Animal> it = animals.iterator(); it.hasNext(); ) {
            Animal animal = it.next();
            animal.act(newAnimals, foodweb, Time.isDay(day, step));
            if(! animal.isAlive()) {
                it.remove();
            }
        }
        
        // Let all the plants act.
        for(Iterator<Land> it = lands.iterator(); it.hasNext(); ) {
            Land land = it.next();
            if(land.getPlant() != null){
                land.getPlant().act(Time.isDay(5, step), islandField);
            }
        }
        
        // This simulates how the weather changes
        if(Time.isDay2(10,step)){
            simulateWeather();
        }
        
        // Add the newly born animals to the animal list.
        animals.addAll(newAnimals);
        view.showStatus(step ,step/(day*2) , animalField, islandField);
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        animals.clear();
        lands.clear();
        
        // These methods create the objects used the ecosystem
        drawLand();
        populatePlants();
        populateAnimals();
        simulateWeather();
        simulateDisease();

        // Show the starting state in the view.
        view.showStatus(step, 0, animalField, islandField);
    }

    /**
     * Randomly populateAnimals the animalField with animals.
     */
    private void populateAnimals()
    {
        Random rand = Randomizer.getRandom();
        Habitat animalLand = new Habitat();
        animalField.clear();
        for(int row = 0; row < animalField.getDepth(); row++) {
            for(int col = 0; col < animalField.getWidth(); col++) {
                //returns a random animal object.
                Animal animal = animalTypes.get(rand.nextInt(animalTypes.size()));
                List<Class> spawnLand = animalLand.getLandMap().get(animal.getClass());
                for(Class landClass : spawnLand){
                    if(rand.nextDouble() <= animal.getSpawn()) {
                        //spawns the animal at the location and then adds the animal to the animal list.            
                        Location location = new Location(row, col);
                        Land typeOfLand = (Land) islandField.getObjectAt(row, col);
                        if(typeOfLand.getClass() == landClass){
                            animals.add(animal.createAnimal(true, animal.setGender(), animalField, location, islandField));
                        }
                    }
                }
            }
        }
    }
    
    /**
     * 
     */
    private void simulateDisease()
    {
        Random rand = Randomizer.getRandom();
        for(int row = 0; row < animalField.getDepth(); row++) {
            for(int col = 0; col < animalField.getWidth(); col++) {
                //returns a random animal object.
                Object animal = animalField.getObjectAt(row ,col);
                Disease disease = diseaseTypes.get(rand.nextInt(diseaseTypes.size()));
                List<Class> animals = disease.getSusceptableAnimals();
                for(Class animalType : animals)
                if(animal != null && animal.getClass() == animalType){
                    Animal currentAnimal = (Animal) animal;
                    if(rand.nextDouble() <= disease.getSpawn()){
                        currentAnimal.addDisease(disease.createDisease());
                    }
                }
                
            }
        }
    }
    
    /**
     * Populates the islandField with plants.
     */
    private void populatePlants()
    {
        Random rand = Randomizer.getRandom();
        Habitat plantLand = new Habitat();
        animalField.clear();
        for(int row = 0; row < animalField.getDepth(); row++) {
            for(int col = 0; col < animalField.getWidth(); col++) {
                //returns a random animal object.
                List<Plant> tempPlantList = new ArrayList<>(plantTypes);
                boolean planted = false;
                while(!planted){
                    Plant plant = (Plant) tempPlantList.get(rand.nextInt(tempPlantList.size()));
                    List<Class> spawnLand = plantLand.getLandMap().get(plant.getClass());
                    for(Class landClass : spawnLand){
                            if(rand.nextDouble() <= plant.getSpawn()) {
                            //spawns the animal at the location and then adds the animal to the animal list.
                            Location location = new Location(row, col);
                            Land typeOfLand = (Land) islandField.getObjectAt(row, col);
                            if(typeOfLand.getClass() == landClass){
                                typeOfLand.addPlant(plant.createPlant(true, location));
                                planted = true;
                            }
                        }
                    }
                    tempPlantList.remove(plant);
                }
            }
        }
    }

    /**
     * Creates land objects in the islandField.
     */
    private void drawLand()
    {
        int [][] landmap = map.GenerateMap();
        Random rand = Randomizer.getRandom();
        islandField.clear();
        for(int row = 0; row < animalField.getDepth(); row++) {
            for(int col = 0; col < animalField.getWidth(); col++) {
                for(Land land : landTypes){
                    Location location = new Location(row, col);
                    if(land.matchId(landmap[row][col])){
                        lands.add(land.createLand(islandField, location));   
                    }
                }
            }   
        }
    }
   
    /**
     * This sets the weather for each land object.
     */
    private void simulateWeather()
    {
        int [][] weatherMap = map.GenerateMap();
        Random rand = Randomizer.getRandom();
        for(int row = 0; row < animalField.getDepth(); row++) {
            for(int col = 0; col < animalField.getWidth(); col++) {
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

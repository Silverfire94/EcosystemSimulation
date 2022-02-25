import java.util.Random;
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
    // List of animalTypes in the field
    private List<Animal> animalTypes;
    // List of landTypes in the islandField;
    private List<Land> landTypes; 
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
        lands = new ArrayList<>();
        landTypes = new ArrayList<>();

        field = new Field(depth, width);
        islandField = new Field(depth, width);
        map = new Map(depth, width);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.setColor(Pig.class, Color.PINK);
        view.setColor(Python.class, Color.YELLOW);
        view.setColor(Human.class, Color.white);
        view.setColor(Eagles.class, Color.MAGENTA);
        view.setColor(Fish.class, Color.CYAN);
        view.setColor(Shark.class, Color.red);

        view.setColor(Water.class, Color.BLUE); 
        view.setColor(Ground.class, Color.GREEN);
        view.setColor(ShallowWater.class, Color.CYAN);

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

        // Provide space for newborn animals.
        List<Animal> newAnimals = new ArrayList<>();        
        // Let all Pythons act.
        for(Iterator<Animal> it = animals.iterator(); it.hasNext(); ) {
            Animal animal = it.next();
            animal.act(newAnimals, foodweb);
            if(! animal.isAlive()) {
                it.remove();
            }
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
        drawLand();
        populate();

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
                Animal animal = animalTypes.get(rand.nextInt(6));
                List<Class> spawnLand = animalLand.getLandMap().get(animal.getClass());
                for(Class landClass : spawnLand){
                if(rand.nextDouble() <= animal.getSpawn()*4) {
                    //spawns the animal at the location and then adds the animal to the animal list.            
                    Location location = new Location(row, col);
                    Land typeOfLand = (Land) islandField.getObjectAt(row, col);
                    if(typeOfLand.getClass() == landClass){
                        animals.add(animal.createAnimal(true, animal.setGender(), field, location, typeOfLand));
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

        for(Land land : lands){
            if(land instanceof Ground){
                List<Location> adjacent = islandField.adjacentLocations(land.getLocation());
                Iterator<Location> it = adjacent.iterator();
                while(it.hasNext()){
                    Location where = it.next();
                    Object adjacentLand = islandField.getObjectAt(where);
                    if(adjacentLand != null && adjacentLand.getClass() == Water.class){
                        int i = lands.indexOf(land) + 2;
                        System.out.println(i);
                        lands.set(i,landTypes.get(2).createLand(islandField, where));
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

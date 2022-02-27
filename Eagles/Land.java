

/**
 * Abstract class Land - write a description of the class here
 *
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class Land
{
    // instance variables - replace the example below with your own
    private int x;
    private Location location; 
    private Field field;
    
    private static int ID = 0;
    public Land(Field field, Location location){
        this.field = field;
        setLocation(location);
    }
    
    public Land(){
        
    }
    
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Return the land's field.
     * @return the land's field.
     */
    protected Field getField(){
        return field;
    }
    
    /**
     * Return the land's location.
     * @return the land's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * This return the ID of the land.
     */
    abstract public boolean matchId(int givenId);
    
    abstract public Land createLand(Field field, Location location);
    
    abstract public Plant getPlant();
    
    abstract public void addPlant(Plant plant);
}



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
    public Land(Field field, Location location){
        this.field = field;
        setLocation(location);
    }

    
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
}

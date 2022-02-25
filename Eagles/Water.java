
/**
 * Write a description of class Water here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Water extends Land
{
    private static int ID = 1;
    /**
     * Constructor for objects of class Water
     */
    public Water(Field field, Location location)
    {
     super(field, location); 
    }
    
    public Water()
    {}
    
    public Land createLand(Field field, Location location){
        return new Water(field, location);
    }
    
    public boolean matchId(int givenId){
        return givenId == ID;
    }
    
    public void ChangeId(int newId){
        ID = newId;
    }
}

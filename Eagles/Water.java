
/**
 * Write a description of class Water here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Water extends Land
{
    private static int ID = 1;
    private Plant plant;
    /**
     * Constructor for objects of class Water
     */
    public Water(Field field, Location location, Plant plant)
    {
     super(field, location);
     this.plant = plant;
    }
    
    public Water()
    {}
    
    public Land createLand(Field field, Location location){
        return new Water(field, location, null);
    }
    
    public boolean matchId(int givenId){
        return givenId == ID;
    }
    
    public void ChangeId(int newId){
        ID = newId;
    }
    
    public Plant getPlant()
    {
        return plant;
    }
    
    public void addPlant(Plant plant)
    {
        this.plant = plant;
    }
}

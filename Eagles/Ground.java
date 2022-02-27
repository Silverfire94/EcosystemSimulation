
/**
 * Write a description of class Ground here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Ground extends Land
{
    // instance variables - replace the example below with your own
    private static int Id = 0;
    private Plant plant;

    /**
     * Constructor for objects of class Ground
     */
    public Ground(Field field, Location location, Plant plant)
    {
        super(field, location);
        this.plant = plant;
    }
    public Ground(){
        
    }
    
    public Land createLand(Field field, Location location){
        return new Ground(field, location, null);
    }
    
    public boolean matchId(int givenId){
        return givenId == Id;
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

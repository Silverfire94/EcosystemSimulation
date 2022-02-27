import java.util.List;
import java.util.Iterator;

public class ShallowWater extends Land
{
    private static int ID = 2;
    private Plant plant;
    
    public ShallowWater(Field field, Location location, Plant plant){
        super(field, location);
        this.plant = plant;
    }
    
    public ShallowWater()
    {
        
    }
    
    public Land createLand(Field field, Location location){
        return new ShallowWater(field, location, null);
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


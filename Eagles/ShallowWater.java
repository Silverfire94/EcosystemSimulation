import java.util.List;
import java.util.Iterator;

public class ShallowWater extends Land
{
    private static int ID = 2;
    
    public ShallowWater(Field field, Location location){
        super(field, location); 
    }
    
    public ShallowWater()
    {
        
    }
    
    public Land createLand(Field field, Location location){
        return new ShallowWater(field, location);
    }
    
    public boolean matchId(int givenId){
        return givenId == ID;
    }
    
    public void ChangeId(int newId){
        ID = newId;
    }
    
}


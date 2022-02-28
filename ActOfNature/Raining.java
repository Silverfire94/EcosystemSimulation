package ActOfNature;


/**
 * Write a description of class Raining here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Raining extends Weather
{
    private static int ID = 2;
    
    /**
     * Constructor for objects of class Raining
     */
    public Raining()
    {
        growthRate = 2; 
        viewDistance = 0;
    }
    
     public boolean matchId(int givenId){
        return givenId == ID;
    }
    
    public Weather createWeather(){
        return new Raining();
    }

}


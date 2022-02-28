package ActOfNature;


/**
 * Write a description of class Sunny here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Sunny extends Weather
{
    
    private static int ID = 0;
    /**
     * 
     */
    public Sunny(){
        growthRate = 3;
        viewDistance = 2; 
    }
    
    public boolean matchId(int givenId){
        return givenId == ID;
    }
    
    public Weather createWeather(){
        return new Sunny();
    }
}

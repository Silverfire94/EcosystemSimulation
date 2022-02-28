package ActOfNature;


/**
 * Write a description of class Cloudy here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Cloudy extends Weather
{
    // instance variables - replace the example below with your own
    private static int ID = 1;

    /**
     * Constructor for objects of class Cloudy
     */
    public Cloudy()
    {
        viewDistance = 1;
        growthRate = 1;
    }

     public boolean matchId(int givenId){
        return givenId == ID;
    }
    
    public Weather createWeather(){
        return new Cloudy();
    }
}

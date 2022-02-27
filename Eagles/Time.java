
/**
 * Write a description of class Time here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Time
{
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class Time
     */
    public Time()
    {
        
    }

    /**
     * 
     */
    public static boolean isDay(int dayLengthInSteps, int steps)
    {
        return (steps/dayLengthInSteps + 1) % 2 > 0;
    }
}

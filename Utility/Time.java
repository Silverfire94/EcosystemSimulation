package Utility;
/**
 * Used to define methods for describing Time.
 *
 * @author Mark Emmanuel and Vaibhavkumar Patel
 * @version 2.0
 */
public class Time
{
    /**
     * Constructor for objects of class Time
     */
    public Time()
    {
        
    }

    /**
     * @param dayLengthInSteps How long we want daylight hours to be.
     * @param steps The total number of steps that has occurered.
     * @Return True if it is dayTime.
     */
    public static boolean isDay(int dayLengthInSteps, int steps)
    {
        return (steps/dayLengthInSteps + 1) % 2 > 0;
    }
    
    /**
     * @param dayLengthInSteps How long we want a day to be.
     * @param steps The total number of steps that has occurered.
     * @Return True if a whole day has passed.
     */
    public static boolean isDay2(int dayLengthInStep , int steps){
        return (steps % dayLengthInStep == 0);
    }
}

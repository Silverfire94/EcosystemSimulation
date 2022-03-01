package Utility;
import java.util.Random;

/**
 * Write a description of class Map here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Map
{
    private int width, height;
    private PerlinNoise noise;
    private Random rand;
    private int [][] map;
    
    public Map(int width, int height)
    {
        this.width = width; 
        this.height = height;
        rand = new Random();
        noise = new PerlinNoise();
        
    }

    public int[][] GenerateMap(){
        float [][] noisemap = noise.generatePerlinNoise(width, height, 5, 0.4f, rand.nextInt(100));
        map = new int[width][height];
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                if(noisemap[x][y] < 0.5){
                    map[x][y] = 0;
                } else if ( 0.5 <= noisemap[x][y] && noisemap[x][y] < 0.6){
                    map[x][y] = 1;
                } else {
                    map[x][y] = 2;
                }
            }      
        }
        return map;
    }
    
}

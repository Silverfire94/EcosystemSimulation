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
    private Noise noise;
    private int [][] map;
    private int seed;
    private float s, h; 
    public Map(int width, int height)
    {
        this.width = width; 
        this.height = height;
        
        Random rand = new Random();
        noise = new Noise(rand.nextInt(10), 100.5f, 0.01f);
        GenerateMap();
    }

    private int[][] GenerateMap(){
        float [][] noisemap = noise.perlinNoiseMap(width, height);
        map = new int[width][height];
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                if(noisemap[x][y] < 0 ){
                    map[x][y] = 1;
                } else{
                    map[x][y] = 0;
                }
            }      
        }
        return map;
    }
    
    
    public int[][] getMap(){
        return map;
    }
}

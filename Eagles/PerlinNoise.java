import java.util.Random;
import java.util.Scanner;
 
/**
 * For detailed info and implementation see: <a href="http://devmag.org.za/2009/04/25/perlin-noise/">Perlin-Noise</a>
 */
public class PerlinNoise {
    
    public PerlinNoise(){
        
    }
    /**
     * @param width       width of noise array
     * @param height      height of noise array
     * @param octaveCount numbers of layers used for blending noise
     * @param persistence value of impact each layer get while blending
     * @param seed        used for randomizer
     * @return float array containing calculated "Perlin-Noise" values
     */
    static float[][] generatePerlinNoise(int width, int height, int octaveCount, float persistence, long seed) {
        final float[][] base = new float[width][height];
        final float[][] perlinNoise = new float[width][height];
        final float[][][] noiseLayers = new float[octaveCount][][];
 
        Random random = new Random(seed);
        //fill base array with random values as base for noise
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                base[x][y] = random.nextFloat();
            }
        }
 
        //calculate octaves with different roughness
        for (int octave = 0; octave < octaveCount; octave++) {
            noiseLayers[octave] = generatePerlinNoiseLayer(base, width, height, octave);
        }
 
        float amplitude = 1f;
        float totalAmplitude = 0f;
 
        //calculate perlin noise by blending each layer together with specific persistence
        for (int octave = octaveCount - 1; octave >= 0; octave--) {
            amplitude *= persistence;
            totalAmplitude += amplitude;
 
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    //adding each value of the noise layer to the noise
                    //by increasing amplitude the rougher noises will have more impact
                    perlinNoise[x][y] += noiseLayers[octave][x][y] * amplitude;
                }
            }
        }
 
        //normalize values so that they stay between 0..1
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                perlinNoise[x][y] /= totalAmplitude;
            }
        }
 
        return perlinNoise;
    }
 
    /**
     * @param base   base random float array
     * @param width  width of noise array
     * @param height height of noise array
     * @param octave current layer
     * @return float array containing calculated "Perlin-Noise-Layer" values
     */
    static float[][] generatePerlinNoiseLayer(float[][] base, int width, int height, int octave) {
        float[][] perlinNoiseLayer = new float[width][height];
 
        //calculate period (wavelength) for different shapes
        int period = 1 << octave; //2^k
        float frequency = 1f / period; // 1/2^k
 
        for (int x = 0; x < width; x++) {
            //calculates the horizontal sampling indices
            int x0 = (x / period) * period;
            int x1 = (x0 + period) % width;
            float horizintalBlend = (x - x0) * frequency;
 
            for (int y = 0; y < height; y++) {
                //calculates the vertical sampling indices
                int y0 = (y / period) * period;
                int y1 = (y0 + period) % height;
                float verticalBlend = (y - y0) * frequency;
 
                //blend top corners
                float top = interpolate(base[x0][y0], base[x1][y0], horizintalBlend);
 
                //blend bottom corners
                float bottom = interpolate(base[x0][y1], base[x1][y1], horizintalBlend);
 
                //blend top and bottom interpolation to get the final blend value for this cell
                perlinNoiseLayer[x][y] = interpolate(top, bottom, verticalBlend);
            }
        }
 
        return perlinNoiseLayer;
    }
 
    /**
     * @param a     value of point a
     * @param b     value of point b
     * @param alpha determine which value has more impact (closer to 0 -> a, closer to 1 -> b)
     * @return interpolated value
     */
    static float interpolate(float a, float b, float alpha) {
        return a * (1 - alpha) + alpha * b;
    }
 
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
 
        final int width;
        final int height;
        final int octaveCount;
        final float persistence;
        final long seed;
        final String charset;
        final float[][] perlinNoise;
 
        System.out.println("Width (int): ");
        width = in.nextInt();
 
        System.out.println("Height (int): ");
        height = in.nextInt();
 
        System.out.println("Octave count (int): ");
        octaveCount = in.nextInt();
 
        System.out.println("Persistence (float): ");
        persistence = in.nextFloat();
 
        System.out.println("Seed (long): ");
        seed = in.nextLong();
 
        System.out.println("Charset (String): ");
        charset = in.next();
 
 
        perlinNoise = generatePerlinNoise(width, height, octaveCount, persistence, seed);
        final char[] chars = charset.toCharArray();
        final int length = chars.length;
        final float step = 1f / length;
        //output based on charset
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                float value = step;
                float noiseValue = perlinNoise[x][y];
 
                for (char c : chars) {
                    if (noiseValue <= value) {
                        System.out.print(c);
                        break;
                    }
 
                    value += step;
                }
            }
 
            System.out.println();
        }
        in.close();
    }
}
// /**
 // * Perlin noise generation using the same method that 
 // * <a href="https://github.com/warmwaffles/Noise/blob/master/lib/Noise/Noise.rb">Noise</a>
 // * uses.
 // * 
 // * @author WarmWaffles (Matthew Johnston)
 // *
 // */
// public class Noise {
    // public int seed;
    // public float octaves;
    // public float persistence;
    // public float smoothing;
    
    // /**
     // * Constructs a new Noise object
     // * 
     // * @param seed - The random seed that you want
     // * @param octaves - The number of octaves you wish to go
     // * @param persistence - The amount of persistence you desire
     // */
    // public Noise(int seed, float octaves, float persistence) {
        // this.seed = seed;
        // this.octaves = octaves;
        // this.persistence = persistence;
        // this.smoothing = 235f;
    // }
    
    // /**
     // * Constructs a new Noise object
     // * 
     // * @param seed - The random seed that you want
     // * @param octaves - The number of octaves you wish to go
     // */
    // public Noise(int seed, float octaves) {
        // this(seed, octaves, 0.123f);
    // }
    
    // /**
     // * Constructs a new Noise object
     // * 
     // * @param seed - The random seed that you want
     // */
    // public Noise(int seed) {
        // this(seed, 4, .34f);
    // }
    
    // public float rawNoise(float x) {
        // int n = ((int)x << 13) ^ ((int)x);
        // return (1.0f - ((n * (n * n * 15731 * seed + 789221 * seed) + 1376312589 * seed) & 0x7fffffff) / 1073741824.0f);
    // }
    
    // public float rawNoise2D(float x, float y) {
        // return rawNoise(x + y * 57);
    // }
    
    // public float smoothNoise(float x) {
        // float left = rawNoise(x - 1.0f);
        // float right = rawNoise(x + 1.0f);
        // return (rawNoise(x) / 2.0f) + (left / smoothing) + (right / smoothing);
    // }
    
    // public float smoothNoise2D(float x, float y) {
        // float corners = rawNoise2D(x - 1, y - 1) + rawNoise2D(x - 1, y + 1) + rawNoise2D(x + 1, y - 1) + rawNoise2D(x + 1, y + 1);
        // float sides = rawNoise2D(x, y - 1) + rawNoise2D(x, y + 1) + rawNoise2D(x - 1, y) + rawNoise2D(x + 1, y);
        // float center = rawNoise2D(x,y);
        // return (center / 4.0f) + (sides / 8.0f) + (corners / 16.0f);
    // }
    
    // public float linearInterpolate(float a, float b, float x) {
        // return a * (1 - x) + b * x;
    // }
    
    // public float cosineInterpolate(float a, float b, float x) {
        // float f = (1.0f - (float)Math.cos(x * Math.PI))/2.0f;
        // return a * (1 - f) + b * f;
    // }
    
    // public float interpolateNoise(float x) {
        // return cosineInterpolate(smoothNoise((float)Math.floor(x)), smoothNoise((float)Math.floor(x) + 1.0f), (float)(x - Math.floor(x)));
    // }
    
    // public float interpolateNoise2D(float x, float y) {
        // float a = cosineInterpolate(
                // smoothNoise2D((float) Math.floor(x), (float) Math.floor(y)), 
                // smoothNoise2D((float) Math.floor(x) + 1.0f, (float) Math.floor(y)), 
                // x - (float) Math.floor(x));
        // float b = cosineInterpolate(
                // smoothNoise2D(
                        // (float) Math.floor(x), 
                        // (float) Math.floor(y) + 1), 
                // smoothNoise2D((float) Math.floor(x) + 1, (float) Math.floor(y) + 1), 
                // x - (float) Math.floor(x));
        // return cosineInterpolate(a, b, y - (float) Math.floor(y));
    // }
    
    // public float perlinNoise(float x) {
        // float total = 0, frequency, amplitude;
        // for(int i = 0; i < octaves; i++) {
            // frequency = (float)Math.pow(2.0, i);
            // amplitude = (float)Math.pow(persistence, i);
            // total += interpolateNoise(x * frequency) * amplitude;
        // }
        // return total;
    // }
    
    // public float perlinNoise2D(float x, float y) {
        // float total = 0, frequency, amplitude;
        // for(int i = 0; i < octaves; i++) {
            // frequency = (float)Math.pow(2.0, i);
            // amplitude = (float)Math.pow(persistence, i);
            // total += interpolateNoise2D(x * frequency, y * frequency) * amplitude;
        // }
        // return total;
    // }
    
    // /**
     // * Generates a 2D array of floats. The array is structured as follows,
     // * 
     // * array[y][x]. It is not structured like array[x][y]
     // * 
     // * @param w - The width of the array you want
     // * @param h - The height of the array you want
     // * @return an array that is (w * h) number of floats
     // */
    // public float[][] perlinNoiseMap(int w, int h) {
        // float[][] noise = new float[w][h];
            // for(int x = 0; x < w; x++) {
                // for(int y = 0; y < h; y++) {
                // noise[x][y] = perlinNoise2D(x,y);
            // }
        // }
        // return noise;
    // }
    

// }

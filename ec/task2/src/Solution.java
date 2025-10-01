import java.util.Arrays;

public class Solution {

    public Double[] x;
    public double fitness;

    public Solution(Solution second){
        fitness = second.fitness;
        x = Arrays.copyOf(second.x,x.length);
    }

    public Solution(Double[] newX, double fitness) {
        x = newX;
        this.fitness = fitness;
    }

    public Double[] getX() {
        return x;
    }

    public double getFitness() {
        return fitness;
    }
}

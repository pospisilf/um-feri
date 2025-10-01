import java.util.Arrays;

public class Solution {

    public Double[] x;
    public double fitness;

    public Solution(Solution second) {
        if (second.x != null) {
            this.x = Arrays.copyOf(second.x, second.x.length);
        } else {
            this.x = null;
        }
        this.fitness = second.fitness;
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

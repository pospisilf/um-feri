import java.util.Arrays;

public class Solution {

    private Double[] x;          // Current position
    private Double[] pBest;      // Personal best position
    private Double fitness;      // Current fitness
    private Double pBestFitness; // Fitness of the personal best position

    // Copy constructor
    public Solution(Solution second) {
        if (second.x != null) {
            this.x = Arrays.copyOf(second.x, second.x.length);
        } else {
            this.x = null;
        }
        if (second.pBest != null) {
            this.pBest = Arrays.copyOf(second.pBest, second.pBest.length);
        } else {
            this.pBest = null;
        }
        this.fitness = second.fitness;
        this.pBestFitness = second.pBestFitness;
    }

    // Constructor for a new solution
    public Solution(Double[] newX, Double fitness) {
        this.x = newX;
        this.fitness = fitness;
        this.pBest = Arrays.copyOf(newX, newX.length); // Initialize pBest with current position
        this.pBestFitness = fitness;                  // Initialize pBestFitness with current fitness
    }

    // Getter for current position
    public Double[] getX() {
        return x;
    }

    // Getter for current fitness
    public double getFitness() {
        return fitness;
    }

    // Getter for personal best position
    public Double[] getPBest() {
        return pBest;
    }

    // Getter for fitness of the personal best position
    public double getPBestFitness() {
        return pBestFitness;
    }

    // Method to update the current position and fitness
    public void updatePosition(Double[] newX, Double newFitness) {
        this.x = newX;
        this.fitness = newFitness;

        // Update pBest if the new fitness is better
        if (newFitness < pBestFitness) {
            this.pBest = Arrays.copyOf(newX, newX.length);
            this.pBestFitness = newFitness;
        }
    }
}

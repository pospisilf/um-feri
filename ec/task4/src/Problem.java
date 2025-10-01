import java.util.Random;

abstract public class Problem {

    String name;
    int numberOfDimension;
    Double[] upperLimit;
    Double[] lowerLimit;
    int maxFes;
    int currentFes;

    public Problem(String name, int numberOfDimension, int maxFes) {
        this.name = name;
        this.numberOfDimension = numberOfDimension;
        this.maxFes = maxFes;
        currentFes = 0;
        upperLimit = new Double[numberOfDimension];
        lowerLimit = new Double[numberOfDimension];
    }

    public abstract double fitnessFunction(Double[] x);

    public Solution generateRandomSolution() {
        Double[] newX = new Double[numberOfDimension];
        Random random = new Random();

        // Generate a random solution within the specified limits
        for (int i = 0; i < numberOfDimension; i++) {
            // Generate a random value between the lower and upper bounds
            newX[i] = lowerLimit[i] + (upperLimit[i] - lowerLimit[i]) * random.nextDouble();
        }

        // Calculate fitness based on the generated random solution
        double fitness = fitnessFunction(newX);
        return new Solution(newX, fitness);
    }

    public int getCurrentFes() {
        return currentFes;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfDimension() {
        return numberOfDimension;
    }

    public Double[] getUpperLimit() {
        return upperLimit;
    }

    public Double[] getLowerLimit() {
        return lowerLimit;
    }

    public int getMaxFes() {
        return maxFes;
    }
}

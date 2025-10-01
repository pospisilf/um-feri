public class Schwefel extends Problem {
    private int numberOfDimensions;

    public Schwefel(int numberOfDimensions, int maxFes) {
        super("Schwefel", numberOfDimensions, maxFes);
        this.numberOfDimensions = numberOfDimensions;

        upperLimit = new Double[numberOfDimensions];
        lowerLimit = new Double[numberOfDimensions];

        for (int i = 0; i < numberOfDimensions; i++) {
            lowerLimit[i] = -500.0;
            upperLimit[i] = 500.0;
        }
    }

    @Override
    public double fitnessFunction(Double[] x) {
        double fitness = 0.0;
        for (int i = 0; i < numberOfDimensions; i++) {
            fitness -= x[i] * Math.sin(Math.sqrt(Math.abs(x[i])));
        }
        currentFes++;
        return fitness;
    }
}

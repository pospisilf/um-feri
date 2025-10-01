public class Griewank extends Problem {

    public Griewank(int numberOfDimension, int maxFes) {
        super("Griewank", numberOfDimension, maxFes);

        upperLimit = new Double[numberOfDimension];
        lowerLimit = new Double[numberOfDimension];

        for (int i = 0; i < numberOfDimension; i++) {
            lowerLimit[i] = -600.0;
            upperLimit[i] = 600.0;
        }
    }

    @Override
    public double fitnessFunction(Double[] x) {
        double sum = 0.0;
        double product = 1.0;

        for (int i = 0; i < numberOfDimension; i++) {
            sum += (x[i] * x[i]) / 4000.0;
            product *= Math.cos(x[i] / Math.sqrt(i+1));
        }

        double fitness = sum - product + 1.0;

        currentFes++;
        return fitness;
    }
}

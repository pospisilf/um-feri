public class Rosenbrock extends Problem {

    public Rosenbrock(int numberOfDimension, int maxFes) {
        super("Rosenbrock", numberOfDimension, maxFes);

        upperLimit = new Double[numberOfDimension];
        lowerLimit = new Double[numberOfDimension];

        for (int i = 0; i < numberOfDimension; i++) {
            lowerLimit[i] = -5.0;
            upperLimit[i] = 10.0; 
        }
    }

    @Override
    public double fitnessFunction(Double[] x) {
        double sum = 0.0;

        for (int i = 0; i < numberOfDimension - 1; i++) {
            double xi = x[i];
            double xiPlus1 = x[i + 1];
            sum += 100 * Math.pow((xiPlus1 - xi * xi), 2) + Math.pow((xi - 1), 2);
        }

        currentFes++;
        return sum;
    }
}

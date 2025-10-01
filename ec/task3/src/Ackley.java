public class Ackley extends Problem {
    private static final double A = 20.0;
    private static final double B = 0.2;
    private static final double C = 2 * Math.PI;

    public Ackley(int numberOfDimension, int maxFes) {
        super("Ackley", numberOfDimension, maxFes);

        upperLimit = new Double[numberOfDimension];
        lowerLimit = new Double[numberOfDimension];

        for (int i = 0; i < numberOfDimension; i++) {
            lowerLimit[i] = -32.768;
            upperLimit[i] = 32.768;
        }
    }

    @Override
    public double fitnessFunction(Double[] x) {
        double sum1 = 0.0;
        double sum2 = 0.0;
        int n = numberOfDimension;

        for (int i = 0; i < n; i++) {
            sum1 += x[i] * x[i];
            sum2 += Math.cos(C * x[i]);
        }

        double term1 = -A * Math.exp(-B * Math.sqrt(sum1 / n));
        double term2 = -Math.exp(sum2 / n);
        
        double fitness = term1 + term2 + A + Math.exp(1);

        currentFes++;
        return fitness;
    }
}

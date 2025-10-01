public class Levy extends Problem {

    public Levy(int numberOfDimensions, int maxFes) {
        super("Levy", numberOfDimensions, maxFes);

        upperLimit = new Double[numberOfDimensions];
        lowerLimit = new Double[numberOfDimensions];

        for (int i = 0; i < numberOfDimensions; i++) {
            lowerLimit[i] = -10.0;
            upperLimit[i] = 10.0;
        }
    }

    @Override
    public double fitnessFunction(Double[] x) {
        int d = x.length;
        double[] w = new double[d];

        for (int i = 0; i < d; i++) {
            w[i] = 1 + (x[i] - 1) / 4;
        }

        double term1 = Math.pow(Math.sin(Math.PI * w[0]), 2);

        double term3 = Math.pow(w[d - 1] - 1, 2) * (1 + Math.pow(Math.sin(2 * Math.PI * w[d - 1]), 2));

        double sum = 0.0;
        for (int i = 0; i < d - 1; i++) {
            sum += Math.pow(w[i] - 1, 2) * (1 + 10 * Math.pow(Math.sin(Math.PI * w[i] + 1), 2));
        }

        double y = term1 + sum + term3;
        return y;
    }
}

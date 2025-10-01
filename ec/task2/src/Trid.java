public class Trid extends Problem {

    public Trid(int numberOfDimension, int maxFes) {
        super("Trid", numberOfDimension, maxFes);

        upperLimit = new Double[numberOfDimension];
        lowerLimit = new Double[numberOfDimension];

        for (int i = 0; i < numberOfDimension; i++) {
            lowerLimit[i] = -((double) (numberOfDimension*numberOfDimension));
            upperLimit[i] = (double) (numberOfDimension*numberOfDimension);
        }
    }

    @Override
    public double fitnessFunction(Double[] x) {
        int d = x.length;
        double sum1 = 0.0;
        double sum2 = 0.0;

        sum1 += Math.pow(x[0] - 1, 2);
        for (int i = 1; i < d; i++) {
            sum1 += Math.pow(x[i] - 1, 2);
        }

        for (int i = 1; i < d; i++) {
            sum2 += x[i] * x[i - 1];
        }

        return sum1 - sum2;
    }
}

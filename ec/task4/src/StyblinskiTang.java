public class StyblinskiTang extends Problem {

    public StyblinskiTang(int numberOfDimensions, int maxFes) {
        super("Styblinski-Tang", numberOfDimensions, maxFes);

        upperLimit = new Double[numberOfDimensions];
        lowerLimit = new Double[numberOfDimensions];

        for (int i = 0; i < numberOfDimensions; i++) {
            lowerLimit[i] = -5.0;
            upperLimit[i] = 5.0;
        }
    }

    @Override
    public double fitnessFunction(Double[] x) {
        double sum = 0.0;
        for (int i = 0; i < numberOfDimension; i++) {
            sum += Math.pow(x[i], 4) - 16 * Math.pow(x[i], 2) + 5 * x[i];
        }
        currentFes++;
        return sum / 2.0;
    }
}

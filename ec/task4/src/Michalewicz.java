public class Michalewicz extends Problem {

    public Michalewicz(int numberOfDimensions, int maxFes) {
        super("Michalewicz", numberOfDimensions, maxFes);

        upperLimit = new Double[numberOfDimensions];
        lowerLimit = new Double[numberOfDimensions];

        for (int i = 0; i < numberOfDimensions; i++) {
            lowerLimit[i] = 0.0;
            upperLimit[i] = Math.PI;
        }
    }

    @Override
    public double fitnessFunction(Double[] x) {
        double sum = 0.0;
        int d = x.length;

        for (int i = 0; i < d; i++) {
            sum += Math.sin(x[i]) * Math.pow(Math.sin((i + 1) * Math.pow(x[i], 2) / Math.PI), 20);
        }

        currentFes++;
        return -sum;
    }
}

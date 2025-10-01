public class Rastrigin extends Problem {

    public Rastrigin(int numberOfDimension, int maxFes) {
        super("Rastrigin", numberOfDimension, maxFes);
       
        upperLimit = new Double[numberOfDimension];
        lowerLimit = new Double[numberOfDimension];

        for (int i = 0; i < numberOfDimension; i++) {
            lowerLimit[i] = -5.12; 
            upperLimit[i] = 5.12;
        }
    }

    @Override
    public double fitnessFunction(Double[] x) {
        double sum = 0.0;

        for (int i = 0; i < numberOfDimension; i++) {
            sum += x[i] * x[i] - 10 * Math.cos(2 * Math.PI * x[i]);
        }

        double fitness = 10 * numberOfDimension + sum;

        currentFes++;
        return fitness;
    }
}

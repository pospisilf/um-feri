public class Sphere extends Problem {

    public Sphere(int numberOfDimension, int maxFes) {
        super("Sphere", numberOfDimension, maxFes);

        upperLimit = new Double[numberOfDimension];
        lowerLimit = new Double[numberOfDimension];

        for (int i = 0; i < numberOfDimension; i++) {
            if(i%2==0){ // 2,4,6...
                lowerLimit[i] = -10.0;
                upperLimit[i] = 10.0;
            } else {
                lowerLimit[i] = -100.0;
                upperLimit[i] = 100.0;
            }
        }
    }

    @Override
    public double fitnessFunction(Double[] x) {
        double fitness = 0.0;

        for (int i = 0; i < numberOfDimension; i++) {
            fitness += x[i] * x[i];
        }

        currentFes++;
        return fitness;
    }
}

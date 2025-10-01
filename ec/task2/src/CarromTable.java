public class CarromTable extends Problem {

    public CarromTable(int numberOfDimensions, int maxFes) {
        super("CarromTable", numberOfDimensions, maxFes);

        upperLimit = new Double[numberOfDimensions];
        lowerLimit = new Double[numberOfDimensions];

        for (int i = 0; i < numberOfDimensions; i++) {
            lowerLimit[i] = -10.0;
            upperLimit[i] = 10.0;
        }
    }

    @Override
    public double fitnessFunction(Double[] x) {
        double x1 = x[0];
        double x2 = x[1];

        double distance = Math.sqrt(x1 * x1 + x2 * x2);

        double absPart = Math.abs(1 - (distance / Math.PI));
        double cosSquaredX1 = Math.pow(Math.cos(x1), 2);
        double cosSquaredX2 = Math.pow(Math.cos(x2), 2);

        double result = -(1.0 / 30) * Math.exp(2 * absPart) * cosSquaredX1 * cosSquaredX2;

        currentFes++;
        return result;
    }
    
}

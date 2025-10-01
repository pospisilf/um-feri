public class Bukin extends Problem {

    public Bukin(int maxFes) {
        super("Bukin", 2, maxFes); // Only 2 dimmensions!
        
        lowerLimit = new Double[] {-15.0, -3.0};
        upperLimit = new Double[] {-5.0, 3.0};
    }

    @Override
    public double fitnessFunction(Double[] x) {

        double x1 = x[0];
        double x2 = x[1];
        
        double term1 = 100 * Math.sqrt(Math.abs(x2 - 0.01 * x1 * x1));
        double term2 = 0.01 * Math.abs(x1 + 10);
        
        currentFes++;
        return term1 + term2;
    }
}

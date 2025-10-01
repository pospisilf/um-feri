import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Arrays;

public class ImprovedGrayWolfOptimization extends Algorithm {

    private final int populationSize = 30; // number of wolves, N
    private final double aMax = 2.0; // maximum value of control parameter a
    private final double aMin = 0.2; // minimum value of control parameter a
    private final double k = 0.3; // scaling factor

    @Override
    public Solution execute(Problem p, boolean isDebug) {

        int numDimensions = p.getNumberOfDimension();
        int maxFes = p.getMaxFes(); // parameter t
        int fesCountd = p.getCurrentFes();

        // initialize random population with random solutions
        List<Solution> population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            Solution solution = p.generateRandomSolution();
            population.add(solution);
        }

        // calculate min and max values for each dimension
        double[] XdMin = calculateMin(population, numDimensions);
        double[] XdMax = calculateMax(population, numDimensions);

        // apply the equation to the population and update solutions
        for (int i = 0; i < populationSize; i++) {
            Solution solution = population.get(i);
            Double[] x = solution.getX();
            Double[] updatedX = new Double[numDimensions];

            // apply equalization to each dimension
            for (int d = 0; d < numDimensions; d++) {
                double Xi = x[d];
                updatedX[d] = calculateXdStar(XdMin[d], XdMax[d], Xi, k);
            }

            // create a new solution with updated dimensions and calculate its fitness
            Solution updatedSolution = new Solution(updatedX, p.fitnessFunction(updatedX));

            // update the solution in the population if the new one has better fitness
            if (updatedSolution.getFitness() < solution.getFitness()) {
                population.set(i, updatedSolution);
            }
        }

        // identify alpha, beta, and delta wolves (best, second best, third best)
        Solution alpha = null, beta = null, delta = null;
        for (Solution wolf : population) {
            if (alpha == null || wolf.getFitness() < alpha.getFitness()) {
                delta = beta;
                beta = alpha;
                alpha = wolf;
            } else if (beta == null || wolf.getFitness() < beta.getFitness()) {
                delta = beta;
                beta = wolf;
            } else if (delta == null || wolf.getFitness() < delta.getFitness()) {
                delta = wolf;
            }
        }

        while (p.getCurrentFes() < p.getMaxFes()) {
            for (int i = 0; i < populationSize; i++) {
                Solution wolf = population.get(i);

                double inertiaAlpha = calculateInertiaAlpha(fesCountd, maxFes);
                double inertiaBeta = calculateInertiaBeta(fesCountd, maxFes);
                double a = updateControlParameter(aMax, aMin, p.getCurrentFes(), p.getMaxFes(), k); // update a
                double C = updateC(); // update c

                Double[] updatedPosition = updatePosition(toPrimitive(wolf.getX()), toPrimitive(alpha.getX()),
                        toPrimitive(beta.getX()), toPrimitive(wolf.getPBest()), inertiaAlpha, inertiaBeta, a, C);

                updatedPosition = clampToBounds(updatedPosition, p.getLowerLimit(), p.getUpperLimit());

                double newFitness = p.fitnessFunction(updatedPosition);

                // check if the new position is better
                if (newFitness < wolf.getFitness()) {
                    wolf.updatePosition(updatedPosition, newFitness);
                }

                if (wolf.getFitness() < alpha.getFitness()) {
                    delta = beta;
                    beta = alpha;
                    alpha = wolf;
                } else if (wolf.getFitness() < beta.getFitness()) {
                    delta = beta;
                    beta = wolf;
                } else if (wolf.getFitness() < delta.getFitness()) {
                    delta = wolf;
                }

            }
        }

        return alpha;
    }

    public static double[] calculateMin(List<Solution> population, int numDimensions) {
        double[] minValues = new double[numDimensions];
        Arrays.fill(minValues, Double.MAX_VALUE);

        for (Solution solution : population) {
            for (int d = 0; d < numDimensions; d++) {
                double value = solution.getX()[d]; // use getX() to access the dimension value
                if (value < minValues[d]) {
                    minValues[d] = value;
                }
            }
        }
        return minValues;
    }

    public static double[] calculateMax(List<Solution> population, int numDimensions) {
        double[] maxValues = new double[numDimensions];
        Arrays.fill(maxValues, Double.MIN_VALUE);

        for (Solution solution : population) {
            for (int d = 0; d < numDimensions; d++) {
                double value = solution.getX()[d]; // use getX() to access the dimension value
                if (value > maxValues[d]) {
                    maxValues[d] = value;
                }
            }
        }
        return maxValues;
    }

    public static double calculateXdStar(double XdMin, double XdMax, double Xi, double k) {

        double term1 = (XdMin + XdMax) / 2;
        double term2 = (XdMin + XdMax) / (2 * k);
        double term3 = Xi / k;

        return term1 + term2 - term3;
    }

    public static Double[] updatePosition(double[] currentPosition, double[] preyPositionX1, double[] preyPositionX2,
            double[] pBest, double inertiaAlpha, double inertiaBeta, double a, double C) {
        Random random = new Random();
        int dimension = currentPosition.length;
        Double[] newPosition = new Double[dimension];

        // parameters
        double b1 = 0.5;
        double b2 = 0.5;
        double r3 = random.nextDouble();
        double r4 = random.nextDouble();

        // calculate weights
        double w1 = inertiaAlpha / (inertiaAlpha + inertiaBeta);
        double w2 = inertiaBeta / (inertiaAlpha + inertiaBeta);

        // update position
        for (int i = 0; i < dimension; i++) {
            // calculate distance using C
            double D1 = Math.abs(C * preyPositionX1[i] - currentPosition[i]);
            double D2 = Math.abs(C * preyPositionX2[i] - currentPosition[i]);

            // calculate A
            double A1 = 2 * a * r3 - a;
            double A2 = 2 * a * r4 - a;

            // update position using modified equation
            newPosition[i] = (w1 * preyPositionX1[i] - A1 * D1)
                    + (w2 * preyPositionX2[i] - A2 * D2)
                    + (b1 * r3 * (pBest[i] - currentPosition[i]))
                    + (b2 * r4 * (preyPositionX1[i] - currentPosition[i]));
        }

        return newPosition;
    }

    public static double calculateInertiaAlpha(int currentIteration, int maxIterations) {
        return 1.0 - ((double) currentIteration / maxIterations);
    }

    public static double calculateInertiaBeta(int currentIteration, int maxIterations) {
        return (double) currentIteration / maxIterations;
    }

    public static double[] toPrimitive(Double[] array) {
        if (array == null) {
            return null;
        }
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    public static double updateControlParameter(double aInitial, double aEnd, int t, int tMax, double k) {
        double exponent = -Math.pow(t, 2) / Math.pow(k * tMax, 2);
        return (aInitial - aEnd) * Math.exp(exponent) + aEnd;
    }

    public static double updateC() {
        Random random = new Random();
        double r = random.nextDouble(); // generate a random number between 0 and 1
        return 2 * r; // calculate C as 2 * r
    }

    public static Double[] clampToBounds(Double[] position, Double[] lowerBound, Double[] upperBound) {
        for (int i = 0; i < position.length; i++) {
            if (position[i] < lowerBound[i]) {
                position[i] = lowerBound[i];
            } else if (position[i] > upperBound[i]) {
                position[i] = upperBound[i];
            }
        }
        return position;
    }

}

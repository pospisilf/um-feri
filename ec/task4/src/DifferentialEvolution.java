import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Arrays;

public class DifferentialEvolution extends Algorithm {

    private final int populationSize = 20; // NP
    private final double crossoverRate = 0.5; // CR
    private final double mutationFactor = 0.6; // F

    @Override
    public Solution execute(Problem p, boolean isDebug) {
        int dimensions = p.getNumberOfDimension();
        int maxFes = p.getMaxFes();
        int fesCount = p.getCurrentFes();

        Random random = new Random();

        // initialize population with random solutions
        List<Solution> population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            Solution solution = p.generateRandomSolution();
            if (solution.getX() == null || solution.getX().length != dimensions) {
                throw new IllegalStateException("Generated solution has an invalid x array");
            }
            population.add(solution);
        }

        // find the best initial solution
        Solution bestSolution = population.get(0);
        for (Solution solution : population) {
            if (solution.getFitness() < bestSolution.getFitness()) {
                bestSolution = solution;
            }
        }

        // main DE loop
        while (fesCount < maxFes) {
            List<Solution> newPopulation = new ArrayList<>();

            for (int i = 0; i < populationSize; i++) {
                // select random solutions a, b, c
                int a, b, c;
                do {
                    a = random.nextInt(populationSize);
                } while (a == i);
                do {
                    b = random.nextInt(populationSize);
                } while (b == a || b == i);
                do {
                    c = random.nextInt(populationSize);
                } while (c == a || c == b || c == i);

                Solution sa = population.get(a);
                Solution sb = population.get(b);
                Solution sc = population.get(c);
                Solution xi = population.get(i);

                // create mutant vector v
                Double[] v = new Double[dimensions];
                for (int j = 0; j < dimensions; j++) {
                    v[j] = sa.getX()[j] + mutationFactor * (sb.getX()[j] - sc.getX()[j]);

                    // ensure v is within problem boundaries
                    v[j] = Math.max(p.getLowerLimit()[j], Math.min(p.getUpperLimit()[j], v[j]));
                }

                // create trial vector y
                Double[] y = new Double[dimensions];
                int R = random.nextInt(dimensions);
                for (int j = 0; j < dimensions; j++) {
                    if (random.nextDouble() < crossoverRate || j == R) {
                        y[j] = v[j];
                    } else {
                        y[j] = xi.getX()[j];
                    }

                    // ensure y is within problem boundaries
                    y[j] = Math.max(p.getLowerLimit()[j], Math.min(p.getUpperLimit()[j], y[j]));
                }

                // evaluate trial solution
                Solution trialSolution = new Solution(y, p.fitnessFunction(y));
                fesCount++;

                // selection
                if (trialSolution.getFitness() <= xi.getFitness()) {
                    newPopulation.add(trialSolution);
                    if (trialSolution.getFitness() < bestSolution.getFitness()) {
                        bestSolution = trialSolution;
                    }
                } else {
                    newPopulation.add(xi);
                }

                if (isDebug) {
                    System.out.println(fesCount + ": x=" + Arrays.toString(trialSolution.getX()) + " = " + trialSolution.getFitness());
                }
            }

            // Update population
            population = newPopulation;
        }

        // Return the best solution
        return bestSolution;
    }
}

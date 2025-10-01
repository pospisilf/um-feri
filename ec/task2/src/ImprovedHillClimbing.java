import java.util.*;

public class ImprovedHillClimbing extends Algorithm {

    private double stepSize;
    private final double minStepSize = 0.001;
    private final double maxStepSize = 1.0;
    private Set<String> visited;
    private int noImprovementCounter = 0;
    private final int maxNoImprovement = 2;
    private final double defaultStepDown = 0.9;
    private final double defaultStepUp = 1.1;

    public ImprovedHillClimbing(double initialStepSize) {
        this.stepSize = initialStepSize;
        this.visited = new HashSet<>();
    }

    @Override
    public Solution execute(Problem problem, boolean isDebug) {
        Solution bestSolution = problem.generateRandomSolution();
        Double[] bestX = Arrays.copyOf(bestSolution.getX(), bestSolution.getX().length);
        double bestFitness = bestSolution.getFitness();
        int fes = problem.getCurrentFes();

        if (isDebug) {
            System.out.println("Starting Improved Hill Climbing with stepSize: " + stepSize);
        }

        Double[] globalBestX = Arrays.copyOf(bestX, bestX.length);
        double globalBestFitness = bestFitness;
        visited.add(Arrays.toString(bestX));

        while (fes < problem.getMaxFes()) {
            Double[] bestNeighborX = null;
            double bestNeighborFitness = Double.POSITIVE_INFINITY;

            for (Double[] neighbor : generateNeighbors(bestX, problem)) {
                String neighborKey = Arrays.toString(neighbor);
                if (visited.contains(neighborKey)) {
                    continue;
                }

                double fitness = problem.fitnessFunction(neighbor);
                fes++;
                visited.add(neighborKey);

                if (fitness < bestNeighborFitness) {
                    bestNeighborFitness = fitness;
                    bestNeighborX = neighbor;
                }

                if (fitness < globalBestFitness) {
                    globalBestFitness = fitness;
                    System.arraycopy(neighbor, 0, globalBestX, 0, neighbor.length);
                    if (isDebug) {
                        System.out.println("New global best: Fitness = " + globalBestFitness + ", Variables = " + Arrays.toString(globalBestX));
                    }
                }

                if (fes >= problem.getMaxFes()) {
                    break;
                }
            }

            if (bestNeighborX == null || bestNeighborFitness >= bestFitness) {
                noImprovementCounter++;

                if (noImprovementCounter >= maxNoImprovement) {
                    adjustStepSize(false); //decrease step size
                    noImprovementCounter = 0;
                }

                do {
                    bestX = problem.generateRandomSolution().getX();
                } while (visited.contains(Arrays.toString(bestX)));

                bestFitness = problem.fitnessFunction(bestX);
                fes++;
                visited.add(Arrays.toString(bestX));

                if (bestFitness < globalBestFitness) {
                    globalBestFitness = bestFitness;
                    System.arraycopy(bestX, 0, globalBestX, 0, bestX.length);
                    if (isDebug) {
                        System.out.println("New global best after restart: Fitness = " + globalBestFitness);
                    }
                }

                if (isDebug) {
                    System.out.println("Restarted with random point: " + Arrays.toString(bestX));
                }

                continue;
            }

            // improvement found: move to the best neighbor
            bestX = bestNeighborX;
            bestFitness = bestNeighborFitness;

            if (isDebug) {
                System.out.println("Improved solution: Fitness = " + bestFitness + ", Variables = " + Arrays.toString(bestX));
            }

            noImprovementCounter = 0; // reset counter if improvement
            adjustStepSize(true); // increase step size to explore larger areas
        }

        return new Solution(globalBestX, globalBestFitness);
    }

    private List<Double[]> generateNeighbors(Double[] current, Problem problem) {
        List<Double[]> neighbors = new ArrayList<>();
        int dimensions = problem.getNumberOfDimension();

        for (int d = 0; d < dimensions; d++) {
            for (int direction = -1; direction <= 1; direction += 2) {
                Double[] neighbor = Arrays.copyOf(current, current.length);
                neighbor[d] += direction * stepSize;
                neighbor[d] = Math.min(Math.max(neighbor[d], problem.getLowerLimit()[d]), problem.getUpperLimit()[d]);
                neighbors.add(neighbor);
            }
        }

        return neighbors;
    }

    private void adjustStepSize(boolean increase) {
        if (increase) {
            stepSize = Math.min(stepSize * defaultStepUp, maxStepSize); // increase step size, cap at max
        } else {
            stepSize *= defaultStepDown; // gradually reduce step size
            if (stepSize < minStepSize) {
                stepSize = minStepSize; // reset to minimum if too small
            }
        }
    }
}

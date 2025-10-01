import java.util.*;

public class HillClimbing extends Algorithm {

    private double stepSize; // step size for neighbors
    private Set<String> visited; // history of evaluated positions

    public HillClimbing(double stepSize) {
        this.stepSize = stepSize;
        this.visited = new HashSet<>();
    }

    @Override
    public Solution execute(Problem problem, boolean isDebug) {
        // initialize the best solution with a random solution
        Solution bestSolution = problem.generateRandomSolution();
        Double[] bestX = Arrays.copyOf(bestSolution.getX(), bestSolution.getX().length);
        double bestFitness = bestSolution.getFitness();
        int fes = problem.getCurrentFes();

        // track the global best solution
        Double[] globalBestX = Arrays.copyOf(bestX, bestX.length);
        double globalBestFitness = bestFitness;

        // save the initial position to history
        visited.add(Arrays.toString(bestX));

        while (fes < problem.getMaxFes()) {
            // generate and evaluate all neighbors
            Double[] bestNeighborX = null;
            double bestNeighborFitness = Double.POSITIVE_INFINITY; // minimization, start with a high value

            for (Double[] neighbor : generateNeighbors(bestX, problem)) {
                String neighborKey = Arrays.toString(neighbor);

                // skip already evaluated neighbors
                if (visited.contains(neighborKey)) {
                    continue;
                }

                // evaluate the neighbor
                double fitness = problem.fitnessFunction(neighbor);
                fes++;
                visited.add(neighborKey);

                // update the best neighbor if it improves fitness
                if (fitness < bestNeighborFitness) {
                    bestNeighborFitness = fitness;
                    bestNeighborX = Arrays.copyOf(neighbor, neighbor.length);
                }

                // update the global best solution if it improves
                if (fitness < globalBestFitness) {
                    globalBestFitness = fitness;
                    System.arraycopy(neighbor, 0, globalBestX, 0, neighbor.length);

                    if (isDebug) {
                        System.out.println("New global best solution: Fitness = " + globalBestFitness + ", Variables = " + Arrays.toString(globalBestX));
                    }
                }

                // stop early if max evaluations are reached
                if (fes >= problem.getMaxFes()) {
                    break;
                }
            }

            // if no improvement, generate a new random starting point
            if (bestNeighborX == null || bestNeighborFitness >= bestFitness) {
                do {
                    bestX = problem.generateRandomSolution().getX();
                } while (visited.contains(Arrays.toString(bestX)));

                bestFitness = problem.fitnessFunction(bestX);
                fes++;
                visited.add(Arrays.toString(bestX));

                // update the global best solution if the new random solution is better
                if (bestFitness < globalBestFitness) {
                    globalBestFitness = bestFitness;
                    System.arraycopy(bestX, 0, globalBestX, 0, bestX.length);

                    if (isDebug) {
                        System.out.println("New global best solution after restart: Fitness = " + globalBestFitness + ", Variables = " + Arrays.toString(globalBestX));
                    }
                }

                if (isDebug) {
                    System.out.println("Restarted with new random point: " + Arrays.toString(bestX));
                }

                continue;
            }

            // update to the best neighbor
            bestFitness = bestNeighborFitness;
            bestX = bestNeighborX;

            if (isDebug) {
                System.out.println("Improved solution found: Fitness = " + bestFitness + ", Variables = " + Arrays.toString(bestX));
            }
        }

        // return the global best solution
        return new Solution(globalBestX, globalBestFitness);
    }

    /**
     * Generates neighbors for the given position.
     */
    private List<Double[]> generateNeighbors(Double[] current, Problem problem) {
        List<Double[]> neighbors = new ArrayList<>();
        int dimensions = problem.getNumberOfDimension();

        // generate neighbors in each dimension (cross and diagonal directions)
        for (int d = 0; d < dimensions; d++) {
            for (int direction = -1; direction <= 1; direction += 2) {
                Double[] neighbor = Arrays.copyOf(current, current.length);
                neighbor[d] += direction * stepSize;

                // ensure neighbors are within bounds
                neighbor[d] = Math.min(Math.max(neighbor[d], problem.getLowerLimit()[d]), problem.getUpperLimit()[d]);
                neighbors.add(neighbor);
            }
        }

        // optionally include diagonal neighbors
        // if (dimensions > 1) {
        //     for (int d1 = 0; d1 < dimensions; d1++) {
        //         for (int d2 = d1 + 1; d2 < dimensions; d2++) {
        //             for (int direction1 = -1; direction1 <= 1; direction1 += 2) {
        //                 for (int direction2 = -1; direction2 <= 1; direction2 += 2) {
        //                     Double[] neighbor = Arrays.copyOf(current, current.length);
        //                     neighbor[d1] += direction1 * stepSize;
        //                     neighbor[d2] += direction2 * stepSize;

        //                     // ensure neighbors are within bounds
        //                     neighbor[d1] = Math.min(Math.max(neighbor[d1], problem.getLowerLimit()[d1]), problem.getUpperLimit()[d1]);
        //                     neighbor[d2] = Math.min(Math.max(neighbor[d2], problem.getLowerLimit()[d2]), problem.getUpperLimit()[d2]);
        //                     neighbors.add(neighbor);
        //                 }
        //             }
        //         }
        //     }
        // }

        return neighbors;
    }
}

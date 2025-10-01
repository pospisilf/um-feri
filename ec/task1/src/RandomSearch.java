import java.util.Arrays;

public class RandomSearch extends Algorithm {
    @Override
    public Solution execute(Problem p, boolean isDebug) {
        Solution bestSolution = null;
        double bestFitness = Double.POSITIVE_INFINITY; // Initialize to a large value
        int fesCount = 0; // Function evaluation count

        // Continue generating random solutions until the max function evaluations are reached
        while (fesCount < p.getMaxFes()) {
            // Generate a random solution
            Solution newSolution = p.generateRandomSolution();
            fesCount++; // Increment the function evaluation count

            // Check if this solution is better than the best found so far
            if (newSolution.getFitness() < bestFitness) {
                bestFitness = newSolution.getFitness();
                bestSolution = newSolution; // Update the best solution
                if(isDebug){
                    System.out.println(fesCount + ": x=" + Arrays.toString(newSolution.getX()) + " = " + newSolution.getFitness());
                }    
            }
        }

        // Return the best found solution
        return bestSolution;
    }
}

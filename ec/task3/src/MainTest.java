import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;

public class MainTest {

    public static void main(String[] args) {
        boolean writeToFile = false; // don't write to file by default
        String surname = "Pospisil";
        int numberOfRuns = 50;

        for (String arg : args) {
            if (arg.equalsIgnoreCase("writeToFile")) {
                writeToFile = true;
                System.out.println("Write to file enabled.");
            }
        }

        int[] dimensions = {10, 20, 30};
        String[] problemNames = {
            "Ackley", 
            "Griewank", 
            "Levy", 
            "Michalewicz",
            "Rastrigin", 
            "Rosenbrock", 
            "Schwefel", 
            "Sphere", 
            "StyblinskiTang",
            "Trid"
        };

        for (int dim : dimensions) {
            for (String problemName : problemNames) {
                StringBuilder results = new StringBuilder();

                for (int i = 0; i < numberOfRuns; i++) {

                    Problem problem = createProblem(problemName, dim);
                    if (problem == null) {
                        System.out.println("Problem " + problemName + " not recognized.");
                        continue;
                    }

                    Algorithm de = new DifferentialEvolution();
                    Solution bestSolution = de.execute(problem, false);

                    BigDecimal fitness = BigDecimal.valueOf(bestSolution.getFitness());
                    results.append(fitness.toPlainString()).append("\n");
                    
                    // console output
                    String runNumber = String.format("%02d", i + 1); // format the run number with leading zero
                    System.out.println("Run " + runNumber + " - Problem: " + problemName + " (dim=" + dim + ")" +
                            " Best Fitness: " + fitness.toPlainString());
                }

                // write results to file (if enabled)
                if (writeToFile) {
                    String fileName = "DE-" + surname + "_" + problemName + "D" + dim + ".txt";
                    try (FileWriter writer = new FileWriter("results/" + fileName)) {
                        writer.write(results.toString());
                        System.out.println("Results written to file: " + fileName);
                    } catch (IOException e) {
                        System.err.println("Failed to write to file: " + fileName);
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static Problem createProblem(String problemName, int dimensions) {
        int maxFes = 3000 * dimensions;
        switch (problemName) {
            case "Ackley":
                return new Ackley(dimensions, maxFes);
            case "Griewank":
                return new Griewank(dimensions, maxFes);
            case "Levy":
                return new Levy(dimensions, maxFes);
            case "Michalewicz":
                return new Michalewicz(dimensions, maxFes);
            case "Rastrigin":
                return new Rastrigin(dimensions, maxFes);
            case "Rosenbrock":
                return new Rosenbrock(dimensions, maxFes);
            case "Schwefel":
                return new Schwefel(dimensions, maxFes);
            case "Sphere":
                return new Sphere(dimensions, maxFes);
            case "StyblinskiTang":
                return new StyblinskiTang(dimensions, maxFes);
            case "Trid":
                return new Trid(dimensions, maxFes);
            default:
                return null;
        }
    }
}

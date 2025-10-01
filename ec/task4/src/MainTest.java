import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;

public class MainTest {

    public static void main(String[] args) {
        boolean writeToFile = false; // don't write to file by default
        boolean compare = false;
        int numberOfRuns = 50;

        for (String arg : args) {
            if (arg.equalsIgnoreCase("writeToFile")) {
                writeToFile = true;
                System.out.println("Write to file enabled.");
            } else if (arg.equalsIgnoreCase("compare")) {
                compare = true;
            }
        }

        if (compare) {
            System.out.println("Compare mode activated:");
            compare();
        } else {
            System.out.println("Running IGWO only:");
            runIGWO(numberOfRuns, writeToFile);
        }
    }

    private static void compare() {
        int dim_10 = 10;
        double trid_expected10 = -dim_10 * (dim_10 + 4) * (dim_10 - 1) / 6;
        double schwefel_expected = (-418.982887272433799807913601398 * dim_10);
    
        Object[][] parameters_10_dimensions = {
            // name, problem, expected value
            { "Sphere", 0.0 },
            { "Ackley", 0.0 },
            { "Griewank", 0.0 },
            { "Rastrigin", 0.0 },
            { "Schwefel", schwefel_expected },
            // { "Rosenbrock", 0.0 }, // not sure if good for compare -> getting very different results than expected
            { "Trid", trid_expected10 }, 
            { "StyblinskiTang", -39.16616570377142 * dim_10 },
            { "Levy", 0.0 },
            { "Michalewicz", -9.6601517 },
        };
    
        int deBetterCount = 0;
        int igwoBetterCount = 0;
        int equalCount = 0;
    
        for (Object[] params : parameters_10_dimensions) {
            String problemName = (String) params[0];
            Double expected = (Double) params[1];
    
            // DE
            Problem deProblem = createProblem(problemName, dim_10);
            Algorithm de = new DifferentialEvolution();
            Solution deSolution = de.execute(deProblem, false);
            BigDecimal deFitness = BigDecimal.valueOf(deSolution.getFitness());
    
            // IGWO
            Problem igwoProblem = createProblem(problemName, dim_10);
            Algorithm igwo = new ImprovedGrayWolfOptimization();
            Solution igwoSolution = igwo.execute(igwoProblem, false);
            BigDecimal igwoFitness = BigDecimal.valueOf(igwoSolution.getFitness());
    
            // Calculate differences
            double deDifference = Math.abs(deFitness.doubleValue() - expected);
            double igwoDifference = Math.abs(igwoFitness.doubleValue() - expected);
    
            // Determine which is better
            String betterAlgorithm;
            if (deDifference < igwoDifference) {
                betterAlgorithm = "DE";
                deBetterCount++;
            } else if (igwoDifference < deDifference) {
                betterAlgorithm = "IGWO";
                igwoBetterCount++;
            } else {
                betterAlgorithm = "Equal";
                equalCount++;
            }
    
            System.out.println(problemName);
            System.out.println("EXP:  " + expected);
            System.out.println("DE:   " + deFitness);
            System.out.println("IGWO: " + igwoFitness);
            System.out.println("DE Difference:   " + deDifference);
            System.out.println("IGWO Difference: " + igwoDifference);
            System.out.println("Better: " + betterAlgorithm);
            System.out.println("\n");
        }
    
        System.out.println("Summary:");
        System.out.println("DE performed better " + deBetterCount + " times.");
        System.out.println("IGWO performed better " + igwoBetterCount + " times.");
        System.out.println("Both performed equally " + equalCount + " times.");
    }
    

    private static void runIGWO(int numberOfRuns, boolean writeToFile) {
        String surname = "Pospisil";
        int[] dimensions = {10, 20, 30};
        // int[] dimensions = { 2 };
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
                double totalFitness = 0.0; // accumulator for fitness values

                for (int i = 0; i < numberOfRuns; i++) {

                    Problem problem = createProblem(problemName, dim);
                    if (problem == null) {
                        System.out.println("Problem " + problemName + " not recognized.");
                        continue;
                    }

                    Algorithm igwo = new ImprovedGrayWolfOptimization();
                    Solution bestSolution = igwo.execute(problem, false);

                    BigDecimal fitness = BigDecimal.valueOf(bestSolution.getFitness());
                    totalFitness += fitness.doubleValue();
                    results.append(fitness.toPlainString()).append("\n");

                    // console output
                    String runNumber = String.format("%02d", i + 1); // format the run number with leading zero
                    System.out.println("Run " + runNumber + " - Problem: " + problemName + " (dim=" + dim + ")" +
                            " Best Fitness: " + fitness.toPlainString());
                }

                // calculate and display average fitness -> not writing to files!
                double averageFitness = totalFitness / numberOfRuns;
                System.out.println(
                        "Problem: " + problemName + " (dim=" + dim + ") - Average Fitness: " + averageFitness);

                // write results to file (if enabled)
                if (writeToFile) {
                    String fileName = "IGWO-" + surname + "_" + problemName + "D" + dim + ".txt";
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

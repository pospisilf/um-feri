import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class MainTest {

        private static final int dim_2 = 2;
        private static final int dim_5 = 5;
        private static final int dim_10 = 10;
        private static final int maxFestDefault = 3000;
        private static final double defaultStepSize = 0.1;
        private static final int runs = 100;
        public static boolean debugMode = false;
        public static boolean runAll = false;
        public static boolean runOne = false;
        public static boolean compare = false;

        public static void main(String[] args) {

                for (String arg : args) {
                        if (arg.equalsIgnoreCase("isDebug")) {
                                debugMode = true;
                                System.out.println("Debug mode enabled");
                        } else if (arg.equalsIgnoreCase("runAll")) {
                                runAll = true;
                        }
                        else if (arg.equalsIgnoreCase("runOne")) {
                                runOne = true;
                        }
                        else if (arg.equalsIgnoreCase("compare")) {
                                compare = true;
                        }
                }

                if(runAll && runOne) {
                        throw new IllegalArgumentException("Can't be executed with runAll and runOne argument together.");
                }

                if (runAll) {
                        Object[][] problems = {
                                        { "Sphere", Sphere.class },
                                        { "Ackley", Ackley.class },
                                        { "Griewank", Griewank.class },
                                        { "Rastrigin", Rastrigin.class },
                                        { "Schwefel26", Schwefel.class },
                                        { "Rosenbrock", Rosenbrock.class },
                                        { "Bukin", Bukin.class },
                                        { "CarromTable", CarromTable.class },
                                        { "StyblinskiTang", StyblinskiTang.class },
                                        { "Michalewicz", Michalewicz.class }
                        };

                        runProblems(problems, dim_2);
                        runProblems(problems, dim_5);
                        runProblems(problems, dim_10);
                }

                if (runOne) {
                        Object[][] problems = {
                                { "Sphere", Sphere.class },
                        };
                        compare(problems, dim_2);
                }

                if (compare) {
                        Object[][] problems = {
                                { "Sphere", Sphere.class },
                                { "Ackley", Ackley.class },
                                { "Griewank", Griewank.class },
                                { "Rastrigin", Rastrigin.class },
                                { "Schwefel26", Schwefel.class },
                                { "Rosenbrock", Rosenbrock.class },
                                { "Bukin", Bukin.class },
                                { "CarromTable", CarromTable.class },
                                { "StyblinskiTang", StyblinskiTang.class },
                                { "Michalewicz", Michalewicz.class }
                        };

                        compare(problems, dim_2);
                        compare(problems, dim_5);
                        compare(problems, dim_10);
                }

        }

        private static void compare(Object[][] problems, int dimensions){
                for (Object[] problem : problems) {

                        String name = (String) problem[0];
                        // Bukin is defined only for 2 dimensions
                        if (name == "Bukin" && dimensions != 2) {
                                continue;
                        }
                        int maxFes = maxFestDefault * dimensions;

                        // Normal
                        Class<?> problemClassNormal = (Class<?>) problem[1];
                        List<Double> resultsNormal = runProblem(name, problemClassNormal, dimensions, maxFes);
                        double bestSolutionNormal = StatisticsUtility.findMin(resultsNormal);
                        double averageNormal = StatisticsUtility.calculateAverage(resultsNormal);
                        double standardDeviationNormal = StatisticsUtility.calculateStandardDeviation(resultsNormal, averageNormal);


                        // Improved
                        Class<?> problemClassImproved = (Class<?>) problem[1];
                        List<Double> resultsImproved = runProblemImproved(name, problemClassImproved, dimensions, maxFes);
                        double bestSolutionImproved = StatisticsUtility.findMin(resultsImproved);
                        double averageImproved = StatisticsUtility.calculateAverage(resultsImproved);
                        double standardDeviationImproved = StatisticsUtility.calculateStandardDeviation(resultsImproved, averageImproved);


                        System.out.println("Problem: " + name + ", Dimensions: " + dimensions);
                        System.out.println("Original - Min: " + bestSolutionNormal + ", Average: " + averageNormal + ", Std: " + standardDeviationNormal);
                        System.out.println("Improved - Min: " + bestSolutionImproved + ", Average: " + averageImproved + ", Std: " + standardDeviationImproved);
                }
        }

        private static void runProblems(Object[][] problems, int dimensions) {
                for (Object[] problem : problems) {
                        String name = (String) problem[0];
                        // Bukin is defined only for 2 dimensions
                        if (name == "Bukin" && dimensions != 2) {
                                continue;
                        }
                        Class<?> problemClass = (Class<?>) problem[1];
                        int maxFes = maxFestDefault * dimensions;

                        List<Double> results = runProblem(name, problemClass, dimensions, maxFes);

                        double bestSolution = StatisticsUtility.findMin(results);
                        double average = StatisticsUtility.calculateAverage(results);
                        double standardDeviation = StatisticsUtility.calculateStandardDeviation(results, average);

                        System.out.println("Problem: " + name + ", " + "Dimensions: " + dimensions + ", " +
                                        "Min: " + bestSolution + ", " + "Average: " + average + ", " + "Std: "
                                        + standardDeviation);
                }
        }

        private static List<Double> runProblem(String name, Class<?> problemClass, int dimensions, int maxFes) {
                List<Double> results = new ArrayList<>();

                for (int i = 0; i < runs; i++) {
                        try {
                                Problem problem = createProblemInstance(problemClass, dimensions, maxFes);
                                Solution best = runProblemHillClimbing(name, problem, defaultStepSize);
                                results.add(best.getFitness());
                        } catch (Exception e) {
                                e.printStackTrace();
                                throw new RuntimeException("Failed to instantiate problem: " + name);
                        }
                }

                return results;
        }

        private static List<Double> runProblemImproved(String name, Class<?> problemClass, int dimensions, int maxFes) {
                List<Double> results = new ArrayList<>();

                for (int i = 0; i < runs; i++) {
                        try {
                                Problem problem = createProblemInstance(problemClass, dimensions, maxFes);
                                Solution best = runProblemImprovedHillClimbing(name, problem, defaultStepSize);
                                results.add(best.getFitness());
                        } catch (Exception e) {
                                e.printStackTrace();
                                throw new RuntimeException("Failed to instantiate problem: " + name);
                        }
                }
                return results;
        }

        private static Problem createProblemInstance(Class<?> problemClass, int dimensions, int maxFes)
                        throws Exception {
                Constructor<?> constructor = problemClass.getConstructor(int.class, int.class);
                return (Problem) constructor.newInstance(dimensions, maxFes);
        }

        public static Solution runProblemHillClimbing(String problemName, Problem problem, double stepSize) {
                Algorithm hillClimbing = new HillClimbing(stepSize);
                return hillClimbing.execute(problem, debugMode);
        }

        public static Solution runProblemImprovedHillClimbing(String problemName, Problem problem, double stepSize) {
                Algorithm hillClimbing = new ImprovedHillClimbing(stepSize);
                return hillClimbing.execute(problem, debugMode);
        }
}

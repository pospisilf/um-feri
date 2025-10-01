import java.util.Arrays;

public class MainTest {

    private static final int dim_2 = 2;
    private static final int dim_5 = 5;
    private static final int dim_10 = 10;
    private static final int maxFestDefault = 3000;
    private static final double acceptBorder = 0.0000001;
    public static boolean debugMode = false;
    public static boolean testMode = false;

    public static void main(String[] args) {

        // Check for the debug flag in the arguments
        for (String arg : args) {
            if (arg.equalsIgnoreCase("isDebug")) {
                debugMode = true;
                System.out.println("Debug mode enabled");
            } else if (arg.equalsIgnoreCase("test")) {
                testMode = true;
            }
        }

        double trid_expected2 = dim_2*(dim_2+4)*(dim_2-1)/6;

        if (testMode) {
            // Two dimensions
            System.out.println("Unit tests - 2 dimensions");
            Object[][] parameters_2_dimensions = {
                    // name, problem, optimum values, expected value
                    { "Sphere", new Sphere(dim_2, 1), new Double[] { 0.0, 0.0 }, 0.0 },
                    { "Ackley", new Ackley(dim_2, 1), new Double[] { 0.0, 0.0 }, 0.0 },
                    { "Griewank", new Griewank(dim_2, 1), new Double[] { 0.0, 0.0 }, 0.0 },
                    { "Rastrigin", new Rastrigin(dim_2, 1), new Double[] { 0.0, 0.0 }, 0.0 },
                    { "Schwefel26", new Schwefel(dim_2, 1), new Double[] { 420.9687, 420.9687 },
                            -418.982887272433799807913601398 * dim_2 },
                    { "Rosenbrock", new Rosenbrock(dim_2, 1), new Double[] { 1.0, 1.0 }, 0.0 },
                    { "Trid", new Trid(dim_2, 1), new Double[] { 0.0, 2.0 }, trid_expected2 }, // bad numbers?
                    { "Bukin", new Bukin(1), new Double[] { -10.0, 1.0 }, 0.0 }, // only 2 dims!
                    { "Carrom Table", new CarromTable(dim_2, 1), new Double[] { 9.64128758, -9.64128758 },
                            -24.15681551650653 },
                    { "Styblinski-Tang", new StyblinskiTang(dim_2, 1),
                            new Double[] { -2.90353401818596, -2.90353401818596 }, -39.16616570377142 * dim_2 },
                    { "Levy", new Levy(dim_2, 1), new Double[] { 1.0, 1.0 }, 0.0 },
                    { "Michalewicz", new Michalewicz(dim_2, 1), new Double[] { 2.20290552014618, 1.57079632677565 },
                            -1.80130341009855321 },
            };
            int passed2 = 0;
            for (Object[] params : parameters_2_dimensions) {
                String name = (String) params[0];
                Problem p = (Problem) params[1];
                Double[] optimum = (Double[]) params[2];
                Double expected = (Double) params[3];
                if (unitTest(name, p, optimum, expected, acceptBorder) == true) {
                    passed2++;
                }
                ;
            }
            System.out.println("Passed " + passed2 + " tests out of " + parameters_2_dimensions.length + "\n");

            // 5 dimensions
            double trid_expected5 = -dim_5*(dim_5+4)*(dim_5-1)/6;
            System.out.println("Unit tests - 5 dimensions");
            Object[][] parameters_5_dimensions = {
                    // name, problem, optimum values, expected value
                    { "Sphere", new Sphere(dim_5, 1), new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 }, 0.0 },
                    { "Ackley", new Ackley(dim_5, 1), new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 }, 0.0 },
                    { "Griewank", new Griewank(dim_5, 1), new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 }, 0.0 },
                    { "Rastrigin", new Rastrigin(dim_5, 1), new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 }, 0.0 },
                    { "Schwefel26", new Schwefel(dim_5, 1),
                            new Double[] { 420.9687, 420.9687, 420.9687, 420.9687, 420.9687 },
                            -418.982887272433799807913601398 * dim_5 },
                    { "Rosenbrock", new Rosenbrock(dim_5, 1), new Double[] { 1.0, 1.0, 1.0, 1.0, 1.0 }, 0.0 },
                    { "Trid", new Trid(dim_5, 1), new Double[] { 0.0, 5.0, 9.0, 8.0, 5.0 }, trid_expected5 }, // bad numbers?
                    { "Carrom Table", new CarromTable(dim_5, 1),
                            new Double[] { 9.64128758, 9.64128758, 9.64128758, 9.64128758, 9.64128758 },
                            -24.15681551650653 },
                    { "Styblinski-Tang", new StyblinskiTang(dim_5, 1),
                            new Double[] { -2.90353401818596, -2.90353401818596, -2.90353401818596, -2.90353401818596,
                                    -2.90353401818596 },
                            -39.16616570377142 * dim_5 },
                    { "Levy", new Levy(dim_5, 1), new Double[] { 1.0, 1.0, 1.0, 1.0, 1.0 }, 0.0 },
                    { "Michalewicz", new Michalewicz(dim_5, 1),
                            new Double[] { 2.202906, 1.570796, 1.284992, 1.923058, 1.720470 }, -4.6876582 },
            };
            int passed5 = 0;
            for (Object[] params : parameters_5_dimensions) {
                String name = (String) params[0];
                Problem p = (Problem) params[1];
                Double[] optimum = (Double[]) params[2];
                Double expected = (Double) params[3];
                if (unitTest(name, p, optimum, expected, acceptBorder) == true) {
                    passed5++;
                }
                ;
            }
            System.out.println("Passed " + passed5 + " tests out of " + parameters_5_dimensions.length + "\n");

            double trid_expected10 = -dim_10*(dim_10+4)*(dim_10-1)/6;
            // 10 dimensions
            System.out.println("Unit tests - 10 dimensions");
            Object[][] parameters_10_dimensions = {
                    // name, problem, optimum values, expected value
                    { "Sphere", new Sphere(dim_10, 1),
                            new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, 0.0 },
                    { "Ackley", new Ackley(dim_10, 1),
                            new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, 0.0 },
                    { "Griewank", new Griewank(dim_10, 1),
                            new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, 0.0 },
                    { "Rastrigin", new Rastrigin(dim_10, 1),
                            new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, 0.0 },
                    { "Schwefel26", new Schwefel(dim_10, 1),
                            new Double[] { 420.9687, 420.9687, 420.9687, 420.9687, 420.9687, 420.9687, 420.9687,
                                    420.9687, 420.9687, 420.9687 },
                            -418.982887272433799807913601398 * dim_10 },
                    { "Rosenbrock", new Rosenbrock(dim_10, 1),
                            new Double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0 }, 0.0 },
                    { "Trid", new Trid(dim_10, 1), new Double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0 },
                    trid_expected10 }, // bad numbers?
                    { "Carrom Table", new CarromTable(dim_10, 1),
                            new Double[] { 9.64128758, 9.64128758, 9.64128758, 9.64128758, 9.64128758, 9.64128758,
                                    9.64128758, 9.64128758, 9.64128758, 9.64128758 },
                            -24.15681551650653 },
                    { "Styblinski-Tang", new StyblinskiTang(dim_10, 1),
                            new Double[] { -2.90353401818596, -2.90353401818596, -2.90353401818596, -2.90353401818596,
                                    -2.90353401818596, -2.90353401818596, -2.90353401818596, -2.90353401818596,
                                    -2.90353401818596, -2.90353401818596 },
                            -39.16616570377142 * dim_10 },
                    { "Levy", new Levy(dim_10, 1), new Double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0 },
                            0.0 },
                    { "Michalewicz", new Michalewicz(dim_10, 1),
                            new Double[] { 2.202906, 1.570796, 1.284992, 1.923058, 1.720470, 1.570796, 1.454414,
                                    1.756087, 1.655717, 1.570796 },
                            -9.6601517 },
            };
            int passed10 = 0;
            for (Object[] params : parameters_10_dimensions) {
                String name = (String) params[0];
                Problem p = (Problem) params[1];
                Double[] optimum = (Double[]) params[2];
                Double expected = (Double) params[3];
                if (unitTest(name, p, optimum, expected, acceptBorder) == true) {
                    passed10++;
                }
                ;
            }
            System.out.println("Passed " + passed10 + " tests out of " + parameters_10_dimensions.length + "\n");

        } else {
            System.out.println("Run - 2 dimensions");
            runProblem("Sphere", new Sphere(dim_2, maxFestDefault * dim_2));
            runProblem("Ackley", new Ackley(dim_2, maxFestDefault * dim_2));
            runProblem("Griewank", new Griewank(dim_2, maxFestDefault * dim_2));
            runProblem("Rastrigin", new Rastrigin(dim_2, maxFestDefault * dim_2));
            runProblem("Schwefel26", new Schwefel(dim_2, maxFestDefault * dim_2));
            runProblem("Rosenbrock", new Rosenbrock(dim_2, maxFestDefault * dim_2));
            runProblem("Trid", new Trid(dim_2, maxFestDefault * dim_2));
            runProblem("Bukin", new Bukin(maxFestDefault * dim_2)); // only 2 dimensions!
            runProblem("Carrom Table", new CarromTable(dim_2, maxFestDefault * dim_2));
            runProblem("Styblinski-Tang", new StyblinskiTang(dim_2, maxFestDefault * dim_2));
            runProblem("Levy", new Levy(dim_2, maxFestDefault * dim_2));
            runProblem("Michalewicz", new Michalewicz(dim_2, maxFestDefault * dim_2));
            System.out.println("\n");

            System.out.println("Run - 5 dimensions");
            runProblem("Sphere", new Sphere(dim_5, maxFestDefault * dim_5));
            runProblem("Ackley", new Ackley(dim_5, maxFestDefault * dim_5));
            runProblem("Griewank", new Griewank(dim_5, maxFestDefault * dim_5));
            runProblem("Rastrigin", new Rastrigin(dim_5, maxFestDefault * dim_5));
            runProblem("Schwefel26", new Schwefel(dim_5, maxFestDefault * dim_5));
            runProblem("Rosenbrock", new Rosenbrock(dim_5, maxFestDefault * dim_5));
            runProblem("Trid", new Trid(dim_5, maxFestDefault * dim_5));
            System.out.println("Bukin: skipping (2 dimensions only)");
            runProblem("Carrom Table", new CarromTable(dim_5, maxFestDefault * dim_5));
            runProblem("Styblinski-Tang", new StyblinskiTang(dim_5, maxFestDefault * dim_5));
            runProblem("Levy", new Levy(dim_5, maxFestDefault * dim_5));
            runProblem("Michalewicz", new Michalewicz(dim_5, maxFestDefault * dim_5));
            System.out.println("\n");

            System.out.println("Run - 10 dimensions");
            runProblem("Sphere", new Sphere(dim_10, maxFestDefault * dim_10));
            runProblem("Ackley", new Ackley(dim_10, maxFestDefault * dim_10));
            runProblem("Griewank", new Griewank(dim_10, maxFestDefault * dim_10));
            runProblem("Rastrigin", new Rastrigin(dim_10, maxFestDefault * dim_10));
            runProblem("Schwefel26", new Schwefel(dim_10, maxFestDefault * dim_10));
            runProblem("Rosenbrock", new Rosenbrock(dim_10, maxFestDefault * dim_10));
            runProblem("Trid", new Trid(dim_10, maxFestDefault * dim_10));
            System.out.println(" Bukin: skipping (2 dimensions only)");
            runProblem("Carrom Table", new CarromTable(dim_10, maxFestDefault * dim_10));
            runProblem("Styblinski-Tang", new StyblinskiTang(dim_10, maxFestDefault * dim_10));
            runProblem("Levy", new Levy(dim_10, maxFestDefault * dim_10));
            runProblem("Michalewicz", new Michalewicz(dim_10, maxFestDefault * dim_10));
            System.out.println("\n");
        }
    }

    public static boolean unitTest(String name, Problem p, Double[] optimum, Double expected, Double tolerance) {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_GREEN = "\u001B[32m";

        double eval = p.fitnessFunction(optimum);
        double diff = eval - expected;
        boolean inTolerance = Math.abs(diff) <= tolerance;

        if (inTolerance) {
            System.out.println(ANSI_GREEN + name + ": " + eval + ", expected: " + expected + "; diff: " + diff
                    + ", passed" + ANSI_RESET);
        } else {
            System.out.println(ANSI_RED + name + ": " + eval + ", expected: " + expected + "; diff: " + diff
                    + ", failed" + ANSI_RESET);
        }

        return inTolerance;
    }

    public static void runProblem(String problemName, Problem problem) {
        Algorithm rs = new RandomSearch();
        Solution best = rs.execute(problem, debugMode);
        System.out.println(problemName + ": " + Arrays.toString(best.getX()) + ", fitness: " + best.getFitness());
    }
}

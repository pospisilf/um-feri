import java.util.List;

public class StatisticsUtility {
    public static double findMin(List<Double> values) {
        return values.stream().min(Double::compare).orElse(Double.NaN);
    }

    public static double calculateAverage(List<Double> values) {
        return values.stream().mapToDouble(Double::doubleValue).average().orElse(Double.NaN);
    }

    public static double calculateStandardDeviation(List<Double> values, double average) {
        double sumOfSquares = values.stream()
                                    .mapToDouble(v -> Math.pow(v - average, 2))
                                    .sum();
        return Math.sqrt(sumOfSquares / values.size());
    }
}

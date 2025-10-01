# EC - Evolutionary Computation

**Optimization Algorithms for Continuous Function Optimization**

This project implements and compares various evolutionary computation algorithms for solving continuous optimization problems. The system progresses through multiple tasks, from basic random search to advanced metaheuristic algorithms including Hill Climbing, Differential Evolution, and Improved Gray Wolf Optimization.

## 🎯 Project Overview

The project addresses the fundamental challenge of finding optimal solutions in continuous optimization landscapes using evolutionary computation techniques. Using various metaheuristic algorithms, the system explores solution spaces to find global optima for benchmark optimization functions, demonstrating the effectiveness of different evolutionary approaches.

### Key Features
- **Multiple Optimization Algorithms**: Random Search, Hill Climbing, Differential Evolution, Gray Wolf Optimization
- **Comprehensive Benchmark Suite**: 12 standard optimization functions with varying complexity
- **Multi-dimensional Support**: Testing across 2, 5, 10, 20, and 30 dimensions
- **Statistical Analysis**: Performance evaluation with multiple runs and statistical measures
- **Algorithm Comparison**: Direct performance comparison between different approaches
- **Modular Architecture**: Extensible framework for implementing new algorithms

## 📊 Benchmark Functions

The project uses a comprehensive suite of optimization benchmark functions:

| Function | Description | Global Optimum | Search Space | Characteristics |
|----------|-------------|----------------|--------------|-----------------|
| Sphere | Simple unimodal function | f(0,...,0) = 0 | [-100, 100]^n | Convex, separable |
| Ackley | Multimodal with many local optima | f(0,...,0) = 0 | [-32, 32]^n | Non-separable, many local optima |
| Griewank | Product term creates complexity | f(0,...,0) = 0 | [-600, 600]^n | Non-separable, many local optima |
| Rastrigin | Highly multimodal | f(0,...,0) = 0 | [-5.12, 5.12]^n | Separable, many local optima |
| Schwefel | Deceptive function | f(420.97,...,420.97) = -418.98n | [-500, 500]^n | Non-separable, deceptive |
| Rosenbrock | Valley-shaped landscape | f(1,...,1) = 0 | [-2.048, 2.048]^n | Non-separable, narrow valley |
| Trid | Quadratic function | f(1,...,1) = -n(n+4)(n-1)/6 | [-n², n²]^n | Separable, quadratic |
| Bukin | Non-continuous function | f(-10, 1) = 0 | x₁∈[-15,-5], x₂∈[-3,3] | Non-separable, non-continuous |
| Carrom Table | Complex multimodal | f(±9.646, ±9.646) = -24.1568 | [-10, 10]^2 | Non-separable, multimodal |
| Styblinski-Tang | Sum of quartic functions | f(-2.903,...,-2.903) = -39.166n | [-5, 5]^n | Separable, multimodal |
| Levy | Complex multimodal | f(1,...,1) = 0 | [-10, 10]^n | Non-separable, multimodal |
| Michalewicz | Product of sine terms | f(2.20, 1.57) = -1.8013 | [0, π]^n | Non-separable, multimodal |

## 🏗️ Project Structure

```
ec/
├── task1/                          # Task 1: Random Search Algorithm
│   ├── instructions/               # Assignment instructions and notes
│   ├── src/                        # Java source code
│   │   ├── Algorithm.java          # Abstract algorithm interface
│   │   ├── RandomSearch.java       # Random search implementation
│   │   ├── Problem.java            # Abstract problem interface
│   │   ├── Solution.java           # Solution representation
│   │   ├── Config.java             # Configuration parameters
│   │   ├── MainTest.java           # Main execution class
│   │   └── [Function classes]      # Benchmark function implementations
│   ├── run_task1.sh                # Execution script
│   └── README.md                   # Task-specific documentation
├── task2/                          # Task 2: Hill Climbing Algorithm
│   ├── instructions/               # Assignment instructions
│   ├── src/                        # Java source code
│   │   ├── HillClimbing.java       # Basic hill climbing
│   │   ├── ImprovedHillClimbing.java # Enhanced hill climbing
│   │   ├── StatisticsUtility.java  # Statistical analysis tools
│   │   └── [Shared classes]        # Common problem and solution classes
│   ├── run_task2.sh                # Execution script
│   └── README.md                   # Task-specific documentation
├── task3/                          # Task 3: Differential Evolution
│   ├── src/                        # Java source code
│   │   ├── DifferentialEvolution.java # DE algorithm implementation
│   │   └── [Shared classes]        # Common problem and solution classes
│   ├── results/                    # Experimental results
│   │   ├── DE-Pospisil_*.txt       # Results for each function/dimension
│   │   └── Pospisil_results.zip    # Compressed results archive
│   ├── run_task3.sh                # Execution script
│   └── README.md                   # Task-specific documentation
└── task4/                          # Task 4: Gray Wolf Optimization
    ├── src/                        # Java source code
    │   ├── ImprovedGrayWolfOptimization.java # IGWO implementation
    │   └── [Shared classes]        # Common problem and solution classes
    ├── run_task4.sh                # Execution script
    └── README.md                   # Task-specific documentation
```

## 🚀 Task Implementation

### Task 1: Random Search Algorithm
- **Algorithm**: Pure random search with uniform sampling
- **Purpose**: Baseline comparison for other algorithms
- **Features**: 
  - Random solution generation within problem bounds
  - Fitness evaluation tracking
  - Statistical performance analysis
- **Testing**: 2, 5, and 10 dimensions with 3000 × dimension function evaluations

### Task 2: Hill Climbing Algorithm
- **Algorithm**: Local search with neighborhood exploration
- **Features**:
  - Step-size based neighbor generation
  - Restart mechanism for escaping local optima
  - Visited position tracking to avoid cycles
  - Improved version with enhanced exploration
- **Testing**: Statistical comparison between original and improved versions
- **Performance**: 30 runs per problem with statistical analysis

### Task 3: Differential Evolution
- **Algorithm**: Population-based evolutionary algorithm
- **Parameters**:
  - Population size: 20 individuals
  - Crossover rate: 0.5
  - Mutation factor: 0.6
- **Features**:
  - Mutation: DE/rand/1 strategy
  - Crossover: Binomial crossover
  - Selection: Greedy selection
- **Testing**: 50 runs per problem in 10, 20, and 30 dimensions

### Task 4: Improved Gray Wolf Optimization
- **Algorithm**: Nature-inspired metaheuristic based on wolf pack behavior
- **Improvements**:
  - Lens Imaging Reverse Learning for population initialization
  - Nonlinear convergence strategy for better exploration-exploitation balance
  - Adaptive position updates inspired by TSA and PSO
- **Features**:
  - Social hierarchy simulation (alpha, beta, delta, omega wolves)
  - Dynamic parameter adjustment
  - Enhanced global search capability
- **Testing**: Comparison with Differential Evolution

## 🛠️ Technical Implementation

### Algorithm Interface
```java
public abstract class Algorithm {
    public abstract Solution execute(Problem p, boolean isDebug);
}
```

### Problem Interface
```java
public abstract class Problem {
    public abstract double fitnessFunction(Double[] x);
    public abstract Solution generateRandomSolution();
    public abstract int getNumberOfDimension();
    public abstract Double[] getLowerLimit();
    public abstract Double[] getUpperLimit();
}
```

### Solution Representation
```java
public class Solution {
    private Double[] x;          // Decision variables
    private double fitness;      // Objective function value
    
    public Solution(Double[] x, double fitness) {
        this.x = x.clone();
        this.fitness = fitness;
    }
}
```

### Differential Evolution Implementation
```java
// Mutation: v = x_a + F * (x_b - x_c)
Double[] v = new Double[dimensions];
for (int j = 0; j < dimensions; j++) {
    v[j] = sa.getX()[j] + mutationFactor * (sb.getX()[j] - sc.getX()[j]);
    v[j] = Math.max(p.getLowerLimit()[j], Math.min(p.getUpperLimit()[j], v[j]));
}

// Crossover: binomial crossover
for (int j = 0; j < dimensions; j++) {
    if (random.nextDouble() < crossoverRate || j == R) {
        y[j] = v[j];
    } else {
        y[j] = xi.getX()[j];
    }
}
```

## 🔧 Usage

### Running Individual Tasks
```bash
# Task 1: Random Search
cd task1
./run_task1.sh

# Task 2: Hill Climbing
cd task2
./run_task2.sh runAll

# Task 3: Differential Evolution
cd task3
./run_task3.sh

# Task 4: Gray Wolf Optimization
cd task4
./run_task4.sh
```

### Debug Mode
```bash
# Enable debug output for algorithm execution
./run_task1.sh debug
./run_task2.sh runAll debug
```

### Statistical Analysis
```bash
# Run multiple experiments and save results
./run_task3.sh write
./run_task4.sh write
```

### Algorithm Comparison
```bash
# Compare DE vs IGWO performance
./run_task4.sh compare
```

## 📚 Dependencies

### Java Requirements
- **Java Development Kit (JDK)**: Version 8 or higher
- **Java Runtime Environment (JRE)**: For execution
- **Shell Environment**: Bash or compatible shell

## 🎓 Learning Outcomes

This project demonstrates practical implementation of:

- **Evolutionary Algorithms**: Understanding of population-based optimization
- **Local Search Methods**: Hill climbing and neighborhood exploration
- **Metaheuristic Design**: Algorithm improvement and parameter tuning
- **Statistical Analysis**: Performance evaluation and comparison
- **Software Engineering**: Modular design and extensible architecture
- **Experimental Design**: Systematic testing and result analysis

## 📄 Assignment Details

This project was completed as part of the **EC (Evolutionary Computation)** course at the University of Maribor, Faculty of Electrical Engineering and Computer Science (FERI). The assignment focused on implementing and comparing various evolutionary computation algorithms for continuous optimization problems.

**Course**: EC - Evolutionary Computation  
**Institution**: University of Maribor, FERI  
**Focus**: Metaheuristic algorithm implementation and performance analysis

---

*This project showcases the complete implementation and comparison of evolutionary computation algorithms, demonstrating modern optimization techniques and systematic experimental methodology for algorithm evaluation.*

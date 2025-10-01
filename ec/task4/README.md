# EC Task 4

### Author
- **Name**: Filip Pospisil
- **Email**: filip.pospisil@student.um.si

---

### Prerequisites
- Java
- Shell

---

## About selected algorhitm
### Original Gray Wolf Optimization (GWO)
The **Gray Wolf Optimization (GWO)** algorithm is a nature-inspired metaheuristic optimization method based on the social hierarchy and hunting behavior of gray wolves. It was introduced by Mirjalili et al. in 2014 and is commonly used for solving single-objective optimization problems.

### Key Concepts of Original GWO:
1. **Social Hierarchy**:
   - Wolves are categorized as alpha (leader), beta (second best), delta (third best), and omega (followers).
   - The alpha guides the pack, and beta and delta support by influencing decision-making.

2. **Position Updates**:
   - Wolves adjust their positions based on the influence of the alpha, beta, and delta wolves.
   - The position update formula uses two key parameters:
     - **`A`**: Controls exploration and exploitation.
     - **`C`**: Random coefficients for diversity.

3. **Exploration and Exploitation**:
   - GWO balances these phases using a control parameter `a`, which linearly decreases from 2 to 0 over iterations, gradually shifting focus from exploration to exploitation.

4. **Advantages**:
   - Suitable for various continuous optimization problems.

5. **Limitations**:
   - Can suffer from premature convergence.
   - Struggles with high-dimensional or complex multi-modal problems due to limited diversity and local search capability.

### Improved Gray Wolf Optimization (Improved GWO)
The **Improved Gray Wolf Optimization (Improved GWO)** builds upon the original GWO with the following enhancements:

1. **Lens Imaging Reverse Learning**:
   - Introduced to improve population diversity and enhance global search capability during initialization.

2. **Nonlinear Convergence Strategy**:
   - Dynamically adjusts the control parameter `a` using methods like cosine variation for better exploration-exploitation balance.

3. **Adaptive Position Updates**:
   - Inspired by Tunicate Swarm Algorithm (TSA) and Particle Swarm Optimization (PSO), enabling faster convergence and more effective local search.

These improvements make the algorithm more robust and effective for high-dimensional, multi-modal optimization problems, offering better performance and faster convergence compared to the standard GWO.

### Summary Table: GWO vs. Improved GWO
| **Feature**                  | **Standard GWO**                    | **Improved GWO**                               |
|------------------------------|--------------------------------------|-----------------------------------------------|
| **Population Initialization** | Random                              | Reverse Learning for enhanced diversity       |
| **Control Parameter**         | Linear Decrement                    | Nonlinear Strategy (e.g., Cosine Variation)   |
| **Position Update**           | Linear formulas                     | TSA/PSO-inspired adaptive tuning             |
| **Exploration & Exploitation**| Limited                             | Balanced and adaptive                        |
| **Performance**               | Moderate                            | Enhanced convergence and precision           |
| **Complex Problem Suitability**| Moderate                            | Excellent for complex, multi-modal problems  |

---

### Basic Commands

#### Run
Run all supported problems in dimensions 10, 20 and 30 with 50 runs for each.

```shell
./run_task4.sh
```

#### Write to file
Will do same as run but will write results to files located in results folder.

```shell
./run_task4.sh write
```

#### Compare with DE
Will run all supported problems in 10 dimension with DE and IGWO and checks which one performs better.

Example: 
```shell
./run_task4.sh compare
```

# EC Task 2

### Author
- **Name**: Filip Pospisil
- **Email**: filip.pospisil@student.um.si

---

### Prerequisites
- Java
- Shell

---

### Basic Commands

#### Run
This command will compile code and run Hill Climbing algorhitm for problems in 2, 5 and 10 dimensions (if possible). Default maxFest is 3000*number of dimensions.

Default Hill Climbing algorhitm already contains some improvements.

```shell
./run_task2.sh runAll
```

Example: 
```shell
Problem: Sphere, Dimensions: 2, Min: 3.811571488258368E-6, Average: 7.485692818368677E-4, Std: 5.935334025366721E-4
Problem: Ackley, Dimensions: 2, Min: 0.03525287007517708, Average: 4.449277875010676, Std: 2.5084944737751966
Problem: Griewank, Dimensions: 2, Min: 0.008240992585274443, Average: 1.9452021334598704, Std: 1.8526864917650803
Problem: Rastrigin, Dimensions: 2, Min: 0.008956186797668408, Average: 0.2804018394907681, Std: 0.30140523194592356
Problem: Schwefel26, Dimensions: 2, Min: -837.9657560407059, Average: -588.7234102274222, Std: 142.86372866138367
Problem: Rosenbrock, Dimensions: 2, Min: 1.7239787712416106E-4, Average: 0.059667760732900726, Std: 0.04607204447773491
Problem: Bukin, Dimensions: 2, Min: 0.08077464098496653, Average: 0.9743385577006218, Std: 0.4694775410150775
Problem: CarromTable, Dimensions: 2, Min: -24.156692027724258, Average: -24.140740234648153, Std: 0.013783543437607956
Problem: StyblinskiTang, Dimensions: 2, Min: -78.332155477901, Average: -78.33003742966062, Std: 0.0021701815893590964
Problem: Michalewicz, Dimensions: 2, Min: -1.8012915446815607, Average: -1.8002370930593203, Std: 9.571000435406109E-4
```

#### Run One
Run only one problem with default and improved version - Sphere in dimension 2. 
```shell
./run_task2.sh runOne
```

Example: 
```shell
Problem: Sphere, Dimensions: 2
Original - Min: 5.5930365547381925E-6, Average: 7.572063931075877E-4, Std: 6.439374923413097E-4
Improved - Min: 2.2391587252148297E-5, Average: 0.009590995478575793, Std: 0.00849845266338008
```

#### Compare
Compare default and improved version on all problems in dimensions 2, 5 and 10. 
```shell
./run_task2.sh compare
```

Example: 
```shell
Problem: Sphere, Dimensions: 2
Original - Min: 6.221660324619952E-6, Average: 7.4006842405896E-4, Std: 6.133118637486493E-4
Improved - Min: 2.0397596247491147E-6, Average: 0.007743755855567777, Std: 0.008983066748316233
Problem: Ackley, Dimensions: 2
Original - Min: 0.055434273268169854, Average: 4.45925254351617, Std: 2.130880085292501
Improved - Min: 0.0059133656152505765, Average: 0.18387305627277795, Std: 0.1257947126278156
Problem: Griewank, Dimensions: 2
Original - Min: 0.007745156772514417, Average: 1.3563049545487487, Std: 1.240876983300791
Improved - Min: 0.03385668758429983, Average: 0.2722023238638711, Std: 0.17917884459485697
```

#### Debug
Will activate debug log. Can't be used alone. Need to be combined with runAll or runOne. 
```shell
./run_task1.sh runAll debug
```

Example: 
```shell
Improved solution found: Fitness = 20.32282631910154, Variables = [2.1638956928276203, -1.2381691964677484]
Improved solution found: Fitness = 9.887762574016154, Variables = [2.06389569282762, -1.1381691964677483]
New global best solution: Fitness = 8.946068115970654, Variables = [1.96389569282762, -1.1381691964677483]
New global best solution: Fitness = 6.41878158902632, Variables = [2.06389569282762, -1.0381691964677482]
New global best solution: Fitness = 5.47708713098082, Variables = [1.96389569282762, -1.0381691964677482]
Improved solution found: Fitness = 5.47708713098082, Variables = [1.96389569282762, -1.0381691964677482]
Restarted with new random point: [1.0657810469793292, -0.4858317071196465]
Improved solution found: Fitness = 18.8474105308958, Variables = [0.9657810469793292, -0.38583170711964654]
```
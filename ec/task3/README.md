# EC Task 3

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
Run all supported problems in dimensions 10, 20 and 30 with 50 runs for each.

```shell
./run_task3.sh
```

Example: 
```shell
Run 01 - Problem: Ackley (dim=10) Best Fitness: 0.000000000000004440892098500626
Run 02 - Problem: Ackley (dim=10) Best Fitness: 0.000000000000004440892098500626
Run 03 - Problem: Ackley (dim=10) Best Fitness: 0.000000000000004440892098500626
Run 04 - Problem: Ackley (dim=10) Best Fitness: 0.000000000000004440892098500626
Run 05 - Problem: Ackley (dim=10) Best Fitness: 0.0000000000000008881784197001252
Run 06 - Problem: Ackley (dim=10) Best Fitness: 0.000000000000004440892098500626
Run 07 - Problem: Ackley (dim=10) Best Fitness: 0.000000000000004440892098500626
```

#### Write to file
Will do same as run but will write results to files located in results folder.

```shell
./run_task3.sh write
```

Example: 
```shell
...
Run 49 - Problem: Ackley (dim=10) Best Fitness: 0.000000000000004440892098500626
Run 50 - Problem: Ackley (dim=10) Best Fitness: 0.000000000000004440892098500626
Results written to file: DE-Pospisil_AckleyD10.txt
```

Content of file: 
```shell
...
0.000000000000007993605777301127
0.000000000000007993605777301127
0.000000000000007993605777301127
0.000000000000007993605777301127
0.000000000000007993605777301127
0.000000000000007993605777301127
0.000000000000007993605777301127
0.000000000000007993605777301127
0.000000000000007993605777301127
0.000000000000004440892098500626
0.000000000000007993605777301127
0.000000000000007993605777301127
0.000000000000007993605777301127
0.000000000000007993605777301127
...
```
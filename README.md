# OOP_2

___

## Part 1

___
In this part we compare 3 methods of calculating the combined number of lines of multiple txt files:

#### Files creator

```
    public static String[] createTextFiles(int n, int seed, int bound)
```

A method to create *n* txt files, each with a random number of lines.

### Iterative approach

```
    public static int getNumOfLines(String[] fileNames)
```

Loop over each file and count its lines.

### Threads

```
    public static int getNumOfLinesThreads(String[] fileNames)
```

Using the *class* ***LineCounter*** we built, which extents the *class* ***Thread***, we were able to calculate the
number of lines by assigning a thread to calculate the number of lines in each file.

### ThreadPool

```
    public static int getNumOfLinesThreadPool(String[] fileNames)
```

Using the *class* ***LineCounterPool*** we built, which implements the *interface* ***Callable***, we create a
LineCounterPool object for each file and submit it to an executor service (a thread pool).
The **OVERRIDDEN** *call()* method counts the number of line in the file assigned to it, and waits for the results of
the Callable tasks and returns the total number of lines in all the files.

### Results Comparison

|     | Num of files | Bound  | Total num of lines | *Iterative* | *Threads* | *ThreadPool* |
|-----|--------------|--------|--------------------|-------------|-----------|--------------|
| 1   | 10           | 10     | 41                 | 18 ms       | 6 ms      | 9 ms         |
| 2   | 100          | 50     | 2,730              | 171 ms      | 28 ms     | 11 ms        |
| 3   | 100          | 100    | 5,380              | 211 ms      | 21 ms     | 15 ms        | 
| 4   | 1,000        | 500    | 247,226            | 2,629 ms    | 91 ms     | 106 ms       |
| 5   | 10,000       | 1,000  | 5,031,925          | 38,324 ms   | 820 ms    | 1,024 ms     |
| 6   | 10,000       | 5,000  | 25,195,925         | 87,420 ms   | 2,291 ms  | 5,863 ms     |
| 7   | 50,000       | 10,000 | 249,777,821        | 404,677 ms  | 12,281 ms | 103,469 ms   |
| 8   |              |        |                    |             |           |              |
| 9   |              |        |                    |             |           |              |
| 10  |              |        |                    |             |           |              |

## UML Diagram for Part 1
![your-UML-diagram-name] (//www.plantuml.com/plantuml/png/XP9HI_im5CNVyoak_5ywri7VFGqHcGm5GLHm365CoPgxBNYRbEHMbE5tDsrMrwqJyp16yZldvDnqx5awBdCIR0jW2qlBR7DGsc76sXhXtxGff9Cva3R8dmM2TRonGcZ3w5OoGvX88haal9XTmbO0z24BEqdl76RYIsGalOVhv-7hMJoD_vDU91rAnYb-y8qcz4bmJQ7v2GvFxBHPpnSrl4Q-B_E7rPqswAUR85K-QOW01qdDAIHal2AAdYtYtMhrczMZjNJira5F0RdKfXFI5AzK4rjMmuhrspixLJMA0PWohoFsnguqoVxIXy2fWAf1DwJLgtVmEhVXT8tknocE0RR7sG4GxHhrwW_c_qR3_s6b1KbjqgKbjHX0VDqk7_0A7WqFy1VOfOhZ6b5G9QHA-WsglQBvtZgQlourHgFAdBu1)
## Authors

Roey Feingold https://github.com/RF555

Gal Hilell https://github.com/GalHillel
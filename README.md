# comp3111grp34

![alt text](https://github.com/yxiaoaz/Comp3111F23G34/blob/main/group%20info.png)


# How To Run
## 1. Add the `com.opencsv.CSVWriter` dependency in IntelliJ

*Step 1*
![Step 1](https://github.com/yxiaoaz/Comp3111F23G34/blob/main/AddOpenCSVDependencyNo1.png)

*Step 2*
![Step 2](https://github.com/yxiaoaz/Comp3111F23G34/blob/main/AddOpenCSVDependencyNo2.png)

*Step 3*
![Step 3](https://github.com/yxiaoaz/Comp3111F23G34/blob/main/AddOpenCSVDependencyNo3.png)

*Step 4*
![Step 4](https://github.com/yxiaoaz/Comp3111F23G34/blob/main/AddOpenCSVDependencyNo4.png)

Before running the main function, please remember to search for `com.opencsv:opencsv` in "Dependencies", and add `com.opencsv:opencsv` to your dependency.
The version of `com.opencsv:opencsv` should be ***5.7.1*** in ***default*** mode. (If you cannot find ***default*** mode, choose ***compile*** mode instead.)
Also, remember to reload Maven afterwards so that the dependency is properly configured in your running environment.

## 2. Run main program in `src/main/java/GameFactory.java`

# Reading the shortest path csv file
After running f(A) & f(B), shortest path's routing data will be output in a .csv file. Here are the instructions for reading that file.

1. The file's directory from the repository root is "src/main/java/shortest_path_at_beginning.csv".
2. The first line of the file is:
s1
3. s1 represents the first shortest path found in the maze. Data type of line 1 is three char. There will be only one path being output into this .csv file.
4. Starting from line 2, each line will have 2 attributes: Row_X and Col_Y, which both are integers. The represent from grid in the map.
5. Row_X ranged from 0 to 29. Col_Y ranged from 0 to 29. The uppermost row represents Row_X = 0, the lowermost row represents Row_X = 29. The leftmost column represents Col_Y = 0, the rightmost column represents Col_Y = 29.
6. In the output starting from line 2, Row_X will be the first attribute, then comma-separated, Col_Y will be the second attribute.
7. Complete sample output using seed = 3111 to generate the map and the shortest path's routing data is as follows.
![Sample output.png](Sample%20output.png)
9. The starting point is at line 2, Row 11, Column 0. The ending point is at line 37, Row 9, Column 29.
10. By looking at the line number, we can know the length of the shortest path easily. For example, in the above output, length of the shortest path = 37 - 1 = 36.

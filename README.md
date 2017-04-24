# A-Star-Shortest-Pathfinding-Algorithm-Square-Grid-Java

Java based solution to find the shortest path between 2 Grid Cells. [A* Shortest Pathfinding Algorithm]

### Paths and Values
- Manhattan Path - Can only Travel in Vertical directions (Vertical gCost = 1)
- Chebyshev Path - Can Travel in both Diagonal and Vertical directions (Vertical gCost = 1, Diagonal gCost = 1)
- Euclidean Path - Can Travel in both Diagonal and Vertical directions (Vertical gCost = 1, Diagonal gCost = 1.4)

### Input
- Grid Size (NxN) => E.g: 20
- Percolation Ratio (0-1) => E.g: 0.8
- x, y  coordinates of the starting Cell => E.g: 0, 0
- x, y  coordinates of the ending Cell => E.g: 0, 0

### Output
- Total path gCost 
- Time to took to calculate the shortest path
- Manhattan Path - Yellow Line
- Chebyshev Path - Red Color Filled Squares
- Euclidean Path - Black Line

## Screenshots

**Grid Size: 20x20, Percolation Ratio: 0.8**

<img src="http://i.imgur.com/tJH5BUp.png" width="350">

**Grid Size: 20x20, Percolation Ratio: 0.6**

<img src="http://i.imgur.com/TK91JQ9.png" width="350">


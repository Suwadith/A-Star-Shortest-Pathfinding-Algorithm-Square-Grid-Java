# A-Star-Shortest-Pathfinding-Algorithm-Square-Grid-Java

Java based solution to find the shortest path's distance between 2 Grid Cells. [A* Shortest Pathfinding Algorithm]

### Paths and Values
```
Manhattan Path - Travels in vertical/horizontal directions (Vertical/Horizontal gCost = 1)
Chebyshev Path - Travels in both diagonal and vertical/horizontal directions (Vertical/Horizontal gCost = 1, Diagonal gCost = 1)
Euclidean Path - Travels in both diagonal and vertical/horizontal directions (Vertical/Horizontal gCost = 1, Diagonal gCost = 1.4)
```

### Input
```
Grid size (NxN) => E.g: 20
Percolation ratio (0-1) => E.g: 0.8
x, y  coordinates of the starting cell => E.g: 0, 0
x, y  coordinates of the ending cell => E.g: 0, 0
```

### Output
```
Total path gCost 
Time taken to calculate the shortest path
Manhattan Path - Yellow line
Chebyshev Path - Squares filled in red color
Euclidean Path - Black line
```

## Screenshots

**Grid Size: 20x20, Percolation Ratio: 0.8**

<img src="http://i.imgur.com/TK91JQ9.png" width="350">

**Grid Size: 20x20, Percolation Ratio: 0.6**

<img src="http://i.imgur.com/tJH5BUp.png" width="350">


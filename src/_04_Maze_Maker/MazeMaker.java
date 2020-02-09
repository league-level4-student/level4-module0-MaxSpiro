package _04_Maze_Maker;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;


public class MazeMaker{
	
	private static int width;
	private static int height;
	
	private static Maze maze;
	
	private static Random randGen = new Random();
	private static Stack<Cell> uncheckedCells = new Stack<Cell>();
	
	
	public static Maze generateMaze(int w, int h){
		width = w;
		height = h;
		maze = new Maze(width, height);
		
		Random random = new Random();
		//4. select a random cell to start
		
		//5. call selectNextPath method with the randomly selected cell
		

		Cell start;
		String[] possSides = {"UD","LR"};
		String startingSide = possSides[(int) (random.nextDouble()*2)];
		if(startingSide.equals("LR")) {
			start = maze.cells[0][(int) (random.nextDouble()*width)];
			start.setWestWall(false);
			maze.cells[width-1][(int) (random.nextDouble()*width)].setEastWall(false);
			
		} else {
			start = maze.cells[(int) (random.nextDouble()*height)][height-1];
			start.setSouthWall(false);
			maze.cells[(int) (random.nextDouble()*height)][0].setNorthWall(false);
			
		}
		System.out.println(start.getX()+" "+start.getY());
		selectNextPath(start);
		return maze;
	}

	//6. Complete the selectNextPathMethod
	private static void selectNextPath(Cell currentCell) {
		//A. mark cell as visited
		currentCell.setBeenVisited(true);
		//B. Get an ArrayList of unvisited neighbors using the current cell and the method below
		ArrayList<Cell> unvisited = getUnvisitedNeighbors(currentCell);
		//C. if has unvisited neighbors,
		if(unvisited.size()>0) {
			//C1. select one at random.
			Cell next = unvisited.get((int)(new Random().nextDouble() * unvisited.size()));
			//C2. push it to the stack
			uncheckedCells.push(next);
			//C3. remove the wall between the two cells
			removeWalls(currentCell, next);
			//C4. make the new cell the current cell and mark it as visited
			currentCell = next;
			currentCell.setBeenVisited(true);
			//C5. call the selectNextPath method with the current cell
			selectNextPath(currentCell);
		}
			
		//D. if all neighbors are visited
		if(unvisited.size()==0) {
			//D1. if the stack is not empty
				if(uncheckedCells.size()!=0) {
				// D1a. pop a cell from the stack
					currentCell = uncheckedCells.pop();
				// D1b. make that the current cell
					
				// D1c. call the selectNextPath method with the current cell
					selectNextPath(currentCell);
				}
		}
			
		
	}

	//7. Complete the remove walls method.
	//   This method will check if c1 and c2 are adjacent.
	//   If they are, the walls between them are removed.
	private static void removeWalls(Cell c1, Cell c2) {
		if(c1.getX()>c2.getX() && Math.abs(c1.getX()-c2.getX())==1) {
			c1.setWestWall(false);
			c2.setEastWall(false);
		}
		if(c1.getX()<c2.getX() && Math.abs(c1.getX()-c2.getX())==1) {
			c1.setEastWall(false);
			c2.setWestWall(false);
		}
		if(c1.getY()>c2.getY() && Math.abs(c1.getY()-c2.getY())==1) {
			c1.setNorthWall(false);
			c2.setSouthWall(false);
		}
		if(c1.getY()<c2.getY() && Math.abs(c1.getY()-c2.getY())==1) {
			c1.setSouthWall(false);
			c2.setNorthWall(false);
		}
	}
	
	//8. Complete the getUnvisitedNeighbors method
	//   Any unvisited neighbor of the passed in cell gets added
	//   to the ArrayList
	private static ArrayList<Cell> getUnvisitedNeighbors(Cell c) {
		ArrayList<Cell> u = new ArrayList<Cell>();
		int x = c.getX();
		int y = c.getY();
		if(x!=0) {
			if(!maze.getCell(x-1, y).hasBeenVisited())
				u.add(maze.getCell(x-1, y));
		}
		if(x!=maze.getWidth()-1) {
			if(!maze.getCell(x+1, y).hasBeenVisited())
				u.add(maze.getCell(x+1, y));
		}
		if(y!=0) {
			if(!maze.getCell(x, y-1).hasBeenVisited())
				u.add(maze.getCell(x, y-1));
		}
		if(y!=maze.getHeight()-1) {
			if(!maze.getCell(x,y+1).hasBeenVisited())
				u.add(maze.getCell(x, y+1));
		}
		return u;
	}
}

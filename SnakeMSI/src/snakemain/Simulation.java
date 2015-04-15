package snakemain;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import snake.Snake;
import snake.Snake.Movement;

/**
 * <p>
 * Class providing snake game logic. It only takes care of
 * managing snake game map.
 * Issue who controls the snake (human or CPU) is completely transparent.
 * </p>
 * @author Filip
 */
public class Simulation
{
	/* CONSTANTS */
	private static final int MAPX = 30;
	private static final int MAPY = 15;
	
	/* ENUMS */
	public enum Field { EMPTY, SNAKE, APPLE, WALL };
	private enum Direction { UP, DOWN, LEFT, RIGHT };
	
	/* EMBEDDED CLASSES */
	private class Point
	{
		public int x, y;
		public Point(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
	}
	
	/* VARIABLES */
	private Random r;
	private Field[][] map;
	private Snake snake;
	private boolean snakeDead;
	
	private byte[][] snakePerspective;
	private int snakePosX, snakePosY;
	private Direction snakeDirection;
	
	private List<Point> snakeFields;
	
	/* METHODS */
	
	public Simulation()
	{
		r = new Random();
		snakeFields = new LinkedList<>();
		map = new Field[MAPX][MAPY];
		snakePerspective = new byte[3][3];
		snake = null;
	}
	
	/**
	 * <p>Method resets simulation, initializes random generator with given seed, resets snake, clears map.</p>
	 * @param s given snake
	 * @param randSeed given random seed
	 */
	public void resetSimulation(Snake s, long randSeed)
	{
		r.setSeed(randSeed);
		snakeFields.clear();
		snakeDead = false;
		snakeDirection = Direction.UP;
		snakePosX = MAPX/2;
		snakePosY = MAPY/2;
		
		snake = s;
		snake.reset();
		clearMap();
	}
	
	/**
	 * <p>Getter for map.</p>
	 */
	public Field[][] getMap()
	{
		return map;
	}
	
	/**
	 * @return true if snake is dead (simulation needs to finish)
	 */
	public boolean isSnakeDead()
	{
		return snakeDead;
	}
	
	
	/**
	 * <p>Performs single decision and movement of the snake.</p>
	 */
	public void singleStep()
	{
		if ( snakeDead )return;
		
		//TODO: Complete
	}
	
	
	/* PRIVATE AUXILIARY FUNCTIONS */
	
	private void clearMap()
	{
		for ( int x=0; x<MAPX; x++ )
			for ( int y=0; y<MAPY; y++ )
				map[x][y] = Field.EMPTY;
		
		for ( int x=0; x<MAPX; x++ )
			map[x][0] = map[x][MAPY-1] = Field.WALL;
		
		for ( int y=0; y<MAPY; y++ )
			map[0][y] = map[MAPX-1][y] = Field.WALL;
	}
	
	private void getSnakePerspective()
	{
		
	}
}

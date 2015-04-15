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
	private static final int APPLESCORE = 1000;
	private static final int MOVESWITHOUTAPPLELIMIT = MAPX*MAPY;
	
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
	
	private Field[][] snakePerspective;
	private int snakePosX, snakePosY;
	private Direction snakeDirection;
	private List<Point> snakeTail;
	private int currentMovesWithoutApple;
	
	private int targetX, targetY;
	
	/* METHODS */
	
	public Simulation()
	{
		r = new Random();
		snakeTail = new LinkedList<>();
		map = new Field[MAPX][MAPY];
		snakePerspective = new Field[3][3];
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
		snakeDead = false;
		snakeDirection = Direction.UP;
		snakePosX = MAPX/2;
		snakePosY = MAPY/2;
		snakeTail.clear();
		snakeTail.add(new Point(snakePosX,snakePosY));
		currentMovesWithoutApple = 0;
		
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
		
		calculateSnakePerspective();
		calculateTargetCoords(snake.decision(snakePerspective));
		handleMovementToTarget();
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
		
		
		map[snakePosX][snakePosY] = Field.SNAKE;
		generateNewApple();
	}
	
	private void calculateSnakePerspective()
	{
		if ( snakeDirection == Direction.UP )
		{
			for ( int x=0; x<3; x++ )
				for ( int y=0; y<3; y++ )
					snakePerspective[x][y] = map[snakePosX+x-1][snakePosY+y-1];
		}
		
		else if ( snakeDirection == Direction.DOWN )
		{
			for ( int x=0; x<3; x++ )
				for ( int y=0; y<3; y++ )
					snakePerspective[2-x][2-y] = map[snakePosX+x-1][snakePosY+y-1];
		}
		
		else if ( snakeDirection == Direction.RIGHT )
		{
			for ( int x=0; x<3; x++ )
				for ( int y=0; y<3; y++ )
					snakePerspective[y][x] = map[snakePosX+x-1][snakePosY+y-1];
		}
		
		else //LEFT
		{
			for ( int x=0; x<3; x++ )
				for ( int y=0; y<3; y++ )
					snakePerspective[2-y][2-x] = map[snakePosX+x-1][snakePosY+y-1];
		}
	}
	
	private void handleMovementToTarget()
	{
		//snake hits an obstacle
		if ( map[targetX][targetY] == Field.SNAKE || map[targetX][targetY] == Field.WALL )
		{
			snakeDead = true;
			return;
		}
		
		//snake finds an apple
		if ( map[targetX][targetY] == Field.APPLE )
		{
			currentMovesWithoutApple = 0;
			snake.increaseScore(APPLESCORE);
			doMoveToTarget();
			generateNewApple();
		}
		
		//normal move
		else
		{
			doMoveToTarget();
			if ( (++currentMovesWithoutApple) >= MOVESWITHOUTAPPLELIMIT )
			{
				snakeDead = true;
				return;
			}
		}
	}
	
	private void generateNewApple()
	{
		int x,y;
		do
		{
			x = r.nextInt(MAPX);
			y = r.nextInt(MAPY);
		}while ( map[x][y] != Field.EMPTY );
		
		map[x][y] = Field.APPLE;
	}
	
	private void doMoveToTarget()
	{
		Point last = snakeTail.get(snakeTail.size()-1);
		
		snakePosX = targetX;
		snakePosY = targetY;
		
		map[snakePosX][snakePosY] = Field.SNAKE;
		map[last.x][last.y] = Field.EMPTY; 
		
		snakeTail.remove(last);
		snakeTail.add(new Point(snakePosX,snakePosY));
		
		snake.increaseScore(1);
	}
	
	private void calculateTargetCoords(Movement nextMove)
	{
		targetX = snakePosX;
		targetY = snakePosY;
		
		if ( snakeDirection == Direction.UP )
		{
			switch (nextMove)
			{
				case FORWARD:
					targetY--;
					break;
				case LEFT:
					targetX--;
					break;
				case RIGHT:
					targetX++;
					break;
			}
		}
		
		if ( snakeDirection == Direction.DOWN )
		{
			switch (nextMove)
			{
				case FORWARD:
					targetY++;
					break;
				case LEFT:
					targetX++;
					break;
				case RIGHT:
					targetX--;
					break;
			}
		}
		
		if ( snakeDirection == Direction.LEFT )
		{
			switch (nextMove)
			{
				case FORWARD:
					targetX--;
					break;
				case LEFT:
					targetY++;
					break;
				case RIGHT:
					targetY--;
					break;
			}
		}
		
		if ( snakeDirection == Direction.RIGHT )
		{
			switch (nextMove)
			{
				case FORWARD:
					targetX++;
					break;
				case LEFT:
					targetY--;
					break;
				case RIGHT:
					targetY++;
					break;
			}
		}
	}
}

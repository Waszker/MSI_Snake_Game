package snakemain;

import java.util.Random;

import snake.Snake;

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
	
	
	/* VARIABLES */
	private Random r;
	private Field[][] map;
	private Snake snake;
	
	/* METHODS */
	
	public Simulation()
	{
		r = new Random();
		map = new Field[MAPX][MAPY];
		snake = null;
	}
	
	/**
	 * <p>Method resets simulation, initializes random generator with given seed, resets snake, clears map.</p>
	 * @param s given snake
	 * @param randSeed given random seed
	 */
	public void resetSimulation(Snake s, long randSeed)
	{
		snake = s;
		snake.reset();
		clearMap();
		r.setSeed(randSeed);
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
}

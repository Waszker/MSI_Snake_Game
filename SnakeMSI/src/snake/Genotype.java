package snake;

import java.io.Serializable;
import java.util.Random;

import snake.Snake.AppleposX;
import snake.Snake.AppleposY;
import snake.Snake.Movement;
import snakemain.Simulation.Field;

/**
 * <p>
 * Genotype class. Provides crossing, mutating, cloning.
 * </p>
 * @author Filip
 */
final class Genotype implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6390183010525011551L;
	
	
	/* CONSTANTS */
	public  static final int NUMGENS = 26;
	public  static final int NUMACTIONS = 3;
	
	private static final int MINMUTATEGENS = 2;
	private static final int MAXMUTATEGENS = 4;
	private static final int MAXMUTATION = 10;
	
	private static final int MINCROSSGENS = 4;
	private static final int MAXCROSSGENS = 8;
	
	private static final Random r = new Random();
	
	/* VARIABLES */
	private long[][] weights = null; //visible only for Genotype and Snake
	private long[] sum = null;
	
	
	
	
	/* PUBLIC METHODS */
	
	protected Genotype()
	{
		weights = new long[NUMGENS][NUMACTIONS];
		sum = new long[NUMACTIONS];
	}
	
	/**
	 * @param neighbourhood nearest fields from snakes perspective
	 * @param prev last move
	 * @param prevprev move previous to last move
	 * @return summed weights for all 'active' genes
	 */
	public long[] weightsForSituation(Field[][] neighbourhood, Movement prev, Movement prevprev,
			AppleposX apX, AppleposY apY)
	{
		for ( int i=0; i<NUMACTIONS; i++ )
			sum[i] = 0;
		
		for ( int i=0; i<NUMGENS; i++ )
			if ( geneActive(i, neighbourhood, prev, prevprev, apX, apY) )
				for ( int j=0; j<NUMACTIONS; j++ )
				{
					sum[j] += weights[i][j] * (i > 22 ? 3 : 1); //* (...) wzmacnia węch
				}
		
		return sum;
	}
	
	/**
	 * <p>Initializes genotype randomly, approximately equal to zero.</p>
	 */
	protected void init()
	{
		for ( int i=0; i<NUMGENS; i++)
		{
			for ( int j=0; j<weights[i].length; j++ )
				weights[i][j] = 0;
			mutateGene(i);
		}
	}
	
	/**
	 * <p>Clones and returns genotype.</p>
	 */
	protected Genotype clone()
	{
		Genotype ret = new Genotype();
		for ( int i=0; i<NUMGENS; i++)
			for ( int j=0; j<weights[i].length; j++ )
				ret.weights[i][j] = weights[i][j];
		return ret;
	}
	
	/**
	 * <p>Performs genotype mutation.</p>
	 */
	protected void mutate()
	{
		boolean[] mutated = new boolean[NUMGENS];
		for ( int i=0; i<mutated.length; i++ )mutated[i] = false;
		
		final int gensToMutate = MINMUTATEGENS + r.nextInt(MAXMUTATEGENS - MINMUTATEGENS);
		for ( int i=0; i<gensToMutate; i++ )
		{
			int j;
			do
			{
				j = r.nextInt(NUMGENS);
			}while ( mutated[j] );
			mutateGene(j);
			mutated[j] = true;
		}
	}
	
	/**
	 * <p>Modifies calling genotype by crossing it with another one.</p>
	 * @param g genotype to cross calling genotype with
	 */
	protected void crossWith(Genotype g)
	{
		boolean[] crossed = new boolean[NUMGENS];
		for ( int i=0; i<crossed.length; i++ )crossed[i] = false;
		
		final int gensToCross = MINCROSSGENS + r.nextInt(MAXCROSSGENS - MINCROSSGENS);
		for ( int i=0; i<gensToCross; i++ )
		{
			int j;
			do
			{
				j = r.nextInt(NUMGENS);
			}while ( crossed[j] );
			for ( int k=0; k<weights[j].length; k++ )
				weights[j][k] = g.weights[j][k];
			crossed[j] = true;
		}
	}	
	
	/* PRIVATE AUXILIARY FUNCTIONS */
	private void mutateGene(int i)
	{
		for ( int j=0; j<weights[i].length; j++ )
			weights[i][j] += r.nextInt(2*MAXMUTATION+1)-MAXMUTATION-1;
	}
	
	private boolean geneActive(int i, Field[][] neighbourhood, Movement prev, Movement prevprev,
			AppleposX applePosX, AppleposY applePosY)
	{
		if ( i < 4 )
		{
			if ( neighbourhood[i%3][i/3] == Field.APPLE )return true;
		}
		else if ( i < 8 )
		{
			if ( neighbourhood[(i+1)%3][(i+1)/3] == Field.APPLE ) return true;
		}
		else if ( i < 12 )
		{
			if ( neighbourhood[(i-8)%3][(i-8)/3] == Field.SNAKE
					|| neighbourhood[(i-8)%3][(i-8)/3] == Field.WALL )return true;
		}
		else if ( i < 16 )
		{
			if ( neighbourhood[(i-7)%3][(i-7)/3] == Field.SNAKE
					|| neighbourhood[(i-7)%3][(i-7)/3] == Field.WALL )return true;
		}
		else if ( i < 19 )
		{
			if ( i == 16 && prev == Movement.FORWARD )return true;
			if ( i == 17 && prev == Movement.RIGHT )return true;
			if ( i == 18 && prev == Movement.LEFT )return true;
		}
		else if ( i < 22)
		{
			if ( i == 19 && prevprev == Movement.FORWARD )return true;
			if ( i == 20 && prevprev == Movement.RIGHT )return true;
			if ( i == 21 && prevprev == Movement.LEFT )return true;
		}
		else
		{
			if ( i == 22 && applePosY == AppleposY.APF )return true;
			if ( i == 23 && applePosY == AppleposY.APB )return true;
			if ( i == 24 && applePosX == AppleposX.APR )return true;
			if ( i == 25 && applePosX == AppleposX.APL )return true;
		}
		
		return false;
	}
	
	/* Field nums: 	123
	 * 				456 (snake is on 5, looking up)
	 * 				789
	 * 
	 * List of genes:
	 *  0) Apple on 1
	 *  1) Apple on 2
	 *  2) Apple on 3
	 *  3) Apple on 4
	 *  4) Apple on 6
	 *  5) Apple on 7
	 *  6) Apple on 8
	 *  7) Apple on 9
	 * 
	 *  8) Obstacle on 1
	 *  9) Obstacle on 2
	 * 10) Obstacle on 3
	 * 11) Obstacle on 4
	 * 12) Obstacle on 6
	 * 13) Obstacle on 7
	 * 14) Obstacle on 8
	 * 15) Obstacle on 9
	 *
	 * 16) Last action was FORWARD
	 * 17) Last action was RIGHT
	 * 18) Last action was LEFT
	 * 
	 * 19) Previous to last action was FORWARD
	 * 20) Previous to last action was RIGHT
	 * 21) Previous to last action was LEFT
	 * 
	 * SMELL:
	 * 22) Apple is somewhere in front of the snake
	 * 23) Apple is somewhere behind the snake
	 * 24) Apple is somewhere on the right
	 * 25) Apple is somewhere on the left
	 */
}

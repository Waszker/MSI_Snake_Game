package snake;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import snakemain.Simulation.Field;

/**
 * <p>
 * Genotype class. Provides crossing, mutating, cloning.
 * </p>
 * @author Filip
 */
final class Genotype
{
	/* CONSTANTS */
	public  static final int NUMGENS = 20;
	public  static final int NUMACTIONS = 3;
	
	private static final int MINMUTATEGENS = 2;
	private static final int MAXMUTATEGENS = 5;
	private static final int MINCROSSGENS = 2;
	private static final int MAXCROSSGENS = 5;
	private static final int MAXMUTATION = 2;
	
	private static final Random r = new Random();
	
	/* VARIABLES */
	protected int[][] weights = null; //visible only for Genotype and Snake
	private int[] sum = null;
	
	
	
	
	/* PUBLIC METHODS */
	
	protected Genotype()
	{
		weights = new int[NUMGENS][NUMACTIONS];
		sum = new int[NUMACTIONS];
	}
	
	/**
	 * @param neighbourhood nearest fields from snakes perspective
	 * @return sumed weights for all 'active' genes
	 */
	public int[] weightsForSituation(Field[][] neighbourhood)
	{
		for ( int i=0; i<NUMACTIONS; i++ )
			sum[i] = 0;
		
		for ( int i=0; i<NUMGENS; i++ )
			if ( geneActive(i, neighbourhood) )
				for ( int j=0; j<NUMACTIONS; j++ )
					sum[j] += weights[i][j];
		
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
	
	/**
	 * <p>Saves genotype to given output stream.</p>
	 * @throws IOException
	 */
	protected void save(OutputStream o) throws IOException
	{
		for ( int i=0; i<NUMGENS; i++)
			for ( int j=0; j<weights[i].length; j++ )
				o.write(weights[i][j]);
	}
	
	/**
	 * <p>Loads genotype from given input stream.</p>
	 * @throws IOException 
	 */
	protected void load(InputStream in) throws IOException
	{
		for ( int i=0; i<NUMGENS; i++)
			for ( int j=0; j<weights[i].length; j++ )
				weights[i][j] = (byte)in.read();
	}
	
	
	
	
	
	/* PRIVATE AUXILIARY FUNCTIONS */
	private void mutateGene(int i)
	{
		for ( int j=0; j<weights[i].length; j++ )
			weights[i][j] += r.nextInt(2*MAXMUTATION+1)-MAXMUTATION-1;
	}
	
	private boolean geneActive(int i, Field[][] neighbourhood)
	{
		if ( i == 0 && neighbourhood[0][1] == Field.APPLE )
			return true;
		//TODO: Complete
		
		return false;
	}
}

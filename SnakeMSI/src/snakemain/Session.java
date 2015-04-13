package snakemain;

/**
 * 
 * @author Filip
 */
public class Session
{
	/* VARIABLES */
	private Generation currentGeneration = null;
	
	/* METHODS */
	
	public void init(int populationSize)
	{
		currentGeneration = new Generation();
		currentGeneration.init(populationSize);
	}
}

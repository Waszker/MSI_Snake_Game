package snakemain;

/**
 * 
 * @author Filip
 */
public class Session
{
	/* VARIABLES */
	private Generation currentGeneration = new Generation();
	
	/* METHODS */
	
	public void init(int populationSize)
	{
		currentGeneration.init(populationSize);
	}
	
	public void save(String file)
	{
		
	}
	
	public void load(String file)
	{
		
	}
	
	public void singleCycle(/*Display display*/)
	{
		/*
		 * zainicjuj symulacje;
		 * odpal symulacje (display);
		 * semaphore.acquile(populationSize);
		 * currentGeneration.evaluate();
		 */
	}
}

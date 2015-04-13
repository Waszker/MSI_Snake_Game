package snake;

/**
 * <p>
 * Snake class. Provides: decisions and score counting.
 * </p>
 * @author Filip
 */
public class Snake
{
	/* ENUMS */
	public enum Movement { FORWARD, LEFT, RIGHT };
	
	/* VARIABLES */
	private Genotype genotype = null;
	private int score = 0;
	
	/* METHODS */
	
	public Snake()
	{
		
	}
	
	/**
	 * <p>Sets score to 0.</p>
	 */
	public void resetScore()
	{
		score = 0;
	}
	
	/**
	 * <p>Getter for score.</p>
	 */
	public int getScore()
	{
		return score;
	}
	
	/**
	 * <p>
	 * Performs snake decision process depending on genotype.
	 * For each situation which occures, weights for snake actions are sumed up.
	 * Result action (Movement) is the one, which has the greatest weight sum.
	 * Then checks if the snake hit an obstacle - then sets dead=true.
	 * Otherwise increases the score.
	 * </p>
	 * @param neighbourhood 9x9 array of map bytes oriented according to snake direction
	 * @return the result action
	 */
	public Movement move(byte[][] neighbourhood)
	{
		//TODO: Complete
		return Movement.FORWARD;
	}
	
	/**
	 * <p>Setter for genotype.</p>
	 */
	public void setGenotype(Genotype g)
	{
		genotype = g;
	}
	
	/**
	 * <p>Getter for genotype.</p>
	 */
	public Genotype getGenotype()
	{
		return genotype;
	}
}

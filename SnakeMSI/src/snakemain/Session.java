package snakemain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import snake.Snake;

/**
 * <p>
 * Class providing interface to manage generation evolution. Provides
 * initializing new generation, saving and loading evolution state and
 * performing evolution process itself.
 * </p>
 * @author Filip
 */
public class Session
{
	/* VARIABLES */
	private Generation currentGeneration = new Generation();
	
	/* METHODS */
	
	/**
	 * <p>Method initializes first generation randomly, approximately equal to zero.</p>
	 * @param populationSize number of snakes in a single generation
	 */
	public void init(int populationSize)
	{
		currentGeneration.init(populationSize);
	}
	
	/**
	 * <p>Saves current generation (with every snake genotype) to file.</p>
	 * @param file path to target file
	 * @throws IOException
	 */
	public void save(String file) throws IOException
	{
		File output = new File(file);
		if ( !output.exists() )
			output.createNewFile();
		
		OutputStream out = new FileOutputStream(output);
		Snake[] snakes = currentGeneration.getPopulation();
		
		for ( Snake s : snakes )
			s.save(out);
		
		out.close();
	}
	
	/**
	 * <p>Loads generation state from file.</p>
	 * @param file path to target file
	 * @throws IOException
	 */
	public void load(String file) throws IOException
	{
		File input = new File(file);
		InputStream in = new FileInputStream(input);
		List<Snake> listOfSnakes = new LinkedList<Snake>();
		
		while ( in.available()>0 )
		{
			Snake next = new Snake();
			next.load(in);
			listOfSnakes.add(next);
		}
		
		currentGeneration.setPopulation((Snake[])listOfSnakes.toArray());
		in.close();
	}
	
	/**
	 * <p>Performs single generation evolution. If display is not null, it provides preview online.</p>
	 * @param display drawing output, can be null
	 * @param simulationSpeed speed of the simulation preview, ignored when display is null
	 */
	public void singleCycle(/*Display display, double simulationSpeed*/)
	{
		/*
		 * zainicjuj symulacje;
		 * odpal symulacje (display);
		 * semaphore.acquile(populationSize); //poczekaj na wszystkie symulacje
		 * currentGeneration.evaluate();
		 */
	}
}

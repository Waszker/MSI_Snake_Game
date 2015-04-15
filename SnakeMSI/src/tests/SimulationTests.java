package tests;

import org.junit.Test;

import snake.Snake;
import snakemain.Simulation;
import snakemain.Simulation.Field;

public class SimulationTests
{
	private void render(Field[][] map)
	{
		final int W = map.length;
		final int H = map[0].length;
		
		for ( int y=0; y<H; y++ )
		{
			for ( int x=0; x<W; x++ )
			{
				char c = '.';
				if ( map[x][y] == Field.APPLE )
					c = 'o';
				else if ( map[x][y] == Field.WALL )
					c = 'X';
				else if ( map[x][y] == Field.SNAKE )
					c = '#';
				
				System.out.print(c);
			}
			System.out.println();
		}
	}

	@Test
	public void simpleGameRulesTestWithPreview() throws InterruptedException
	{
		Simulation s = new Simulation();
		Snake snake = new Snake();
		
		snake.init();
		s.resetSimulation(snake, 10);
		
		while ( !s.isSnakeDead() )
		{
			render(s.getMap());
			s.singleStep();
			Thread.sleep(300);
		}
	}

}

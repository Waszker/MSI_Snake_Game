package gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import snakemain.Simulation;

/**
 * <p>
 * JPanel wrapper that makes it possible to display snake actions.
 * </p>
 * 
 * @author Piotr Waszkiewicz
 * @version 1.0
 * 
 */
public class Display extends JPanel
{
	/******************/
	/* VARIABLES */
	/******************/
	private static final long serialVersionUID = -1461500059364328660L;
	Simulation.Field map[][];

	/******************/
	/* METHODS */
	/******************/
	public Display(Color bckCOlor)
	{
		super();
		this.setBorder(new BevelBorder(BevelBorder.RAISED));
		this.setBackground(bckCOlor);
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);

		if (null != map)
		{
			int x = 0, y = 0;
			int a = this.getWidth() / map.length, b = this.getHeight()
					/ map[0].length;

			for (int i = 0; i < map.length; i++, y += b)
			{
				x = 0;
				for (int j = 0; j < map[i].length; j++, x += a)
					switch (map[i][j])
					{
						case EMPTY:
							g.setColor(Color.DARK_GRAY);
							g.fillRect(x, y, a, b);
							break;

						case APPLE:
							g.setColor(Color.RED);
							g.fillRect(x, y, a, b);
							break;

						case SNAKE:
							g.setColor(Color.GREEN);
							g.fillRect(x, y, a, b);
							break;

						case WALL:
							g.setColor(Color.BLACK);
							g.fillRect(x, y, a, b);
							break;
					}
			}
		}
	}

	/**
	 * <p>
	 * Updates map view.
	 * </p>
	 * 
	 * @param map
	 */
	public void updateView(Simulation.Field map[][])
	{
		this.map = map;
		this.repaint();
	}
}

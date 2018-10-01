package ashih.swingy.view;

import ashih.swingy.model.Entity;
import ashih.swingy.model.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

public class GraphicsMapWindow extends MapWindow
{
	private final JLabel[][] imageLabels;		// [ y ][ x ]

	public GraphicsMapWindow(Map map, boolean isVisible)
	{
		super(map);
		this.imageLabels = new JLabel[this.VIEWPORT_HEIGHT][this.VIEWPORT_WIDTH];
		JPanel panel = new JPanel(new GridLayout(this.VIEWPORT_HEIGHT,this.VIEWPORT_WIDTH));
		this.add(panel);

		for (int y = 0; y < this.VIEWPORT_HEIGHT; y++)
			for (int x = 0; x < this.VIEWPORT_WIDTH; x++)
				panel.add(this.imageLabels[y][x] = new JLabel());

		this.pack();
		this.setVisible(isVisible);
		this.refresh();
	}

	@Override
	public void handleKeys(KeyListener listener)
	{
		this.addKeyListener(listener);
	}

	@Override
	public void refresh()
	{
		for (int y = 0; y < this.VIEWPORT_HEIGHT; y++)
		{
			for (int x = 0; x < this.VIEWPORT_WIDTH; x++)
			{
				int posX = x + this.viewportUpperLeftPosX;
				int posY = y + this.viewportUpperLeftPosY;
				Icon image;

				if (this.map.isValidPosition(posX, posY))
					image = this.map.getEntity(posX, posY).getImage();
				else
					image = Entity.OUT_OF_MAP_BOUNDARY_IMAGE;
				if (image != null)
					this.imageLabels[y][x].setIcon(image);
			}
		}
		this.pack();
	}
}

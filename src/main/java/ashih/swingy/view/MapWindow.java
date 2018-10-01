package ashih.swingy.view;

import ashih.swingy.model.Map;

import javax.swing.*;
import java.awt.event.KeyListener;

public abstract class MapWindow extends JFrame
{
	protected final int VIEWPORT_WIDTH = 30;
	protected final int VIEWPORT_HEIGHT = 20;

	protected int viewportUpperLeftPosX;
	protected int viewportUpperLeftPosY;
	protected Map map;

	public MapWindow(Map map)
	{
		this.map = map;
		this.initializeViewport();
		this.setTitle("Map Level " + this.map.getMapLevel());
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void initializeViewport()
	{
		this.viewportUpperLeftPosX = 0 - (this.VIEWPORT_WIDTH / 2 - this.map.getMapSize() / 2);
		this.viewportUpperLeftPosY = 0 - (this.VIEWPORT_HEIGHT / 2 - this.map.getMapSize() / 2);
	}

	public int getViewportUpperLeftPosX() { return (this.viewportUpperLeftPosX); }
	public int getViewportUpperLeftPosY() { return (this.viewportUpperLeftPosY); }

	public void setViewPortUpperLeftPos(int posX, int posY)
	{
		this.viewportUpperLeftPosX = posX;
		this.viewportUpperLeftPosY = posY;
	}

	public void setMap(Map map)
	{
		this.map = map;
		this.initializeViewport();
		this.setTitle("Map Level " + this.map.getMapLevel());
	}

	public void toggleVisible()
	{
		this.setVisible(!this.isVisible());
		this.setLocation(0, 0);
	}

	public abstract void handleKeys(KeyListener listener);

	public abstract void refresh();
}

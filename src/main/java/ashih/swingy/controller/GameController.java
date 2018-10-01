package ashih.swingy.controller;

import ashih.swingy.model.GameManager;
import ashih.swingy.model.Hero;
import ashih.swingy.view.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameController implements KeyListener
{
	private boolean isGameOver;
	private final GameManager gameManager;
	private final HeroInfoWindow heroInfoWindow;
	private final MapWindow consoleMapWindow;
	private final MapWindow graphicsMapWindow;

	public GameController(Hero hero, boolean isStartingInConsoleMode)
	{
		this.isGameOver = false;
		this.gameManager = new GameManager(this, hero);

		this.heroInfoWindow = new HeroInfoWindow(this.gameManager.getHero());
		this.consoleMapWindow = new ConsoleMapWindow(this.gameManager.getMap(), isStartingInConsoleMode);
		this.graphicsMapWindow = new GraphicsMapWindow(this.gameManager.getMap(), !isStartingInConsoleMode);

		this.graphicsMapWindow.handleKeys(this);
		this.consoleMapWindow.handleKeys(this);
	}

	public void keyTyped(KeyEvent e) { }

	public void keyReleased(KeyEvent e) { }

	public void keyPressed(KeyEvent e)
	{
		if (this.isGameOver) return;

		switch (e.getKeyCode())
		{
			case KeyEvent.VK_ESCAPE:
				System.exit(0);
				break;
			case KeyEvent.VK_UP:
				this.gameManager.movePlayerUp();
				break;
			case KeyEvent.VK_DOWN:
				this.gameManager.movePlayerDown();
				break;
			case KeyEvent.VK_LEFT:
				this.gameManager.movePlayerLeft();
				break;
			case KeyEvent.VK_RIGHT:
				this.gameManager.movePlayerRight();
				break;
			case KeyEvent.VK_NUMPAD8:
				this.moveViewportUp();
				break;
			case KeyEvent.VK_NUMPAD2:
				this.moveViewportDown();
				break;
			case KeyEvent.VK_NUMPAD4:
				this.moveViewportLeft();
				break;
			case KeyEvent.VK_NUMPAD6:
				this.moveViewportRight();
				break;
			case KeyEvent.VK_S:
				this.swapMapWindows();
				break;
			default:
				return;
		}
		this.refreshDisplay();
	}

	public void refreshDisplay()
	{
		this.heroInfoWindow.refresh();
		this.consoleMapWindow.refresh();
		this.graphicsMapWindow.refresh();
	}

	public void setGameOver()
	{
		this.isGameOver = true;
		new DeathWindow();
	}

	public void updateMapWindows()
	{
		this.consoleMapWindow.setMap(this.gameManager.getMap());
		this.graphicsMapWindow.setMap(this.gameManager.getMap());
	}

	private void swapMapWindows()
	{
		this.consoleMapWindow.toggleVisible();
		this.graphicsMapWindow.toggleVisible();
	}

	private void moveViewportUp()
	{
		this.consoleMapWindow.setViewPortUpperLeftPos(this.consoleMapWindow.getViewportUpperLeftPosX(),
				this.consoleMapWindow.getViewportUpperLeftPosY() - 1);
		this.graphicsMapWindow.setViewPortUpperLeftPos(this.graphicsMapWindow.getViewportUpperLeftPosX(),
				this.graphicsMapWindow.getViewportUpperLeftPosY() - 1);
	}

	private void moveViewportDown()
	{
		this.consoleMapWindow.setViewPortUpperLeftPos(this.consoleMapWindow.getViewportUpperLeftPosX(),
				this.consoleMapWindow.getViewportUpperLeftPosY() + 1);
		this.graphicsMapWindow.setViewPortUpperLeftPos(this.graphicsMapWindow.getViewportUpperLeftPosX(),
				this.graphicsMapWindow.getViewportUpperLeftPosY() + 1);
	}

	private void moveViewportLeft()
	{
		this.consoleMapWindow.setViewPortUpperLeftPos(this.consoleMapWindow.getViewportUpperLeftPosX() - 1,
				this.consoleMapWindow.getViewportUpperLeftPosY());
		this.graphicsMapWindow.setViewPortUpperLeftPos(this.graphicsMapWindow.getViewportUpperLeftPosX() - 1,
				this.graphicsMapWindow.getViewportUpperLeftPosY());
	}

	private void moveViewportRight()
	{
		this.consoleMapWindow.setViewPortUpperLeftPos(this.consoleMapWindow.getViewportUpperLeftPosX() + 1,
				this.consoleMapWindow.getViewportUpperLeftPosY());
		this.graphicsMapWindow.setViewPortUpperLeftPos(this.graphicsMapWindow.getViewportUpperLeftPosX() + 1,
				this.graphicsMapWindow.getViewportUpperLeftPosY());
	}

}

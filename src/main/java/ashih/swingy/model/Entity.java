package ashih.swingy.model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Random;

public abstract class Entity
{
	public static final Icon OUT_OF_MAP_BOUNDARY_IMAGE = Entity.loadImageFromFile("images/wall.png");
	protected static final Random RNG = new Random();

	protected static Icon loadImageFromFile(String filename)
	{
		try
		{
			String pathName = ResourceManager.getInstance().getBasePath() + filename;
			BufferedImage deathImage = ImageIO.read(new URL(pathName));
			Image scaledImage = deathImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			return (new ImageIcon(scaledImage));
		}
		catch (Exception ex)
		{
			System.out.println(ex);
			return (null);
		}
	}

	protected int positionX;
	protected int positionY;

	/* Needed for some subclasses that don't know or don't care about position */
	public Entity() { }

	public Entity(int posX, int posY)
	{
		this.positionX = posX;
		this.positionY = posY;
	}

	public int getPositionX() { return (this.positionX); }
	public int getPositionY() { return (this.positionY); }

	public void setPosition(int posX, int posY)
	{
		this.positionX = posX;
		this.positionY = posY;
	}

	public boolean isEmpty() { return (false); }

	public abstract char getChar();

	public abstract Icon getImage();

}

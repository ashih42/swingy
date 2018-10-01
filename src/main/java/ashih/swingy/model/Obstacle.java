package ashih.swingy.model;

import javax.swing.*;
import java.util.ArrayList;

public class Obstacle extends Entity
{
	private static final ArrayList<Icon> IMAGES = new ArrayList<Icon>();
	static
	{
		Obstacle.IMAGES.add(Entity.loadImageFromFile("images/woman_facepalm.png"));
		Obstacle.IMAGES.add(Entity.loadImageFromFile("images/woman_no.png"));
		Obstacle.IMAGES.add(Entity.loadImageFromFile("images/woman_ok.png"));
		Obstacle.IMAGES.add(Entity.loadImageFromFile("images/woman_pouting.png"));
		Obstacle.IMAGES.add(Entity.loadImageFromFile("images/woman_raising_hand.png"));
		Obstacle.IMAGES.add(Entity.loadImageFromFile("images/woman_shrug.png"));
		Obstacle.IMAGES.add(Entity.loadImageFromFile("images/woman_tipping_hand.png"));
	}

	private final Icon image;

	public Obstacle()
	{
		int imageIndex = Entity.RNG.nextInt(Obstacle.IMAGES.size());
		this.image = Obstacle.IMAGES.get(imageIndex);
	}

	@Override
	public char getChar() { return ('O'); }

	@Override
	public Icon getImage() { return (this.image); }

}

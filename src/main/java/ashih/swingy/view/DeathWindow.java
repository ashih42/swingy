package ashih.swingy.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DeathWindow extends JFrame
{
	public DeathWindow()
	{
		this.setTitle("YOU DIEDED");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		this.add(panel);
		try
		{
			BufferedImage deathImage = ImageIO.read(new File("images/death.jpg"));
			JLabel imageLabel = new JLabel(new ImageIcon(deathImage));
			panel.add(imageLabel);
		}
		catch (IOException ex)
		{
			System.out.println(ex);
		}
		this.pack();
		this.setVisible(true);
	}

}

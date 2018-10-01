package ashih.swingy.view;

import ashih.swingy.model.Hero;

import javax.swing.*;
import java.awt.*;

public class HeroInfoWindow extends JFrame
{
	private Hero hero;
	private final JTextArea textArea;

	public HeroInfoWindow(Hero hero)
	{
		this.hero = hero;
		this.setTitle("Hero Info");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		JPanel panel = new JPanel();
		this.add(panel);
		this.textArea = new JTextArea(15, 71);
		this.textArea.setFont(new Font("monospaced", Font.PLAIN, 12));
		this.textArea.setEditable(false);
		panel.add(this.textArea);
		panel.add(new JScrollPane(this.textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));

		this.pack();
		this.setVisible(true);
		this.refresh();

		// Position window at bottom left corner
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		int xPos = 0;
		int yPos = dim.height - this.getHeight();
		this.setLocation(xPos, yPos);
	}

	public void setHero(Hero hero)
	{
		this.hero = hero;
		this.refresh();
	}

	public void refresh()
	{
		if (this.hero == null)
			this.textArea.setText("");
		else
			this.textArea.setText(this.hero.getFullStats());
	}

}

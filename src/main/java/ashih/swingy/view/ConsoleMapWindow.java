package ashih.swingy.view;

import ashih.swingy.model.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

public class ConsoleMapWindow extends MapWindow
{
	private final JTextArea textArea;
	private final StringBuilder stringBuilder;

	public ConsoleMapWindow(Map map, boolean isVisible)
	{
		super(map);
		this.stringBuilder = new StringBuilder();
		JPanel panel = new JPanel();
		this.add(panel);

		this.textArea = new JTextArea(this.VIEWPORT_HEIGHT, this.VIEWPORT_WIDTH);
		this.textArea.setFont(new Font("monospaced", Font.PLAIN, 40));
		this.textArea.setEditable(false);
		panel.add(this.textArea);

		this.pack();
		this.setVisible(isVisible);
		this.refresh();
	}

	@Override
	public void handleKeys(KeyListener listener)
	{
		this.textArea.addKeyListener(listener);
	}

	@Override
	public void refresh()
	{
		this.stringBuilder.setLength(0);
		for (int y = 0; y < this.VIEWPORT_HEIGHT; y++)
		{
			for (int x = 0; x < this.VIEWPORT_WIDTH; x++)
			{
				int posX = x + this.viewportUpperLeftPosX;
				int posY = y + this.viewportUpperLeftPosY;

				if (this.map.isValidPosition(posX, posY))
					this.stringBuilder.append(this.map.getEntity(posX, posY).getChar());
				else if ((posY == -1 || posY == this.map.getMapSize()) && (posX == -1 || posX == this.map.getMapSize()))
					this.stringBuilder.append('+');
				else if ((posY == -1 || posY == this.map.getMapSize()) && 0 <= posX && posX < this.map.getMapSize())
					this.stringBuilder.append('-');
				else if ((posX == -1 || posX == this.map.getMapSize()) && 0 <= posY && posY < this.map.getMapSize())
					this.stringBuilder.append('|');
				else
					this.stringBuilder.append(' ');
			}
			this.stringBuilder.append('\n');
		}
		this.textArea.setText(this.stringBuilder.toString());
	}
}

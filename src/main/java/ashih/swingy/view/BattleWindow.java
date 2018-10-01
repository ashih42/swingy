package ashih.swingy.view;

import ashih.swingy.controller.BattleController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BattleWindow extends JFrame
{
	private final BattleController battleController;

	private final JButton fightButton;
	private final JButton fleeButton;

	public BattleWindow(BattleController battleController)
	{
		this.battleController = battleController;
		this.setTitle("Battle Menu");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		this.add(panel);

		JTextArea enemyTextArea = new JTextArea(15, 72);
		enemyTextArea.setFont(new Font("monospaced", Font.PLAIN, 12));
		enemyTextArea.setEditable(false);
		enemyTextArea.setText(this.battleController.getEnemy().getFullStats());
		panel.add(enemyTextArea, BorderLayout.CENTER);
		panel.add(new JScrollPane(enemyTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));

		JPanel buttonRowPanel = new JPanel();
		buttonRowPanel.setLayout(new GridLayout(0, 2));
		panel.add(buttonRowPanel, BorderLayout.PAGE_END);

		this.fightButton = new JButton("Fight");
		buttonRowPanel.add(this.fightButton);
		this.fightButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				BattleWindow.this.chooseToFight();
			}
		});

		this.fleeButton = new JButton("Flee");
		buttonRowPanel.add(this.fleeButton);
		this.fleeButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				BattleWindow.this.chooseToFlee();
			}
		});

		this.pack();
		this.setVisible(true);
		this.fightButton.requestFocus();
	}

	private void chooseToFight()
	{
		this.battleController.chooseToFight();
		this.dispose();
	}

	private void chooseToFlee()
	{
		this.battleController.chooseToFlee();
		this.dispose();
	}

}

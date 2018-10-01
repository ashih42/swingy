package ashih.swingy.view;

import ashih.swingy.model.Hero;
import ashih.swingy.model.HeroFactory;
import ashih.swingy.model.ValidationManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HeroCreationWindow extends JFrame
{
	private final HeroSelectionWindow heroSelectionWindow;

	private Hero hero;
	private final JTextField heroNameTextField;
	private final JButton generateButton;
	private final JButton confirmButton;

	public HeroCreationWindow(HeroSelectionWindow heroSelectionWindow)
	{
		this.heroSelectionWindow = heroSelectionWindow;

		this.setTitle("Hero Creation");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel();
		this.add(panel);

		JLabel heroNameLabel = new JLabel("Hero Name: ");
		panel.add(heroNameLabel);

		this.heroNameTextField = new JTextField("DefaultName", 20);
		panel.add(this.heroNameTextField);

		this.generateButton = new JButton("Generate");
		panel.add(this.generateButton);
		this.generateButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				HeroCreationWindow.this.generateNewHero();
			}
		});

		this.confirmButton = new JButton("Confirm");
		panel.add(this.confirmButton);
		this.confirmButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				HeroCreationWindow.this.confirmHero();
			}
		});

		this.pack();
		this.setVisible(true);
		this.generateNewHero();
	}

	private void generateNewHero()
	{
		String heroName = this.heroNameTextField.getText();
		Hero tempHero = HeroFactory.getInstance().createNewHero(heroName);

		if (ValidationManager.getInstance().validateHero(tempHero))
			this.hero = tempHero;
		else
			this.hero = null;
		this.heroSelectionWindow.getHeroInfoWindow().setHero(this.hero);
	}

	private void confirmHero()
	{
		if (this.hero == null)
			System.out.println("Error: you must create a valid Hero first to confirm");
		else
		{
			this.heroSelectionWindow.addHero(this.hero);
			this.heroSelectionWindow.getHeroInfoWindow().setHero(null);
			this.dispose();
		}
	}
}

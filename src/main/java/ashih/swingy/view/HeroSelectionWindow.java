package ashih.swingy.view;

import ashih.swingy.controller.HeroSelectionController;
import ashih.swingy.model.Hero;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HeroSelectionWindow extends JFrame
{
	private final HeroSelectionController heroSelectionController;

	private final JList heroList;
	private final DefaultListModel heroListModel;
	private final JButton createHeroButton;
	private final JButton deleteHeroButton;
	private final JButton startGameButton;

	private HeroCreationWindow heroCreationWindow;
	private final HeroInfoWindow heroInfoWindow;

	public HeroSelectionWindow(HeroSelectionController heroSelectionController)
	{
		this.heroSelectionController = heroSelectionController;
		this.heroInfoWindow = new HeroInfoWindow(null);
		this.heroInfoWindow.setLocation(0, 249);

		this.setTitle("Hero Selection");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		this.add(panel);

		JPanel buttonRowPanel = new JPanel();
		buttonRowPanel.setLayout(new GridLayout(0, 3));
		panel.add(buttonRowPanel, BorderLayout.PAGE_END);

		this.createHeroButton = new JButton("New Hero");
		buttonRowPanel.add(this.createHeroButton);
		this.createHeroButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				HeroSelectionWindow.this.openHeroCreationWindow();
			}
		});

		this.deleteHeroButton = new JButton("Delete Hero");
		buttonRowPanel.add(this.deleteHeroButton);
		this.deleteHeroButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				HeroSelectionWindow.this.deleteSelectedHero();
			}
		});

		this.startGameButton = new JButton("Start Game");
		buttonRowPanel.add(this.startGameButton);
		this.startGameButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Hero selectedHero = (Hero)HeroSelectionWindow.this.heroList.getSelectedValue();
				HeroSelectionWindow.this.startGame(selectedHero);
			}
		});

		JPanel textPanel = new JPanel();
		textPanel.setLayout(new FlowLayout());
		panel.add(textPanel, BorderLayout.CENTER);

		this.heroListModel = new DefaultListModel();
		for (Hero hero : this.heroSelectionController.getHeroes())
			this.heroListModel.addElement(hero);

		this.heroList = new JList(this.heroListModel);
		this.heroList.setFixedCellWidth(500);
		this.heroList.setFixedCellHeight(20);
		this.heroList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		textPanel.add(this.heroList);
		this.heroList.addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent e)
			{
				Hero selectedHero = (Hero)HeroSelectionWindow.this.heroList.getSelectedValue();
				HeroSelectionWindow.this.heroInfoWindow.setHero(selectedHero);
			}
		});

		JScrollPane scroller = new JScrollPane(this.heroList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		textPanel.add(scroller);

		this.pack();
		this.setVisible(true);
	}

	private void openHeroCreationWindow()
	{
		if (this.heroCreationWindow == null || !this.heroCreationWindow.isDisplayable())
			this.heroCreationWindow = new HeroCreationWindow(this);
	}

	private void deleteSelectedHero()
	{
		int selectedIndex = this.heroList.getSelectedIndex();

		if (selectedIndex == -1)
			System.out.println("Error: Please select a Hero to delete");
		else
		{
			this.heroListModel.removeElementAt(selectedIndex);
			this.heroInfoWindow.setHero(null);
			this.heroSelectionController.deleteHero(selectedIndex);
		}
	}

	private void startGame(Hero hero)
	{
		if (hero == null)
			System.out.println("Error: Please select a Hero to start the game");
		else
		{
			this.heroInfoWindow.dispose();
			this.dispose();
			this.heroSelectionController.startGame(hero);
		}
	}

	public void addHero(Hero hero)
	{
		this.heroSelectionController.addHero(hero);
		this.heroListModel.addElement(hero);
	}

	public HeroInfoWindow getHeroInfoWindow()
	{
		return (this.heroInfoWindow);
	}

}

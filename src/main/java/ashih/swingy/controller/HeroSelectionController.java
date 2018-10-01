package ashih.swingy.controller;

import ashih.swingy.model.DatabaseManager;
import ashih.swingy.model.Hero;
import ashih.swingy.view.HeroSelectionWindow;

import java.util.ArrayList;

public class HeroSelectionController
{
	private final boolean isStartingInConsoleMode;
	private final ArrayList<Hero> heroArrayList;

	public HeroSelectionController(boolean isStartingInConsoleMode)
	{
		this.isStartingInConsoleMode = isStartingInConsoleMode;
		this.heroArrayList = DatabaseManager.getInstance().loadAllHeroes();
		new HeroSelectionWindow(this);
	}

	public ArrayList<Hero> getHeroes()
	{
		return (this.heroArrayList);
	}

	public void addHero(Hero hero)
	{
		DatabaseManager.getInstance().insertNewHero(hero);
		this.heroArrayList.add(hero);
	}

	public void deleteHero(int heroIndex)
	{
		Hero hero = this.heroArrayList.get(heroIndex);
		this.heroArrayList.remove(heroIndex);
		DatabaseManager.getInstance().deleteHero(hero);
	}

	public void startGame(Hero hero)
	{
		new GameController(hero, this.isStartingInConsoleMode);
	}

	public static void main(String[] args)
	{
		final String USAGE_MESSAGE = "usage: java -jar swingy.jar console|gui";
		boolean isStartingInConsoleMode = false;

		if (args.length != 1)
		{
			System.out.println(USAGE_MESSAGE);
			System.exit(-1);
		}
		if (args[0].equals("console"))
			isStartingInConsoleMode = true;
		else if (args[0].equals("gui"))
			isStartingInConsoleMode = false;
		else
		{
			System.out.println(USAGE_MESSAGE);
			System.exit(-1);
		}

		System.out.println("[Controls]\n" +
				"ARROW KEYS\t\tMove hero, attack enemy, pick up item\n" +
				"NUMPAD 8,2,4,6\t\tMove visible area in map window\n" +
				"S\t\t\tSwitch console/GUI map windows\n"
		);
		new HeroSelectionController(isStartingInConsoleMode);
	}
}

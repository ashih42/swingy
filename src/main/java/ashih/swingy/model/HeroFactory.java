package ashih.swingy.model;

import java.util.ArrayList;
import java.util.Random;

public class HeroFactory
{
	private static final HeroFactory instance = new HeroFactory();

	public static HeroFactory getInstance()
	{
		return (HeroFactory.instance);
	}

	private final ArrayList<String> HERO_CLASS_LIST;
	private final Random RNG;

	private HeroFactory()
	{
		this.HERO_CLASS_LIST = new ArrayList<String>(Hero.CLASS_TO_IMAGE_MAP.keySet());
		this.RNG = new Random();
	}

	public Hero createNewHero(String heroName)
	{
		int heroClassIndex = this.RNG.nextInt(this.HERO_CLASS_LIST.size());
		String heroClass = this.HERO_CLASS_LIST.get(heroClassIndex);
		int attackPoints = 10;
		int defensePoints = 10;
		int healthPoints = 10;

		int points = 5;
		while (points >= 0)
		{
			int randValue = this.RNG.nextInt(3);
			if (randValue == 1)
				attackPoints += 1;
			else if (randValue == 2)
				defensePoints += 1;
			else
				healthPoints += 2;
			points--;
		}
		return (new Hero(heroName, heroClass, attackPoints, defensePoints, healthPoints));
	}
}

package ashih.swingy.model;

import java.util.Random;

public class EquipmentFactory
{
	private static final EquipmentFactory instance = new EquipmentFactory();

	public static EquipmentFactory getInstance()
	{
		return (EquipmentFactory.instance);
	}

	private final Random RNG;

	private EquipmentFactory()
	{
		this.RNG = new Random();
	}

	public Equipment createRandomEquipment(int level)
	{
		int attackModifier = 0;
		int defenseModifier = 0;
		int healthModifier = 0;

		int attackMultiplier = (this.RNG.nextInt(2) ==  1) ? 1 : -1;
		int defenseMultiplier = (this.RNG.nextInt(2) == 1) ? 1 : -1;
		int healthMultiplier = (this.RNG.nextInt(2) == 1) ? 1 : -1;

		int points = level * 5;
		while (points > 0)
		{
			int randValue = this.RNG.nextInt(3);
			if (randValue == 1)
				attackModifier += 1 * attackMultiplier;
			else if (randValue == 2)
				defenseModifier += 1 * defenseMultiplier;
			else
				healthModifier += 2 * healthMultiplier;
			points--;
		}

		int randValue = this.RNG.nextInt(3);
		if (randValue == 1)
			return (new Weapon("Kitchen Knife", attackModifier, defenseModifier, healthModifier));
		else if (randValue == 2)
			return (new Armor("Bikini Armor", attackModifier, defenseModifier, healthModifier));
		else
			return (new Helmet("Top Hat", attackModifier, defenseModifier, healthModifier));
	}
}

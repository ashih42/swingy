package ashih.swingy.model;

import java.util.ArrayList;
import java.util.Random;

public class EnemyFactory
{
	private static final EnemyFactory instance = new EnemyFactory();

	public static EnemyFactory getInstance()
	{
		return (EnemyFactory.instance);
	}

	private final ArrayList<String> ENEMY_NAME_LIST;
	private final Random RNG;

	private EnemyFactory()
	{
		this.ENEMY_NAME_LIST = new ArrayList<String>(Enemy.NAME_TO_IMAGE_MAP.keySet());
		this.RNG = new Random();
	}

	public Enemy createRandomEnemy(int enemyLevel)
	{
		int enemyNameIndex = this.RNG.nextInt(this.ENEMY_NAME_LIST.size());
		String enemyName = this.ENEMY_NAME_LIST.get(enemyNameIndex);

		int statPoints = enemyLevel * 5;
		int attackPoints = 0;
		int defensePoints = 0;
		int healthPoints = 0;

		while (statPoints > 0)
		{
			int randValue = this.RNG.nextInt(3);
			if (randValue == 1)
				attackPoints++;
			else if (randValue == 2)
				defensePoints++;
			else
				healthPoints += 2;
			statPoints--;
		}
		return (new Enemy(enemyName, enemyLevel, attackPoints, defensePoints, healthPoints));
	}

}

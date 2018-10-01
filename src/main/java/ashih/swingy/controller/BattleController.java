package ashih.swingy.controller;

import ashih.swingy.model.Enemy;
import ashih.swingy.model.GameManager;
import ashih.swingy.view.BattleWindow;

import java.util.Random;

public class BattleController
{
	private final GameManager gameManager;
	private final Enemy enemy;
	private final Random RNG;

	public BattleController(GameManager gameManager, Enemy enemy)
	{
		this.gameManager = gameManager;
		this.enemy = enemy;
		this.RNG = new Random();
		new BattleWindow(this);
	}

	public Enemy getEnemy() { return (this.enemy); }

	public void chooseToFight()
	{
		System.out.println("You chose to FIGHT");
		this.gameManager.handleHeroDecisionToFight(this.enemy);
	}

	public void chooseToFlee()
	{
		if (this.RNG.nextInt(2) == 1)
		{
			System.out.println("You FLED successfully");
			this.gameManager.handleHeroDecisionToFight(null);
		}
		else
		{
			System.out.println("You failed to FLEE, and you must FIGHT");
			this.gameManager.handleHeroDecisionToFight(this.enemy);
		}
	}

}

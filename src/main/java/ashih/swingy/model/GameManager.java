package ashih.swingy.model;

import ashih.swingy.controller.BattleController;
import ashih.swingy.controller.GameController;

import java.util.Random;

public class GameManager
{
	private final GameController gameController;
	private final Hero hero;
	private Map map;
	private final Random RNG;
	private BattleController battleController;

	public GameManager(GameController gameController, Hero hero)
	{
		this.gameController = gameController;
		this.hero = hero;
		this.map = new Map(hero);
		RNG = new Random();
		this.battleController = null;
		SoundManager.getInstance().playBGM();
	}

	public Hero getHero() { return (this.hero); }
	public Map getMap() { return (this.map); }

	public void refreshDisplay() { this.gameController.refreshDisplay(); }

	public void movePlayerUp()
	{
		this.tryToMovePlayer(this.hero.getPositionX(), this.hero.getPositionY() - 1);
	}

	public void movePlayerDown()
	{
		this.tryToMovePlayer(this.hero.getPositionX(), this.hero.getPositionY() + 1);
	}

	public void movePlayerLeft()
	{
		this.tryToMovePlayer(this.hero.getPositionX() - 1, this.hero.getPositionY());
	}

	public void movePlayerRight() { this.tryToMovePlayer(this.hero.getPositionX() + 1, this.hero.getPositionY()); }

	private void tryToMovePlayer(int destPosX, int destPosY)
	{
		if (this.battleController != null)
		{
			System.out.println("Error: You must decide to FIGHT or FLEE");
		}
		else
		{
			if (this.map.isValidPosition(destPosX, destPosY))
			{
				this.interactWithPosition(destPosX, destPosY);
			/* If this interaction did not spawn a new BattleController, updateEnemies() NOW.
				Otherwise, updateEnemies() LATER after user makes the battle decision.
			 */
				if (this.battleController == null)
					this.updateEnemies();
			} else
				this.loadNextMap();
		}
	}

	public void handleHeroDecisionToFight(Enemy enemy)
	{
		if (enemy != null)
			this.handleAttack(this.hero, enemy);
		this.updateEnemies();
		this.refreshDisplay();
		this.battleController = null;
	}

	public void updateEnemies()
	{
		if (this.hero.isDead()) return;

		for(Enemy enemy : this.map.getEnemies())
		{
			if (enemy.isDead()) continue;

			int xDisplacementFromHero = this.hero.getPositionX() - enemy.getPositionX();
			int yDisplacementFromHero = this.hero.getPositionY() - enemy.getPositionY();

			int distanceFromHero = Math.abs(xDisplacementFromHero) + Math.abs(yDisplacementFromHero);

			/* If enemy is right next to hero, attack hero */
			if (distanceFromHero == 1)
			{
				this.handleAttack(enemy, this.hero);
			}
			/* If enemy is close enough to hero, move toward hero */
			else if (distanceFromHero <= 5)
			{
				int destPosX = enemy.getPositionX();
				int destPosY = enemy.getPositionY();

				int stepX = (xDisplacementFromHero == 0) ? 0 : xDisplacementFromHero / Math.abs(xDisplacementFromHero);
				int stepY = (yDisplacementFromHero == 0) ? 0 : yDisplacementFromHero / Math.abs(yDisplacementFromHero);

				if (this.RNG.nextInt(2) == 1)
					destPosX += stepX;
				else
					destPosY += stepY;
				if (this.map.getEntity(destPosX, destPosY).isEmpty())
					this.map.updatePosition(destPosX, destPosY, enemy);

				if (this.hero.isDead()) return;
			}
		}
	}

	private void loadNextMap()
	{
		SoundManager.getInstance().playBGM();
		this.hero.setMapLevel(this.hero.getMapLevel() + 1);
		this.map = new Map(hero);
		this.gameController.updateMapWindows();
		DatabaseManager.getInstance().updateHero(this.hero);
	}

	private void interactWithPosition(int destPosX, int destPosY)
	{
		Entity destEntity = this.map.getEntity(destPosX, destPosY);

		if (destEntity.isEmpty())
			this.map.updatePosition(destPosX, destPosY, this.hero);
		else if (destEntity instanceof Enemy)
			this.battleController = new BattleController(this, (Enemy)destEntity);
		else if (destEntity instanceof Equipment)
			this.equipOnHero((Equipment)destEntity);
	}

	private void equipOnHero(Equipment equipment)
	{
		this.map.updatePosition(equipment.getPositionX(), equipment.getPositionY(), this.hero);
		equipment.equip(this.hero);
		System.out.println("You picked up " + equipment);
		SoundManager.getInstance().playEquipSound();
		if (this.hero.isDead())
			this.handleHeroDeath();
	}

	private void handleAttack(ICombat attacker, ICombat defender)
	{
		int damageReceived = defender.receiveDamage(attacker.dealDamage());
		System.out.printf("%s dealt %d damage to %s\n", attacker.getNameWithStats(),
				damageReceived, defender.getNameWithStats());
		if (defender.isDead())
		{
			System.out.printf("%s died!\n", defender.getName());
			attacker.gainExp(defender.giveExp());

			if (defender instanceof Hero)
				this.handleHeroDeath();
			else
				this.handleEnemyDeath((Enemy)defender);
		}
		else
			SoundManager.getInstance().playHurtSound();
	}

	private void handleHeroDeath()
	{
		SoundManager.getInstance().playPlayerDeathSound();
		this.map.removeEntity(this.hero);
		DatabaseManager.getInstance().deleteHero(hero);
		this.gameController.setGameOver();
	}

	private void handleEnemyDeath(Enemy enemy)
	{
		SoundManager.getInstance().playEnemyDeathSound();
		this.map.removeEntity(enemy);
		if (this.RNG.nextInt(10) < 5)
		{
			Equipment equipment = enemy.dropEquipment();
			this.map.updatePosition(enemy.getPositionX(), enemy.getPositionY(), equipment);
		}
	}

}

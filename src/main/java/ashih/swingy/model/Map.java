package ashih.swingy.model;

import java.util.ArrayList;
import java.util.Random;

/*
	 ----------------> x, width
	|
	|
	|
	V
	y, height
 */

public class Map
{
	private final Hero hero;
	private final int mapLevel;
	private int mapSize;
	private Entity[][] entities;		// [ y ] [ x ]
	private final ArrayList<Enemy> enemies;
	private final Random RNG;

	public Map(Hero hero)
	{
		this.hero = hero;
		this.mapLevel = hero.getMapLevel();
		this.enemies = new ArrayList<Enemy>();
		this.RNG = new Random();
		this.initializeMap();
		this.generateObstacles();
		this.generateEnemies();
		this.initializeHeroPosition();
	}

	private void initializeMap()
	{
		this.mapSize = (this.mapLevel - 1) * 5 + 10 - (this.mapLevel % 2);
		this.entities = new Entity[this.mapSize][this.mapSize];

		for (int y = 0; y < this.mapSize; y++)
			for (int x = 0; x < this.mapSize; x++)
				this.entities[y][x] = new EmptyEntity();
	}

	// fill up to 10% of map with obstacles
	private void generateObstacles()
	{
		int count = (int)(this.mapSize * this.mapSize * 0.1);

		for (int i = 0; i < count; i++)
		{
			int posX = this.RNG.nextInt(this.mapSize);
			int posY = this.RNG.nextInt(this.mapSize);
			this.entities[posY][posX] = new Obstacle();
		}
	}

	// fill up to 10% of map with enemies
	private void generateEnemies()
	{
		int count = (int)(this.mapSize * this.mapSize * 0.1);

		for (int i = 0; i < count; i++)
		{
			int posX = this.RNG.nextInt(this.mapSize);
			int posY = this.RNG.nextInt(this.mapSize);
			Enemy enemy = new Enemy("Bad Guy", this.mapLevel, posX, posY);
			this.entities[posY][posX] = enemy;
			this.enemies.add(enemy);
		}
	}

	private void initializeHeroPosition()
	{
		int middlePos = this.mapSize / 2;

		this.entities[middlePos][middlePos] = hero;
		this.hero.setPosition(middlePos, middlePos);
	}

	public int getMapLevel() { return (this.mapLevel); }
	public int getMapSize() { return (this.mapSize); }
	public ArrayList<Enemy> getEnemies() { return (this.enemies); }

	public boolean isValidPosition(int posX, int posY)
	{
		return (0 <= posX && posX < this.mapSize && 0 <= posY && posY < this.mapSize);
	}

	public Entity getEntity(int posX, int posY)
	{
		if (this.isValidPosition(posX, posY))
			return (this.entities[posY][posX]);
		else
			return (null);
	}

	public void updatePosition(int destPosX, int destPosY, Entity entity)
	{
		int sourcePosX = entity.getPositionX();
		int sourcePosY = entity.getPositionY();

		this.entities[sourcePosY][sourcePosX] = new EmptyEntity();
		this.entities[destPosY][destPosX] = entity;
		entity.setPosition(destPosX, destPosY);
	}

	public void removeEntity(Entity entity)
	{
		int posX = entity.getPositionX();
		int posY = entity.getPositionY();

		this.entities[posY][posX] = new EmptyEntity();
	}

}

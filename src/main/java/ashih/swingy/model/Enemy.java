package ashih.swingy.model;

import javax.swing.*;
import java.util.ArrayList;

public class Enemy extends Entity implements ICombat
{
	private static final ArrayList<Icon> IMAGES = new ArrayList<Icon>();
	static
	{
		Enemy.IMAGES.add(Entity.loadImageFromFile("images/burger.png"));
		Enemy.IMAGES.add(Entity.loadImageFromFile("images/burger.png"));
		Enemy.IMAGES.add(Entity.loadImageFromFile("images/french_fries.png"));
		Enemy.IMAGES.add(Entity.loadImageFromFile("images/pizza.png"));
		Enemy.IMAGES.add(Entity.loadImageFromFile("images/hotdog.png"));
		Enemy.IMAGES.add(Entity.loadImageFromFile("images/sandwich.png"));
		Enemy.IMAGES.add(Entity.loadImageFromFile("images/taco.png"));
		Enemy.IMAGES.add(Entity.loadImageFromFile("images/burrito.png"));
	}

	private final String enemyName;
	private final int enemyLevel;
	private int attackPoints;
	private int defensePoints;
	private int currentHealth;
	private int maxHealth;
	private final Icon image;

	private final Equipment equipment;
	private final StringBuilder stringBuilder;

	public Enemy(String enemyName, int enemyLevel, int posX, int posY)
	{
		super(posX, posY);
		this.enemyName = enemyName;
		this.enemyLevel = enemyLevel;
		this.initializeStats();
		this.equipment = EquipmentFactory.getInstance().createRandomEquipment(this.enemyLevel);
		int imageIndex = Entity.RNG.nextInt(Enemy.IMAGES.size());
		this.image = Enemy.IMAGES.get(imageIndex);
		this.stringBuilder = new StringBuilder();
	}

	private void initializeStats()
	{
		int statPoints = this.enemyLevel * 5;
		this.attackPoints = 0;
		this.defensePoints = 0;
		this.maxHealth = 0;

		while (statPoints > 0)
		{
			double probability = Math.random();
			if (probability < 0.3)
				this.attackPoints++;
			else if (probability < 0.6)
				this.defensePoints++;
			else
				this.maxHealth += 2;
			statPoints--;
		}
		this.currentHealth = this.maxHealth;
	}

	public String getFullStats()
	{
		this.stringBuilder.setLength(0);
		this.stringBuilder.append(String.format("%-10s", "Name:"));
		this.stringBuilder.append(this.enemyName);
		this.stringBuilder.append('\n');
		this.stringBuilder.append(String.format("%-10s", "Level:"));
		this.stringBuilder.append(this.enemyLevel);
		this.stringBuilder.append("\n\n");

		this.stringBuilder.append(String.format("%-10s", "Attack:"));
		this.stringBuilder.append(this.attackPoints);
		this.stringBuilder.append('\n');
		this.stringBuilder.append(String.format("%-10s", "Defense:"));
		this.stringBuilder.append(this.defensePoints);
		this.stringBuilder.append('\n');
		this.stringBuilder.append(String.format("%-10s", "Health:"));
		this.stringBuilder.append(String.format("%d / %d",
				this.currentHealth, this.maxHealth));
		this.stringBuilder.append("\n\n");
		return (this.stringBuilder.toString());
	}

	public int dealDamage() { return (this.attackPoints); }

	public int receiveDamage(int damageValue)
	{
		damageValue -= this.defensePoints;
		damageValue = Math.max(0, damageValue);
		this.currentHealth -= damageValue;
		this.currentHealth = Math.max(0, this.currentHealth);
		return (damageValue);
	}

	public int giveExp() { return (this.enemyLevel * 100); }

	public void gainExp(int expValue) { }

	public Equipment dropEquipment(){ return (this.equipment); }

	public boolean isDead() { return (this.currentHealth <= 0); }

	public String getNameWithStats()
	{
		return (String.format("%s (Lv %d, %d/%d HP)", this.enemyName, this.enemyLevel,
				this.currentHealth, this.maxHealth));
	}

	public String getName() { return (this.enemyName); }

	@Override
	public char getChar() { return ('E'); }

	@Override
	public Icon getImage() { return (this.image); }

}

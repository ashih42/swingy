package ashih.swingy.model;

import javax.swing.*;
import java.util.HashMap;

public class Enemy extends Entity implements ICombat
{
	static HashMap<String, Icon> NAME_TO_IMAGE_MAP = new HashMap<String, Icon>();
	static
	{
		Enemy.NAME_TO_IMAGE_MAP.put("Burger", Entity.loadImageFromFile("images/burger.png"));
		Enemy.NAME_TO_IMAGE_MAP.put("French Fries", Entity.loadImageFromFile("images/french_fries.png"));
		Enemy.NAME_TO_IMAGE_MAP.put("Pizza", Entity.loadImageFromFile("images/pizza.png"));
		Enemy.NAME_TO_IMAGE_MAP.put("Hot Dog", Entity.loadImageFromFile("images/hotdog.png"));
		Enemy.NAME_TO_IMAGE_MAP.put("Sandwich", Entity.loadImageFromFile("images/sandwich.png"));
		Enemy.NAME_TO_IMAGE_MAP.put("Taco", Entity.loadImageFromFile("images/taco.png"));
		Enemy.NAME_TO_IMAGE_MAP.put("Burrito", Entity.loadImageFromFile("images/burrito.png"));
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

	public Enemy(String enemyName, int enemyLevel, int attackPoints, int defensePoints, int healthPoints)
	{
		this.enemyName = enemyName;
		this.enemyLevel = enemyLevel;
		this.attackPoints = attackPoints;
		this.defensePoints = defensePoints;
		this.currentHealth = healthPoints;
		this.maxHealth = healthPoints;
		this.image = Enemy.NAME_TO_IMAGE_MAP.get(this.enemyName);
		this.equipment = EquipmentFactory.getInstance().createRandomEquipment(this.enemyLevel);
		this.stringBuilder = new StringBuilder();
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

package ashih.swingy.model;

import javax.swing.*;
import javax.validation.constraints.Size;
import java.util.HashMap;

public class Hero extends Entity implements ICombat
{
	private static Icon DEFAULT_IMAGE = Entity.loadImageFromFile("images/poo.png");
	static HashMap<String, Icon> CLASS_TO_IMAGE_MAP = new HashMap<String, Icon>();
	static
	{
		Hero.CLASS_TO_IMAGE_MAP.put("Gril", Entity.loadImageFromFile("images/aqua.png"));
		Hero.CLASS_TO_IMAGE_MAP.put("Dragon", Entity.loadImageFromFile("images/kanna.png"));
		Hero.CLASS_TO_IMAGE_MAP.put("Trap", Entity.loadImageFromFile("images/trap.jpeg"));
		Hero.CLASS_TO_IMAGE_MAP.put("Robot", Entity.loadImageFromFile("images/bender.png"));
	}

	private int id;

	@Size(min = 1, max = 30, message = "Hero.heroName must be between 1 to 30 characters long")
	private final String heroName;

	private String heroClass;
	private int heroLevel;
	private int mapLevel;

	private int currentExp;
	private int maxExp;
	private int attackPoints;
	private int defensePoints;
	private int currentHealth;
	private int maxHealth;

	private Equipment weapon;
	private Equipment armor;
	private Equipment helmet;
	private Icon image;
	private final StringBuilder stringBuilder;

	/* Constructor for creating a new level 1 hero */
	public Hero(String heroName, String heroClass, int attackPoints, int defensePoints, int healthPoints)
	{
		this.id = -1;
		this.heroName = heroName;
		this.heroClass = heroClass;
		this.heroLevel = 1;
		this.mapLevel = 1;

		this.currentExp = 0;
		setMaxExp();
		this.attackPoints = attackPoints;
		this.defensePoints = defensePoints;
		this.currentHealth = healthPoints;
		this.maxHealth = healthPoints;

		this.weapon = null;
		this.armor = null;
		this.helmet = null;
		this.stringBuilder = new StringBuilder();
		this.initializeImage();
	}

	/* Constructor for a loading an existing hero from saved data */
	public Hero(int id, String heroName, String heroClass, int heroLevel, int mapLevel,
				int currentExp, int attackPoints, int defensePoints, int currentHealth, int maxHealth,
				Equipment weapon, Equipment armor, Equipment helmet)
	{
		this.id = id;
		this.heroName = heroName;
		this.heroClass = heroClass;
		this.heroLevel = heroLevel;
		this.mapLevel = mapLevel;

		this.currentExp = currentExp;
		setMaxExp();
		this.attackPoints = attackPoints;
		this.defensePoints = defensePoints;
		this.currentHealth = currentHealth;
		this.maxHealth = maxHealth;

		this.weapon = weapon;
		this.armor = armor;
		this.helmet = helmet;
		this.stringBuilder = new StringBuilder();
		this.initializeImage();
	}

	private void initializeImage()
	{
		this.image = Hero.CLASS_TO_IMAGE_MAP.get(this.heroClass);
		if (this.image == null)
			this.image = Hero.DEFAULT_IMAGE;
	}

	public void setID(int id) { this.id = id; }
	public void setWeapon(Weapon weapon) { this.weapon = weapon; }
	public void setArmor(Armor armor) { this.armor = armor; }
	public void setHelmet(Helmet helmet) { this.helmet = helmet; }

	public int getID() { return (this.id); }
	public String getHeroName() { return (this.heroName); }
	public String getHeroClass() { return (this.heroClass); }
	public int getHeroLevel() { return (this.heroLevel); }
	public int getMapLevel() { return (this.mapLevel); }

	public int getCurrentExp() { return (this.currentExp); }
	public int getMaxExp() { return (this.maxExp); }
	public int getAttackPoints() { return (this.attackPoints); }
	public int getDefensePoints() { return (this.defensePoints); }
	public int getCurrentHealth() { return (this.currentHealth); }
	public int getMaxHealth() { return (this.maxHealth); }

	public Equipment getWeapon() { return (this.weapon); }
	public Equipment getArmor() { return (this.armor); }
	public Equipment getHelmet() { return (this.helmet); }

	public void setMapLevel(int mapLevel) { this.mapLevel = mapLevel; }

	public int getTotalAttackPoints()
	{
		int attackPoints = this.attackPoints;
		if (this.weapon != null)
			attackPoints += this.weapon.getAttackModifier();
		if (this.armor != null)
			attackPoints += this.armor.getAttackModifier();
		if (this.helmet != null)
			attackPoints += this.helmet.getAttackModifier();
		attackPoints = Math.max(0, attackPoints);
		return (attackPoints);
	}

	public int getTotalDefensePoints()
	{
		int defensePoints = this.defensePoints;
		if (this.weapon != null)
			defensePoints += this.weapon.getDefenseModifier();
		if (this.armor != null)
			defensePoints += this.armor.getDefenseModifier();
		if (this.helmet != null)
			defensePoints += this.helmet.getDefenseModifier();
		defensePoints = Math.max(0, defensePoints);
		return (defensePoints);
	}

	public int getTotalCurrentHealth()
	{
		int health = this.currentHealth;
		if (this.weapon != null)
			health += this.weapon.getHealthModifier();
		if (this.armor != null)
			health += this.armor.getHealthModifier();
		if (this.helmet != null)
			health += this.helmet.getHealthModifier();
		health = Math.max(0, health);
		return (health);
	}

	public int getTotalMaxHealth()
	{
		int health = this.maxHealth;
		if (this.weapon != null)
			health += this.weapon.getHealthModifier();
		if (this.armor != null)
			health += this.armor.getHealthModifier();
		if (this.helmet != null)
			health += this.helmet.getHealthModifier();
		health = Math.max(0, health);
		return (health);
	}

	/* Preview string for hero selection window */
	public String toString()
	{
		return (String.format("%s ( Lv %d %s ) @ Map Lv %d",
				this.heroName, this.heroLevel, this.heroClass, this.mapLevel));
	}

	public String getFullStats()
	{
		this.stringBuilder.setLength(0);
		this.stringBuilder.append(String.format("%-10s", "Name:"));
		this.stringBuilder.append(this.heroName);
		this.stringBuilder.append('\n');
		this.stringBuilder.append(String.format("%-10s", "Class:"));
		this.stringBuilder.append(this.heroClass);
		this.stringBuilder.append('\n');
		this.stringBuilder.append(String.format("%-10s", "Level:"));
		this.stringBuilder.append(this.heroLevel);
		this.stringBuilder.append("\n\n");

		this.stringBuilder.append(String.format("%-10s", "Exp:"));
		this.stringBuilder.append(String.format("%d / %d (%.1f%%)",
				this.currentExp, this.maxExp, (double)this.currentExp / this.maxExp * 100));
		this.stringBuilder.append('\n');
		this.stringBuilder.append(String.format("%-10s", "Attack:"));
		this.stringBuilder.append(this.getTotalAttackPoints());
		this.stringBuilder.append('\n');
		this.stringBuilder.append(String.format("%-10s", "Defense:"));
		this.stringBuilder.append(this.getTotalDefensePoints());
		this.stringBuilder.append('\n');
		this.stringBuilder.append(String.format("%-10s", "Health:"));
		this.stringBuilder.append(String.format("%d / %d",
				this.getTotalCurrentHealth(),
				this.getTotalMaxHealth()));
		this.stringBuilder.append("\n\n");

		this.stringBuilder.append(String.format("%-10s", "Helmet:"));
		this.stringBuilder.append(this.helmet != null ?
				this.helmet.toString() : "(none)");
		this.stringBuilder.append('\n');
		this.stringBuilder.append(String.format("%-10s", "Armor:"));
		this.stringBuilder.append(this.armor != null ?
				this.armor.toString() : "(none)");
		this.stringBuilder.append('\n');
		this.stringBuilder.append(String.format("%-10s", "Weapon:"));
		this.stringBuilder.append(this.weapon != null ?
				this.weapon.toString() : "(none)");
		this.stringBuilder.append('\n');
		return (this.stringBuilder.toString());
	}

	private void setMaxExp()
	{
		this.maxExp = this.heroLevel * 1000 + (int)(Math.pow(this.heroLevel - 1, 2)) * 450;
	}

	private void levelUp()
	{
		System.out.println(this.heroName + " leveled up!");
		this.heroLevel++;
		this.currentHealth = this.maxHealth;
		this.currentExp = 0;
		this.setMaxExp();

		int attackGain = 0;
		int defenseGain = 0;
		int healthGain = 0;
		int statPoints = 5;

		while (statPoints > 0)
		{
			double probability = Math.random();
			if (probability < 0.3)
				attackGain++;
			else if (probability < 0.6)
				defenseGain++;
			else
				healthGain += 2;
			statPoints--;
		}
		this.attackPoints += attackGain;
		this.defensePoints += defenseGain;
		this.currentHealth += healthGain;
		this.maxHealth += healthGain;

		System.out.printf("You leveled up!  You gained +%d Atk, +%d Def, +%d HP\n",
				attackGain, defenseGain, healthGain);
		SoundManager.getInstance().playLevelUpSound();
	}

	public int giveExp() { return (0); }

	public void gainExp(int expValue)
	{
		this.currentExp += expValue;
		if (this.currentExp >= this.maxExp)
			this.levelUp();
	}

	public Equipment dropEquipment() { return (null); }

	public int dealDamage()
	{
		return (this.getTotalAttackPoints());
	}

	public int receiveDamage(int damageValue)
	{
		damageValue -= this.getTotalDefensePoints();
		damageValue = Math.max(0, damageValue);
		this.currentHealth -= damageValue;
		return (damageValue);
	}

	public boolean isDead() { return (this.getTotalCurrentHealth() <= 0); }

	public String getNameWithStats()
	{
		return (String.format("%s (Lv %d, %d/%d HP)", this.heroName, this.heroLevel,
				this.getTotalCurrentHealth(), this.getTotalMaxHealth()));
	}

	public String getName() { return (this.heroName); }

	@Override
	public char getChar() { return ('P'); }

	@Override
	public Icon getImage() { return (this.image); }

}

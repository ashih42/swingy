package ashih.swingy.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class DatabaseManager
{
	private static final DatabaseManager instance = new DatabaseManager();

	public static DatabaseManager getInstance()
	{
		return (DatabaseManager.instance);
	}

	private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "doge";
	private static final String MAX_POOL = "250";

	private Connection connection;
	private final StringBuilder stringBuilder;

	private DatabaseManager()
	{
		this.initializeConnection();
		this.stringBuilder = new StringBuilder();
	}

	private void initializeConnection()
	{
		Properties properties = new Properties();
		properties.setProperty("user", this.USERNAME);
		properties.setProperty("password", this.PASSWORD);
		properties.setProperty("MaxPooledStatements", this.MAX_POOL);

		String DOCKER_MACHINE_IP = System.getenv("DOCKER_MACHINE_IP");
		if (DOCKER_MACHINE_IP == null)
		{
			System.out.println("Error: Environment variable DOCKER_MACHINE_IP is not set");
			return;
		}
		String DATABASE_URL = "jdbc:mysql://" + DOCKER_MACHINE_IP + ":3306/swingyDB";	// "jdbc:mysql://IP:3306/swingyDB"
		try
		{
			Class.forName(DATABASE_DRIVER);
			this.connection = DriverManager.getConnection(DATABASE_URL, properties);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}

	public void deleteHero(Hero hero)
	{
		if (this.connection == null) return;
		try
		{
			String query = String.format("DELETE FROM hero_table WHERE id=%d;", hero.getID());
			PreparedStatement statement = this.connection.prepareStatement(query);
			statement.executeUpdate();
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
	}

	public void updateHero(Hero hero)
	{
		if (this.connection == null) return;
		try
		{
			this.stringBuilder.setLength(0);
			this.stringBuilder.append("UPDATE hero_table SET ");
			this.stringBuilder.append(String.format("hero_level=%d, ", hero.getHeroLevel()));
			this.stringBuilder.append(String.format("map_level=%d, ", hero.getMapLevel()));
			this.stringBuilder.append(String.format("current_exp=%d, ", hero.getCurrentExp()));

			this.stringBuilder.append(String.format("attack_points=%d, ", hero.getAttackPoints()));
			this.stringBuilder.append(String.format("defense_points=%d, ", hero.getDefensePoints()));
			this.stringBuilder.append(String.format("current_health=%d, ", hero.getCurrentHealth()));
			this.stringBuilder.append(String.format("max_health=%d, ", hero.getMaxHealth()));

			// Weapon
			Equipment weapon = hero.getWeapon();
			String weaponNameExpr = (weapon == null) ? "NULL" : String.format("'%s'", weapon.getEquipmentName());
			String weaponAttackExpr = (weapon == null) ? "NULL" : String.format("%d", weapon.getAttackModifier());
			String weaponDefenseExpr = (weapon == null) ? "NULL" : String.format("%d", weapon.getDefenseModifier());
			String weaponHealthExpr = (weapon == null) ? "NULL" : String.format("%d", weapon.getHealthModifier());
			this.stringBuilder.append(String.format("has_weapon=%d, ", weapon == null ? 0 : 1));
			this.stringBuilder.append(String.format("weapon_name=%s, ", weaponNameExpr));
			this.stringBuilder.append(String.format("weapon_attack_modifier=%s, ", weaponAttackExpr));
			this.stringBuilder.append(String.format("weapon_defense_modifier=%s, ", weaponDefenseExpr));
			this.stringBuilder.append(String.format("weapon_health_modifier=%s, ", weaponHealthExpr));

			// Armor
			Equipment armor = hero.getArmor();
			String armorNameExpr = (armor == null) ? "NULL" : String.format("'%s'", armor.getEquipmentName());
			String armorAttackExpr = (armor == null) ? "NULL" : String.format("%d", armor.getAttackModifier());
			String armorDefenseExpr = (armor == null) ? "NULL" : String.format("%d", armor.getDefenseModifier());
			String armorHealthExpr = (armor == null) ? "NULL" : String.format("%d", armor.getHealthModifier());
			this.stringBuilder.append(String.format("has_armor=%d, ", armor == null ? 0 : 1));
			this.stringBuilder.append(String.format("armor_name=%s, ", armorNameExpr));
			this.stringBuilder.append(String.format("armor_attack_modifier=%s, ", armorAttackExpr));
			this.stringBuilder.append(String.format("armor_defense_modifier=%s, ", armorDefenseExpr));
			this.stringBuilder.append(String.format("armor_health_modifier=%s, ", armorHealthExpr));

			// Helmet
			Equipment helmet = hero.getHelmet();
			String helmetNameExpr = (helmet == null) ? "NULL" : String.format("'%s'", helmet.getEquipmentName());
			String helmetAttackExpr = (helmet == null) ? "NULL" : String.format("%d", helmet.getAttackModifier());
			String helmetDefenseExpr = (helmet == null) ? "NULL" : String.format("%d", helmet.getDefenseModifier());
			String helmetHealthExpr = (helmet == null) ? "NULL" : String.format("%d", helmet.getHealthModifier());
			this.stringBuilder.append(String.format("has_helmet=%d, ", helmet == null ? 0 : 1));
			this.stringBuilder.append(String.format("helmet_name=%s, ", helmetNameExpr));
			this.stringBuilder.append(String.format("helmet_attack_modifier=%s, ", helmetAttackExpr));
			this.stringBuilder.append(String.format("helmet_defense_modifier=%s, ", helmetDefenseExpr));
			this.stringBuilder.append(String.format("helmet_health_modifier=%s " , helmetHealthExpr));

			this.stringBuilder.append(String.format("WHERE id=%d;", hero.getID()));

			String query = this.stringBuilder.toString();
			PreparedStatement statement = this.connection.prepareStatement(query);
			statement.executeUpdate();
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
	}

	/* After insertion, check the auto-assigned id in database */
	public void insertNewHero(Hero hero)
	{
		if (this.connection == null) return;
		try
		{
			this.stringBuilder.setLength(0);
			this.stringBuilder.append("INSERT INTO hero_table " +
					"(hero_name, hero_class, hero_level, map_level, " +
					"current_exp, attack_points, defense_points, current_health, max_health, " +
					"has_weapon, weapon_name, weapon_attack_modifier, weapon_defense_modifier, weapon_health_modifier, " +
					"has_armor, armor_name, armor_attack_modifier, armor_defense_modifier, armor_health_modifier, " +
					"has_helmet, helmet_name, helmet_attack_modifier, helmet_defense_modifier, helmet_health_modifier) VALUES (");
			this.stringBuilder.append("?, ");
			this.stringBuilder.append(String.format("'%s', ", hero.getHeroClass()));
			this.stringBuilder.append(String.format("%d, ", hero.getHeroLevel()));
			this.stringBuilder.append(String.format("%d, ", hero.getMapLevel()));

			this.stringBuilder.append(String.format("%d, ", hero.getCurrentExp()));
			this.stringBuilder.append(String.format("%d, ", hero.getAttackPoints()));
			this.stringBuilder.append(String.format("%d, ", hero.getDefensePoints()));
			this.stringBuilder.append(String.format("%d, ", hero.getCurrentHealth()));
			this.stringBuilder.append(String.format("%d, ", hero.getMaxHealth()));

			// Weapon
			Equipment weapon = hero.getWeapon();
			String weaponNameExpr = (weapon == null) ? "NULL" : String.format("'%s'", weapon.getEquipmentName());
			String weaponAttackExpr = (weapon == null) ? "NULL" : String.format("%d", weapon.getAttackModifier());
			String weaponDefenseExpr = (weapon == null) ? "NULL" : String.format("%d", weapon.getDefenseModifier());
			String weaponHealthExpr = (weapon == null) ? "NULL" : String.format("%d", weapon.getHealthModifier());
			this.stringBuilder.append(String.format("%d, ", weapon == null ? 0 : 1));
			this.stringBuilder.append(String.format("%s, ", weaponNameExpr));
			this.stringBuilder.append(String.format("%s, ", weaponAttackExpr));
			this.stringBuilder.append(String.format("%s, ", weaponDefenseExpr));
			this.stringBuilder.append(String.format("%s, ", weaponHealthExpr));

			// Armor
			Equipment armor = hero.getArmor();
			String armorNameExpr = (armor == null) ? "NULL" : String.format("'%s'", armor.getEquipmentName());
			String armorAttackExpr = (armor == null) ? "NULL" : String.format("%d", armor.getAttackModifier());
			String armorDefenseExpr = (armor == null) ? "NULL" : String.format("%d", armor.getDefenseModifier());
			String armorHealthExpr = (armor == null) ? "NULL" : String.format("%d", armor.getHealthModifier());
			this.stringBuilder.append(String.format("%d, ", armor == null ? 0 : 1));
			this.stringBuilder.append(String.format("%s, ", armorNameExpr));
			this.stringBuilder.append(String.format("%s, ", armorAttackExpr));
			this.stringBuilder.append(String.format("%s, ", armorDefenseExpr));
			this.stringBuilder.append(String.format("%s, ", armorHealthExpr));

			// Helmet
			Equipment helmet = hero.getHelmet();
			String helmetNameExpr = (helmet == null) ? "NULL" : String.format("'%s'", helmet.getEquipmentName());
			String helmetAttackExpr = (helmet == null) ? "NULL" : String.format("%d", helmet.getAttackModifier());
			String helmetDefenseExpr = (helmet == null) ? "NULL" : String.format("%d", helmet.getDefenseModifier());
			String helmetHealthExpr = (helmet == null) ? "NULL" : String.format("%d", helmet.getHealthModifier());
			this.stringBuilder.append(String.format("%d, ", helmet == null ? 0 : 1));
			this.stringBuilder.append(String.format("%s, ", helmetNameExpr));
			this.stringBuilder.append(String.format("%s, ", helmetAttackExpr));
			this.stringBuilder.append(String.format("%s, ", helmetDefenseExpr));
			this.stringBuilder.append(String.format("%s", helmetHealthExpr));
			this.stringBuilder.append(");");

			String query = this.stringBuilder.toString();
			PreparedStatement statement = this.connection.prepareStatement(query);
			statement.setString(1, hero.getName());
			statement.executeUpdate();

			hero.setID(this.getLastInsertedHeroID());
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
	}

	private int getLastInsertedHeroID()
	{
		int id = -1;
		if (this.connection != null)
		{
			try
			{
				String query = "SELECT id FROM hero_table ORDER BY id DESC LIMIT 1;";
				PreparedStatement statement = this.connection.prepareStatement(query);
				ResultSet resultSet = statement.executeQuery();

				if (resultSet.next())
					id = Integer.parseInt(resultSet.getString("id"));
			} catch (SQLException e)
			{
				System.out.println(e);
			}
		}
		return (id);
	}

	public ArrayList<Hero> loadAllHeroes()
	{
		ArrayList<Hero> heroes = new ArrayList<Hero>();
		if (this.connection != null)
		{
			try
			{
				String query = "SELECT * FROM hero_table;";
				PreparedStatement statement = this.connection.prepareStatement(query);
				ResultSet resultSet = statement.executeQuery();

				while (resultSet.next())
				{
					Hero hero = this.parseHero(resultSet);
					if (hero != null)
						heroes.add(hero);
				}
			}
			catch (SQLException e)
			{
				System.out.println(e);
			}
		}
		return (heroes);
	}

	private Hero parseHero(ResultSet resultSet)
	{
		Hero hero = null;
		try
		{
			int id = Integer.parseInt(resultSet.getString("id"));
			String heroName = resultSet.getString("hero_name");
			String heroClass = resultSet.getString("hero_class");
			int heroLevel = Integer.parseInt(resultSet.getString("hero_level"));
			int mapLevel = Integer.parseInt(resultSet.getString("map_level"));

			int currentExp = Integer.parseInt(resultSet.getString("current_exp"));
			int attackPoints = Integer.parseInt(resultSet.getString("attack_points"));
			int defensePoints = Integer.parseInt(resultSet.getString("defense_points"));
			int currentHealth = Integer.parseInt(resultSet.getString("current_health"));
			int maxHealth = Integer.parseInt(resultSet.getString("max_health"));

			Weapon weapon = this.parseWeapon(resultSet);
			Armor armor = this.parseArmor(resultSet);
			Helmet helmet = this.parseHelmet(resultSet);

			hero = new Hero(id, heroName, heroClass, heroLevel, mapLevel,
					currentExp, attackPoints, defensePoints, currentHealth, maxHealth,
					weapon, armor, helmet);

		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		return (hero);
	}

	private Weapon parseWeapon(ResultSet resultSet)
	{
		Weapon weapon = null;
		try
		{
			boolean hasWeapon = resultSet.getString("has_weapon").equals("1");
			if (hasWeapon)
			{
				String weaponName = resultSet.getString("weapon_name");
				int attackModifier = Integer.parseInt(resultSet.getString("weapon_attack_modifier"));
				int defenseModifier = Integer.parseInt(resultSet.getString("weapon_defense_modifier"));
				int healthModifier = Integer.parseInt(resultSet.getString("weapon_health_modifier"));
				weapon = new Weapon(weaponName, attackModifier, defenseModifier, healthModifier);
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		return (weapon);
	}

	private Armor parseArmor(ResultSet resultSet)
	{
		Armor armor = null;
		try
		{
			boolean hasArmor = resultSet.getString("has_armor").equals("1");
			if (hasArmor)
			{
				String armorName = resultSet.getString("armor_name");
				int attackModifier = Integer.parseInt(resultSet.getString("armor_attack_modifier"));
				int defenseModifier = Integer.parseInt(resultSet.getString("armor_defense_modifier"));
				int healthModifier = Integer.parseInt(resultSet.getString("armor_health_modifier"));
				armor = new Armor(armorName, attackModifier, defenseModifier, healthModifier);
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		return (armor);
	}

	private Helmet parseHelmet(ResultSet resultSet)
	{
		Helmet helmet = null;
		try
		{
			boolean hasHelmet = resultSet.getString("has_helmet").equals("1");
			if (hasHelmet)
			{
				String helmetName = resultSet.getString("helmet_name");
				int attackModifier = Integer.parseInt(resultSet.getString("helmet_attack_modifier"));
				int defenseModifier = Integer.parseInt(resultSet.getString("helmet_defense_modifier"));
				int healthModifier = Integer.parseInt(resultSet.getString("helmet_health_modifier"));
				helmet = new Helmet(helmetName, attackModifier, defenseModifier, healthModifier);
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		return (helmet);
	}

	public void finalize()
	{
		if (this.connection != null)
		{
			try
			{
				this.connection.close();
				this.connection = null;
			}
			catch (Exception e)
			{
				System.out.println(e);
			}
		}
	}

}

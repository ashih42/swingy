package ashih.swingy.model;

public abstract class Equipment extends Entity
{
	private final String equipmentName;
	private final int attackModifier;
	private final int defenseModifier;
	private final int healthModifier;

	public Equipment(String equipmentName, int attackModifier, int defenseModifier, int healthModifier)
	{
		this.equipmentName = equipmentName;
		this.attackModifier = attackModifier;
		this.defenseModifier = defenseModifier;
		this.healthModifier = healthModifier;
	}

	public String getEquipmentName() { return (this.equipmentName); }
	public int getAttackModifier() { return (this.attackModifier); }
	public int getDefenseModifier() { return (this.defenseModifier); }
	public int getHealthModifier() { return (this.healthModifier); }

	public String toString()
	{
		return (String.format("%s (%3d Atk, %3d Def, %3d HP)",
				this.equipmentName,
				this.attackModifier,
				this.defenseModifier,
				this.healthModifier));
	}

	public abstract void equip(Hero hero);

}

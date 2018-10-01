package ashih.swingy.model;

import javax.swing.*;

public class Weapon extends Equipment
{
	private static final Icon image = Entity.loadImageFromFile("images/knife.png");

	public Weapon(String equipmentName, int attackModifier, int defenseModifier, int healthModifier)
	{
		super(equipmentName, attackModifier, defenseModifier, healthModifier);
	}

	@Override
	public void equip(Hero hero) { hero.setWeapon(this); }

	@Override
	public char getChar() { return ('W'); }

	@Override
	public Icon getImage() { return (Weapon.image); }

}

package ashih.swingy.model;

import javax.swing.*;

public class Armor extends Equipment
{
	private static final Icon image = Entity.loadImageFromFile("images/bikini.png");

	public Armor(String equipmentName, int attackModifier, int defenseModifier, int healthModifier)
	{
		super(equipmentName, attackModifier, defenseModifier, healthModifier);
	}

	@Override
	public void equip(Hero hero) { hero.setArmor(this); }

	@Override
	public char getChar() { return ('A'); }

	@Override
	public Icon getImage() { return (Armor.image); }

}

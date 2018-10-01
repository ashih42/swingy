package ashih.swingy.model;

import javax.swing.*;

public class Helmet extends Equipment
{
	private static final Icon image = Entity.loadImageFromFile("images/top_hat.png");

	public Helmet(String equipmentName, int attackModifier, int defenseModifier, int healthModifier)
	{
		super(equipmentName, attackModifier, defenseModifier, healthModifier);
	}

	@Override
	public void equip(Hero hero) { hero.setHelmet(this); }

	@Override
	public char getChar() { return ('H'); }

	@Override
	public Icon getImage() { return (Helmet.image); }

}

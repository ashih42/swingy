package ashih.swingy.model;

import javax.swing.*;

public class EmptyEntity extends Entity
{
	private static final Icon IMAGE = Entity.loadImageFromFile("images/seedling.png");

	public EmptyEntity() { }

	@Override
	public boolean isEmpty() { return (true); }

	@Override
	public char getChar() { return (' '); }

	@Override
	public Icon getImage() { return (EmptyEntity.IMAGE); }

}

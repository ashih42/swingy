package ashih.swingy.model;

public interface ICombat
{
	public int			dealDamage();
	public int			receiveDamage(int damageValue);
	public int			giveExp();
	public void			gainExp(int expValue);
	public Equipment	dropEquipment();
	public boolean		isDead();
	public String		getNameWithStats();
	public String		getName();

}

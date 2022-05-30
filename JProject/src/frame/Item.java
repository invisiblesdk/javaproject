package frame;

public class Item {
	public Integer Id;
	public String Name;
	public Integer GoldValue;
	public Integer CombatValue;
	public String Type;
	public String State;
	public Item(String name,Integer goldValue,Integer combatValue,String type,String state) {
		Name = name;
		GoldValue = goldValue;
		CombatValue = combatValue;
		Type = type;
		State = state;
	}
}

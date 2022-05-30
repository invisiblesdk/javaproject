package frame;

public class Player {
	public Integer Id;
	public String Name;
	public String Class;
	public Integer Renown;
	public Integer Money; 
	public Player(String name, String _class) {
		Name = name;
		Class = _class;
		Money = 100;
		Renown = 0;
	}
}

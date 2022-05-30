package frame;

import javax.swing.JPanel;

import app.DB.DBRepo;

public class Page {
	DBRepo repo;
	String name = "";
	JPanel upPanel;
	JPanel midPanel;
	JPanel downPanel;
	public JPanel[] Load() {
		JPanel[] arr = new JPanel[3];
		arr[0] = upPanel;
		arr[1] = midPanel;
		arr[2] = downPanel;
		return arr;
	}
	
}

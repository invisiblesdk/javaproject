package frame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import app.DB.DBRepo;

public class SearchPage extends Page{
	
	JTable table = new JTable();
	JScrollPane myScroll = new JScrollPane(table);
	JLabel ItemNamelbl = new JLabel("Item name:");
	JLabel PartyRenownlbl = new JLabel("Party renown:");
	JLabel PartyNamelbl = new JLabel("Party name:");
	JLabel ItemCombatValuelbl = new JLabel("Item strength:");
	JTextField ItemNametxt = new JTextField();
	JTextField PartyRenowntxt = new JTextField();
	JTextField PartyNametxt = new JTextField();
	JTextField ItemCombatValuetxt = new JTextField();
	JPanel miditempanel = new JPanel();
	JButton seachitembtn = new JButton("Search");
	JButton seachpartybtn = new JButton("Search");
	JPanel midpartypanel = new JPanel();
	public SearchPage(DBRepo repository) {
		repo = repository;
		name = "Archive";
		upPanel = new JPanel();
		midPanel = new JPanel();		
		downPanel = new JPanel();
		
		//top
		upPanel.add(myScroll);
		//mid
		midPanel.setLayout(new GridLayout(1,2));
		miditempanel.setLayout(new GridLayout(3,2));
		miditempanel.add(ItemNamelbl);
		miditempanel.add(ItemNametxt);
		miditempanel.add(ItemCombatValuelbl);
		miditempanel.add(ItemCombatValuetxt);
		seachitembtn.addActionListener(new SearchItem());
		miditempanel.add(seachitembtn);
		midPanel.add(miditempanel);
		
		midpartypanel.setLayout(new GridLayout(3,2));
		midpartypanel.add(PartyNamelbl);
		midpartypanel.add(PartyNametxt);
		midpartypanel.add(PartyRenownlbl);
		midpartypanel.add(PartyRenowntxt);
		seachpartybtn.addActionListener(new SearchParty());
		midpartypanel.add(seachpartybtn);
		midPanel.add(midpartypanel);
	}
	class SearchItem implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {			
			if(!ItemNametxt.getText().isBlank() && !ItemCombatValuetxt.getText().isBlank()) {
				table.setModel(repo.GetItemByCValueAndName(Integer.parseInt(ItemCombatValuetxt.getText()),ItemNametxt.getText()));
			}else if(ItemNametxt.getText().isBlank() && !ItemCombatValuetxt.getText().isBlank()) {
				table.setModel(repo.GetItemByCValue(Integer.parseInt(ItemCombatValuetxt.getText())));
			}
			else if(!ItemNametxt.getText().isBlank() && ItemCombatValuetxt.getText().isBlank()) {
				System.out.println(ItemNametxt.getText());
				table.setModel(repo.GetItemByName(ItemNametxt.getText()));
			}
		}
		
	}
	class SearchParty implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {			
			if(!PartyNametxt.getText().isBlank() && !PartyRenowntxt.getText().isBlank()) {
				table.setModel(repo.GetPartyByNameAndRenown(Integer.parseInt(PartyRenowntxt.getText()), PartyNametxt.getText()));
			}
			else if(PartyNametxt.getText().isBlank() && !PartyRenowntxt.getText().isBlank()) {
				table.setModel(repo.GetPartyByRenown(Integer.parseInt(PartyRenowntxt.getText())));
			}
			else if (!PartyNametxt.getText().isBlank() && PartyRenowntxt.getText().isBlank()) {
				table.setModel(repo.GetPartyByName(PartyNametxt.getText()));
			}
		}
		
	}
}

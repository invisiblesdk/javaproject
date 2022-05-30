package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import app.DB.DBRepo;
import frame.ItemPage.MouseAction;

public class PartyPage extends Page{
	Integer partyid = -1;
	Integer playerid = -1;
	JTable tableParties = new JTable();
	JScrollPane myScrollparties = new JScrollPane(tableParties);
	JLabel selectedPartylbl = new JLabel("Currently selected: ");
	
	JTable tableMembers = new JTable();
	JScrollPane myScrollMembers = new JScrollPane(tableMembers);
	
	JTable tableAllFreePlayers = new JTable();
	JScrollPane myScrollAllFreePlayers = new JScrollPane(tableAllFreePlayers);
	
	JPanel addremovepanel = new JPanel();
	JButton addMemberbtn = new JButton("Add");
	JLabel selectedPlayerNamelbl = new JLabel();
	JLabel selectedPlayerRenownlbl = new JLabel();
	JLabel selectedPlayerClasslbl = new JLabel();
	JLabel selectedPlayerCombatValuelbl = new JLabel();
	JButton removeMemberbtn = new JButton("Remove");
	JPanel labelbuttonpanel = new JPanel();
	JButton refreshbtn = new JButton("Refresh");
	
	JPanel partyCreationPanel = new JPanel();
	JLabel partyNamelbl = new JLabel("Party name: ");
	JTextField partyNametxt = new JTextField();
	JButton createPartybtn = new JButton("Create party");
	
	JPanel nameClassPanel = new JPanel();
	JButton disbandPartybtn = new JButton("Disband");
	
	JButton changeNamebtn = new JButton("Change name");
 public PartyPage(DBRepo repository){
	 	repo = repository;
		name = "Party room";
		upPanel = new JPanel();
		midPanel = new JPanel();
		downPanel = new JPanel();
		
		//---top---
		upPanel.setLayout(new BorderLayout());
		myScrollparties.setPreferredSize(new Dimension(400,200));
		upPanel.add(myScrollparties,BorderLayout.CENTER);
		
		labelbuttonpanel.setLayout(new BorderLayout());
		labelbuttonpanel.add(selectedPartylbl,BorderLayout.WEST);
		refreshbtn.addActionListener(new Refresh());
		labelbuttonpanel.add(refreshbtn,BorderLayout.EAST);
		upPanel.add(labelbuttonpanel,BorderLayout.PAGE_END);
		tableParties.addMouseListener(new MouseActionParty());
		RefreshPartiesTable();
		
		//table all parties and currently selected on the bottom borderlayout*
		//(there is a table containing party id and player id)
		
		//---mid---
		midPanel.setLayout(new BorderLayout());
		myScrollMembers.setPreferredSize(new Dimension(175,200));
		midPanel.add(myScrollMembers,BorderLayout.WEST);
		tableMembers.addMouseListener(new MouseActionPartyMembers());
		
		addremovepanel.setLayout(new BorderLayout());
		nameClassPanel.setLayout(new GridLayout(2,1));
		nameClassPanel.add(selectedPlayerNamelbl);
		nameClassPanel.add(selectedPlayerClasslbl);
		addMemberbtn.addActionListener(new AddPlayerToParty());
		removeMemberbtn.addActionListener(new RemovePlayerFromParty());
		
		myScrollAllFreePlayers.setPreferredSize(new Dimension(175,200));
		midPanel.add(myScrollAllFreePlayers,BorderLayout.EAST);
		tableAllFreePlayers.addMouseListener(new MouseActionFreePlayers());
		RefreshFreePlayersTable();
		// LEFT:table current party members
		//RIGHT: table players without party
		//CENTER 2 buttons ADD member and REMOVE member (only one is active at a time)
		
		
		
		//---bot---
		downPanel.setLayout(new BorderLayout());
		partyCreationPanel.setLayout(new GridLayout(1,4));
		partyCreationPanel.add(partyNamelbl);
		partyCreationPanel.add(partyNametxt);
		partyCreationPanel.add(createPartybtn);
		changeNamebtn.addActionListener(new RenameParty());
		partyCreationPanel.add(changeNamebtn);
		createPartybtn.addActionListener(new CreateParty());
		downPanel.add(partyCreationPanel,BorderLayout.NORTH);
		disbandPartybtn.addActionListener(new DisbandParty());
		downPanel.add(disbandPartybtn,BorderLayout.SOUTH);
		// DISBAND button
 }
 public void ToggleAddButton() {
	 midPanel.remove(addremovepanel);
	 addremovepanel = new JPanel();
	 addremovepanel.setLayout(new BorderLayout());
	 addremovepanel.add(addMemberbtn,BorderLayout.PAGE_END);
	 addremovepanel.add(nameClassPanel,BorderLayout.CENTER);
	 midPanel.add(addremovepanel,BorderLayout.CENTER);
 }
 public void ToggleRemoveButton() {
	 midPanel.remove(addremovepanel);
	 addremovepanel = new JPanel();
	 addremovepanel.setLayout(new BorderLayout());
	 addremovepanel.add(removeMemberbtn,BorderLayout.PAGE_END);
	 addremovepanel.add(nameClassPanel,BorderLayout.CENTER);
	 midPanel.add(addremovepanel,BorderLayout.CENTER);
 }
 public void CleanData() {
	 playerid = -1;
	 selectedPlayerNamelbl.setText("");
	 selectedPlayerClasslbl.setText("");
	 selectedPlayerCombatValuelbl.setText("");
	 selectedPlayerRenownlbl.setText("");
	 addremovepanel.removeAll();
	 addremovepanel.revalidate();
 }
 class Refresh implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {	
			RefreshPartiesTable();
			RefreshFreePlayersTable();
			if(partyid > -1) {
				RefreshMemberTable();
			}
		}
		
	}
 class AddPlayerToParty implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {			
			if(partyid > -1 && playerid > -1) {
				repo.AddPlayerToParty(partyid,playerid);
				RefreshMemberTable();
				RefreshFreePlayersTable();
				CleanData();
			}
			else {
				infoBox("Select a party and a player!");
			}
		}
		
	}
 class RemovePlayerFromParty implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {	
			if(partyid > -1 && playerid > -1) {
				repo.DeletePartyMember(playerid);
				RefreshMemberTable();
				RefreshFreePlayersTable();
				CleanData();
			}
			else {
				infoBox("Select a party and a player!");
			}
			
		}
		
	}
 class DisbandParty implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {			
			if(partyid > -1) {
				repo.DeleteParty(partyid);
			}
			else {
				infoBox("Select a party!");
			}
		}
		
	}
 class RenameParty implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {			
			if(!partyNametxt.getText().isBlank() && partyid > -1) {
				repo.UpdatePartyname(partyid ,partyNametxt.getText());
				RefreshPartiesTable();
			}
			else {
				infoBox("Select a party and enter a party name!");
			}
		}
		
	}
 class CreateParty implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {			
			if(!partyNametxt.getText().isBlank()) {
				repo.AddParty(partyNametxt.getText());
				RefreshPartiesTable();
			}
			else {
				infoBox("Enter a party name!");
			}
		}
		
	}
 class MouseActionParty implements  MouseListener{


		@Override
		public void mouseClicked(MouseEvent e) {
			
			int row = tableParties.getSelectedRow();			
			partyid = Integer.parseInt(tableParties.getValueAt(row, 0).toString());
			selectedPartylbl.setText("Currently selected:  " + tableParties.getValueAt(row, 1).toString());	
			RefreshMemberTable();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
 public void RefreshPartiesTable() {
		try {
			if(repo.GetPartyTable() != null) {
				tableParties.setModel(repo.GetPartyTable());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
 public void RefreshMemberTable() {
		try {
			if(partyid > -1) {
				if(repo.GetPartyMemberTable(partyid) != null) {
					tableMembers.setModel(repo.GetPartyMemberTable(partyid));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
 public void RefreshFreePlayersTable() {
		try {
			if(repo.GetFreePlayerTable() != null) {
				tableAllFreePlayers.setModel(repo.GetFreePlayerTable());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
 
 public static void infoBox(String infoMessage)
 {
     JOptionPane.showMessageDialog(null, infoMessage, "Error", JOptionPane.INFORMATION_MESSAGE);
 }
 class MouseActionPartyMembers implements  MouseListener{


		@Override
		public void mouseClicked(MouseEvent e) {
			
			int row = tableMembers.getSelectedRow();
			ToggleRemoveButton();
			playerid = Integer.parseInt(tableMembers.getValueAt(row, 0).toString());
			selectedPlayerNamelbl.setText("Name: "+tableMembers.getValueAt(row, 1).toString());
			selectedPlayerClasslbl.setText("Class: "+tableMembers.getValueAt(row, 2).toString());			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
 
 class MouseActionFreePlayers implements  MouseListener{


		@Override
		public void mouseClicked(MouseEvent e) {
			
			if(partyid > -1) {
				int row = tableAllFreePlayers.getSelectedRow();
				ToggleAddButton();		
				playerid = Integer.parseInt(tableAllFreePlayers.getValueAt(row, 0).toString());
				selectedPlayerNamelbl.setText("Name: "+tableAllFreePlayers.getValueAt(row, 1).toString());
				selectedPlayerClasslbl.setText("Class: "+tableAllFreePlayers.getValueAt(row, 2).toString());
			}
				
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
 
}

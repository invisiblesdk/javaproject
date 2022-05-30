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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import app.DB.DBRepo;
import frame.PlayerPage.MouseActionPlayer;

public class StartPage  extends Page{
	
	int id = -1;
	int rowPlayer = -1;
	JLabel lb = new JLabel("Select a player:");
	//String[] listOfParties;
	//JComboBox<String> partySelectCombo = new JComboBox<String>(listOfParties);
	JPanel registerPartyPnl = new JPanel();
	//JPanel  = new JPanel();
	
	JLabel memberlbl = new JLabel();
	JLabel strenghtlbl = new JLabel();
	JLabel namelbl = new JLabel("Enter name:");
	JLabel classlbl = new JLabel("Enter class:");
	JTextField PlayerNametxt = new JTextField("");
	JTextField playerClasstxt = new JTextField("");
	JButton addPlayerbtn = new JButton("Register new player");
	JTable tablePlayers = new JTable();
	JScrollPane myScrollPlayers = new JScrollPane(tablePlayers);
	
	
	JButton startQuestbtn = new JButton("Start quest");
	JLabel playerNamelbl = new JLabel("Name: ");
	JLabel playerClasslbl = new JLabel("Class: ");
	JLabel playerMoneylbl = new JLabel("Money: ");
	JLabel playerRenownlbl = new JLabel("Renown: ");
	JLabel playerCombatlbl = new JLabel("Combat Strength: ");
	JLabel playerNamelblValue = new JLabel("");
	JLabel playerClasslblValue = new JLabel("");
	JLabel playerMoneylblValue = new JLabel("");
	JLabel playerRenownlblValue = new JLabel("");
	JLabel playerCombatlblValue = new JLabel("");
	JPanel PlayerPanel = new JPanel();
	
	JPanel midbotPanel = new JPanel();
	JButton refreshbtn = new JButton("Refresh");
	public StartPage(DBRepo repository) {
		repo = repository;
		name = "Guild";
		upPanel = new JPanel();
		midPanel = new JPanel();
		downPanel = new JPanel();
		
		//---top---
		tablePlayers.addMouseListener(new MouseActionPlayer());
		myScrollPlayers.setPreferredSize(new Dimension(400,150));
		upPanel.add(myScrollPlayers);
		RefreshTable();
		//scoreboard? top party power / completion score
		
		
		//---mid---
		midPanel.setLayout(new BorderLayout());
		PlayerPanel.setLayout(new GridLayout(5,2));
		PlayerPanel.add(playerNamelbl);
		PlayerPanel.add(playerNamelblValue);
		PlayerPanel.add(playerClasslbl);
		PlayerPanel.add(playerClasslblValue);
		PlayerPanel.add(playerMoneylbl);
		PlayerPanel.add(playerMoneylblValue);
		PlayerPanel.add(playerRenownlbl);
		PlayerPanel.add(playerRenownlblValue);
		PlayerPanel.add(playerCombatlbl);
		PlayerPanel.add(playerCombatlblValue);
		midPanel.add(PlayerPanel,BorderLayout.CENTER);
		startQuestbtn.addActionListener(new Quest());
		midbotPanel.setLayout(new GridLayout(1,2));
		midbotPanel.add(startQuestbtn);
		refreshbtn.addActionListener(new Refresh());
		midbotPanel.add(refreshbtn);
		midPanel.add(midbotPanel,BorderLayout.SOUTH);
		 //start quests party and solo

		
		//---bot---
		downPanel.setLayout(new GridLayout(3,2));
		downPanel.add(namelbl);
		downPanel.add(PlayerNametxt);
		downPanel.add(classlbl);
		downPanel.add(playerClasstxt);
		addPlayerbtn.addActionListener(new RegisterPlayer());
		downPanel.add(addPlayerbtn);
		
		// register adventurer/player
	}
	private void RefreshTable() {
		tablePlayers.setModel(repo.GetAllPlayers());
	}
	class MouseActionPlayer implements  MouseListener{


		@Override
		public void mouseClicked(MouseEvent e) {			
			rowPlayer = tablePlayers.getSelectedRow();
			id = Integer.parseInt(tablePlayers.getValueAt(rowPlayer, 0).toString());	
			RefreshMidPanelInfo();
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
	public void Clear() {
		playerClasstxt.setText("");
		PlayerNametxt.setText("");
	}
	 public static void infoBox(String infoMessage)
	 {
	     JOptionPane.showMessageDialog(null, infoMessage, "Error", JOptionPane.INFORMATION_MESSAGE);
	 }
	 private void RefreshMidPanelInfo() {

			playerNamelblValue.setText(tablePlayers.getValueAt(rowPlayer, 1).toString());
			playerClasslblValue.setText(tablePlayers.getValueAt(rowPlayer, 2).toString());
			playerRenownlblValue.setText(tablePlayers.getValueAt(rowPlayer, 3).toString());
			playerMoneylblValue.setText(tablePlayers.getValueAt(rowPlayer, 4).toString());
			playerCombatlblValue.setText(repo.GetSelectedPlayerCombatStrength(id).toString());
		} 
	 class RegisterPlayer implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {			
				if(!playerClasstxt.getText().isBlank() && !PlayerNametxt.getText().isBlank()) {
					Player player = new Player(PlayerNametxt.getText(),playerClasstxt.getText());
					repo.AddNewPlayer(player);
					RefreshTable();
				}
			}
			
		}
	 class Quest implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {			
				if(id > -1) {
					repo.DoQuest(id);
					RefreshTable();
					RefreshMidPanelInfo();
				}
			}
			
		}
	 class Refresh implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {			
				RefreshTable();
				if(id > -1)
				{
					RefreshMidPanelInfo();
				}
			}
			
		}
}

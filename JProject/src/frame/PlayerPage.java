package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import app.DB.DBRepo;

public class PlayerPage extends Page{
	Integer id = -1;
	Integer itemid = -1;
	Integer rowPlayer = 0;
	JTable tablePlayers = new JTable();
	JScrollPane myScrollPlayers = new JScrollPane(tablePlayers);
	JPanel searchPanel = new JPanel();
	JButton searchbtn = new JButton("Search");
	JButton showAllbtn = new JButton("Show all players");
	JLabel searchnamelbl = new JLabel("Search by name");
	JLabel searchclasslbl = new JLabel("Search by class");
	JTextField searchnametxt = new JTextField();
	JTextField searchclasstxt = new JTextField();
	JPanel PlayerPanel = new JPanel();
	JTable tablePlayerItems = new JTable();
	JScrollPane myScrollPlayerItems = new JScrollPane(tablePlayerItems);
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
	JLabel playerPartylbl = new JLabel("Current party: ");
	JPanel ItemPanel = new JPanel();
	JLabel itemNamelbl = new JLabel("Name: ");
	JLabel itemSlotlbl = new JLabel("Slot: ");
	JLabel itemCValuelbl = new JLabel("Combat Strength: ");
	JLabel itemStatelbl = new JLabel("State: ");
	JButton itemSendModbtn = new JButton("Send for modification");
	JButton openShop = new JButton("Open shop");
	JPanel inventoryPanel = new JPanel();
	JPanel shopPanel = new JPanel();
	JTable tableShopitems = new JTable();
	JScrollPane myScrollShop = new JScrollPane(tableShopitems);
	JPanel shopMenu = new JPanel();
	JLabel shopItemNamelbl = new JLabel("Name: ");
	JLabel shopItemSlotlbl = new JLabel("Slot: ");
	JLabel shopItemCValuelbl = new JLabel("Combat Strength: ");
	JLabel shopItemCostlbl = new JLabel("Price: ");
	JLabel shopItemStatelbl = new JLabel("State: ");
	JButton buybtn = new JButton("Buy");
	JButton cancelbtn = new JButton("Cancel");
	JButton retirebtn = new JButton("Retire");
	JPanel midRightPanel = new JPanel();
	JButton refreshbtn = new JButton("Refresh");
	boolean canbuy = false;
	public PlayerPage(DBRepo repository){
		repo = repository;
		name = "Personal room";
		upPanel = new JPanel();
		upPanel.setLayout(new GridLayout(2,1));
		midPanel = new JPanel();
		downPanel = new JPanel();
			
			//---top---
			//player stats
		tablePlayers.addMouseListener(new MouseActionPlayer());
		myScrollPlayers.setPreferredSize(new Dimension(500,200));
		upPanel.add(myScrollPlayers);
		searchPanel.setLayout(new GridLayout(3,2));
		searchPanel.add(searchnamelbl);
		searchPanel.add(searchnametxt);
		searchPanel.add(searchclasslbl);
		searchPanel.add(searchclasstxt);
		searchPanel.add(searchbtn);
		searchbtn.addActionListener(new Search());
		searchPanel.add(showAllbtn);
		showAllbtn.addActionListener(new ShowAll());
		upPanel.add(searchPanel);
			//---mid---
		midPanel.setLayout(new GridLayout(1,2));
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
		midPanel.add(PlayerPanel);
		midRightPanel.setLayout(new GridLayout(3,1));
		midRightPanel.add(playerPartylbl);
		midRightPanel.add(retirebtn);
		refreshbtn.addActionListener(new Refresh());
		midRightPanel.add(refreshbtn);
		retirebtn.addActionListener(new Retire());
		midPanel.add(midRightPanel);
			//table (connecting table id equipment id player), choose item to send to smithy
			
			
			
			//---bot---
		myScrollPlayerItems.setPreferredSize(new Dimension(200,200));
		tablePlayerItems.addMouseListener(new MouseActionItem());
		inventoryPanel.setLayout(new GridLayout(1,2));
		inventoryPanel.add(myScrollPlayerItems);
		ItemPanel.setLayout(new GridLayout(0,1));
		ItemPanel.add(itemNamelbl);
		ItemPanel.add(itemSlotlbl);
		ItemPanel.add(itemCValuelbl);
		ItemPanel.add(itemStatelbl);
		itemSendModbtn.addActionListener(new SendForModification());
		ItemPanel.add(itemSendModbtn);
		openShop.addActionListener(new OpenShop());
		ItemPanel.add(openShop);
		inventoryPanel.add(ItemPanel);
		tableShopitems.addMouseListener(new MouseActionShop());	
		shopMenu.setLayout(new  GridLayout(0,1));
		shopMenu.add(shopItemNamelbl);
		shopMenu.add(shopItemSlotlbl);
		shopMenu.add(shopItemCValuelbl);
		shopMenu.add(shopItemCostlbl);
		shopMenu.add(shopItemStatelbl);
		buybtn.addActionListener(new BuyItem());
		shopMenu.add(buybtn);
		cancelbtn.addActionListener(new CloseShop());
		shopMenu.add(cancelbtn);		
		
		downPanel.setLayout(new BorderLayout());
		downPanel.add(inventoryPanel,BorderLayout.CENTER);
		//ActivateInventoryPanel();
		//ActivateShopPanel();
			// if not in party"Currently not in a party" else party name and stats
		RefreshTable();
	 }	
	public void ClearSelection() {
		id = -1;
		itemid = -1;
		playerNamelblValue.setText("");
		playerClasslblValue.setText("");
		playerRenownlblValue.setText("");
		playerMoneylblValue.setText("");
		playerCombatlblValue.setText("");
		playerPartylbl.setText("Current party: ");
		itemNamelbl.setText("Name: ");
		itemSlotlbl.setText("Slot: ");
		itemCValuelbl.setText("Combat Strength: ");
		itemStatelbl.setText("State: ");
	} 
	private void RefreshItemTable() {
		tablePlayerItems.setModel(repo.GetPlayerEquipment(id));
	}
	private void RefreshTable() {
		tablePlayers.setModel(repo.GetAllPlayers());
	}
	private void RefreshShopTable() {
		tableShopitems.setModel(repo.GetUnownedItemTable());
	}
	private void ActivateInventoryPanel() {		
		downPanel.remove(0);
		inventoryPanel = new JPanel();
		inventoryPanel.setLayout(new GridLayout(1,2));
		inventoryPanel.add(myScrollPlayerItems);
		inventoryPanel.add(ItemPanel);
		downPanel.add(inventoryPanel,BorderLayout.CENTER);
		CloseShop();
		downPanel.revalidate();
		RefreshItemTable();
	}
	private void ActivateShopPanel() {
		downPanel.remove(0);
		shopPanel = new JPanel();
		shopPanel.setLayout(new GridLayout(1,2));
		shopPanel.add(myScrollShop);
		shopPanel.add(shopMenu);
		downPanel.add(shopPanel,BorderLayout.CENTER);
		downPanel.revalidate();
		RefreshItemTable();
	}
	private void CloseShop() {
		itemid = -1;
		itemNamelbl.setText("Name: ");
		itemSlotlbl.setText("Slot: ");
		itemCValuelbl.setText("Combat Strength: ");
		itemStatelbl.setText("State: ");		
		shopItemNamelbl.setText("Name: ");
		shopItemSlotlbl.setText("Slot: ");
		shopItemCValuelbl.setText("Combat Strength: ");
		shopItemCostlbl.setText("Price: ");
		shopItemStatelbl.setText("State: ");
	}
	private void RefreshMidPanelInfo() {

		playerNamelblValue.setText(tablePlayers.getValueAt(rowPlayer, 1).toString());
		playerClasslblValue.setText(tablePlayers.getValueAt(rowPlayer, 2).toString());
		playerRenownlblValue.setText(tablePlayers.getValueAt(rowPlayer, 3).toString());
		playerMoneylblValue.setText(tablePlayers.getValueAt(rowPlayer, 4).toString());
		playerCombatlblValue.setText(repo.GetSelectedPlayerCombatStrength(id).toString());
		playerPartylbl.setText("Current party: " + repo.GetPlayerPartyName(id));
	} 
	 class MouseActionPlayer implements  MouseListener{


			@Override
			public void mouseClicked(MouseEvent e) {			
				rowPlayer = tablePlayers.getSelectedRow();
				id = Integer.parseInt(tablePlayers.getValueAt(rowPlayer, 0).toString());
				RefreshItemTable();
				RefreshMidPanelInfo() ;
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
	 class MouseActionItem implements  MouseListener{


			@Override
			public void mouseClicked(MouseEvent e) {
				
				int row = tablePlayerItems.getSelectedRow();
				
				itemid = Integer.parseInt(tablePlayerItems.getValueAt(row, 0).toString());
				itemNamelbl.setText("Name: "+tablePlayerItems.getValueAt(row, 1).toString());
				itemCValuelbl.setText("Combat Strength: "+tablePlayerItems.getValueAt(row, 3).toString());
				itemSlotlbl.setText("Slot: "+tablePlayerItems.getValueAt(row, 2).toString());
				itemStatelbl.setText("State: "+tablePlayerItems.getValueAt(row, 4).toString());
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
	
	
	class Search implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {			
			if(!searchnametxt.getText().isBlank() && searchclasstxt.getText().isBlank()) {
				tablePlayers.setModel(repo.GetPlayersByName(searchnametxt.getText().toString()));
			}
			else if(searchnametxt.getText().isBlank() && !searchclasstxt.getText().isBlank()){
				tablePlayers.setModel(repo.GetPlayersByClass(searchclasstxt.getText().toString()));
			}
			else if(!searchnametxt.getText().isBlank() && !searchclasstxt.getText().isBlank()) {
				tablePlayers.setModel(repo.GetPlayersByClassAndName(searchnametxt.getText().toString(),searchclasstxt.getText().toString()));
			}
		}
		
	}
	class Refresh implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {			
			RefreshTable();
			RefreshMidPanelInfo();
			RefreshShopTable();
			if(id > -1) {
				RefreshItemTable();
			}
		}
		
	}
	public static void infoBox(String infoMessage)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "Error", JOptionPane.INFORMATION_MESSAGE);
    }
	class SendForModification implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {			
			if(itemid > -1) {
				repo.SendItemForMod(itemid);
				RefreshItemTable();
			}
			else {
				infoBox("Select an item first");
			}
		}
		
	}
	class ShowAll implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {			
			RefreshTable();
		}
		
	}
	class OpenShop implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {	
			ActivateShopPanel();
			RefreshShopTable();
			itemid = -1;			
		}
		
	}
	class CloseShop implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {	
			
			ActivateInventoryPanel();
			 RefreshMidPanelInfo();
			 canbuy = false;
		}
		
	}
	class Retire implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {	
			if(id > -1) {
				repo.RetirePlayer(id);			
				ClearSelection();
				RefreshTable();
				//tablePlayerItems.setModel(null);
			}
		}
		
	}
	class BuyItem implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {			
			if(id > -1 && canbuy) {	
				Integer remainingMoney = Integer.parseInt(playerMoneylblValue.getText()) - (Integer)tableShopitems.getValueAt(tableShopitems.getSelectedRow(), 2);
				if(remainingMoney >= 0) {
					repo.BuyItem(id, itemid, remainingMoney);
					ActivateInventoryPanel();
					//RefreshTable();
					//RefreshMidPanelInfo();
					playerMoneylblValue.setText(repo.GetPlayerMoney(id).toString());
					playerCombatlblValue.setText(repo.GetSelectedPlayerCombatStrength(id).toString());
					canbuy = false;
				}
			}
			else {
				infoBox("Select a player and an item first!");
			}
		}
		
	}
	 class MouseActionShop implements  MouseListener{


			@Override
			public void mouseClicked(MouseEvent e) {
				
				int row = tableShopitems.getSelectedRow();
				canbuy = true;
				itemid = Integer.parseInt(tableShopitems.getValueAt(row, 0).toString());
				shopItemNamelbl.setText("Name: "+tableShopitems.getValueAt(row, 1).toString());
				shopItemCostlbl.setText("Price: "+tableShopitems.getValueAt(row, 2).toString());
				shopItemCValuelbl.setText("Combat Strength: "+tableShopitems.getValueAt(row, 3).toString());
				shopItemSlotlbl.setText("Slot: "+tableShopitems.getValueAt(row, 4).toString());
				shopItemStatelbl.setText("State: "+tableShopitems.getValueAt(row, 5).toString());
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

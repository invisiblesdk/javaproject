package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import app.DB.DBRepo;

public class ItemPage extends Page{
	
	Integer id = -1;
	String state = "n";
	JButton createBtn = new JButton("Create");
	JLabel itemNamelbl = new JLabel("Item Name:");
	JTextField itemNametxt = new JTextField("");
	JLabel itemTypelbl = new JLabel("Item Type:");
	String[] type = {"Weapon","Helm","Chest","Gloves","Legguards"};
	JComboBox<String> itemCombo = new JComboBox<String>(type);
	JLabel armourlbl = new JLabel("Armour value:");	
	JLabel damagelbl = new JLabel("Damage value:");
	JPanel armourPnl = new JPanel();
	JButton decreaseArmour = new JButton("-");
	JLabel showArmour = new JLabel("0");
	JButton increaseArmour = new JButton("+");
	JPanel damagePnl = new JPanel();
	JButton decreaseDamage = new JButton("-");
	JLabel showDamage = new JLabel("0");
	JButton increaseDamage = new JButton("+");
	JLabel itemValue = new JLabel("0");
	JPanel valuePanel = new JPanel();
	JLabel projectedValue= new JLabel("Value:");
	
	JButton getUnownedItemsbtn = new JButton("Show available items");
	JButton getOwnedItemsbtn = new JButton("Show items send for modification");
	JButton modifyItembtn = new JButton("Modify the selected item");
	JButton cancelbtn = new JButton("Cancel");
	JButton destroybtn = new JButton("Destroy item");
	JButton returnItembtn = new JButton("Return to owner");
	
	JPanel tablePanel = new JPanel();
	JLabel idlbl = new JLabel("Id");
	JLabel namelbl = new JLabel("Item name");
	JLabel valuelbl = new JLabel("Cost");
	JLabel cvaluelbl = new JLabel("Combat value");
	JLabel typelbl = new JLabel("Type");
	JLabel statelbl = new JLabel("State");
	JTable table = new JTable();
	JScrollPane myScroll = new JScrollPane(table);
	public ItemPage(DBRepo repository) {
		repo = repository;
		name = "Smithy";
		upPanel = new JPanel();
		midPanel = new JPanel();
		downPanel = new JPanel();
		
		//---mid---
		midPanel.setLayout(new GridLayout(3,2));
		midPanel.add(getUnownedItemsbtn);
		midPanel.add(getOwnedItemsbtn);
		midPanel.add(modifyItembtn);
		midPanel.add(cancelbtn);
		midPanel.add(returnItembtn);
		midPanel.add(destroybtn);
		cancelbtn.addActionListener(new Cancel());
		modifyItembtn.addActionListener(new Reforge());
		getOwnedItemsbtn.addActionListener(new ShowSendItems());
		getUnownedItemsbtn.addActionListener(new ShowUnownedItems());
		returnItembtn.addActionListener(new ReturnItem());
		destroybtn.addActionListener(new Delete());
		//---top---
		upPanel.setLayout(new GridLayout(0,2));
		upPanel.add(itemNamelbl);
		upPanel.add(itemNametxt);
		upPanel.add(itemTypelbl);
		upPanel.add(itemCombo);
		upPanel.add(armourlbl);
		armourPnl.setLayout(new GridLayout(1,3));
		decreaseArmour.addActionListener(new DecreaseArmour());
		armourPnl.add(decreaseArmour);
		armourPnl.add(showArmour);
		increaseArmour.addActionListener(new IncreaseArmour());
		armourPnl.add(increaseArmour);
		upPanel.add(armourPnl);
		upPanel.add(damagelbl);
		damagePnl.setLayout(new GridLayout(1,3));
		decreaseDamage.addActionListener(new DecreaseDamage());
		damagePnl.add(decreaseDamage);
		damagePnl.add(showDamage);
		increaseDamage.addActionListener(new IncreaseDamage());
		damagePnl.add(increaseDamage);
		upPanel.add(damagePnl);
		
		valuePanel.setLayout(new GridLayout(1,2));
		valuePanel.add(projectedValue);
		valuePanel.add(itemValue);
		upPanel.add(valuePanel);
		upPanel.add(createBtn);
		
		itemCombo.addItemListener(new Change());
		itemCombo.setSelectedIndex(1);
		itemCombo.setSelectedIndex(0);
		
		createBtn.addActionListener(new Create());
		RecalcValue();
		//---bot---
		//downPanel.add(table);
		myScroll.setPreferredSize(new Dimension(400,175));
		downPanel.add(myScroll);
		table.addMouseListener(new MouseAction());
		RefreshTable();
		
	}
	public void RemoveCreate() {
		upPanel.remove(createBtn);	
		upPanel.validate();
		
	}
	public void ActivateCreateBtn() {
		upPanel.add(createBtn);
		upPanel.validate();
		
	}
	public void RefreshTable() {
		try {
			if(repo.GetUnownedItemTable() != null) {
				table.setModel(repo.GetUnownedItemTable());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void RefreshTableSendItems() {
		try {
			if(repo.GetSendItemTable() != null) {
				table.setModel(repo.GetSendItemTable());
			}
						
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static void infoBox(String infoMessage)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "Error", JOptionPane.INFORMATION_MESSAGE);
    }
	public void CleanData() {
		ActivateCreateBtn();
		itemNametxt.setText("");
		itemCombo.setSelectedIndex(0);
		showArmour.setText("0");
		showDamage.setText("0");
		id = -1;
		state = "n";
		RecalcValue();
	}
	class Cancel implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			CleanData();			
		}
	}
	class MouseAction implements  MouseListener{


		@Override
		public void mouseClicked(MouseEvent e) {
			
			int row = table.getSelectedRow();
			
			id = Integer.parseInt(table.getValueAt(row, 0).toString());
			itemNametxt.setText(table.getValueAt(row, 1).toString());
			//itemValue.setText(table.getValueAt(row, 2).toString());
			if(table.getValueAt(row, 4).toString().equals("Weapon"))
			{
				itemCombo.setSelectedIndex(0);
			}
			else if(table.getValueAt(row, 4).toString().equals("Helm"))
			{
				itemCombo.setSelectedIndex(1);
			}
			else if(table.getValueAt(row, 4).toString().equals("Chest"))
			{
				itemCombo.setSelectedIndex(2);
			}
			else if(table.getValueAt(row, 4).toString().equals("Gloves"))
			{
				itemCombo.setSelectedIndex(3);
			}
			else if(table.getValueAt(row, 4).toString().equals("Legguards"))
			{
				itemCombo.setSelectedIndex(4);
			}
			if(table.getValueAt(row, 4).toString().equals("Weapon"))
			{
				showArmour.setText("0");
				showDamage.setText(table.getValueAt(row, 3).toString());
			}
			else {
				showArmour.setText(table.getValueAt(row, 3).toString());
				showDamage.setText("0");
			}
			RecalcValue();
			RemoveCreate();
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
	public void RecalcValue() {
		Integer Value = 10;
		float modifier = 1;
		String type = (String) itemCombo.getSelectedItem();
		if(type == "Helm") {
			modifier += 1.5;
		}
		else if(type == "Gloves") {
			modifier += 1;
		}
		else if(type == "Legguards") {
			modifier += 0.5;
		}
		Value += Integer.parseInt(showArmour.getText());
		Value += Integer.parseInt(showDamage.getText());
		if(type == "Weapon") {
			modifier +=  Double.parseDouble(showDamage.getText()) / 3;
		}
		if(type != "Weapon") {
			modifier +=  Double.parseDouble(showArmour.getText()) / 8;
		}
		this.itemValue.setText(Integer.toString(Math.round(Value * modifier)));
	}
	class Create implements ActionListener{ // Creates a new item in the database

		@Override
		public void actionPerformed(ActionEvent e) {
			if(!itemNametxt.getText().isBlank()) {
				Item newItem;
				if(itemCombo.getSelectedItem() == "Weapon") {
					newItem = new Item(itemNametxt.getText(),Integer.parseInt(itemValue.getText()),Integer.parseInt(showDamage.getText()),itemCombo.getSelectedItem().toString(),state);
				}
				else {
					newItem = new Item(itemNametxt.getText(),Integer.parseInt(itemValue.getText()),Integer.parseInt(showArmour.getText()),itemCombo.getSelectedItem().toString(),state);
				}
				repo.AddItem(newItem);
				RefreshTable();
			}
			else {
				infoBox("Name is INVALID!");
			}
			
		}
		
	}
	class Reforge implements ActionListener { // Update the data about the selected item

		@Override
		public void actionPerformed(ActionEvent e) {
			if(!itemNametxt.getText().isBlank() && id>=0) {
				Item newItem;
				if(itemCombo.getSelectedItem() == "Weapon") {
					newItem = new Item(itemNametxt.getText(),Integer.parseInt(itemValue.getText()),Integer.parseInt(showDamage.getText()),itemCombo.getSelectedItem().toString(), state);
				}
				else {
					newItem = new Item(itemNametxt.getText(),Integer.parseInt(itemValue.getText()),Integer.parseInt(showArmour.getText()),itemCombo.getSelectedItem().toString(), state);
				}
				repo.UpdateItem(newItem,id);
				RefreshTable();
				//RefreshTableSendItems();
				CleanData();
			}
			else {
				infoBox("You have NOT SELECTED an item to modify or the name is INVALID or both..");
			}
			
		}
		
	}
	class Delete implements ActionListener{ //Remove selected item from the database

		@Override
		public void actionPerformed(ActionEvent e) {
			if(id >= 0 ) {
				repo.DeleteItems(id);	
				id = -1;
				RefreshTable();
				CleanData();
			}
			else {
				infoBox("No existing item is SELECTED!");
			}
			
		}		
	}
	
	
	class ShowUnownedItems implements ActionListener{ //Loads the table with items without owners

		@Override
		public void actionPerformed(ActionEvent e) {
			if(repo.GetUnownedItemTable() != null) {
				RefreshTable();
			}
			else{
				infoBox("Requested table is empty! CREATE a new item to fill it!");
			}
		}
		
	}

	class ReturnItem implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(id > -1) {
				repo.ReturnItem(id);
				RefreshTableSendItems();
			}
			else{
				infoBox("Select an item to return");
			}
		}
		
	}
	class ShowSendItems implements ActionListener{ //Loads the table with owned items send for modification

		@Override
		public void actionPerformed(ActionEvent e) {
			if(repo.GetSendItemTable() != null) {
				RefreshTableSendItems();
			}
			else{
				infoBox("Requested table is empty! Unless a player SENDS his/her item it will be empty!");
			}
		}
		
	}
	
	
	class Change implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			if(itemCombo.getSelectedItem() == "Weapon") {
				damagePnl.setVisible(true);
				showArmour.setText("0");	
				armourPnl.setVisible(false);
			}
			else {
				armourPnl.setVisible(true);
				showDamage.setText("0");
				damagePnl.setVisible(false);
			}
			RecalcValue();
			
		}

		
	}
	class DecreaseArmour implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(itemCombo.getSelectedItem() != "Weapon") {				
			int currentVal = Integer.parseInt(showArmour.getText());
			if(currentVal > 0) {
				currentVal--;
			}
			showArmour.setText(Integer.toString(currentVal));			
			}
			RecalcValue();
			
		}
		
	}
	class IncreaseArmour implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(itemCombo.getSelectedItem() != "Weapon") {				
				int currentVal = Integer.parseInt(showArmour.getText());
				currentVal++;
				showArmour.setText(Integer.toString(currentVal));
				}
			RecalcValue();
			
		}
		
	}
	class DecreaseDamage implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(itemCombo.getSelectedItem() == "Weapon") {				
				int currentVal = Integer.parseInt(showDamage.getText());
				if(currentVal > 0) {
					currentVal--;
				}
				showDamage.setText(Integer.toString(currentVal));
				}
			RecalcValue();
			
		}
		
	}
	class IncreaseDamage implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(itemCombo.getSelectedItem() == "Weapon") {				
				int currentVal = Integer.parseInt(showDamage.getText());
				currentVal++;
				showDamage.setText(Integer.toString(currentVal));
				}
			RecalcValue();
		}
		
	}
}

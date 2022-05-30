package app.DB;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import frame.Item;
import frame.Player;

public class DBRepo {	
	Connection conn = null;
	PreparedStatement state = null;
	ResultSet result = null;
	String sql = "";
	
	public DBRepo() {
		conn = DBConnection.getConnection();
		try {		
			sql="CREATE TABLE IF NOT EXISTS ITEMS(\r\n"
					+ "id int primary key AUTO_INCREMENT,\r\n"
					+ "name varchar(255) not null,\r\n"
					+ "goldValue int not null,\r\n"
					+ "CombatValue int not null,\r\n"
					+ "type varchar(30) not null,\r\n"
					+ "state varchar(1) not null)";
			state = conn.prepareStatement(sql);			
			state.execute();
			sql="CREATE TABLE IF NOT EXISTS Players(\r\n"
					+ "id int primary key AUTO_INCREMENT,\r\n"
					+ "name varchar(255) not null,\r\n"
					+ "class varchar(255) not null,\r\n"
					+ "Renown int,\r\n"
					+ "Money int)";
			state = conn.prepareStatement(sql);			
			state.execute();
			sql="CREATE TABLE IF NOT EXISTS Parties(\r\n"
					+ "id int primary key AUTO_INCREMENT,\r\n"
					+ "name varchar(255) not null)";
			state = conn.prepareStatement(sql);			
			state.execute();
			sql="\r\n"
					+ "CREATE TABLE IF NOT EXISTS Party_Players(\r\n"
					+ "id int primary key AUTO_INCREMENT,\r\n"
					+ "party_id int not null,\r\n"
					+ "player_id int not null,\r\n"
					+ "FOREIGN KEY(party_id) REFERENCES Parties(id),\r\n"
					+ "FOREIGN KEY(player_id) REFERENCES Players(id))";
			state = conn.prepareStatement(sql);			
			state.execute();
			sql="CREATE TABLE IF NOT EXISTS Player_Items(\r\n"
					+ "id int primary key AUTO_INCREMENT,\r\n"
					+ "item_id int not null,\r\n"
					+ "player_id int not null,\r\n"
					+ "FOREIGN KEY(item_id) REFERENCES Items(id),\r\n"
					+ "FOREIGN KEY(player_id) REFERENCES Players(id))";
			state = conn.prepareStatement(sql);			
			state.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
		    try { result.close(); } catch (Exception e) { /* Ignored */ }
		    try { state.close(); } catch (Exception e) { /* Ignored */ }
		    try { conn.close(); } catch (Exception e) { /* Ignored */ }
		}
		
		
	}
	
	
	public void AddItem(Item item) {
		sql = "insert into items(name,goldvalue,combatvalue,type,state) values(?,?,?,?,?)";
		conn = DBConnection.getConnection();
		try {
			state = conn.prepareStatement(sql);
			state.setString(1, item.Name);
			state.setInt(2, item.GoldValue);
			state.setInt(3, item.CombatValue);
			state.setString(4, item.Type);
			state.setString(5, item.State);
			state.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
		    try { result.close(); } catch (Exception e) { /* Ignored */ }
		    try { state.close(); } catch (Exception e) { /* Ignored */ }
		    try { conn.close(); } catch (Exception e) { /* Ignored */ }
		}
	}
	public void AddParty(String name) {
		sql = "insert into parties(name) values(?)";
		conn = DBConnection.getConnection();
		try {
			state = conn.prepareStatement(sql);
			state.setString(1, name);
			state.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
		    try { result.close(); } catch (Exception e) { /* Ignored */ }
		    try { state.close(); } catch (Exception e) { /* Ignored */ }
		    try { conn.close(); } catch (Exception e) { /* Ignored */ }
		}
	}
	public void UpdatePartyname(Integer id,String name) {
		sql = "update parties set name = ? where id =?";
		conn = DBConnection.getConnection();
		try {
			state = conn.prepareStatement(sql);
			state.setString(1, name);
			state.setInt(2, id);
			state.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
		    try { result.close(); } catch (Exception e) { /* Ignored */ }
		    try { state.close(); } catch (Exception e) { /* Ignored */ }
		    try { conn.close(); } catch (Exception e) { /* Ignored */ }
		}
	}
	public void AddPlayerToParty(Integer partyid,Integer playerid) {
		sql = "insert into Party_Players(party_id,player_id) values(?,?)";
		conn = DBConnection.getConnection();
		try {
			state = conn.prepareStatement(sql);
			state.setInt(1, partyid);
			state.setInt(2, playerid);
			state.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
		    try { result.close(); } catch (Exception e) { /* Ignored */ }
		    try { state.close(); } catch (Exception e) { /* Ignored */ }
		    try { conn.close(); } catch (Exception e) { /* Ignored */ }
		}
	}
	public void AddNewPlayer(Player player) {
		sql = "insert into players(name,class,renown,money) values(?,?,?,?)"; 
		conn = DBConnection.getConnection();
		try {
			state = conn.prepareStatement(sql);	
			state.setString(1, player.Name);
			state.setString(2, player.Class);
			state.setInt(3, player.Renown);
			state.setFloat(4, player.Money);
			state.execute();			
		} catch (Exception e) {
			e.printStackTrace();			
		}
		 finally {
		  try { result.close(); } catch (Exception e) { /* Ignored */ }
		  try { state.close(); } catch (Exception e) { /* Ignored */ }
		  try { conn.close(); } catch (Exception e) { /* Ignored */ }
		} 
	}
	public MyModel GetPlayersByName(String name) {
		sql = "select * from Players where name=?"; 
		conn = DBConnection.getConnection();
		try {
			state = conn.prepareStatement(sql);	
			state.setString(1, name);
			result = state.executeQuery();
			return new MyModel(result);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		// finally {
		//  try { result.close(); } catch (Exception e) { /* Ignored */ }
		//  try { state.close(); } catch (Exception e) { /* Ignored */ }
		//  try { conn.close(); } catch (Exception e) { /* Ignored */ }
		//} 
	}
	public MyModel GetPlayersByClass(String _class) {
		sql = "select * from Players where class=?"; 
		conn = DBConnection.getConnection();
		try {
			state = conn.prepareStatement(sql);
			state.setString(1, _class);
			result = state.executeQuery();
			return new MyModel(result);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		// finally {
		//  try { result.close(); } catch (Exception e) { /* Ignored */ }
		//  try { state.close(); } catch (Exception e) { /* Ignored */ }
		//  try { conn.close(); } catch (Exception e) { /* Ignored */ }
		//} 
	}
	public MyModel GetItemByName(String name) {
		sql = "select * from items where name=?"; 
		conn = DBConnection.getConnection();
		try {
			state = conn.prepareStatement(sql);
			state.setString(1, name);
			result = state.executeQuery();
			return new MyModel(result);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		// finally {
		//  try { result.close(); } catch (Exception e) { /* Ignored */ }
		//  try { state.close(); } catch (Exception e) { /* Ignored */ }
		//  try { conn.close(); } catch (Exception e) { /* Ignored */ }
		//} 
	}
	public MyModel GetItemByCValue(Integer cvalue) {
		sql = "select * from items where CombatValue=?"; 
		conn = DBConnection.getConnection();
		try {
			state = conn.prepareStatement(sql);
			state.setInt(1, cvalue);
			result = state.executeQuery();
			return new MyModel(result);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		// finally {
		//  try { result.close(); } catch (Exception e) { /* Ignored */ }
		//  try { state.close(); } catch (Exception e) { /* Ignored */ }
		//  try { conn.close(); } catch (Exception e) { /* Ignored */ }
		//} 
	}
	public MyModel GetItemByCValueAndName(Integer cvalue,String name) {
		sql = "select * from items where CombatValue=? and name = ? "; 
		conn = DBConnection.getConnection();
		try {
			state = conn.prepareStatement(sql);
			state.setInt(1, cvalue);
			state.setString(2, name);
			result = state.executeQuery();
			return new MyModel(result);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		// finally {
		//  try { result.close(); } catch (Exception e) { /* Ignored */ }
		//  try { state.close(); } catch (Exception e) { /* Ignored */ }
		//  try { conn.close(); } catch (Exception e) { /* Ignored */ }
		//} 
	}
	public MyModel GetPartyByNameAndRenown(Integer renown,String name) {
		sql = "select p.id,p.name,sum(pl.renown) from parties p\r\n"
				+ "  inner join party_players pp on p.id = pp.party_id\r\n"
				+ " inner join players pl on pl.id=pp.player_id \r\n"
				+ "where p.name = ? \r\n"
				+ " group by p.id,p.name \r\n"
				+ "having  sum(pl.renown)= ? "; 
		conn = DBConnection.getConnection();
		try {
			state = conn.prepareStatement(sql);
			state.setString(1, name);
			state.setInt(2, renown);
			result = state.executeQuery();
			return new MyModel(result);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		// finally {
		//  try { result.close(); } catch (Exception e) { /* Ignored */ }
		//  try { state.close(); } catch (Exception e) { /* Ignored */ }
		//  try { conn.close(); } catch (Exception e) { /* Ignored */ }
		//} 
	}
	public MyModel GetPartyByName(String name) {
		sql = "select p.id,p.name,sum(pl.renown) from parties p\r\n"
				+ "  inner join party_players pp on p.id = pp.party_id\r\n"
				+ " inner join players pl on pl.id=pp.player_id \r\n"
				+ "where p.name = ? \r\n"
				+ " group by p.id,p.name \r\n"
				+ ""; 
		conn = DBConnection.getConnection();
		try {
			state = conn.prepareStatement(sql);
			state.setString(1, name);
			result = state.executeQuery();
			return new MyModel(result);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		// finally {
		//  try { result.close(); } catch (Exception e) { /* Ignored */ }
		//  try { state.close(); } catch (Exception e) { /* Ignored */ }
		//  try { conn.close(); } catch (Exception e) { /* Ignored */ }
		//} 
	}
	public MyModel GetPartyByRenown(Integer renown) {
		sql = "select p.id,p.name,sum(pl.renown) from parties p\r\n"
				+ "  inner join party_players pp on p.id = pp.party_id\r\n"
				+ " inner join players pl on pl.id=pp.player_id \r\n"
				+ "\r\n"
				+ " group by p.id,p.name \r\n"
				+ "having  sum(pl.renown)= ? "; 
		conn = DBConnection.getConnection();
		try {
			state = conn.prepareStatement(sql);
			state.setInt(1, renown);
			result = state.executeQuery();
			return new MyModel(result);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		// finally {
		//  try { result.close(); } catch (Exception e) { /* Ignored */ }
		//  try { state.close(); } catch (Exception e) { /* Ignored */ }
		//  try { conn.close(); } catch (Exception e) { /* Ignored */ }
		//} 
	}
	public MyModel GetPlayersByClassAndName(String name, String _class) {
		sql = "select * from Players where name=? and class=?"; 
		conn = DBConnection.getConnection();
		try {
			state = conn.prepareStatement(sql);	
			state.setString(1, name);
			state.setString(2, _class);
			result = state.executeQuery();
			return new MyModel(result);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		// finally {
		//  try { result.close(); } catch (Exception e) { /* Ignored */ }
		//  try { state.close(); } catch (Exception e) { /* Ignored */ }
		//  try { conn.close(); } catch (Exception e) { /* Ignored */ }
		//} 
	}
	public MyModel GetAllPlayers() {
		sql = "select * from Players"; 
		conn = DBConnection.getConnection();
		try {
			state = conn.prepareStatement(sql);				
			result = state.executeQuery();
			return new MyModel(result);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		// finally {
		//  try { result.close(); } catch (Exception e) { /* Ignored */ }
		//  try { state.close(); } catch (Exception e) { /* Ignored */ }
		//  try { conn.close(); } catch (Exception e) { /* Ignored */ }
		//} 
	}
	public MyModel GetPartyTable() {
		sql = "select * from Parties"; 
		conn = DBConnection.getConnection();
		try {
			state = conn.prepareStatement(sql);			
			result = state.executeQuery();
			return new MyModel(result);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		// finally {
		//  try { result.close(); } catch (Exception e) { /* Ignored */ }
		//  try { state.close(); } catch (Exception e) { /* Ignored */ }
		//  try { conn.close(); } catch (Exception e) { /* Ignored */ }
		//} 
	}
	public MyModel GetUnownedItemTable() {
		sql = "select * from Items where state =?"; 
		conn = DBConnection.getConnection();
		try {
			state = conn.prepareStatement(sql);
			state.setString(1, "n");
			result = state.executeQuery();
			return new MyModel(result);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		// finally {
		//  try { result.close(); } catch (Exception e) { /* Ignored */ }
		//  try { state.close(); } catch (Exception e) { /* Ignored */ }
		//  try { conn.close(); } catch (Exception e) { /* Ignored */ }
		//} 
	}
	public MyModel GetSendItemTable() {
		sql = "select * from Items where state =?"; 
		conn = DBConnection.getConnection();
		try {
			state = conn.prepareStatement(sql);
			state.setString(1, "m");
			result = state.executeQuery();			
			return new MyModel(result);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		// finally {
		//   try { result.close(); } catch (Exception e) { /* Ignored */ }
		//    try { state.close(); } catch (Exception e) { /* Ignored */ }
		//    try { conn.close(); } catch (Exception e) { /* Ignored */ }
		//} 
	}
	public void DeleteItems(Integer id) {
		sql = "delete from player_items where item_id=?";
		conn = DBConnection.getConnection();
		try {
			state = conn.prepareStatement(sql);
			state.setInt(1, id);
			state.execute();
			
			
			
			sql = "delete from Items where id=?";
			state = conn.prepareStatement(sql);
			state.setInt(1, id);
			state.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
		    try { result.close(); } catch (Exception e) { /* Ignored */ }
		    try { state.close(); } catch (Exception e) { /* Ignored */ }
		    try { conn.close(); } catch (Exception e) { /* Ignored */ }
		}
	}
	public void RetirePlayer(Integer id) {
		conn = DBConnection.getConnection();
		try {	
			sql = "SELECT pi.player_id, i.id \r\n"
					+ "FROM Player_items pi inner join Items i on pi.item_id=i.id \r\n"
					+ " where pi.player_id=?";
			state = conn.prepareStatement(sql);
			state.setInt(1, id);
			result = state.executeQuery();
			while (result.next()) {
				sql = "UPDATE Items SET state=? WHERE id=?";
				state = conn.prepareStatement(sql);
				state.setString(1, "n");
				int itemid = result.getInt(2);
				state.setInt(2, itemid);
				state.execute();
				
			}
			
			sql = "delete from player_items where player_id=?";
			state = conn.prepareStatement(sql);
			state.setInt(1, id);
			state.execute();
			
			sql = "delete from Party_Players where player_id=?";
			state = conn.prepareStatement(sql);
			state.setInt(1, id);
			state.execute();

			sql = "delete from Players where id=?";
			state = conn.prepareStatement(sql);
			state.setInt(1, id);
			state.execute();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
		    try { result.close(); } catch (Exception e) { /* Ignored */ }
		    try { state.close(); } catch (Exception e) { /* Ignored */ }
		    try { conn.close(); } catch (Exception e) { /* Ignored */ }
		}
	}
	public void DeletePartyMember(Integer party_memberid) {
		sql = "delete from Party_Players where player_id=?";
		conn = DBConnection.getConnection();
		try {
			state = conn.prepareStatement(sql);
			state.setInt(1, party_memberid);
			state.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
		    try { result.close(); } catch (Exception e) { /* Ignored */ }
		    try { state.close(); } catch (Exception e) { /* Ignored */ }
		    try { conn.close(); } catch (Exception e) { /* Ignored */ }
		}
	}	
	public void DeleteParty(Integer partyid) {
		sql = "delete from party_players where party_id=?";
		conn = DBConnection.getConnection();
		try {
			state = conn.prepareStatement(sql);
			state.setInt(1, partyid);
			state.execute();
			sql = "delete from parties where id=?";
			state = conn.prepareStatement(sql);
			state.setInt(1, partyid);
			state.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
		    try { result.close(); } catch (Exception e) { /* Ignored */ }
		    try { state.close(); } catch (Exception e) { /* Ignored */ }
		    try { conn.close(); } catch (Exception e) { /* Ignored */ }
		}
	}
	public Integer GetPlayerMoney(Integer id) {
		sql = "Select money from players where id =?";
		conn = DBConnection.getConnection();
		try {
			state = conn.prepareStatement(sql);
			state.setInt(1, id);
			result = state.executeQuery();
			if(result.first()) {
				return result.getInt(1);
			}
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		finally {
		    try { result.close(); } catch (Exception e) { /* Ignored */ }
		    try { state.close(); } catch (Exception e) { /* Ignored */ }
		    try { conn.close(); } catch (Exception e) { /* Ignored */ }
		}
	}
	private Integer GetPlayerRenown(Integer id) {
		sql = "Select renown from players where id =?";
		conn = DBConnection.getConnection();
		try {
			state = conn.prepareStatement(sql);
			state.setInt(1, id);
			result = state.executeQuery();
			if(result.first()) {
				return result.getInt(1);
			}
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		finally {
		    try { result.close(); } catch (Exception e) { /* Ignored */ }
		    try { state.close(); } catch (Exception e) { /* Ignored */ }
		    try { conn.close(); } catch (Exception e) { /* Ignored */ }
		}
	}
	public void DoQuest(Integer playerid) {
		try {
			int newMoney = GetPlayerMoney(playerid);
			newMoney += GetPlayerRenown(playerid) + 5;
			int newRenown = GetPlayerRenown(playerid);
			newRenown += 1;
			sql = "update players set money = ?,renown = ? where id = ?";
			conn = DBConnection.getConnection();
			state = conn.prepareStatement(sql);
			state.setInt(1, newMoney);
			state.setInt(2, newRenown);
			state.setInt(3, playerid);
			state.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
		    try { result.close(); } catch (Exception e) { /* Ignored */ }
		    try { state.close(); } catch (Exception e) { /* Ignored */ }
		    try { conn.close(); } catch (Exception e) { /* Ignored */ }
		}
	}	
	public Integer GetSelectedPlayerCombatStrength(Integer playerid) {
		
		sql = "SELECT pi.player_id, Sum(i.combatvalue)\r\n"
				+ "FROM Player_items pi inner join Items i on pi.item_id=i.id\r\n"
				+ "where pi.player_id=?\r\n"
				+ "GROUP BY pi.player_id\r\n";
		conn = DBConnection.getConnection();
		try {
			state = conn.prepareStatement(sql);
			state.setInt(1, playerid);
			result = state.executeQuery();			
			if(result.first()) {
			return result.getInt(2);
			}
			else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		finally {
		    try { result.close(); } catch (Exception e) { /* Ignored */ }
		    try { state.close(); } catch (Exception e) { /* Ignored */ }
		    try { conn.close(); } catch (Exception e) { /* Ignored */ }
		}
	}
	
	public String GetPlayerPartyName(Integer playerid) {
		
		sql = "SELECT pa.name \r\n"
				+ " FROM players p inner join party_players pp on p.id=pp.player_id "
				+ " inner join parties pa on pa.id= pp.party_id "
				+ " where p.id=?\r\n "
				+ "";
		conn = DBConnection.getConnection();
		try {
			state = conn.prepareStatement(sql);
			state.setInt(1, playerid);
			result = state.executeQuery();
			if(result.first()) {			
			
			return result.getString(1);
			}
			else {
				return "No party";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "No party";
		}
		finally {
		    try { result.close(); } catch (Exception e) { /* Ignored */ }
		    try { state.close(); } catch (Exception e) { /* Ignored */ }
		    try { conn.close(); } catch (Exception e) { /* Ignored */ }
		}
	}
	public void UpdateItem(Item item,Integer id) {
		sql = "UPDATE Items SET name=?, goldvalue=?,combatvalue=?,type=? WHERE id=?";
		conn = DBConnection.getConnection();
		try {
			state = conn.prepareStatement(sql);
			
			state.setString(1, item.Name);
			state.setInt(2, item.GoldValue);
			state.setInt(3, item.CombatValue);
			state.setString(4, item.Type);
			state.setInt(5, id);			
			
			state.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
		    try { result.close(); } catch (Exception e) { /* Ignored */ }
		    try { state.close(); } catch (Exception e) { /* Ignored */ }
		    try { conn.close(); } catch (Exception e) { /* Ignored */ }
		}
	}
	public void BuyItem(Integer player_id,Integer item_id, Integer remainingMoney) {
		sql = "UPDATE Items SET state=? WHERE id=?";
		conn = DBConnection.getConnection();
		try {
			state = conn.prepareStatement(sql);			
			state.setString(1, "o");
			state.setInt(2, item_id);			
			state.execute();
			
			sql = "UPDATE players SET money=? WHERE id=?";
			state = conn.prepareStatement(sql);			
			state.setInt(1, remainingMoney);
			state.setInt(2, player_id);			
			state.execute();
			
			sql = "insert into player_items(item_id,player_id) values(?,?)";
			state = conn.prepareStatement(sql);			
			state.setInt(1, item_id);
			state.setInt(2, player_id);			
			state.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
		    try { result.close(); } catch (Exception e) { /* Ignored */ }
		    try { state.close(); } catch (Exception e) { /* Ignored */ }
		    try { conn.close(); } catch (Exception e) { /* Ignored */ }
		}
	}
	public void SendItemForMod(Integer id) {
		sql = "UPDATE Items SET state=? WHERE id=?";
		conn = DBConnection.getConnection();
		try {
			state = conn.prepareStatement(sql);
			
			state.setString(1, "m");
			state.setInt(2, id);
			
			state.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
		    try { result.close(); } catch (Exception e) { /* Ignored */ }
		    try { state.close(); } catch (Exception e) { /* Ignored */ }
		    try { conn.close(); } catch (Exception e) { /* Ignored */ }
		}
	}
	public void ReturnItem(Integer id) {
		sql = "UPDATE Items SET state=? WHERE id=?";
		conn = DBConnection.getConnection();
		try {
			state = conn.prepareStatement(sql);
			
			state.setString(1, "o");
			state.setInt(2, id);
			
			state.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
		    try { result.close(); } catch (Exception e) { /* Ignored */ }
		    try { state.close(); } catch (Exception e) { /* Ignored */ }
		    try { conn.close(); } catch (Exception e) { /* Ignored */ }
		}
	}
	public MyModel GetPartyMemberTable(Integer partyid) {
		sql = "SELECT pl.id,pl.name,pl.class,pl.renown\r\n"
				+ "FROM Parties p \r\n"
				+ "INNER JOIN party_players pp ON p.id=pp.party_id\r\n"
				+ "inner join players pl on pp.player_id=pl.id "
				+ "Where p.id=?"; 
		conn = DBConnection.getConnection();
		try {
			state = conn.prepareStatement(sql);	
			state.setInt(1, partyid);
			result = state.executeQuery();
			return new MyModel(result);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		// finally {
		//  try { result.close(); } catch (Exception e) { /* Ignored */ }
		//  try { state.close(); } catch (Exception e) { /* Ignored */ }
		//  try { conn.close(); } catch (Exception e) { /* Ignored */ }
		//} 
	}
	public MyModel GetPlayerEquipment(Integer playerid) {
		sql = "SELECT i.id as itemid, i.name,i.type as slot,i.CombatValue,i.state\r\n"
				+ "FROM Players pl \r\n"
				+ "INNER JOIN player_items pi ON pl.id=pi.player_id\r\n"
				+ "inner join items i on pi.item_id=i.id "
				+ "Where pl.id=?"; 
		conn = DBConnection.getConnection();
		try {
			state = conn.prepareStatement(sql);	
			state.setInt(1, playerid);
			result = state.executeQuery();
			return new MyModel(result);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		// finally {
		//  try { result.close(); } catch (Exception e) { /* Ignored */ }
		//  try { state.close(); } catch (Exception e) { /* Ignored */ }
		//  try { conn.close(); } catch (Exception e) { /* Ignored */ }
		//} 
	}
	public MyModel GetFreePlayerTable() {
		sql = "select pl.id, pl.name, pl.class from Players pl \r\n"
				+ "where pl.id not in (Select player_id from party_players)"; 
		conn = DBConnection.getConnection();
		try {
			state = conn.prepareStatement(sql);			
			result = state.executeQuery();
			return new MyModel(result);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		// finally {
		//  try { result.close(); } catch (Exception e) { /* Ignored */ }
		//  try { state.close(); } catch (Exception e) { /* Ignored */ }
		//  try { conn.close(); } catch (Exception e) { /* Ignored */ }
		//} 
	}
}

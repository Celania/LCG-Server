package stores;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import units.Spell;
import units.Unit;
import simplemysql.SimpleMySQL;
import simplemysql.SimpleMySQLResult;

public class DBStore {

	public static Vector<Unit> units;	//consider a map
	public static Vector<Spell> spells;	//consider a map
	// Vector<Hero> heroes;
	
	private static SimpleMySQL mysql;
	
	public static Unit getUnit(int ID){
		if(ID < units.size())
			return units.elementAt(ID);
		return null;
	}

	public static Spell getSpell(int ID){
		if(ID < spells.size())
			return spells.elementAt(ID);
		return null;
	}

	
	public static void loadFile(String fileName) {
	}
	
	public static void loadDB(){
		units = new Vector<Unit>();
		spells = new Vector<Spell>();
		mysql = new SimpleMySQL();
		mysql.connect("localhost", "root", "", "lcg");
		
		loadCreatures();
		loadSpells();
		
		
	}
	
	private static void loadCreatures(){
		ResultSet rs = mysql.Query("SELECT * FROM creature_template").getResultSet();
		
		//process creatures
		try {
			while(rs.next()){
				//TODO pull effects for the unit from the DB and add them
				ResultSet rsEffect = mysql.Query("SELECT * FROM creature_effects").getResultSet();
				units.add(new Unit(rs.getInt("ID"),
									rs.getString("name"),
									rs.getInt("baseHealth"),
									rs.getInt("baseAttack"),
									rs.getInt("manaCost"),
									rs.getInt("baseStatusMask"),
									rs.getString("description")
								)
						);
			}
		} catch (SQLException e) {
			System.out.println("Problem loading creature DataSet!");
			e.printStackTrace();
		}
		
		System.out.println(units.size() + " Creatures loaded!");
		
	}
	
	private static void loadSpells(){
		SimpleMySQLResult result = mysql.Query("SELECT * FROM spell_template");
		ResultSet rs = result.getResultSet();
				
		//process spells
		while (result.next()){
			//pull effects from db and add to spell
			try {
				spells.add(new Spell(rs.getInt("ID"),
									rs.getString("name"),
									rs.getInt("manaCost"),
									rs.getString("description")));
			} catch (SQLException e) {
				System.out.println("Problem loading Data Set!");
				e.printStackTrace();
			}
			
		}
		
		System.out.println(spells.size() + " Spells loaded!");
		
	}

}

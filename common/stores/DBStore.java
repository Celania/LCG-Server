package stores;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import units.BaseSpell;
import units.BaseUnit;

public class DBStore {

    public static ArrayList<BaseUnit>  units; // consider a map
    public static ArrayList<BaseSpell> spells; // consider a map
    // Vector<Hero> heroes;

    public static BaseUnit getUnit(int ID) {
        if (ID < units.size()) return units.get(ID);
        return null;
    }

    public static BaseSpell getSpell(int ID) {
        if (ID < spells.size()) return spells.get(ID);
        return null;
    }

    public static void loadFile(String fileName) {
    }

    public static void loadDB() {
        units = new ArrayList<BaseUnit>(100);
        spells = new ArrayList<BaseSpell>(100);

    }

}

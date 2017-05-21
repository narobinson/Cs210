package adt;
import java.util.HashMap;

// Mapping: Table Name -> Table
public class Database extends HashMap<String, Table> {
	public Database() {
		super();
	}

	public Database(Database database) {
		super(database);
	}
}
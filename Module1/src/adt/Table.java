package adt;
import java.util.HashMap;

// Mapping: Primary Key Value -> Row
public class Table extends HashMap<Field<?>, Row> {
	
	public String primeCol;
	
	Table alias;
	
	public Table() {
		super();
	}

	public Table(Table table) {
		super(table);
	}
	
	public void makePrime(Row row){
		primeCol = row.getPrime();
	}
	
	public String getPrime(){
		return primeCol;
	}
	
	public void setAlias(Table alias){
		this.alias = alias; 
	}
	public Table getAlias(){
		return alias;
	}
	
}
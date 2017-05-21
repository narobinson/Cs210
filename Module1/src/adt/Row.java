package adt;
import java.util.HashMap;

// Mapping: Field Name -> Field Value
public class Row extends HashMap<String, Field<?>> {
	
	String prime;
	
    public Row() {
    	super();
    }

    public Row(Row row) {
    	super(row);
    }
    
    public void makePrime(String s){
    	prime = s;
    }
    
    public String getPrime(){
    	
    return prime;
    }

}
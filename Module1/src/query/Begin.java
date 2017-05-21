package query;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import adt.Database;
import adt.Field;
import adt.Row;
import adt.Table;


public class Begin implements Driver {
	
	Database temp;
	
	String action;
	
	boolean start = false;
	
	private static final Pattern pattern;
	static {
		pattern = Pattern.compile(
			"((Begin)?(RollBack)?(Commit)?){1}".replace(" ", "\\s*"),
			Pattern.CASE_INSENSITIVE
		);
	}
	
	
	@Override
	public boolean isValid(String query) {
		Matcher matcher = pattern.matcher(query.trim());
		
		if (!matcher.matches()) return false;
		
		action = matcher.group(1).trim();
		if(action.equalsIgnoreCase("begin")){
			 start = true;
		}
		return start;
	}
	
	public void begin(Database d) {
		
		String primeArray[] = new String[d.size()];
		for (int i = 0; i < d.size();i++){
		Table tempL = d.get(primeArray[i]);
		//System.out.println(tables[0]);
		
		Field[] primaryLeftArray = new Field[tempL.size()];
		tempL.keySet().toArray(primaryLeftArray);
		
		Row row = new Row();
		Table end= new Table();
		end.primeCol=tempL.primeCol;
			for (int j = 0; j < primaryLeftArray.length; j++){
				if(primaryLeftArray[j] != null){
					  row.putAll(tempL.get(tempL.get(primaryLeftArray[j])));
					  end.put(primaryLeftArray[j], row);
					  row = new Row();
				}
			}
			temp.put(primeArray[i], end);
		}
		
		
				
					
	}
	
	public Database rollback(){
		return temp;
		
	}
	@Override
	public Response CreateT(Database d) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Response dropResponse(Database d) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Response showResponse(Database d) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Response insertResponse(Database d) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Response selectTable(Database d) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Response getResponse() {
		// TODO Auto-generated method stub
		return null;
	}
}

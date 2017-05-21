package query;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import adt.Database;
import adt.Field;
import adt.Row;
import adt.Table;


public class ShowTable implements Driver {
	
	
	private static final Pattern pattern;
	static {
		pattern = Pattern.compile(
			"SHOW TABLES".replace(" ", "\\s*"),
			Pattern.CASE_INSENSITIVE
		);
	}

	@Override
	public boolean isValid(String query) {
		Matcher matcher = pattern.matcher(query.trim());
		
		if (!matcher.matches()) return false;
		
		return true;
	}
	
	public Response showResponse(Database d) 
	{
		String[] tables = new String[d.size()];
		
		Table table = new Table();
		
		d.keySet().toArray(tables);
		
		
		
		
		
		for(int i = 0; i < tables.length; i++){
			Row row = new Row();
			Field<String> prime = new Field<String>(tables[i]);
			row.put("TableName*",prime );
			int count = d.get(tables[i]).size();
			row.put("Row_Count", new Field<Integer> (count) );
			table.primeCol="Table_Name*";
			table.put(prime, row);
		}
		
		return new Response(true, Integer.toString(d.size()), table);
	}

	@Override
	public Response getResponse() {
		// TODO Auto-generated method stub
		return null;
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
	public void begin(Database d) {
		// TODO Auto-generated method stub
	}
}
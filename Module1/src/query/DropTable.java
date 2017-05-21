package query;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import adt.Database;
import adt.Table;

public class DropTable implements Driver{
	
	
	private String tableName;
	
	private static final Pattern pattern;
	
	static {
		pattern = Pattern.compile(
			"DROP TABLE ([a-zA-Z0-9_ ]+)".replace(" ", "\\s*"), Pattern.CASE_INSENSITIVE

		);
	}
	

	@Override
	public boolean isValid(String query) {
		//System.err.println("{" + query + "}");
		Matcher matcher = pattern.matcher(query.trim());
		
		if (!matcher.matches()) return false;
		

		tableName = matcher.group(1);
		
		return true;
	}
		
		
//		String[] dropTable;
//		dropTable = query.split(" ");
//		System.out.println(dropTable[0] + dropTable[1]);
//		if (dropTable[0].equals("DROP") && dropTable[1].equals("TABLE"))
//		{
//			tableName = dropTable[2];	
//		return true;
//		}
//		
//		else
//			return false;
//	}
//	
//	

	public Response dropResponse(Database d)
	{
		
		System.out.println(tableName);
		if (d.containsKey(tableName))
		{
		Table t = d.get(tableName);
		d.remove(tableName);
		return new Response(true, tableName, t);
		}
		else
			return new Response(false,"Table is not found");
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
	public void begin(Database d) {
		// TODO Auto-generated method stub
	}
}
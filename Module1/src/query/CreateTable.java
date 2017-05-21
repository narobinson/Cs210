package query;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import adt.Database;
import adt.Field;
import adt.Row;
import adt.Table;

public class CreateTable implements Driver {
	
	private String string;
	private String tableName;
	int count = 0;
	Table table = new Table();
	
	private static final Pattern pattern;
	static {
		pattern = Pattern.compile(
			"CREATE TABLE ([a-zA-Z0-9 _]+)[(]([a-zA-Z0-9 ,_]+)[)]".replace(" ", "\\s*"),
				//"CREATE TABLE ([a-zA-Z0-9_]+) ?(?: +)?[(](?:(?: +)?(((?: ?(PRIMARY)? ?([a-zA-z]+) ? +([a-zA-z_0-9]+)(?:[ ]?)?),?)+)[)])+".replace(" ", "\\s*"),
			Pattern.CASE_INSENSITIVE
		);
	}
	

	@Override
	public boolean isValid(String query) {
		
		Matcher matcher = pattern.matcher(query.trim());
		if (!matcher.matches()) return false;
//         String[] data = s.split("\\s+");
		tableName = matcher.group(1).trim();
		string = matcher.group(2).trim();
		//System.out.println(string);
//         if (matcher.group(3) != null )
//         prime = matcher.group(4);
//         value = matcher.group(5);
//         columnName = matcher.group(6);
		
		
		return true;
	}

	@Override
	public Response CreateT(Database d) {
		
		
		
		String[] str = string.split(",");
		
		int primeCheck = 0;
		
		Row row = new Row();
		
		
		
		for( int i = 0; i < str.length; i++)
		{
			String[] col = str[i].split(" ");
			
			for( int j = 0; j < col.length; j++)
			{
				//System.out.println(col[j]);
				//System.out.println(i);
				if ( col[j].equals("BOOLEAN"))
				{	
					//Row row = new Row();
					//System.out.println(col[j+1]);
					//Field<String> colName = new Field<String>(col[j+1]);
					Field<Boolean> regValue = new Field<Boolean>(false);
					row.put(col[j+1],regValue);
					//table.put(colName, row);
					count++;
				}
				if ( col[j].equals("INTEGER"))
				{
					//Row row = new Row();
					Field<Integer> regValue = new Field<Integer>(0);
					row.put(col[j+1],regValue);
					//table.put(colName, row);
					count++;
				}
				if ( col[j].equals("PRIMARY"))
				{
					//Row row = new Row();
					row.makePrime(col[j+2]);
					table.primeCol = col[j+2];
					primeCheck = 1;
					Field<String> regValue = new Field<String>("");
					row.put(col[j+2],regValue);
					count++;
					
				}
				if ( col[j].equals("STRING"))
				{	
//					Field<String> regValue = new Field<String>("");
//					row.put(col[j+1],regValue);
//					count++;
				}
				else{
					//System.out.println(table.toString());
				}
			}
		}
		
		if(primeCheck == 0 || primeCheck > 1)
			return new Response(false, "Check your prime ids", table);
		if(d.containsKey(tableName))
			return new Response(false, tableName);
		
		table.put(null, row);
		d.put(tableName, table);
		return  new Response(true,tableName+ " " + count, table);
		
		
		
	}

	@Override
	public Response getResponse() {
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
	public void begin(Database d) {
	}
		
	//CREATE TABLE ([a-zA-Z0-9_]+) ?(?: +)?[(](?:(?: +)?(((?: ?(PRIMARY)? ?([a-zA-z]+) ? +([a-zA-z_0-9]+)(?:[ ]?)?),?)+)[)])+

}

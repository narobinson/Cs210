package query;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import adt.Database;
import adt.Field;
import adt.Row;
import adt.Table;

public class InsertToTable implements Driver {
	
	private String tableName, string, string1;
	
	@SuppressWarnings("rawtypes")
	Field prime;
	
	
	private static final Pattern pattern;
	static {
		pattern = Pattern.compile(
			"Insert into ([a-zA-Z0-9_ ]+)(?:[(]([a-zA-Z_0-9 ,]+)[)])? +values +[(]([a-zA-Z0-9 ,@+\"-]+)[)]".replace(" ", "\\s*"),
			Pattern.CASE_INSENSITIVE
		);
	}
	@Override
	public boolean isValid(String query) {
		
		Matcher matcher = pattern.matcher(query.trim());
		
		if (!matcher.matches()) return false;
		

		tableName = matcher.group(1);
		
		string = matcher.group(2);
		
		string1 = matcher.group(3);
		
		return true;
	}
	public Response insertResponse( Database d) {
		
		String[] colName = null;
		
		String[] values = string1.split(",");
		
		tableName = tableName.trim();
		
		Table t = d.get(tableName);
		
		Table fin = new Table();
		
		if(string == null){
			Row row = new Row();
			row = t.get(null);
			Set<String> colSet = row.keySet();
			colName = new String[colSet.size()];
			colSet.toArray(colName);
			System.err.print(colName[0]);
		}
		
		if (string != null)
		colName = string.split(",");
		
		
		
		HashSet<String> dupCheck = new HashSet<String>();
		dupCheck.add(colName[0].trim());
		for( int i = 1; i < colName.length; i++){
			if(dupCheck.contains(colName[i].trim()))
				return new Response(false, "duplicate Table");
			dupCheck.add(colName[i].trim());
		}
		
		if(colName.length != values.length )
			return new Response (false, "Not the correct Length");
		
		
		
		Row row = new Row();
		
//		if(t.containsKey(null))
//		row = t.get(null);
		
		
		int count = 0;
		
		for( int i = 0; i < colName.length; i++){
			

			
			String value = values[i].trim();
			
			String col = colName[i].trim();
			
			if (value.equalsIgnoreCase("null"))
			{
				Field<String> tempField = new Field<String>("");
				row.put(col,tempField);
//				d.put(tableName, d.get(null));
//				return new Response(true, tableName,d.get(null) );
			}
			
//			if ( !row.isEmpty()){
//				System.out.println(row.get(t.getPrime()).data());
//				System.out.println();
//			if (row.get(t.getPrime()).data().equals(value))
//					return new Response(false);
//			}

//			System.err.print(col + "     ");
//			System.err.println(t.get(null).get(col).data().getClass() != Boolean.class);

			
			char check = value.charAt(0);
			//System.err.println("Check    " + check);
//			
//			if(i > values.length()){
//				
//				Field temp = new Field("");
//				 
//				row.put(colName[i],temp);
//				i++;
//				
//			}
		
			
			
			if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("FALSE"))
			{
				//System.err.println("BOOLEAN*****");
				
				if (t.get(null).get(col).data().getClass() !=  Boolean.class)
					return new Response(false);
				
				Field<Boolean> temp = new Field<Boolean>(Boolean.parseBoolean(value));
				
				
				
								
				
				if( col.equals(t.getPrime()))
				{
				row.remove(col);
				
				row.put(col, temp);
				//t.remove(null);
				t.put(temp, row);
				}
				else{
					row.remove(col);
					row.put(col, temp);
				}
			}
				
			
				
			else
			{
			if(check == '"'){
				
				//System.err.println("String*****");
				
				if (t.get(null).get(col).data().getClass() !=  String.class)
					return new Response(false, "Supposed to be String");
			
				Field<String> temp = new Field<String>(value);
			

			
			
			if( col.equalsIgnoreCase(t.getPrime()))
			{
			//row.remove(col);
			row.put(col, temp);
			row.makePrime(value);
			fin.primeCol=col;
			prime = temp;
			//t.remove(null);
			t.put(temp, row);
			}
			else{
				//row.remove(col);
				//row.makePrime(value);
				prime = temp;
				row.put(col, temp);
			}
			
	
			
			}
			
			
			if (check == '+' || check == '-' || (check >= '0' && check <= '9')){
				
			//System.err.println("Integer*****");
				
				if (check == '0' && value.length() != 1)
					return new Response(false, "Leading zeroes");
				
				if (t.get(null).get(col).data().getClass() !=  Integer.class)
					return new Response(false, "Supposed to be Integer Class");
					
				int integer = 0;
				
				if( check == '-')
				{
					 integer = Integer.parseInt(value.substring(1)) * -1;
				}
				if( check == '+')
				{
					integer = Integer.parseInt(value.substring(1));
				}
				else
				{
					integer =Integer.parseInt(value);
				}
				

				
				Field<Integer> temp = new Field<Integer>(integer);
												
				
				if( col.equals(row.getPrime()))
				{
				//row.remove(col);
				
				row.put(col, temp);
				row.makePrime(value);
				//t.remove(null);
				t.put(temp, row);
				}
				else{
					//row.remove(col);
					
					row.put(col, temp);
				}
				
			}
			
			//d.remove(tableName);
			d.put(tableName, t);
			
			
			
		}
			count++;
		}
		
		
		
		if( prime==null && count == values.length)
			return new Response(false, "No prime");
		
		String columnCheck[] = new String[t.get(null).size()];
		t.get(null).keySet().toArray(columnCheck);
		
		for (int x = 0; x < columnCheck.length; x++){
			if(!row.containsKey(columnCheck[x])){
				Field<String> blank = new Field<String>("");
			 
			row.put(columnCheck[x],blank);
			}
		}
				
	    
		fin.put(prime, row);
		
		
		

		return new Response(true, tableName + " " + count,fin );
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
	public Response getResponse() {
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

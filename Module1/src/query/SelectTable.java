package query;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import adt.Database;
import adt.Field;
import adt.Row;
import adt.Table;

public class SelectTable implements Driver {
	
	private String tableName, string;
	private Field<String> prime = null;
	private String[] tables;
	
	
	
	private static final Pattern pattern;
	static {
		pattern = Pattern.compile(
			"Select ([a-zA-Z_0-9 .,*]+) from ([a-zA-Z_0-9 ,]+)".replace(" ", "\\s*"),
			Pattern.CASE_INSENSITIVE
		);
	}
	
	
	public boolean isValid(String query) {
		
		Matcher matcher = pattern.matcher(query.trim());
		
		if (!matcher.matches()) return false;
		

		tableName = matcher.group(2);
		
		string = matcher.group(1);

		
		return true;
	}
	
	public Response selectTable( Database d) {
		
		Table table2 = new Table();
		
		int check = 0;
		
		
		if(containsJoin(tableName)){
			tableName = tableName.toLowerCase();
			tables = tableName.split("join");
			
			if(string.charAt(0)=='*'){
				
				//System.out.println("here");
				
				Table tempL = d.get(tables[0].trim());
				//System.out.println(tables[0]);
				
				Field[] primaryLeftArray = new Field[tempL.size()];
				tempL.keySet().toArray(primaryLeftArray);
				
				Table tempR = d.get(tables[1].trim());
				//System.out.println(tables[1].trim());
				
				
				Field[] primaryRightArray = new Field[tempR.size()];
				tempR.keySet().toArray(primaryRightArray);
				Row row = new Row();
				Table end= new Table();
				end.primeCol=tempL.primeCol;
					for (int j = 0; j < primaryRightArray.length; j++){
						if(primaryRightArray[j] != null){
						   Field left = containskey(tempL, primaryRightArray[j]);
						   if (left != null){
							  row.putAll(tempL.get(left));
							  row.putAll(tempR.get(primaryRightArray[j]));
							  end.put(left, row);
							  row = new Row();
						   }
						}
						
					
				}
				
				return new Response(true, tables[0] + "joined with" + tables[1], end);
				
			}
			
			//System.out.println("Contains works");
			 tableName = tableName.toLowerCase();
			
			String[] section = string.split(",");		
			
			
			tableName = tables[0].trim();
			
			//System.out.println(tableName);
			
			String[] tablesArray = new String[section.length];
			
			Table table = d.get(tableName);
	
			Row row2 = new Row();
			
			System.out.println(tableName);
			
					
			
			String colName[] = new String[section.length];
			
			String colNameAs[] = new String[section.length];
			
			for (int j = 0; j < section.length; j++)
			{
					
					section[j] = section[j].trim();
					if (section[j].contains("as") ){
					String[] temp = section[j].toLowerCase().split("as");
					colName[j] = temp[0].trim();
					colNameAs[j] = temp[1].trim();
					}
					if (section[j].contains(".") ){
						String[] temp = section[j].split(("\\."));
						colName[j] = temp[1].trim();
						colNameAs[j] = temp[1].trim();
						//System.err.println(colNameAs[j]);
						tablesArray[j] = temp[0].trim();
						table = d.get(tableName);
					}
					//System.err.println(colNameAs[j]);
			}
			
			
			
			Table end = d.get(tableName);
			
			Field primaryArray[] = new Field[end.size()];  
			
			end.keySet().toArray(primaryArray);
			
			
			for (int c = 1; c < primaryArray.length; c++){
				
				row2 = new Row();

				
				for(int z = 0; z < colName.length; z++){
					//System.out.println(colName.length);
					//System.err.println(tablesArray[z]);
					if(tablesArray[z] != "" && tablesArray[z] != null)
					{
						String tempTableName = tablesArray[z];
						Table tempTable = d.get(tempTableName);
						
	
						if (containskey(tempTable, primaryArray[c]) != null && primaryArray[c] != null)	
						{
							//System.out.println(primaryArray[c]);
							Row value = tempTable.get(containskey(tempTable, primaryArray[c]));
							Field tempValue = value.get(colName[z]);
							//System.err.println(colNameAs[z]);
							row2.put(colNameAs[z], tempValue);
							
						if (colName[z].equalsIgnoreCase(end.getPrime()))
						{
							check = 1;
							prime = tempValue;
							table2.primeCol = colNameAs[z];
						    row2.makePrime(prime.data);
						}
						}
		
					}
				else{
				Row value = end.get(primaryArray[c]);
				
				Field rowValue = value.get(colName[z]);
				
				row2.put(colNameAs[z], rowValue);
				}
				//System.err.println(colName[z] + " " + table.getPrime());
				
			
				
				if (colName[z].equalsIgnoreCase(table.getPrime()))
				{
					Row value = end.get(primaryArray[c]);
					Field rowValue = value.get(colName[z]);
					check = 1;
					prime = rowValue;
					table2.primeCol = colNameAs[z];
				    row2.makePrime(prime.data);
				}		
				
				//table2.put(prime, row2);
	            
				}
				
				if( row2.size() == colNameAs.length)
//					for(int i = 0; i < colNameAs.length; i++){
//						if(row2.containsKey(colNameAs[i]) || colNameAs[i] == null){
//						}
//							else{	
//							row2.put(colNameAs[i], new Field<String>(""));
//							}
//				}
				
			
				table2.put(prime, row2);
			}
			
			

		
		
			if(check == 0)
				return new Response(false, "No prime");		
		
		
			

			return new Response(true, tables[0] + "with" + tables[1] + " " + section.length, table2);
		
		
		}
		
		if(string.charAt(0)=='*'){
			
			
			Table temp = d.get(tableName);
			
			return new Response(true, tableName, temp);
			
		}
		
		
		
		
		String[] section = string.split(",");
		
		tableName = tableName.trim();
		
		Table table = d.get(tableName);
					
		Row row = new Row();
		
		Row row2 = new Row();
				
		String alias[] = new String[section.length];
		
		String colName[] = new String[section.length];
	
		
		for (int i = 0; i < section.length; i++)
		{
				String[] temp = section[i].toLowerCase().split("as");
				colName[i] = temp[0].trim();
				alias[i] = temp[1].trim();	
		}
				
		
				Table end = d.get(tableName);				
				
				Field primaryArray[] = new Field[end.size()];  
				
				end.keySet().toArray(primaryArray);
				
				//row2= new Row();
				

					for (int c = 0; c < primaryArray.length; c++){
						
					row2 = new Row();
					
					for(int z = 0; z < colName.length; z++){
					//row2 = new Row();
						
					
					
					Row value = end.get(primaryArray[c]);
					Field rowValue = value.get(colName[z]);
					
					
					row2.put(alias[z], rowValue);
					//System.err.println(colName[z] + " " + table.getPrime());
					
				
					
					if (colName[z].equalsIgnoreCase(table.getPrime()))
					{
						//System.err.println(colName[z] + " " + rowValue);
						check = 1;
						prime = rowValue;
						table2.primeCol = alias[z];
					    row2.makePrime(prime.data);
					}		
					
					//table2.put(prime, row2);
		            
					}
					table2.put(prime, row2);

				
				

					
		}
		
		
		

		
		System.out.println(check);
		if(check == 0)
			return new Response(false, "No prime");		

		return new Response(true, tableName + section.length, table2);
		

	}
	
	public boolean containsJoin( String tables ) {
			String joins = "join";
			tables = tables == null ? "" : tables;
			joins = joins == null ? "" : joins;
			return tables.toLowerCase().contains(joins.toLowerCase() );
	}
	
	public Field containskey(Table table, Field key ) {
		
		
		Field primaryArray[] = new Field[table.size()];  
		
		table.keySet().toArray(primaryArray);
		
		
		
		for(int i = 1; i < primaryArray.length; i++){
			if (primaryArray[i].data.equals(key.data))
				return primaryArray[i];
		}
		return null;
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
	public void begin(Database d) {
		// TODO Auto-generated method stub
	}
	
}

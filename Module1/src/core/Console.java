package core;

import java.util.Scanner;
import java.util.Set;

import adt.Field;
import adt.Row;
import adt.Table;
import query.Response;

public class Console {
	public static void main(String[] args) {
		Server DB = new Server();
		
		// TODO: loop prompt until sentinel
		Scanner in = new Scanner(System.in);
		System.out.print(">> ");
		String line;
		while(!((line = in.nextLine()).equals("exit"))) {
		Response[] out;
		out = DB.interpret(line);
		

		 //TODO: pretty print w/ tables
		for( int i = 0; i < out.length; i++){
		System.out.println("Success: " + out[i].success());
		System.out.println("Message: " + out[i].message());
//		//System.out.println("Table:   " + out[i].table());
		
		Table temp = out[i].table();
		
		
		
		if (out[i].success() && temp != null){
		Field primaryArray[] = new Field[temp.size()];  
		temp.keySet().toArray(primaryArray);
		Row row = temp.get(primaryArray[0]);
		String dividerLower = ""; 
		String dividerMid = "";
		String space = "";
		for(int z = 0; z < row.size()-3; z++ ){
			dividerLower+= "___________";
			dividerMid+="-----------";
			space += "       ";
		}

	 System.out.print("\n ________________________________" + dividerLower + "\n");
		System.out.print("|" + out[i].message().format("%13s", out[i].message()) + space +  "                   | \n");
		System.out.print("---------------------------------" + dividerMid +"\n|");
		
			
			
			
			for (int k = 0; k < row.size(); k++)
			{
				String columnName[] = new String[row.size()];
				row.keySet().toArray(columnName);
				if (temp.primeCol.equalsIgnoreCase(columnName[k]))
                System.out.print(columnName[k].format("%7s", columnName[k]) +  "*  |");
				else
					System.out.print(columnName[k].format("%7s", columnName[k]) +  "   |");
    
                
			}
			
			if (row.size()< 3){
				System.out.print("          |");
			}
			System.out.print("\n----------------------------------" + dividerMid+ "\n");
			
			
			if(primaryArray.length > 1)
			{
			for (int l = 0; l < primaryArray.length; l++)
			{
				if(primaryArray[l]==null)
					l++;
				
				
			Row rowValue = temp.get(primaryArray[l]);
			System.out.print("|");
			for (int k = 0; k < rowValue.size(); k++){
			String columnName[] = new String[rowValue.size()];
			rowValue.keySet().toArray(columnName);
			String value = rowValue.get(columnName[k]).toString();
            System.out.print(value.format("%7s", value) + "   |" );
			}
			
			if (rowValue.size()< 3){
				System.out.print("          |");
			}
			System.out.print("\n");	
			}			
			
		}
			else{
				System.out.print("|");
				for (int k = 0; k < row.size(); k++){
					String columnName[] = new String[row.size()];
					row.keySet().toArray(columnName);
					String value = row.get(columnName[k]).toString();
		            System.out.print(value.format("%7s", value) + "   |" );
				}
				if (row.size()< 3){
					System.out.print("          |");
				}
				System.out.print("\n");
				
			}
			//System.out.print("\n");	
			System.out.print(" --------------------------------"+ dividerMid +"\n\n");	

		}
		
	System.out.println("");}

	}
	}
}

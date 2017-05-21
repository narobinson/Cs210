package core;

import java.util.HashMap;

import adt.Database;
import adt.Field;
import adt.Row;
import adt.Table;
import query.*;

public class Server {
	
	Database d = new Database();
	
	Database temp = new Database();
	Begin begin = new Begin();
	
	boolean transaction = false;
	

	// DO NOT CHANGE METHOD DEFINITION LINE
	public Response[] interpret(String script) {
		// TODO: instantiate the database
		// TODO: 
		
		
		String[] queries;
		queries = script.split(";");
		
		Response[] R;
		R = new Response[queries.length];
		script = queries[0];
				
	
		  
		for(int i = 0; i < queries.length; i++){
		script = queries[i];
		
		// TODO: improve encapsulation of drivers
		Driver CreateTable = new CreateTable();
		Driver DropTable = new DropTable();
		Driver ShowTable = new ShowTable();
		Driver InsertToTable = new InsertToTable();
		Driver SelectTable = new SelectTable();
		//Driver Begin = new Begin();
		
		// TODO: iterate through queries
		if (script.equalsIgnoreCase("begin") && transaction == false) {
			

			temp.putAll(d);
			
	
			transaction = true;
			R[i]= new Response (true, "Begining..");
			}
		else if (script.equalsIgnoreCase("Commit") && transaction == true) {
			temp = new Database();
			transaction = false;
			R[i]= new Response (true, "Commited");
			}
		else if (script.equalsIgnoreCase("Rollback") && transaction == true) {
			
			
			d = temp;
			
			temp = new Database();
			transaction = false;
			R[i]= new Response (true, "RolledBack");
			}
		
		
		
		
		else if (CreateTable.isValid(script)) {
				R[i] = CreateTable.CreateT(d);
		}
		else if (ShowTable.isValid(script)) {
			R[i] = ShowTable.showResponse(d);	
		}
		else if (DropTable.isValid(script)) {
			R[i] =  DropTable.dropResponse(d);
		}
		else if (InsertToTable.isValid(script)) {
			R[i] =  InsertToTable.insertResponse(d);
		}
		else if (SelectTable.isValid(script)) {
			R[i] =  SelectTable.selectTable(d);
		}
		
		else 
			R[i] = new Response(false, "Unknown query: " + script);
		}
		
		return R;
		// TODO: return one array of all responses
		
	//}
	}
}

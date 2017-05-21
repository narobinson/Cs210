package query;

import adt.Table;

// DO NOT MODIFY CLASS
public class Response {
	private boolean success = false;
	private String message = null;
	private Table table = null;
	
	public Response(boolean success) {
		this.success = success;
	}
	
	public Response(boolean success, String message) {
		this.success = success;
		this.message = message;
	}
	
	public Response(boolean success, Table table) {
		this.success = success;
		this.table = table;
	}
	
	public Response(boolean success, String message, Table table) {
		this.success = success;
		this.message = message;
		this.table = table;
	}
	
	public boolean success() {
		return success;
	}
	
	public String message() {
		return message;
	}
	
	public Table table() {
		return table;
	}
}
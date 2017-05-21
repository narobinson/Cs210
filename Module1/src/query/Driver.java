package query;

import adt.Database;

public interface Driver {
	public boolean isValid(String query);
	public Response getResponse();
	public Response CreateT(Database d);
	public Response dropResponse(Database d);
	public Response showResponse(Database d);
	public Response insertResponse(Database d);
	public Response selectTable(Database d);
	public void begin(Database d);
}

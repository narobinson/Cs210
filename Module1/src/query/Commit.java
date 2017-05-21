//package query;
//
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import adt.Database;
//
//public class Commit implements Driver {
//		
//		Database temp;
//		
//		String action;
//		
//		boolean start = false;
//		
//		private static final Pattern pattern;
//		static {
//			pattern = Pattern.compile(
//				"((Begin)?(RollBack)?(Commit)?){1}".replace(" ", "\\s*"),
//				Pattern.CASE_INSENSITIVE
//			);
//		}
//		
//		
//		public static boolean isValid(String query) {
//			Matcher matcher = pattern.matcher(query.trim());
//			
//			if (!matcher.matches()) return false;
//			
//			action = matcher.group(1).trim();
//			if(action.equalsIgnoreCase("commit")){
//				 start = true;
//			}
//			return start;
//		}
//		
//		
//
//
//		@Override
//		public Response getResponse() {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//
//		@Override
//		public Response CreateT(Database d) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//
//		@Override
//		public Response dropResponse(Database d) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//
//		@Override
//		public Response showResponse(Database d) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//
//		@Override
//		public Response insertResponse(Database d) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//
//		@Override
//		public Response selectTable(Database d) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//
//		@Override
//		public Database begin(Database d) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//}

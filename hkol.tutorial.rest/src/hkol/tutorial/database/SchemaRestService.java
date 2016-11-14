package hkol.tutorial.database;

import java.sql.*;

import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import hkol.tutorial.util.ToJSON;

public class SchemaRestService extends AWSMySql {

	public JSONArray queryReturnPersonsPerCity(String city) throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		
		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();
		
		try {
			conn = mysqlPersonsConnection();
//			query = conn.prepareStatement("select * from Persons " +
//											"where UPPER(City) = ? ");
			query = conn.prepareStatement("select * from Persons");

			
			query.setString(1, city.toUpperCase());
			ResultSet rs = query.executeQuery();
			
			json = converter.toJSONArray(rs);
			query.close();
				        
		}
		catch (SQLException sqlError){
			sqlError.printStackTrace();
			return json;
		}
		catch (Exception e){
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		
		return json;
}
	
}

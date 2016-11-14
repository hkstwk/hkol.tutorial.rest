package hkol.tutorial.rest.persons;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;

import hkol.tutorial.database.SchemaRestService;

@Path("/v2/persons")
public class V2_persons {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnPersons(
					@QueryParam("City") String city) 
					throws Exception {
		
		
		String returnString = null;
		JSONArray json = new JSONArray();
		
		try {
			SchemaRestService dao = new SchemaRestService();
			
			json = dao.queryReturnPersonsPerCity(city);
			returnString = json.toString();
            
		}
		catch (Exception e){
			e.printStackTrace();
			return Response.status(500).entity("Server not able to proces request").build();
		}
		
		return Response.ok(returnString).build();
	}
}

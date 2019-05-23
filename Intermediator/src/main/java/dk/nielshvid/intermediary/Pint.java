package dk.nielshvid.intermediary;

import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Path("/")
public class Pint {


//
//	@Inject
//	IntermediatorClient fisk;

	private Shield shield = new Shield();
	private IntermediatorClient forwardClient;
	private Client client;
	private Cookie sessionId = null;
//
//	public Pint(ForwardClient forwardClient){
//		if(forwardClient != null) {
//			this.forwardClient = forwardClient;
//		}
//	}

    @PostConstruct
    public void init() {
        this.forwardClient = new IntermediatorClient();
        this.client = ClientBuilder.newClient();
    }

	@Path("Freezer/insert/{sample}")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response jens (@PathParam("sample") String fisk){
		System.out.println(fisk);
		return null;
	}

    @Path("biostore/users")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response jensTest (@Context UriInfo info, String body, @QueryParam("sample") String fisk){
        Gson gson = new Gson();

        //TODO: body skal enten også være understøttet,
		//TODO ellers skal vi kunne lave en body om til et MultivaluedMap og derved bruge det vi har i forvejen
		MultivaluedMap<String, String> fisk2 = info.getQueryParameters();


		Map<String,Object> map = new HashMap<String,Object>();
		map = (Map<String,Object>) gson.fromJson(body, map.getClass());
		Map<String,Object> map2 = (Map<String,Object>) gson.fromJson(map.get("owner").toString(), map.getClass());

		System.out.println(map.get("owner"));

        Entities.Sample sample = gson.fromJson(body, Entities.Sample.class);
//        Entities.Person person = gson.fromJson(body, Entities.Person.class);
        System.out.println(sample);



        return null;
    }


//	@Path("Freezer/insert")
//	@GET
//	@Produces(MediaType.TEXT_PLAIN)
//	public Response insertFreezer (@Context UriInfo info, @QueryParam("UserID") String UserID, @QueryParam("Capability") UUID Capability, @QueryParam("EntityID") String EntityID, @QueryParam("xPos") int xPos, @QueryParam("yPos") int yPos){
//
//
//		// Check policies
//		if (shield.authorize(UserID, EntityID, Capability, "Freezer/insert", info.getQueryParameters())){
//			// Forward request
//			return forwardClient.insertFreezer(UserID, EntityID, info.getQueryParameters());
//		};
//		throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
//	}

//	@Path("Freezer/retrieve")
//	@GET
//	@Produces(MediaType.TEXT_PLAIN)
//	public Response retrieveFreezer (@Context UriInfo info, @QueryParam("UserID") String UserID, @QueryParam("Capability") UUID Capability, @QueryParam("EntityID") String EntityID, @QueryParam("xPos") int xPos, @QueryParam("yPos") int yPos){
//
//
//		// Check policies
//		if (shield.authorize(UserID, EntityID, Capability, "Freezer/retrieve", info.getQueryParameters())){
//			// Forward request
//			return forwardClient.retrieveFreezer(UserID, EntityID, info.getQueryParameters());
//		};
//
//		throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
//	}

//	@Path("BoxDB/get")
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response getBoxDB (@Context UriInfo info, @QueryParam("UserID") String UserID, @QueryParam("Capability") UUID Capability, @QueryParam("EntityID") String EntityID){
//
//		// This structure can be optimized (maybe)
//		UUID CapabilityID = shield.generateCapability(UserID, EntityID, "BoxDB/get",info.getQueryParameters());
//
//		// Check policies
//		if (shield.authorize(UserID, EntityID, Capability, "BoxDB/get", info.getQueryParameters())){
//			// Forward request
//			return Response.fromResponse(forwardClient.getBoxDB(UserID, EntityID, info.getQueryParameters())).header("Capability", CapabilityID).build();
//		};
//
//		throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
//	}

//	@Path("BoxDB/insert")
//	@GET
//	@Produces(MediaType.TEXT_PLAIN)
//	public Response insertBoxDB (@Context UriInfo info, @QueryParam("UserID") String UserID, @QueryParam("Capability") UUID Capability, @QueryParam("EntityID") String EntityID, @QueryParam("xPos") int xPos, @QueryParam("yPos") int yPos){
//
//
//		// Check policies
//		if (shield.authorize(UserID, EntityID, Capability, "BoxDB/insert", info.getQueryParameters())){
//			// Forward request
//			return forwardClient.insertBoxDB(UserID, EntityID, info.getQueryParameters());
//		};
//
//		throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
//	}

//	@Path("BoxDB/retrieve")
//	@GET
//	@Produces(MediaType.TEXT_PLAIN)
//	public Response retrieveBoxDB (@Context UriInfo info, @QueryParam("UserID") String UserID, @QueryParam("Capability") UUID Capability, @QueryParam("EntityID") String EntityID){
//
//
//		// Check policies
//		if (shield.authorize(UserID, EntityID, Capability, "BoxDB/retrieve", info.getQueryParameters())){
//			// Forward request
//			return forwardClient.retrieveBoxDB(UserID, EntityID, info.getQueryParameters());
//		};
//
//		throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
//	}

//	@Path("BoxDB/findEmptySlot")
//	@GET
//	@Produces(MediaType.TEXT_PLAIN)
//	public Response findEmptySlotBoxDB (@Context UriInfo info, @QueryParam("UserID") String UserID, @QueryParam("Capability") UUID Capability, @QueryParam("EntityID") String EntityID){
//
//		// This structure can be optimized (maybe)
//		UUID CapabilityID = shield.generateCapability(UserID, EntityID, "BoxDB/findEmptySlot",info.getQueryParameters());
//
//		// Check policies
//		if (shield.authorize(UserID, EntityID, Capability, "BoxDB/findEmptySlot", info.getQueryParameters())){
//			// Forward request
//			return Response.fromResponse(forwardClient.findEmptySlotBoxDB(UserID, EntityID, info.getQueryParameters())).header("Capability", CapabilityID).build();
//		};
//
//		throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
//	}

//	@Path("BoxDB/getID")
//	@GET
////	@Produces(MediaType.APPLICATION_JSON)
//	public Response getIDBoxDB (@Context UriInfo info, @QueryParam("UserID") String UserID, @QueryParam("Capability") UUID Capability, @QueryParam("EntityID") String EntityID, @QueryParam("xPos") int xPos, @QueryParam("yPos") int yPos){
//		// Check policies
//		if (shield.authorize(UserID, EntityID, Capability, "BoxDB/getID", info.getQueryParameters())){
//			// Forward request
//			return forwardClient.getIDBoxDB(UserID, EntityID, info.getQueryParameters());
//		};
//
//		throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
//	}

	@Path("biostore/authenticate/login")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
    //@Context UriInfo info, String body, @QueryParam("UserID") String UserID, @QueryParam("Capability") UUID Capability, @QueryParam("EntityID") String EntityID
	public Response authorizeFreezer (String body){
        String entity = client.target("http://tek-ffu-h0a.tek.sdu.dk:80")
                .path("biostore/authenticate/login").request()
                .post(Entity.json(body), String.class);

        return Response.ok(entity).build();

	    //		JSONObject jsonOb = null;
//		try {
//			jsonOb = new JSONObject(body);
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		// Check policies
//		if (shield.authorize(UserID, EntityID, Capability, "biostore/authenticate/login", info.getQueryParameters(), jsonOb)){
//			// Forward request
////			return IntermediatorClient.authorizeFreezer(UserID, EntityID, info.getQueryParameters());
//		};
//
//		throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);

	}

	@Path("biostore/logicalsets")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFreezer (@Context UriInfo info, String body, @Context HttpHeaders headers){
		Map<String, Cookie> cookies = headers.getCookies();
		// Check policies
		if (shield.authorize("biostore/logicalsets", info.getQueryParameters(), body)){
			// Forward request
			return Response.fromResponse(forwardClient.getLogicalSets(info, body, headers)).build();
		};

		throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);

	}

	@Path("biostore/logicalsets")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response setFreezer (@Context UriInfo info, String body, @QueryParam("UserID") String UserID, @QueryParam("Capability") UUID Capability, @QueryParam("EntityID") String EntityID){

		// Check policies
		if (shield.authorize("biostore/logicalsets", info.getQueryParameters(), body)){
			// Forward request
			return IntermediatorClient.setFreezer(UserID, EntityID, info.getQueryParameters());
		};

		throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);

	}

    @PreDestroy
    public void destroy() {
        this.client.close();
    }
}
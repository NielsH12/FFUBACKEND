package dk.nielshvid.intermediator;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.util.UUID;

@Path("/")
public class RestInterface{
	private IdentityService identityService = new IdentityService();
	private Guard guard = new Guard(identityService);

	@Path("Freezer/insert")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response insertFreezer (@Context UriInfo info, @QueryParam("UserID") String UserID, @QueryParam("Capability") UUID Capability, @QueryParam("EntityID") String EntityID, @QueryParam("xPos") int xPos, @QueryParam("yPos") int yPos){


		// Check policies
		if (guard.authorize(UserID, EntityID, Capability, "Freezer/insert", info.getQueryParameters())){
			// Forward request
			return IntermediatorClient.insertFreezer(UserID, EntityID, xPos, yPos);
		};

		throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);

	}

	@Path("Freezer/retrieve")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response retrieveFreezer (@Context UriInfo info, @QueryParam("UserID") String UserID, @QueryParam("Capability") UUID Capability, @QueryParam("EntityID") String EntityID, @QueryParam("xPos") int xPos, @QueryParam("yPos") int yPos){


		// Check policies
		if (guard.authorize(UserID, EntityID, Capability, "Freezer/retrieve", info.getQueryParameters())){
			// Forward request
			return IntermediatorClient.retrieveFreezer(UserID, EntityID, xPos, yPos);
		};

		throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);

	}

	@Path("BoxDB/get")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBoxDB (@Context UriInfo info, @QueryParam("UserID") String UserID, @QueryParam("Capability") UUID Capability, @QueryParam("EntityID") String EntityID){

		// This structure can be optimized (maybe)
		UUID CapabilityID = guard.generateCapability(UserID, EntityID, "BoxDB/get",info.getQueryParameters());

		// Check policies
		if (guard.authorize(UserID, EntityID, Capability, "BoxDB/get", info.getQueryParameters())){
			// Forward request
			return Response.fromResponse(IntermediatorClient.getBoxDB(UserID, EntityID)).header("Capability", CapabilityID).build();
		};

		throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);

	}

	@Path("BoxDB/insert")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response insertBoxDB (@Context UriInfo info, @QueryParam("UserID") String UserID, @QueryParam("Capability") UUID Capability, @QueryParam("EntityID") String EntityID, @QueryParam("xPos") int xPos, @QueryParam("yPos") int yPos){


		// Check policies
		if (guard.authorize(UserID, EntityID, Capability, "BoxDB/insert", info.getQueryParameters())){
			// Forward request
			return IntermediatorClient.insertBoxDB(UserID, EntityID, xPos, yPos);
		};

		throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);

	}

	@Path("BoxDB/retrieve")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response retrieveBoxDB (@Context UriInfo info, @QueryParam("UserID") String UserID, @QueryParam("Capability") UUID Capability, @QueryParam("EntityID") String EntityID){


		// Check policies
		if (guard.authorize(UserID, EntityID, Capability, "BoxDB/retrieve", info.getQueryParameters())){
			// Forward request
			return IntermediatorClient.retrieveBoxDB(UserID, EntityID);
		};

		throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);

	}

	@Path("BoxDB/findEmptySlot")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response findEmptySlotBoxDB (@Context UriInfo info, @QueryParam("UserID") String UserID, @QueryParam("Capability") UUID Capability, @QueryParam("EntityID") String EntityID){

		// This structure can be optimized (maybe)
		UUID CapabilityID = guard.generateCapability(UserID, EntityID, "BoxDB/findEmptySlot",info.getQueryParameters());

		// Check policies
		if (guard.authorize(UserID, EntityID, Capability, "BoxDB/findEmptySlot", info.getQueryParameters())){
			// Forward request
			return Response.fromResponse(IntermediatorClient.findEmptySlotBoxDB(UserID, EntityID)).header("Capability", CapabilityID).build();
		};

		throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);

	}

	@Path("BoxDB/getID")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getIDBoxDB (@Context UriInfo info, @QueryParam("UserID") String UserID, @QueryParam("Capability") UUID Capability, @QueryParam("EntityID") String EntityID, @QueryParam("xPos") int xPos, @QueryParam("yPos") int yPos){


		// Check policies
		if (guard.authorize(UserID, EntityID, Capability, "BoxDB/getID", info.getQueryParameters())){
			// Forward request
			return IntermediatorClient.getIDBoxDB(UserID, EntityID, xPos, yPos);
		};

		throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);

	}
}
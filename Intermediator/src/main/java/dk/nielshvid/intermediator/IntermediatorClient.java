package dk.nielshvid.intermediator;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

public class IntermediatorClient implements ForwardClient{

    private static final String REST_URI_Freezer = "http://localhost:8082/";
    private static final String REST_URI_BoxDB = "http://localhost:8083/";

    private static Client client = ClientBuilder.newClient();

    public Response insertFreezer(String UserID, String EntityID, int xPos, int yPos){

        return client.target(REST_URI_Freezer + "insert?UserID=" + UserID + "&EntityID=" + EntityID + "&xPos=" + xPos + "&yPos=" + yPos).request().get();
    }

    public Response retrieveFreezer(String UserID, String EntityID, int xPos, int yPos){

        return client.target(REST_URI_Freezer + "retrieve?UserID=" + UserID + "&EntityID=" + EntityID + "&xPos=" + xPos + "&yPos=" + yPos).request().get();
    }

    public Response getBoxDB(String UserID, String EntityID){

        return client.target(REST_URI_BoxDB + "get?UserID=" + UserID + "&EntityID=" + EntityID).request().get();
    }

    public Response insertBoxDB(String UserID, String EntityID, int xPos, int yPos){

        return client.target(REST_URI_BoxDB + "insert?UserID=" + UserID + "&EntityID=" + EntityID + "&xPos=" + xPos + "&yPos=" + yPos).request().get();
    }

    public Response retrieveBoxDB(String UserID, String EntityID){

        return client.target(REST_URI_BoxDB + "retrieve?UserID=" + UserID + "&EntityID=" + EntityID).request().get();
    }

    public Response findEmptySlotBoxDB(String UserID, String EntityID){

        return client.target(REST_URI_BoxDB + "findEmptySlot?UserID=" + UserID + "&EntityID=" + EntityID).request().get();
    }

    public Response getIDBoxDB(String UserID, String EntityID, int xPos, int yPos){

        return client.target(REST_URI_BoxDB + "getID?UserID=" + UserID + "&EntityID=" + EntityID + "&xPos=" + xPos + "&yPos=" + yPos).request().get();
    }
}
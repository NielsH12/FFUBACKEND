import com.google.gson.Gson;
import dk.nielshvid.storagemanagement.dbBox;

import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;


//A70D717E-935E-4CA2-8192-22E65D84BF71
public class ServiceEmulator {

    private static BufferedReader br;
    private static String input;
    private static Gson gson = new Gson();

    private static void insert() throws IOException{
        System.out.println("Enter ID of the box to insert");
        input = br.readLine();

        if (!verifyID(input)){
            System.out.println("Not valid id");
            return;
        }

        String r = RestClient.insertBoxByIDv2(input);

        System.out.println(r);
    }

    private static void retrieve() throws IOException{
        System.out.println("Enter ID of the box");
        input = br.readLine();

        if (!verifyID(input)){
            System.out.println("Not valid id");
            return;
        }

        Response r = RestClient.retrieveBoxByID(input);

        if(r.getStatus() != 200){
            System.out.println(r.getStatus());
            System.out.println(r.getStatusInfo().getReasonPhrase());
            return;
        }

    }

    private static void get() throws IOException{
        System.out.println("Enter ID of the box to retrieve");
        input = br.readLine();

        if (!verifyID(input)){
            System.out.println("Not valid id");
            return;
        }

        Response r = RestClient.retrieveBoxByID(input);

        if(r.getStatus() != 200){
            System.out.println(r.getStatus());
            System.out.println(r.getStatusInfo().getReasonPhrase());
            return;
        }

        dbBox t = gson.fromJson(r.readEntity(String.class), dbBox.class);

        System.out.println("First name: " + t.firstName);
        System.out.println("Last name: " + t.lastName);
        System.out.println("Email: " + t.email);
        System.out.println("Created: " + t.created);
        System.out.println("Expiration: " + t.expiration);

    }


    private static void getBoxInfoByID() throws IOException{
        System.out.println("Enter ID of the box");
        input = br.readLine();
        System.out.println(input);

        if (!verifyID(input)){
            System.out.println("Not valid id");
            return;
        }

        System.out.println("Enter Your user ID");
        String UserID = br.readLine();
        System.out.println(UserID);

        if (!verifyID(UserID)){
            System.out.println("Not valid id");
            return;
        }

        Response r = RestClient.getBoxInfoByIDv2(input, UUID.fromString(UserID));

        if(r.getStatus() != 200){
            System.out.println(r.getStatus());
            System.out.println(r.getStatusInfo().getReasonPhrase());
            return;
        }

        dbBox t = gson.fromJson(r.readEntity(String.class), dbBox.class);

        System.out.println("First name: " + t.firstName);
        System.out.println("Last name: " + t.lastName);
        System.out.println("Email: " + t.email);
        System.out.println("Created: " + t.created);
        System.out.println("Expiration: " + t.expiration);
    }

    private static boolean verifyID(String id){
        try{
            UUID uuid = UUID.fromString(input);
            return true;
        } catch (IllegalArgumentException exception){
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));

        while (true){
            System.out.println("Enter command (retrieve, get, insert)");
            input = br.readLine();

            switch(input){
                case "retrieve":
                    retrieve();
                    break;

                case "get":
                    getBoxInfoByID();
                    break;

                case "insert":
                    insert();
                    break;

                default:
                    System.out.println("Invalid command");
                    break;
            }
            System.out.println();
        }
    }
}
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;


//A70D717E-935E-4CA2-8192-22E65D84BF71
public class ServiceEmulator {

    private static BufferedReader br;
    private static String input;

    private static void insert() throws IOException{
        System.out.println("Enter ID of the box to insert");
        input = br.readLine();

        System.out.println("Enter Your user ID");
        String UserID = br.readLine();
        System.out.println(UserID);

        if (!verifyID(UserID)){
            System.out.println("Not valid id");
            return;
        }

        String r = RestClient.insertBoxByID( UserID, input);

        System.out.println(r);
    }

    private static void retrieve() throws IOException{
        System.out.println("Enter ID of the box");
        input = br.readLine();

        System.out.println("Enter Your user ID");
        String UserID = br.readLine();
        System.out.println(UserID);

        if (!verifyID(UserID)){
            System.out.println("Not valid id");
            return;
        }

        String result = RestClient.retrieveBoxByID(input, UserID);

        System.out.println(result);
    }

    private static void getBoxInfoByID() throws IOException{
        System.out.println("Enter ID of the box");
        String Boxid = br.readLine();
        System.out.println(Boxid);

        if (!verifyBoxID(Boxid)){
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

        String r = RestClient.getBoxInfoByID(UserID, Boxid);
        System.out.println(r);
    }

    private static void jensGet(){
        String UserID = "B6F64D8F-1916-4236-9BBA-039A380329AD";
        String Boxid = "A70D717E-935E-4CA2-8192-22E65D84BF71@1E3C9DBF-C004-4F93-B3BB-D1E45945D482";
        String r = RestClient.getBoxInfoByID(UserID, Boxid);
        System.out.println(r);
    }

    private static boolean verifyBoxID(String id){
        System.out.println("verifyBoxID" + id.length());
        return id.length() == 73;
    }

    private static boolean verifyID(String id){
        try{
            UUID uuid = UUID.fromString(id);
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

                case "jensget":
                    jensGet();
                    break;

                default:
                    System.out.println("Invalid command");
                    break;
            }
            System.out.println();
        }
    }
}
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class PrinterServant extends UnicastRemoteObject implements PrinterService {

    FileReader readerUserLogin = new FileReader("C:\\Users\\MegaMiddelBrutality\\IdeaProjects\\RMI_Lab2\\src\\main\\java\\UserLogin.json");
    FileReader readerUserRoles = new FileReader("C:\\Users\\MegaMiddelBrutality\\IdeaProjects\\RMI_Lab2\\src\\main\\java\\UserRoles.json");
    FileReader readerUserAccess = new FileReader("C:\\Users\\MegaMiddelBrutality\\IdeaProjects\\RMI_Lab2\\src\\main\\java\\RoleAccess.json");
    
    JSONParser jsonParser = new JSONParser();
    Map<String,String> users = new HashMap<String, String>();
    Map<String,String> roles = new HashMap<String, String>();
    Map<String, ArrayList> access = new HashMap<String, ArrayList>();


    protected PrinterServant() throws IOException, ParseException {
        super();
    }

    public Map<String,String> MapUsers() throws IOException, ParseException {

        Object obj = jsonParser.parse(readerUserLogin);
        JSONObject jObjectUser = (JSONObject) obj;
        JSONArray jArrayUser = (JSONArray) jObjectUser.get("users");

        for(int i = 0; i < jArrayUser.size(); i++ ) {
            JSONObject tempObj = (JSONObject) jArrayUser.get(i);
            String user = (String) tempObj.get("firstName");
            String hashedPassword = (String) tempObj.get("hashedPassword");

            users.put(user,hashedPassword);
        }
        return users;
    }

    public Map<String,String> MapUserRoles() throws IOException, ParseException {

        Object obj = jsonParser.parse(readerUserRoles);
        JSONObject jObjectRole = (JSONObject) obj;
        JSONArray jArrayRole = (JSONArray) jObjectRole.get("users");

        for(int i = 0; i < jArrayRole.size(); i++ ) {
            JSONObject tempObj = (JSONObject) jArrayRole.get(i);
            String user = (String) tempObj.get("userName");
            String role = (String) tempObj.get("role");
            roles.put(user,role);
        }
        return roles;
    }

    public Map<String,ArrayList> MapUsersAccess() throws IOException, ParseException, ClassCastException {

        Map<String, ArrayList> tempAccess = new HashMap<>();

        Object obj = jsonParser.parse(readerUserAccess);
        JSONObject jObjectAcces = (JSONObject) obj;
        JSONArray jArrayAcces = (JSONArray) jObjectAcces.get("roles");

        // reads in RoleAcces to a temporary map
        for(int i = 0; i < jArrayAcces.size(); i++ ) {
            JSONObject tempObj = (JSONObject) jArrayAcces.get(i);

                String role = (String) tempObj.get("role");
                ArrayList accessLevel = (ArrayList) tempObj.get("access");

                tempAccess.put(role,accessLevel);
        }

        // Maps users role to access level
        for(Map.Entry <String, String> roles : roles.entrySet()) {
            String user = roles.getKey();
            String role = roles.getValue();

            if(tempAccess.containsKey(role)) {

                access.put(user,tempAccess.get(role));
            }
        }
        return access;
    }

    public boolean PasswordChecker(String username, String password) throws IOException, ParseException, NoSuchAlgorithmException  {
        boolean validated = false;

        if(users.containsKey(username)) {
            if(Objects.equals(users.get(username), HashValue(password))) {
                validated = true;
            }
        } else {
            HashValue(username);
        }
        return validated;
    }

    private static String BytesToHex(byte [] hash) throws FileNotFoundException {

        StringBuilder hexString = new StringBuilder(2* hash.length);

        for(int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length()== 1)
            {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public String HashValue(String input) throws NoSuchAlgorithmException, RemoteException, FileNotFoundException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        return BytesToHex(md.digest(input.getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    public String HelloTest(String input) throws RemoteException {
        return "From server: " + input;
    }

    @Override
    public String print(String filename, String printer, String user, ArrayList access) throws RemoteException {

        if(access.contains("print")) {
            return "From server: Printing : " + filename + " at: " + printer;
        } else {
            return "Access Denied.";
        }
    }

    @Override
    public String queue(String printer, String user, ArrayList access) throws RemoteException {

        if(access.contains("queue")) {
            return "From server: Que for printer: " + printer + "---";
           } else {
            return "Access Denied.";
        }
    }

    @Override
    public String topQueue(String printer, int job, String user, ArrayList access) throws RemoteException {
        if(access.contains("topQueue")) {
            return "From server: Moved job at position: " + job + "to top of " + printer + " que";
        } else {
            return "Access Denied.";
        }
    }

    @Override
    public String start(String user, ArrayList access) throws RemoteException {

        if(access.contains("start")) {
        } else {
            return "Access Denied.";
        }
        return "From server: Started print server";
    }

    @Override
    public String stop(String user, ArrayList access) throws RemoteException {

        if(access.contains("stop")) {
            return "From server: Stopped the print server";
        } else {
            return "Access Denied.";
        }
    }

    @Override
    public String restart(String user, ArrayList access) throws RemoteException {

        if(access.contains("restart")) {
            return "From server: Restarted print server";
        } else {
            return "Access Denied.";
        }
    }

    @Override
    public String status(String printer, String user, ArrayList access) throws RemoteException {
        if(access.contains("status")) {
            return "From server: Status of printer: " + printer + "printer.status == FULL SPEED AHEAD";
        } else {
            return "Access Denied.";
        }

    }

    @Override
    public String readConfig(String parameter, String user, ArrayList access) throws RemoteException {
        if(access.contains("readConfig")) {
            return "From server: Printer configured to: " + parameter;
        } else {
            return "Access Denied.";
        }
    }

    @Override
    public String setConfig(String parameter, String value, String user, ArrayList access) throws RemoteException {
        if(access.contains("setConfiq")) {
            return "From server: Printers config has been set to: " + parameter + "with value: " + value;
        } else {
            return "Access Denied.";
        }
    }
}

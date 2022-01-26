import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;

public interface PrinterService extends Remote {

    public String HelloTest(String input) throws RemoteException;

    public String print(String filename, String printer, String user, ArrayList access)throws RemoteException ;   // prints file filename on the specified printer
    public String queue(String printer, String user, ArrayList access) throws RemoteException;   // lists the print queue for a given printer on the user's display in lines of the form <job number>   <file name>
    public String topQueue(String printer, int job, String user, ArrayList access) throws RemoteException;   // moves job to the top of the queue
    public String start(String user, ArrayList access) throws RemoteException;   // starts the print server
    public String stop(String user, ArrayList access) throws RemoteException ;   // stops the print server
    public String restart(String user, ArrayList access) throws RemoteException;   // stops the print server, clears the print queue and starts the print server again
    public String status(String printer, String user, ArrayList access) throws RemoteException;  // prints status of printer on the user's display
    public String readConfig(String parameter, String user, ArrayList access) throws RemoteException;   // prints the value of the parameter on the user's display
    public String setConfig(String parameter, String value, String user, ArrayList access) throws RemoteException;   // sets the parameter to value

    // Our methods
    public String HashValue(String input) throws NoSuchAlgorithmException, RemoteException, FileNotFoundException;
    public boolean PasswordChecker(String username, String password) throws IOException, ParseException, NoSuchAlgorithmException;
    public Map<String,String> MapUsers() throws IOException, ParseException;
    public Map<String,String> MapUserRoles() throws IOException, ParseException;
    public Map<String, ArrayList> MapUsersAccess() throws IOException, ParseException;
}

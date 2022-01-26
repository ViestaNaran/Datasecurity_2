import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PrinterClient  {

    public static void main(String[] args) throws IOException, NotBoundException, NoSuchAlgorithmException, ParseException {

        PrinterService service = (PrinterService) Naming.lookup("rmi://localhost:5099/Printer");

        Map<String,String> users = new HashMap<String,String>();
        Map<String, String> roles = new HashMap<String,String>();
        Map<String, ArrayList> access = new HashMap<String,ArrayList>();

        users = service.MapUsers();
        roles = service.MapUserRoles();
        access = service.MapUsersAccess();
        Scanner scanner = new Scanner(System.in);

        System.out.println("----" + service.HelloTest("Testing 1..2..3."));
        System.out.println("----" + service.print("File1", "printer1","Alice", access.get("Alice")));
        System.out.println("----" + service.queue("printer1","Bob", access.get("Bob")));
        System.out.println("----" + service.topQueue("printer1", 2,"Cecilie", access.get("Cecilie")));
        System.out.println("----" +  service.start("George",access.get("George")));
        System.out.println("----" + service.stop("Fred",access.get("Fred")));
        System.out.println("----" +  service.restart("Bob",access.get("Bob")));
        System.out.println("----" + service.status("printer1","Cecilie", access.get("Cecilie")));
        System.out.println("----" + service.readConfig("maximum jobs!","David",access.get("David")));
        System.out.println("----" + service.setConfig("maximum jobs:", "10","Erica", access.get("Erica")));

        System.out.println(users);
        System.out.println(roles);
        System.out.println(access);

        /* Login Stuff
        System.out.println("Enter usename: ");
        String userName = scanner.nextLine();
        System.out.println("Enter password: ");
        String userPassword = scanner.nextLine();
        System.out.println("----" + service.PasswordChecker(userName,userPassword));
        */
    }
}
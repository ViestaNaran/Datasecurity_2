import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLOutput;
import java.util.Scanner;

public class PrinterServant extends UnicastRemoteObject implements PrinterService {

    protected PrinterServant() throws RemoteException {
        super();
    }

    public void passwordChecker() throws FileNotFoundException {
        File file = new File("src/main/java/Password_Secret.txt");
        Scanner sc = new Scanner(file);
        System.out.println("Hello from the password checker");
        while (sc.hasNextLine()) {
            System.out.println(sc.nextLine());
        }
    }

    private static String bytesToHex(byte [] hash) throws FileNotFoundException {

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

    public String hashValue(String input) throws NoSuchAlgorithmException, RemoteException, FileNotFoundException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        return bytesToHex(md.digest(input.getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    public String HelloTest(String input) throws RemoteException {
        return "From server: " + input;
    }

    @Override
    public String print(String filename, String printer) throws RemoteException {
        return "From server: Printing: " + filename + "at: " + printer;
    }

    @Override
    public String queue(String printer) throws RemoteException {
        return "From server: Que for printer: " + printer + "---";
    }

    @Override
    public String topQueue(String printer, int job) throws RemoteException {
        return "From server: Moved job at position: " + job + "to top of " + printer + " que";
    }

    @Override
    public String start() throws RemoteException {
        return "From server: Started print server";
    }

    @Override
    public String stop() throws RemoteException {
        return "From server: Stopped the print server";
    }

    @Override
    public String restart() throws RemoteException {
        return "From server: Restarted print server";
    }

    @Override
    public String status(String printer) throws RemoteException {
        return "From server: Status of printer: " + printer + "printer.status == FULL SPEED AHEAD";
    }

    @Override
    public String readConfig(String parameter) throws RemoteException {
        return "From server: Printer configured to: " + parameter;
    }

    @Override
    public String setConfig(String parameter, String value) throws RemoteException {
        return "From server: Printers config has been set to: " + parameter + "with value: " + value;
    }
}

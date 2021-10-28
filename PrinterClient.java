import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class PrinterClient  {

    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException, NoSuchAlgorithmException, FileNotFoundException {


        PrinterService service = (PrinterService) Naming.lookup("rmi://localhost:5099/Printer");
        System.out.println("----" + service.HelloTest("Testing 1..2..3."));
        System.out.println("----" + service.print("File1", "printer1"));
        System.out.println("----" + service.queue("printer1"));
        System.out.println("----" + service.topQueue("printer1", 2));
        System.out.println("----" +  service.start());
        System.out.println("----" + service.stop());
        System.out.println("----" +  service.restart());
        System.out.println("----" + service.status("printer1"));
        System.out.println("----" + service.readConfig("maximum jobs!"));
        System.out.println("----" + service.setConfig("maximum jobs:", "10"));

        System.out.println("----" + "Hashed value from server:" + service.hashValue("Doofenshmirtzs"));
        System.out.println("----" + "Hashed value from server:" + service.hashValue("Ondskabs"));
        System.out.println("----" + "Hashed value from server:" + service.hashValue("Aktieselskab"));
        service.passwordChecker();

    }
}
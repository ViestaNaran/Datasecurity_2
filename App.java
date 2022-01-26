import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class
App {

    public static void main(String[] args) throws IOException, ParseException {
        Registry registry = LocateRegistry.createRegistry(5099);
        registry.rebind("Printer", new PrinterServant());

    }
}

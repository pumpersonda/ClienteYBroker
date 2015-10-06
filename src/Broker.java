import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author PIX
 */
public class Broker {
    
    private final HashMap<String,Service> availableServices = new HashMap();
    
    public static void main(String[] args) {
        Broker aBroker = new Broker();
        aBroker.registerAvailableServices();
        aBroker.attendClients();
    }
    
    public void attendClients(){
        try (ServerSocket serverSocket = new ServerSocket(4444))
        {
            boolean comunicationEnabled = true;
            System.err.println("BROKER IS READY IN LOCAL PORT : " + serverSocket.getLocalPort());
            while(comunicationEnabled)
            {
                Socket clientSocket = serverSocket.accept();
                System.err.println("NEW CLIENT CONNECTED IN GLOBAL PORT : " + clientSocket.getPort());
                 (new Thread (new BrokerThread(clientSocket,this))).start();

            }
        } catch (IOException ex) {
            Logger.getLogger(Broker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Service getService(String keyOfService) throws ServiceNotFoundException{
        if (availableServices.containsKey(keyOfService)) 
        {
            return availableServices.get(keyOfService);
        }else{
            throw new ServiceNotFoundException();
        }
    }
    
    private void registerAvailableServices() {
        Service pixcompuComputer = new Service(5555, "Andre", "192.168.2.0");
        availableServices.put("pastel"           , pixcompuComputer);
        availableServices.put("barras"          , pixcompuComputer);
    }
    
    public String getListOfAvailableServices(){
        Object[] listOfKeys = availableServices.keySet().toArray();
        return Arrays.toString(listOfKeys);
    }
    
}

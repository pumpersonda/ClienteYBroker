import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PIX
 */
public class BrokerThread implements Runnable {

    private final String SERVICE_IS_AVAILABLE_TEXT = "SERVICIO HALLADO";
    private final String WELCOME_MESSAGE_FROM_BROKER = "Psst! Psst! Hey client! if you are lost just type \"SERVICES\"";
    private final Socket clientSocket;
    private final Broker asignedBroker;
    private String regexToken = "\\Q::::\\E";
    private int serviceKeyIndex =0;

    public BrokerThread(Socket clientSocket, Broker asignedBroker) {
        this.clientSocket = clientSocket;
        this.asignedBroker = asignedBroker;
    }

    @Override
    public void run() {
        try (
                PrintWriter outputToClient = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            ) 
        {
            outputToClient.println(WELCOME_MESSAGE_FROM_BROKER);

            String clientResponse;
            while ((clientResponse = inputFromClient.readLine()) != null) {
                requestService(outputToClient, clientResponse);
            }
            this.clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(BrokerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void requestService(PrintWriter outputToClient, String requestFromClient) {
        String[] separateRequest;
        separateRequest=requestFromClient.split(regexToken);

        try {
            if (requestFromClient.trim().equalsIgnoreCase("SERVICES")) {
                String listOfServiceNames = asignedBroker.getListOfAvailableServices();
                outputToClient.println("List of available services : " + listOfServiceNames);
                return;
            }
            System.err.println("Client in PORT : " + clientSocket.getPort() + " WANTS \"" + requestFromClient + "\"");
            Service requestedService = asignedBroker.getService(separateRequest[serviceKeyIndex]);
            ConnectToServer(requestedService,requestFromClient);
            outputToClient.println(SERVICE_IS_AVAILABLE_TEXT);


        } catch (ServiceNotFoundException ex) {
            outputToClient.println(ex.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void ConnectToServer(Service requestService,String requestFromClient) throws IOException {
        String hostName=requestService.getServiceHostName();
        int portNumber=requestService.getServicePort();
        Socket serverSocket = new Socket(hostName,portNumber);

        PrintWriter outServerSocket = new PrintWriter(serverSocket.getOutputStream(), true);
        BufferedReader inServerSocket = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));

        System.out.println("Enviando el mensaje: "+requestFromClient+" Al servidor");
        outServerSocket.println(requestFromClient);
    }

}

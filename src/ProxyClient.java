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
public class ProxyClient {

    private PrintWriter outputToBroker;
    private BufferedReader inputFromBroker;
    private final String COMMAND_LINE_INPUT_STYLE = "ClientRequest>";

    public void connectToBroker() throws IOException {
        Socket clientSocket     = new Socket("Andre", 4444);
        this.outputToBroker     = new PrintWriter(clientSocket.getOutputStream(), true);
        this.inputFromBroker    = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void proccessUserRequestInput() throws IOException {
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        String brokerResponse, userInputString;

        while ((brokerResponse = inputFromBroker.readLine()) != null) 
        {
            System.out.println(brokerResponse);
            System.out.print(COMMAND_LINE_INPUT_STYLE);
            userInputString = userInput.readLine();
            if (userInputString != null) 
            {
                outputToBroker.println(userInputString);
            }
        }
    }

    public static void main(String[] args) {
        try {
            ProxyClient aProxyClient = new ProxyClient();
            aProxyClient.connectToBroker();
            aProxyClient.proccessUserRequestInput();
        } catch (IOException ex) {
            Logger.getLogger(ProxyClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

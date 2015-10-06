/**
 *
 * @author PIX
 */
public class Service {
    private int servicePort;
    private String serviceHostName;
    private String serviceHostIP;

    public Service(int servicePort, String serviceHostName, String serviceHostIP) {
        this.servicePort = servicePort;
        this.serviceHostName = serviceHostName;
        this.serviceHostIP = serviceHostIP;
    }

    public int getServicePort() {
        return servicePort;
    }

    public void setServicePort(int servicePort) {
        this.servicePort = servicePort;
    }

    public String getServiceHostName() {
        return serviceHostName;
    }

    public void setServiceHostName(String serviceHostName) {
        this.serviceHostName = serviceHostName;
    }

    public String getServiceHostIP() {
        return serviceHostIP;
    }

    public void setServiceHostIP(String serviceHostIP) {
        this.serviceHostIP = serviceHostIP;
    }
    
    
}

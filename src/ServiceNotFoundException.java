/**
 *
 * @author PIX
 */
public class ServiceNotFoundException extends Exception{
    
    private final String ERROR_MESSAGE = "NO SE PUDO ENCONTRAR EL SERVICIO :(";

    @Override
    public String getMessage() {
        return ERROR_MESSAGE; 
    }
    
}

package server;

/**
 * Created by Pietro on 16/05/2017.
 */
public class NetworkException extends Exception {
    private String messaggio;

    public NetworkException(String messaggio){
        super(messaggio);
    }


}

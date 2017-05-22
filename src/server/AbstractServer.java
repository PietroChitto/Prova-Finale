package server;

import java.rmi.ServerException;

/**
 * Created by Pietro on 16/05/2017.
 */
public abstract class AbstractServer {

    private ServerInterface controller;

    public AbstractServer(ServerInterface controller){
        this.controller=controller;
    }

    public ServerInterface getController() {
        return controller;
    }

    public abstract void startServer(int port) throws NetworkException, ServerException;







}

package server.rmiServer;

import Client.InterfacciaClient;
import partita.Partita;
import partita.componentiDelTabellone.Giocatore;
import server.GiocatoreRemoto;
import server.Server;
import server.ServerInterface;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.ServerException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Set;

/**
 * Created by Pietro on 16/05/2017.
 */
public class RMIServer extends UnicastRemoteObject implements ServerInterface{


    public RMIServer(/*ServerInterface controller*/) throws RemoteException {

       /* super(controller);*/


    }

    /*@Override*/
    public void startServer(int port) throws ServerException{
        try {
            Registry registry = LocateRegistry.createRegistry(port);
            System.out.println("RegistryCreato");
            try {
                registry.bind("InterfaciaRemotaRMI", this);
            } catch (AlreadyBoundException e) {
                e.printStackTrace();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public InterfaciaRemotaRMI partecipaAPartita(String username, InterfacciaClient controller) throws RemoteException {
        GiocatoreRMI giocatore=new GiocatoreRMI();
        giocatore.setUsername(username);
        giocatore.setControllerClientRMI(controller);
        Server.giocatori.add(giocatore);

        stampaListaGiocatori();
        if(Server.giocatori.size()==4){
            System.out.println("sono nell'if");
            Partita p=new Partita();
            System.out.println("partitaCreata, aggiungo i giocatori");
            for(int i=0; i<4; i++) {
                Giocatore modelloGiocatore=new Giocatore(i);
                Server.giocatori.get(i).setGiocatore(modelloGiocatore);
                p.addGiocatore(Server.giocatori.get(i));
                System.out.println("aggiunto giocatore"+i);
                Server.giocatori.get(i).setPartita(p);
            }
            p.iniziaPartita();
            System.out.print("svuoto lista giocatori");
            Server.giocatori.clear();
            stampaListaGiocatori();
            return giocatore;
        }
        else{
            return giocatore;
        }

    }

    public static void stampaListaGiocatori() {
        for (GiocatoreRemoto g: Server.giocatori){
            System.out.println(g.getUsername());
        }
    }
}

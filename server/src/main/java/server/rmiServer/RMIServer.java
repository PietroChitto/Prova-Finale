package server.rmiServer;

import partita.Partita;
import partita.componentiDelTabellone.Giocatore;
import server.GiocatoreRemoto;
import server.InterfacciaClient;
import server.Server;
import server.ServerInterface;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.ServerException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Pietro on 16/05/2017.
 */
public class RMIServer extends UnicastRemoteObject implements ServerInterface{

    public RMIServer() throws RemoteException {
        super();
    }

    /*@Override*/
    public void startServer(int port) throws ServerException{
        try {
            Registry registry = LocateRegistry.createRegistry(port);
            System.out.println("RegistryCreato");
            try {
                registry.bind("InterfaciaServer", this);
            } catch (AlreadyBoundException e) {
                e.printStackTrace();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public InterfaciaServer partecipaAPartita(String username, InterfacciaClient controller, int numeroGiocatori) throws RemoteException {
        GiocatoreRMI giocatore=new GiocatoreRMI();
        giocatore.setUsername(username);
        giocatore.setControllerClientRMI(controller);

        Server.giocatori.get(numeroGiocatori).add(giocatore);
        System.out.println("Giocatore aggiunto alla lista "+numeroGiocatori);
        if(Server.giocatori.get(numeroGiocatori).size()==numeroGiocatori){
            Partita p=new Partita(numeroGiocatori);
            System.out.println("partitaCreata, aggiungo i giocatori");
            for(int i=0; i<Partita.N_GIOCATORI; i++) {
                Giocatore modelloGiocatore=new Giocatore(i);
                //prendo la lista giusta della mappa e per ogni giocatoreRemoto gli setto un giocatore
                Server.giocatori.get(numeroGiocatori).get(i).setGiocatore(modelloGiocatore);
                p.addGiocatore(Server.giocatori.get(numeroGiocatori).get(i));
                System.out.println("aggiunto giocatore"+i);
                Server.giocatori.get(numeroGiocatori).get(i).setPartita(p);
            }
            p.iniziaPartita();
            System.out.print("svuoto lista giocatori");
            Server.giocatori.get(numeroGiocatori).clear();
            stampaListaGiocatori(numeroGiocatori);
            return giocatore;
        }
        else{
            return giocatore;
        }

    }

    private static void stampaListaGiocatori(int numeroGiocatori) {
        for (GiocatoreRemoto g: Server.giocatori.get(numeroGiocatori)){
            System.out.println(g.getUsername());
        }
    }
}

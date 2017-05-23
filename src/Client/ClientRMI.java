package Client;

import server.GiocatoreRemoto;
import server.ServerInterface;
import server.rmiServer.GiocatoreRMI;
import server.rmiServer.InterfaciaRemotaRMI;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 * Created by Pietro on 16/05/2017.
 */
public class ClientRMI implements InterfacciaClient, Serializable {
    private transient Scanner in;
    private String nickname;
    private int id;
    boolean partitaIncorso;
    private InterfaciaRemotaRMI metodiPartita;

    public ClientRMI() throws RemoteException, NotBoundException {
        in=new Scanner(System.in);
        partitaIncorso=false;
        Registry registry = LocateRegistry.getRegistry(8000);
        System.out.println("Registry caricato");
        ServerInterface inizio = (ServerInterface) registry.lookup("InterfaciaRemotaRMI");
        System.out.println("Oggetto scaricato");
        System.out.println("Inserici un nickName");
        metodiPartita=(InterfaciaRemotaRMI) inizio.partecipaAPartita(in.next(), this);
    }

    private void startClient() {
       /* try {
            metodiPartita.selezionaFamiliare("nero",this.id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }*/
        while(true){}
    }


    @Override
    public void iniziaPartita(int mioId) throws RemoteException {
        this.id=mioId;
        partitaIncorso=true;
        startClient();
    }

    @Override
    public void SpostatoFamiliarePiano(int numeroTorre, int numeroPiano, String coloreDado, int idGiocatore) throws RemoteException {

    }
}

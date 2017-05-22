package Client;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

/**
 * Created by Pietro on 16/05/2017.
 */
public class MainClient {
    public static void main(String[] args) throws IOException, NotBoundException {
        Scanner in=new Scanner(System.in);
        System.out.println("Come vuoi connetterti:\n1-Socket\n2-RMI");
        InterfacciaClient client;
        int scelta=in.nextInt();

        switch (scelta){
            case 1:
                try {
                    client=new ClientSocket();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 2:client=new ClientRMI();
                break;

        }
    }
}

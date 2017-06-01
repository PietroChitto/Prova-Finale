package partita.carteDaGioco;




import partita.carteDaGioco.effetti.*;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Pietro on 11/05/2017.
 * un mazzo è formato da carte dello stesso periodo e tipo
 */
public class CreatoreMazzi {

   private Connection c;
    private Statement s;
    private ResultSet rs;

    public CreatoreMazzi() throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.jdbc.Driver");
        c= DriverManager.getConnection("jdbc:mysql://localhost:3306/carte", "root", "Ilfatato1");
        s = c.createStatement();

    }

    public ArrayList<CartaSviluppo> primoPeriodoTerreni() throws SQLException {
        ArrayList<CartaSviluppo> carte=new ArrayList<CartaSviluppo>();
        carte=generaMazzoCarteTerritorio("1");
        carte=mischiaMazzo(carte);

        return carte;

    }

    public ArrayList<CartaSviluppo> secondoPeriodoTerreni() throws SQLException {
        ArrayList<CartaSviluppo> carte=new ArrayList<CartaSviluppo>();
        carte=generaMazzoCarteTerritorio("2");
        carte=mischiaMazzo(carte);

        return carte;

    }

    public ArrayList<CartaSviluppo> terzoPeriodoTerreni() throws SQLException {

        ArrayList<CartaSviluppo> carte=new ArrayList<CartaSviluppo>();
        carte=generaMazzoCarteTerritorio("3");
        carte=mischiaMazzo(carte);
        return carte;
    }

    public ArrayList<CartaSviluppo> primoPeriodoEdificio() throws SQLException {
        ArrayList<CartaSviluppo> carte=new ArrayList<CartaSviluppo>();
        carte=generaMazzoCarteEdificio("1");
        carte=mischiaMazzo(carte);

        return carte;

    }

    public ArrayList<CartaSviluppo> secondoPeriodoEdificio() throws SQLException {
        ArrayList<CartaSviluppo> carte=new ArrayList<CartaSviluppo>();
        carte=generaMazzoCarteEdificio("2");
        carte=mischiaMazzo(carte);

        return carte;

    }

    public ArrayList<CartaSviluppo> terzoPeriodoEdificio() throws SQLException {
        ArrayList<CartaSviluppo> carte=new ArrayList<CartaSviluppo>();
        carte=generaMazzoCarteEdificio("3");
        carte=mischiaMazzo(carte);

        return carte;

    }

    public ArrayList<CartaSviluppo> primoPeriodoImpresa() throws SQLException {
        ArrayList<CartaSviluppo> carte=new ArrayList<CartaSviluppo>();
        carte=generaMazzoCarteImpresa("1");
        carte=mischiaMazzo(carte);

        return carte;

    }

    public ArrayList<CartaSviluppo> secondoPeriodoImpresa() throws SQLException {
        ArrayList<CartaSviluppo> carte=new ArrayList<CartaSviluppo>();
        carte=generaMazzoCarteImpresa("2");
        carte=mischiaMazzo(carte);

        return carte;

    }

    public ArrayList<CartaSviluppo> terzoPeriodoImpresa() throws SQLException {
        ArrayList<CartaSviluppo> carte=new ArrayList<CartaSviluppo>();
        carte=generaMazzoCarteImpresa("3");
        carte=mischiaMazzo(carte);

        return carte;

    }

    public ArrayList<CartaSviluppo> primoPeriodoPersonaggio() throws SQLException {
        ArrayList<CartaSviluppo> carte=new ArrayList<CartaSviluppo>();
        carte=generaMazzoCartePersonaggio("1");
        carte=mischiaMazzo(carte);

        return carte;

    }

    public ArrayList<CartaSviluppo> secondoPeriodoPersonaggio() throws SQLException {
        ArrayList<CartaSviluppo> carte=new ArrayList<CartaSviluppo>();
        carte=generaMazzoCartePersonaggio("2");
        carte=mischiaMazzo(carte);

        return carte;

    }

    public ArrayList<CartaSviluppo> terzoPeriodoPersonaggio() throws SQLException {
        ArrayList<CartaSviluppo> carte=new ArrayList<CartaSviluppo>();
        carte=generaMazzoCartePersonaggio("3");
        carte=mischiaMazzo(carte);

        return carte;

    }


    private ArrayList<CartaSviluppo> mischiaMazzo(ArrayList<CartaSviluppo> mazzettoNonMischiato) {

        for(int i=0; i<mazzettoNonMischiato.size(); i++){
            //temp=mazzettoNonMischiato.get(i);
            double ran=(Math.random()*100);
            int r=(int)ran%mazzettoNonMischiato.size();
            if(i<r) {
                mazzettoNonMischiato.add(i, mazzettoNonMischiato.get(r));
                mazzettoNonMischiato.remove(r + 1);
            }
        }

        return mazzettoNonMischiato;
    }



    private ArrayList<CartaSviluppo> generaMazzoCarteTerritorio(String periodo) throws SQLException {
        ArrayList<CartaSviluppo> carte= new ArrayList<CartaSviluppo>();
        rs=s.executeQuery("SELECT* FROM carte.carte WHERE periodo="+periodo+" AND tipo='territorio'");

        while(rs.next()){
            int per=rs.getInt("periodo");
            String nome=rs.getString("nome");
            boolean boolRapido=rs.getBoolean("boolRapido");
            boolean boolPermanente=rs.getBoolean("boolPermanente");
            String nomeEffettoRapido=rs.getString("effettoRapido");
            String nomeEffettoPermanente=rs.getString("effettoPermanente");
            int costoAttivazione=rs.getInt("costoAttivazione");
            Effetto effR=creaEffetto(nomeEffettoRapido);
            Effetto effP=creaEffetto(nomeEffettoPermanente);
            String codiceEffR=generaCodiceEffetto(nomeEffettoRapido);
            String codiceEffP=generaCodiceEffetto(nomeEffettoPermanente);
            CartaTerritorio carta=new CartaTerritorio(per, nome, boolRapido,boolPermanente, codiceEffR, codiceEffP, effP,effP,costoAttivazione);

            carte.add(carta);
        }

        return carte;
    }

    private ArrayList<CartaSviluppo> generaMazzoCarteEdificio(String periodo) throws SQLException {
        ArrayList<CartaSviluppo> carte= new ArrayList<CartaSviluppo>();
        rs=s.executeQuery("SELECT* FROM carte.carte WHERE periodo="+periodo+" AND tipo='edificio'");

        while(rs.next()){
            String costoAcquisizione= rs.getString("costoAquisizione");
            int per=rs.getInt("periodo");
            String nome=rs.getString("nome");
            boolean boolRapido=rs.getBoolean("boolRapido");
            boolean boolPermanente=rs.getBoolean("boolPermanente");
            String nomeEffettoRapido=rs.getString("effettoRapido");
            String nomeEffettoPermanente=rs.getString("effettoPermanente");
            int costoAttivazione=rs.getInt("costoAttivazione");
            CostoCarta costo=cifraCosto(costoAcquisizione);
            Effetto effR=creaEffetto(nomeEffettoRapido);
            Effetto effP=creaEffetto(nomeEffettoPermanente);
            String codiceEffR=generaCodiceEffetto(nomeEffettoRapido);
            String codiceEffP=generaCodiceEffetto(nomeEffettoPermanente);
            CartaEdificio carta=new CartaEdificio(costo, costoAttivazione, per,nome, boolRapido,boolPermanente, codiceEffP, codiceEffR, effR,effP);
            carte.add(carta);
        }

        return carte;
    }
    private ArrayList<CartaSviluppo> generaMazzoCartePersonaggio(String periodo) throws SQLException {
        ArrayList<CartaSviluppo> carte= new ArrayList<CartaSviluppo>();
        rs=s.executeQuery("SELECT* FROM carte.carte WHERE periodo="+periodo+" AND tipo='personaggio'");

        while(rs.next()){
            String costoAcquisizione= rs.getString("costoAquisizione");
            int per=rs.getInt("periodo");
            String nome=rs.getString("nome");
            boolean boolRapido=rs.getBoolean("boolRapido");
            boolean boolPermanente=rs.getBoolean("boolPermanente");
            String nomeEffettoRapido=rs.getString("effettoRapido");
            String nomeEffettoPermanente=rs.getString("effettoPermanente");
            CostoCarta costo=cifraCosto(costoAcquisizione);
            Effetto effR=creaEffetto(nomeEffettoRapido);
            Effetto effP=creaEffetto(nomeEffettoPermanente);
            String codiceEffR=generaCodiceEffetto(nomeEffettoRapido);
            String codiceEffP=generaCodiceEffetto(nomeEffettoPermanente);
            CartaPersonaggio carta=new CartaPersonaggio(costo, per,nome, boolRapido,boolPermanente, codiceEffR, codiceEffP, effR,effP);
            carte.add(carta);
        }

        return carte;
    }

    private String generaCodiceEffetto(String codice) {
        String cod;

        if(codice.startsWith("INCREMENTA")){
            cod=codice.substring(10);
            return cod;
        }
        else if(codice.startsWith("AUMENTAFORZA")){
            cod=codice.substring(12);
            return cod;
        }
        else if(codice.startsWith("AUMENTA")){
            cod=codice.substring(7);
            return cod;
        }
        else if(codice.startsWith("SCAMBIA")){
            cod=codice.substring(7);
            return cod;
        }
        else if(codice.startsWith("NUOVAAZIONE")){
            cod=codice.substring(11);
            return cod;
        }

        return null;
    }

    private ArrayList<CartaSviluppo> generaMazzoCarteImpresa(String periodo) throws SQLException {
        ArrayList<CartaSviluppo> carte= new ArrayList<CartaSviluppo>();
        rs=s.executeQuery("SELECT* FROM carte.carte WHERE periodo="+periodo+" AND tipo='impresa'");

        while(rs.next()){
            String costoAcquisizione= rs.getString("costoAquisizione");
            int per=rs.getInt("periodo");
            String nome=rs.getString("nome");
            boolean boolRapido=rs.getBoolean("boolRapido");
            boolean boolPermanente=rs.getBoolean("boolPermanente");
            String nomeEffettoRapido=rs.getString("effettoRapido");
            String nomeEffettoPermanente=rs.getString("effettoPermanente");
            CostoCarta costo=cifraCosto(costoAcquisizione);
            Effetto effR=creaEffetto(nomeEffettoRapido);
            Effetto effP=creaEffetto(nomeEffettoPermanente);
            String codiceEffR=generaCodiceEffetto(nomeEffettoRapido);
            String codiceEffP=generaCodiceEffetto(nomeEffettoPermanente);
            CartaImpresa carta=new CartaImpresa(costo,costo,false, per,nome, boolRapido, codiceEffR, codiceEffP, effR,effP);
            carte.add(carta);
        }

        return carte;
    }


    /**
     * questo metodo prende in ingresso una stringa dal database e genera un oggetto costo
     * @param costoDaCifrare
     * @return
     */

    private CostoCarta cifraCosto(String costoDaCifrare){
        int[] costi=new int[5];
        for(int i=0; i<5;i++){
            costi[i]=costoDaCifrare.charAt(i*2)-48;
        }
        CostoCarta costo= new CostoCarta(costi[0], costi[1], costi[2], costi[3], costi[4]);
        return costo;
    }

    private Effetto creaEffetto(String codice){
        Effetto effetto;

        if(codice.startsWith("INCREMENTA")){
            effetto=new EffettoIncrementa();
            return effetto;
        }
        else if(codice.startsWith("AUMENTAFORZA")){
            effetto =new EffettoAumentaForza();
            return effetto;
        }
        else if(codice.startsWith("AUMENTA")){
            effetto=new EffettoAumenta();
            return effetto;
        }
        else if(codice.startsWith("SCAMBIA")){
            effetto=new EffettoScambia();
            return effetto;
        }
        else if(codice.startsWith("NUOVAAZIONE")){
            effetto=new EffettoNuovaAzione();
            return effetto;
        }

        return new EffettoIncrementa();
    }

}

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

    public ArrayList<CartaTerritorio> primoPeriodoTerreni() throws SQLException {
        ArrayList<CartaTerritorio> carte=new ArrayList<CartaTerritorio>();
        carte=generaMazzoCarteTerritorio("1");
        carte=mischiaMazzoTerritorio(carte);

        return carte;

    }

    public ArrayList<CartaTerritorio> secondoPeriodoTerreni() throws SQLException {
        ArrayList<CartaTerritorio> carte=new ArrayList<CartaTerritorio>();
        carte=generaMazzoCarteTerritorio("2");
        carte=mischiaMazzoTerritorio(carte);

        return carte;

    }

    public ArrayList<CartaTerritorio> terzoPeriodoTerreni() throws SQLException {

        ArrayList<CartaTerritorio> carte=new ArrayList<CartaTerritorio>();
        carte=generaMazzoCarteTerritorio("3");
        carte=mischiaMazzoTerritorio(carte);
        return carte;
    }

    public ArrayList<CartaEdificio> primoPeriodoEdificio() throws SQLException {
        ArrayList<CartaEdificio> carte=new ArrayList<CartaEdificio>();
        carte=generaMazzoCarteEdificio("1");
        carte=mischiaMazzoEdificio(carte);

        return carte;

    }

    public ArrayList<CartaEdificio> secondoPeriodoEdificio() throws SQLException {
        ArrayList<CartaEdificio> carte=new ArrayList<CartaEdificio>();
        carte=generaMazzoCarteEdificio("2");
        carte=mischiaMazzoEdificio(carte);

        return carte;

    }

    public ArrayList<CartaEdificio> terzoPeriodoEdificio() throws SQLException {
        ArrayList<CartaEdificio> carte=new ArrayList<CartaEdificio>();
        carte=generaMazzoCarteEdificio("3");
        carte=mischiaMazzoEdificio(carte);

        return carte;

    }

    public ArrayList<CartaImpresa> primoPeriodoImpresa() throws SQLException {
        ArrayList<CartaImpresa> carte=new ArrayList<CartaImpresa>();
        carte=generaMazzoCarteImpresa("1");
        carte=mischiaMazzoImpresa(carte);

        return carte;

    }

    public ArrayList<CartaImpresa> secondoPeriodoImpresa() throws SQLException {
        ArrayList<CartaImpresa> carte=new ArrayList<CartaImpresa>();
        carte=generaMazzoCarteImpresa("2");
        carte=mischiaMazzoImpresa(carte);

        return carte;

    }

    public ArrayList<CartaImpresa> terzoPeriodoImpresa() throws SQLException {
        ArrayList<CartaImpresa> carte=new ArrayList<CartaImpresa>();
        carte=generaMazzoCarteImpresa("3");
        carte=mischiaMazzoImpresa(carte);

        return carte;

    }

    public ArrayList<CartaPersonaggio> primoPeriodoPersonaggio() throws SQLException {
        ArrayList<CartaPersonaggio> carte=new ArrayList<CartaPersonaggio>();
        carte=generaMazzoCartePersonaggio("1");
        carte=mischiaMazzoPersonaggio(carte);

        return carte;

    }

    public ArrayList<CartaPersonaggio> secondoPeriodoPersonaggio() throws SQLException {
        ArrayList<CartaPersonaggio> carte=new ArrayList<CartaPersonaggio>();
        carte=generaMazzoCartePersonaggio("2");
        carte=mischiaMazzoPersonaggio(carte);

        return carte;

    }

    public ArrayList<CartaPersonaggio> terzoPeriodoPersonaggio() throws SQLException {
        ArrayList<CartaPersonaggio> carte=new ArrayList<CartaPersonaggio>();
        carte=generaMazzoCartePersonaggio("3");
        carte=mischiaMazzoPersonaggio(carte);

        return carte;

    }


    private ArrayList<CartaTerritorio> mischiaMazzoTerritorio(ArrayList<CartaTerritorio> mazzettoNonMischiato) {

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

    private ArrayList<CartaEdificio> mischiaMazzoEdificio(ArrayList<CartaEdificio> mazzettoNonMischiato) {

        for(int i=0; i<mazzettoNonMischiato.size(); i++){
            double ran=(Math.random()*100);
            int r=(int)ran%mazzettoNonMischiato.size();
            if(i<r) {
                mazzettoNonMischiato.add(i, mazzettoNonMischiato.get(r));
                mazzettoNonMischiato.remove(r + 1);
            }
        }

        return mazzettoNonMischiato;
    }

    private ArrayList<CartaPersonaggio> mischiaMazzoPersonaggio(ArrayList<CartaPersonaggio> mazzettoNonMischiato) {

        for(int i=0; i<mazzettoNonMischiato.size(); i++){
            double ran=(Math.random()*100);
            int r=(int)ran%mazzettoNonMischiato.size();
            if(i<r) {
                mazzettoNonMischiato.add(i, mazzettoNonMischiato.get(r));
                mazzettoNonMischiato.remove(r + 1);
            }
        }

        return mazzettoNonMischiato;
    }

    private ArrayList<CartaImpresa> mischiaMazzoImpresa(ArrayList<CartaImpresa> mazzettoNonMischiato) {

        for(int i=0; i<mazzettoNonMischiato.size(); i++){
            double ran=(Math.random()*100);
            int r=(int)ran%mazzettoNonMischiato.size();
            if(i<r) {
                mazzettoNonMischiato.add(i, mazzettoNonMischiato.get(r));
                mazzettoNonMischiato.remove(r + 1);
            }
        }

        return mazzettoNonMischiato;
    }

    private ArrayList<CartaTerritorio> generaMazzoCarteTerritorio(String periodo) throws SQLException {
        ArrayList<CartaTerritorio> carte= new ArrayList<CartaTerritorio>();
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

    private ArrayList<CartaEdificio> generaMazzoCarteEdificio(String periodo) throws SQLException {
        ArrayList<CartaEdificio> carte= new ArrayList<CartaEdificio>();
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
            CostoCarta costo=cifraCosto("costoAcquisizione");
            Effetto effR=creaEffetto(nomeEffettoRapido);
            Effetto effP=creaEffetto(nomeEffettoPermanente);
            String codiceEffR=generaCodiceEffetto(nomeEffettoRapido);
            String codiceEffP=generaCodiceEffetto(nomeEffettoPermanente);
            CartaEdificio carta=new CartaEdificio(costo, costoAttivazione, per,nome, boolRapido,boolPermanente, codiceEffP, codiceEffR, effR,effP);
            carte.add(carta);
        }

        return carte;
    }
    private ArrayList<CartaPersonaggio> generaMazzoCartePersonaggio(String periodo) throws SQLException {
        ArrayList<CartaPersonaggio> carte= new ArrayList<CartaPersonaggio>();
        rs=s.executeQuery("SELECT* FROM carte.carte WHERE periodo="+periodo+" AND tipo='personaggio'");

        while(rs.next()){
            String costoAcquisizione= rs.getString("costoAquisizione");
            int per=rs.getInt("periodo");
            String nome=rs.getString("nome");
            boolean boolRapido=rs.getBoolean("boolRapido");
            boolean boolPermanente=rs.getBoolean("boolPermanente");
            String nomeEffettoRapido=rs.getString("effettoRapido");
            String nomeEffettoPermanente=rs.getString("effettoPermanente");
            CostoCarta costo=cifraCosto("costoAcquisizione");
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

    private ArrayList<CartaImpresa> generaMazzoCarteImpresa(String periodo) throws SQLException {
        ArrayList<CartaImpresa> carte= new ArrayList<CartaImpresa>();
        rs=s.executeQuery("SELECT* FROM carte.carte WHERE periodo="+periodo+" AND tipo='impresa'");

        while(rs.next()){
            String costoAcquisizione= rs.getString("costoAquisizione");
            int per=rs.getInt("periodo");
            String nome=rs.getString("nome");
            boolean boolRapido=rs.getBoolean("boolRapido");
            boolean boolPermanente=rs.getBoolean("boolPermanente");
            String nomeEffettoRapido=rs.getString("effettoRapido");
            String nomeEffettoPermanente=rs.getString("effettoPermanente");
            CostoCarta costo=cifraCosto("costoAcquisizione");
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
            costi[i]=costoDaCifrare.charAt(i*2);
        }
        CostoCarta costo= new CostoCarta(costi[1], costi[0], costi[2], costi[3], costi[4]);
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

        return null;
    }

}

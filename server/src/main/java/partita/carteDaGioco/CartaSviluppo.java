package partita.carteDaGioco;

/**
 * Created by william on 10/05/2017.
 */
public abstract class CartaSviluppo {

    private int periodo;
    private String nome;
    private boolean effettoRapido;
    private boolean effettoPermanente;
    private String tipo;

    public CartaSviluppo(int periodo, String nome, boolean effettoRapido, boolean effettoPermanente, String tipo){

        this.effettoPermanente=effettoPermanente;
        this.effettoRapido=effettoRapido;
        this.nome=nome;
        this.periodo=periodo;
        this.tipo=tipo;

    }

    public int getPeriodo(){
        return periodo;
    }

    public String getNome(){
        return nome;
    }

    public boolean getEffettoRapido(){
        return effettoRapido;
    }

    public boolean getEffettoPermanente(){
        return effettoPermanente;
    }

    public String getTipo(){
        return tipo;
    }

}

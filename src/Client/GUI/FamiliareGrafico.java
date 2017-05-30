package Client.GUI;

import javafx.event.EventHandler;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Shadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.awt.event.MouseEvent;

/**
 * Created by Pietro on 26/05/2017.
 */
public class FamiliareGrafico extends Pane {
    private Color colore;
    private Color coloreDado;
    private Circle cerchio;
    private Rectangle quadrato;
    private boolean selezionato;
    private String nomeColoreDado;

    public FamiliareGrafico(double raggio,Color colore, Color coloreDado){
        selezionato=false;
        this.colore=colore;
        this.coloreDado=coloreDado;
        setRaggio(raggio);
        setNomeColoreDado();

    }

    private void setNomeColoreDado() {
        if(coloreDado.equals(Color.BLACK)){
            nomeColoreDado="nero";
        }
        else if(coloreDado.equals(Color.ORANGE)){
            nomeColoreDado="arancio";
        }
        else if(coloreDado.equals(Color.WHITE)){
            nomeColoreDado="bianco";
        }
        else if(coloreDado.equals(Color.SILVER)){
            nomeColoreDado="neutro";
        }
    }

    public void setRaggio(double raggio){
        cerchio = new Circle(raggio,colore);
        quadrato = new Rectangle(raggio,raggio,coloreDado);
        quadrato.setX(cerchio.getCenterX()-cerchio.getRadius()/2);
        quadrato.setY(cerchio.getCenterY()-cerchio.getRadius()/2);
        this.getChildren().removeAll();
        this.getChildren().addAll(cerchio,quadrato);
        settaEvento();
    }

    private void settaEvento() {
        cerchio.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> evento());
        quadrato.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> evento());
    }

    public void evento(){

    }

    public Color getColore(){
        return colore;
    }

    public Color getColoreDado(){
        return coloreDado;
    }

    public boolean isSelezionato() {
        return selezionato;
    }

    public void setSelezionato(boolean selezionato) {
        this.selezionato = selezionato;
    }

    public String getNomeColoreDado(){
        return nomeColoreDado;
    }

    public void setEffetto(Effect effetto) {
        this.cerchio.setEffect(effetto);
        this.quadrato.setEffect(effetto);
    }
}

package Client.GUI;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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

    public FamiliareGrafico(double raggio,Color colore, Color coloreDado){
        this.colore=colore;
        this.coloreDado=coloreDado;
        setRaggio(raggio);
        aggiungiEventi();

    }

    private void aggiungiEventi() {
        cerchio.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                cerchio.setEffect(new Lighting());
            }
        });
    }

    public void setRaggio(double raggio){
        cerchio = new Circle(raggio,colore);
        quadrato = new Rectangle(raggio,raggio,coloreDado);
        quadrato.setX(cerchio.getCenterX()-cerchio.getRadius()/2);
        quadrato.setY(cerchio.getCenterY()-cerchio.getRadius()/2);
        this.getChildren().addAll(cerchio,quadrato);
    }

    public Color getColore(){
        return colore;
    }

    public Color getColoreDado(){
        return coloreDado;
    }
}

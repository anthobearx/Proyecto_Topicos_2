import java.awt.*;
import java.io.Serializable;

/**Clase utilizada para la creacion de Figuras como:
 * Línea, Rectángulo y Círculo */
/*El implements serializable es para poder guardar las figuras en un .obj*/
public class Figura implements Serializable {
    enum Tipos{
        LINEA,
        RECTANGULO,
        CIRCULO
    }
    //atributos de la clase
    private Shape figura;
    private Color color=Color.black;
    private boolean relleno;
    private Tipos tipo;

    /**Constructor: se necesita de un constructor, para poder almacenar
    los atributos de cada figura, para poder volver a dibujarla, cuando
    sea necesario*/
    public Figura(Shape figura, Color color, boolean relleno){
        this.figura=figura;
        this.color=color;
        this.relleno=relleno;
    }
    /**Metodo para dibujar figura con sus respectivos atributos
    (Lo llamo para poder imprimir las figuras)*/
    public void dibujarFigura(Graphics2D g2D){
        g2D.setColor(color);
        if (relleno){
            g2D.fill(figura);
        }else{
            g2D.draw(figura);
        }
    }
    /** Método get para obtener figura (shape)*/
    public Shape getFigura() {
        return figura;
    }
    /** Método get para obtener tipo de figura*/
    public Tipos getTipo() {
        return tipo;
    }
    /** Método get para obtener color de figura*/
    public Color getColor() {
        return color;
    }
    /** Método get para determinar si la figura es rellena o no*/
    public boolean isRelleno() {
        return relleno;
    }

}
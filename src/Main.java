/*
        Elaborar un programa en Java que resuelva el siguiente problema: Se desea una aplicacion en
        ambiente Windows que permita dibujar figuras utilizando los movimientos de arrastre del raton de la
        computadora.
        La aplicacion debe tener un menu con los submenus Archivo, Dibujar y Ayuda.
        El menu Archivo debe tener las siguientes opciones:
        � Nuevo: Elimina todos los dibujos de la aplicacion.
        � Abrir: Debe mostrar un dialogo de tipo JFileChooser para seleccionar el archivo a abrir. Una
        vez abierto el archivo su contenido (figuras) se deben mostrar en la ventana de la aplicacion.
        � Guardar como: Debe mostrar un dialogo de tipo JFileChooser para seleccionar la carpeta y
        el nombre del archivo donde se debe guardar el contenido (figuras) de la aplicacion (Nota:
        no debe guardarse como archivo de imagen).
        � Imprimir: Esta opcion debe imprimir los dibujos realizados en una hoja de papel. (Utilizar
        codigo anexo).
        � Salir: Esta opcion debe terminar la aplicacion.
        El menu Dibujar debe tener las siguientes opciones:
        � Linea: Al seleccionar esta opcion la aplicacion dibujara una linea cuando se arrastre el raton.
        � Rectangulo: Al seleccionar esta opcion la aplicacion dibujara un rectangulo cuando se
        arrastre el raton.
        � Elipse: Al seleccionar esta opcion la aplicacion dibujara una elipse cuando se arrastre el
        raton.
        � Relleno: Al seleccionar esta opcion se indica que al dibujar rectangulos o elipses, estas se
        dibujaran rellenas. Cuando esta opcion no esta seleccionada, a los rectangulos o elipses se les
        dibujara unicamente el contorno.
        � Color: Debe mostrar un dialogo JColorChooser para seleccionar el color con el que se
        dibujaran las siguientes figuras de la aplicacion.
        El menu Ayuda debe tener las siguientes opciones:
        � Acerca de: Al seleccionar esta opcion se debe mostrar un cuadro de dialogo con los datos
        del autor del programa.
        Nota: No se aceptan trabajos identicos ni con codigo descargado de paginas Web.

 */
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main {
    public static void main(String[] args) {
        /*Creacion de ventana mediante JFrame*/
        JFrame miVentanaP = new JFrame("Proyecto topicos 2- Vega Gonzalez Jesus Antonio");
        miVentanaP.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MiPanelP miPanelp = new MiPanelP();
        miVentanaP.add( miPanelp);

        miVentanaP.setSize(600,400);
        miVentanaP.setVisible(true);


        miVentanaP.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                /*Ni idea, pero ya funciona*/
                if (e.getKeyCode() == KeyEvent.VK_Z && e.isControlDown()) {
                    miPanelp.regresarMetodo();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }
        });
    }
}

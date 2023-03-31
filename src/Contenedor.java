import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;

public class Contenedor implements WindowListener {
    static JFrame miVentanaP;//Hacemos la ventana static para poder utilizarlo en las demás clases
    MiPanelP miPanelitoContenedor;//usado para poder cerrar
    Contenedor(){
        /*Creacion de ventana mediante JFrame*/
        miVentanaP = new JFrame("Proyecto topicos 2- Vega Gonzalez Jesus Antonio");
        miVentanaP.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        miVentanaP.addWindowListener(this);//registrar windowslistener en mi ventana

        //Agrega la logica de MiPanelP
        MiPanelP miPanelp = new MiPanelP();
        miVentanaP.add(miPanelp);

        miVentanaP.setResizable(false);//evita que la ventana sea redimensionable
        miVentanaP.setSize(600,400);
        miVentanaP.setVisible(true);


        miPanelitoContenedor = new MiPanelP();


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

    @Override
    public void windowOpened(WindowEvent e) {
        System.out.println("Ventana abierta");

    }

    @Override
    public void windowClosing(WindowEvent e) {
        miPanelitoContenedor.cambiosSinGuardar(true);
    }

    @Override
    public void windowClosed(WindowEvent e) {



    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Contenedor {
    static JFrame miVentanaP;//Hacemos la ventana static para poder utilizarlo en las demás clases
    Contenedor(){
        /*Creacion de ventana mediante JFrame*/
        miVentanaP = new JFrame("Proyecto topicos 2- Vega Gonzalez Jesus Antonio");
        miVentanaP.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Agrega la logica de MiPanelP
        MiPanelP miPanelp = new MiPanelP();
        miVentanaP.add(miPanelp);

        miVentanaP.setResizable(false);//evita que la ventana sea redimensionable
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

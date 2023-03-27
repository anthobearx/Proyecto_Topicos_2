import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;


class MiPanelP extends JPanel {

    /**Antes tipo Vector, ahora ArrayList.
    Util para guardar los tipos figuras aquí*/
    ArrayList<Figura> figuras;
    /* Atributos modificables a traves del menu dibujar, se utilizan para el metodo crearFigura
    (atributos que juntos forman la clase Figura)*/

    private Shape forma;
    private Color color = new Color(0, 0, 255);
    private Figura.Tipos tipo = Figura.Tipos.LINEA;
    private boolean relleno = false;
    private boolean guardado = false;
    /*Atributos para crear formas (Shape)*/
    Point p1;
    Point p2;
    /*Atributos */
    JMenu menuAyuda;
    JMenuItem menuItemAcercaDe;

    //componentes de dibujo. Se crean en la clase para que se puedan usar en los oyentes
    JRadioButtonMenuItem menuItemLinea;
    JRadioButtonMenuItem menuItemRectangulo;
    JRadioButtonMenuItem menuItemCirculo;
    JRadioButtonMenuItem menuItemRelleno;
    ButtonGroup grupoFiguras;
    //grupoFigura es utilizado para agrupar el tipo de figura (linea, rectangulo o elipse), y que se seleccione
    //solo uno a la vez
    Figura figuraCompleta;//en esta figura se creara la figura con shape, color y relleno


    // Constructor para inicializar valores
    public MiPanelP() {
        MiOyente miOyente = new MiOyente();
        addMouseListener(miOyente);
        addMouseMotionListener(miOyente);

        // Valores iniciales
        figuras = new ArrayList<>();
        forma = null;
        p1 = null;
        p2 = null;

        // Crear el menu
        JMenuBar menuBar = new JMenuBar();

        // Menu Archivo
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem menuItemGuardar = new JMenuItem("Guardar");
        JMenuItem menuItemSalir = new JMenuItem("Salir");
        menuArchivo.add(menuItemGuardar);
        menuArchivo.addSeparator();
        menuArchivo.add(menuItemSalir);

        // Menu Dibujar
        JMenu menuDibujar = new JMenu("Dibujar");
        ManejadorDibujo manejadorDibujo = new ManejadorDibujo();
        //Aqui creo 3 menuitem, para colocarlos dentro del JMenu dibujar
        menuItemLinea = new JRadioButtonMenuItem("Linea", true);
        menuItemRectangulo = new JRadioButtonMenuItem("Rectangulo");
        menuItemCirculo = new JRadioButtonMenuItem("Elipse");
        menuItemRelleno = new JRadioButtonMenuItem("Relleno", false);


        //Oyentes para dibujar
        menuItemCirculo.addActionListener(manejadorDibujo);
        menuItemLinea.addActionListener(manejadorDibujo);
        menuItemRectangulo.addActionListener(manejadorDibujo);
        menuItemRelleno.addActionListener(manejadorDibujo);

        //Esto crea un grupo, con lo cual solo puedo seleccionar uno a la vez
        grupoFiguras = new ButtonGroup();
        grupoFiguras.add(menuItemLinea);
        grupoFiguras.add(menuItemRectangulo);
        grupoFiguras.add(menuItemCirculo);
        //Esto los agrega al menu dibujar
        menuDibujar.add(menuItemLinea);
        menuDibujar.add(menuItemRectangulo);
        menuDibujar.add(menuItemCirculo);
        menuDibujar.addSeparator();
        menuDibujar.add(menuItemRelleno);

        // Menu Ayuda
        menuAyuda = new JMenu("Ayuda");

        ManejadorAyuda manejadorAyuda = new ManejadorAyuda();
        menuAyuda.setMnemonic('y');

        menuItemAcercaDe = new JMenuItem("Acerca de...");
        menuItemAcercaDe.addActionListener(manejadorAyuda);
        menuAyuda.add(menuItemAcercaDe);

        // Agregar los menus a la barra de menu
        menuBar.add(menuArchivo);
        menuBar.add(menuDibujar);
        menuBar.add(menuAyuda);

        // Agregar la barra de menu al panel
        this.setLayout(new BorderLayout());
        this.add(menuBar, BorderLayout.NORTH);
    }
    public Shape crearFigura() {
        Shape f;

        // Preguntar por el tipo de figura
        if (tipo == Figura.Tipos.LINEA)
            f = new Line2D.Double(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        else {
            double xInicio = Math.min(p1.getX(), p2.getX());
            double yInicio = Math.min(p1.getY(), p2.getY());
            double ancho = Math.abs(p2.getX() - p1.getX());
            double alto = Math.abs(p2.getY() - p1.getY());

            if (tipo == Figura.Tipos.RECTANGULO) {
                f = new Rectangle2D.Double(xInicio, yInicio, ancho, alto);
            } else if (tipo == Figura.Tipos.CIRCULO) {
                f = new Ellipse2D.Double(xInicio, yInicio, ancho, alto);
            } else {
                f = new Arc2D.Double(xInicio, yInicio, 1, 1, 1, 1, 1);
            }
        }
        return f;
    }

/**Método para pintar las formas, utiliza la clase padre*/
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        /*NOTA: Este es un for each el cual
         * obtiene las todas figuras del vector "figuras",
         * vector, el cual va guardando todas las figuras
         * y sus respectivas coordenadas. Pregunta el color de estas
         * para que cada vez que se redibuje, se coloque el color indicado.

         * Después pregunta si este es de tipo relleno, si es verdadero,
         * lo rellena, si no, solo dibujara su contorno.

         **/
        for (Figura f : figuras) {
            g2D.setColor(f.getColor());
            if (f.isRelleno()) {
                g2D.fill(f.getFigura());
            } else {
                g2D.draw(f.getFigura());
            }
        }


    }



    class MiOyente extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            // Cambiar cursor
            MiPanelP.this.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
            // Obtener el punto inicial
            p1 = e.getPoint();
        }

        public void mouseReleased(MouseEvent e) {
            // Cambiar cursor
            MiPanelP.this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            // Obtener el punto final
            p2 = e.getPoint();//se obtiene el segundo punto
            forma = crearFigura();//se guarda solo el shape en forma
            figuraCompleta = new Figura(forma, color, relleno);//aquí se crea la figura con sus 3 atributos (shape, color y relleno)
            figuras.add(figuraCompleta);//aqui se agrega a la lista de vectores la figura completa
            forma = null;//limpiar el atributo, para poder crear otra figura
            repaint();//se vuelve a pintar to-do
        }

        public void mouseDragged(MouseEvent e) {
            //Obtener objeto Graphics2D
            Graphics2D g2D = (Graphics2D) MiPanelP.this.getGraphics();
            // Establecer modo XOR de dibujo
            g2D.setXORMode(MiPanelP.this.getBackground());

            // Si la figura existe, borrarla
            if (forma != null)
                g2D.draw(forma);

            // Crear la nueva figura y dibujarla
            p2 = e.getPoint();
            forma = crearFigura();

            g2D.draw(forma);

        }
    }


    private class ManejadorDibujo implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JMenuItem click = (JMenuItem) e.getSource();
            if (click == menuItemLinea) {
                tipo = Figura.Tipos.LINEA;
            } else if (click == menuItemRectangulo) {
                tipo = Figura.Tipos.RECTANGULO;
            } else if (click == menuItemCirculo) {
                tipo = Figura.Tipos.CIRCULO;
            } else {
                if (menuItemRelleno.isSelected()) {
                    relleno = true;
                } else {
                    relleno = false;
                }
            }

        }
    }

    private class ManejadorAyuda implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {

            JMenuItem click = (JMenuItem) e.getSource();

            if (click == menuItemAcercaDe) {
                JOptionPane.showMessageDialog(MiPanelP.this,
                        "Programa hecho con mucho cari\u00F1o por:" +
                                "\nJes\u00FAs Antonio Vega Gonz\u00E1lez el 23/Marzo/2023" +
                                "\npara la clase de T\u00F3picos Avanzados :)" +
                                "\nSaludos",
                        "Acerca de", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}

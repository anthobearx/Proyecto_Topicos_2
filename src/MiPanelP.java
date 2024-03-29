import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.*;
import java.util.ArrayList;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;


class MiPanelP extends JPanel {

    /**Antes tipo Vector, ahora ArrayList.
    Util para guardar los tipos figuras aquí*/
    static ArrayList<Figura> figuras;
    /* Atributos modificables a traves del menu dibujar, se utilizan para el metodo crearFigura
    (atributos que juntos forman la clase Figura)*/

    private Shape forma;
    private Color color = new Color(0, 0, 255);
    private Figura.Tipos tipo = Figura.Tipos.LINEA;
    private boolean relleno = false;
    /*Variable para determinar si el archivo se guardo con los ultimos cambios o no*/
    boolean dibujoGuardado=false;
    /*Atributos para crear formas (Shape)*/
    Point p1;
    Point p2;
    /*Atributos JMenu para JMenuBar*/
    JMenu menuArchivo;
    JMenu menuDibujar;
    JMenu menuAyuda;
    JMenuItem menuItemAcercaDe;
    //componentes de archivo
    JMenuItem menuItemNuevo;
    JMenuItem menuItemAbrir;
    JMenuItem menuItemGuardar;
    JMenuItem menuItemImprimir;
    JMenuItem menuItemSalir;
    //componentes de dibujo. Se crean en la clase para que se puedan usar en los oyentes
    JRadioButtonMenuItem menuItemLinea;
    JRadioButtonMenuItem menuItemRectangulo;
    JRadioButtonMenuItem menuItemCirculo;
    JCheckBoxMenuItem menuItemRelleno;
    JMenuItem menuItemColor;
    //grupoFigura es utilizado para agrupar el tipo de figura (linea, rectangulo o elipse), y que se seleccione
    //solo uno a la vez
    ButtonGroup grupoFiguras;

    Figura figuraCompleta;//en esta figura se creara la figura con shape, color y relleno

    /*Regresar*/
    //ImageIcon regresarImagen = new ImageIcon("src\\imagenes\\flecha.png");
    JLabel imagenLabel;

    /*Ruta seleccionada*/
    String rutaSeleccionada=".";
    JFileChooser archivoVentana;


    // Constructor para inicializar valores
    public MiPanelP() {
        /*Oyentes de la clase*/
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

        /*Estos separadores son utilizados para que exista un espacio personalizado entre los MenuBar
         *El setsize fue puesto para darle un tamaño
         *El setMinimunSize y el MaximunSize son para que BorderLayout no edite el tamaño de estos
         * */
        //Separador 1
        JSeparator menuSeparador = new JSeparator();//Separador para JMenuBar
        menuSeparador.setSize(8,0);
        menuSeparador.setMinimumSize(menuSeparador.getSize());
        menuSeparador.setMaximumSize(menuSeparador.getSize());
        //Separador 2
        JSeparator menuSeparador2 = new JSeparator();//Separador para JMenuBar
        menuSeparador2.setSize(5,0);
        menuSeparador2.setMinimumSize(menuSeparador2.getSize());
        menuSeparador2.setMaximumSize(menuSeparador2.getSize());

        // Menu Archivo
        menuArchivo = new JMenu("Archivo");
        //Crearlos y darle texto
        menuItemNuevo = new JMenuItem("Nuevo");
        menuItemAbrir = new JMenuItem("Abrir");
        menuItemGuardar = new JMenuItem("Guardar como...");
        menuItemImprimir = new JMenuItem("Imprimir");
        menuItemSalir = new JMenuItem("Salir");
        //Agregarlos
        menuArchivo.add(menuItemNuevo);
        menuArchivo.add(menuItemAbrir);
        menuArchivo.add(menuItemGuardar);
        menuArchivo.add(menuItemImprimir);
        menuArchivo.addSeparator();
        menuArchivo.add(menuItemSalir);

        //Oyentes Archivo
        ManejadorArchivo manejadorArchivo = new ManejadorArchivo();
        menuItemNuevo.addActionListener(manejadorArchivo);
        menuItemAbrir.addActionListener(manejadorArchivo);
        menuItemGuardar.addActionListener(manejadorArchivo);
        menuItemImprimir.addActionListener(manejadorArchivo);
        menuItemSalir.addActionListener(manejadorArchivo);

        // Menu Dibujar
        menuDibujar = new JMenu("Dibujar");
        ManejadorDibujo manejadorDibujo = new ManejadorDibujo();
        //Aqui creo 3 menuitem, para colocarlos dentro del JMenu dibujar
        menuItemLinea = new JRadioButtonMenuItem("Linea", true);
        menuItemRectangulo = new JRadioButtonMenuItem("Rectangulo");
        menuItemCirculo = new JRadioButtonMenuItem("Elipse");
        menuItemRelleno = new JCheckBoxMenuItem("Relleno", false);
        menuItemColor = new JMenuItem("Color...");



        //Oyentes para dibujar
        menuItemCirculo.addActionListener(manejadorDibujo);
        menuItemLinea.addActionListener(manejadorDibujo);
        menuItemRectangulo.addActionListener(manejadorDibujo);
        menuItemRelleno.addActionListener(manejadorDibujo);
        menuItemRelleno.setEnabled(false);
        menuItemColor.addActionListener(manejadorDibujo);



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
        menuDibujar.add(menuItemColor);
        // Menu Ayuda
        menuAyuda = new JMenu("Ayuda");

        ManejadorAyuda manejadorAyuda = new ManejadorAyuda();
        menuAyuda.setMnemonic('y');

        menuItemAcercaDe = new JMenuItem("Acerca de...");
        menuItemAcercaDe.addActionListener(manejadorAyuda);
        menuAyuda.add(menuItemAcercaDe);

        //Menu Regresar
        imagenLabel = new JLabel("<--");
        /*Aqui se hace que el Jlabel tenga un Listener, sin la necesidad de crear el Listener (Oyente) como metodo,
        * y lo añade ahi mismo*/
        imagenLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                regresarMetodo();
            }
        });



        // Agregar los menus a la barra de menu
        /*Añadi un dos separadorres, ya que para añadir mas separadores, necesito crear otros, y no lo vi necesario*/
        menuBar.add(menuSeparador);
        menuBar.add(imagenLabel);
        menuBar.add(menuSeparador2);
        menuBar.add(menuArchivo);
        menuBar.add(menuDibujar);
        menuBar.add(menuAyuda);

        // Agregar la barra de menu al panel
        this.setLayout(new BorderLayout());
        this.add(menuBar, BorderLayout.NORTH);
    }
    /**Metodo utilizado para la creacion de figuras (shape), utiliza el punto 1 y el punto 2
     * de presionar (p1) y al sortar click (p2), para obtener esas coordenadas y crear una figura*/
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
    /**En este método está la lógica del botón regresar:
     * Pregunta si hay al menos una figura dentro del arreglo, si la hay, elimina la última agregada,
     * si no, salta un mensaje de error, diciendo que no hay figuras que borrar*/
    public void regresarMetodo(){
        if(figuras.size()>0){
            figuras.remove(figuras.size() - 1);
            dibujoGuardado=false;//al hacer un movimiento el dibujo se pondra como guardado:falso
            repaintMetodo();
        }else{
            JOptionPane.showMessageDialog(MiPanelP.this,
                    "No se ha podido borrar ninguna figura, debido a que no hay ninguna :o!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**Este metodo fue creado con finalidad de que al utilizar un repaint, se avise al programa que los cambios guardados
     * son falsos, es decir que no se han guardado los cambios*/
    public void repaintMetodo(){
        repaint();
        dibujoGuardado=false;
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
            //System.out.println("El mouse se solto");
            MiPanelP.this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            // Obtener el punto final
            p2 = e.getPoint();//se obtiene el segundo punto
            dibujoGuardado=false;//al hacer un movimiento el dibujo se pondra como guardado:falso
            forma = crearFigura();//se guarda solo el shape en forma
            figuraCompleta = new Figura(forma, color, relleno);//aquí se crea la figura con sus 3 atributos (shape, color y relleno)
            figuras.add(figuraCompleta);//aqui se agrega a la lista de vectores la figura completa
            forma = null;//limpiar el atributo, para poder crear otra figura
            repaintMetodo();//se vuelve a pintar to-do
        }

        public void mouseDragged(MouseEvent e) {
            //Obtener objeto Graphics2D
            Graphics2D g2D = (Graphics2D) MiPanelP.this.getGraphics();
            //g2D.setColor(color);//Añadir el color actual
            g2D.setColor(new Color(color.getRed(),color.getGreen(),color.getBlue(),0));
            // Establecer modo XOR de dibujo
            g2D.setXORMode(MiPanelP.this.getBackground());

            // Si la figura existe, borrarla
            if (forma != null) {
                if (menuItemRelleno.isSelected()){
                    g2D.fill(forma);
                }else{
                    g2D.draw(forma);
                }

            }
            // Crear la nueva figura y dibujarla
            p2 = e.getPoint();
            forma = crearFigura();

            if (menuItemRelleno.isSelected()){
                g2D.fill(forma);
            }else{
                g2D.draw(forma);
            }

        }
    }

    private class ManejadorDibujo implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JMenuItem click = (JMenuItem) e.getSource();
            if (click == menuItemLinea) {
                tipo = Figura.Tipos.LINEA;
                menuItemRelleno.setEnabled(false);
            } else if (click == menuItemRectangulo) {
                tipo = Figura.Tipos.RECTANGULO;
                menuItemRelleno.setEnabled(true);
            } else if (click == menuItemCirculo) {
                tipo = Figura.Tipos.CIRCULO;
                menuItemRelleno.setEnabled(true);
            } else if (click == menuItemRelleno) {
                if (menuItemRelleno.isSelected()) {
                    relleno = true;
                    menuItemLinea.setEnabled(false);
                } else {
                    relleno = false;
                    menuItemLinea.setEnabled(true);
                }
            } else if (click == menuItemColor){
                JDialog dialogColores = new JDialog();
                JColorChooser ventanaColor = new JColorChooser();
                dialogColores.add(ventanaColor, BorderLayout.CENTER);
                JButton btnAceptarColor = new JButton("Aceptar");
                dialogColores.add(btnAceptarColor,BorderLayout.SOUTH);
                dialogColores.pack();
                dialogColores.setLocationRelativeTo(null);
                dialogColores.setVisible(true);
                //Al pulsar el boton se hace esto
                btnAceptarColor.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        color=ventanaColor.getColor();
                        dialogColores.dispose();
                    }
                });

            }

        }
    }
    private class ManejadorArchivo implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JMenuItem click = (JMenuItem) e.getSource();
            if(click==menuItemNuevo){
                cambiosSinGuardar(false);//antes de abrir algo nuevo, preguntara si hay cambios y si quiere guardarlos
                //figuras = new ArrayList<>(); //por terminos de eficiencia, se utiliza removeAll
                figuras.removeAll(figuras);
                repaintMetodo();

            }else if(click==menuItemAbrir){
                cambiosSinGuardar(false);//antes de abrir algo mas, preguntara si hay cambios y si quiere guardarlos
                abrirDibujo();
            }else if(click==menuItemGuardar){
                guardarDibujo();
            }else if(click==menuItemImprimir){
                print();
            }else if (click==menuItemSalir){
                cambiosSinGuardar(true);
            }
        }
    }
    public void print(){
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setJobName(" Programa de Dibujo ");

        pj.setPrintable (new Printable() {
            public int print(Graphics pg, PageFormat pf, int pageNum){
                if (pageNum > 0){
                    return Printable.NO_SUCH_PAGE;
                }

                Graphics2D g2 = (Graphics2D) pg;
                g2.translate(pf.getImageableX(), pf.getImageableY());
                //Estas linea provocan que la imagen se vea chiquita
                /**
                double factorEscalaX = 100.0;
                double factorEscalaY = 100.0;

                //g2.scale(factorEscalaX/pf.getImageableWidth(), factorEscalaY/pf.getImageableHeight());*/
                /*Debido a que el metodo print() anterior imprimia el panel y el JMenuBar, se ideo el metodo printFiguras
                * pera imprimir unicamente las figuras dibujadas*/
                //paint(g2);
                printFiguras(g2);
                return Printable.PAGE_EXISTS;
            }
        });
        if (pj.printDialog() == false)
            return;

        try {
            pj.print();
        } catch (PrinterException ex) {
            // handle exception
        }
    }
    public void printFiguras(Graphics2D g2) {
        for (Figura figura : figuras) {
            figura.dibujarFigura(g2);
        }
    }
    private boolean abrirDibujo() {
        boolean regresar = false;
        File directorioActual = new File(rutaSeleccionada);//directorio donde abrira la ventana
        try {
            //aqui entrara por defecto en la carpeta del proyecto por rutaSeleccionada="."
            if (rutaSeleccionada=="."){
                archivoVentana = new JFileChooser(directorioActual.getCanonicalPath());
            } else {
                //se crea filechooser, en este camino se pondra la ruta en la que se quedo la rutaseleccionada
                archivoVentana = new JFileChooser(directorioActual);
            }
            //se crea un filtro para solo utilizar la extension de ahi
            FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivo dibujo - Vega Gonzalez", "dbjx");
            archivoVentana.setFileFilter(filtro);
            if (archivoVentana.showOpenDialog(Contenedor.miVentanaP) == 0) {
                // Obtener la ruta seleccionada
                rutaSeleccionada = archivoVentana.getSelectedFile().getAbsolutePath();
                // Archivo con la ruta seleccionada y el nombre del archivo
                File archivoAbrir = new File(rutaSeleccionada);
                FileInputStream fis = new FileInputStream(archivoAbrir);//se lee archivo
                ObjectInputStream ois = new ObjectInputStream(fis);//se lee objeto
                figuras = (ArrayList<Figura>) ois.readObject();//se pasan los objetos al arraylist
                fis.close();
                ois.close();
                menuItemNuevo.setEnabled(true);
                regresar = true;
                repaint();//este repaint, no es necesario llamar al metodo para poner guardado como falso
                dibujoGuardado=true;//tecnicamente, al ser un archivo que se esta abriendo, ya esta guardado
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(Contenedor.miVentanaP, "Error al intentar abrir el archivo: \n"+ ex, "Programa de Dibujo",
                    2);
        }
        return regresar;
    }
    private boolean guardarDibujo() {
        boolean regresar = false;
        File directorioActual = new File(rutaSeleccionada);//directorio donde abrira la ventana
        try {
            //aqui entrara por defecto en la carpeta del proyecto por rutaSeleccionada="."
            if (rutaSeleccionada=="."){
                archivoVentana = new JFileChooser(directorioActual.getCanonicalPath());
            } else {
                //se crea filechooser, en este camino se pondra la ruta en la que se quedo la rutaseleccionada
                archivoVentana = new JFileChooser(directorioActual);
            }


            //se crea extension y se añade filtro
            FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivo dibujo - Vega Gonzalez","dbjx");
            archivoVentana.setFileFilter(filtro);
            if (archivoVentana.showSaveDialog(Contenedor.miVentanaP) == 0) {
                // Obtener la ruta seleccionada
                rutaSeleccionada = archivoVentana.getSelectedFile().getAbsolutePath();

                // Añadir la extensión si no la tiene
                if (!rutaSeleccionada.toLowerCase().endsWith(".dbjx")) {
                    rutaSeleccionada += ".dbjx";
                }

                // Crear el nuevo File con la ruta seleccionada y el nombre del archivo
                File nuevoArchivo = new File(rutaSeleccionada);

                //se guarda archivo, utilizando el arraylist donde se guardaron las figuras
                FileOutputStream fos = new FileOutputStream(nuevoArchivo);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(figuras);
                fos.close();
                oos.close();
                JOptionPane.showMessageDialog(Contenedor.miVentanaP,  nuevoArchivo.getName()+ " guardado correctamente", "Programa de Dibujo",
                        -1);
                regresar = true;
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(Contenedor.miVentanaP, "Error al guardar el archivo: " + ex, "Programa de Dibujo", 2);
        }
        dibujoGuardado = true;//el archivo se guardo
        return regresar;
    }
    /**Este metodo recibe el parametro boolean de cerrar, si este es true, cerrada despues de guardarse si fue necesario*/
    public void cambiosSinGuardar(boolean cerrar){
        //si el dibujo no esta guardado y hay al menos una figura, pregunta si quiere guardar
        if (dibujoGuardado==false && figuras.size()>0){
            int respuesta = JOptionPane.showConfirmDialog(Contenedor.miVentanaP,"Hay cambios sin guardar, ¿Deseas guardar el archivo","Guardar antes...",0);
            if(respuesta==JOptionPane.YES_OPTION){
                guardarDibujo();//opcion de guardar
                //puede que despues de todo no se guarde(que cierre la ventana antes de guardar)
                if (dibujoGuardado==true && cerrar==true){
                    System.exit(0);//se saldra unicamente si se guardo el archivo
                }
                //si se le da que no, se sale sin guardar
            } else if (respuesta==JOptionPane.NO_OPTION && cerrar==true) {
                System.exit(0);
            }
            //si no se cumple ninguna condicion, simplemente, salir
        }else if (cerrar==true){
            System.exit(0);
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




package clases;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.*;

public class Clase1 extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    JTextField num = new JTextField(10);
    JTextField nombre = new JTextField(25);
    JTextField loc = new JTextField(25);

    JLabel mensaje = new JLabel(" ----------------------------- ");
    JLabel titulo = new JLabel("GESTIÓN DE DEPARTAMENTOS.");

    JLabel lnum = new JLabel("NUMERO DEPARTAMENTO:");
    JLabel lnom = new JLabel("NOMBRE:");
    JLabel lloc = new JLabel("LOCALIDAD:");

    JButton balta = new JButton("Insertar Depar.t");
    JButton consu = new JButton("Consultar Depart.");

    JButton fin = new JButton("CERRAR");
    Color c;

    public Clase1(JFrame f) {
        setTitle("GESTIÓN DE DEPARTAMENTOS.");

        JPanel p0 = new JPanel();
        c = Color.CYAN;
        p0.add(titulo);
        p0.setBackground(c);
        JPanel p1 = new JPanel();
        p1.setLayout(new FlowLayout());
        p1.add(lnum);
        p1.add(num);
        p1.add(consu);
        JPanel p2 = new JPanel();
        p2.setLayout(new FlowLayout());
        p2.add(lnom);
        p2.add(nombre);
        JPanel p3 = new JPanel();
        p3.setLayout(new FlowLayout());
        p3.add(lloc);
        p3.add(loc);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(p0);
        add(p1);
        add(p2);
        add(p3);
        pack();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        balta.addActionListener(this);
        fin.addActionListener(this);
        consu.addActionListener(this);

    }

    public void actionPerformed(ActionEvent e) {
        int dep, confirm;
        if (e.getSource() == balta) alta();

        if (e.getSource() == consu) consultar();

        if (e.getSource() == fin) { //SE PULSA EL BOTON salir 	
            System.exit(0);
            //dispose();   	
        }

    }

    public void consultar() {
        int dep;
        mensaje.setText(" has pulsado el boton consulta");
        try {
            dep = Integer.parseInt(num.getText());
            if (dep > 0) {
                if (consultaDepartamento(dep)) {
                    mensaje.setText("DEPARTAMENTO EXISTE.");
                    visualiza(dep);
                } else {
                    mensaje.setText("DEPARTAMENTO NO EXISTE.");
                    nombre.setText(" ");
                    loc.setText(" ");
                }
            } else {
                mensaje.setText("DEPARTAMENTO DEBE SER MAYOR QUE 0");
            }
            
        } catch (java.lang.NumberFormatException ex) //controlar el error del Integer.parseInt
        {
            mensaje.setText("DEPARTAMENTO ERRÓNEO");
        } catch (IOException ex2) {
            mensaje.setText(" ERRORRR EN EL FICHERO. Fichero no existe. (ALTA)");
        }
    }

    public void alta() {
        int dep;
        mensaje.setText(" has pulsado el boton alta");
        try {
            dep = Integer.parseInt(num.getText());
            if (dep > 0) {
                if (consultaDepartamento(dep)) {
                    mensaje.setText(EXISTEDEPARTAMENTO);
                } else {
                    mensaje.setText("NUEVO DEPARTAMENTO.");
                    grabar(dep, nombre.getText(), loc.getText());
                    mensaje.setText("NUEVO DEPARTAMENTO GRABADO.");
                }
            } else {
                mensaje.setText("DEPARTAMENTO DEBE SER MAYOR QUE 0");
            }
            
        } catch (java.lang.NumberFormatException ex) //controlar el error del Integer.parseInt
        {
            mensaje.setText("DEPARTAMENTO ERRÓNEO.");
        } catch (IOException ex2) {
            mensaje.setText("ERRORRR EN EL FICHERO. Fichero no existe. (ALTA)");
        }
    }
    public static final String EXISTEDEPARTAMENTO = "DEPARTAMENTO EXISTE.";

    boolean consultaDepartamento(int dep) throws IOException {
        long pos;
        int depa;
        File fichero = new File("AleatorioDep.dat");
        RandomAccessFile file = new RandomAccessFile(fichero, "r");
        // Calculo del reg a leer
        try {
            pos = 44 * (dep - 1);
            if (file.length() == 0) {
                return false; // si está vacío
            }
            file.seek(pos);
            depa = file.readInt();
            file.close();
            System.out.println("Depart leido:" + depa);
            if (depa > 0) {
                return true;
            } else {
                return false;
            }
        } catch (IOException ex2) {
            System.out.println(" ERRORRR al leerrrrr..");
            return false;
        }
    } // fin consultar

    void visualiza(int dep) {
        String nom = "", loca = "";
        long pos;
        int depa;
        File fichero = new File("AleatorioDep.dat");
        try {
            RandomAccessFile file = new RandomAccessFile(fichero, "r");
            // Calculo del reg a leer
            pos = 44 * (dep - 1);
            file.seek(pos);
            depa = file.readInt();
            System.out.println("Depart leido:" + depa);
            char nom1[] = new char[10], aux, loc1[] = new char[10];
            for (int i = 0; i < 10; i++) {
                aux = file.readChar();
                nom1[i] = aux;
            }
            for (int i = 0; i < 10; i++) {
                aux = file.readChar();
                loc1[i] = aux;
            }
            nom = new String(nom1);
            loca = new String(loc1);
            System.out.println("DEP: " + dep + ", Nombre: " + nom + ", Localidad: " + loca);
            nombre.setText(nom);
            loc.setText(loca);
            file.close();
        } catch (IOException e1) {
            System.out.println("ERRROR AL LEEERRRRRR AleatorioDep.dat");
            e1.printStackTrace();
        }
    } // fin visualiza

    void grabar(int dep, String nom, String loc) {
        long pos;
        StringBuffer buffer = null;
        File fichero = new File("AleatorioDep.dat");
        try {
            RandomAccessFile file = new RandomAccessFile(fichero, "rw");
            // Calculo del reg a leer
            pos = 44 * (dep - 1);
            //if (file.length()==0) return false; // si está vacío

            file.seek(pos);
            file.writeInt(dep);
            buffer = new StringBuffer(nom);
            buffer.setLength(10);
            file.writeChars(buffer.toString());//insertar nombre
            buffer = new StringBuffer(loc);
            buffer.setLength(10);
            file.writeChars(buffer.toString());//insertar loc
            file.close();
            System.out.println(" GRABADOOO el " + dep);
        } catch (IOException e1) {
            System.out.println("ERRROR AL grabarr AleatorioDep.dat");
            e1.printStackTrace();
        }
    } // fin grabar
}//fin clase
